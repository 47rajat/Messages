package com.wssholmes.stark.messagesotp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wssholmes.stark.messagesotp.data.DataContract;

public class SendMessageActivity extends AppCompatActivity {
    private static final String LOG_TAG = SendMessageActivity.class.getSimpleName();

    public static final String INTENT_MOBILE_NUMBER_KEY = "Mobile Number";
    public static final String INTENT_CONTACT_NAME_KEY = "Contact Name ";
    private static final int SMS_PERMISSION_ID = 0;

    private TextView mSendMessage;
    private Button mSendButton;
    private SmsManager mSmsManager;

    private static final int OTP_UPPER_LIMIT = 999999;
    private static final int OTP_LOWER_LIMIT = 100000;

    private String mPhoneNumber;
    private String mOTPMessage;
    private Context mContext;
    private String mContactName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        mContext = this;

        if(getIntent().hasExtra(INTENT_MOBILE_NUMBER_KEY)){
            mPhoneNumber = getIntent().getStringExtra(INTENT_MOBILE_NUMBER_KEY);
        }
        if(getIntent().hasExtra(INTENT_MOBILE_NUMBER_KEY)){
            mContactName = getIntent().getStringExtra(INTENT_CONTACT_NAME_KEY);
        }
        final int OTP = (int)(Math.random()*(OTP_UPPER_LIMIT - OTP_LOWER_LIMIT) + OTP_LOWER_LIMIT);
        mOTPMessage = getString(R.string.otp_message, Integer.toString(OTP));
        mSendMessage = (TextView) findViewById(R.id.text_message);
        mSendMessage.setText(mOTPMessage);

        mSmsManager = SmsManager.getDefault();

        mSendButton = (Button) findViewById(R.id.send_button);
        checkSMSPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case SMS_PERMISSION_ID:
                if(grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableSendButton();
                } else {
                    Snackbar.make(this.findViewById(R.id.activity_send_message),
                            getString(R.string.sms_permission_denied_message),
                            Snackbar.LENGTH_INDEFINITE).show();
                }
                break;
            default:
                Log.v(LOG_TAG, "Unknown permission requested");
        }
    }

    private void checkSMSPermission() {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.SEND_SMS},
                    SMS_PERMISSION_ID);
        } else {
            enableSendButton();
        }
    }

    private void enableSendButton(){
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPhoneNumber != null){
                    try {
                        mSmsManager.sendTextMessage(mPhoneNumber, null, mOTPMessage, null, null);
                        Toast.makeText(getApplicationContext(), R.string.message_sent_success,
                                Toast.LENGTH_LONG).show();

                        updateMessageList(mContactName, mOTPMessage);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                R.string.message_sent_failure,
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, R.string.phone_number_error,Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void updateMessageList(String name, String mesaage){
        ContentValues contentValues = new ContentValues();
        long time = System.currentTimeMillis();

        contentValues.put(DataContract.SentMessageEntry.COLUMN_CONTACT_NAME, mContactName);
        contentValues.put(DataContract.SentMessageEntry.COLUMN_MESSAGE_SENT, mesaage);
        contentValues.put(DataContract.SentMessageEntry.COLUMN_TIME_SENT, time);

        getContentResolver().insert(DataContract.SentMessageEntry.CONTENT_URI, contentValues);

    }
}

package com.seankim.hsschedulemanager;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PlanEditActivity extends AppCompatActivity {

    public final static String EXTRA_RETURN_MESSAGE = "com.seankim.hsschedulemanager.RETURN_MESSAGE";
    public final static String EXTRA_RETURN_MESSAGE1 = "com.seankim.hsschedulemanager.RETURN_MESSAGE1";
    public final static String EXTRA_RETURN_MESSAGE2 = "com.seankim.hsschedulemanager.RETURN_MESSAGE2";
    public final static String EXTRA_RETURN_MESSAGE3 = "com.seankim.hsschedulemanager.RETURN_MESSAGE3";
    public final static String EXTRA_RETURN_MESSAGE4 = "com.seankim.hsschedulemanager.RETURN_MESSAGE4";


    private EditText mStartTime;
    private EditText mEndTime;
    private RadioGroup mState;
    private EditText mSubject;
    private RadioButton mSelectedState;
    private Button mSaveBtn;
    private Button mCancelBtn;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_edit);

        mStartTime = findViewById(R.id.StatTimeET);
        mEndTime = findViewById(R.id.EndTimeET);
        mSubject = findViewById(R.id.subjectET);
        mState = findViewById(R.id.stateRadioGroup);
        int selectedID = mState.getCheckedRadioButtonId();
        mSelectedState = findViewById(selectedID);

        id = getIntent().getIntExtra("id", -1);
        if(id != -1) {
            String subject = getIntent().getStringExtra("subject");
            mSubject.setText(subject);
        }

        mSaveBtn = findViewById(R.id.saveBtn);
        mCancelBtn = findViewById(R.id.cancelBtn);

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanEditActivity.this.finish();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTime = findViewById(R.id.StatTimeET);
                mEndTime = findViewById(R.id.EndTimeET);
                mSubject = findViewById(R.id.subjectET);
                mState = findViewById(R.id.stateRadioGroup);
                int selectedID = mState.getCheckedRadioButtonId();
                mSelectedState = findViewById(selectedID);

                String startTime = mStartTime.getText().toString();
                String endTime = mEndTime.getText().toString();
                String subject = mSubject.getText().toString();
                String state = mSelectedState.getText().toString();


                Intent intent = new Intent();
                intent.putExtra(EXTRA_RETURN_MESSAGE, startTime);
                intent.putExtra(EXTRA_RETURN_MESSAGE1, endTime);
                intent.putExtra(EXTRA_RETURN_MESSAGE2, subject);
                intent.putExtra(EXTRA_RETURN_MESSAGE3, state);
                if(id != -1) {
                    intent.putExtra(EXTRA_RETURN_MESSAGE4, id);
                }

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("subject") && getIntent().hasExtra("subject") && getIntent().hasExtra("startTime") && getIntent().hasExtra("endTime") && getIntent().hasExtra("state")) {
            String subject = getIntent().getStringExtra("subject");
            String startTime = getIntent().getStringExtra("startTime");
            String endTime = getIntent().getStringExtra("endTime");

            mStartTime = findViewById(R.id.StatTimeET);
            mEndTime = findViewById(R.id.EndTimeET);
            mSubject = findViewById(R.id.subjectET);

            mStartTime.setText(startTime);
            mEndTime.setText(endTime);
            mSubject.setText(subject);

        }
    }





}

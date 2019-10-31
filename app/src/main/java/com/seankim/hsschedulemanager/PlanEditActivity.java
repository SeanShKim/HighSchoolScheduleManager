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


    private EditText mStartTime;
    private EditText mEndTime;
    private RadioGroup mState;
    private EditText mSubject;
    private RadioButton mSelectedState;
    private Button mSaveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_edit);

        mSaveBtn = findViewById(R.id.saveBtn);

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


                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });





    }





}

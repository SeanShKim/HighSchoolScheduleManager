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
    public final static String EXTRA_RETURN_MESSAGE5 = "com.seankim.hsschedulemanager.RETURN_MESSAGE5";
    public final static String EXTRA_RETURN_MESSAGE6 = "com.seankim.hsschedulemanager.RETURN_MESSAGE6";
    public final static String EXTRA_RETURN_MESSAGE7 = "com.seankim.hsschedulemanager.RETURN_MESSAGE7";



    private EditText mStartHour;
    private EditText mStartMinute;
    private EditText mEndHour;
    private EditText mEndMinute;
    private RadioGroup mState;
    private EditText mSubject;
    private RadioButton mSelectedState;
    private Button mSaveBtn;
    private Button mCancelBtn;
    private int id = -1;

    Plan.State state;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_edit);

        mStartHour = findViewById(R.id.StartHourET);
        mEndHour = findViewById(R.id.EndHourET);
        mStartMinute = findViewById(R.id.StartMinuteET);
        mEndMinute = findViewById(R.id.EndMinuteET);
        mSubject = findViewById(R.id.subjectET);
        mState = findViewById(R.id.stateRadioGroup);


        mState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.done:
                        state = Plan.State.DONE;
                        break;
                    case R.id.in_progress:
                        state = Plan.State.IN_PROGRESS;
                        break;
                    case R.id.planned:
                        state = Plan.State.PLANNED;
                        break;
                }
            }
        });

        id = getIntent().getIntExtra("id", -1);
        if(id != -1) {
            String subject = getIntent().getStringExtra("subject");
            mSubject.setText(subject);

            String startHour = getIntent().getStringExtra("startHour");
            mStartHour.setText(startHour);

            String startMinute = getIntent().getStringExtra("startMinute");
            mStartHour.setText(startMinute);

            String endHour = getIntent().getStringExtra("endHour");
            mStartHour.setText(endHour);

            String endMinute = getIntent().getStringExtra("endMinute");
            mEndMinute.setText(endMinute);

            String state = getIntent().getStringExtra("state");
            if(state.equals("In Progress")) {
                mSelectedState = findViewById(R.id.in_progress);
                mSelectedState.setChecked(true);
            }
            else if(state.equals("Done")) {
                mSelectedState = findViewById(R.id.done);
                mSelectedState.setChecked(true);
            }
            else {
                mSelectedState = findViewById(R.id.planned);
                mSelectedState.setChecked(true);

            }





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
                mStartHour = findViewById(R.id.StartHourET);
                mEndHour = findViewById(R.id.EndHourET);
                mStartMinute = findViewById(R.id.StartMinuteET);
                mEndMinute = findViewById(R.id.EndMinuteET);
                mSubject = findViewById(R.id.subjectET);
                mState = findViewById(R.id.stateRadioGroup);
                int selectedID = mState.getCheckedRadioButtonId();
                mSelectedState = findViewById(selectedID);

                String startHour = mStartHour.getText().toString() + ":";
                String endHour = mEndHour.getText().toString() + ":";
                String startMinute = mStartMinute.getText().toString();
                String endMinute = mEndMinute.getText().toString();
                String subject = mSubject.getText().toString();
                String stateName = mSelectedState.getText().toString();
                if(startMinute.length() == 1) {
                    startMinute = "0" + startMinute;
                }
                if(endMinute.length() == 1) {
                    endMinute = "0" + endMinute;
                }


                Intent intent = new Intent();
                intent.putExtra(EXTRA_RETURN_MESSAGE, startHour);
                intent.putExtra(EXTRA_RETURN_MESSAGE1, endHour);
                intent.putExtra(EXTRA_RETURN_MESSAGE2, subject);
                intent.putExtra(EXTRA_RETURN_MESSAGE3, stateName);
                intent.putExtra(EXTRA_RETURN_MESSAGE5, state);
                intent.putExtra(EXTRA_RETURN_MESSAGE4, id);
                intent.putExtra(EXTRA_RETURN_MESSAGE6, startMinute);
                intent.putExtra(EXTRA_RETURN_MESSAGE7, endMinute);


                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("subject") && getIntent().hasExtra("startHour") && getIntent().hasExtra("endHour") && getIntent().hasExtra("state") && getIntent().hasExtra("endMinute") && getIntent().hasExtra("startMinute")) {
            String subject = getIntent().getStringExtra("subject");
            String startHour = getIntent().getStringExtra("startHour");
            String startMinute = getIntent().getStringExtra("startMinute");
            String endMinute = getIntent().getStringExtra("endMinute");
            String endHour = getIntent().getStringExtra("endHour");

            mStartHour = findViewById(R.id.StartHourET);
            mEndHour = findViewById(R.id.EndHourET);
            mStartMinute = findViewById(R.id.StartMinuteET);
            mEndMinute = findViewById(R.id.EndMinuteET);
            mSubject = findViewById(R.id.subjectET);

            mStartHour.setText(startHour);
            mEndHour.setText(endHour);
            mStartMinute.setText(startMinute);
            mEndMinute.setText(endMinute);
            mSubject.setText(subject);
        }
    }

}



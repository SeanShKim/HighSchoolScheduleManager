package com.seankim.hsschedulemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewMyPlanActivity extends AppCompatActivity {

    public static final int TEXT_REQUEST = 1;


    private RecyclerView mPlanRecyclerView;
    private PlanAdapter mPlanAdapter;
    private ArrayList<Plan> mPlanArrayList;
    private TextView setDate;
    private int year;
    private int month;
    private int day;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_plan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEditPlan(view);
            }
        });

        setDate = findViewById(R.id.DateDisplayTV);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDate.setText(month + "/" + day + "/" + year);
        mPlanArrayList = new ArrayList<>();

        mPlanRecyclerView = (RecyclerView) findViewById(R.id.planRecyclerView);
        mPlanAdapter = new PlanAdapter(mPlanArrayList, this);
        mPlanRecyclerView.setAdapter(mPlanAdapter);
        mPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void launchEditPlan(View view) {
        Intent intent = new Intent(this, PlanEditActivity.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String startTime = data.getStringExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE);
                String endTime = data.getStringExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE1);
                String subject = data.getStringExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE2);
                String state = data.getStringExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE3);

                Plan plan = new Plan(subject, startTime, endTime, state);
                mPlanArrayList.add(plan);
                mPlanAdapter.notifyDataSetChanged();
            }
        }
    }


}

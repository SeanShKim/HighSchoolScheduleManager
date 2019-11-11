package com.seankim.hsschedulemanager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    private ArrayList<Plan> mPlanArrayList;
    private Context mContext;
    private LayoutInflater mLayoutInflator;
    private OnClickListener mDelegate;

    public PlanAdapter(ArrayList<Plan> mPlanArrayList, Context mContext, OnClickListener delegate) {
        this.mPlanArrayList = mPlanArrayList;
        this.mContext = mContext;
        mLayoutInflator = LayoutInflater.from(mContext);
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public PlanAdapter.PlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_item, viewGroup, false);
        return new PlanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.PlanViewHolder planViewHolder, int i) {
        planViewHolder.bind(mPlanArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return mPlanArrayList.size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mStartHour;
        private TextView mStartMinute;
        private TextView mEndHour;
        private TextView mEndMinute;
        private TextView mSubject;
        private TextView mState;
        private Button mStateButton;
        private ConstraintLayout mConstraint;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            mStartHour = itemView.findViewById(R.id.startHourTV);
            mStartMinute = itemView.findViewById(R.id.startMinuteTV);
            mEndHour = itemView.findViewById(R.id.endHourTV);
            mEndMinute = itemView.findViewById(R.id.endMinuteTV);
            mSubject = itemView.findViewById(R.id.subjectTV);
            mState = itemView.findViewById(R.id.stateMessageTV);
            mConstraint = itemView.findViewById(R.id.constraintLayout);
            mStateButton = itemView.findViewById(R.id.stateBtn);
            itemView.setOnClickListener(this);

        }

        public void bind(Plan plan) {
            mStartHour.setText(plan.getStartHour());
            mEndHour.setText(plan.getEndHour());
            mStartMinute.setText(plan.getStartMinute());
            mEndMinute.setText(plan.getEndMinute());
            mSubject.setText(plan.getSubject());
            mState.setText(plan.getState());

            switch (plan.getActualState()) {
                case IN_PROGRESS:
                    mStateButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.in_progress_btn));
                    break;
                case DONE:
                    mStateButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.done_btn));
                    break;
                case PLANNED:
                    mStateButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.planned_btn));
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mDelegate.onClickViewHolder(v, pos);
        }
    }
}
interface OnClickListener {
    void onClickViewHolder(View view, int pos);
}


package com.seankim.hsschedulemanager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        private TextView mStartTime;
        private TextView mEndTime;
        private TextView mSubject;
        private TextView mState;
        private ConstraintLayout mConstraint;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            mStartTime = itemView.findViewById(R.id.startTimeTV);
            mEndTime = itemView.findViewById(R.id.endTimeTV);
            mSubject = itemView.findViewById(R.id.subjectTV);
            mState = itemView.findViewById(R.id.stateMessageTV);
            mConstraint = itemView.findViewById(R.id.constraintLayout);
            itemView.setOnClickListener(this);

        }

        public void bind(Plan plan) {
            mStartTime.setText(plan.getStartTime());
            mEndTime.setText(plan.getEndTime());
            mSubject.setText(plan.getSubject());
            mState.setText(plan.getState());
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

package com.seankim.hsschedulemanager;

import android.content.Context;
import android.support.annotation.NonNull;
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

    public PlanAdapter(ArrayList<Plan> mPlanArrayList, Context mContext) {
        this.mPlanArrayList = mPlanArrayList;
        this.mContext = mContext;
        mLayoutInflator = LayoutInflater.from(mContext);
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



        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            mStartTime = itemView.findViewById(R.id.startTimeTV);
            mEndTime = itemView.findViewById(R.id.endTimeTV);
            mSubject = itemView.findViewById(R.id.subjectTV);
            mState = itemView.findViewById(R.id.stateMessageTV);
        }

        public void bind(Plan plan) {
            mStartTime.setText(plan.getStartTime());
            mEndTime.setText(plan.getEndTime());
            mSubject.setText(plan.getSubject());
            mState.setText(plan.getState());
        }

        @Override
        public void onClick(View v) {

        }
    }
}

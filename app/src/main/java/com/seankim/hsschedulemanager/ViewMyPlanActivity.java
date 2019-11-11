package com.seankim.hsschedulemanager;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;

import static android.support.v7.widget.helper.ItemTouchHelper.*;
import static android.support.v7.widget.helper.ItemTouchHelper.Callback.makeMovementFlags;

public class ViewMyPlanActivity extends AppCompatActivity implements OnClickListener {

    public static final int TEXT_REQUEST = 1;

    private static final String TAG = "ViewMyPlanActivity";


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
        mPlanAdapter = new PlanAdapter(mPlanArrayList, getApplicationContext(), this);
        mPlanRecyclerView.setAdapter(mPlanAdapter);
        mPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                mPlanArrayList.remove(position);
                mPlanAdapter.notifyDataSetChanged();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(mPlanRecyclerView);

        mPlanRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });


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
                String startHour = data.getStringExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE);
                String endHour = data.getStringExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE1);
                String subject = data.getStringExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE2);
                String startMinute = data.getStringExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE6);
                String endMinute = data.getStringExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE7);
                String state = data.getStringExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE3);
                int id = data.getIntExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE4, -1);
                Plan.State s = (Plan.State) data.getSerializableExtra(PlanEditActivity.EXTRA_RETURN_MESSAGE5);

                if (id == -1) {
                    Plan plan = new Plan(subject, startHour, startMinute, endHour, endMinute, state);
                    plan.setActualState(s);
                    mPlanArrayList.add(plan);
                    mPlanAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < mPlanArrayList.size(); i++) {
                        if (mPlanArrayList.get(i).getId() == id) {
                            Plan plan = new Plan(subject, startHour, startMinute, endHour, endMinute, state);
                            plan.setActualState(s);
                            mPlanArrayList.remove(i);
                            mPlanArrayList.add(i, plan);
                            mPlanAdapter.notifyDataSetChanged();
                            return;
                        }
                    }

                }

//                Plan plan = new Plan(subject, startTime, endTime, state);
//                mPlanArrayList.add(plan);
//                mPlanAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClickViewHolder(View view, int pos) {
        final Plan plan = mPlanArrayList.get(pos);
        String subject = plan.getSubject();
        String startHour = plan.getStartHour();
        String startMinute = plan.getStartMinute();
        String endHour = plan.getEndHour();
        String endMinute = plan.getEndMinute();
        String state = plan.getState();


        Intent intent = new Intent(this, PlanEditActivity.class);
        intent.putExtra("id", plan.getId());
        intent.putExtra("subject", subject);
        intent.putExtra("startHour", startHour);
        intent.putExtra("startMinute", startMinute);
        intent.putExtra("endHour", endHour);
        intent.putExtra("endMinute", endMinute);
        intent.putExtra("state", state);
        startActivityForResult(intent, TEXT_REQUEST);

    }

    enum ButtonsState {
        GONE,
        LEFT_VISIBLE,
        RIGHT_VISIBLE
    }

    class SwipeController extends Callback {
        private boolean swipeBack = false;
        private ButtonsState buttonShowedState = ButtonsState.GONE;
        private static final float buttonWidth = 300;
        private RectF buttonInstance = null;

        private RecyclerView.ViewHolder currentItemViewHolder = null;
        private SwipeControllerActions buttonsActions = null;


        public SwipeController(SwipeControllerActions buttonsActions) {
            this.buttonsActions = buttonsActions;
        }


        @Override
        public int convertToAbsoluteDirection(int flags, int layoutDirection) {
            if (swipeBack) {
                swipeBack = false;
                return 0;
            }
            return super.convertToAbsoluteDirection(flags, layoutDirection);
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, LEFT | RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            if (actionState == ACTION_STATE_SWIPE) {
                if (buttonShowedState != ButtonsState.GONE) {
                    if (buttonShowedState == ButtonsState.LEFT_VISIBLE) dX = Math.max(dX, buttonWidth);
                    if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) dX = Math.min(dX, -buttonWidth);
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                else {
                    setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

            if (buttonShowedState == ButtonsState.GONE) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            currentItemViewHolder = viewHolder;

        }

        private void setTouchListener(final Canvas c,
                                      final RecyclerView recyclerView,
                                      final RecyclerView.ViewHolder viewHolder,
                                      final float dX, final float dY,
                                      final int actionState, final boolean isCurrentlyActive) {
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                    if (swipeBack) {
                        if (dX < -buttonWidth) buttonShowedState = ButtonsState.RIGHT_VISIBLE;
                        else if (dX > buttonWidth) buttonShowedState  = ButtonsState.LEFT_VISIBLE;

                        if (buttonShowedState != ButtonsState.GONE) {
                            setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                            setItemsClickable(recyclerView, false);
                        }
                    }
                    return false;
                }
            });
        }

        private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                    return false;
                }
            });
        }

        private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        SwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                        recyclerView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        setItemsClickable(recyclerView, true);
                        swipeBack = false;

                        if (buttonsActions != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {

                            if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                                buttonsActions.onRightClicked(viewHolder.getAdapterPosition());
                            }
                        }
                        buttonShowedState = ButtonsState.GONE;
                        currentItemViewHolder = null;

                    }
                    return false;
                }
            });

        }

        private void setItemsClickable(RecyclerView recyclerView,
                                       boolean isClickable) {
            for (int i = 0; i < recyclerView.getChildCount(); ++i) {
                recyclerView.getChildAt(i).setClickable(isClickable);
            }
        }

        private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {
            float buttonWidthWithoutPadding = buttonWidth - 20;
            float corners = 16;

            View itemView = viewHolder.itemView;
            Paint p = new Paint();

            RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            p.setColor(Color.RED);
            c.drawRect(rightButton, p);
            drawText("DELETE", c, rightButton, p);

            buttonInstance = null;

            if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                buttonInstance = rightButton;
            }
        }

        private void drawText(String text, Canvas c, RectF button, Paint p) {
            float textSize = 60;
            p.setColor(Color.WHITE);
            p.setAntiAlias(true);
            p.setTextSize(textSize);

            float textWidth = p.measureText(text);
            c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
        }

        public void onDraw(Canvas c) {
            if (currentItemViewHolder != null) {
                drawButtons(c, currentItemViewHolder);
            }
        }


    }

    public abstract class SwipeControllerActions {

        public void onLeftClicked(int position) {}

        public void onRightClicked(int position) {}
    }



}

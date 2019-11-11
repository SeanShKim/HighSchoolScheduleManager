package com.seankim.hsschedulemanager;

import android.graphics.Color;
import android.support.annotation.Nullable;

public class Plan {
    private String subject;
    private String startHour;
    private String startMinute;
    private String endHour;
    private String endMinute;
    private String state;
    private int id;

    private State actualState;

    public Plan(String subject, String startHour, String startMinute, String endHour, String endMinute, String state) {
        this.subject = subject;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.state = state;
        this.id = (int) (Math.random()*129);

    }


    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getStartMinute() {
        return startMinute;
    }

    public String getEndMinute() {
        return endMinute;
    }

    public String getStartHour() {
        return startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public String getState() {
        return state;
    }

    public State getActualState() {
        return actualState;
    }

    public void setActualState(State actualState) {
        this.actualState = actualState;
    }


    public enum State{
        IN_PROGRESS(Color.BLUE),
        DONE(Color.GREEN),
        PLANNED(Color.YELLOW);

        private int color;

        State(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }
    }

}


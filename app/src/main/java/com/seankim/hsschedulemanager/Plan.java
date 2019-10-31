package com.seankim.hsschedulemanager;
public class Plan {
    private String subject;
    private String startTime;
    private String endTime;
    private String state;

    public Plan(String subject, String startTime, String endTime, String state) {
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;

    }

    public String getSubject() {
        return subject;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getState() {
        return state;
    }
}

package com.varsity_management.model;

import java.io.Serializable;

public class SubjectName implements Serializable {
    private String subCodeName;
    private double credit;

    public SubjectName() {
    }

    public SubjectName(String subCodeName,double credit) {
        this.subCodeName = subCodeName;
        this.credit = credit;
    }

    public String getSubCodeName() {
        return subCodeName;
    }

    public void setSubCodeName(String subCodeName) {
        this.subCodeName = subCodeName;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "SubjectName{" +
                "subCodeName='" + subCodeName + '\'' +
                "credit='" + credit + '\'' +
                '}';
    }
}

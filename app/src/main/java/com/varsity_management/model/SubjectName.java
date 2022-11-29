package com.varsity_management.model;

import java.io.Serializable;

public class SubjectName implements Serializable {
    private String subCodeName;

    public SubjectName() {
    }

    public SubjectName(String subCodeName) {
        this.subCodeName = subCodeName;
    }

    public String getSubCodeName() {
        return subCodeName;
    }

    public void setSubCodeName(String subCodeName) {
        this.subCodeName = subCodeName;
    }

    @Override
    public String toString() {
        return "SubjectName{" +
                "subCodeName='" + subCodeName + '\'' +
                '}';
    }
}

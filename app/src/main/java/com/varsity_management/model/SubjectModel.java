package com.varsity_management.model;

import java.io.Serializable;
import java.util.List;

public class SubjectModel implements Serializable {
    private String semester;
    private List<SubjectName> subjectNameList;

    public SubjectModel() {
    }

    public SubjectModel(String semester, List<SubjectName> subjectNameList) {
        this.semester = semester;
        this.subjectNameList = subjectNameList;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<SubjectName> getSubjectNameList() {
        return subjectNameList;
    }

    public void setSubjectNameList(List<SubjectName> subjectNameList) {
        this.subjectNameList = subjectNameList;
    }

    @Override
    public String toString() {
        return "SubjectModel{" +
                "semester='" + semester + '\'' +
                ", subjectNameList=" + subjectNameList +
                '}';
    }
}

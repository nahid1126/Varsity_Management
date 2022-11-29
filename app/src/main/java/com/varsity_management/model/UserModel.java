package com.varsity_management.model;

public class UserModel {
    private String name;
    private String id;
    private String mail;
    private String dept;
    private String prof;
    private String pass;

    public UserModel() {
    }

    public UserModel(String name, String id, String mail, String dept, String prof, String pass) {
        this.name = name;
        this.id = id;
        this.mail = mail;
        this.dept = dept;
        this.prof = prof;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", mail='" + mail + '\'' +
                ", dept='" + dept + '\'' +
                ", prof='" + prof + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}

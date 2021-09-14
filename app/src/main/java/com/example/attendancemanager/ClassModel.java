package com.example.attendancemanager;

public class ClassModel {
    String classname;
    String subjectname;
    int priority;


    public ClassModel(String classname, String subjectname, int priority) {
        this.classname = classname;
        this.subjectname = subjectname;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }
}

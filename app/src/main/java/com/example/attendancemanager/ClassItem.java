package com.example.attendancemanager;

public class ClassItem {
    String classname;
    String subjectname;

    public ClassItem(String classname, String subjectname) {
        this.classname = classname;
        this.subjectname = subjectname;
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

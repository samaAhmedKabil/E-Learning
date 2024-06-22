package com.example.elearning;

public class ModelStudentQuizAnswer{
    private String studentId ;
    private String studentEmail ;
    private int studentGrade ;
    private String studentName ;
    public ModelStudentQuizAnswer(){}

    public ModelStudentQuizAnswer(String studentId, String studentEmail, int studentGrade, String studentName) {
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.studentGrade = studentGrade;
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public int getStudentGrade() {
        return studentGrade;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}

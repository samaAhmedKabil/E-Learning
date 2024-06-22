package com.example.elearning;

public class ModelMember {
    private String courseId ;
    private String courseName ;
    private String studentId ;
    private int attendanceGrade ;
    private int quizGrade ;

    public ModelMember(){}

    public ModelMember(String courseId, String courseName, String studentId, int attendanceGrade, int quizGrade) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.studentId = studentId;
        this.attendanceGrade = attendanceGrade;
        this.quizGrade = quizGrade;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getAttendanceGrade() {
        return attendanceGrade;
    }

    public int getQuizGrade() {
        return quizGrade;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setAttendanceGrade(int attendanceGrade) {
        this.attendanceGrade = attendanceGrade;
    }

    public void setQuizGrade(int quizGrade) {
        this.quizGrade = quizGrade;
    }
}

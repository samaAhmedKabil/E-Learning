package com.example.elearning;

public class ModelCourse {
    private String courseId ;
    private String courseName ;
    private String instructorId ;
    private String projectGrades ;
    private String quizGrades ;
    private String attendGrades ;
    public ModelCourse(){}

    public ModelCourse(String courseId, String courseName, String instructorId, String projectGrades, String quizGrades, String attendGrades) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructorId = instructorId;
        this.projectGrades = projectGrades;
        this.quizGrades = quizGrades;
        this.attendGrades = attendGrades;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public String getProjectGrades() {
        return projectGrades;
    }

    public String getquizGrades() {
        return quizGrades;
    }

    public String getAttendGrades() {
        return attendGrades;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public void setProjectGrades(String projectGrades) {
        this.projectGrades = projectGrades;
    }

    public void setquizGrades(String quizGrades) {
        this.quizGrades = quizGrades;
    }

    public void setAttendGrades(String attendGrades) {
        this.attendGrades = attendGrades;
    }
}

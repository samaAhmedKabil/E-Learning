package com.example.elearning;

public class ModelGrade {
    private int quizGrade ;
    private int attendGrade ;
    public ModelGrade(){}

    public ModelGrade(int quizGrade, int attendGrade) {
        this.quizGrade = quizGrade;
        this.attendGrade = attendGrade;
    }

    public int getQuizGrade() {
        return quizGrade;
    }

    public int getAttendGrade() {
        return attendGrade;
    }

    public void setQuizGrade(int quizGrade) {
        this.quizGrade = quizGrade;
    }

    public void setAttendGrade(int attendGrade) {
        this.attendGrade = attendGrade;
    }
}

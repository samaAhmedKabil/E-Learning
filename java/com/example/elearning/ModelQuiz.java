package com.example.elearning;

public class ModelQuiz {
    private String question ;
    private int rightAnswer ;
    private String Answer1 ;
    private String Answer2 ;
    private String Answer3 ;
    private String Answer4 ;

    public ModelQuiz (){}
    public ModelQuiz(String question, int rightAnswer, String answer1, String answer2, String answer3, String answer4) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        Answer1 = answer1;
        Answer2 = answer2;
        Answer3 = answer3;
        Answer4 = answer4;
    }

    public String getQuestion() {
        return question;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public String getAnswer4() {
        return Answer4;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setAnswer1(String answer1) {
        Answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        Answer2 = answer2;
    }

    public void setAnswer3(String answer3) {
        Answer3 = answer3;
    }

    public void setAnswer4(String answer4) {
        Answer4 = answer4;
    }
}

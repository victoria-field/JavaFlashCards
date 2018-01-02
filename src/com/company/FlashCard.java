package com.company;

public class FlashCard {


    private String question;
    private String answer;

    public FlashCard(String quest, String ans ){
        question = quest;
        answer = ans;
    }

//    Setters and getters

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}

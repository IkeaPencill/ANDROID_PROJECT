package iut.s4.project;

import java.util.ArrayList;

public class Question {

    String question;
    int reponse;
    ArrayList<String> reponses= new ArrayList<>();


    public Question(){}
    public Question(String q,int pos,ArrayList<String> rep){
        this.question=q;
        this.reponse=pos;
        this.reponses=rep;
    }

    public String getAnswer(){return this.reponses.get(this.reponse);}

    public String getQuestion() {return question;}


}

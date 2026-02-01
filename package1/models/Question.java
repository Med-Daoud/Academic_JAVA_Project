package projet_java.package1.models;

import java.util.*;

public class Question implements QuestionInterface {
    private String ennonce;
    private List<String> choix;
    private int reponse = 0;

    public Question(String ennonce, int reponse) {
        this.ennonce = ennonce;
        this.choix = new ArrayList<>();
        this.reponse = reponse;
    }

   public Question(String ennonce, List<String> choix) {
    this.ennonce = ennonce;
    this.choix = choix;
}


    @Override
    public String getEnnonce() {
        return ennonce;
    }

    @Override
    public void setEnnonce(String ennonce) {
        this.ennonce = ennonce;
    }

    @Override
    public List<String> getChoix() {
        return choix;
    }

    @Override
    public void setChoix(List<String> choix) {
        this.choix = choix;
    }

    @Override
    public int getReponse() {
        return reponse;
    }

    @Override
    public void setReponse(int reponse) {
        this.reponse = reponse;
    }

        public int getReponseCorrecte() {
        return reponse;
    }
private String intitule; // assure-toi que tu as un champ pour le texte


   public String getIntitule() {
    return intitule;
}


    
}

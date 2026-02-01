package projet_java.package1.services;

import projet_java.package1.models.Question;
import java.util.List;

public class QuestionService {

    private Question question;

    public QuestionService(Question question) {
        this.question = question;
    }

       public void setEnnonce(String ennonce) {
        question.setEnnonce(ennonce);
    }

    public void ajouterChoix(String choix) {
        question.getChoix().add(choix);}
 
    
    public boolean supprimerChoix(int index) {
        List<String> choix = question.getChoix();

        if (index >= 0 && index < choix.size()) {
            choix.remove(index);
            return true;
        }
        System.out.println("Index du choix invalide !");
        return false;
    }

    
    
    public boolean modifierChoix(int index, String nouveauChoix) {
        List<String> choix = question.getChoix();

        if (index >= 0 && index < choix.size()) {
            choix.set(index, nouveauChoix);
            return true;
        }
        System.out.println("Index du choix invalide !");
        return false;
    }

 
    public boolean setReponseCorrecte(int index) {
        if (index >= 0 && index < question.getChoix().size()) {
            question.setReponse(index);
            return true;
        }
        System.out.println("Index invalide pour la réponse !");
        return false;
    }

    
  
    public String getEnnonce() {
        return question.getEnnonce();
    }

    public List<String> getChoix() {
        return question.getChoix();
    }

    public int getReponseCorrecte() {
        return question.getReponse();
    }

    public void afficherQuestion() {
        System.out.println("Question : " + question.getEnnonce());

        List<String> choix = question.getChoix();
        for (int i = 0; i < choix.size(); i++) {
            System.out.println((i + 1) + ". " + choix.get(i));
        }

        System.out.println("Réponse correcte : choix n°" + (question.getReponse() + 1));
    }
}

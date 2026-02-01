package projet_java.package1.services;
import java.io.*;
import projet_java.package1.models.Question;
import java.util.ArrayList;
import java.util.List;

public class QuestionnaireService {
    private static QuestionnaireService instance;
    private ArrayList<Question> questions;
    private final String FILE_PATH = "data/questions.txt"; 

    private QuestionnaireService() {
        questions = new ArrayList<>();
         chargerQuestions(); 
        if (questions.isEmpty()) {
           
            sauvegarderQuestions(); 
        }
    }

        

    public static QuestionnaireService getInstance() {
        if (instance == null) {
            instance = new QuestionnaireService();
        }
        return instance;
    }
    
   public void sauvegarderQuestions() {
    try {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs(); 
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
             System.out.println("fichier cree");
            for (Question q : questions) {
                String line = q.getEnnonce() + "|" + q.getReponseCorrecte() + "|" + String.join(";", q.getChoix());
                writer.write(line);
                writer.newLine();
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    public void chargerQuestions() {
        questions.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String ennonce = parts[0];
                    int rep = Integer.parseInt(parts[1]);
                    String[] choixArray = parts[2].split(";");
                    Question q = new Question(ennonce, rep);
                    q.setChoix(new ArrayList<>(List.of(choixArray)));
                    questions.add(q);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    
    public void ajouterQuestion(Question q) {
        questions.add(q);
         sauvegarderQuestions();
        System.out.println("Question ajoutée: " + q.getEnnonce());
    }
    
    public List<Question> getToutesLesQuestions() {
        return new ArrayList<>(questions);
    }
    
    public Question getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }
    
    public int getNombreQuestions() {
        return questions.size();
    }
    
    public void supprimerQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            questions.remove(index);
            sauvegarderQuestions();
        }
    }
    
    public void modifierQuestion(int index, Question nouvelleQuestion) {
        if (index >= 0 && index < questions.size()) {
            questions.set(index, nouvelleQuestion);
            sauvegarderQuestions();
        }
    }

  public void afficherQuestionnaire() {
    if (questions.isEmpty()) {
        System.out.println("Le questionnaire est vide !");
    } else {
        System.out.println("===== AFFICHAGE DU QUESTIONNAIRE =====");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ". " + questions.get(i).getIntitule());
        }
    }
}

}
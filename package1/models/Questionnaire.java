
package projet_java.package1.models;



import java.util.*;

public class Questionnaire {

    private List<Question> listQuestions;

   
    public Questionnaire() {
        this.listQuestions = new ArrayList<>();
    }

    
    public Questionnaire(List<Question> listQuestions) {
        this.listQuestions = listQuestions;
    }

   
    public List<Question> getListQuestions() {
        return listQuestions;
    }

    
    public void setListQuestions(List<Question> listQuestions) {
        this.listQuestions = listQuestions;
    }

 
    public void addQuestion(Question q) {
        this.listQuestions.add(q);
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "listQuestions=" + listQuestions +
                '}';
    }
}


package projet_java.package1.models;
import java.util.*;

public interface QuestionInterface {
    String getEnnonce();
    void setEnnonce(String ennonce);

    List<String> getChoix();
    void setChoix(List<String> choix);

    int getReponse();
    void setReponse(int reponse);

   
}

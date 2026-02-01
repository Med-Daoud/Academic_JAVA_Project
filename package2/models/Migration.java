
package projet_java.package2.models;

import java.util.*;
public class Migration {
    private List<Pays> paysList;
    private List<Professionnel> ProfessionnelsList;

    public Migration() {
        this.paysList = new ArrayList<>();
        this.ProfessionnelsList = new ArrayList<>();
    }
    public Migration(List<Pays> paysList) {
        this.paysList = new ArrayList<>(paysList);
        this.ProfessionnelsList = new ArrayList<>(ProfessionnelsList);
    }
    public List<Pays> getPaysList() {
        return Collections.unmodifiableList(paysList);
    }
    public void setPaysList(List<Pays> paysList) {
        this.paysList = new ArrayList<>(paysList);
    }
     public List<Professionnel> getProfessionnelsList() {
        return Collections.unmodifiableList(ProfessionnelsList);
    }
     public void setProfessionnelsList(List<Professionnel> ProfessionnelsList) {
        this.ProfessionnelsList = new ArrayList<>(ProfessionnelsList);
    }
    @Override
    public String toString() {
        return "les liste des pays :" + paysList + "et les liste des professionnels  :" + ProfessionnelsList;
    }
}

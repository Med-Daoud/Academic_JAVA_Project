
package projet_java.package2.models;

import java.time.LocalDate;
public class Profession implements ProfessionInterface {
    private String nom;
    private LocalDate dateDeProfession;
    private String specialite;
    private int anneeEtude;

    public Profession(String nom, LocalDate dateDeProfession, String specialite, int anneeEtude) {
        this.nom = nom;
        this.dateDeProfession = dateDeProfession;
        this.specialite = specialite;
        this.anneeEtude = anneeEtude;
    }

    @Override public String getNom(){ return nom; }
    @Override public void setNom(String nom){ this.nom = nom; }

    @Override public LocalDate getDateDeProfession(){ return dateDeProfession; }
    @Override public void setDateDeProfession(LocalDate date){ this.dateDeProfession = date; }

    @Override public String getSpecialite(){ return specialite; }
    @Override public void setSpecialite(String specialite){ this.specialite = specialite; }

    @Override public int getAnneeEtude(){ return anneeEtude; }
    @Override public void setAnneeEtude(int annee){ this.anneeEtude = annee; }

    @Override
    public String toString() {
        return String.format("Profession[nom=%s, date=%s, specialite=%s, annee=%d]",
            nom, dateDeProfession, specialite, anneeEtude);
    }
}

package projet_java.package2.models;


public interface ProfessionInterface {
     String getNom();
    void setNom(String nom);

    java.time.LocalDate getDateDeProfession();
    void setDateDeProfession(java.time.LocalDate date);

    String getSpecialite();
    void setSpecialite(String specialite);

    int getAnneeEtude();
    void setAnneeEtude(int annee);
}


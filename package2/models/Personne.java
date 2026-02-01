
package projet_java.package2.models;

import java.time.LocalDate;
public sealed class Personne permits Professionnel {
    private String id;          
    private String nom;
    private String prenom;
    private String adresse;
    private String numTel;
    private LocalDate dateNaissance;
    private Pays pays;           
    

    public Personne(String id, String nom, String prenom, String adresse, String numTel, LocalDate dateNaissance, Pays pays) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.numTel = numTel;
        this.dateNaissance = dateNaissance;
        this.pays = pays;
        
    }

    public String getId() {
        return id;}
    public void setId(String id) {
        this.id = id;}
    public String getNom() {
        return nom;}
    public void setNom(String nom) {
        this.nom = nom;}
    public String getPrenom() {
        return prenom;}
    public void setPrenom(String prenom) {
        this.prenom = prenom;}
    public String getAdresse() {
        return adresse;}
    public void setAdresse(String adresse) {
        this.adresse = adresse;}
    public String getNumTel() {
        return numTel;}
    public void setNumTel(String numTel) {
        this.numTel = numTel;}
    public LocalDate getDateNaissance() {
        return dateNaissance;}
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;}
    public Pays getPays() {
        return pays;}
    public void setPays(Pays pays) {
        this.pays = pays;}
   

   @Override
public String toString() {
    String paysName = (pays != null) ? pays.name() : "N/A";
    return "Personne: " + nom + " " + prenom +
           "\nID: " + id +
           "\nAdresse: " + adresse +
           "\nTéléphone: " + numTel +
           "\nDate Naissance: " + dateNaissance +
           "\nPays: " + paysName;
}

}

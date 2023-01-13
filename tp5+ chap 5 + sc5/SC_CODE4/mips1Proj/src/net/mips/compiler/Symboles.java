package net.mips.compiler;

public class Symboles {

    Tokens token;
    String nom;
    int adresse;
    ClasseIdf classe;


    public Symboles(Tokens t, String s) {
        this.token = t;
        this.nom = s;
    }

    public Symboles(Tokens t, String s, ClasseIdf c) {
        this.token = t;
        this.nom = s;
        this.classe = c;

    }

    public int getAdresse() {
        return adresse;
    }

    public void setAdresse(int adresse) {
        this.adresse = adresse;
    }

    public ClasseIdf getClasse() {
        return classe;
    }

    public Tokens get_token() {
        return this.token;
    }

    public String get_nom() {
        return this.nom;
    }

    public void set_symbole(Tokens t, String nom) {
        this.token = t;
        this.nom = nom;
    }


}

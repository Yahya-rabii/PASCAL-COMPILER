package net.mips.compiler;

public class Symboles {

    Tokens token;
    String nom;


    public Symboles(Tokens t, String s) {
        this.token = t;
        this.nom = s;

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

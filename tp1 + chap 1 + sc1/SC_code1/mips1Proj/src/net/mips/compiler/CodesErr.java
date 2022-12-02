package net.mips.compiler;

public enum CodesErr {

    CAR_INC_ERR("Symbole inconnu"),
    FIC_VID_ERR("Erreur d'ouverture de fichier");

    private String message;

    CodesErr(String s) {

        message = s;
    }

    public String get_errmessage() {
        return this.message;
    }

    public void set_errmessage(String msg) {
        this.message = msg;
    }

}

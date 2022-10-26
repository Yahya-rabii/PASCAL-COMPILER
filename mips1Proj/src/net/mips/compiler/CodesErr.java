package net.mips.compiler;

public enum CodesErr {

    CAR_INC_ERR("Symbole inconnu"),
    FIC_VID_ERR("Erreur d'ouverture de fichier"),
    PROGRAMM_ERR("mot cles programme attendu ! "),
    CONST_ERR("mot cles const attendu ! "),

    VAR_ERR("mot cles var attendu !"),

    ID_ERR("identificateur attendu !"),
    PVIR_ERR("symbole ; attendu !"),

    AFEC_ERR("symbole := attendu !"),
    PNT_ERR("symbole . attendu !"),

    EG_ERR("symbole = attendu !"),
    NUM_ERR("Numero attendu !"),
    WHILE_ERR("mot cles while attendu !"),
    WRITE_ERR("mot cles write attendu !"),
    IF_ERR("mot cles if attendu !"),

    READ_ERR("mot read attendu !"),
    PARG_ERR("symbole ( attendu !"),
    PARD_ERR("symbole ) attendu !");

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

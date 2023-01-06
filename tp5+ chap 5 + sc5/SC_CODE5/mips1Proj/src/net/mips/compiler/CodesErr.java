package net.mips.compiler;

public enum CodesErr {

    CAR_INC_ERR("Symbole inconnu"), FIC_VID_ERR("Erreur d'ouverture de fichier"), PROGRAMM_ERR("mot cles programme attendu ! "),
    CONST_ERR("mot cles const attendu ! "), THEN_ERR("mot cles then attendu !"), BEGIN_ERR("mot cles begin attendu !"),
    VAR_ERR("mot cles var attendu !"), ID_ERR("identificateur attendu !"), PVIR_ERR("symbole ; attendu !"),
    AFEC_ERR("symbole := attendu !"), PNT_ERR("symbole . attendu !"), SUP_ERR("symbole > attendu !"),
    SUPEG_ERR("symbole >= attendu !"), DIFF_ERR("symbole != attendu !"), INF_ERR("symbole < attendu !"),
    INFEG_ERR("symbole <= attendu !"), EG_ERR("symbole = attendu !"), NUM_ERR("Numero attendu !"),
    WHILE_ERR("mot cles while attendu !"), WRITE_ERR("mot cles write attendu !"), IF_ERR("mot cles if attendu !"),
    READ_ERR("mot read attendu !"), PARG_ERR("symbole ( attendu !"), PARD_ERR("symbole ) attendu !"),
    VIR_ERR("symbole , attendu !"), DIV_ERR("symbole / attendu !"), MUL_ERR("symbole * attendu !"),
    MOIN_ERR("symbole - attendu !"), PLUS_ERR("symbole + attendu !"), END_ERR("mot cles end attendu !"), DO_ERR("mot cles do attendu !"),

    ID_pas_declarer ("ID_pas_declarer"),
    ID_PROGRAMME_pres_def("ID programme deja utilise"),
    ID_CONST_err("vous pouvez pas chager une constante"),
    ID_DEJA_DEFINI("ID deja defini");
    private String message;

    CodesErr(String s) {message = s;}

    public String get_errmessage() {
        return this.message;
    }

    public void set_errmessage(String msg) {
        this.message = msg;
    }

}

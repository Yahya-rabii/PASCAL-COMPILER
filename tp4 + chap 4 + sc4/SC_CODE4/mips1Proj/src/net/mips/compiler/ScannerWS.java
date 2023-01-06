package net.mips.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScannerWS extends Scanner {
    private final ArrayList<Symboles> tablesymb;
    private int placesymb;
    private int offset;

    public ScannerWS(String nomf) throws IOException {
        super(nomf);
        this.tablesymb = new ArrayList<Symboles>();
        this.offset = -1;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<Symboles> getTablesymb() {
        return tablesymb;
    }

    public int getPlacesymb() {
        return placesymb;
    }

    public void initMotsCles() {
        super.initMotsCles();
    }

    public Tokens codagelex(String mot) throws IOException {

        return super.codagelex(mot);
    }

    public void entrersymb(ClasseIdf c) {

        this.tablesymb.add(new Symboles(this.get_symbCour().get_token() , this.get_symbCour().get_nom() , c));
        if (c.equals(ClasseIdf.CONSTANT) || c.equals(ClasseIdf.VARIABLE)) {
            this.offset = this.getOffset() + 1;
            this.tablesymb.get(this.tablesymb.size()-1).adresse = this.getOffset();
        }
    }

    public void cherchersymb() {
        for (int i = 0; i < this.tablesymb.size(); i++) {
            if (this.tablesymb.get(i).get_token().equals(this.get_symbCour().get_token()) && this.tablesymb.get(i).get_nom().equals(this.get_symbCour().get_nom())) {
                this.placesymb = i;
                return;
            }
        }
        this.placesymb = -1;
    }


}

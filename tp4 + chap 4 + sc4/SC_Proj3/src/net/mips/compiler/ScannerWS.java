package net.mips.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScannerWS extends Scanner {
    private final ArrayList<Symboles> tablesymb;
    private int placesymb;

    public ScannerWS(String nomf) throws IOException {
        super(nomf);
        this.tablesymb = new ArrayList<Symboles>();
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
        this.tablesymb.add(new Symboles(this.symbCour.get_token(), this.symbCour.get_nom(), c));
    }

    public void cherchersymb() {
        for (int i = 0; i < this.tablesymb.size(); i++) {
            if (this.tablesymb.get(i).get_token().equals(this.get_symbCour().get_token()) && this.tablesymb.get(i).get_nom().equals(this.get_symbCour().get_nom())) {
                this.placesymb = i;
                return;
            }
        }this.placesymb = -1;
    }


}

package net.mips.compiler;

public class ParserWS extends Parser {


    public ParserWS(String nomf) throws Exception {
        super(nomf);
        this.scanner =new ScannerWS(nomf);
    }


    public void testeInsere(Tokens token, ClasseIdf classe, CodesErr err) throws Exception {
        if (token == this.get_scanner().get_symbCour().get_token()) {
            ((ScannerWS)this.get_scanner()).cherchersymb();
            if ((((ScannerWS) this.get_scanner()).getPlacesymb()) != -1)
                throw new ErreurSementique(CodesErr.ID_DEJA_DEFINI);  // regle 3
            ((ScannerWS) this.get_scanner()).entrersymb(classe);
            this.get_scanner().symbsuiv();
        } else throw new ErreurSementique(err);
    }

    public void testCherche(Tokens t, CodesErr c) throws Exception {
        if (this.get_scanner().get_symbCour().get_token() == t) {
            ((ScannerWS) this.get_scanner()).cherchersymb();
            if ((((ScannerWS) this.get_scanner()).getPlacesymb()) == -1)
                throw new ErreurSementique(CodesErr.ID_pas_declarer);  // regle 3
            if ((((ScannerWS) this.get_scanner()).getTablesymb().get(((ScannerWS) this.get_scanner()).getPlacesymb())).getClasse().equals(ClasseIdf.PROGRAMME))
                throw new ErreurSementique(CodesErr.ID_PROGRAMME_pres_def);// regle 5
            this.get_scanner().symbsuiv();
        } else throw new ErreurSementique(c);
    }


    public void program() throws Exception {
        testAccept(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAMM_ERR);
        testeInsere(Tokens.ID_TOKEN, ClasseIdf.PROGRAMME, CodesErr.ID_ERR);
        testAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        block();
        testAccept(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);
    }


    public void consts() throws Exception {
        if (this.get_scanner().get_symbCour().get_token() == Tokens.CONST_TOKEN) {
            testAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
            while (this.get_scanner().get_symbCour().get_token() == Tokens.ID_TOKEN) {
                testeInsere(Tokens.ID_TOKEN, ClasseIdf.CONSTANT, CodesErr.ID_ERR);
                testAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
                testAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
                testAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
            }
        }
    }

    public void vars() throws Exception {
        if (this.get_scanner().get_symbCour().get_token() == Tokens.VAR_TOKEN) {

            boolean x = false;
            testAccept(Tokens.VAR_TOKEN, CodesErr.VAR_ERR);
            do {
                testeInsere(Tokens.ID_TOKEN, ClasseIdf.VARIABLE, CodesErr.ID_ERR);
                if (this.get_scanner().get_symbCour().get_token() == Tokens.VIR_TOKEN) {
                    testAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
                    x = true;
                } else {
                    x = false;
                }
            } while (x);
            testAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        }
    }


    public void affec() throws Exception {
        testCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        if ((((ScannerWS) this.get_scanner()).getTablesymb().get(((ScannerWS) this.get_scanner()).getPlacesymb())).getClasse().equals(ClasseIdf.CONSTANT))
            throw new ErreurSementique(CodesErr.ID_CONST_err); // regle 4
        testAccept(Tokens.AFFEC_TOKEN, CodesErr.AFEC_ERR);
        expr();
    }


    public void lire() throws Exception {
        testAccept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
        testAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        testCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        if ((((ScannerWS) this.get_scanner()).getTablesymb().get(((ScannerWS) this.get_scanner()).getPlacesymb())).getClasse().equals(ClasseIdf.CONSTANT))
            throw new ErreurSementique(CodesErr.ID_CONST_err); // regle 4

        while (this.get_scanner().carCour == ',') {
            testAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
            testCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            if ((((ScannerWS) this.get_scanner()).getTablesymb().get(((ScannerWS) this.get_scanner()).getPlacesymb())).getClasse().equals(ClasseIdf.CONSTANT))
                throw new ErreurSementique(CodesErr.ID_CONST_err); // regle 4
        }
        testAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
    }


    public void fact() throws Exception {
        switch (this.get_scanner().get_symbCour().get_token()) {
            case ID_TOKEN -> {
                testCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            }
            case NUM_TOKEN -> {
                testAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
            }
            case PARG_TOKEN -> {
                testAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
                expr();
                testAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
            }
            default -> {
                throw new ErreurSyntaxique(CodesErr.CAR_INC_ERR);
            }
        }
    }

}



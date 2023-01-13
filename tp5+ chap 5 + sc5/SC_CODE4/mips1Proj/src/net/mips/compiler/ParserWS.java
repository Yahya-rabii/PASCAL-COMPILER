package net.mips.compiler;

import net.mips.interpreter.Instruction;
import net.mips.interpreter.Mnemonique;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class ParserWS extends Parser {

    private ArrayList<Instruction> pcode;
    private PrintWriter fluxCible;

    public ArrayList<Instruction> getPcode() {
        return pcode;
    }

    public void setPcode(ArrayList<Instruction> pcode) {
        this.pcode = pcode;
    }

    public PrintWriter getFluxCible() {
        return fluxCible;
    }

    public void setFluxCible(PrintWriter fluxCible) {
        this.fluxCible = fluxCible;
    }

    public ParserWS(String nomf, OutputStream out) throws Exception {
        super(nomf);
        this.pcode = new ArrayList<>();
        this.scanner = new ScannerWS(nomf);

        this.fluxCible = new PrintWriter(out, true);//true for flush

    }


    public void savePcode() {
        Mnemonique[] val = {Mnemonique.INT, Mnemonique.LDA, Mnemonique.LDI};
        for (Instruction i : this.pcode) {
            this.fluxCible.println(Arrays.asList(val).contains(i.getMne()) ? (i.getMne() + " " + i.getSuite()) : i.getMne());
        }

    }

    public void generer1(Mnemonique i) {
        this.getPcode().add(new Instruction(i));
    }


    public void generer2(Mnemonique i, int n) {
        this.getPcode().add(new Instruction(i, n));
    }

    public void testeInsere(Tokens token, ClasseIdf classe, CodesErr err) throws Exception {
        if (token == this.get_scanner().get_symbCour().get_token()) {
            ((ScannerWS) this.get_scanner()).cherchersymb();
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
        this.generer1(Mnemonique.HLT);
        testAccept(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);
    }

    public void consts() throws Exception {
        if (this.get_scanner().get_symbCour().get_token() == Tokens.CONST_TOKEN) {
            testAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
            while (this.get_scanner().get_symbCour().get_token() == Tokens.ID_TOKEN) {
                testeInsere(Tokens.ID_TOKEN, ClasseIdf.CONSTANT, CodesErr.ID_ERR);
                generer2(Mnemonique.LDA, ((ScannerWS) this.get_scanner()).getOffset());
                testAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
                generer2(Mnemonique.LDI, Integer.parseInt(this.get_scanner().get_symbCour().get_nom()));//add some upgrades
                testAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
                generer1(Mnemonique.STO);
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
                generer2(Mnemonique.LDA, ((ScannerWS) this.get_scanner()).getOffset());

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

    public void block() throws Exception {
        ((ScannerWS) this.get_scanner()).setOffset(-1);
        consts();
        vars();//reservation de la zone memoire
        this.pcode.add(0, new Instruction(Mnemonique.INT, ((ScannerWS) this.get_scanner()).getOffset())); // in order to add int on the first element of an array
        insts();
    }


    public void affec() throws Exception {
        testCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        generer2(Mnemonique.LDA, ((ScannerWS) this.get_scanner()).getTablesymb().get(((ScannerWS) this.get_scanner()).getPlacesymb()).getAdresse());

        if ((((ScannerWS) this.get_scanner()).getTablesymb().get(((ScannerWS) this.get_scanner()).getPlacesymb())).getClasse().equals(ClasseIdf.CONSTANT))
            throw new ErreurSementique(CodesErr.ID_CONST_err); // regle 4
        testAccept(Tokens.AFFEC_TOKEN, CodesErr.AFEC_ERR);

        this.expr();
        generer1(Mnemonique.STO);
    }

    public void expr() throws Exception {
        term();
        while (this.scanner.get_symbCour().get_token() == Tokens.PLUS_TOKEN || this.scanner.get_symbCour().get_token() == Tokens.MOINS_TOKEN) {
            var it = this.get_scanner().get_symbCour().token;
            addop();
            term();
            generer1(it == Tokens.PLUS_TOKEN ? Mnemonique.ADD : Mnemonique.SUB);
        }
    }

    public void term() throws Exception {
        fact();
        while (this.scanner.get_symbCour().get_token() == Tokens.MUL_TOKEN || this.scanner.get_symbCour().get_token() == Tokens.DIV_TOKEN) {

            var it = this.get_scanner().get_symbCour().token;
            mulop();
            fact();
            generer1(it == Tokens.MUL_TOKEN ? Mnemonique.MUL : Mnemonique.DIV);
        }
    }

    public void fact() throws Exception {
        switch (this.get_scanner().get_symbCour().get_token()) {
            case ID_TOKEN -> {
                testCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
                generer2(Mnemonique.LDA, ((ScannerWS) this.get_scanner()).getTablesymb().get(((ScannerWS) this.get_scanner()).getPlacesymb()).getAdresse());
                generer1(Mnemonique.LDV);

            }
            case NUM_TOKEN -> {
                generer2(Mnemonique.LDI, Integer.parseInt(this.get_scanner().get_symbCour().get_nom()));
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

    public void ecrire() throws Exception {
        testAccept(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
        testAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        expr();
        generer1(Mnemonique.PRN);
        while (this.scanner.carCour == ',') {
            testAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
            expr();
            generer1(Mnemonique.PRN);

        }
        testAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
    }

    public void lire() throws Exception {
        testAccept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
        testAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        testCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        if ((((ScannerWS) this.get_scanner()).getTablesymb().get(((ScannerWS) this.get_scanner()).getPlacesymb())).getClasse().equals(ClasseIdf.CONSTANT))
            throw new ErreurSementique(CodesErr.ID_CONST_err); // regle 4

        generer2(Mnemonique.LDA, ((ScannerWS) this.get_scanner()).getTablesymb().get(((ScannerWS) this.get_scanner()).getPlacesymb()).getAdresse());
        generer1(Mnemonique.INN);
        while (this.get_scanner().carCour == ',') {
            testAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
            testCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            if ((((ScannerWS) this.get_scanner()).getTablesymb().get(((ScannerWS) this.get_scanner()).getPlacesymb())).getClasse().equals(ClasseIdf.CONSTANT))
                throw new ErreurSementique(CodesErr.ID_CONST_err); // regle 4
            generer2(Mnemonique.LDA, ((ScannerWS) this.get_scanner()).getTablesymb().get(((ScannerWS) this.get_scanner()).getPlacesymb()).getAdresse());
            generer1(Mnemonique.STO);
        }

        testAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
    }


}



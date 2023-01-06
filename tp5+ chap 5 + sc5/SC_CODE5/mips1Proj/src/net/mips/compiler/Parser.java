package net.mips.compiler;

import static net.mips.compiler.Scanner.EOF;

public class Parser {
    Scanner scanner;

    public Parser(String nomf) throws Exception {
        this.scanner = new Scanner(nomf);
    }

    public Scanner get_scanner() {
        return this.scanner;
    }

    public void set_scanner(Scanner sc) {
        this.scanner = sc;
    }

    public void testAccept(Tokens t, CodesErr c) throws Exception {
        if (this.scanner.get_symbCour().get_token() == t) {
            if (this.scanner.get_carCour() != EOF) this.scanner.symbsuiv();
        } else {
            throw new ErreurSyntaxique(c);
        }
    }

    public void program() throws Exception {
        testAccept(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAMM_ERR);
        testAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        block();
        testAccept(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);
    }


    public void block() throws Exception {
        consts();
        vars();
        insts();
    }


    public void consts() throws Exception {
        testAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
        while (this.scanner.symbCour.token == Tokens.ID_TOKEN) {
            testAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            testAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
            testAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
            testAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        }
    }


    public void vars() throws Exception {
        boolean x = false;
        testAccept(Tokens.VAR_TOKEN, CodesErr.VAR_ERR);
        do {
            testAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            if (this.scanner.symbCour.token == Tokens.VIR_TOKEN) {
                testAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
                x = true;
            } else {
                x = false;
            }
        } while (x);
        testAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }


    public void affec() throws Exception {
        testAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testAccept(Tokens.AFFEC_TOKEN, CodesErr.AFEC_ERR);
        expr();
    }


    public void si() throws Exception {
        testAccept(Tokens.IF_TOKEN, CodesErr.IF_ERR);
        this.cond();
        testAccept(Tokens.THEN_TOKEN, CodesErr.THEN_ERR);
        this.inst();
    }


    public void ecrire() throws Exception {
        testAccept(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
        testAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        expr();
        while (this.scanner.carCour == ',') {
            testAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
            expr();
        }
        testAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
    }

    public void lire() throws Exception {
        testAccept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
        testAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        testAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        while (this.scanner.carCour == ',') {
            testAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
            testAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        }
        testAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
    }


    public void tantque() throws Exception {
        testAccept(Tokens.WHILE_TOKEN, CodesErr.WHILE_ERR);
        this.cond();
        testAccept(Tokens.DO_TOKEN, CodesErr.DO_ERR);
        this.inst();
    }

    public void cond() throws Exception {
        expr();
        relop();
        expr();
    }

    private void relop() throws Exception {
        switch (this.scanner.get_symbCour().get_token()) {
            case SUP_TOKEN -> {
                testAccept(Tokens.SUP_TOKEN, CodesErr.SUP_ERR);
            }
            case SUPEG_TOKEN -> {
                testAccept(Tokens.SUPEG_TOKEN, CodesErr.SUPEG_ERR);
            }
            case INF_TOKEN -> {
                testAccept(Tokens.INF_TOKEN, CodesErr.INF_ERR);
            }
            case INFEG_TOKEN -> {
                testAccept(Tokens.INFEG_TOKEN, CodesErr.INFEG_ERR);
            }
            case EG_TOKEN -> {
                this.scanner.lireCar();
                testAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
            }
            case DIFF_TOKEN -> {
                this.scanner.lireCar();
                testAccept(Tokens.DIFF_TOKEN, CodesErr.DIFF_ERR);
            }
        }
    }


    public void insts() throws Exception {
        testAccept(Tokens.BEGIN_TOKEN, CodesErr.BEGIN_ERR);
        inst();
        while (this.scanner.get_symbCour().get_token() != Tokens.END_TOKEN) {
            testAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
            inst();
        }
        testAccept(Tokens.END_TOKEN, CodesErr.END_ERR);
    }

    public void inst() throws Exception {
        switch (this.scanner.symbCour.token) {
            case BEGIN_TOKEN -> {
                insts();
            }
            case ID_TOKEN -> {
                affec();
            }
            case IF_TOKEN -> {
                si();
            }
            case WHILE_TOKEN -> {
                tantque();
            }
            case WRITE_TOKEN -> {
                ecrire();
            }
            case READ_TOKEN -> {
                lire();
            }
        }
    }

    public void expr() throws Exception {
        term();
        while (this.scanner.get_symbCour().get_token() == Tokens.PLUS_TOKEN || this.scanner.get_symbCour().get_token() == Tokens.MOINS_TOKEN) {
            addop();
            term();
        }
    }

    public void term() throws Exception {
        fact();
        while (this.scanner.get_symbCour().get_token() == Tokens.MUL_TOKEN || this.scanner.get_symbCour().get_token() == Tokens.DIV_TOKEN) {
            mulop();
            fact();
        }
    }

    public void addop() throws Exception {
        switch (this.scanner.get_symbCour().get_token()) {
            case PLUS_TOKEN -> {
                testAccept(Tokens.PLUS_TOKEN, CodesErr.PLUS_ERR);
            }
            case MOINS_TOKEN -> {
                testAccept(Tokens.MOINS_TOKEN, CodesErr.MOIN_ERR);
            }
        }
    }

    public void fact() throws Exception {
        switch (this.scanner.get_symbCour().get_token()) {
            case ID_TOKEN -> {
                testAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            }
            case NUM_TOKEN -> {
                testAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
            }
            case PARG_TOKEN -> {
                testAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
                expr();
                testAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
            }
        }
    }

    public void mulop() throws Exception {
        switch (this.scanner.get_symbCour().get_token()) {
            case MUL_TOKEN -> {
                testAccept(Tokens.MUL_TOKEN, CodesErr.MUL_ERR);
            }
            case DIV_TOKEN -> {
                testAccept(Tokens.DIV_TOKEN, CodesErr.DIV_ERR);
            }
        }
    }
}



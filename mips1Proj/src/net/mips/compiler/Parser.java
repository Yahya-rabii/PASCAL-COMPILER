package net.mips.compiler;

import java.io.IOException;

public class Parser {

    Scanner scanner;

    public Parser(String nomf) throws IOException {


        this.scanner = new Scanner(nomf);
    }

    public Scanner get_scanner() {

        return this.scanner;

    }

    public void set_scanner(Scanner sc) {

        this.scanner = sc;

    }

    public void testeAccept(Tokens t, CodesErr c) throws Exception {

        System.out.println(this.scanner.symbCour.token +" : " + String.valueOf(this.scanner.symbCour.nom));

        if (this.scanner.symbCour.get_token() == t) {
            this.scanner.symbsuiv();
        } else {
            throw new ErreurSyntaxique(c);
        }

    }

    public void program() throws Exception {

        testeAccept(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAMM_ERR);
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        block();
        testeAccept(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);


    }


    public void block() throws Exception {


        consts();
        vars();
        insts();


    }


    public void consts() throws Exception {

        testeAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);

        if (this.scanner.symbCour.token == Tokens.PVIR_TOKEN) {
            testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);

        } else if (this.scanner.symbCour.token == Tokens.AFFEC_TOKEN) {

            testeAccept(Tokens.AFFEC_TOKEN, CodesErr.AFEC_ERR);

            if (this.scanner.symbCour.token == Tokens.ID_TOKEN) {
                testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
                testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);


            } else if (this.scanner.symbCour.token == Tokens.VAR_TOKEN) {

                testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
                testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);

            }


        }

    }


    public void vars() throws Exception {

        testeAccept(Tokens.VAR_TOKEN, CodesErr.VAR_ERR);
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);

        if (this.scanner.symbCour.token == Tokens.PVIR_TOKEN) {
            testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);

        } else if (this.scanner.symbCour.token == Tokens.AFFEC_TOKEN) {

            testeAccept(Tokens.AFFEC_TOKEN, CodesErr.AFEC_ERR);

            if (this.scanner.symbCour.token == Tokens.ID_TOKEN) {
                testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);

            } else if (this.scanner.symbCour.token == Tokens.VAR_TOKEN) {

                testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
            }


        }

    }


    public void affec() throws Exception {

        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testeAccept(Tokens.AFFEC_TOKEN, CodesErr.AFEC_ERR);

        if (this.scanner.symbCour.token == Tokens.ID_TOKEN) {
            testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);

        } else if (this.scanner.symbCour.token == Tokens.VAR_TOKEN) {

            testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
        }

    }


    public  void si() throws Exception {

        testeAccept(Tokens.IF_TOKEN, CodesErr.IF_ERR);
        testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);



    }


    public void ecrire() throws Exception {
        testeAccept(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
        testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        vars();
        testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }

    public void lire() throws Exception {
        testeAccept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
        testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        vars();
        testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }


    public void insts() {


    }

    public void inst() {


    }

    public void tantque() {


    }

    public void cond() {


    }

    public void expr() {


    }

    public void term() {


    }

    public void fact() {


    }

}



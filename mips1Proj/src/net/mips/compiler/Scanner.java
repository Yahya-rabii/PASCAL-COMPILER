package net.mips.compiler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Scanner {

    static public final char EOF = '\0';
    ArrayList<Symboles> motCles;
    Symboles symbCour;
    char carCour;
    FileReader fluxSour;

    public Scanner(String nomf) throws IOException {

        File file = new File(nomf);

        if (!file.exists()) {

            throw new ErreurLexicale(CodesErr.FIC_VID_ERR);

        } else {

            this.fluxSour = new FileReader(file);
            this.motCles = new ArrayList<>();
            this.symbCour = new Symboles(this.codagelex(String.valueOf(this.get_carCour())), String.valueOf(this.get_carCour()));
            System.out.println(this.symbCour.token);

        }

    }


    public ArrayList<Symboles> get_motcles() {
        return motCles;
    }

    public Symboles get_symbCour() {
        return this.symbCour;
    }

    public char get_carCour() {
        return this.carCour;
    }

    public FileReader get_fluxSour() {
        return this.fluxSour;
    }


    public void set_Scannervals(ArrayList<Symboles> mc, Symboles sc, char cc, FileReader fs) {

        this.motCles = mc;
        this.symbCour = sc;
        this.carCour = cc;
        this.fluxSour = fs;

    }


    public void initMotsCles() {


        this.motCles.add(new Symboles(Tokens.PROGRAM_TOKEN, "program"));
        this.motCles.add(new Symboles(Tokens.CONST_TOKEN, "const"));
        this.motCles.add(new Symboles(Tokens.VAR_TOKEN, "var"));
        this.motCles.add(new Symboles(Tokens.BEGIN_TOKEN, "begin"));
        this.motCles.add(new Symboles(Tokens.END_TOKEN, "end"));
        this.motCles.add(new Symboles(Tokens.IF_TOKEN, "if"));
        this.motCles.add(new Symboles(Tokens.THEN_TOKEN, "then"));
        this.motCles.add(new Symboles(Tokens.WHILE_TOKEN, "while"));
        this.motCles.add(new Symboles(Tokens.DO_TOKEN, "do"));
        this.motCles.add(new Symboles(Tokens.WRITE_TOKEN, "write"));
        this.motCles.add(new Symboles(Tokens.READ_TOKEN, "read"));

    }


    public Tokens codagelex(String mot) throws IOException {


        for (int i = 0; i < this.motCles.size(); i++) {

            if (this.motCles.get(i).nom.equals(mot)) {

                return this.motCles.get(i).token;

            }

        }

        return Tokens.ID_TOKEN;
    }


    public void lireCar() throws IOException {


        if (this.fluxSour.ready()) {

            this.carCour = (char) this.fluxSour.read();

        } else {
            this.carCour = EOF;
        }


    }


    public void lireNombre() throws Exception {

        StringBuilder s = new StringBuilder();


        while (Character.isDigit(this.carCour) && this.carCour != ' ') {

            s.append(this.carCour);
            lireCar();


        }


        this.symbCour = new Symboles(Tokens.NUM_TOKEN, s.toString());


    }


    public void liremot() throws Exception {


        StringBuilder s = new StringBuilder();


        while (Character.isLetter(this.carCour) || Character.isDigit(this.carCour) && this.carCour != ' ') {

            s.append(this.carCour);
            lireCar();


        }

        this.symbCour = new Symboles(codagelex(s.toString()), s.toString());


    }

    public void symbsuiv() throws Exception {

        boolean x = false;

        while (this.carCour == ' ' || this.carCour == '\n' || this.carCour == '\t' || this.carCour == '\r' || this.carCour == '\\') {

            lireCar();

        }

        if (Character.isDigit(this.carCour)) {

            lireNombre();

        } else if (Character.isLetter(this.carCour)) {

            liremot();

        } else {

            switch (this.carCour) {
                case '+' -> {

                    this.symbCour = new Symboles(Tokens.PLUS_TOKEN, Character.toString(this.carCour));

                }
                case '-' -> {

                    this.symbCour = new Symboles(Tokens.MOINS_TOKEN, Character.toString(this.carCour));


                }
                case '*' -> {
                    this.symbCour = new Symboles(Tokens.MUL_TOKEN, Character.toString(this.carCour));

                }
                case '/' -> {
                    this.symbCour = new Symboles(Tokens.DIV_TOKEN, Character.toString(this.carCour));

                }
                case '=' -> {
                    this.symbCour = new Symboles(Tokens.EG_TOKEN, Character.toString(this.carCour));


                }
                case '(' -> {
                    this.symbCour = new Symboles(Tokens.PARG_TOKEN, Character.toString(this.carCour));

                }
                case ')' -> {
                    this.symbCour = new Symboles(Tokens.PARD_TOKEN, Character.toString(this.carCour));

                }
                case ',' -> {
                    this.symbCour = new Symboles(Tokens.VIR_TOKEN, Character.toString(this.carCour));

                }
                case ';' -> {
                    this.symbCour = new Symboles(Tokens.PVIR_TOKEN, Character.toString(this.carCour));


                }
                case '.' -> {

                    this.symbCour = new Symboles(Tokens.PNT_TOKEN, Character.toString(this.carCour));

                }


                case ':' -> {
                    lireCar();
                    if (this.carCour == '=') {
                        x = true;
                        this.symbCour = new Symboles(Tokens.AFFEC_TOKEN, ":=");

                    }
                }
                case '!' -> {
                    lireCar();
                    if (this.carCour == '=') {
                        x = true;
                        this.symbCour = new Symboles(Tokens.DIFF_TOKEN, "!=");

                    }
                }
                case '<' -> {
                    lireCar();
                    if (this.carCour == '=') {
                        x = true;
                        this.symbCour = new Symboles(Tokens.INFEG_TOKEN, "<=");


                    } else {
                        this.symbCour = new Symboles(Tokens.INF_TOKEN, "<");

                    }
                }
                case '>' -> {
                    lireCar();
                    if (this.carCour == '=') {
                        x = true;

                        this.symbCour = new Symboles(Tokens.SUPEG_TOKEN, ">=");


                    } else {
                        this.symbCour = new Symboles(Tokens.SUP_TOKEN, ">");

                    }
                }


                default -> {
                    this.symbCour = new Symboles(Tokens.ERR_TOKEN, CodesErr.CAR_INC_ERR.get_errmessage());

                }


            }
            if (!x) lireCar();


        }


    }


}






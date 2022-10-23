import net.mips.compiler.Scanner;

import java.nio.file.Path;


public class Main {
    public static void main(String[] args) throws Exception {

        var progfile = Path.of(System.getProperty("user.dir"), "prog.mips1");

        var sc = new Scanner(progfile.toString());

        sc.initMotsCles();

        sc.lireCar();

        while (sc.get_carCour() != '\0') {
            sc.symbsuiv();
            System.out.println(sc.get_symbCour().get_token().toString() + " <===> " + sc.get_symbCour().get_nom());

        }
    }
}
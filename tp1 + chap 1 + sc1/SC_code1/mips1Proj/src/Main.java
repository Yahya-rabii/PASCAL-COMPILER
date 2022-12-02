import net.mips.compiler.Scanner;

import java.nio.file.Path;

import static net.mips.compiler.Scanner.EOF;


public class Main {
    public static void main(String[] args) throws Exception {

        var progfile = Path.of(System.getProperty("user.dir"), "pascalprog.txt");

        var sc = new Scanner(progfile.toString());


        sc.initMotsCles();

        sc.lireCar();

        while (sc.get_carCour() != EOF) {
            sc.symbsuiv();
            System.out.println("{    " + sc.get_symbCour().get_token().toString() + " ===> "  + sc.get_symbCour().get_nom() + "    }");

        }
    }
}
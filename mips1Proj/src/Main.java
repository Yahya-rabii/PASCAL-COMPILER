import net.mips.compiler.Parser;
import net.mips.compiler.ParserWS;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        var progfile = Path.of(System.getProperty("user.dir"), "pascalprog.txt");

        ParserWS parser = new ParserWS(progfile.toString());

        parser.get_scanner().initMotsCles();
        parser.get_scanner().lireCar();
        parser.get_scanner().symbsuiv();

        parser.program();

    }
}
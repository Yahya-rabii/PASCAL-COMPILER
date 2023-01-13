import net.mips.compiler.ParserWS;
import net.mips.interpreter.ParserPcode;

import java.io.FileOutputStream;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        var progfile = Path.of(System.getProperty("user.dir"), "pascalprog.txt");
        var resfile = Path.of(System.getProperty("user.dir"), "res.txt");

        ParserWS parser = new ParserWS(progfile.toString(), new FileOutputStream(resfile.toString()));
        parser.get_scanner().initMotsCles();
        parser.get_scanner().lireCar();
        parser.get_scanner().symbsuiv();
        parser.program();
        parser.savePcode();
        ParserPcode pcodeParser = new ParserPcode(resfile.toString());
        pcodeParser.getScanner().lireCar();
        pcodeParser.getScanner().symbsuiv();
        pcodeParser.pcode();
        pcodeParser.getInterpreterPcode().INTER_PCODE();
    }
}
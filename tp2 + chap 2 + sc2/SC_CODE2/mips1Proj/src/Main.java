import net.mips.compiler.CodesErr;
import net.mips.compiler.Parser;
import net.mips.compiler.Tokens;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        var progfile = Path.of(System.getProperty("user.dir"), "pascalprog.txt");
        var parser = new Parser(progfile.toString());
        parser.get_scanner().initMotsCles();
        parser.get_scanner().lireCar();
        parser.get_scanner().symbsuiv();
        parser.program();

    }
}





/*
void pour(){

    testAccept(Tokens.fortoken, CodesErr.for_ERR);
    testAccept(Tokens.idtoken, CodesErr.id_ERR);
    testAccept(Tokens.inrangetoken, CodesErr.inrange_ERR);
    testAccept(Tokens.par_g_token, CodesErr.par_g_ERR);
    ///lirenombre(); /// testAccept(Tokens.num_token, CodesErr.num_ERR);
    testAccept(Tokens.virg_token, CodesErr.virg_ERR);
    ///lirenombre(); /// testAccept(Tokens.num_token, CodesErr.num_ERR);
    testAccept(Tokens.par_d_token, CodesErr.par_d_ERR);
    testAccept(Tokens.do_token, CodesErr.do_ERR);
    inst();

}



void si(){

    testAccept(Tokens.iftoken, CodesErr.if_ERR);
    cond();
    testAccept(Tokens.deuxptoken, CodesErr.deuxp_ERR);
    inst();
    if(this.scanner.symbCour.token == Tokens.elif_TOKEN){
        testAccept(Tokens.eliftoken, CodesErr.elif_ERR);
        cond();
        testAccept(Tokens.deuxptoken, CodesErr.deuxp_ERR);
        inst();
    }
    if(this.scanner.symbCour.token == Tokens.else_TOKEN){
        testAccept(Tokens.elsetoken, CodesErr.else_ERR);
        testAccept(Tokens.deuxptoken, CodesErr.deuxp_ERR);
        inst();
    }

}






*/

















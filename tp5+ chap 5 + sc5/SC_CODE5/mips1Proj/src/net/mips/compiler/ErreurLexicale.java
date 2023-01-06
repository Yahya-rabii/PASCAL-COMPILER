package net.mips.compiler;

public class ErreurLexicale extends ErreurCompilation {
    public ErreurLexicale( CodesErr c) {super(c.get_errmessage());}

}

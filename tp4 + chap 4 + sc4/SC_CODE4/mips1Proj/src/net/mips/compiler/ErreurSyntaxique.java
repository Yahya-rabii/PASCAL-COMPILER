package net.mips.compiler;

public class ErreurSyntaxique extends ErreurCompilation {
    public ErreurSyntaxique(CodesErr c) {super(c.get_errmessage());}

}

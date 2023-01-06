package net.mips.compiler;

public class ErreurSementique extends ErreurCompilation {
    public ErreurSementique( CodesErr c) {super(c.get_errmessage());}

}

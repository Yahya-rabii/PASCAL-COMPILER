package net.mips.compiler;
import org.jetbrains.annotations.NotNull;

public class ErreurLexicale extends ErreurCompilation {
    public ErreurLexicale(@NotNull CodesErr c) {super(c.get_errmessage());}

}

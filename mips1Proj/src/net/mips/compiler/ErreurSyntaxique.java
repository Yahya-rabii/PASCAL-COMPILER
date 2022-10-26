package net.mips.compiler;

import org.jetbrains.annotations.NotNull;

public class ErreurSyntaxique extends ErreurCompilation {

    public ErreurSyntaxique(@NotNull CodesErr c) {

        super(c.get_errmessage());

    }


}

package net.mips.compiler;
import java.io.IOException;

public class ErreurCompilation extends IOException {
    ErreurCompilation(String m) {
        super(m);
    }

}

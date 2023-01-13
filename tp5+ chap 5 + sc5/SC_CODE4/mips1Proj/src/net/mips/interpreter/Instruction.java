package net.mips.interpreter;

public class Instruction {
    Mnemonique mne;
    int suite;


    public Mnemonique getMne() {
        return mne;
    }

    public void setMne(Mnemonique mne) {
        this.mne = mne;
    }

    public int getSuite() {
        return suite;
    }

    public void setSuite(int suite) {
        this.suite = suite;
    }

    public Instruction() {
        this.mne = null;
        this.suite = 0;
    }

    public Instruction(Mnemonique m) {
        this.mne = m;
        this.suite =Integer.MIN_VALUE;
    }

    public Instruction(Mnemonique mne, int suite) {
        this.mne = mne;
        this.suite = suite;
    }
}

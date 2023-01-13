package net.mips.interpreter;

import net.mips.compiler.ErreurSementique;

import java.util.Scanner;

public class InterpreterPcode {
    private Instruction[] pcode;
    private int[] pile;
    private int pc, offset, sp;

    public InterpreterPcode() {
        this.pcode = new Instruction[50];
        this.pile = new int[50];
        this.pc = this.offset = this.sp = -1;
    }


    public void loadMnemonic(Mnemonique mne) throws ErreurSementique {
        if (this.pc == this.pcode.length) {
            throw new Error(String.valueOf(mne));
        } else {
            pc++;
            this.pcode[this.pc] = new Instruction(mne);
        }
    }

    public void loadMnemonic(Mnemonique mne, int suite) throws ErreurSementique {
        if (this.pc == this.pcode.length) {
            throw new Error(String.valueOf(mne));
        } else {
            pc++;
            this.pcode[this.pc] = new Instruction(mne, suite);
        }
    }

    public void INTER_INST(Instruction instruction) {
        int val1, val2, adr;
        switch (instruction.mne) {
            case INT -> {
                this.offset = this.sp = instruction.suite;
                this.pc++;
            }
            case LDI, LDA -> {
                this.pile[++sp] = instruction.getSuite();
                this.pc++;
            }
            case STO -> {
                val1 = this.pile[this.sp--];
                adr = this.pile[this.sp--];
                this.pile[adr] = val1;
                this.pc++;
            }
            case LDV -> {
                adr = this.pile[this.sp--];
                this.pile[++sp] = this.pile[adr];
                this.pc++;
            }
            case EQL -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];

                if (val1 == val2) this.pile[++sp] = 1;
                else this.pile[++sp] = 0;
                this.pc++;
            }
            case NEQ -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];

                if (val1 != val2) this.pile[++sp] = 1;
                else this.pile[++sp] = 0;
                this.pc++;
            }
            case GTR -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                if (val1 > val2) this.pile[++sp] = 1;
                else this.pile[++sp] = 0;
                this.pc++;
            }
            case LSS -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                if (val1 < val2) this.pile[++sp] = 1;
                else this.pile[++sp] = 0;
                this.pc++;
            }
            case GEQ -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                if (val1 >= val2) this.pile[++sp] = 1;
                else this.pile[++sp] = 0;
                this.pc++;
            }
            case LEQ -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                if (val1 <= val2) this.pile[++sp] = 1;
                else this.pile[++sp] = 0;
                this.pc++;
            }
            case ADD -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 + val2);
                this.pc++;


                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                if (val1 > val2) this.pile[++sp] = 1;
                else this.pile[++sp] = 0;
                this.pc++;
            }
            case SUB -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val2 - val1);
                this.pc++;
            }
            case MUL -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 * val2);
                this.pc++;
            }
            case DIV -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 / val2);
                this.pc++;
            }
            case PRN -> {
                val1 = this.pile[this.sp--];
                System.out.println(val1);
                this.pc++;
            }
            case INN -> {
                var sc = new Scanner(System.in);
                adr = this.pile[sp--];
                System.out.print("Entrer une valeur : ");
                pile[adr] = sc.nextInt();
                this.pc++;
            }
            case BZE -> {
                if (pile[sp--] == 0) pc = instruction.suite;
                else pc++;
            }
            case BRN -> this.pc = instruction.suite;

        }


    }

    public void INTER_PCODE() {
        this.pc = 0;
        while (pcode[pc] != null && pcode[pc].getMne() != Mnemonique.HLT)
            INTER_INST(this.pcode[pc]);
    }


}

public class ALU
{

    public static void halt()
    {
        SystemControl.PrintToDebugConsole("Executing Instruction 'Halt'");

        SystemControl.Running = false;
        SystemControl.Idle = true;

        SystemControl.PrintToDebugConsole("Executed Instruction 'Halt'");
    }


    public static void LDR(int GPR_X, int IXR_X, int EA_X)
    {
        SystemControl.PrintToDebugConsole("Executed Instruction 'LDR'");

        Registers.GPRS[GPR_X] = Memory.get_from_memory(Utils.calculateEffectiveAddress());

        SystemControl.PrintToDebugConsole(String.format("GPR: %d\n  EA: %H", Registers.GPRS[GPR_X], EA_X));

    }


    public static void str()
    {
        SystemControl.PrintToDebugConsole("Executed Instruction 'STR'");

        Memory.store_to_memory(Utils.calculateEffectiveAddress(), Registers.GPRS[Utils.GPR_Index]);

        SystemControl.PrintToDebugConsole("  GPR: %d\n  EA: %H");
    }


    public static void lda()
    {
        SystemControl.PrintToDebugConsole("Executed Instruction 'STR'");

        Registers.GPRS[Utils.GPR_Index] = Utils.calculateEffectiveAddress();

        SystemControl.PrintToDebugConsole("  GPR: %d\n  EA: %H");
    }


    public static void ldx()
    {
        SystemControl.PrintToDebugConsole("Executed Instruction 'STR'");

        Registers.IXRS[Utils.IXR_Index] = Memory.get_from_memory(Utils.calculateEffectiveAddress_IXR());

        SystemControl.PrintToDebugConsole("  GPR: %d\n  EA: %H");
    }


    public static void stx()
    {
        SystemControl.PrintToDebugConsole("Executed Instruction 'STR'");

        Memory.store_to_memory(Utils.calculateEffectiveAddress_IXR(), Registers.IXRS[Utils.IXR_Index]);

        SystemControl.PrintToDebugConsole("  GPR: %d\n  EA: %H");
    }


    public static void jz()
    {
        if (Registers.GPRS[Utils.GPR_Index] == 0) {
            Registers.PC = Utils.calculateEffectiveAddress();
            SystemControl.PrintToDebugConsole(String.format("Running JZ\n  GPR %d is zero, jump to %H"));
            return;
        }
        SystemControl.PrintToDebugConsole(String.format("Running JZ\n  GPR %d is not zero, do not jump"));
    }


    public static void jne() {

        if (Registers.GPRS[Utils.GPR_Index] != 0) {
            Registers.PC = Utils.calculateEffectiveAddress();
            SystemControl.PrintToDebugConsole(String.format("Running JNE\n  GPR %d is not zero, jump to %H"));
            return;
        }
        SystemControl.PrintToDebugConsole(String.format("Running JNE\n  GPR %d is zero, do not jump"));
    }


    public static void jcc() {
        boolean checkflag = false;

        switch (Registers.GPRS[Utils.GPR_Index]) {
            case 0:
                checkflag = Registers.CC1;
                break;
            case 1:
                checkflag = Registers.CC2;
                break;
            case 2:
                checkflag = Registers.CC3;
                break;
            case 3:
                checkflag = Registers.CC4;
                break;
        }
        if (checkflag) {
            Registers.PC = Utils.calculateEffectiveAddress();
//            SystemControl.PrintToDebugConsole(String.format("Running JCC\n  CC: %d equals to required CC: %d jump to %H", SystemControl.cc, Utils.GPR_Index, ea));
            return;
        }
//        SystemControl.PrintToDebugConsole(String.format("Running JCC\n  CC: %d does not equal to required CC: %d", SystemControl.cc, Utils.GPR_Index));
    }



    public static void jma() {

        Registers.PC = Utils.calculateEffectiveAddress();

        SystemControl.PrintToDebugConsole(String.format("Running JMA\n  Jump to %H"));
    }


    public static void jsr() {
        Registers.GPRS[3] = Registers.PC;
        Registers.PC = Utils.calculateEffectiveAddress();

//        SystemControl.PrintToDebugConsole(String.format("Running JSR\n  Jump to %H, current Args at %H", ea, SystemControl.GPRS[0]));
    }


    public static void jgt() {

        if (Registers.GPRS[Utils.Rx] > Registers.GPRS[Utils.Ry]) {
            Registers.PC = Utils.calculateEffectiveAddress();
//            SystemControl.PrintToDebugConsole(String.format("Running JGT\n  Jump to %H", ea));
            return;
        }

//        SystemControl.PrintToDebugConsole(String.format("Running JGT\n  do not jump to %H", ea));
    }


    public static void rfs() {
        Registers.PC = Registers.GPRS[3];
        Registers.GPRS[0] = Utils.Address;

//        SystemControl.PrintToDebugConsole(String.format("Running RFS\n  Return to %H, return value at %H", SystemControl.GPRS[3], SystemControl.GPRS[0]));
    }



    public static void sob() {
        SystemControl.PrintToDebugConsole("Running SOB");

        Registers.GPRS[Utils.GPR_Index]--;
        if (Registers.GPRS[Utils.GPR_Index] < 0) {
            Registers.GPRS[Utils.GPR_Index] = (int) (Math.pow(2, 16) - 1);
            Registers.CC2 = true;
            SystemControl.PrintToDebugConsole("  Underflow!");
        }

        if (Registers.GPRS[Utils.GPR_Index] > 0) {
            Registers.PC = Utils.calculateEffectiveAddress();
//            SystemControl.PrintToDebugConsole(String.format("  GPR%d: %d, jump to %H", i.gprIndex, backEnd.gpr[i.gprIndex], ea));
            return;
        }
//        SystemControl.PrintToDebugConsole(String.format("  GPR%d: %d, not jump", i.gprIndex, backEnd.gpr[i.gprIndex]));
    }



    public static void jge() {

        if (Registers.GPRS[Utils.GPR_Index] >= 0) {
            Registers.PC = Utils.calculateEffectiveAddress();
//            SystemControl.PrintToDebugConsole(String.format("Running JGE\n  GRP%d: %d, jump to %H", i.gprIndex, backEnd.gpr[i.gprIndex], ea));
            return;
        }
//        SystemControl.PrintToDebugConsole(String.format("Running JGE\n  GRP%d: %d, not jump", i.gprIndex, backEnd.gpr[i.gprIndex]));
    }


    public static void amr() {
        SystemControl.PrintToDebugConsole("Running AMR");


        Registers.GPRS[Utils.GPR_Index] += Memory.get_from_memory(Utils.calculateEffectiveAddress());
        if (Registers.GPRS[Utils.GPR_Index] > 65535) {
            Registers.GPRS[Utils.GPR_Index] -= 65536;
            Registers.CC1 = true;
            SystemControl.PrintToDebugConsole("  Overflow!");
        }

//        SystemControl.PrintToDebugConsole(String.format("  Add %d at %H to GPR%d, result is %d", backEnd.memory[ea], ea, i.gprIndex, backEnd.gpr[i.gprIndex]));
    }

    public static void smr() {
        SystemControl.PrintToDebugConsole("Running SMR");

        Registers.GPRS[Utils.GPR_Index] -= Memory.get_from_memory(Utils.calculateEffectiveAddress());
        if (Registers.GPRS[Utils.GPR_Index] < 0) {
            Registers.GPRS[Utils.GPR_Index] += 65536;
            Registers.CC2 = true;
            SystemControl.PrintToDebugConsole("  Underflow!");
        }

//        SystemControl.PrintToDebugConsole(String.format("  Sub %d at %H from GPR%d, result is %d", backEnd.memory[ea], ea, i.gprIndex, backEnd.gpr[i.gprIndex]));
    }

    public static void air() {
        SystemControl.PrintToDebugConsole("Running AIR");

        Registers.GPRS[Utils.GPR_Index] += Utils.calculateEffectiveAddress();

        if (Registers.GPRS[Utils.GPR_Index] > 65535) {
            Registers.GPRS[Utils.GPR_Index] -= 65536;
            Registers.CC1 = true;
            SystemControl.PrintToDebugConsole("  Overflow!");
        }

//        SystemControl.PrintToDebugConsole(String.format("  Add %d to GPR%d, result is %d", ea, i.gprIndex, backEnd.gpr[i.gprIndex]));
    }


    public static void sir() {
        SystemControl.PrintToDebugConsole("Running SIR");

        Registers.GPRS[Utils.GPR_Index] -= Utils.calculateEffectiveAddress();

        if (Registers.GPRS[Utils.GPR_Index] < 0) {
            Registers.GPRS[Utils.GPR_Index] += 65536;
            Registers.CC2 = true;
            SystemControl.PrintToDebugConsole("  Underflow!");
        }

//        SystemControl.PrintToDebugConsole(String.format("  Sub %d from GPR%d, result is %d", ea, i.gprIndex, backEnd.gpr[i.gprIndex]));
    }

    public static void mlt() {
        SystemControl.PrintToDebugConsole("Running MLT");

        /*
        if (!(i.rx == 0 | i.rx == 2)) {
            backEnd.debugPrint("  Invalid operands");
            return;
        }
        */

        long result = (long) Registers.GPRS[Utils.Rx] * Registers.GPRS[Utils.Ry];
        String resultStr = String.format("%32s", Long.toBinaryString(result)).replace(' ', '0');

        String hBits = resultStr.substring(0, 16);
        String lBits = resultStr.substring(16, 32);

        Registers.GPRS[Utils.Rx] = Integer.parseInt(hBits, 2);
        Registers.GPRS[Utils.Rx+1] = Integer.parseInt(lBits, 2);

//        SystemControl.PrintToDebugConsole(String.format("  MLT r%d with r%d, result is %d %s %s %s", i.rx, i.ry, result, resultStr, hBits, lBits));
    }

    public static void dvd() {
        SystemControl.PrintToDebugConsole("Running DVD");

        /*
        if (!(i.rx == 0 | i.rx == 2)) {
            backEnd.debugPrint("  Invalid operands");
            return;
        }
        */

        if (Registers.GPRS[Utils.Ry] == 0) {
            SystemControl.PrintToDebugConsole("  Divide by 0");
            Registers.CC3 = true;
            return;
        }

        int quotient = Registers.GPRS[Utils.Rx] / Registers.GPRS[Utils.Ry];
        int remainder = Registers.GPRS[Utils.Rx] % Registers.GPRS[Utils.Ry];

        Registers.GPRS[Utils.Rx] = quotient;
        Registers.GPRS[Utils.Rx + 1] = remainder;

//        SystemControl.PrintToDebugConsole(String.format(" DVD r%d with r%d, quotient is %d, remainder is %d", i.rx, i.ry, quotient, remainder));
    }

    public static void trr() {
        SystemControl.PrintToDebugConsole("Running TRR");

        if (Registers.GPRS[Utils.Rx] == Registers.GPRS[Utils.Rx]) {
            Registers.CC4 = true;
//            SystemControl.PrintToDebugConsole(String.format("  r%d equals to r%d", i.rx, i.ry));
            return;
        }

//        SystemControl.PrintToDebugConsole(String.format("  r%d does not equal to r%d", Utils.Rx, i.ry));
    }


    public static void and() {
        SystemControl.PrintToDebugConsole("Running AND");

        Registers.GPRS[Utils.Rx] &= Registers.GPRS[Utils.Ry];

//        SystemControl.PrintToDebugConsole(String.format("  r%d AND r%d", i.rx, i.ry));
    }

    public static void orr() {
        SystemControl.PrintToDebugConsole("Running ORR");

        Registers.GPRS[Utils.Rx] |= Registers.GPRS[Utils.Ry];

//        SystemControl.PrintToDebugConsole(String.format("  r%d ORR r%d", i.rx, i.ry));
    }


    public static void not() {
        SystemControl.PrintToDebugConsole("Running NOT");

        int result = ~Registers.GPRS[Utils.Rx];
        String resultStr = Integer.toBinaryString(result);
        resultStr = resultStr.substring(resultStr.length() - 16);

        Registers.GPRS[Utils.Rx] = Integer.parseInt(resultStr, 2);

//        SystemControl.PrintToDebugConsole(String.format("  NOT r%d", i.rx));
    }

    public static void src() {
        // TODO
    }

    public static void rrc() {
        // TODO
    }

    public static void in() {
        SystemControl.PrintToDebugConsole("Running IN");

        char c;

        if (Utils.DevID == 0) {
            c = IOGUI.popKBBuffer();
            Registers.GPRS[Utils.GPR_Index] = c;
//            SystemControl.PrintToDebugConsole(String.format("  Read %c from keyboard, store to gpr%d", c, i.gprIndex));
        } else if (Utils.DevID == 2) {
            c = IOGUI.popCRBuffer();
            Registers.GPRS[Utils.GPR_Index] = c;
//            SystemControl.PrintToDebugConsole(String.format("  Read %c from card reader, store to gpr%d", c, i.gprIndex));
        } else {
            SystemControl.PrintToDebugConsole("  Invalid operands");
        }
    }

    public static void out() {
        SystemControl.PrintToDebugConsole("Running OUT");

        int devID = Utils.Address;

        if (devID != 1) {
            SystemControl.PrintToDebugConsole("  Invalid operands");
            return;
        }

        if (Utils.I_bit == 1) {
            IOGUI.insertPrinterBuffer((char) Registers.GPRS[Utils.GPR_Index]);
        } else {
           IOGUI.pushPrinterBuffer((char) Registers.GPRS[Utils.GPR_Index]);
        }


//        SystemControl.PrintToDebugConsole(String.format("  Print %c to console printer", (char) backEnd.gpr[i.gprIndex]));
    }

    public static void chk() {
        SystemControl.PrintToDebugConsole("Running CHK");

        if (Utils.DevID == 0) {
            if (IOGUI.isKBBufferEmpty()) {
                Registers.GPRS[Utils.GPR_Index] = 0;
                SystemControl.PrintToDebugConsole("  No keyboard input to read");
            } else {
                Registers.GPRS[Utils.GPR_Index] = 1;
                SystemControl.PrintToDebugConsole("  There is keyboard input to read");
            }
        } else if (Utils.DevID == 2) {
            if (IOGUI.isCRBufferEmpty()) {
                Registers.GPRS[Utils.GPR_Index] = 0;
                SystemControl.PrintToDebugConsole("  No card reader input to read");
            } else {
                Registers.GPRS[Utils.GPR_Index] = 1;
                SystemControl.PrintToDebugConsole("  There is card reader input to read");
            }
        } else if (Utils.DevID == 1) {
            Registers.GPRS[Utils.GPR_Index] = 1;
            SystemControl.PrintToDebugConsole("  Console printer is enabled");
        } else {
            Registers.GPRS[Utils.GPR_Index] = 0;
        }
    }
}
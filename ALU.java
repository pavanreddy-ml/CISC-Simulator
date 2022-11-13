public class ALU
{

    public static void HALT()
    {
        SystemControl.PrintToDebugConsole("Executing Instruction 'Halt'");

        SystemControl.Running = false;
        SystemControl.Idle = true;

        SystemControl.PrintToDebugConsole("Executed Instruction 'Halt'");
    }


    public static void LDR(int GPR_X, int IXR_X, int EA_X)
    {
        SystemControl.PrintToDebugConsole("Executed Instruction 'LDR'");

        Registers.GPRS[GPR_X] = Memory.get_from_memory(EA_X);

        SystemControl.PrintToDebugConsole(String.format("GPR: %d\n  EA: %H", Registers.GPRS[GPR_X], EA_X));

    }


    public static void STR(int GPR_X, int IXR_X, int EA_X)
    {
        SystemControl.PrintToDebugConsole("Executed Instruction 'STR'");

        Memory.store_to_memory(EA_X, Registers.GPRS[GPR_X]);

        SystemControl.PrintToDebugConsole("  GPR: %d\n  EA: %H");
    }


    public static void LDA(int GPR_X, int IXR_X, int EA_X)
    {
        SystemControl.PrintToDebugConsole("Executed Instruction 'LDA'");

        Registers.GPRS[GPR_X] = EA_X;

        SystemControl.PrintToDebugConsole("  GPR: %d\n  EA: %H");
    }


    public static void LDX(int IXR_X, int IXEA_X)
    {
        SystemControl.PrintToDebugConsole("Executed Instruction 'LDX'");

        Registers.IXRS[IXR_X] = Memory.get_from_memory(Utils.calculateEffectiveAddress_IXR());

        SystemControl.PrintToDebugConsole("  GPR: %d\n  EA: %H");
    }


    public static void STX(int IXR_X, int IXEA_X)
    {
        SystemControl.PrintToDebugConsole("Executed Instruction 'STX'");

        Memory.store_to_memory(Utils.calculateEffectiveAddress_IXR(), Registers.IXRS[IXR_X]);

        SystemControl.PrintToDebugConsole("  GPR: %d\n  EA: %H");
    }


    public static void JZ(int GPR_X, int IXR_X, int EA_X)
    {
        if (Registers.GPRS[GPR_X] == 0) {
            Registers.PC = EA_X;
            SystemControl.PrintToDebugConsole(String.format("Executing instruction JZ\n  GPR %d is zero, jump to %H"));
            return;
        }
        SystemControl.PrintToDebugConsole(String.format("Executing instruction JZ\n  GPR %d is not zero, do not jump"));
    }


    public static void JNE(int GPR_X, int IXR_X, int EA_X) {

        if (Registers.GPRS[GPR_X] != 0) {
            Registers.PC = EA_X;
            SystemControl.PrintToDebugConsole(String.format("Executing instruction JNE\n  GPR %d is not zero, jump to %H"));
            return;
        }
        SystemControl.PrintToDebugConsole(String.format("Executing instruction JNE\n  GPR %d is zero, do not jump"));
    }


    public static void JCC(int GPR_X, int IXR_X, int EA_X) {
        boolean checkflag = false;

        switch (Registers.GPRS[GPR_X]) {
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
            Registers.PC = EA_X;
//           SystemControl.PrintToDebugConsole(String.format("Executing instruction JCC\n  CC: %d equals to required CC: %d jump to %H", SystemControl.cc, GPR_X, EA_X));
            return;
        }
//        SystemControl.PrintToDebugConsole(String.format("Executing instruction JCC\n  CC: %d does not equal to required CC: %d", SystemControl.cc, GPR_X));
    }



    public static void JMA(int IXR_X, int EA_X) {

        Registers.PC = EA_X;

        SystemControl.PrintToDebugConsole(String.format("Executing instruction JMA\n  Jump to %H"));
    }


    public static void JSR(int IXR_X, int EA_X) {
        Registers.GPRS[3] = Registers.PC;
        Registers.PC = EA_X;

//       SystemControl.PrintToDebugConsole(String.format("Executing instruction JSR\n  Jump to %H, current Args at %H", EA_X, SystemControl.GPRS[0]));
    }


    public static void jgt() {

        if (Registers.GPRS[Utils.Rx] > Registers.GPRS[Utils.Ry]) {
            Registers.PC = EA_X;
           SystemControl.PrintToDebugConsole(String.format("Executing instruction JGT\n  Jump to %H", EA_X));
            return;
        }

        SystemControl.PrintToDebugConsole(String.format("Executing instruction JGT\n  do not jump to %H", EA_X));
    }


    public static void RFS() {
        Registers.PC = Registers.GPRS[3];
        Registers.GPRS[0] = Utils.Address;

//        SystemControl.PrintToDebugConsole(String.format("Executing instruction RFS\n  Return to %H, return value at %H", SystemControl.GPRS[3], SystemControl.GPRS[0]));
    }



    public static void SOB(int GPR_X, int IXR_X, int EA_X) {
        SystemControl.PrintToDebugConsole("Executing instruction SOB");

        Registers.GPRS[GPR_X]--;
        if (Registers.GPRS[GPR_X] < 0) {
            Registers.GPRS[GPR_X] = (int) (Math.pow(2, 16) - 1);
            Registers.CC2 = true;
            SystemControl.PrintToDebugConsole("  Underflow!");
        }

        if (Registers.GPRS[GPR_X] > 0) {
            Registers.PC = EA_X;
            SystemControl.PrintToDebugConsole(String.format("  GPR%d: %d, jump to %H", GPR_X, Registers.GPRS[Utils.GPR_Index], EA_X));
            return;
        }
        SystemControl.PrintToDebugConsole(String.format("  GPR%d: %d, not jump", GPR_X, Registers.GPRS[Utils.GPR_Index]));
    }



    public static void JGE(int GPR_X, int IXR_X, int EA_X) {

        if (Registers.GPRS[GPR_X] >= 0) {
            Registers.PC = EA_X;
            SystemControl.PrintToDebugConsole(String.format("Executing instruction JGE\n  GRP%d: %d, jump to %H", GPR_X, Registers.GPRS[Utils.GPR_Index], EA_X));
            return;
        }
       SystemControl.PrintToDebugConsole(String.format("Executing instruction JGE\n  GRP%d: %d, not jump", GPR_X, Registers.GPRS[Utils.GPR_Index]));
    }


    public static void AMR(int GPR_X, int IXR_X, int EA_X) {
        SystemControl.PrintToDebugConsole("Executing instruction AMR");


        Registers.GPRS[GPR_X] += Memory.get_from_memory(EA_X);
        if (Registers.GPRS[GPR_X] > 65535) {
            Registers.GPRS[GPR_X] -= 65536;
            Registers.CC1 = true;
            SystemControl.PrintToDebugConsole("  Overflow!");
        }

//        SystemControl.PrintToDebugConsole(String.format("  Add %d at %H to GPR%d, result is %d", backEnd.memory[ea], EA_X, GPR_X, Registers.GPRS[Utils.GPR_Index]));
    }

    public static void SMR(int GPR_X, int IXR_X, int EA_X) {
        SystemControl.PrintToDebugConsole("Executing instruction SMR");

        Registers.GPRS[GPR_X] -= Memory.get_from_memory(EA_X);
        if (Registers.GPRS[GPR_X] < 0) {
            Registers.GPRS[GPR_X] += 65536;
            Registers.CC2 = true;
            SystemControl.PrintToDebugConsole("  Underflow!");
        }

//       SystemControl.PrintToDebugConsole(String.format("  Sub %d at %H from GPR%d, result is %d", Registers.memory[EA_X], EA_X, GPR_X, Registers.GPRS[Utils.GPR_Index]));
    }

    public static void AIR(int GPR_X, int Address) {
        SystemControl.PrintToDebugConsole("Executing instruction AIR");

        Registers.GPRS[GPR_X] += Address;

        if (Registers.GPRS[GPR_X] > 65535) {
            Registers.GPRS[GPR_X] -= 65536;
            Registers.CC1 = true;
            SystemControl.PrintToDebugConsole("  Overflow!");
        }

       SystemControl.PrintToDebugConsole(String.format("  Add %d to GPR%d, result is %d", Address, GPR_X, Registers.GPRS[Utils.GPR_Index]));
    }


    public static void SIR(int GPR_X, int Address) {
        SystemControl.PrintToDebugConsole("Executing instruction SIR");

        Registers.GPRS[GPR_X] -= Address;

        if (Registers.GPRS[GPR_X] < 0) {
            Registers.GPRS[GPR_X] += 65536;
            Registers.CC2 = true;
            SystemControl.PrintToDebugConsole("  Underflow!");
        }

      SystemControl.PrintToDebugConsole(String.format("  Sub %d from GPR%d, result is %d", Address, GPR_X, Registers.GPRS[Utils.GPR_Index]));
    }

    public static void MLT(int Rx_X,int Ry_X) {
        SystemControl.PrintToDebugConsole("Executing instruction MLT");

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

        SystemControl.PrintToDebugConsole(String.format("  MLT r%d with r%d, result is %d %s %s %s", Utils.Rx, Utils.Ry, result, resultStr, hBits, lBits));
    }

    public static void DVD(int Rx_X,int Ry_X) {
        SystemControl.PrintToDebugConsole("Executing instruction DVD");

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

        SystemControl.PrintToDebugConsole(String.format(" DVD r%d with r%d, quotient is %d, remainder is %d", Utils.Rx, Utils.Ry, quotient, remainder));
    }

    public static void TRR(int Rx_X,int Ry_X) {
        SystemControl.PrintToDebugConsole("Executing instruction TRR");

        if (Registers.GPRS[Utils.Rx] == Registers.GPRS[Utils.Rx]) {
            Registers.CC4 = true;
            SystemControl.PrintToDebugConsole(String.format("  r%d is equal to r%d", Utils.Rx, Utils.Ry));
            return;
        }

        SystemControl.PrintToDebugConsole(String.format("  r%d is not equal to r%d", Utils.Rx, Utils.Ry));
    }


    public static void AND(int Rx_X,int Ry_X) {
        SystemControl.PrintToDebugConsole("Executing instruction AND");

        Registers.GPRS[Utils.Rx] &= Registers.GPRS[Utils.Ry];

        SystemControl.PrintToDebugConsole(String.format("  r%d AND r%d", Utils.Rx, Utils.Ry));
    }

    public static void ORR(int Rx_X,int Ry_X) {
        SystemControl.PrintToDebugConsole("Executing instruction ORR");

        Registers.GPRS[Utils.Rx] |= Registers.GPRS[Utils.Ry];

       SystemControl.PrintToDebugConsole(String.format("  r%d ORR r%d", Utils.Rx, Utils.Ry));
    }


    public static void NOT(int Rx_X) {
        SystemControl.PrintToDebugConsole("Executing instruction NOT");

        int result = ~Registers.GPRS[Utils.Rx];
        String resultStr = Integer.toBinaryString(result);
        resultStr = resultStr.substring(resultStr.length() - 16);

        Registers.GPRS[Utils.Rx] = Integer.parseInt(resultStr, 2);

        SystemControl.PrintToDebugConsole(String.format("  NOT r%d", Utils.Rx));
    }

    public static void SRC(int GPR_X,int Count_X,int RL_X,int AL_X) {
        SystemControl.PrintToDebugConsole("Executing instruction SRC");
        // TODO
    }

    public static void RRC(int GPR_X,int Count_X,int RL_X,int AL_X) {
        SystemControl.PrintToDebugConsole("Executing instruction RRC");
        // TODO
    }

    public static void IN(int GPR_X,int DevID_X) {
        SystemControl.PrintToDebugConsole("Executing IN");

        char c;

        if (Utils.DevID == 0) {
            c = IOGUI.popKBBuffer();
            Registers.GPRS[GPR_X] = c;
            SystemControl.PrintToDebugConsole(String.format("  Read %c from keyboard, store to gpr%d", c, GPR_X));
        } else if (Utils.DevID == 2) {
            c = IOGUI.popCRBuffer();
            Registers.GPRS[GPR_X] = c;
            SystemControl.PrintToDebugConsole(String.format("  Read %c from card reader, store to gpr%d", c, GPR_X));
        } else {
            SystemControl.PrintToDebugConsole("  Invalid operands");
        }
    }

    public static void OUT(int GPR_X,int DevID_X) {
        SystemControl.PrintToDebugConsole("Executing OUT");

        int devID = Utils.Address;

        if (devID != 1) {
            SystemControl.PrintToDebugConsole("  Invalid operands");
            return;
        }

        if (Utils.I_bit == 1) {
            IOGUI.insertPrinterBuffer((char) Registers.GPRS[GPR_X]);
        } else {
           IOGUI.pushPrinterBuffer((char) Registers.GPRS[GPR_X]);
        }


    SystemControl.PrintToDebugConsole(String.format("  Print %c to console printer", (char) Registers.GPRS[GPR_X]));
    }

    public static void CHK(int GPR_X,int DevID_X) {
        SystemControl.PrintToDebugConsole("Executing CHK");

        if (Utils.DevID == 0) {
            if (IOGUI.isKBBufferEmpty()) {
                Registers.GPRS[GPR_X] = 0;
                SystemControl.PrintToDebugConsole("  No input from keyboard to read");
            } else {
                Registers.GPRS[GPR_X] = 1;
                SystemControl.PrintToDebugConsole("  There is input to read from keyboard");
            }
        } else if (Utils.DevID == 2) {
            if (IOGUI.isCRBufferEmpty()) {
                Registers.GPRS[GPR_X] = 0;
                SystemControl.PrintToDebugConsole("  No input from card reader to read");
            } else {
                Registers.GPRS[GPR_X] = 1;
                SystemControl.PrintToDebugConsole("  There is input to read from card reader");
            }
        } else if (Utils.DevID == 1) {
            Registers.GPRS[GPR_X] = 1;
            SystemControl.PrintToDebugConsole("  Enabled: Console printer");
        } else {
            Registers.GPRS[GPR_X] = 0;
        }
    }
}
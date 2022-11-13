public class Utils
{
    public static int opcode_int = 0;
    public static String opcode = "000000";
    public static int GPR_Index = 0;
    public static int IXR_Index = 0;
    public static int I_bit = 0;
    public static int Address = 0;
    public static int Rx = 0;
    public static int Ry = Rx + 1;
    public static int AL = 0;
    public static int RL = 0;
    public static int Count = 0;
    public static int DevID = 0;
    public static int S_Bit = 0;
    public static int Exponent = 0;
    public static int Mantissa = 0;

    public static int EA = 0;
    public static int IXEA = 0;


    public static Utils utils;


    public static void split_instructions(int instruction_code)
    {
        String Instruction_binary = String.format("%16s", Integer.toBinaryString(instruction_code).replace(' ', '0'));

        opcode_int = Integer.parseInt(Instruction_binary.substring(0, 6), 2);
        opcode = Instruction_binary.substring(0, 6);
        GPR_Index = Integer.parseInt(Instruction_binary.substring(6, 8), 2);
        IXR_Index = Integer.parseInt(Instruction_binary.substring(8, 10), 2) - 1;
        I_bit = Integer.parseInt(Instruction_binary.substring(10, 11), 2);
        Address = Integer.parseInt(Instruction_binary.substring(11), 2);
        Rx = Integer.parseInt(Instruction_binary.substring(6, 8), 2);
        Ry = Integer.parseInt(Instruction_binary.substring(8, 10), 2);
        AL = Integer.parseInt(Instruction_binary.substring(8, 9), 2);
        RL = Integer.parseInt(Instruction_binary.substring(9, 10), 2);
        Count = Integer.parseInt(Instruction_binary.substring(12), 2);
        DevID = Integer.parseInt(Instruction_binary.substring(11), 2);
        S_Bit = Integer.parseInt(Instruction_binary.substring(0, 1), 2);
        Exponent = Integer.parseInt(Instruction_binary.substring(1, 8), 2);
        Mantissa = Integer.parseInt(Instruction_binary.substring(8), 2);

    }



    public static int calculateEffectiveAddress() {
        if (I_bit == 0) {
            if (IXR_Index == 0) {
                return Address;
            } else {
                return Address + Registers.IXRS[IXR_Index];
            }
        } else {
            if (IXR_Index == 0) {
                return Memory.get_from_memory(Address);
            } else {
                return Memory.get_from_memory(Address) + Memory.get_from_memory(Registers.IXRS[IXR_Index]);
            }
        }
    }
    
    
    public static int calculateEffectiveAddress_IXR()
    {
        if (I_bit == 0) {
            return Address;
        } else {
            return Memory.get_from_memory(Address);
        }    
    }
    
    

    public static void execute()
    {
        SystemControl.PrintToDebugConsole("Executing Instruction -> ");

        EA = calculateEffectiveAddress();
        IXEA = calculateEffectiveAddress_IXR();


        if (((opcode_int >= 4 && opcode_int <= 7) || (opcode_int >= 16 && opcode_int <= 18))) {
            Registers.CC1 = false;
            Registers.CC2 = false;
            Registers.CC3 = false;
            Registers.CC4 = false;
        }

        switch (opcode) {
            case "000001":
                ALU.LDR(GPR_Index, IXR_Index, EA);
                break;
            case 2:
                ALU.str();
                break;
            case 3:
                ALU.lda();
                break;
            case 33:
                ALU.ldx();
                break;
            case 34:
                ALU.stx();
                break;
            case 8:
                ALU.jz();
                break;
            case 9:
                ALU.jne();
                break;
            case 10:
                ALU.jcc();
                break;
            case 11:
                ALU.jma();
                break;
            case 12:
                ALU.jsr();
                break;
            case 13:
                ALU.rfs();
                break;
            case 14:
                ALU.sob();
                break;
            case 15:
                ALU.jge();
                break;
            case 4:
                ALU.amr();
                break;
            case 5:
                ALU.smr();
                break;
            case 6:
                ALU.air();
                break;
            case 7:
                ALU.sir();
                break;
            case 16:
                ALU.mlt();
                break;
            case 17:
                ALU.dvd();
                break;
            case 18:
                ALU.trr();
                break;
            case 19:
                ALU.and();
                break;
            case 20:
                ALU.orr();
                break;
            case 21:
                ALU.not();
            case 25:
                ALU.src();
                break;
            case 26:
                ALU.rrc();
                break;
            case 49:
                ALU.in();
                break;
            case 50:
                ALU.out();
                break;
            case 51:
                ALU.chk();
                break;
            case 35:
                ALU.jgt();
                break;
            default:
                ALU.halt();
                break;
        }

        if (!((opcode_int >= 4 && opcode_int <= 7) || (opcode_int >= 16 && opcode_int <= 18))) {
            Registers.CC1 = false;
            Registers.CC2 = false;
            Registers.CC3 = false;
            Registers.CC4 = false;

        }

    }




}

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
            case "000010":
                ALU.STR(GPR_Index, IXR_Index, EA);
                break;
            case "000011":
                ALU.LDA(GPR_Index, IXR_Index, EA);
                break;
            case "100001":
                ALU.LDX(IXR_Index, IXEA);
                break;
            case "100010":
                ALU.STX(IXR_Index, IXEA);
                break;
            case "001000":
                ALU.JZ(GPR_Index, IXR_Index, EA);
                break;
            case "001001":
                ALU.JNE(GPR_Index, IXR_Index, EA);
                break;
            case "001010":
                ALU.JCC(GPR_Index, IXR_Index, EA);
                break;
            case "001011":
                ALU.JMA(IXR_Index, EA);
                break;
            case "001100":
                ALU.JSR(IXR_Index, EA);
                break;
            case "001101":
                ALU.RFS();
                break;
            case "001110":
                ALU.SOB(GPR_Index, IXR_Index, EA);
                break;
            case "001111":
                ALU.JGE(GPR_Index, IXR_Index, EA);
                break;
            case "000100":
                ALU.AMR(GPR_Index, IXR_Index, EA);
                break;
            case "000101":
                ALU.SMR(GPR_Index, IXR_Index, EA);
                break;
            case "000110":
                ALU.AIR(GPR_Index,Address);
                break;
            case "000111":
                ALU.SIR(GPR_Index,Address);
                break;
            case "010000":
                ALU.MLT(Rx,Ry);
                break;
            case "010001":
                ALU.DVD(Rx,Ry);
                break;
            case "010010":
                ALU.TRR(Rx,Ry);
                break;
            case "010011":
                ALU.AND(Rx,Ry);
                break;
            case "010100":
                ALU.ORR(Rx,Ry);
                break;
            case "010101":
                ALU.NOT(Rx);
            case "011001":
                ALU.SRC(GPR_Index,Count,RL,AL);
                break;
            case "011010":
                ALU.RRC(GPR_Index,Count,RL,AL);
                break;
            case "110001":
                ALU.IN(GPR_Index,DevID);
                break;
            case "110010":
                ALU.OUT(GPR_Index,DevID);
                break;
            case "110011":
                ALU.CHK(GPR_Index,DevID);
                break;
            case "100011":
                ALU.JGT();
                break;
            default:
                ALU.HALT();
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

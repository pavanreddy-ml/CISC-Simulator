public class Utils
{
    private final SystemControl systemControl;

    public Utils(SystemControl systemControl) {
        this.systemControl = systemControl;
    }

    public String opcode = "000000";
    public int GPR_Index = 0;
    public int IXR_Index = 0;
    public int I_bit = 0;
    public int Address = 0;
    public int Rx = 0;
    public int Ry = Rx + 1;
    public int AL = 0;
    public int RL = 0;
    public int Count = 0;
    public int DevID = 0;
    public int S_Bit = 0;
    public int Exponent = 0;
    public int Mantissa = 0;
    public int TrapCode = 12;

    public int EA = 0;
    public int IXEA = 0;
    


    public void split_instructions(int instruction_code)
    {
        String Instruction_binary = String.format("%16s", Integer.toBinaryString(instruction_code)).replace(' ', '0');

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
        TrapCode = Integer.parseInt(Instruction_binary.substring(12), 2);

        System.out.println(IXR_Index);

    }



    public int calculateEffectiveAddress() {
        if (I_bit == 0) {
            if (IXR_Index >= 0) {
                return Address + systemControl.registers.IXRS[IXR_Index];
            } else {
                return Address;
            }
        } else {
            if (IXR_Index >= 0) {
                return systemControl.memory.get_from_memory(Address) +systemControl.memory.get_from_memory(systemControl.registers.IXRS[IXR_Index]);
            } else {
                return systemControl.memory.get_from_memory(Address);
            }
        }
    }


    public int calculateEffectiveAddress_IXR()
    {
        if (I_bit == 0) {
            return Address;
        } else {
            return systemControl.memory.get_from_memory(Address);
        }
    }



    public void execute()
    {
//        systemControl.PrintToDebugConsole("Executing Instruction -> ");

        EA = calculateEffectiveAddress();
        IXEA = calculateEffectiveAddress_IXR();


        if (((Integer.parseInt(opcode, 2) >= 4 && Integer.parseInt(opcode, 2) <= 7) ||
                (Integer.parseInt(opcode, 2) >= 16 && Integer.parseInt(opcode, 2) <= 18))) {
            systemControl.registers.CC1 = false;
            systemControl.registers.CC2 = false;
            systemControl.registers.CC3 = false;
            systemControl.registers.CC4 = false;
        }

        switch (opcode) {
            case "000001":
                systemControl.alu.LDR(GPR_Index, IXR_Index, EA);
                break;
            case "000010":
                systemControl.alu.STR(GPR_Index, IXR_Index, EA);
                break;
            case "000011":
                systemControl.alu.LDA(GPR_Index, IXR_Index, EA);
                break;
            case "100001":
                systemControl.alu.LDX(IXR_Index, IXEA);
                break;
            case "100010":
                systemControl.alu.STX(IXR_Index, IXEA);
                break;
            case "001000":
                systemControl.alu.JZ(GPR_Index, IXR_Index, EA);
                break;
            case "001001":
                systemControl.alu.JNE(GPR_Index, IXR_Index, EA);
                break;
            case "001010":
                systemControl.alu.JCC(GPR_Index, IXR_Index, EA);
                break;
            case "001011":
                systemControl.alu.JMA(IXR_Index, EA);
                break;
            case "001100":
                systemControl.alu.JSR(IXR_Index, EA);
                break;
            case "001101":
                systemControl.alu.RFS(Address);
                break;
            case "001110":
                systemControl.alu.SOB(GPR_Index, IXR_Index, EA);
                break;
            case "001111":
                systemControl.alu.JGE(GPR_Index, IXR_Index, EA);
                break;
            case "000100":
                systemControl.alu.AMR(GPR_Index, IXR_Index, EA);
                break;
            case "000101":
                systemControl.alu.SMR(GPR_Index, IXR_Index, EA);
                break;
            case "000110":
                systemControl.alu.AIR(GPR_Index,Address);
                break;
            case "000111":
                systemControl.alu.SIR(GPR_Index,Address);
                break;
            case "010000":
                systemControl.alu.MLT(Rx,Ry);
                break;
            case "010001":
                systemControl.alu.DVD(Rx,Ry);
                break;
            case "010010":
                systemControl.alu.TRR(Rx,Ry);
                break;
            case "010011":
                systemControl.alu.AND(Rx,Ry);
                break;
            case "010100":
                systemControl.alu.ORR(Rx,Ry);
                break;
            case "010101":
                systemControl.alu.NOT(Rx);
            case "011001":
                systemControl.alu.SRC(GPR_Index,Count,RL,AL);
                break;
            case "011010":
                systemControl.alu.RRC(GPR_Index,Count,RL,AL);
                break;
            case "110001":
                systemControl.alu.IN(GPR_Index,DevID);
                break;
            case "110010":
                systemControl.alu.OUT(GPR_Index,DevID, I_bit);
                break;
            case "110011":
                systemControl.alu.CHK(GPR_Index,DevID);
                break;
            case "100011":
                systemControl.alu.JGT(Rx, Ry, IXEA);
                break;
            case " ":
                systemControl.alu.TRAP(TrapCode);

            default:
                systemControl.alu.HALT();
                break;
        }

        if (!((Integer.parseInt(opcode, 2) >= 4 && Integer.parseInt(opcode, 2) <= 7) ||
                (Integer.parseInt(opcode, 2) >= 16 && Integer.parseInt(opcode, 2) <= 18))) {
            systemControl.registers.CC1 = false;
            systemControl.registers.CC2 = false;
            systemControl.registers.CC3 = false;
            systemControl.registers.CC4 = false;

        }

    }




}

public class InstructionComponents {

    public int opcode = 0;
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


    public InstructionComponents(int content) {
        String Instruction_binary = String.format("%16s", Integer.toBinaryString(content)).replace(' ', '0');
        opcode = Integer.parseInt(Instruction_binary.substring(0, 6), 2);
        GPR_Index = Integer.parseInt(Instruction_binary.substring(6, 8), 2);
        IXR_Index = Integer.parseInt(Instruction_binary.substring(8, 10), 2) - 1;
        I_bit = Integer.parseInt(Instruction_binary.substring(10, 11), 2);
        Address = Integer.parseInt(Instruction_binary.substring(11), 2);
        Rx = Integer.parseInt(Instruction_binary.substring(6, 8), 2);;
        Ry = Integer.parseInt(Instruction_binary.substring(8, 10), 2);
        AL = Integer.parseInt(Instruction_binary.substring(8, 9), 2);
        RL = Integer.parseInt(Instruction_binary.substring(9, 10), 2);
        Count = Integer.parseInt(Instruction_binary.substring(11), 2);
        TrapCode = Integer.parseInt(Instruction_binary.substring(12), 2);
        DevID = Integer.parseInt(Instruction_binary.substring(11), 2);
        S_Bit = Integer.parseInt(Instruction_binary.substring(0, 1), 2);
        Exponent = Integer.parseInt(Instruction_binary.substring(1, 8), 2);
        Mantissa = Integer.parseInt(Instruction_binary.substring(8), 2);
        TrapCode = Integer.parseInt(Instruction_binary.substring(12), 2);
    }
}

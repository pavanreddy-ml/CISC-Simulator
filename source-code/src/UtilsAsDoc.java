import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UtilsAsDoc {

    public SystemCore systemCore;

    public UtilsAsDoc(SystemCore systemCore) {
        this.systemCore = systemCore;
    }

    public void LoadProgram(File file) {
        String line;
        int Address;
        int content;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            line = br.readLine();
            while (line != null) {
                Address = Integer.parseInt(line.substring(0, 4), 16);
                content = Integer.parseInt(line.substring(5, 9), 16);
                systemCore.memory.ProcessorMemory[Address] = content;
                line = br.readLine();
            }
            systemCore.frame.PrintToDebugConsole(String.format("Program %s loaded", file.getName()));
        } catch (IOException e) {
            systemCore.frame.PrintToDebugConsole(String.format("Program %s doesn't exist", file.getName()));
        }
    }

    public void exec(InstructionComponents instruction) throws Exception {
        instruction.EA = computeEA(instruction);
        instruction.IXEA = computeIXREA(instruction);


        if (((instruction.opcode >= 4 && instruction.opcode <= 7) || (instruction.opcode >= 16 && instruction.opcode <= 18) || (instruction.opcode >= 25 && instruction.opcode <= 26))) {
            systemCore.registers.CC = 0;
        }

        switch (instruction.opcode) {
            case 0:
                systemCore.alu.halt();
                break;
            case 1:
                systemCore.alu.LDR(instruction);
                break;
            case 2:
                systemCore.alu.STR(instruction);
                break;
            case 3:
                systemCore.alu.LDA(instruction);
                break;
            case 41:
                systemCore.alu.LDX(instruction);
                break;
            case 42:
                systemCore.alu.STX(instruction);
                break;
            case 10:
                systemCore.alu.JZ(instruction);
                break;
            case 11:
                systemCore.alu.JNE(instruction);
                break;
            case 12:
                systemCore.alu.JCC(instruction);
                break;
            case 13:
                systemCore.alu.JMA(instruction);
                break;
            case 14:
                systemCore.alu.JSR(instruction);
                break;
            case 15:
                systemCore.alu.RFS(instruction);
                break;
            case 16:
                systemCore.alu.SOB(instruction);
                break;
            case 17:
                systemCore.alu.JGE(instruction);
                break;
            case 4:
                systemCore.alu.AMR(instruction);
                break;
            case 5:
                systemCore.alu.SMR(instruction);
                break;
            case 6:
                systemCore.alu.AIR(instruction);
                break;
            case 7:
                systemCore.alu.SIR(instruction);
                break;
            case 20:
                systemCore.alu.MLT(instruction);
                break;
            case 21:
                systemCore.alu.DVD(instruction);
                break;
            case 22:
                systemCore.alu.TRR(instruction);
                break;
            case 23:
                systemCore.alu.AND(instruction);
                break;
            case 24:
                systemCore.alu.ORR(instruction);
                break;
            case 25:
                systemCore.alu.NOT(instruction);
                break;
            case 31:
                systemCore.alu.SRC(instruction);
                break;
            case 32:
                systemCore.alu.RRC(instruction);
                break;
            case 61:
                systemCore.alu.IN(instruction);
                break;
            case 62:
                systemCore.alu.OUT(instruction);
                break;
            case 63:
                systemCore.alu.CHK(instruction);
                break;
            case 64:
                systemCore.alu.JGT(instruction);
                break;
            case 30:
                if (instruction.TrapCode > 15 || instruction.TrapCode < 0) {
                    systemCore.registers.MFR |= 1;
                    break;
                }
                systemCore.alu.trap(instruction);
                break;
            case 33:
                systemCore.alu.FADD(instruction);
                break;
            case 34:
                systemCore.alu.FSUB(instruction);
                break;
            case 35:
                systemCore.alu.VADD(instruction);
                break;
            case 36:
                systemCore.alu.VSUB(instruction);
                break;
            case 37:
                systemCore.alu.CNVRT(instruction);
                break;
            case 50:
                systemCore.alu.LDFR(instruction);
                break;
            case 87:
                systemCore.alu.STFR(instruction);
                break;
            default:
                systemCore.registers.MFR |= 4;
                systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
                systemCore.frame.PrintToDebugConsole("Illegal Operation Code");
                break;
        }

        /*
        if (!((instruction.opcode >= 4 && instruction.opcode <= 7) || (instruction.opcode >= 16 && instruction.opcode <= 18) || (instruction.opcode >= 25 && instruction.opcode <= 26))) {
            registers.CC = 0;
        }
        */

        systemCore.systemFunctions.Delay();
    }


    public int computeEA(InstructionComponents instruction) {
        if (instruction.I_bit == 0) {
            if (instruction.IXR_Index >= 0) {
                return instruction.Address + systemCore.registers.IXRS[instruction.IXR_Index];
            } else {
                return instruction.Address;
            }
        } else {
            if (instruction.IXR_Index >= 0) {
                return systemCore.memory.ReadFromMemory(instruction.Address) + systemCore.memory.ReadFromMemory(systemCore.registers.IXRS[instruction.IXR_Index]);
            } else {
                return systemCore.memory.ReadFromMemory(instruction.Address);
            }
        }
    }

    //Connecting and updating the memory.ProcessorMemory
    public int computeIXREA(InstructionComponents instruction) {
        if (instruction.I_bit == 0) {
            return instruction.Address;
        } else {
            return systemCore.memory.ReadFromMemory(instruction.Address);
        }
    }


    public double convertToFloat(int content) {
        double number;

        String bits = String.format("%16S", Integer.toBinaryString(content).replace(' ', '0'));

        int S_Bit = Integer.parseInt(bits.substring(0, 1), 2);
        int Exponent = Integer.parseInt(bits.substring(1, 8), 2) - 63;
        int Mantissa = Integer.parseInt(bits.substring(8), 2);

        number = Mantissa * Math.pow(10, Exponent);

        if (S_Bit == 0)
        {
            number = 0 - number;
        }

//        System.out.println(convertToFloat("33538"));   // Sting 1100001100000010

        return number;
    }


    public int getIntValofFloat(double number)
    {
        String s = String.valueOf(number);
        int n = s.length();

        StringBuilder num_string = new StringBuilder();
        int i, j, exponent, c;

        for (i = 0; s.charAt(i) == '0' || s.charAt(i) == '.'; i++)
            ;
        for (j = n - 1; s.charAt(j) == '0' || s.charAt(j) == '.'; j--)
            ;

        c = s.indexOf('.');

        if (c == -1) {
            c = n;
        }

        num_string.append(s.charAt(i));

        if (i != j) {
            num_string.append('.');
        }

        for (int k = i + 1; k <= j; k++) {
            if (s.charAt(k) != '.') {
                num_string.append(s.charAt(k));
            }
        }

        if (i < c) {
            exponent = c - i - 1;
        }
        else {
            exponent = c - i;
        }

        i = 0;
        StringBuilder new_num_string = new StringBuilder();


        while (true)
        {

            if (num_string.charAt(i) != '.')
            {
                new_num_string.append(num_string.charAt(i));
            }

            if (new_num_string.length() == 3)
            {
                break;
            }

            i++;
        }

        exponent = exponent - 2;


        int mantissa = Integer.parseInt(new_num_string.toString());

        if (mantissa > 255)
        {
            mantissa = Integer.parseInt(String.valueOf(mantissa).substring(0, 2));
            exponent++;
        }





        String Final_Num_Bin = "";

        if (number < 0)
        {
            Final_Num_Bin = Final_Num_Bin + "0";
        }
        else
        {
            Final_Num_Bin = Final_Num_Bin + "1";
        }


        if (exponent == 0)
        {
            Final_Num_Bin = Final_Num_Bin + "0000000";
        } else if (exponent > 0) {
            Final_Num_Bin = Final_Num_Bin + String.format("%7S", Integer.toBinaryString(64 + exponent).replace(' ', '0'));
        } else if (exponent < 0) {
            Final_Num_Bin = Final_Num_Bin + "0";
            Final_Num_Bin = Final_Num_Bin + String.format("%6S", Integer.toBinaryString(64 - Math.abs(exponent)).replace(' ', '0'));
        }


        Final_Num_Bin = Final_Num_Bin + String.format("%8S", Integer.toBinaryString(mantissa).replace(' ', '0'));


        return Integer.parseInt(Final_Num_Bin, 2);
    }

}

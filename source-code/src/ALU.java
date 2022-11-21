public class ALU {
    public SystemCore systemCore;

    //It redirects to the backend
    public ALU(SystemCore systemCore) {
        this.systemCore = systemCore;
    }
    //Connecting and updating the IXR

    //Defining the Halt
    public void halt() {
        systemCore.systemSettings.Running = false;
        systemCore.systemSettings.Idle = true;

        systemCore.frame.debugPrint("Running Halt");
    }

    //Defining the Trap
    public void trap(InstructionComponents instruction) {
        systemCore.memory.WriteToMemory(2, systemCore.registers.PC + 1);

        systemCore.registers.PC = systemCore.memory.ReadFromMemory(systemCore.memory.ReadFromMemory(0) + instruction.TrapCode);

        systemCore.frame.debugPrint("Running TRAP");
    }


    //Defining the LDR
    public void ldr(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running LDR");
        int ea = instruction.EA;

        if (ea > systemCore.memory.ProcessorMemory.length) {
            systemCore.registers.MFR |= 8;
            systemCore.frame.debugPrint("Illegal Memory Address beyond " + systemCore.memory.ProcessorMemory.length);
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        if (ea >= 0 && ea <= 5) {
            systemCore.registers.MFR |= 1;
            systemCore.frame.debugPrint("Illegal Memory Address to Reserved Locations");
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        systemCore.registers.GPRS[instruction.GPR_Index] = (systemCore.memory.ReadFromMemory(ea));

        systemCore.frame.debugPrint(String.format("  GPR: %d\n  EA: %H", instruction.GPR_Index, ea));
    }

    //Defining the STR
    public void str(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running STR");

        int ea = instruction.EA;

        if (ea > systemCore.memory.ProcessorMemory.length) {
            systemCore.registers.MFR |= 8;
            systemCore.frame.debugPrint("Illegal Memory Address beyond " + systemCore.memory.ProcessorMemory.length);
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        if (ea >= 0 && ea <= 5) {
            systemCore.registers.MFR |= 1;
            systemCore.frame.debugPrint("Illegal Memory Address to Reserved Locations");
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        systemCore.memory.WriteToMemory(ea, systemCore.registers.GPRS[instruction.GPR_Index]);

        systemCore.frame.debugPrint(String.format("  GPR: %d\n  EA: %H", instruction.GPR_Index, ea));
    }

    //Defining the LDA
    public void lda(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running LDA");

        int ea = instruction.EA;

        systemCore.registers.GPRS[instruction.GPR_Index] = ea;

        systemCore.frame.debugPrint(String.format("  GPR: %d\n  EA: %H", instruction.GPR_Index, ea));
    }

    public void ldx(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running LDX");

        int ea = instruction.IXEA;

        if (ea > systemCore.memory.ProcessorMemory.length) {
            systemCore.registers.MFR |= 8;
            systemCore.frame.debugPrint("Illegal Memory Address beyond " + systemCore.memory.ProcessorMemory.length);
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        if (ea >= 0 && ea <= 5) {
            systemCore.registers.MFR |= 1;
            systemCore.frame.debugPrint("Illegal Memory Address to Reserved Locations");
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        systemCore.registers.IXRS[instruction.IXR_Index] = systemCore.memory.ReadFromMemory(ea);

        systemCore.frame.debugPrint(String.format("  IXR: %d\n  EA: %H", instruction.IXR_Index, ea));
    }

    //Defining the STX
    public void stx(InstructionComponents instruction) {
        int ea = instruction.IXEA;

        if (ea > systemCore.memory.ProcessorMemory.length) {
            systemCore.registers.MFR |= 8;
            systemCore.frame.debugPrint("Illegal Memory Address beyond " + systemCore.memory.ProcessorMemory.length);
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        if (ea >= 0 && ea <= 5) {
            systemCore.registers.MFR |= 1;
            systemCore.frame.debugPrint("Illegal Memory Address to Reserved Locations");
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        systemCore.memory.WriteToMemory(ea, systemCore.registers.IXRS[instruction.IXR_Index]);

        systemCore.frame.debugPrint(String.format("Running STX\n  IXR: %d\n  EA: %H", instruction.IXR_Index, ea));
    }

    //Defining the JZ
    public void jz(InstructionComponents instruction) {
        int ea = instruction.EA;

        if (systemCore.registers.GPRS[instruction.GPR_Index] == 0) {
            systemCore.registers.PC = ea;
            systemCore.frame.debugPrint(String.format("Running JZ\n  GPR %d is zero, jump to %H", instruction.GPR_Index, ea));
            return;
        }
        systemCore.frame.debugPrint(String.format("Running JZ\n  GPR %d is not zero, do not jump", instruction.GPR_Index));
    }

    //Defining the JNE
    public void jne(InstructionComponents instruction) {
        int ea = instruction.EA;

        if (systemCore.registers.GPRS[instruction.GPR_Index] != 0) {
            systemCore.registers.PC = ea;
            systemCore.frame.debugPrint(String.format("Running JNE\n  GPR %d is not zero, jump to %H", instruction.GPR_Index, ea));
            return;
        }
        systemCore.frame.debugPrint(String.format("Running JNE\n  GPR %d is zero, do not jump", instruction.GPR_Index));
    }

    //Defining the JCC
    public void jcc(InstructionComponents instruction) {
        int ea = instruction.EA;
        int bitMask = 0;

        switch (instruction.GPR_Index) {
            case 0:
                bitMask = 1; // 0001
                break;
            case 1:
                bitMask = 2; // 0010
                break;
            case 2:
                bitMask = 4; // 0100
                break;
            case 3:
                bitMask = 8; // 1000
                break;
        }

        if ((systemCore.registers.CC & bitMask) != 0) {
            systemCore.registers.PC = ea;
            systemCore.frame.debugPrint(String.format("Running JCC\n  CC: %d equals to required CC: %d jump to %H", systemCore.registers.CC, instruction.GPR_Index, ea));
            return;
        }
        systemCore.frame.debugPrint(String.format("Running JCC\n  CC: %d does not equal to required CC: %d", systemCore.registers.CC, instruction.GPR_Index));
    }

    //Defining the JMA
    public void jma(InstructionComponents instruction) {
        int ea = instruction.EA;

        systemCore.registers.PC = ea;

        systemCore.frame.debugPrint(String.format("Running JMA\n  Jump to %H", ea));
    }

    //Defining the JSR
    public void jsr(InstructionComponents instruction) {
        int ea = instruction.EA;

        systemCore.registers.GPRS[3] = systemCore.registers.PC;
        systemCore.registers.PC = ea;

        systemCore.frame.debugPrint(String.format("Running JSR\n  Jump to %H, current Args at %H", ea, systemCore.registers.GPRS[0]));
    }

    //Defining the JGT
    public void jgt(InstructionComponents instruction) {
        int ea = instruction.IXEA;

        if (systemCore.registers.GPRS[instruction.Rx] > systemCore.registers.GPRS[instruction.Ry]) {
            systemCore.registers.PC = ea;
            systemCore.frame.debugPrint(String.format("Running JGT\n  Jump to %H", ea));
            return;
        }

        systemCore.frame.debugPrint(String.format("Running JGT\n  do not jump to %H", ea));
    }

    //Defining the RFS
    public void rfs(InstructionComponents instruction) {
        systemCore.registers.PC = systemCore.registers.GPRS[3];
        systemCore.registers.GPRS[0] = instruction.Address;

        systemCore.frame.debugPrint(String.format("Running RFS\n  Return to %H, return value at %H", systemCore.registers.GPRS[3], systemCore.registers.GPRS[0]));
    }

    //Defining the SOB
    public void sob(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running SOB");

        int ea = instruction.EA;

        systemCore.registers.GPRS[instruction.GPR_Index]--;
        if (systemCore.registers.GPRS[instruction.GPR_Index] < 0) {
            systemCore.registers.GPRS[instruction.GPR_Index] = (int) (Math.pow(2, 16) - 1);
            systemCore.registers.CC |= 2;
            systemCore.frame.debugPrint("  Underflow!");
        }

        if (systemCore.registers.GPRS[instruction.GPR_Index] > 0) {
            systemCore.registers.PC = ea;
            systemCore.frame.debugPrint(String.format("  GPR%d: %d, jump to %H", instruction.GPR_Index, systemCore.registers.GPRS[instruction.GPR_Index], ea));
            return;
        }
        systemCore.frame.debugPrint(String.format("  GPR%d: %d, not jump", instruction.GPR_Index, systemCore.registers.GPRS[instruction.GPR_Index]));
    }

    //Defining the JGE
    public void jge(InstructionComponents instruction) {
        int ea = instruction.EA;

        if (systemCore.registers.GPRS[instruction.GPR_Index] >= 0) {
            systemCore.registers.PC = ea;
            systemCore.frame.debugPrint(String.format("Running JGE\n  GRP%d: %d, jump to %H", instruction.GPR_Index, systemCore.registers.GPRS[instruction.GPR_Index], ea));
            return;
        }
        systemCore.frame.debugPrint(String.format("Running JGE\n  GRP%d: %d, not jump", instruction.GPR_Index, systemCore.registers.GPRS[instruction.GPR_Index]));
    }

    //Defining the AMR
    public void amr(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running AMR");

        int ea = instruction.EA;

        if (ea > systemCore.memory.ProcessorMemory.length) {
            systemCore.registers.MFR |= 8;
            systemCore.frame.debugPrint("Illegal Memory Address beyond " + systemCore.memory.ProcessorMemory.length);
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        if (ea >= 0 && ea <= 5) {
            systemCore.registers.MFR |= 1;
            systemCore.frame.debugPrint("Illegal Memory Address to Reserved Locations");
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        systemCore.registers.GPRS[instruction.GPR_Index] += systemCore.memory.ReadFromMemory(ea);
        if (systemCore.registers.GPRS[instruction.GPR_Index] > 65535) {
            systemCore.registers.GPRS[instruction.GPR_Index] -= 65536;
            systemCore.registers.CC |= 1;
            systemCore.frame.debugPrint("  Overflow!");
        }

        systemCore.frame.debugPrint(String.format("  Add %d at %H to GPR%d, result is %d", systemCore.memory.ProcessorMemory[ea], ea, instruction.GPR_Index, systemCore.registers.GPRS[instruction.GPR_Index]));
    }

    //Defining the SMR
    public void smr(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running SMR");

        int ea = instruction.EA;

        if (ea > systemCore.memory.ProcessorMemory.length) {
            systemCore.registers.MFR |= 8;
            systemCore.frame.debugPrint("Illegal Memory Address beyond " + systemCore.memory.ProcessorMemory.length);
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        if (ea >= 0 && ea <= 5) {
            systemCore.registers.MFR |= 1;
            systemCore.frame.debugPrint("Illegal Memory Address to Reserved Locations");
            systemCore.registers.PC = systemCore.memory.ReadFromMemory(1);
            return;
        }

        systemCore.registers.GPRS[instruction.GPR_Index] -= systemCore.memory.ReadFromMemory(ea);
        if (systemCore.registers.GPRS[instruction.GPR_Index] < 0) {
            systemCore.registers.GPRS[instruction.GPR_Index] += 65536;
            systemCore.registers.CC |= 2;
            systemCore.frame.debugPrint("  Underflow!");
        }

        systemCore.frame.debugPrint(String.format("  Sub %d at %H from GPR%d, result is %d", systemCore.memory.ProcessorMemory[ea], ea, instruction.GPR_Index, systemCore.registers.GPRS[instruction.GPR_Index]));
    }

    //Defining the AIR
    public void air(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running AIR");

        int ea = instruction.EA;

        systemCore.registers.GPRS[instruction.GPR_Index] += ea;

        if (systemCore.registers.GPRS[instruction.GPR_Index] > 65535) {
            systemCore.registers.GPRS[instruction.GPR_Index] -= 65536;
            systemCore.registers.CC |= 1;
            systemCore.frame.debugPrint("  Overflow!");
        }

        systemCore.frame.debugPrint(String.format("  Add %d to GPR%d, result is %d", ea, instruction.GPR_Index, systemCore.registers.GPRS[instruction.GPR_Index]));
    }

    //Defining the SIR
    public void sir(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running SIR");

        int ea = instruction.EA;

        systemCore.registers.GPRS[instruction.GPR_Index] -= ea;

        if (systemCore.registers.GPRS[instruction.GPR_Index] < 0) {
            systemCore.registers.GPRS[instruction.GPR_Index] += 65536;
            systemCore.registers.CC |= 2;
            systemCore.frame.debugPrint("  Underflow!");
        }

        systemCore.frame.debugPrint(String.format("  Sub %d from GPR%d, result is %d", ea, instruction.GPR_Index, systemCore.registers.GPRS[instruction.GPR_Index]));
    }

    //Defining the MLT
    public void mlt(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running MLT");

        long result = (long) systemCore.registers.GPRS[instruction.Rx] * systemCore.registers.GPRS[instruction.Ry];
        String resultStr = String.format("%32s", Long.toBinaryString(result)).replace(' ', '0');

        String hBits = resultStr.substring(0, 16);
        String lBits = resultStr.substring(16, 32);

        systemCore.registers.GPRS[instruction.Rx] = Integer.parseInt(hBits, 2);
        systemCore.registers.GPRS[instruction.Rx + 1] = Integer.parseInt(lBits, 2);

        systemCore.frame.debugPrint(String.format("  MLT r%d with r%d, result is %d %s %s %s", instruction.Rx, instruction.Ry, result, resultStr, hBits, lBits));
    }

    //Defining the DVD
    public void dvd(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running DVD");

        if (systemCore.registers.GPRS[instruction.Ry] == 0) {
            systemCore.frame.debugPrint("  Divide by 0");
            systemCore.registers.CC |= 4;
            return;
        }

        int quotient = systemCore.registers.GPRS[instruction.Rx] / systemCore.registers.GPRS[instruction.Ry];
        int remainder = systemCore.registers.GPRS[instruction.Rx] % systemCore.registers.GPRS[instruction.Ry];

        systemCore.registers.GPRS[instruction.Rx] = quotient;
        systemCore.registers.GPRS[instruction.Rx + 1] = remainder;

        systemCore.frame.debugPrint(String.format(" DVD r%d with r%d, quotient is %d, remainder is %d", instruction.Rx, instruction.Ry, quotient, remainder));
    }

    //Defining the TRR
    public void trr(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running TRR");

        if (systemCore.registers.GPRS[instruction.Rx] == systemCore.registers.GPRS[instruction.Ry]) {
            systemCore.registers.CC |= 8;
            systemCore.frame.debugPrint(String.format("  r%d equals to r%d", instruction.Rx, instruction.Ry));
            return;
        }

        systemCore.frame.debugPrint(String.format("  r%d does not equal to r%d", instruction.Rx, instruction.Ry));
    }

    //Defining the AND
    public void and(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running AND");

        systemCore.registers.GPRS[instruction.Rx] &= systemCore.registers.GPRS[instruction.Ry];

        systemCore.frame.debugPrint(String.format("  r%d AND r%d", instruction.Rx, instruction.Ry));
    }

    //Defining the ORR
    public void orr(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running ORR");

        systemCore.registers.GPRS[instruction.Rx] |= systemCore.registers.GPRS[instruction.Ry];

        systemCore.frame.debugPrint(String.format("  r%d ORR r%d", instruction.Rx, instruction.Ry));
    }

    //Defining the NOT
    public void not(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running NOT");

        int result = ~systemCore.registers.GPRS[instruction.Rx];
        String resultStr = Integer.toBinaryString(result);
        resultStr = resultStr.substring(resultStr.length() - 16);

        systemCore.registers.GPRS[instruction.Rx] = Integer.parseInt(resultStr, 2);

        systemCore.frame.debugPrint(String.format("  NOT r%d", instruction.Rx));
    }

    //Defining the SRC
    public void src(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running SRC");

        String origin = String.format("%16S", Integer.toBinaryString(systemCore.registers.GPRS[instruction.GPR_Index])).replace(' ', '0');
        StringBuilder result = new StringBuilder();

        if (instruction.RL == 0) {
            if (origin.substring(16 - instruction.Count).contains("1")) {
                systemCore.registers.CC |= 2;
                systemCore.frame.debugPrint("  Underflow!");
            }
        } else {
            System.out.println(origin.substring(0, instruction.Count));
            if (origin.substring(0, instruction.Count).contains("1")) {
                systemCore.registers.CC |= 1;
                systemCore.frame.debugPrint("  Overflow!");
            }
        }

        if (instruction.AL == 1) {
            if (instruction.RL == 0) {
                systemCore.frame.debugPrint("Shifting right");
                for (int j = 0; j < instruction.Count; j++) {
                    result.append('0');
                }
                result.append(origin, 0, 16 - instruction.Count);
            } else {
                systemCore.frame.debugPrint("Shifting left");
                result.append(origin.substring(instruction.Count));
                for (int j = 0; j < instruction.Count; j++) {
                    result.append('0');
                }
            }
        } else {
            systemCore.frame.debugPrint("Shift arithmetically");
            char maskBit = origin.charAt(0);

            if (instruction.RL == 0) {
                systemCore.frame.debugPrint("Shifting right");
                for (int j = 0; j < instruction.Count; j++) {
                    result.append(maskBit);
                }
                result.append(origin, 0, 16 - instruction.Count);
            } else {
                systemCore.frame.debugPrint("Shifting left");
                result.append(origin.substring(instruction.Count));
                for (int j = 0; j < instruction.Count; j++) {
                    result.append('0');
                }
            }
        }

        systemCore.registers.GPRS[instruction.GPR_Index] = Integer.parseInt(result.toString(), 2);

        systemCore.frame.debugPrint(String.format("  Prev binary: %s, result: %s", origin, result));
    }

    //Defining the RRC
    public void rrc(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running RRC");

        String origin = String.format("%16S", Integer.toBinaryString(systemCore.registers.GPRS[instruction.GPR_Index])).replace(' ', '0');
        String result;

        if (instruction.RL == 0) {
            systemCore.frame.debugPrint("Rotating right");
            result = origin.substring(16 - instruction.Count) + origin.substring(0, 16 - instruction.Count);
        } else {
            systemCore.frame.debugPrint("Rotating left");
            result = origin.substring(instruction.Count) + origin.substring(0, instruction.Count);
        }

        systemCore.registers.GPRS[instruction.GPR_Index] = Integer.parseInt(result, 2);

        systemCore.frame.debugPrint(String.format("  Prev binary: %s, result: %s", origin, result));
    }

    public void in(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running IN");

        int devID = instruction.EA;

        char c;

        if (devID == 0) {
            c = systemCore.frame.popKBBuffer();
            systemCore.registers.GPRS[instruction.GPR_Index] = c;
            systemCore.frame.debugPrint(String.format("  Read %c from keyboard, store to gpr%d", c, instruction.GPR_Index));
        } else if (devID == 2) {
            c = systemCore.frame.popCRBuffer();
            systemCore.registers.GPRS[instruction.GPR_Index] = c;
            systemCore.frame.debugPrint(String.format("  Read %c from card reader, store to gpr%d", c, instruction.GPR_Index));
        } else {
            systemCore.frame.debugPrint("  Invalid operands");
        }
    }

    //Defining the OUT
    public void out(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running OUT");

        int devID = instruction.Address;

        if (devID != 1) {
            systemCore.frame.debugPrint("  Invalid operands");
            return;
        }

        if (instruction.I_bit == 1) {
            systemCore.frame.insertPrinterBuffer((char) systemCore.registers.GPRS[instruction.GPR_Index]);
        } else {
            systemCore.frame.pushPrinterBuffer((char) systemCore.registers.GPRS[instruction.GPR_Index]);
        }


        systemCore.frame.debugPrint(String.format("  Print %c to console printer", (char) systemCore.registers.GPRS[instruction.GPR_Index]));
    }

    //Defining the CHK
    public void chk(InstructionComponents instruction) {
        systemCore.frame.debugPrint("Running CHK");

        int devID = instruction.EA;

        if (devID == 0) {
            if (systemCore.frame.isKBBufferEmpty()) {
                systemCore.registers.GPRS[instruction.GPR_Index] = 0;
                systemCore.frame.debugPrint("  No keyboard input to read");
            } else {
                systemCore.registers.GPRS[instruction.GPR_Index] = 1;
                systemCore.frame.debugPrint("  There is keyboard input to read");
            }
        } else if (devID == 2) {
            if (systemCore.frame.isCRBufferEmpty()) {
                systemCore.registers.GPRS[instruction.GPR_Index] = 0;
                systemCore.frame.debugPrint("  No card reader input to read");
            } else {
                systemCore.registers.GPRS[instruction.GPR_Index] = 1;
                systemCore.frame.debugPrint("  There is card reader input to read");
            }
        } else if (devID == 1) {
            systemCore.registers.GPRS[instruction.GPR_Index] = 1;
            systemCore.frame.debugPrint("  Console printer is enabled");
        } else {
            systemCore.registers.GPRS[instruction.GPR_Index] = 0;
        }
    }
}
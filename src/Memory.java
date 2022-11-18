import java.util.Arrays;
import java.util.List;

public class Memory
{
    private final SystemControl systemControl;

    public Memory(SystemControl systemControl)
    {
        this.systemControl = systemControl;
        int size = 2048;
    }

    int size = 2048;
    int[] ProcessorMemory = new int[size];
    int[] ProcessorMemoryTemp;

    public void create_memory(int mem_size)
    {
        size = mem_size;
        ProcessorMemory = new int[size];
        Arrays.fill(ProcessorMemory, 0);

        store_to_memory(2, 1500);
        store_to_memory(4, 1600);
    }

    public void reset_memory()
    {
        ProcessorMemory = new int[size];
        Arrays.fill(ProcessorMemory, 0);


        store_to_memory(2, 1500);
        store_to_memory(4, 1600);
    }

    public void modify_memory(String modification)
    {
        if (modification.equals("expand") && ProcessorMemory.length == 2048)
        {
            ProcessorMemoryTemp = new int[4096];
            for(int i=0; i<ProcessorMemory.length; i++)
            {
                ProcessorMemoryTemp[i] = ProcessorMemory[i];
            }
            ProcessorMemory = ProcessorMemoryTemp;
            size = 4096;
        }
        else if (modification.equals("contract") && ProcessorMemory.length == 4096)
        {
            ProcessorMemoryTemp = new int[2048];
            for(int i=0; i<ProcessorMemoryTemp.length; i++)
            {
                ProcessorMemoryTemp[i] = ProcessorMemory[i];
            }
            ProcessorMemory = ProcessorMemoryTemp;
            size = 2048;
        }
    }

    public int get_from_memory(int addr)
    {
        if (addr > 2047)
        {
            systemControl.frame.debugPrint("Trying to access memory greater than 2047. MFR4 set to 1. Returning 0");
            systemControl.registers.MFR4 = true;
            return 0;
        }

        if (addr < 6)
        {
            systemControl.frame.debugPrint("Trying to access memory greater than 2047.. MFR1 set to 1. Returning 0");
            systemControl.registers.MFR1 = true;
            return 0;
        }

        systemControl.registers.MAR = addr;

        systemControl.registers.MBR = systemControl.cache.read(systemControl.registers.MAR);

        if (systemControl.registers.MBR == -1) {
            systemControl.PrintToDebugConsole(String.format("Cache missed at %H", systemControl.registers.MAR));
            systemControl.registers.MBR = ProcessorMemory[systemControl.registers.MAR];
            systemControl.simulateDelay();
            systemControl.cache.write(systemControl.registers.MAR, systemControl.registers.MBR);
        }

        return systemControl.registers.MBR;
    }

    public void store_to_memory(int addr, int val)
    {
        if (addr > 2047)
        {
            systemControl.frame.debugPrint("Trying to access memory greater than 2047. MFR4 set to 1");
            systemControl.registers.MFR4 = true;
            return;
        }

        if (addr < 6)
        {
            systemControl.frame.debugPrint("Trying to Access Reserved locations. MFR1 set to 1");
            systemControl.registers.MFR1 = true;
            return;
        }


        systemControl.registers.MAR = addr;

        systemControl.registers.MBR = val;

        systemControl.cache.write(systemControl.registers.MAR, systemControl.registers.MBR);

        systemControl.PrintToDebugConsole(String.format("Write %d to %H", val, addr));

        ProcessorMemory[addr] = val;

        systemControl.frame.update_memory_single(val, addr);
    }

    public void clear_memory_address(int addr)
    {
        if (addr > 2047)
        {
            systemControl.frame.debugPrint("Trying to access memory greater than 2047. MFR4 set to 1.");
            systemControl.registers.MFR4 = true;
            return;
        }

        if (addr < 6)
        {
            systemControl.frame.debugPrint("Trying to Access Reserved locations. MFR1 set to 1.");
            systemControl.registers.MFR1 = true;
            return;
        }

        ProcessorMemory[addr] = 0;

        systemControl.frame.update_memory_single(0, addr);
    }

}

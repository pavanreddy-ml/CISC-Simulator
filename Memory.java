import java.util.Arrays;
import java.util.List;

public class Memory
{

    static int size = 2048;
    static int[] ProcessorMemory = new int[size];
    static int[] ProcessorMemoryTemp;

    public static void create_memory(int mem_size)
    {
        size = mem_size;
        ProcessorMemory = new int[size];
    }

    public static void reset_memory()
    {
        ProcessorMemory = new int[size];
    }

    public static void modify_memory(String modification)
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

    public static int get_from_memory(int addr)
    {
        Registers.MAR = addr;

        Registers.MBR = Cache.read(Registers.MAR);

        if (Registers.MBR == -1) {
            SystemControl.PrintToDebugConsole(String.format("Cache missed at %H", Registers.MAR));
            Registers.MBR = ProcessorMemory[Registers.MAR];
            SystemControl.simulateDelay();
            Cache.write(Registers.MAR, Registers.MBR);
        }

        return Registers.MBR;
    }

    public static void store_to_memory(int addr, int val)
    {
        Registers.MAR = addr;

        Registers.MBR = val;

        Cache.write(Registers.MAR, Registers.MBR);

        SystemControl.PrintToDebugConsole(String.format("Write %d to %H", val, addr));

        ProcessorMemory[addr] = val;
    }

    public static void clear_memory_address(int addr)
    {
        ProcessorMemory[addr] = 0;
    }

}

public class Memory {

    public SystemCore systemCore;
    public int[] ProcessorMemory;

    public Memory(SystemCore systemCore, int size) {
        this.systemCore = systemCore;
        ProcessorMemory = new int[size];
    }

    public int ReadFromMemory(int addr) {
        systemCore.registers.MAR = addr;

        systemCore.registers.MBR = systemCore.cache.CacheRead(systemCore.registers.MAR);
        if (systemCore.registers.MBR == -1) {
            systemCore.frame.PrintToDebugConsole(String.format("Cache missed at %H", systemCore.registers.MAR));
            systemCore.registers.MBR = ProcessorMemory[systemCore.registers.MAR];
            systemCore.systemFunctions.Delay();
            systemCore.cache.CacheWrite(systemCore.registers.MAR, systemCore.registers.MBR);
        }


        return systemCore.registers.MBR;
    }

    public void WriteToMemory(int addr, int content) {
        systemCore.registers.MAR = addr;

        systemCore.registers.MBR = content;

        systemCore.cache.CacheWrite(systemCore.registers.MAR, systemCore.registers.MBR);

        systemCore.frame.PrintToDebugConsole(String.format("Write %d to %H", content, addr));
    }
}

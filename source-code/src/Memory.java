public class Memory {

    public SystemCore systemCore;
    public int[] ProcessorMemory;

    public Memory(SystemCore systemCore, int size) {
        this.systemCore = systemCore;
        ProcessorMemory = new int[size];
    }

    public int ReadFromMemory(int addr) {
        systemCore.registers.MAR = addr;

        systemCore.registers.MBR = systemCore.cache.read(systemCore.registers.MAR);
        if (systemCore.registers.MBR == -1) {
            systemCore.frame.debugPrint(String.format("Cache missed at %H", systemCore.registers.MAR));
            systemCore.registers.MBR = ProcessorMemory[systemCore.registers.MAR];
            systemCore.systemFunctions.Delay();
            systemCore.cache.write(systemCore.registers.MAR, systemCore.registers.MBR);
        }


        return systemCore.registers.MBR;
    }

    public void WriteToMemory(int addr, int content) {
        systemCore.registers.MAR = addr;

        systemCore.registers.MBR = content;

        systemCore.cache.write(systemCore.registers.MAR, systemCore.registers.MBR);

        systemCore.frame.debugPrint(String.format("Write %d to %H", content, addr));
    }
}

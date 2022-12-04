public class SystemFunctions {

    public SystemCore systemCore;

    public SystemFunctions(SystemCore systemCore) {
        this.systemCore = systemCore;
    }


    public void Delay() {
        try {
            Thread.sleep(1000 / systemCore.systemSettings.Speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Reset() {
        systemCore.registers.reset_registers();
        systemCore.memory = new Memory(systemCore, 4096);
    }

    public void StartSystem() {
        systemCore.systemSettings.Idle = false;
        systemCore.systemSettings.Running = true;

        Thread cpuThread = new Thread(() -> {
            while (systemCore.systemSettings.Running) {
                FetchInstruction();
                systemCore.frame.update();
                DecodeInstruction();
                systemCore.frame.update();
                systemCore.utils.exec(systemCore.i);
                systemCore.frame.update();
            }
        });

        cpuThread.start();
    }

    public void StopSystem() {
        systemCore.systemSettings.Running = false;
        systemCore.frame.update();
    }

    public void SingleStep() {
        Thread cpuThread = new Thread(() -> {
            FetchInstruction();
            systemCore.frame.update();
            DecodeInstruction();
            systemCore.frame.update();
            systemCore.utils.exec(systemCore.i);
            systemCore.frame.update();
        });
        cpuThread.start();
    }

    public void FetchInstruction() {
        systemCore.registers.MAR = systemCore.registers.PC;
        systemCore.registers.IR = systemCore.memory.ReadFromMemory(systemCore.registers.MAR);
        systemCore.registers.PC++;

        if (systemCore.registers.PC > 4095) {
            systemCore.registers.PC = 0;
        }
        Delay();
    }

    public void DecodeInstruction() {
        systemCore.i = new InstructionComponents(systemCore.registers.IR);
        Delay();
    }
}

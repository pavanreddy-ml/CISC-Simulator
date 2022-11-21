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

    public void reset() {
        systemCore.registers.reset_registers();
        systemCore.memory = new Memory(systemCore, 4096);
    }

    public void startMachine() {
        systemCore.systemSettings.Idle = false;
        systemCore.systemSettings.Running = true;

        Thread cpuThread = new Thread(() -> {
            while (systemCore.systemSettings.Running) {
                fetch();
                systemCore.frame.update();
                decode();
                systemCore.frame.update();
                systemCore.utils.exec(systemCore.i);
                systemCore.frame.update();
            }
        });

        cpuThread.start();
    }

    public void stopMachine() {
        systemCore.systemSettings.Running = false;
        systemCore.frame.update();
    }

    public void runSingleStep() {
        Thread cpuThread = new Thread(() -> {
            fetch();
            systemCore.frame.update();
            decode();
            systemCore.frame.update();
            systemCore.utils.exec(systemCore.i);
            systemCore.frame.update();
        });
        cpuThread.start();
    }

    public void fetch() {
        systemCore.registers.MAR = systemCore.registers.PC;
        systemCore.registers.IR = systemCore.memory.ReadFromMemory(systemCore.registers.MAR);
        systemCore.registers.PC++;

        if (systemCore.registers.PC > 4095) {
            systemCore.registers.PC = 0;
        }
        Delay();
    }

    public void decode() {
        systemCore.i = new InstructionComponents(systemCore.registers.IR);
        Delay();
    }
}

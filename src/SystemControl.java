import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SystemControl
{
    public boolean Running;
    public boolean Idle;
    public boolean Debug = true;
    public int CPUSpeed = 1000;


    Memory memory = new Memory(this);
    Cache cache = new Cache(this, 16);
    ALU alu = new ALU(this);
    Utils utils = new Utils(this);
    Registers registers = new Registers(this);


    public Frame frame;



    public void bindGUI(Frame frame) {
        this.frame = frame;
    }


    public void PrintToDebugConsole(String s) {
        if (!Debug) {
            return;
        }

        frame.debugPrint(s);
    }


    public int getHz() {
        return CPUSpeed;
    }

    //Setting the CPU Speed in Hertz
    public void setHz(int Speed) {
        CPUSpeed = Speed;
    }

    //Setting the CPU Speed to 1000 in Hertz
    public void simulateDelay() {
        try {
            Thread.sleep(1000 / CPUSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void reset () {
        registers.reset_registers();

        memory.create_memory(2096);

        frame.update_memory();
    }


    public void startMachine() {
        Idle = false;
        Running = true;

        Thread cpuThread = new Thread(() -> {
            while (Running) {
                fetch();
                frame.update();
                decode();
                frame.update();
                utils.execute();
                frame.update();
            }
        });

        cpuThread.start();

        frame.update_memory();
    }


    public void stopMachine() {
        Running = false;

        frame.update();
    }

    //Updates single instructions on Main GUI
    public void runSingleStep() {
        Thread cpuThread = new Thread(() -> {
            fetch();
            frame.update();
            decode();
            frame.update();
            utils.execute();
            frame.update();
        });
        cpuThread.start();
    }


    public void loadExternalProgram(String s) {
        File file = new File(s); // IPL.txt should be right next to the main class or the jar
        String line;
        int addr;
        int content;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            line = br.readLine(); // Decode line by line
            while (line != null) {
                if (line.equals("")) {
                    line = br.readLine();
                    continue;
                }
                if (line.charAt(0) == '#') {
                    line = br.readLine();
                    continue;
                }
                addr = Integer.parseInt(line.substring(0, 4), 16); // Content must be in hex
                content = Integer.parseInt(line.substring(5, 9), 16);
                memory.ProcessorMemory[addr] = content;
                line = br.readLine();
            }
            PrintToDebugConsole(String.format("Program %s loaded", s));
        } catch (IOException e) {
            PrintToDebugConsole(String.format("Program %s doesn't exist", s));
        }
    }


    public void fetch() {
        registers.MAR = registers.PC;
        registers.IR = memory.get_from_memory(registers.MAR);
        registers.PC++;

        if (registers.PC > 4095) {
            registers.PC = 0;
        }
        simulateDelay();
    }

    public void decode() {
        utils.split_instructions(registers.IR);
        simulateDelay();
    }




}

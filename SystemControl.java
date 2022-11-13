import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SystemControl
{
    public static boolean Running;
    public static boolean Idle;
    public static boolean Debug = true;
    public static int CPUSpeed = 1000;

    public static void PrintToDebugConsole(String s) {
        if (!Debug) {
            return;
        }

        DebugGUI.debugPrint(s);
    }


    public static int getHz() {
        return CPUSpeed;
    }

    //Setting the CPU Speed in Hertz
    public static void setHz(int Speed) {
        CPUSpeed = Speed;
    }

    //Setting the CPU Speed to 1000 in Hertz
    public static void simulateDelay() {
        try {
            Thread.sleep(1000 / CPUSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void reset () {
        Registers.reset_registers();

        Memory.create_memory(2096);
    }


    public static void startMachine() {
        Idle = false;
        Running = true;

        Thread cpuThread = new Thread(() -> {
            while (Running) {
                fetch();
                MainGUI.update();
                decode();
                MainGUI.update();
                Utils.execute();
                MainGUI.update();
            }
        });

        cpuThread.start();
    }


    public static void stopMachine() {
        Running = false;

        MainGUI.update();
    }

    //Updates single instructions on Main GUI
    public static void runSingleStep() {
        Thread cpuThread = new Thread(() -> {
            fetch();
            MainGUI.update();
            decode();
            MainGUI.update();
            Utils.execute();
            MainGUI.update();
        });
        cpuThread.start();
    }

    public static void runSingleInstruction(int ins) {
        Thread cpuThread = new Thread(() -> {
            Utils.split_instructions(ins);
            simulateDelay();
            Utils.execute();
            MainGUI.update();
        });
        cpuThread.start();
    }


    public static void loadExternalProgram(String s) {
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
                Memory.ProcessorMemory[addr] = content;
                line = br.readLine();
            }
            PrintToDebugConsole(String.format("Program %s loaded", s));
        } catch (IOException e) {
            PrintToDebugConsole(String.format("Program %s doesn't exist", s));
        }
    }


    public static void fetch() {
        Registers.MAR = Registers.PC;
        Registers.IR = Memory.get_from_memory(Registers.MAR);
        Registers.PC++;

        if (Registers.PC > 4095) {
            Registers.PC = 0;
        }
        simulateDelay();
    }

    public static void decode() {
        Utils.split_instructions(Registers.IR);
        simulateDelay();
    }




}

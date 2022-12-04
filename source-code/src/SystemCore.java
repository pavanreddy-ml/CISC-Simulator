public class SystemCore {
    Cache cache = new Cache(this, 16);

    Memory memory = new Memory(this, 4096);

    Registers registers = new Registers();

    ALU alu = new ALU(this);

    Utils utils = new Utils(this);

    public Frame frame;

    public InstructionComponents i;

    SystemSettings systemSettings = new SystemSettings();

    SystemFunctions systemFunctions = new SystemFunctions(this);

    public void bindGUI(Frame frame) {
        this.frame = frame;
    }
}
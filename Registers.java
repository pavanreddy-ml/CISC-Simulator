public class Registers
{
    private final SystemControl systemControl;

    public Registers(SystemControl systemControl)
    {
        this.systemControl = systemControl;
    }

    public int[] GPRS = {0, 0, 0, 0};
    public int[] IXRS = {0, 0, 0};
    public int IR = 0;
    public int MAR = 0;
    public int MBR = 0;
    public int MFR = 0;
    public int PC = 0;
    public boolean CC1 = false;
    public boolean CC2 = false;
    public boolean CC3 = false;
    public boolean CC4 = false;


    public void reset_registers()
    {
        GPRS = new int[]{0, 0, 0, 0};
        IXRS = new int[]{0, 0, 0};
        IR = 0;
        MAR = 0;
        MBR = 0;
        MFR = 0;
        PC = 0;
        CC1 = false;
        CC2 = false;
        CC3 = false;
        CC4 = false;
    }

}

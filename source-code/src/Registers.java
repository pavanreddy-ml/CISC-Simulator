public class Registers {

    public int[] GPRS = {0, 0, 0, 0};
    public int[] IXRS = {0, 0, 0};
    public int IR = 0;
    public int MAR = 0;
    public int MBR = 0;
    public int PC = 0;
    public int CC = 0;
    public int MFR = 0;
    public int[] FRS = {0, 0};


    public void reset_registers()
    {
        GPRS = new int[]{0, 0, 0, 0};
        IXRS = new int[]{0, 0, 0};
        IR = 0;
        MAR = 0;
        MBR = 0;
        PC = 0;
    }

}

public class Registers
{
    public static int[] GPRS = {0, 0, 0, 0};
    public static int[] IXRS = {0, 0, 0};
    public static int IR = 0;
    public static int MAR = 0;
    public static int MBR = 0;
    public static int MFR = 0;
    public static int PC = 0;
    public static boolean CC1 = false;
    public static boolean CC2 = false;
    public static boolean CC3 = false;
    public static boolean CC4 = false;


    public static void reset_registers()
    {
        Registers.GPRS = new int[]{0, 0, 0, 0};
        Registers.IXRS = new int[]{0, 0, 0};
        Registers.IR = 0;
        Registers.MAR = 0;
        Registers.MBR = 0;
        Registers.MFR = 0;
        Registers.PC = 0;
        Registers.CC1 = false;
        Registers.CC2 = false;
        Registers.CC3 = false;
        Registers.CC4 = false;
    }

}
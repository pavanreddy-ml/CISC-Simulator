import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SystemControl systemControl = new SystemControl();

        //This is the GUI for the main simulator
        Frame frame = new Frame();

        //This is the backend part connecting to Front-End
        frame.bindSystemControl(systemControl);

        //linking and calling the front end
        systemControl.bindGUI(frame);

        //Heading to the 3 Frames
        JFrame mainFrame = new JFrame("Simulator");

        //Exit Notes for Simulator GUI
        mainFrame.setContentPane(frame.getMainPanel());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        frame.update_memory();

        //Messages in Input/Output Panel
        frame.debugPrint("Simulator Started");
        frame.debugPrint("By default, PC Started at 0*0");
        frame.debugPrint(String.format("CPU Speed = %d Hertz", systemControl.getHz()));

    }
}

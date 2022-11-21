import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SystemCore systemCore = new SystemCore();

        //This is the GUI for the main simulator
        Frame frame = new Frame();

        //This is the backend part connecting to Front-End
        frame.bindSystemControl(systemCore);

        //Linking and calling the front end
        systemCore.bindGUI(frame);

        //Heading to the 3 Frames
        JFrame mainFrame = new JFrame("Simulator");

        //Exit Notes for Simulator GUI
        mainFrame.setContentPane(frame.getMainPanel());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}

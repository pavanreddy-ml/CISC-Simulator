import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SystemCore systemCore = new SystemCore();

        //Create a Frame Object
        Frame frame = new Frame();

        //Linking the Frame with SystemCore
        frame.bindSystemCore(systemCore);

        //Linking the Frame with SystemCore
        systemCore.bindGUI(frame);

        // Title of the Simulator
        JFrame mainFrame = new JFrame("Simulator");


        mainFrame.setContentPane(frame.GetFrame());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}

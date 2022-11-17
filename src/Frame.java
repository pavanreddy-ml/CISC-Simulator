import javax.swing.*;
import java.util.Objects;
import java.util.Vector;
import java.io.File;

public class Frame {
    public final Frame frame = this;
    private SystemControl systemControl;

    private JButton gpr0LDButton;
    private JTextField gpr0HexTextField;
    private JTextField gpr0BinTextField;
    private JButton gpr1LDButton;
    private JTextField gpr1BinTextField;
    private JTextField gpr1HexTextField;
    private JButton gpr2LDButton;
    private JTextField gpr2BinTextField;
    private JTextField gpr2HexTextField;
    private JButton gpr3LDButton;
    private JTextField gpr3BinTextField;
    private JTextField gpr3HexTextField;
    private JButton ixr1LDButton;
    private JTextField ixr1HexTextField;
    private JTextField ixr1BinTextField;
    private JButton ixr2LDButton;
    private JTextField ixr2BinTextField;
    private JTextField ixr2HexTextField;
    private JButton ixr3LDButton;
    private JTextField ixr3BinTextField;
    private JTextField ixr3HexTextField;
    private JButton mbrLDButton;
    private JTextField mbrBinTextField;
    private JTextField mbrHexTextField;
    private JButton marLDButton;
    private JTextField marBinTextField;
    private JTextField marHexTextField;
    private JButton pcLDButton;
    private JTextField pcBinTextField;
    private JTextField pcHexTextField;
    private JTextField irBinTextField;
    private JTextField irHexTextField;
    private JTextField ccHexTextField;
    private JRadioButton CC4RadioButton;
    private JRadioButton CC3RadioButton;
    private JRadioButton CC2RadioButton;
    private JRadioButton CC1RadioButton;
    private JTextField mfrHexTextField;
    private JRadioButton MFR4RadioButton;
    private JRadioButton MFR3RadioButton;
    private JRadioButton MFR2RadioButton;
    private JRadioButton MFR1RadioButton;
    private JPanel Memory;
    private JScrollPane MemoryListScroll;
    private JPanel MemoryFullList;
    private JLabel MemoryAddressLabel;
    private JList MemoryValueList;
    private JList MemoryHexValueList;
    private JList MemoryAssembleCodeList;
    private JLabel MemoryValueLabel;
    private JLabel MemoryHexValue;
    private JLabel MemoryAssembleCode;
    private JList MemoryAddressList;
    private JTextField Cache0;
    private JTextField kbInputField;
    private JButton kbInputButton;
    private JButton crInputButton;
    private JTextArea kbBuffer;
    private JTextArea crBuffer;
    private JTextArea printerBuffer;
    private JScrollPane consoleScrollPanel;
    private JTextArea debugConsoleOutput;
    private JTextField cpuHzTextField;
    private JButton setButton;
    private JButton runButton;
    private JButton stopButton;
    private JTextField inputBinTextField;
    private JTextField inputHexTextField;
    private JButton IPLButton;
    private JButton prog1Button;
    private JButton prog2Button;
    private JButton singleStepButton;
    private JPanel mainPanel;
    private JCheckBox runningCheckBox;
    private JCheckBox haltedCheckBox;
    private JTextField Cache1;
    private JTextField Cache2;
    private JTextField Cache3;
    private JTextField Cache4;
    private JTextField Cache5;
    private JTextField Cache6;
    private JTextField Cache7;
    private JTextField Cache8;
    private JTextField Cache9;
    private JTextField Cache10;
    private JTextField Cache11;
    private JTextField Cache12;
    private JTextField Cache13;
    private JTextField Cache14;
    private JTextField Cache15;
    private JPanel CardReaderFileText;
    private JFileChooser CardReadeFile;




    private Vector AddressList = new Vector();
    private Vector memoryValue = new Vector();
    private Vector memoryHexValue = new Vector();
    private Vector memoryAssembleCode = new Vector();



    public Frame() {


        runButton.addActionListener(e -> systemControl.startMachine());

        stopButton.addActionListener(e -> systemControl.stopMachine());

        inputBinTextField.addActionListener(e -> {
            try {
                int inputValue = Integer.parseUnsignedInt(inputBinTextField.getText(), 2);
                inputHexTextField.setText(Integer.toHexString(inputValue));
            } catch (NumberFormatException ex) {
                inputBinTextField.setText("");
                inputHexTextField.setText("");
                debugPrint("Invalid binary input");
            }
        });

        inputHexTextField.addActionListener(e -> {
            try {
                int inputValue = Integer.parseUnsignedInt(inputHexTextField.getText(), 16);
                inputBinTextField.setText(Integer.toBinaryString(inputValue));
            } catch (NumberFormatException ex) {
                inputBinTextField.setText("");
                inputHexTextField.setText("");
                debugPrint("Invalid binary input");
            }
        });

        gpr0LDButton.addActionListener(e -> {
            int inputValue;
            if (inputBinTextField.getText().equals("")) {
                inputValue = Integer.parseInt(inputHexTextField.getText(), 16);
            } else {
                inputValue = Integer.parseInt(inputBinTextField.getText(), 2);
            }
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                debugPrint("Input is invalid for GPR0");
                return;
            }
            systemControl.registers.GPRS[0] = inputValue;
            frame.update();
        });

        gpr1LDButton.addActionListener(e -> {
            int inputValue;
            if (inputBinTextField.getText().equals("")) {
                inputValue = Integer.parseInt(inputHexTextField.getText(), 16);
            } else {
                inputValue = Integer.parseInt(inputBinTextField.getText(), 2);
            }
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                debugPrint("Input is invalid for GPR1");
                return;
            }
            systemControl.registers.GPRS[1] = inputValue;
            frame.update();
        });

        gpr2LDButton.addActionListener(e -> {
            int inputValue;
            if (inputBinTextField.getText().equals("")) {
                inputValue = Integer.parseInt(inputHexTextField.getText(), 16);
            } else {
                inputValue = Integer.parseInt(inputBinTextField.getText(), 2);
            }
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                debugPrint("Input is invalid for GPR2");
                return;
            }
            systemControl.registers.GPRS[2] = inputValue;
            frame.update();
        });

        gpr3LDButton.addActionListener(e -> {
            int inputValue;
            if (inputBinTextField.getText().equals("")) {
                inputValue = Integer.parseInt(inputHexTextField.getText(), 16);
            } else {
                inputValue = Integer.parseInt(inputBinTextField.getText(), 2);
            }
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                debugPrint("Input is invalid for GPR3");
                return;
            }
            systemControl.registers.GPRS[3] = inputValue;
            frame.update();
        });

        ixr1LDButton.addActionListener(e -> {
            int inputValue;
            if (inputBinTextField.getText().equals("")) {
                inputValue = Integer.parseInt(inputHexTextField.getText(), 16);
            } else {
                inputValue = Integer.parseInt(inputBinTextField.getText(), 2);
            }
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                debugPrint("Input is invalid for IXR1");
                return;
            }
            systemControl.registers.IXRS[0] = inputValue;
            frame.update();
        });

        ixr2LDButton.addActionListener(e -> {
            int inputValue;
            if (inputBinTextField.getText().equals("")) {
                inputValue = Integer.parseInt(inputHexTextField.getText(), 16);
            } else {
                inputValue = Integer.parseInt(inputBinTextField.getText(), 2);
            }
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                debugPrint("Input is invalid for IXR2");
                return;
            }
            systemControl.registers.IXRS[1] = inputValue;
            frame.update();
        });

        ixr3LDButton.addActionListener(e -> {
            int inputValue;
            if (inputBinTextField.getText().equals("")) {
                inputValue = Integer.parseInt(inputHexTextField.getText(), 16);
            } else {
                inputValue = Integer.parseInt(inputBinTextField.getText(), 2);
            }
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                debugPrint("Input is invalid for IXR3");
                return;
            }
            systemControl.registers.IXRS[2] = inputValue;
            frame.update();
        });

        pcLDButton.addActionListener(e -> {
            int inputValue;
            if (inputBinTextField.getText().equals("")) {
                inputValue = Integer.parseInt(inputHexTextField.getText(), 16);
            } else {
                inputValue = Integer.parseInt(inputBinTextField.getText(), 2);
            }
            if (inputValue > (Math.pow(2, 12) - 1) || inputValue < 0) {
                debugPrint("Input is invalid for PC");
                return;
            }
            systemControl.registers.PC = inputValue;
            frame.update();
        });

        marLDButton.addActionListener(e -> {
            int inputValue;
            if (inputBinTextField.getText().equals("")) {
                inputValue = Integer.parseInt(inputHexTextField.getText(), 16);
            } else {
                inputValue = Integer.parseInt(inputBinTextField.getText(), 2);
            }
            if (inputValue > (Math.pow(2, 12) - 1) || inputValue < 0) {
                debugPrint("Input is invalid for MAR");
                return;
            }
            systemControl.registers.MAR = inputValue;
            frame.update();
        });

        mbrLDButton.addActionListener(e -> {
            int inputValue;
            if (inputBinTextField.getText().equals("")) {
                inputValue = Integer.parseInt(inputHexTextField.getText(), 16);
            } else {
                inputValue = Integer.parseInt(inputBinTextField.getText(), 2);
            }
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                debugPrint("Input is invalid for MBR");
                return;
            }
            systemControl.registers.MBR = inputValue;
            frame.update();
        });

        IPLButton.addActionListener(e -> systemControl.loadExternalProgram("IPL.txt"));

        prog1Button.addActionListener(e -> {
            systemControl.reset();
            // Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            // debugPrint(path.toString());
            systemControl.loadExternalProgram("program1.txt");
            systemControl.registers.PC = 0x100;
            systemControl.cache.flush();
            update();
            systemControl.PrintToDebugConsole("PC Set to 0x100");
        });

        prog2Button.addActionListener(e ->
        {
            systemControl.reset();
            systemControl.loadExternalProgram("program2.txt");
            systemControl.registers.PC = 0x12A;
            systemControl.cache.flush();
            update();
            systemControl.PrintToDebugConsole("PC Set to 0x12A");
        });

        singleStepButton.addActionListener(e -> {
            systemControl.runSingleStep();
            debugPrint("Running single step");
        });














        kbInputButton.addActionListener(e -> {
            kbBuffer.append(kbInputField.getText());
            kbBuffer.append("\0");

            kbBuffer.setCaretPosition(kbBuffer.getDocument().getLength());
        });

        kbInputField.addActionListener(e -> {
            kbBuffer.append(kbInputField.getText());
            kbBuffer.append("\0");

            kbBuffer.setCaretPosition(kbBuffer.getDocument().getLength());
        });

        crInputButton.addActionListener(e -> {
            crBuffer.append(kbInputField.getText());
            crBuffer.append("\0");

            crBuffer.setCaretPosition(crBuffer.getDocument().getLength());
        });

    }



    public char popKBBuffer() {
        String s = kbBuffer.getText();
        char c = '\0';
        if (!s.equals("")) {
            c = s.charAt(0);
            s = s.substring(1);
        }
        kbBuffer.setText(s);

        return c;
    }

    public boolean isKBBufferEmpty() {
        return kbBuffer.getText().equals("");
    }

    public boolean isCRBufferEmpty() {
        return crBuffer.getText().equals("");
    }

    public char popCRBuffer() {
        String s = crBuffer.getText();
        char c = '\0';
        if (!s.equals("")) {
            c = s.charAt(0);
            s = s.substring(1);
        }
        crBuffer.setText(s);

        return c;
    }

    public void pushPrinterBuffer(char c) {
        String s = printerBuffer.getText();

        s = s + c;
        printerBuffer.setText(s);

        printerBuffer.setCaretPosition(printerBuffer.getDocument().getLength());
    }

    public void insertPrinterBuffer(char c) {
        String s = printerBuffer.getText();

        String[] split = s.split("\n");

        split[split.length - 1] = c + split[split.length - 1];

        StringBuilder ns = new StringBuilder();

        for (String value : split) {
            ns.append(value);
            ns.append("\n");
        }
        printerBuffer.setText(ns.toString());

        printerBuffer.setCaretPosition(printerBuffer.getDocument().getLength());
    }












    public void debugPrint(String s) {
        debugConsoleOutput.append(s);
        debugConsoleOutput.append("\n");

        debugConsoleOutput.setCaretPosition(debugConsoleOutput.getDocument().getLength());
    }














    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    public void bindSystemControl(SystemControl systemControl) {
        this.systemControl = systemControl;
    }






    public void update_cache_hit_miss(int cache_loc, int cache_value, String hit_miss)
    {

    }

    public void update_memory()
    {
        for (int i =0; i < systemControl.memory.ProcessorMemory.length; i++)
        {
            AddressList.add(String.format("%4S", Integer.toHexString(i)).replace(' ', '0'));
            memoryValue.add(String.format("%16S", Integer.toBinaryString(systemControl.memory.ProcessorMemory[i])).replace(' ', '0'));
            memoryHexValue.add(String.format("%4S", Integer.toHexString(systemControl.memory.ProcessorMemory[i])).replace(' ', '0'));
            memoryAssembleCode.add("NONE");
        }


        MemoryAddressList.setListData(AddressList);
        MemoryValueList.setListData(memoryValue);
        MemoryHexValueList.setListData(memoryHexValue);
        MemoryAssembleCodeList.setListData(memoryAssembleCode);


    }


    public void update_memory_single(int Value, int Address)
    {
        memoryValue.setElementAt(String.format("%16S", Integer.toBinaryString(Value)).replace(' ', '0'), Address);
        memoryHexValue.setElementAt(String.format("%4S", Integer.toHexString(Value)).replace(' ', '0'), Address);

        MemoryAddressList.setListData(AddressList);
        MemoryValueList.setListData(memoryValue);
        MemoryHexValueList.setListData(memoryHexValue);
        MemoryAssembleCodeList.setListData(memoryAssembleCode);
    }


    public void update_registers()
    {
        CC1RadioButton.setSelected(systemControl.registers.CC4);
        CC2RadioButton.setSelected(systemControl.registers.CC3);
        CC3RadioButton.setSelected(systemControl.registers.CC2);
        CC4RadioButton.setSelected(systemControl.registers.CC1);

        MFR1RadioButton.setSelected(systemControl.registers.MFR1);
        MFR2RadioButton.setSelected(systemControl.registers.MFR2);
        MFR3RadioButton.setSelected(systemControl.registers.MFR3);
        MFR4RadioButton.setSelected(systemControl.registers.MFR4);


        gpr0BinTextField.setText(String.format("%16S", Integer.toBinaryString(systemControl.registers.GPRS[0])).replace(' ', '0'));
        gpr1BinTextField.setText(String.format("%16S", Integer.toBinaryString(systemControl.registers.GPRS[1])).replace(' ', '0'));
        gpr2BinTextField.setText(String.format("%16S", Integer.toBinaryString(systemControl.registers.GPRS[2])).replace(' ', '0'));
        gpr3BinTextField.setText(String.format("%16S", Integer.toBinaryString(systemControl.registers.GPRS[3])).replace(' ', '0'));
        irBinTextField.setText(String.format("%16S", Integer.toBinaryString(systemControl.registers.IR)).replace(' ', '0'));
        ixr1BinTextField.setText(String.format("%16S", Integer.toBinaryString(systemControl.registers.IXRS[0])).replace(' ', '0'));
        ixr2BinTextField.setText(String.format("%16S", Integer.toBinaryString(systemControl.registers.IXRS[1])).replace(' ', '0'));
        ixr3BinTextField.setText(String.format("%16S", Integer.toBinaryString(systemControl.registers.IXRS[2])).replace(' ', '0'));
        marBinTextField.setText(String.format("%12S", Integer.toBinaryString(systemControl.registers.MAR)).replace(' ', '0'));
        mbrBinTextField.setText(String.format("%16S", Integer.toBinaryString(systemControl.registers.MBR)).replace(' ', '0'));
        pcBinTextField.setText(String.format("%12S", Integer.toBinaryString(systemControl.registers.PC)).replace(' ', '0'));

        gpr0HexTextField.setText(String.format("%4S", Integer.toHexString(systemControl.registers.GPRS[0])).replace(' ', '0'));
        gpr1HexTextField.setText(String.format("%4S", Integer.toHexString(systemControl.registers.GPRS[1])).replace(' ', '0'));
        gpr2HexTextField.setText(String.format("%4S", Integer.toHexString(systemControl.registers.GPRS[2])).replace(' ', '0'));
        gpr3HexTextField.setText(String.format("%4S", Integer.toHexString(systemControl.registers.GPRS[3])).replace(' ', '0'));
        irHexTextField.setText(String.format("%4S", Integer.toHexString(systemControl.registers.IR)).replace(' ', '0'));
        ixr1HexTextField.setText(String.format("%4S", Integer.toHexString(systemControl.registers.IXRS[0])).replace(' ', '0'));
        ixr2HexTextField.setText(String.format("%4S", Integer.toHexString(systemControl.registers.IXRS[1])).replace(' ', '0'));
        ixr3HexTextField.setText(String.format("%4S", Integer.toHexString(systemControl.registers.IXRS[2])).replace(' ', '0'));
        marHexTextField.setText(String.format("%4S", Integer.toHexString(systemControl.registers.MAR)).replace(' ', '0'));
        mbrHexTextField.setText(String.format("%4S", Integer.toHexString(systemControl.registers.MBR)).replace(' ', '0'));
        pcHexTextField.setText(String.format("%4S", Integer.toHexString(systemControl.registers.PC)).replace(' ', '0'));

        runningCheckBox.setSelected(systemControl.Running);
        haltedCheckBox.setSelected(systemControl.Idle);
    }

    public void update_cache()
    {
        try {
            Cache0.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(0))).replace(' ', '0'));
            Cache1.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(1))).replace(' ', '0'));
            Cache2.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(2))).replace(' ', '0'));
            Cache3.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(3))).replace(' ', '0'));
            Cache4.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(4))).replace(' ', '0'));
            Cache5.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(5))).replace(' ', '0'));
            Cache6.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(6))).replace(' ', '0'));
            Cache7.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(7))).replace(' ', '0'));
            Cache8.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(8))).replace(' ', '0'));
            Cache9.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(9))).replace(' ', '0'));
            Cache10.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(10))).replace(' ', '0'));
            Cache11.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(11))).replace(' ', '0'));
            Cache12.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(12))).replace(' ', '0'));
            Cache13.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(13))).replace(' ', '0'));
            Cache14.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(14))).replace(' ', '0'));
            Cache15.setText(String.format("%16S", Integer.toBinaryString(systemControl.cache.contentList.get(15))).replace(' ', '0'));
        } catch (Exception e)
        {
            assert true;
        }

    }


    public void update()
    {
        update_registers();
        update_cache();
    }

}





import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;
import java.io.File;

public class Frame {
    public final Frame frame = this;
    private SystemCore systemCore;

    private JButton GPR0Load;
    private JTextField GPR0HexTF;
    private JTextField GPR0BinTF;
    private JButton GPR1Load;
    private JTextField GPR1BinTF;
    private JTextField GPR1HexTF;
    private JButton GPR2Load;
    private JTextField GPR2BinTF;
    private JTextField GPR2HexTF;
    private JButton GPR3Load;
    private JTextField GPR3BinTF;
    private JTextField GPR4HexTF;
    private JButton IXR1Load;
    private JTextField IXR1HexField;
    private JTextField IXR1BinField;
    private JButton IXR2Load;
    private JTextField IXR2BinField;
    private JTextField IXR2HexField;
    private JButton IXR3Load;
    private JTextField IXR3BinField;
    private JTextField IXR3HexField;
    private JButton MBRLoad;
    private JTextField MBRBinTF;
    private JTextField MBRHexTF;
    private JButton MARLoad;
    private JTextField MARBinTF;
    private JTextField MARHexTF;
    private JButton PCLoad;
    private JTextField PCBinTF;
    private JTextField PCHexTF;
    private JTextField IRBinTF;
    private JTextField IRHexTF;
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
    private JTextField KeyboardIP;
    private JButton KeyBoardInputButton;
    private JButton CardReaderSelectFile;
    private JTextArea KeyboardBuffer;
    private JTextArea CardReaderBuffer;
    private JTextArea OPConsole;
    private JScrollPane consoleScrollPanel;
    private JTextArea DebugConsole;
    private JTextField cpuHzTextField;
    private JButton setButton;
    private JButton runButton;
    private JButton stopButton;
    private JTextField inputDecTextField;
    private JButton IPLButton;
    private JButton LoadProgramBut;
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
    private JLabel CRFileName;
    private JCheckBox DebugCheck;
    private JTextField FR1BinField;
    private JTextField FR2BinField;
    private JTextField FR1HexField;
    private JTextField FR2HexField;
    private JButton FR1Load;
    private JButton FR2Load;
    private JFileChooser CardReadeFile;

    private Color defaultcolor;






    private Vector AddressList = new Vector();
    private Vector memoryValue = new Vector();
    private Vector memoryHexValue = new Vector();
    private Vector memoryAssembleCode = new Vector();



    public Frame() {

        JFileChooser MEMFileChooser = new JFileChooser();

        defaultcolor = Cache0.getBackground();

        runButton.addActionListener(e -> systemCore.systemFunctions.StartSystem());

        stopButton.addActionListener(e -> systemCore.systemFunctions.StopSystem());

        inputDecTextField.addActionListener(e -> {
            try {
                int inputValue = Integer.parseUnsignedInt(inputDecTextField.getText());
            } catch (NumberFormatException ex) {
                inputDecTextField.setText("");
                PrintToDebugConsole("Invalid binary input");
            }
        });


        GPR0Load.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for GPR0");
                return;
            }
            systemCore.registers.GPRS[0] = inputValue;
            frame.update();
        });

        GPR1Load.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for GPR1");
                return;
            }
            systemCore.registers.GPRS[1] = inputValue;
            frame.update();
        });

        GPR2Load.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for GPR2");
                return;
            }
            systemCore.registers.GPRS[2] = inputValue;
            frame.update();
        });

        GPR3Load.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for GPR3");
                return;
            }
            systemCore.registers.GPRS[3] = inputValue;
            frame.update();
        });

        IXR1Load.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for IXR1");
                return;
            }
            systemCore.registers.IXRS[0] = inputValue;
            frame.update();
        });

        IXR2Load.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for IXR2");
                return;
            }
            systemCore.registers.IXRS[1] = inputValue;
            frame.update();
        });

        IXR3Load.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for IXR3");
                return;
            }
            systemCore.registers.IXRS[2] = inputValue;
            frame.update();
        });

        PCLoad.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 12) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for PC");
                return;
            }
            systemCore.registers.PC = inputValue;
            frame.update();
        });

        MARLoad.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 12) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for MAR");
                return;
            }
            systemCore.registers.MAR = inputValue;
            frame.update();
        });

        MBRLoad.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for MBR");
                return;
            }
            systemCore.registers.MBR = inputValue;
            frame.update();
        });

        FR1Load.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for IXR1");
                return;
            }
            systemCore.registers.IXRS[0] = inputValue;
            frame.update();
        });

        FR2Load.addActionListener(e -> {
            int inputValue;
            inputValue = Integer.parseInt(inputDecTextField.getText());
            if (inputValue > (Math.pow(2, 16) - 1) || inputValue < 0) {
                PrintToDebugConsole("Input is invalid for IXR1");
                return;
            }
            systemCore.registers.IXRS[0] = inputValue;
            frame.update();
        });

        IPLButton.addActionListener(e -> {
            File file = new File("IPL.txt");
            systemCore.utils.LoadProgram(file);
        });

        LoadProgramBut.addActionListener(e -> {

            File MEMFile;

            int select = MEMFileChooser.showOpenDialog(null);
            if (select == MEMFileChooser.APPROVE_OPTION) {

                MEMFile = MEMFileChooser.getSelectedFile();
                systemCore.frame.PrintToDebugConsole("Selected File" + MEMFile.getName());

                if (MEMFile.getName().endsWith(".txt")) {
                    systemCore.systemFunctions.Reset();
                    systemCore.utils.LoadProgram(MEMFile);
                    System.out.println(MEMFile.getName());
                    switch (MEMFile.getName())
                    {
                        case "program1.txt": systemCore.registers.PC = 0x100; break;
                        case "program2.txt": systemCore.registers.PC = 0x200; break;
                    }
                    systemCore.cache.CacheFlush();
                    update();
                    update_memory();
                }
                else {
                    systemCore.frame.PrintToDebugConsole("Please Select .txt file");
                }
            }


        });

        singleStepButton.addActionListener(e -> {
            systemCore.systemFunctions.SingleStep();
            PrintToDebugConsole("Running single step");
        });

        DebugCheck.addActionListener(e -> {
            systemCore.systemSettings.Debug = DebugCheck.isSelected();
            if (systemCore.systemSettings.Debug)
            {
                PrintToDebugConsole("Debug Enabled");
            }
            else {
                PrintToDebugConsole("Debug Disabled");
            }
        });

        KeyBoardInputButton.addActionListener(e -> {
            KeyboardBuffer.append(KeyboardIP.getText());
            KeyboardBuffer.append("\0");

            KeyboardBuffer.setCaretPosition(KeyboardBuffer.getDocument().getLength());
        });

        KeyboardIP.addActionListener(e -> {
            KeyboardBuffer.append(KeyboardIP.getText());
            KeyboardBuffer.append("\0");

            KeyboardBuffer.setCaretPosition(KeyboardBuffer.getDocument().getLength());
        });

        CardReaderSelectFile.addActionListener(e -> {

            int select = MEMFileChooser.showOpenDialog(null);
            if (select == MEMFileChooser.APPROVE_OPTION) {

                String Final_string = "";
                CardReaderBuffer.setText(Final_string);

                File MEMFile = MEMFileChooser.getSelectedFile();

                String file_name = MEMFile.getName();


                systemCore.frame.PrintToDebugConsole("Selected File" + MEMFile.getName());

                if (file_name.endsWith(".txt"))
                {
                    try (BufferedReader br = new BufferedReader(new FileReader(MEMFile.getPath())))
                    {
                        StringBuilder sb = new StringBuilder();
                        String line = br.readLine();

                        while (line != null) {
                            sb.append(line);
                            sb.append(System.lineSeparator());
                            line = br.readLine();
                        }
                        Final_string = sb.toString();

                        CardReaderBuffer.append(Final_string);
                        CardReaderBuffer.append("\0");
                        CardReaderBuffer.setCaretPosition(CardReaderBuffer.getDocument().getLength());

                        CRFileName.setText(file_name);
                    }
                    catch (Exception error)
                    {
                        systemCore.frame.PrintToDebugConsole("Unable to Read File \nReason :" + error);
                    }
                }
                else {
                    systemCore.frame.PrintToDebugConsole("Please select a .txt file");
                }
            }

        });


    }

    public boolean CheckKeyBoardBuffer() {
        return KeyboardBuffer.getText().equals("");
    }

    public boolean CheckCardReaderBuffer() {
        return CardReaderBuffer.getText().equals("");
    }

    public char GetKeyBoardChar() {
        String s = KeyboardBuffer.getText();
        char c = '\0';
        if (!s.equals("")) {
            c = s.charAt(0);
            s = s.substring(1);
        }
        KeyboardBuffer.setText(s);

        return c;
    }

    public char GetCardReaderChar() {
        String s = CardReaderBuffer.getText();
        char c = '\0';
        if (!s.equals("")) {
            c = s.charAt(0);
            s = s.substring(1);
        }
        CardReaderBuffer.setText(s);

        return c;
    }

    public void PrintCharToConsole(char c) {
        String s = OPConsole.getText();

        s = s + c;
        OPConsole.setText(s);

        OPConsole.setCaretPosition(OPConsole.getDocument().getLength());
    }

    public void InsertCharToConsole(char c) {
        String s = OPConsole.getText();

        String[] split = s.split("\n");

        split[split.length - 1] = c + split[split.length - 1];

        StringBuilder ns = new StringBuilder();

        for (String value : split) {
            ns.append(value);
            ns.append("\n");
        }
        OPConsole.setText(ns.toString());

        OPConsole.setCaretPosition(OPConsole.getDocument().getLength());
    }

    public void PrintToDebugConsole(String s) {
        if (!systemCore.systemSettings.Debug) {
            return;
        }
        DebugConsole.append(s);
        DebugConsole.append("\n");
        DebugConsole.setCaretPosition(DebugConsole.getDocument().getLength());
    }














    public JPanel GetFrame() {
        return this.mainPanel;
    }

    public void bindSystemCore(SystemCore systemCore) {
        this.systemCore = systemCore;
    }






    public void update_cache_hit_miss(int cache_loc)
    {
        switch (cache_loc)
        {
            case 0:
                if (Cache0.getBackground() != Color.GREEN)
                {
                    Cache0.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 1:
                if (Cache1.getBackground() != Color.GREEN)
                {
                    Cache1.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 2:
                if (Cache2.getBackground() != Color.GREEN)
                {
                    Cache2.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 3:
                if (Cache3.getBackground() != Color.GREEN)
                {
                    Cache3.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 4:
                if (Cache4.getBackground() != Color.GREEN)
                {
                    Cache4.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 5:
                if (Cache5.getBackground() != Color.GREEN)
                {
                    Cache5.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 6:
                if (Cache6.getBackground() != Color.GREEN)
                {
                    Cache6.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 7:
                if (Cache7.getBackground() != Color.GREEN)
                {
                    Cache7.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 8:
                if (Cache8.getBackground() != Color.GREEN)
                {
                    Cache8.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 9:
                if (Cache9.getBackground() != Color.GREEN)
                {
                    Cache9.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 10:
                if (Cache10.getBackground() != Color.GREEN)
                {
                    Cache10.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 11:
                if (Cache11.getBackground() != Color.GREEN)
                {
                    Cache11.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 12:
                if (Cache12.getBackground() != Color.GREEN)
                {
                    Cache12.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 13:
                if (Cache13.getBackground() != Color.GREEN)
                {
                    Cache13.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 14:
                if (Cache14.getBackground() != Color.GREEN)
                {
                    Cache14.setBackground(Color.GREEN);
                    set_cache_bg(cache_loc);
                }

            case 15:
                if (Cache15.getBackground() != Color.GREEN)
                {
                    Cache15.setBackground(Color.GREEN);
                }
                set_cache_bg(cache_loc);
        };
    }

    public void set_cache_bg(int cache_loc)
    {
        if (cache_loc != 0)
        {
            Cache0.setBackground(defaultcolor);
        }
        if (cache_loc != 1)
        {
            Cache1.setBackground(defaultcolor);
        }
        if (cache_loc != 2)
        {
            Cache2.setBackground(defaultcolor);
        }
        if (cache_loc != 3)
        {
            Cache3.setBackground(defaultcolor);
        }
        if (cache_loc != 4)
        {
            Cache4.setBackground(defaultcolor);
        }
        if (cache_loc != 5)
        {
            Cache5.setBackground(defaultcolor);
        }
        if (cache_loc != 6)
        {
            Cache6.setBackground(defaultcolor);
        }
        if (cache_loc != 7)
        {
            Cache7.setBackground(defaultcolor);
        }
        if (cache_loc != 8)
        {
            Cache8.setBackground(defaultcolor);
        }
        if (cache_loc != 9)
        {
            Cache9.setBackground(defaultcolor);
        }
        if (cache_loc != 10)
        {
            Cache10.setBackground(defaultcolor);
        }
        if (cache_loc != 11)
        {
            Cache11.setBackground(defaultcolor);
        }
        if (cache_loc != 12)
        {
            Cache12.setBackground(defaultcolor);
        }
        if (cache_loc != 13)
        {
            Cache13.setBackground(defaultcolor);
        }
        if (cache_loc != 14)
        {
            Cache14.setBackground(defaultcolor);
        }
        if (cache_loc != 15)
        {
            Cache15.setBackground(defaultcolor);
        }
    }


    public void update_memory()
    {
        for (int i = 0; i < systemCore.memory.ProcessorMemory.length; i++)
        {
            AddressList.add(String.format("%4S", Integer.toHexString(i)).replace(' ', '0'));
            memoryValue.add(String.format("%16S", Integer.toBinaryString(systemCore.memory.ProcessorMemory[i])).replace(' ', '0'));
            memoryHexValue.add(String.format("%4S", Integer.toHexString(systemCore.memory.ProcessorMemory[i])).replace(' ', '0'));
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
        String CC_BIN = String.format("%4S", Integer.toBinaryString(systemCore.registers.CC)).replace(' ', '0');
        String MFR_BIN = String.format("%4S", Integer.toBinaryString(systemCore.registers.MFR)).replace(' ', '0');

        if (CC_BIN.charAt(3) == '1') {CC1RadioButton.setSelected(true);} else {CC1RadioButton.setSelected(false);}
        if (CC_BIN.charAt(2) == '1') {CC2RadioButton.setSelected(true);} else {CC2RadioButton.setSelected(false);}
        if (CC_BIN.charAt(1) == '1') {CC3RadioButton.setSelected(true);} else {CC3RadioButton.setSelected(false);}
        if (CC_BIN.charAt(0) == '1') {CC4RadioButton.setSelected(true);} else {CC4RadioButton.setSelected(false);}

        if (MFR_BIN.charAt(3) == '1') {MFR1RadioButton.setSelected(true);} else {MFR1RadioButton.setSelected(false);}
        if (MFR_BIN.charAt(2) == '1') {MFR2RadioButton.setSelected(true);} else {MFR2RadioButton.setSelected(false);}
        if (MFR_BIN.charAt(1) == '1') {MFR3RadioButton.setSelected(true);} else {MFR3RadioButton.setSelected(false);}
        if (MFR_BIN.charAt(0) == '1') {MFR4RadioButton.setSelected(true);} else {MFR4RadioButton.setSelected(false);}

        GPR0BinTF.setText(String.format("%16S", Integer.toBinaryString(systemCore.registers.GPRS[0])).replace(' ', '0'));
        GPR1BinTF.setText(String.format("%16S", Integer.toBinaryString(systemCore.registers.GPRS[1])).replace(' ', '0'));
        GPR2BinTF.setText(String.format("%16S", Integer.toBinaryString(systemCore.registers.GPRS[2])).replace(' ', '0'));
        GPR3BinTF.setText(String.format("%16S", Integer.toBinaryString(systemCore.registers.GPRS[3])).replace(' ', '0'));
        IRBinTF.setText(String.format("%16S", Integer.toBinaryString(systemCore.registers.IR)).replace(' ', '0'));
        IXR1BinField.setText(String.format("%16S", Integer.toBinaryString(systemCore.registers.IXRS[0])).replace(' ', '0'));
        IXR2BinField.setText(String.format("%16S", Integer.toBinaryString(systemCore.registers.IXRS[1])).replace(' ', '0'));
        IXR3BinField.setText(String.format("%16S", Integer.toBinaryString(systemCore.registers.IXRS[2])).replace(' ', '0'));
        MARBinTF.setText(String.format("%12S", Integer.toBinaryString(systemCore.registers.MAR)).replace(' ', '0'));
        MBRBinTF.setText(String.format("%16S", Integer.toBinaryString(systemCore.registers.MBR)).replace(' ', '0'));
        PCBinTF.setText(String.format("%12S", Integer.toBinaryString(systemCore.registers.PC)).replace(' ', '0'));
        FR1BinField.setText(String.format("%16S", Integer.toBinaryString(systemCore.registers.FRS[0])).replace(' ', '0'));
        FR2BinField.setText(String.format("%16S", Integer.toBinaryString(systemCore.registers.FRS[1])).replace(' ', '0'));

        GPR0HexTF.setText(String.format("%4S", Integer.toHexString(systemCore.registers.GPRS[0])).replace(' ', '0'));
        GPR1HexTF.setText(String.format("%4S", Integer.toHexString(systemCore.registers.GPRS[1])).replace(' ', '0'));
        GPR2HexTF.setText(String.format("%4S", Integer.toHexString(systemCore.registers.GPRS[2])).replace(' ', '0'));
        GPR4HexTF.setText(String.format("%4S", Integer.toHexString(systemCore.registers.GPRS[3])).replace(' ', '0'));
        IRHexTF.setText(String.format("%4S", Integer.toHexString(systemCore.registers.IR)).replace(' ', '0'));
        IXR1HexField.setText(String.format("%4S", Integer.toHexString(systemCore.registers.IXRS[0])).replace(' ', '0'));
        IXR2HexField.setText(String.format("%4S", Integer.toHexString(systemCore.registers.IXRS[1])).replace(' ', '0'));
        IXR3HexField.setText(String.format("%4S", Integer.toHexString(systemCore.registers.IXRS[2])).replace(' ', '0'));
        MARHexTF.setText(String.format("%4S", Integer.toHexString(systemCore.registers.MAR)).replace(' ', '0'));
        MBRHexTF.setText(String.format("%4S", Integer.toHexString(systemCore.registers.MBR)).replace(' ', '0'));
        PCHexTF.setText(String.format("%4S", Integer.toHexString(systemCore.registers.PC)).replace(' ', '0'));
        ccHexTextField.setText(String.format("%1S", Integer.toHexString(systemCore.registers.CC)).replace(' ', '0'));
        mfrHexTextField.setText(String.format("%1S", Integer.toHexString(systemCore.registers.MFR)).replace(' ', '0'));
        FR1BinField.setText(String.format("%4S", Integer.toHexString(systemCore.registers.FRS[0])).replace(' ', '0'));
        FR2BinField.setText(String.format("%4S", Integer.toHexString(systemCore.registers.FRS[1])).replace(' ', '0'));


        runningCheckBox.setSelected(systemCore.systemSettings.Running);
        haltedCheckBox.setSelected(systemCore.systemSettings.Idle);
    }

    public void update_cache()
    {
        try {
            Cache0.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(0))).replace(' ', '0'));
            Cache1.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(1))).replace(' ', '0'));
            Cache2.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(2))).replace(' ', '0'));
            Cache3.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(3))).replace(' ', '0'));
            Cache4.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(4))).replace(' ', '0'));
            Cache5.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(5))).replace(' ', '0'));
            Cache6.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(6))).replace(' ', '0'));
            Cache7.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(7))).replace(' ', '0'));
            Cache8.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(8))).replace(' ', '0'));
            Cache9.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(9))).replace(' ', '0'));
            Cache10.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(10))).replace(' ', '0'));
            Cache11.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(11))).replace(' ', '0'));
            Cache12.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(12))).replace(' ', '0'));
            Cache13.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(13))).replace(' ', '0'));
            Cache14.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(14))).replace(' ', '0'));
            Cache15.setText(String.format("%16S", Integer.toBinaryString(systemCore.cache.CacheContent.get(15))).replace(' ', '0'));
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





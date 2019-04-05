//import javax.swing.*;
import javafx.stage.FileChooser;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

//import java.awt.*;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;


//import java.io.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;


import java.util.ArrayList;

public class MainPanel extends JComponent {

    private final double maxSpinValue = 20000;

    private JLabel programConstructor;
    private JLabel commandLabel;
    private JList<String> comandList;
    private JScrollPane jScrollPane;
    private JLabel nLabel;
    private JLabel mLabel;
    private JLabel qLabel;
    private JSpinner nSpinner;
    private JSpinner mSpinner;
    private JSpinner qSpinner;
    private JButton addBefore;
    private JButton addAfter;
    private JButton changeCommand;
    private JButton deleteCommand;
    private JButton showCommandList;
    private JButton saveProgram;
    private JButton destroyProgram;
    private JButton loadProgram;
    private JLabel commandList;
    private JList<String> programList;
    private JScrollPane programPane;
    private JLabel registersConstructor;
    private JLabel registersQuantity;
    private JSpinner registersQuantitySpinner;
    private JButton cleanRegisters;
    private JButton start;
    private RunFrame runFrame;

    private DefaultListModel listModel;
    private ArrayList<Command> commands;


    private JLabel label;
    private JSpinner spinner;
    public JScrollPane registersPane;
    private JPanel registersPanel;

    private String currentDirectory;
    private boolean saveController = false;

    private boolean runController;

    public MainPanel() {
        setBounds(0,0,635,400);


        listModel = new DefaultListModel();

        programConstructor =  new JLabel();
        programConstructor.setBounds(10,10,240,30);
        programConstructor.setVisible(true);
        programConstructor.setText("Конструктор програм:");
        programConstructor.setFont(new Font("Tahoma",Font.BOLD,14));
        add(programConstructor);

        commandLabel =  new JLabel();
        commandLabel.setBounds(10,40,120,90);
        commandLabel.setVisible(true);
        commandLabel.setText("Типи команд:");
        commandLabel.setFont(new Font("Tahoma",Font.BOLD,14));
        add(commandLabel);

        nLabel =  new JLabel();
        nLabel.setBounds(10,130,120,25);
        nLabel.setVisible(true);
        nLabel.setText("n:");
        nLabel.setFont(new Font("Tahoma",Font.BOLD,14));
        add(nLabel);

        mLabel =  new JLabel();
        mLabel.setBounds(10,155,120,25);
        mLabel.setVisible(true);
        mLabel.setText("m:");
        mLabel.setFont(new Font("Tahoma",Font.BOLD,14));
        add(mLabel);

        qLabel =  new JLabel();
        qLabel.setBounds(10,180,120,25);
        qLabel.setVisible(true);
        qLabel.setText("q:");
        qLabel.setFont(new Font("Tahoma",Font.BOLD,14));
        add(qLabel);


        nSpinner = new JSpinner(new SpinnerNumberModel(1.0, 1.0, maxSpinValue, 1.0));
        nSpinner.setBounds(130,130,120,25);
        nSpinner.setVisible(true);
        nSpinner.setFont(new Font("Tahoma",Font.BOLD,14));
        add(nSpinner);

        mSpinner = new JSpinner(new SpinnerNumberModel(1.0, 1.0, maxSpinValue, 1.0));
        mSpinner.setBounds(130,155,120,25);
        mSpinner.setVisible(true);
        mSpinner.setFont(new Font("Tahoma",Font.BOLD,14));
        add(mSpinner);

        qSpinner = new JSpinner(new SpinnerNumberModel(1.0, 1.0, maxSpinValue, 1.0));
        qSpinner.setBounds(130,180,120,25);
        qSpinner.setVisible(true);
        qSpinner.setFont(new Font("Tahoma",Font.BOLD,14));
        add(qSpinner);

        comandList = new JList<>(new String[]{"  Z(n);", "  S(n);", "  T(n,m);", "  J(n,m,q);"});
        jScrollPane = new JScrollPane(comandList);
        jScrollPane.setBounds(130,40,120,90);
        comandList.setFont(new Font("Tahoma",Font.BOLD,14));
        comandList.setSelectedIndex(0);
        mSpinner.setEnabled(false);
        qSpinner.setEnabled(false);
        comandList.addListSelectionListener((e) -> {
            switch (comandList.getSelectedIndex()){
                case 0:{}
                case 1:{
                    mSpinner.setEnabled(false);
                    qSpinner.setEnabled(false);
                    break;
                }
                case 2:{
                    mSpinner.setEnabled(true);
                    qSpinner.setEnabled(false);
                    break;
                }
                case 3:{
                    mSpinner.setEnabled(true);
                    qSpinner.setEnabled(true);
                    break;
                }
            }
        });
        add(jScrollPane);

        commands = new ArrayList<>();

        addBefore = new JButton();
        addBefore.setBounds(10,205,240,25);
        addBefore.setText("Додати перед поточною командою");
        addBefore.addActionListener((ActionEvent e) ->{
            if (listModel.size()==0){
                switch (comandList.getSelectedIndex()){
                    case 0 :{
                        commands.add(new Command(Type.Z,(int)(double)nSpinner.getValue(),0,0));
                        break;
                    }
                    case 1 :{
                        commands.add(new Command(Type.S,(int)(double)nSpinner.getValue(),0,0));
                        break;
                    }
                    case 2 :{
                        commands.add(new Command(Type.T,(int)(double)nSpinner.getValue(),(int)(double)mSpinner.getValue(),0));
                        break;
                    }
                    case 3 :{
                        commands.add(new Command(Type.J,(int)(double)nSpinner.getValue(),(int)(double)mSpinner.getValue(),(int)(double)qSpinner.getValue()));
                        break;
                    }
                }
                listModel.add(0,"1.  " + commands.get(0).toString());
                programList.setSelectedIndex(listModel.getSize()-1);
            }else{
                int index = programList.getSelectedIndex();

                switch (comandList.getSelectedIndex()){
                    case 0 :{
                        commands.add(index,new Command(Type.Z,(int)(double)nSpinner.getValue(),0,0));
                        break;
                    }
                    case 1 :{
                        commands.add(index,new Command(Type.S,(int)(double)nSpinner.getValue(),0,0));
                        break;
                    }
                    case 2 :{
                        commands.add(index,new Command(Type.T,(int)(double)nSpinner.getValue(),(int)(double)mSpinner.getValue(),0));
                        break;
                    }
                    case 3 :{
                        commands.add(index,new Command(Type.J,(int)(double)nSpinner.getValue(),(int)(double)mSpinner.getValue(),(int)(double)qSpinner.getValue()));
                        break;
                    }
                }
                listModel.addElement("null");
                for (int i = index; i < commands.size(); ++i) {
                    listModel.set(i,    i+1 + ".  " + commands.get(i));
                }
                programList.setSelectedIndex(index);
            }
        });
        add(addBefore);

        addAfter = new JButton();
        addAfter.setBounds(10,255,240,25);
        addAfter.setText("Додати після поточної команди");
        addAfter.addActionListener((ActionEvent e)->{
            if (listModel.size()==0){
                switch (comandList.getSelectedIndex()){
                    case 0 :{
                        commands.add(new Command(Type.Z,(int)(double)nSpinner.getValue(),0,0));
                        break;
                    }
                    case 1 :{
                        commands.add(new Command(Type.S,(int)(double)nSpinner.getValue(),0,0));
                        break;
                    }
                    case 2 :{
                        commands.add(new Command(Type.T,(int)(double)nSpinner.getValue(),(int)(double)mSpinner.getValue(),0));
                        break;
                    }
                    case 3 :{
                        commands.add(new Command(Type.J,(int)(double)nSpinner.getValue(),(int)(double)mSpinner.getValue(),(int)(double)qSpinner.getValue()));
                        break;
                    }
                }
                listModel.add(0,"1.  " + commands.get(0).toString());
                programList.setSelectedIndex(listModel.getSize()-1);
            }else{
                int index = programList.getSelectedIndex()+1;

                switch (comandList.getSelectedIndex()){
                    case 0 :{
                        commands.add(index,new Command(Type.Z,(int)(double)nSpinner.getValue(),0,0));
                        break;
                    }
                    case 1 :{
                        commands.add(index,new Command(Type.S,(int)(double)nSpinner.getValue(),0,0));
                        break;
                    }
                    case 2 :{
                        commands.add(index,new Command(Type.T,(int)(double)nSpinner.getValue(),(int)(double)mSpinner.getValue(),0));
                        break;
                    }
                    case 3 :{
                        commands.add(index,new Command(Type.J,(int)(double)nSpinner.getValue(),(int)(double)mSpinner.getValue(),(int)(double)qSpinner.getValue()));
                        break;
                    }
                }
                listModel.addElement("null");
                for (int i = index; i < commands.size(); ++i) {
                    listModel.set(i,    i+1 + ".  " + commands.get(i));
                }
                programList.setSelectedIndex(index);
            }
        });
        add(addAfter);

        changeCommand  = new JButton();
        changeCommand.setBounds(10,230,240,25);
        changeCommand.setText("Замінити поточну команду");
        changeCommand.setEnabled(false);
        changeCommand.addActionListener((ActionEvent e)->{
            if (listModel.getSize()>0){
                int index = programList.getSelectedIndex();

                switch (comandList.getSelectedIndex()){
                    case 0 :{
                        commands.set(index,new Command(Type.Z,(int)(double)nSpinner.getValue(),0,0));
                        break;
                    }
                    case 1 :{
                        commands.set(index,new Command(Type.S,(int)(double)nSpinner.getValue(),0,0));
                        break;
                    }
                    case 2 :{
                        commands.set(index,new Command(Type.T,(int)(double)nSpinner.getValue(),(int)(double)mSpinner.getValue(),0));
                        break;
                    }
                    case 3 :{
                        commands.set(index,new Command(Type.J,(int)(double)nSpinner.getValue(),(int)(double)mSpinner.getValue(),(int)(double)qSpinner.getValue()));
                        break;
                    }
                }
                listModel.set(index, index + 1 + ".  " + commands.get(index));
            }
        });
        add(changeCommand);

        deleteCommand  = new JButton();
        deleteCommand.setBounds(10,280,240,25);
        deleteCommand.setText("Видалити поточну команду");
        deleteCommand.setEnabled(false);
        deleteCommand.addActionListener((ActionEvent e)->{
            if (listModel.getSize()>0){
                int index = programList.getSelectedIndex();

                commands.remove(index);
                listModel.remove(listModel.getSize()-1);
                for (int i = index; i < commands.size(); ++i) {
                    listModel.set(i,    i+1 + ".  " + commands.get(i));
                }
                programList.setSelectedIndex(index-1);
            }
        });
        add(deleteCommand);

        showCommandList  = new JButton();
        showCommandList.setBounds(10,305,240,25);
        showCommandList.setText("Показати список команд");
        showCommandList.addActionListener((ActionEvent e)->{
            String s[]= new String[listModel.getSize()];
            for (int i = 0; i < listModel.getSize(); ++i) {
                s[i] = listModel.get(i).toString();
            }
            JList<String> comList = new JList<>(s);

            Object[] options = {"Копіювати список команд", "Скасувати!"};

            int n = JOptionPane.showOptionDialog(null, new JScrollPane(comList), "Список команд", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/icon.jpg"), options,  //the titles of buttons
                    options[0]); //default button title
            if(n == 0){
                String string = "";
                for (int i = 0; i < listModel.getSize();++i){
                    string += listModel.get(i).toString();
                    string += "\r\n";
                }

                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(string),null);
            }
        });
        add(showCommandList);

        saveProgram = new JButton();
        saveProgram.setBounds(10,330,240,25);
        saveProgram.setText("Зберегти програму");
        saveProgram.addActionListener((ActionEvent e)->{
            JFileChooser saveFile = setUpdateUI(new JFileChooser());

            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".txt","txt");
            saveFile.setFileFilter(fileFilter);

            saveFile.setSelectedFile(new File("newProgram.txt"));

            int n, j;
            do {
                n = saveFile.showSaveDialog(saveFile);
                switch (n){
                    case JFileChooser.ERROR_OPTION:{}
                    case JFileChooser.CANCEL_OPTION:{
                        saveController = false;
                        break;
                    }
                    case JFileChooser.APPROVE_OPTION:{
                        File file = new File(saveFile.getSelectedFile().getAbsolutePath());
                        if(file.exists() && file.isFile()){
                            currentDirectory = saveFile.getCurrentDirectory().getPath();

                            Object[] options = {"Так", "Ні"};

                            j = JOptionPane.showOptionDialog(null, "Замінити файл?", "Файл з такою назвою вже існує!", JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/icon.jpg"), options,  //the titles of buttons
                                    options[0]); //default button title

                            switch (j){
                                case 0:{
                                    try {
                                        FileWriter fw = new FileWriter(file);
                                        for (int i = 0; i < listModel.getSize(); ++i) {
                                            fw.write(listModel.get(i).toString());
                                            fw.write("\r\n");
                                            fw.flush();
                                            //fw.close();
                                        }
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                    saveController = false;
                                    break;
                                }
                                case 1:{
                                    saveController = true;
                                    break;
                                }
                            }

                        }else{
                            saveFile.setSelectedFile(file);

                            currentDirectory = saveFile.getCurrentDirectory().toString();
                            File currentFile = saveFile.getSelectedFile();

                            try {
                                FileWriter fw = new FileWriter(currentFile);
                                for (int i = 0; i < listModel.getSize(); ++i) {
                                    fw.write(listModel.get(i).toString());
                                    fw.write("\r\n");
                                    fw.flush();
                                    //fw.close();
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            saveController = false;
                        }
                    }
                }
            }while(saveController);
        });
        add(saveProgram);


        destroyProgram  = new JButton();
        destroyProgram.setBounds(260,385, 180,25);
        destroyProgram.setText("Очистити список команд");
        destroyProgram.addActionListener((ActionEvent e)->{
            commands.removeAll(commands);
            listModel.removeAllElements();
        });
        add(destroyProgram);

        loadProgram  = new JButton();
        loadProgram.setBounds(10,355,240,25);
        loadProgram.setText("Завантажити готову програму");
        loadProgram.addActionListener((ActionEvent e)->{

            Object[] options = {"Вставити з буфера", "Відкрити файл"};

            int n = JOptionPane.showOptionDialog(null, "Вставити з буфера чи відкрити файл?", "Завантажити програму", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/icon.jpg"), options,  //the titles of buttons
                    options[0]); //default button title

            switch (n){
                case 0:{
                    /**    Копіювання з буфера    **/
                    if( Toolkit.getDefaultToolkit().getSystemClipboard().isDataFlavorAvailable(DataFlavor.stringFlavor) ){
                        Object text = null;
                        try {
                            text = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                        } catch (UnsupportedFlavorException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        deserialize( (String) text );
                    }
                    break;
                }
                case 1:{

                    /**    Відкривання файлу    **/

                    JFileChooser openFile = setUpdateUI(new JFileChooser());

                    FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".txt","txt");
                    openFile.setFileFilter(fileFilter);

                    int temp = openFile.showOpenDialog(openFile);

                    if(openFile.APPROVE_OPTION == temp){
                        String string = "";

                        try ( FileReader fr = new FileReader(openFile.getSelectedFile()) ){
                            int c;
                            while( (c = fr.read()) != -1 ){
                                string += (char)c;
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        deserialize(string);
                    }
                    break;
                }
            }

            programList.setSelectedIndex(listModel.getSize()-1);
        });
        add(loadProgram);

/**********************************************************************************************************************/
        commandList = new JLabel();
        commandList.setBounds(260,10,180,30);
        commandList.setVisible(true);
        commandList.setText("Список команд:");
        commandList.setFont(new Font("Tahoma",Font.BOLD,14));
        add(commandList);

        programList = new JList(listModel);
        programPane = new JScrollPane(programList);
        programPane.setBounds(260,40,180,340);
        programPane.setFont(new Font("Tahoma",Font.BOLD,14));
        programList.addListSelectionListener((e) -> {
            if (programList.getSelectedIndex() == -1){
                changeCommand.setEnabled(false);
                deleteCommand.setEnabled(false);
                if (listModel.getSize() == 0){
                    start.setEnabled(false);
                    destroyProgram.setEnabled(false);
                }else{
                    start.setEnabled(true);
                    destroyProgram.setEnabled(true);
                }
            }else{
                changeCommand.setEnabled(true);
                deleteCommand.setEnabled(true);

                start.setEnabled(true);
                destroyProgram.setEnabled(true);
            }
        });
        add(programPane);

/**********************************************************************************************************************/
        registersConstructor = new JLabel();
        registersConstructor.setBounds(450,10,180,30);
        registersConstructor.setVisible(true);
        registersConstructor.setText("Конструктор регістрів:");
        registersConstructor.setFont(new Font("Tahoma",Font.BOLD,14));
        add(registersConstructor);

        registersQuantity = new JLabel();
        registersQuantity.setBounds(450,40,100,25);
        registersQuantity.setVisible(true);
        registersQuantity.setText("К-сть регістрів:");
        registersQuantity.setFont(new Font("Tahoma",Font.BOLD,12));
        add(registersQuantity);



        registersPanel = new JPanel();
        for(int i = 0; i < 10; ++i) {
            label = new JLabel();
            label.setBounds(20,i*25,(int)(140*0.4),25);
            label.setText("R"+ (i+1) +":");
            label.setFont(new Font("Tahoma",Font.BOLD,14));
            label.setVisible(true);
            registersPanel.add(label);

            spinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, maxSpinValue, 1.0));
            spinner.setBounds((int)(140*0.4),i*25,140-(int)(140*0.4),25);
            spinner.setFont(new Font("Tahoma",Font.BOLD,14));
            spinner.setVisible(true);
            registersPanel.add(spinner);
        }
        registersPanel.setPreferredSize(new Dimension(140,10*25));

        registersQuantitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 1.0, 50, 1.0));
        registersQuantitySpinner.setBounds(550,40,60,25);
        registersQuantitySpinner.setVisible(true);
        registersQuantitySpinner.setFont(new Font("Tahoma",Font.BOLD,14));
        registersQuantitySpinner.addChangeListener(e->{
            registersPanel = new JPanel();
            registersPanel.setPreferredSize(new Dimension(140,0));
            int i;
            for(i = 0; i < (double)registersQuantitySpinner.getValue(); ++i) {
                label = new JLabel();
                label.setBounds(0,i*27,(int)(140*0.4),27);
                label.setText("R"+(i+1)+":");
                label.setFont(new Font("Tahoma",Font.BOLD,14));
                registersPanel.add(label);

                spinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, maxSpinValue, 1.0));
                spinner.setBounds((int)(140*0.4),i*27,140-(int)(140*0.4),27);
                spinner.setFont(new Font("Tahoma",Font.BOLD,14));
                spinner.setVisible(true);
                spinner.setEnabled(true);
                registersPanel.add(spinner);

                registersPanel.repaint();
            }
            registersPanel.setPreferredSize(new Dimension(140, (int) (( (JSpinner)registersPanel.getComponent( ( i * 2 - 1) ) ).getY()+27) ));

            registersPane.setViewportView(registersPanel);

        });
        add(registersQuantitySpinner);

        registersPane = new JScrollPane(registersPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        registersPane.setPreferredSize(new Dimension(160,230));
        registersPane.setBounds(450,70,160,310);
        registersPane.setFont(new Font("Tahoma",Font.BOLD,14));
        add(registersPane);


        cleanRegisters = new JButton();
        cleanRegisters.setVisible(true);
        cleanRegisters.setText("Очистити регістри");
        cleanRegisters.setBounds(450,385,160,25);
        cleanRegisters.addActionListener((ActionEvent e)->{
            for (int i = 0; i < (int)(double)(Double)registersQuantitySpinner.getValue(); ++i) {
                ( (JSpinner) registersPanel.getComponent(i*2+1) ).setValue(0.0);
            }
        });
        add(cleanRegisters);

        start = new JButton();
        start.setVisible(true);
        start.setText("Запустити програму");
        start.setEnabled(false);
        start.setBounds(10 ,385,240,25);
        start.addActionListener((ActionEvent e)->{
            if (runFrame != null){
                runFrame.setVisible(false);
                runFrame.dispose();
            }
            runFrame = new RunFrame(getRegisters(), commands);
            //runFrame.setResizable(false);
            runFrame.setMinimumSize(new Dimension(645,470));
            runFrame.setSize(740,runFrame.getHeight());
            runFrame.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent evt) {
                    runFrame.resize();
                }
            });
            runFrame.setTitle("Виконання програми МНР");
            runFrame.setVisible(true);

        });
        add(start);
    }

    public int[] getRegisters(){
        int[] n = new int[(int)(double)(Double)registersQuantitySpinner.getValue()];
        for (int i = 0; i < (int)(double)(Double)registersQuantitySpinner.getValue(); ++i) {
            n[i] = (int)(double)(Double)(( (JSpinner) registersPanel.getComponent(i*2+1) ).getValue());
        }
        return n;
    }

    private void deserialize(String string){

        commands.removeAll(commands);
        listModel.removeAllElements();

        char s[] = string.toCharArray();
        Command com;

        int i = 0;
        while (i < s.length/** || s[i] != '\0'*/){
            if ( !(s[i] == 'Z' || s[i] == 'S' || s[i] == 'T' || s[i] == 'J') )++i;
            else{
                com = new Command();
                switch (s[i]){
                    case 'Z':{
                        com.setType(Type.Z);
                        break;
                    }
                    case 'S':{
                        com.setType(Type.S);
                        break;
                    }
                    case 'T': {
                        com.setType(Type.T);
                        break;
                    }
                    default: {
                        com.setType(Type.J);
                        break;
                    }
                }
                i+=2;//пропускаємо дужечку

                String numS;
                char numC[];
                int numI;

                //записуємо n
                numS = "";
                while (s[i] > 47 && s[i] < 58){
                    numS += s[i];
                    ++i;
                }
                numC = numS.toCharArray();
                numI = 0;
                for (int j = 0; j < numC.length; ++j){
                    numI *= 10;
                    numI += numC[j] - 48;
                }
                com.setN(numI);

                //записуємо m
                if (com.getType() == Type.T || com.getType() == Type.J){
                    ++i;//пропускаємо кому
                    numS = "";
                    while (s[i] > 47 && s[i] < 58){
                        numS += s[i];
                        ++i;
                    }
                    numC = numS.toCharArray();
                    numI = 0;
                    for (int j = 0; j < numC.length; ++j){
                        numI *= 10;
                        numI += numC[j] - 48;
                    }
                    com.setM(numI);
                }

                //записуємо q
                if (com.getType() == Type.J){
                    ++i;//пропускаємо кому
                    numS = "";
                    while (s[i] > 47 && s[i] < 58){
                        numS += s[i];
                        ++i;
                    }
                    numC = numS.toCharArray();
                    numI = 0;
                    for (int j = 0; j < numC.length; ++j){
                        numI *= 10;
                        numI += numC[j] - 48;
                    }
                    com.setQ(numI);
                }

                commands.add(com);
            }
        }

        for (i = 0; i < commands.size(); ++i) {
            listModel.addElement(i+1 + ".  " + commands.get(i));
        }
        programList.setSelectedIndex(commands.size());
    }

    public JFileChooser setUpdateUI(JFileChooser choose) {
        UIManager.put("FileChooser.openButtonText", "Відкрити");
        UIManager.put("FileChooser.cancelButtonText", "Відміна");
        UIManager.put("FileChooser.lookInLabelText", "Шукати в");
        UIManager.put("FileChooser.homeText","Робочий стіл");
        UIManager.put("FileChooser.fileNameLabelText", "Ім'я файлу");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файлу");

        UIManager.put("FileChooser.saveButtonText", "Зберегти");
        UIManager.put("FileChooser.saveButtonToolTipText", "Зберегти");
        UIManager.put("FileChooser.openButtonText", "Відкрити");
        UIManager.put("FileChooser.openButtonToolTipText", "Відкрити");
        UIManager.put("FileChooser.cancelButtonText", "Відміна");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Відміна");
        UIManager.put("FileChooser.lookInLabelText", "Папка");
        UIManager.put("FileChooser.saveInLabelText", "Папка");
        UIManager.put("FileChooser.fileNameLabelText", "Ім'я файлу");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файлів");

        UIManager.put("FileChooser.upFolderToolTipText", "На один рінь вище");
        UIManager.put("FileChooser.newFolderToolTipText", "Створити нову папку");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Таблиця");
        UIManager.put("FileChooser.fileNameHeaderText", "Ім'я");
        UIManager.put("FileChooser.fileSizeHeaderText", "Розмір");
        UIManager.put("FileChooser.fileTypeHeaderText", "Тип");
        UIManager.put("FileChooser.fileDateHeaderText", "Редаговано");
        UIManager.put("FileChooser.fileAttrHeaderText", "Атрибути");

        UIManager.put("FileChooser.acceptAllFileFilterText", "Усі файли");
        choose.updateUI();

        return choose;
    }
}
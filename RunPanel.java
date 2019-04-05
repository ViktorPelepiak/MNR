import javax.swing.*;


import java.awt.Dimension;
import java.awt.Font;


import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RunPanel extends JComponent {
    private JRadioButton quick;
    private JRadioButton detail;
    private ButtonGroup buttonGroup;
    private JLabel regime;

    private JButton complete;
    private JButton nextStep;
    private JButton stop;

    private JComponent panel;
    private JScrollPane scroll;

    private long receivedRegisters[];
    private long registers[];
    private State s;
    private int i = 0;
    private int publicationsQuantity;
    private Command[] commands;

    private Command currentCommand;
    private int n,m,q;
    private boolean isEnd = true;
    private long commandCounter;

    public void resize(){
        int width = this.getSize().width;
        if (detail.isSelected()){
            complete.setBounds((width/2)-225,45,150,25);
            nextStep.setBounds((width/2)-75,45,150,25);
            stop.setBounds((width/2)+75,45,150,25);
        }else{
            complete.setBounds((width/2)-150,45,150,25);
            stop.setBounds(width/2,45,150,25);
        }

        scroll.setBounds(20,75,width-50,this.getSize().height-130);
        scroll.setViewportView(panel);
    }

    public RunPanel(int[] registers, ArrayList<Command> commands){
        //setBounds(0,0,635,410);
        setVisible(true);

        this.receivedRegisters = new long[registers.length];
        for (int i = 0; i < registers.length; i++) {
            this.receivedRegisters[i] = registers[i];
        }

        this.registers = new long[receivedRegisters.length];
        removeRegisters();

        this.commands = new Command[commands.size()];
        for (int i=0;i<commands.size();++i){
            this.commands[i] = commands.get(i);
        }


        panel = new MyPanel();
        panel.setVisible(true);

        publicationsQuantity = 0;


        s = new State("Початкова конфігурація", this.registers);
        s.setBounds(10,i*55+10,150+50*registers.length+5,50);
        ++publicationsQuantity;
        panel.add(s);
        panel.setPreferredSize(new Dimension(150+50*registers.length+20,publicationsQuantity*55+20));


        regime = new JLabel();
        regime.setVisible(true);
        regime.setText("Виберіть режим роботи програми:");
        regime.setFont(new Font("Tahoma",Font.BOLD,14));
        regime.setBounds(20,10,270,30);
        add(regime);

        buttonGroup = new ButtonGroup();

        quick = new JRadioButton();
        quick.setText("Компактний");
        quick.setBounds(290,10,100,30);
        quick.setVisible(true);
        quick.setSelected(true);
        buttonGroup.add(quick);
        quick.addActionListener((ActionEvent e)->{
            nextStep.setVisible(false);
            int width = this.getSize().width;
            complete.setBounds((width/2)-150,45,150,25);
            stop.setBounds(width/2,45,150,25);

            i = 0;
            isEnd = true;
            panel.removeAll();

            removeRegisters();
            s = new State("Початкова конфігурація", this.registers);
            s.setBounds(10,i*55+10,150+50*registers.length+5,50);
            publicationsQuantity = 1;
            panel.add(s);
            panel.setPreferredSize(new Dimension(150+50*registers.length+20,publicationsQuantity*55+20));

            scroll.setViewportView(panel);
        });
        add(quick);

        detail = new JRadioButton();
        detail.setText("Детальний");
        detail.setBounds(390,10,100,30);
        detail.setVisible(true);
        detail.setSelected(false);
        buttonGroup.add(detail);
        detail.addActionListener((ActionEvent e)->{
            nextStep.setVisible(true);

            int width = this.getSize().width;

            complete.setBounds((width/2)-225,45,150,25);
            nextStep.setBounds((width/2)-75,45,150,25);
            stop.setBounds((width/2)+75,45,150,25);

            i = 0;
            isEnd = true;
            panel.removeAll();

            removeRegisters();
            s = new State("Початкова конфігурація", this.registers);
            s.setBounds(10,i*55+10,150+50*registers.length+5,50);
            publicationsQuantity = 1;
            panel.add(s);
            panel.setPreferredSize(new Dimension(150+50*registers.length+20,publicationsQuantity*55+20));

            scroll.setViewportView(panel);
        });
        add(detail);



        complete = new JButton();
        complete.setText("Виконати все");
        complete.setBounds(167,45,150,25);
        complete.setVisible(true);
        complete.addActionListener((ActionEvent e)->{
            //if (i<commands.size()){
            if(true){
                Runnable r = ()->{
                    //try {
                    if (quick.isSelected()) compactComplete();
                    else detailComplete();
                    //} catch (InterruptedException ex) {}
                };
                Thread t = new Thread(r); t.start();
            }
        });
        add(complete);

        nextStep = new JButton();
        nextStep.setText("Наступний крок");
        nextStep.setBounds(250,45,150,25);
        nextStep.setVisible(false);
        nextStep.addActionListener((ActionEvent e)->{
            if(i >= this.commands.length && !isEnd){
                Object[] options = {"Так", "Ні"};

                int n = JOptionPane.showOptionDialog(null, "Програма завершила роботу. Виконати програму ще раз?", "", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/icon.jpg"), options,  //the titles of buttons
                        options[0]); //default button title
                if (n == 0) {
                    i = 0;
                    isEnd = true;
                    panel.removeAll();

                    removeRegisters();
                    s = new State("Початкова конфігурація", this.registers);
                    s.setBounds(10,i*55+10,150+50*this.registers.length+5,50);
                    publicationsQuantity = 1;
                    panel.add(s);
                    panel.setPreferredSize(new Dimension(150+50*this.registers.length+20,publicationsQuantity*55+20));
                    scroll.setViewportView(panel);
                }
            }else
            if (i<commands.size()){
                currentCommand = commands.get(i);
                switch (currentCommand.getType()){
                    case Z:{
                        n = currentCommand.getN();
                        if (n <= this.registers.length) this.registers[n-1] = 0;

                        s = new State((i+1)+".  "+commands.get(i).toString(), this.registers);
                        s.setBounds(10,publicationsQuantity*55+10,150+50*this.registers.length+5,50);
                        panel.add(s);
                        repaint();
                        ++publicationsQuantity;

                        ++i;
                        break;
                    }
                    case S:{
                        n = currentCommand.getN();
                        if (n <= this.registers.length) ++this.registers[n-1];

                        s = new State((i+1)+".  "+commands.get(i).toString(), this.registers);
                        s.setBounds(10,publicationsQuantity*55+10,150+50*this.registers.length+5,50);
                        panel.add(s);
                        ++publicationsQuantity;
                        repaint();
                        ++i;
                        break;
                    }
                    case T:{
                        n = currentCommand.getN();
                        m = currentCommand.getM();
                        if (m <= this.registers.length){
                            if (n > this.registers.length) this.registers[n-1] = 0;
                            else this.registers[m-1] = this.registers[n-1];
                        }

                        s = new State((i+1)+".  "+commands.get(i).toString(), this.registers);
                        s.setBounds(10,publicationsQuantity*55+10,150+50*this.registers.length+5,50);
                        panel.add(s);
                        ++publicationsQuantity;
                        repaint();
                        ++i;
                        break;
                    }
                    case J:{
                        n = currentCommand.getN();
                        m = currentCommand.getM();
                        q = currentCommand.getQ();

                        s = new State((i+1)+".  "+commands.get(i).toString(), this.registers);
                        s.setBounds(10,publicationsQuantity*55+10,150+50*this.registers.length+5,50);
                        panel.add(s);
                        ++publicationsQuantity;
                        repaint();

                        if (n > this.registers.length && m > this.registers.length) i = q - 1;else
                        if (n > this.registers.length && this.registers[m-1] == 0) i = q - 1;else
                        if (m > this.registers.length && this.registers[n-1] == 0) i = q - 1;else
                        if (this.registers[n-1] == this.registers[m-1]) i = q - 1;else
                            ++i;
                        break;
                    }
                }
                panel.setPreferredSize(new Dimension(150+50*this.registers.length+20,publicationsQuantity*55+20));
                scroll.setViewportView(panel);
            }else if (isEnd){
                s = new State("Кінцева конфігурація", this.registers);
                s.setBounds(10,publicationsQuantity*55+10,150+50*this.registers.length+5,50);
                ++publicationsQuantity;
                panel.add(s);
                isEnd = false;

                panel.setPreferredSize(new Dimension(150+50*this.registers.length+20,publicationsQuantity*55+20));

                commandCounter = publicationsQuantity - 2;
                printResults();

                scroll.setViewportView(panel);
            }
        });
        add(nextStep);

        stop = new JButton();
        stop.setText("Припинити роботу");
        stop.setBounds(317,45,150,25);
        stop.setVisible(true);
        stop.addActionListener((ActionEvent e)->{
            i = commands.size();
        });
        add(stop);


        scroll = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVisible(true);
        scroll.getVerticalScrollBar().setUnitIncrement(20);

        scroll.setPreferredSize(new Dimension(595,300));
        scroll.setBounds(20,75,595,340);
        add(scroll);

        repaint();
    }

    private void compactComplete(){
        Command currentCommand;
        int n,m,q;
        if(i >= commands.length){
            Object[] options = {"Так", "Ні"};

            int k = JOptionPane.showOptionDialog(null, "Програма завершила роботу. Виконати програму ще раз?", "", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/icon.jpg"), options,  //the titles of buttons
                    options[0]); //default button title
            if (k == 0){
                i = 0;
                isEnd = true;
                panel.removeAll();

                removeRegisters();
                s = new State("Початкова конфігурація", this.registers);
                s.setBounds(10,i*55+10,150+50*registers.length+5,50);
                publicationsQuantity = 1;
                panel.add(s);
                panel.setPreferredSize(new Dimension(150+50*registers.length+20,publicationsQuantity*55+20));

                scroll.setViewportView(panel);
            }else return;
        }
        while (i < commands.length) {
            currentCommand = commands[i];
            switch (currentCommand.getType()){
                case Z:{
                    n = currentCommand.getN();
                    if (n <= this.registers.length) this.registers[n-1] = 0;
                    ++i;
                    break;
                }
                case S:{
                    n = currentCommand.getN();
                    if (n <= this.registers.length) ++this.registers[n-1];
                    ++i;
                    break;
                }
                case T:{
                    n = currentCommand.getN();
                    m = currentCommand.getM();
                    if (m <= this.registers.length){
                        if (n > this.registers.length) this.registers[n-1] = 0;
                        else this.registers[m-1] = this.registers[n-1];
                    }
                    ++i;
                    break;
                }
                case J:{
                    n = currentCommand.getN();
                    m = currentCommand.getM();
                    q = currentCommand.getQ();
                    if (n > this.registers.length && m > this.registers.length) i = q - 1;else
                    if (n > this.registers.length && this.registers[m-1] == 0) i = q - 1;else
                    if (m > this.registers.length && this.registers[n-1] == 0) i = q - 1;else
                    if (this.registers[n-1] == this.registers[m-1]) i = q - 1;else
                        ++i;
                    break;
                }
            }
            ++commandCounter;
            try {
                Thread.sleep(0,0);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        s = new State("Кінцева конфігурація", this.registers);
        s.setBounds(10,publicationsQuantity*55+10,150+50*this.registers.length+5,50);
        ++publicationsQuantity;
        repaint();
        panel.add(s);
        isEnd = false;

        panel.setPreferredSize(new Dimension(150+50*this.registers.length+20,publicationsQuantity*55+20));

        printResults();
        scroll.setViewportView(panel);
    }

    private void detailComplete(){
        if(i >= commands.length){
            Object[] options = {"Так", "Ні"};

            int n = JOptionPane.showOptionDialog(null, "Програма завершила роботу. Виконати програму ще раз?", "", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/icon.jpg"), options,  //the titles of buttons
                    options[0]); //default button title
            if (n == 0){
                i = 0;
                isEnd = true;
                panel.removeAll();

                removeRegisters();
                s = new State("Початкова конфігурація", this.registers);
                s.setBounds(10,i*55+10,150+50*registers.length+5,50);
                publicationsQuantity = 1;
                panel.add(s);
                panel.setPreferredSize(new Dimension(150+50*registers.length+20,publicationsQuantity*55+20));
                scroll.setViewportView(panel);
            }else return;
        }
        {while (i < commands.length) {
            currentCommand = commands[i];
            switch (currentCommand.getType()){
                case Z:{
                    n = currentCommand.getN();
                    if (n <= this.registers.length) this.registers[n-1] = 0;

                    s = new State((i+1)+".  "+commands[i].toString(), this.registers);
                    s.setBounds(10,publicationsQuantity*55+10,150+50*this.registers.length+5,50);
                    panel.add(s);
                    ++publicationsQuantity;
                    repaint();
                    ++i;
                    break;
                }
                case S:{
                    n = currentCommand.getN();
                    if (n <= this.registers.length) ++this.registers[n-1];

                    s = new State((i+1)+".  "+commands[i].toString(), this.registers);
                    s.setBounds(10,publicationsQuantity*55+10,150+50*this.registers.length+5,50);
                    panel.add(s);
                    ++publicationsQuantity;
                    repaint();
                    ++i;
                    break;
                }
                case T:{
                    n = currentCommand.getN();
                    m = currentCommand.getM();
                    if (m <= this.registers.length){
                        if (n > this.registers.length) this.registers[n-1] = 0;
                        else this.registers[m-1] = this.registers[n-1];
                    }

                    s = new State((i+1)+".  "+commands[i].toString(), this.registers);
                    s.setBounds(10,publicationsQuantity*55+10,150+50*this.registers.length+5,50);
                    panel.add(s);
                    ++publicationsQuantity;
                    repaint();
                    ++i;
                    break;
                }
                case J:{
                    n = currentCommand.getN();
                    m = currentCommand.getM();
                    q = currentCommand.getQ();

                    s = new State((i+1)+".  "+commands[i].toString(), this.registers);
                    s.setBounds(10,publicationsQuantity*55+10,150+50*this.registers.length+5,50);
                    panel.add(s);
                    ++publicationsQuantity;
                    repaint();

                    if (n > this.registers.length && m > this.registers.length) i = q - 1;else
                    if (n > this.registers.length && this.registers[m-1] == 0) i = q - 1;else
                    if (m > this.registers.length && this.registers[n-1] == 0) i = q - 1;else
                    if (this.registers[n-1] == this.registers[m-1]) i = q - 1;else
                        ++i;
                    break;
                }
            }
            panel.setPreferredSize(new Dimension(150+50*this.registers.length+20,publicationsQuantity*55+20));
            scroll.setViewportView(panel);
            try {
                Thread.sleep(0,0);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        s = new State("Кінцева конфігурація", this.registers);
        s.setBounds(10,publicationsQuantity*55+10,150+50*this.registers.length+5,50);
        ++publicationsQuantity;
        panel.setPreferredSize(new Dimension(150+50*this.registers.length+20,publicationsQuantity*55+20));

        commandCounter = publicationsQuantity-2;
        printResults();

        scroll.setViewportView(panel);
        repaint();
        panel.add(s);
        isEnd = false;}
    }

    private void removeRegisters(){
        for (int j = 0; j < receivedRegisters.length; ++j) {
            this.registers[j] = receivedRegisters[j];
        }
    }

    private void printResults(){
        Font myFont = new Font("Tahoma",Font.BOLD,14);

        JLabel r1 = new JLabel();
        r1.setText( ("Результат виконання програми: R1 = " + this.registers[0]) );
        r1.setVisible(true);
        r1.setBounds(10,publicationsQuantity*55+10,300+5,50);
        r1.setFont(myFont);
        panel.add(r1);

        JLabel steps = new JLabel();
        steps.setText("Виконано команд : " + commandCounter);
        steps.setVisible(true);
        steps.setBounds(10+300+50,publicationsQuantity*55+10,300+5,50);
        steps.setFont(myFont);
        panel.add(steps);

        panel.setPreferredSize(new Dimension(150+50*this.registers.length+20,publicationsQuantity*55+20+50));
    }
}

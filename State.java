import javax.swing.JComponent;

import java.awt.*;


public class State extends JComponent{
    private String currentCommand;
    private long register[];

    public State(String currentCommand, long[] register) {
        setVisible(true);

        this.currentCommand = currentCommand;
        this.register = new long[register.length];
        for (int i = 0; i < register.length; ++i) {
            this.register[i] = register[i];
        }

        repaint();
    }


    public void paintComponent(Graphics g){
        Graphics2D gr = (Graphics2D)g;

        gr.setColor(Color.BLACK);//задання стилів для клітинки команди
        if (currentCommand.equals("Початкова конфігурація") || currentCommand.equals("Кінцева конфігурація")){
            gr.setColor(new Color(254,171,173));
            gr.setFont(new Font("Tahoma",Font.BOLD,11));
        }else{
            gr.setColor(new Color(177,171,254));
            gr.setFont(new Font("Tahoma",Font.BOLD,14));
        }
        gr.fillRect(0,0,150,49);//замальовуємо клітинку команди

        gr.setColor(Color.BLACK);
        gr.drawRect(0,0,150,49);

        gr.setFont(new Font("Tahoma",Font.BOLD,11));
        gr.drawString(currentCommand,5,30);

        gr.setColor(new Color(255,226,159));//замальовка заголовочних клітинок регістрів
        gr.fillRect(150,0,register.length*50,25);

        gr.setColor(Color.BLACK);
        for (int i = 0; i < register.length; ++i) {
            gr.drawRect(150+50*i,0,50,49);
            gr.drawString("R"+(i+1),165+50*i,15);
            gr.drawString(""+register[i],165+50*i - 10,40);
        }
        gr.drawLine(150,25,150+50*register.length-1,25);
    }

}

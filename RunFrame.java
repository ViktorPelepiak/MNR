import javax.swing.JFrame;
import java.awt.*;
import java.util.ArrayList;

public class RunFrame extends JFrame {
    RunPanel runPanel;

    public RunFrame(int[] registers, ArrayList<Command> commands){

        setBounds(350,100,645,470);

        runPanel = new RunPanel(registers, commands);
        runPanel.setVisible(true);
        add(runPanel);
    }

    public void resize(){
        runPanel.setSize(this.getSize());
        runPanel.resize();
    }
}

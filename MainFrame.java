import javax.swing.JFrame;
import java.awt.HeadlessException;

public class MainFrame extends JFrame{

    public MainFrame() throws HeadlessException {
        setTitle("Емулятор МНР");

        setBounds(350,100,645,460);
        MainPanel mainPanel = new MainPanel();
        mainPanel.setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(mainPanel);
    }
}

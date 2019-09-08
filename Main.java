import views.MainFrame;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            MainFrame mF = new MainFrame();
            mF.setResizable(false);
            mF.setVisible(true);
        });
    }
}
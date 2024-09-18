// Arnas Šniokaitis 5 gr. 2 pogr.
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        Main m = new Main();
        m.ffs();
    }
    public void ffs() {
        SwingUtilities.invokeLater(() ->{
            GameWindow gw = new GameWindow(1);
            JFrame fr = new JFrame();
            fr.setSize(800, 477);
            fr.setVisible(true);
            fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fr.setResizable(false);
            fr.add(gw);
            gw.requestFocusInWindow();
        });
    }
}
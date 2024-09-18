// Arnas Šniokaitis 5 gr. 2 pogr.
import javax.swing.*;
public class Editor {
    public static void main(String[] args) {
        String num = JOptionPane.showInputDialog("Enter level number");
        int l = Integer.parseInt(JOptionPane.showInputDialog("Emter level tile length"));
        SwingUtilities.invokeLater(()->{
            Framing fr = new Framing(num, l);
        });
    }
}
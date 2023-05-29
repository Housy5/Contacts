package housy.contact;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    private static void initLaf() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        initLaf();
        EventQueue.invokeLater(() -> {
            MainGUI mg = new MainGUI();
            mg.setVisible(true);
        });
    }
    
}

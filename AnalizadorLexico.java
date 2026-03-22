package analizador.lexico;

import javax.swing.SwingUtilities;

/**
 *
 * @author vedua
 */
public class AnalizadorLexico {

    /**
     * @param args the command line arguments
     */
public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new MenuAFN().setVisible(true);
        }
    });
}
    
}

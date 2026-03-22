package analizador.lexico;

import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import java.util.HashMap;
import java.util.Map;

public class MenuAFN extends JFrame {

    Map<Integer, AFN> afns = new HashMap<>();

    public MenuAFN() {
        setTitle("Simulador AFN");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuAFN = new JMenu("Operaciones AFN");

        JMenuItem itemBasico = new JMenuItem("AFN básico");
        JMenuItem itemUnion = new JMenuItem("Unir");
        JMenuItem itemConcat = new JMenuItem("Concatenar");

        JMenuItem itemCerrPos = new JMenuItem("Cerradura +");
        JMenuItem itemCerrKleene = new JMenuItem("Cerradura *");
        JMenuItem itemOpcional = new JMenuItem("Opcional");
        
        JMenuItem itemER = new JMenuItem("ER -> AFN"); 
        JMenuItem itemUnionLexico = new JMenuItem("Unión para Analizador Léxico"); 
        JMenuItem itemAFNaAFD = new JMenuItem("Convertir AFN a AFD"); 
        JMenuItem itemAnalizarCadena = new JMenuItem("Analizar una Cadena"); 
        JMenuItem itemProbarLexico = new JMenuItem("Probar Analizador Léxico");

        // Menú
        menuAFN.add(itemBasico);
        menuAFN.addSeparator();
        menuAFN.add(itemUnion);
        menuAFN.add(itemConcat);
        menuAFN.add(itemCerrPos);
        menuAFN.add(itemCerrKleene);
        menuAFN.add(itemOpcional);
        menuAFN.addSeparator(); 
        menuAFN.add(itemER); 
        menuAFN.addSeparator(); 
        menuAFN.add(itemUnionLexico);
        menuAFN.add(itemAFNaAFD); 
        menuAFN.add(itemAnalizarCadena); 
        menuAFN.add(itemProbarLexico);
        
        menuBar.add(menuAFN);
        setJMenuBar(menuBar);

        // Eventos
        itemBasico.addActionListener(e -> mostrarAFNBasico());
        itemUnion.addActionListener(e -> mostrarOperacion("Unir"));
        itemConcat.addActionListener(e -> mostrarOperacion("Concatenar"));
        itemCerrPos.addActionListener(e -> mostrarOperacionUnaria("+"));
        itemCerrKleene.addActionListener(e -> mostrarOperacionUnaria("*"));
        itemOpcional.addActionListener(e -> mostrarOperacionUnaria("?"));
        itemER.addActionListener(e -> mostrarVentana("ER -> AFN", "Seleccionaste ER -> AFN")); 
        itemUnionLexico.addActionListener(e -> mostrarVentana("Unión Léxico", "Seleccionaste Unión para Analizador Léxico")); 
        itemAFNaAFD.addActionListener(e -> mostrarVentana("AFN a AFD", "Seleccionaste Convertir AFN a AFD")); 
        itemAnalizarCadena.addActionListener(e -> mostrarVentana("Analizar Cadena", "Seleccionaste Analizar una Cadena")); 
        itemProbarLexico.addActionListener(e -> mostrarVentana("Probar Léxico", "Seleccionaste Probar Analizador Léxico"));
    }
    
    // Método para mostrar ventana emergente 
    private void mostrarVentana(String titulo, String mensaje) { 
        JDialog dialogo = new JDialog(this, titulo, true); 
        dialogo.setSize(300, 200); 
        dialogo.setLocationRelativeTo(this); JLabel label = new JLabel(mensaje, SwingConstants.CENTER); 
        dialogo.add(label); 
        dialogo.setVisible(true); }

    // ========================= AFN BÁSICO =========================
    private void mostrarAFNBasico() {
        JDialog dialogo = new JDialog(this, "AFN Básico", true);
        dialogo.setSize(550, 250);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(null);

        JLabel lblChar = new JLabel("Carácter Inf:");
        lblChar.setBounds(20, 20, 120, 25);
        dialogo.add(lblChar);

        JTextField txtChar = new JTextField();
        txtChar.setBounds(120, 20, 40, 25);
        dialogo.add(txtChar);

        JLabel lblChar2 = new JLabel("Carácter Sup:");
        lblChar2.setBounds(20, 60, 120, 25);
        dialogo.add(lblChar2);

        JTextField txtChar2 = new JTextField();
        txtChar2.setBounds(120, 60, 40, 25);
        dialogo.add(txtChar2);

        JLabel lblId = new JLabel("ID del AFN:");
        lblId.setBounds(20, 100, 120, 25);
        dialogo.add(lblId);

        JTextField txtId = new JTextField();
        txtId.setBounds(120, 100, 60, 25);
        dialogo.add(txtId);

        // Imagen
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/analizador/lexico/imagen.png"));
            JLabel lblImagen = new JLabel(icono);
            lblImagen.setBounds(210, 20, 310, 150);
            dialogo.add(lblImagen);
        } catch (Exception e) {
            System.out.println("Imagen no encontrada");
        }

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(60, 150, 120, 30);
        dialogo.add(btnAceptar);

        btnAceptar.addActionListener(e -> {
            String c1 = txtChar.getText();
            String idTexto = txtId.getText();

            if (c1.length() != 1) {
                JOptionPane.showMessageDialog(dialogo, "Ingresa un carácter válido");
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idTexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "El ID debe ser numérico");
                return;
            }

AFN nuevo;

if (txtChar2.getText().isEmpty()) {
    nuevo = new AFN().basico(c1.charAt(0));
} else {
    String c2 = txtChar2.getText();
    
    if (c2.length() != 1) {
        JOptionPane.showMessageDialog(dialogo, "Carácter superior inválido");
        return;
    }

    nuevo = new AFN().basico(c1.charAt(0), c2.charAt(0));
}            afns.put(id, nuevo);

            JOptionPane.showMessageDialog(dialogo, "AFN guardado con ID: " + id);
            dialogo.dispose();
        });

        dialogo.setVisible(true);
    }

    // ========================= OPERACIONES =========================
    private void mostrarOperacion(String operacion) {

        JDialog dialogo = new JDialog(this, operacion, true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(null);

        JLabel lblOp = new JLabel(operacion);
        lblOp.setBounds(20, 30, 120, 25);
        dialogo.add(lblOp);

        JComboBox<Integer> combo1 = new JComboBox<>();
        combo1.setBounds(120, 30, 80, 25);

        JComboBox<Integer> combo2 = new JComboBox<>();
        combo2.setBounds(260, 30, 80, 25);

        for (Integer id : afns.keySet()) {
            combo1.addItem(id);
            combo2.addItem(id);
        }

        dialogo.add(combo1);

        JLabel lblCon = new JLabel("con:");
        lblCon.setBounds(210, 30, 40, 25);
        dialogo.add(lblCon);

        dialogo.add(combo2);

        JButton btn = new JButton("Aceptar");
        btn.setBounds(130, 90, 120, 30);
        dialogo.add(btn);

        btn.addActionListener(e -> {
            Integer id1 = (Integer) combo1.getSelectedItem();
            Integer id2 = (Integer) combo2.getSelectedItem();

            if (id1 == null || id2 == null) {
                JOptionPane.showMessageDialog(dialogo, "Selecciona IDs válidos");
                return;
            }
            
            AFN a1 = afns.get(id1);
            AFN a2 = afns.get(id2);

            if (a1 == null || a2 == null) {
              JOptionPane.showMessageDialog(dialogo, "Error: AFN no encontrado");
                return;
            }   

            afns.remove(id2);

            switch (operacion) {
                case "Unir":
                    a1.union(a2);
                    break;
                case "Concatenar":
                    a1.concatenar(a2);
                    break;
            }

 JOptionPane.showMessageDialog(dialogo,
    "Resultado:\n" + a1.toString());
 
 
            dialogo.dispose();
        });

        dialogo.setVisible(true);
    }

    private void mostrarOperacionUnaria(String operacion) {

    JDialog dialogo = new JDialog(this, operacion, true);
    dialogo.setSize(350, 180);
    dialogo.setLocationRelativeTo(this);
    dialogo.setLayout(null);

    // Label
    JLabel lbl = new JLabel("Realizar operación " + operacion + " :");
    lbl.setBounds(20, 30, 200, 25);
    dialogo.add(lbl);

    // ComboBox
    JComboBox<Integer> combo = new JComboBox<>();
    combo.setBounds(200, 30, 80, 25);

    for (Integer id : afns.keySet()) {
        combo.addItem(id);
    }

    dialogo.add(combo);

    // Botón
    JButton btn = new JButton("Aceptar");
    btn.setBounds(100, 80, 120, 30);
    dialogo.add(btn);

    btn.addActionListener(e -> {
        Integer id = (Integer) combo.getSelectedItem();

        if (id == null) {
            JOptionPane.showMessageDialog(dialogo, "Selecciona un ID válido");
            return;
        }

        AFN a = afns.get(id);

        switch (operacion) {
            case "+":
                a.cerraduraPositiva();
                break;

            case "*":
                a.cerraduraKleene();
                break;

            case "?":
                a.opcional();
                break;
        }

        JOptionPane.showMessageDialog(dialogo,
                "Operación " + operacion + " aplicada al AFN " + id);

        dialogo.dispose();
    });

    dialogo.setVisible(true);
}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuAFN().setVisible(true);
        });
    }
}
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import model.Persona;

public class EditorPersona extends JDialog {

    private final Color BG = new Color(18, 18, 18);
    private final Color CARD = new Color(30, 30, 30);
    private final Color ORANGE = new Color(255, 140, 0);
    private final Color GRAY_BTN = new Color(60, 60, 60);

    private JTextField[] fields = {
            new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField()
    };

    private String[] labels = { "NOME *", "COGNOME *", "INDIRIZZO", "TELEFONO *", "ETÀ" };
    private boolean confermato = false;

    public EditorPersona(Frame parent, Persona p) {
        super(parent, "Editor Persona", true);
        setSize(420, 650);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new GridLayout(10, 1, 5, 5));
        center.setBackground(CARD);
        center.setBorder(new EmptyBorder(20, 40, 20, 40));

        if (p != null) {
            fields[0].setText(p.getNome());
            fields[1].setText(p.getCognome());
            fields[2].setText(p.getIndirizzo());
            fields[3].setText(p.getTelefono());
            fields[4].setText(String.valueOf(p.getEta()));
        }

        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i]);
            l.setForeground(new Color(200, 200, 200));
            l.setFont(new Font("SansSerif", Font.BOLD, 12));
            center.add(l);
            styleField(fields[i]);
            center.add(fields[i]);
        }

        JButton btnSalva = new JButton("SALVA");
        styleButton(btnSalva, ORANGE);
        btnSalva.addActionListener(e -> {
            if (valida()) { 
                confermato = true; 
                dispose(); 
            }
        });

        JButton btnAnnulla = new JButton("ANNULLA");
        styleButton(btnAnnulla, GRAY_BTN);
        btnAnnulla.addActionListener(e -> {
            confermato = false;
            dispose(); 
        });

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        south.setBackground(BG);
        south.add(btnSalva);
        south.add(btnAnnulla);

        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
    }

    private void styleField(JTextField f) {
        f.setBackground(new Color(45, 45, 45));
        f.setForeground(Color.WHITE);
        f.setCaretColor(ORANGE);
        f.setFont(new Font("SansSerif", Font.PLAIN, 15));
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(70, 70, 70), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleButton(JButton b, Color bg) {
        b.setPreferredSize(new Dimension(140, 45)); 
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(bg.brighter(), 1));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.setContentAreaFilled(true);
    }

    private boolean valida() {
        String nome = fields[0].getText().trim();
        String cognome = fields[1].getText().trim();
        String tel = fields[3].getText().trim();
        String etaTesto = fields[4].getText().trim();

        if (nome.isEmpty() || cognome.isEmpty() || tel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome, Cognome e Telefono sono obbligatori!");
            return false;
        }

        if (!tel.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Il campo Telefono deve contenere solo numeri!");
            return false;
        }

        if (!etaTesto.isEmpty()) {
            if (!etaTesto.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "L'età deve essere un numero!");
                return false;
            }
            int eta = Integer.parseInt(etaTesto);
            if (eta < 0 || eta > 120) {
                JOptionPane.showMessageDialog(this, "Inserire un'età valida (0-120)!");
                return false;
            }
        }
        return true;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return "";
        str = str.trim();
        if (str.length() == 1) return str.toUpperCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public boolean isConfermato() { return confermato; }

    public Persona getPersona() {
        return new Persona(
                capitalize(fields[0].getText()),
                capitalize(fields[1].getText()),
                fields[2].getText().trim(),
                fields[3].getText().trim(),
                fields[4].getText().trim().isEmpty() ? 0 : Integer.parseInt(fields[4].getText().trim())
        );
    }
}
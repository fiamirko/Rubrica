package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class RegisterGui extends JFrame {

    private static final long serialVersionUID = 1L;
    private final Color BG = new Color(18, 18, 18);
    private final Color CARD = new Color(28, 28, 28);
    private final Color ORANGE = new Color(255, 140, 0);

    private JTextField userField = new JTextField(20);
    private JPasswordField passField = new JPasswordField(20);
    private JPasswordField confirmPassField = new JPasswordField(20);
    private JButton registerBtn = new JButton("CREA ACCOUNT");
    private JButton backBtn = new JButton("TORNA AL LOGIN");

    public RegisterGui() {
        setTitle("Turing - Registrazione");
        setSize(500, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(380, 580));
        card.setBackground(CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(40, 35, 40, 35));

        JLabel logo = new JLabel("JOIN US");
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setFont(new Font("Arial Black", Font.BOLD, 42));
        logo.setForeground(ORANGE);

        JLabel sub = new JLabel("NUOVO UTENTE");
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub.setForeground(Color.WHITE);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 15));

        card.add(logo);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(sub);
        card.add(Box.createRigidArea(new Dimension(0, 40)));

        card.add(creaLabel("USERNAME"));
        card.add(styleField(userField));
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(creaLabel("PASSWORD"));
        card.add(styleField(passField));
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        
        card.add(creaLabel("CONFERMA PASSWORD"));
        card.add(styleField(confirmPassField));
        card.add(Box.createRigidArea(new Dimension(0, 35)));

        styleButton(registerBtn, ORANGE, Color.WHITE);
        styleButton(backBtn, new Color(50, 50, 50), Color.WHITE);

        card.add(registerBtn);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(backBtn);

        add(card);
        backBtn.addActionListener(e -> dispose());
    }

    private JLabel creaLabel(String txt) {
        JLabel l = new JLabel(txt);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setBorder(new EmptyBorder(0, 5, 8, 0));
        return l;
    }

    private JTextField styleField(JTextField f) {
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        f.setBackground(new Color(45, 45, 45));
        f.setForeground(Color.WHITE);
        f.setCaretColor(ORANGE);
        f.setFont(new Font("SansSerif", Font.PLAIN, 15));
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(70, 70, 70), 1),
                new EmptyBorder(5, 12, 5, 12)
        ));
        return f;
    }

    private void styleButton(JButton b, Color bg, Color fg) {
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setBorder(new EmptyBorder(10, 10, 10, 10));
        b.setContentAreaFilled(false);
        b.setOpaque(true);
    }

    public String getUsername() { return userField.getText().trim(); }
    public String getPassword() { return new String(passField.getPassword()).trim(); }
    public String getConfirmPassword() { return new String(confirmPassField.getPassword()).trim(); }
    public void setRegisterAction(ActionListener al) { registerBtn.addActionListener(al); }
}
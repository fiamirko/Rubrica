package gui;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import dao.PersonaDAO;
import model.Persona;
import main.Main;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private final Color BG = new Color(18, 18, 18);
    private final Color CARD = new Color(28, 28, 28);
    private final Color ORANGE = new Color(255, 140, 0);
    private JTable tabella;
    private DefaultTableModel model;
    private PersonaDAO dao = new PersonaDAO();
    private Vector<Persona> lista = new Vector<>();

    public MainFrame() throws Exception {
        setTitle("Turing Directory");
        setSize(1250, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(CARD);
        top.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("TURING DIRECTORY");
        title.setForeground(ORANGE);
        title.setFont(new Font("Arial Black", Font.BOLD, 28));
        top.add(title, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actions.setBackground(CARD);

        JButton logout = createButton("LOGOUT", Color.BLACK, ORANGE);
        logout.setBorder(new LineBorder(ORANGE, 1));
        
        JButton edit = createButton("MODIFICA", new Color(60, 60, 60), Color.WHITE);
        JButton del = createButton("ELIMINA", new Color(180, 50, 50), Color.WHITE);
        JButton add = createButton("NUOVO", ORANGE, Color.WHITE);

        actions.add(logout);
        actions.add(edit);
        actions.add(del);
        actions.add(add);
        top.add(actions, BorderLayout.EAST);

        String[] colonne = {"NOME", "COGNOME", "TELEFONO"};
        model = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabella = new JTable(model);
        tabella.setBackground(CARD);
        tabella.setForeground(Color.WHITE);
        tabella.setSelectionBackground(ORANGE);
        tabella.setSelectionForeground(Color.BLACK);
        tabella.setRowHeight(45);
        tabella.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // MODIFICA QUI: Colore arancione per i nomi delle colonne
        JTableHeader header = tabella.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(ORANGE); // Stesso arancione del logo
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 45));

        JScrollPane scroll = new JScrollPane(tabella);
        scroll.setBorder(new EmptyBorder(20, 20, 20, 20));
        scroll.getViewport().setBackground(CARD);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        logout.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Effettuare il logout?", "Logout", JOptionPane.YES_NO_OPTION) == 0) {
                this.dispose();
                Main.mostraLogin();
            }
        });

        add.addActionListener(e -> {
            try { azioneNuovo(); } catch (Exception ex) {}
        });

        edit.addActionListener(e -> {
            try { azioneModifica(); } catch (Exception ex) {}
        });

        del.addActionListener(e -> {
            try { azioneElimina(); } catch (Exception ex) {}
        });

        aggiornaTabella();
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(140, 42));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setContentAreaFilled(true);
        b.setOpaque(true);
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return b;
    }

    private void azioneNuovo() throws Exception {
        EditorPersona ed = new EditorPersona(this, null);
        ed.setVisible(true);
        if (ed.isConfermato()) {
            lista.add(ed.getPersona());
            dao.salva(lista);
            aggiornaTabella();
        }
    }

    private void azioneModifica() throws Exception {
        int r = tabella.getSelectedRow();
        if (r == -1) {
            JOptionPane.showMessageDialog(this, "Per modificare è necessario prima selezionare una persona.");
            return;
        }
        EditorPersona ed = new EditorPersona(this, lista.get(r));
        ed.setVisible(true);
        if (ed.isConfermato()) {
            lista.set(r, ed.getPersona());
            dao.salva(lista);
            aggiornaTabella();
        }
    }

    private void azioneElimina() throws Exception {
        int r = tabella.getSelectedRow();
        if (r == -1) {
            JOptionPane.showMessageDialog(this, "Per eliminare è necessario prima selezionare una persona.");
            return;
        }
        Persona p = lista.get(r);
        String msg = "ELIMINARE LA PERSONA " + p.getNome().toUpperCase() + " " + p.getCognome().toUpperCase() + "?";
        if (JOptionPane.showConfirmDialog(this, msg, "Conferma", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            lista.remove(r);
            dao.salva(lista);
            aggiornaTabella();
        }
    }

    private void aggiornaTabella() throws Exception {
        lista = dao.carica();
        model.setRowCount(0);
        for (Persona p : lista) {
            model.addRow(new Object[]{p.getNome(), p.getCognome(), p.getTelefono()});
        }
    }
}
package main;

import javax.swing.JOptionPane;
import dao.UtenteDAO;
import gui.LoginGui;
import gui.MainFrame;
import gui.RegisterGui;

public class Main {
    public static void main(String[] args) {
        mostraLogin();
    }

    public static void mostraLogin() {
        LoginGui loginWindow = new LoginGui();
        loginWindow.setVisible(true);

        loginWindow.setLoginAction(e -> {
            String userInserito = loginWindow.getUsername();
            String passInserita = loginWindow.getPassword();

            UtenteDAO utenteDao = new UtenteDAO();
            
            if (utenteDao.verificaCredenziali(userInserito, passInserita)) {
                loginWindow.dispose();
                try {
                    MainFrame rubrica = new MainFrame();
                    rubrica.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            } else {
                JOptionPane.showMessageDialog(loginWindow, "Credenziali errate!", "Errore Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginWindow.setRegisterAction(e -> {
            RegisterGui registerWindow = new RegisterGui();
            registerWindow.setVisible(true);

            registerWindow.setRegisterAction(ev -> {
                String user = registerWindow.getUsername();
                String pass = registerWindow.getPassword();
                String confirm = registerWindow.getConfirmPassword();

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(registerWindow, "Campi obbligatori!");
                    return;
                }

                if (!pass.equals(confirm)) {
                    JOptionPane.showMessageDialog(registerWindow, "Le password non coincidono!");
                    return;
                }

                UtenteDAO utenteDao = new UtenteDAO();
                if (utenteDao.registraUtente(user, pass)) {
                    JOptionPane.showMessageDialog(registerWindow, "Registrazione completata!");
                    registerWindow.dispose();
                } else {
                    JOptionPane.showMessageDialog(registerWindow, "Errore: username esistente.");
                }
            });
        });
    }
}
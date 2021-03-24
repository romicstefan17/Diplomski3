/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller;

import controller.Controller;
import domain.Admin;
import form.FrmAdminLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import cordinator.MainCordinator;

/**
 *
 * @author SystemX
 */
public class LoginAdminController {

    FrmAdminLogin frmLogin;
    private int counter = 3;
    private String nalepi;

    public LoginAdminController(FrmAdminLogin frmLogin) {
        this.frmLogin = frmLogin;
        addActionListener();
    }

    public void openForm() {

        frmLogin.setTitle("Login");
        frmLogin.setLocationRelativeTo(null);
        nalepi = frmLogin.getLbCounter().getText();
        frmLogin.setVisible(true);

    }

    public void addActionListener() {
        frmLogin.addLoginListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String username = frmLogin.getTxtUsername().getText().trim();
                    String password = String.valueOf(frmLogin.getTxtPassword().getPassword());
                    Admin admin = Controller.getInstance().loginAdmin(username, password);
                    Controller.getInstance().setAdmin(admin);
                    JOptionPane.showMessageDialog(frmLogin, "Uspesno ste se ulogovali: " + Controller.getInstance().getAdmin(), "Cestitamo", JOptionPane.INFORMATION_MESSAGE);
                    MainCordinator.getInstance().openFormMain();
                    frmLogin.dispose();

                } catch (Exception ex) {
                    Logger.getLogger(LoginAdminController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmLogin, ex.getMessage(), "Greska pri logovanju", JOptionPane.ERROR_MESSAGE);
                    counter--;
                    frmLogin.getLbCounter().setText(nalepi + ":" + counter);
                    if (counter == 0) {
                        JOptionPane.showMessageDialog(frmLogin, "Nemate vise pokusaja", "Goodbye", JOptionPane.ERROR_MESSAGE);
                        frmLogin.dispose();
                    }
                }

            }

        });

    }

}

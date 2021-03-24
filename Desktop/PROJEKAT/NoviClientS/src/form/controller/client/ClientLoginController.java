/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.client;

import communication.Communication;
import communication.Operation;
import communication.Request;
import cordinator.MainCordinatorClient;
import domain.User;
import form.client.FrmClientLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author SystemX
 */
public class ClientLoginController {

    FrmClientLogin frmLogin;
    private int counter = 3;

    public ClientLoginController(FrmClientLogin frmLogin) {
        this.frmLogin = frmLogin;
        addActionListener();
    }

    public void openForm() {

        frmLogin.setTitle("Login");
        frmLogin.setLocationRelativeTo(null);
        frmLogin.setVisible(true);

    }

    public void addActionListener() {
        frmLogin.btnLoginAddActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    frmLogin.getLbPasswordError().setText("");
                    frmLogin.getLbUsernameError().setText("");

                    String username = frmLogin.getTxtUsername().getText().trim();

                    String password = String.valueOf(frmLogin.getPswPassword().getPassword());
                    User z = new User();
                    z.setUserName(username);
                    z.setPassword(password);

                    Request request = new Request(Operation.LOGIN, z);
                    Communication.getInstance().sendUserRequest(request);
                  
                } catch (Exception ex) {
                    Logger.getLogger(ClientLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmLogin, ex.getMessage(), "Login Greska", JOptionPane.ERROR_MESSAGE);
                    counter--;
                    frmLogin.getLbCounter().setText("Broj pokusaja:" + counter);
                    if (counter == 0) {
                        JOptionPane.showMessageDialog(frmLogin, "Nemate vise pokusaja", "Goodbye", JOptionPane.ERROR_MESSAGE);
                        frmLogin.dispose();
                    }
                }

            }

        });
        frmLogin.btnSignIn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCordinatorClient.getInstance().openSignIn();
                } catch (Exception ex) {
                    Logger.getLogger(ClientLoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public FrmClientLogin getFrmLogin() {
        return frmLogin;
    }

}

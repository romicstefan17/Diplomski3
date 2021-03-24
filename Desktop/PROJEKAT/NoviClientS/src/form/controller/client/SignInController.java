/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.client;

import communication.Communication;
import domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import communication.Request;
import communication.Operation;
import form.client.FrmSignIn;

/**
 *
 * @author SystemX
 */
public class SignInController {

    FrmSignIn in;

    public SignInController(FrmSignIn in) throws Exception {
        this.in = in;
        addActionListener();
        addWindowListener();
    }

    public void openForm() throws Exception {
        in.setTitle("Sign In");
        in.setLocationRelativeTo(null);
        in.getTxtId().setText("###");
        in.getTxtId().setEnabled(false);

        in.setVisible(true);
    }

    private void addActionListener() {
        in.btnSave(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String ime = in.getTxtIme().getText().trim();
                    String prezime = in.getTxtPrezime().getText().trim();
                    String username = in.getTxtUsername().getText().trim();
                    String password = String.valueOf(in.getTxtPassword().getPassword());
                    String adresa = in.getTxtAdresa().getText().trim();

                    validateUser(ime, prezime, username, password, adresa);
                    validateSlovoIBrojAdresa(adresa);
                    validatSlovoIBrojPasssword(password);

                    User user = new User();

                    user.setFirstName(ime);
                    if (Communication.getInstance().getAllUsers().size() > 0) {
                        user.setUserID(Communication.getInstance().getAllUsers().get(Communication.getInstance().getAllUsers().size() - 1).getUserID() + 1);
                    } else {
                        user.setUserID(1l);
                    }
                    user.setLastName(prezime);
                    user.setPassword(password);
                    user.setUserName(username);
                    user.setAdress(adresa);
                    user.setStatus("izlogovan");
                    user.setUloga("user");

                    int k = validateUser(username, password);
                    if (k == 1) {
                        JOptionPane.showMessageDialog(in, "Uspesna registracija", "Sign in", JOptionPane.INFORMATION_MESSAGE);
                        Request request = new Request(Operation.ADD_USER, user);
                        Communication.getInstance().sendUserRequest(request);

                    }

                    in.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(in, ex.getMessage(), "Neuspesna registracija", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void addWindowListener() {
        in.addSignInWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {

                try {

                    Request request1 = new Request(Operation.GET_ALL_USERS, null);
                    Communication.getInstance().sendUserRequest(request1);
                } catch (Exception ex) {
                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    public int validateUser(String ime, String prezime, String username, String password, String adresa) throws Exception {

        String sb = "";
        int i = 1;

        if (username.isEmpty()) {
            in.getLbErrorUser().setText("Username polje ne sme da bude prazno\n");
            sb += "Username polje ne sme da bude prazno\n";
            i = 0;
        }

        if (password.isEmpty()) {
            in.getLnErrorPass().setText("Password polje ne sme da bude prazno\n");
            sb += "Password polje ne sme da bude prazno\n";
            i = 0;
        }

        if (username.contains(" ")) {
            in.getLbErrorUser().setText("Username polje ne sme da sadrzi blankspace\n");
            sb += "Username polje ne sme da sadrzi blankspace\n";
            i = 0;
        }
        if (password.contains(" ")) {
            in.getLnErrorPass().setText("Password polje ne sme da sadrzi blankspace\n");
            sb += "Password polje ne sme da sadrzi blankspace\n";
            i = 0;
        }
        if (username.length() < 5) {
            in.getLbErrorUser().setText("Username polje ne sme da ima manje od 5 karaktera\n");
            sb += "Username polje ne sme da ima manje od 5 karaktera\n";
            i = 0;
        }
        if (password.length() < 5) {
            in.getLnErrorPass().setText("Password polje ne sme da ima manje od 5 karaktera\n");
            sb += "Password polje ne sme da ima manje od 5 karaktera\n";
            i = 0;
        }

        /////////////////////////////////////////////////////
        if (ime.isEmpty()) {
            in.getLbErrorIme().setText("Polje ime ne sme da bude prazno\n");
            sb += "Polje ime ne sme da bude prazno\n";
            i = 0;
        }

        if (prezime.isEmpty()) {
            in.getLbErrorPrezime().setText("Polje prezime ne sme da bude prazno\n");
            sb += "Polje prezime ne sme da bude prazno\n";
            i = 0;
        }

        if (ime.contains(" ")) {
            in.getLbErrorIme().setText("Polje ime ne sme da sadrzi blankspace\n");
            sb += "Username polje ne sme da sadrzi blankspace\n";
            i = 0;
        }
        if (prezime.contains(" ")) {
            in.getLbErrorPrezime().setText("Polje prezime ne sme da sadrzi blankspace\n");
            sb += "Polje prezime ne sme da sadrzi blankspace\n";
            i = 0;
        }
        //////////////////////////
        if (adresa.isEmpty()) {
            in.getLbErrorAdresa().setText("Polje adresa ne sme da bude prazno\n");
            sb += "Polje adresa ne sme da bude prazno\n";
            i = 0;
        }

        if (adresa.length() < 8) {
            in.getLbErrorAdresa().setText("Polje adresa ne sme da ima manje od 8 karaktera\n");
            sb += "Polje adresa ne sme da ima manje od 8 karaktera\n";
            i = 0;
        }

        if (!sb.isEmpty()) {
            throw new Exception(sb);
        }
        return i;

    }

    private void validateSlovoIBrojAdresa(String adresa) throws Exception {
        String sb = "";
        char ch;
        String numberFlag = "0";
        String capitalFlag = "0";
        String lowerFlag = "0";

        for (int i = 0; i < adresa.length(); i++) {
            ch = adresa.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = "1";
            }
            if (Character.isUpperCase(ch)) {
                capitalFlag = "1";

            }
            if (Character.isLowerCase(ch)) {
                lowerFlag = "1";

            }

        }
        if (capitalFlag.equals("0")) {

            in.getLbErrorAdresa().setText("Polje adresa mora imati veliko slovo\n");
            sb += "Polje adresa mora imati veliko slovo\n";

        }

        if (numberFlag.equals("0")) {
            in.getLbErrorAdresa().setText("Polje adresa mora da ima broj u sebi\n");
            sb += "Polje adresa mora da ima broj u sebi\n";
        }

        if (lowerFlag.equals("0")) {
            in.getLbErrorAdresa().setText("Polje adresa mora imati malo slovo\n");
            sb += "Polje adresa mora imati malo slovo\n";
        }
        if (!sb.isEmpty()) {
            throw new Exception(sb);

        }

    }

    public void validatSlovoIBrojPasssword(String password) throws Exception {
        String sb = "";
        char ch;
        String numberFlag = "0";
        String capitalFlag = "0";
        String lowerFlag = "0";

        for (int i = 0; i < password.length(); i++) {
            ch = password.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = "1";
            }
            if (Character.isUpperCase(ch)) {
                capitalFlag = "1";
            }
            if (Character.isLowerCase(ch)) {
                lowerFlag = "1";

            }

        }
        if (numberFlag.equals("0")) {

            in.getLnErrorPass().setText("Password polje mora da ima jednu cifru u sebi\n");
            sb += "Password polje mora da ima jednu cifru u sebi\n";
        }
        if (capitalFlag.equals("0")) {

            in.getLnErrorPass().setText("Password polje mora imati bar jedno veliko slovo\n");
            sb += "Password polje mora imati bar jedno veliko slovo\n";

        }
        if (lowerFlag.equals("0")) {

            in.getLnErrorPass().setText("Password polje mora imati bar jedno malo slovo\n");
            sb += "Password polje mora imati bar jedno malo slovo\n";

        }
        if (!sb.isEmpty()) {
            throw new Exception(sb);

        }

    }

    public int validateUser(String username, String password) throws Exception {
        List<User> zaposlenis = Communication.getInstance().getAllUsers();
        for (User zaposleni : zaposlenis) {
            if (zaposleni.getUserName().equals(username)) {
                throw new Exception("Promenite username");
            }

            if (zaposleni.getPassword().equals(password)) {
                throw new Exception("Promenite password");
            }

        }
        return 1;
    }

    public FrmSignIn getIn() {
        return in;
    }

}

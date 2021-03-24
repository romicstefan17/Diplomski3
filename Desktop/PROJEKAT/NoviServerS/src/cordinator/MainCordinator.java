/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cordinator;


import form.FrmAdminLogin;
import form.FrmMain;
import form.controller.LoginAdminController;
import form.controller.MainController;


/**
 *
 * @author SystemX
 */
public class MainCordinator {

    private static MainCordinator instance;
    private final MainController mc;
    private LoginAdminController login;
   

    private MainCordinator() {
        mc = new MainController(new FrmMain());
    }

    public static MainCordinator getInstance() {
        if (instance == null) {
            instance = new MainCordinator();
        }
        return instance;
    }

    public void openLoginForm() {
        this.login = new LoginAdminController(new FrmAdminLogin());
        login.openForm();

    }

    public void openFormMain() throws Exception {
        mc.openForm();
    }

    public LoginAdminController getLogin() {
        return login;
    }

    public MainController getMc() {
        return mc;
    }

    
    
}

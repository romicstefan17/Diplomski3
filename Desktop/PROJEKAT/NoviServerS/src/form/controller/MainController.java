/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller;

import controller.Controller;
import domain.User;
import form.FrmMain;
import form.component.TableClientStatusiModel;
import form.component.TableUserCellRenderer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author SystemX
 */
public class MainController {

    FrmMain frmMain;

    String nalepi = "";

    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;
        addActionListener();
        addWindowListener();

    }

    public void openForm() throws Exception {

        frmMain.getBtnStopServer().setEnabled(false);
        nalepi = frmMain.getLbStatus().getText();
        frmMain.getLbStatus().setText(nalepi + " SERVER NIJE POKRENUT");
        frmMain.setTitle("Admin: " + Controller.getInstance().getAdmin());
        frmMain.setLocationRelativeTo(null);
        frmMain.setVisible(true);

    }

    private void addActionListener() {
        frmMain.btnStartServerActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frmMain.getBtnStartServer().setEnabled(false);
                frmMain.getLbStatus().setText(nalepi + " SERVER JE POKRENUT");
                frmMain.getLbStatus().setForeground(Color.green);

                frmMain.getBtnStopServer().setEnabled(true);

                Controller.getInstance().startServer();
            }
        });
        frmMain.btnStopServerActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    for (User z : Controller.getInstance().getAllUsers()) {
                        z.setStatus("izlogovan");
                        Controller.getInstance().updateUser(z);
                        Controller.getInstance().stopServer();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }

                frmMain.getBtnStartServer().setEnabled(true);
                frmMain.getLbStatus().setText(nalepi + " SERVER JE UGASEN");
                frmMain.getLbStatus().setForeground(Color.red);
                frmMain.getBtnStopServer().setEnabled(false);

            }
        });

    }

    public void fillTbl(List<User> users) throws Exception {
        TableClientStatusiModel model = new TableClientStatusiModel(users);

        frmMain.getTblKorisnici().setModel(model);
        TableColumnModel tcm = frmMain.getTblKorisnici().getColumnModel();
        tcm.getColumn(3).setCellRenderer(new TableUserCellRenderer());
    }

    private void addWindowListener() {

        frmMain.addWindowListeneronClosed(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    for (User z : Controller.getInstance().getAllUsers()) {
                        z.setStatus("izlogovan");
                        Controller.getInstance().updateUser(z);
                        System.out.println("zatvoren");
                        Controller.getInstance().stopServer();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        frmMain.addWindowListenerOpened(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                try {
                    fillTbl(Controller.getInstance().getAllUsers());
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public FrmMain getFrmMain() {
        return frmMain;
    }

}

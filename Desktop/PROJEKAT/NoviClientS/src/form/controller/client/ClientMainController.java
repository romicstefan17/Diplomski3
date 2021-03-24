
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.client;

import communication.Communication;
import cordinator.MainCordinatorClient;
import domain.Product;
import form.component.TableProductModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import communication.Operation;
import communication.Request;
import form.client.FrmMainClient;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author SystemX
 */
public class ClientMainController {

    FrmMainClient fmn;

    public ClientMainController(FrmMainClient fmn) {
        this.fmn = fmn;
        addActionListener();
        addWindowListener();

    }

    public void openForm() throws Exception {

        fmn.setTitle("Lista proizvoda : ");
        fmn.setLocationRelativeTo(null);
        fmn.setVisible(true);
    }

    private void addActionListener() {
        fmn.btnOtvoriNarudzbeActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCordinatorClient.getInstance().openFormNarudzba();
                } catch (Exception ex) {
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fmn.btnListaNActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCordinatorClient.getInstance().openFormListaNarudzbi();
                } catch (Exception ex) {
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fmn.addBtnDetailActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = fmn.getTblProizvodi().getSelectedRow();
                    TableProductModel model = new TableProductModel(Communication.getInstance().getAllProducts());
                    if (row != -1) {
                        Product p = model.getProizvod(row);
                        MainCordinatorClient.getInstance().openFormDetailsProizvod(p);
                    } else {
                        JOptionPane.showMessageDialog(fmn, "Morate da selektujete red", "Greska", JOptionPane.INFORMATION_MESSAGE);

                    }
                } catch (Exception ex) {
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fmn.addBtnReviewActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = fmn.getTblProizvodi().getSelectedRow();
                    TableProductModel model = new TableProductModel(Communication.getInstance().getAllProducts());
                    if (row != -1) {
                        Product p = model.getProizvod(row);
                        MainCordinatorClient.getInstance().openFormReview(p);

                    } else {
                        JOptionPane.showMessageDialog(fmn, "Morate da selektujete red", "Greska", JOptionPane.INFORMATION_MESSAGE);

                    }
                } catch (Exception ex) {
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fmn.getBtnLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int k = JOptionPane.showConfirmDialog(fmn, "Da li ste sigurni?", "LogOut", JOptionPane.YES_NO_OPTION);
                if (k == JOptionPane.YES_OPTION) {

                    fmn.dispose();
                    JOptionPane.showMessageDialog(fmn, "Uspesno ste izlogovali");
                    MainCordinatorClient.getInstance().openLogIn();

                    try {
                        Request request = new Request(Operation.SIGN_OUT, Communication.getInstance().getUser());
                        Communication.getInstance().sendUserRequest(request);
                        System.out.println("" + request.getArgument());
                    } catch (Exception ex) {
                        Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        fmn.getTxtSearch().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                TableProductModel model = (TableProductModel) fmn.getTblProizvodi().getModel();
                String search = fmn.getTxtSearch().getText().trim();
                TableRowSorter<TableProductModel> tr = new TableRowSorter<TableProductModel>(model);
                fmn.getTblProizvodi().setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter(search));
                if (fmn.getTblProizvodi().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(fmn, "Sistem ne moze da nadje proizvode po zadatoj vrednosti", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        });

    }

    public void fillTbl(List<Product> proizvodi) throws Exception {

        TableProductModel model = new TableProductModel(proizvodi);
        fmn.getTblProizvodi().setModel(model);
    }

    private void addWindowListener() {
        fmn.addMainWindowListeenr(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int k = JOptionPane.showConfirmDialog(fmn, "Da li ste sigurni?", "LogOut", JOptionPane.YES_NO_OPTION);
                if (k == JOptionPane.YES_OPTION) {

                    fmn.dispose();
                    JOptionPane.showMessageDialog(fmn, "Uspesno ste izlogovali");
                    MainCordinatorClient.getInstance().openLogIn();

                    try {
                        Request request = new Request(Operation.SIGN_OUT, Communication.getInstance().getUser());
                        Communication.getInstance().sendUserRequest(request);
                        System.out.println("" + request.getArgument());
                    } catch (Exception ex) {
                        Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void windowActivated(WindowEvent e) {

                try {
                    fillTbl(new ArrayList<>());
                    Request request = new Request(Operation.GET_ALL_PRODUCTS, null);
                    Communication.getInstance().sendUserRequest(request);

                    Request request1 = new Request(Operation.GET_ALL_REVIEWS, null);
                    Communication.getInstance().sendUserRequest(request1);
                } catch (Exception ex) {
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public FrmMainClient getFmn() {
        return fmn;
    }

    void updateTableProduct(Product p) throws Exception {
        Request request = new Request(Operation.GET_ALL_PRODUCTS, null);
        Communication.getInstance().sendUserRequest(request);
    }

}

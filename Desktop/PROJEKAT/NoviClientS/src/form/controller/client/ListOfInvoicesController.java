/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.client;

import communication.Communication;
import cordinator.MainCordinatorClient;
import domain.InvoiceUser;
import domain.User;
import form.component.TableInvoiceUserModel;
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
import form.client.FrmListOfInvoices;

/**
 *
 * @author SystemX
 */
public class ListOfInvoicesController {

    FrmListOfInvoices floi;

    public ListOfInvoicesController(FrmListOfInvoices floi) {
        this.floi = floi;
        addWindowListener();
        addActionListener();

    }

    public void openForm() throws Exception {
        floi.setTitle("Narudzbe od: "+Communication.getInstance().getUser());
        floi.setLocationRelativeTo(null);
        floi.setVisible(true);
    }

    private void addWindowListener() {
        floi.frmListaNarudzbiWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                setTblAsocijacija(new ArrayList<>());
                User z = null;
                try {
                        z = Communication.getInstance().getUser();
                } catch (Exception ex) {
                    Logger.getLogger(ListOfInvoicesController.class.getName()).log(Level.SEVERE, null, ex);
                }

                 InvoiceUser a = new InvoiceUser();
                   a.setUser(z);
                try {
                        Request req = new Request(Operation.GET_INVOICEUSER, a);
                        Communication.getInstance().sendUserRequest(req);

                } catch (Exception ex) {
                    Logger.getLogger(ListOfInvoicesController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
    }

    private void addActionListener() {
        floi.btnCancel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                floi.dispose();
            }
        });
        floi.btnDetalji(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = floi.getTblListaNarudzbi().getSelectedRow();

                    if (row != -1) {

                           TableInvoiceUserModel model = (TableInvoiceUserModel) floi.getTblListaNarudzbi().getModel();
                            InvoiceUser a = model.getAsocijacija(row);
                           MainCordinatorClient.getInstance().openFormNarudzbaDetails(a);
                    } else {
                              JOptionPane.showMessageDialog(floi, "Morate da selektujete red", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ListOfInvoicesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        floi.btnNovaNarudzbaa(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCordinatorClient.getInstance().openFormNarudzba();
                } catch (Exception ex) {
                    Logger.getLogger(ListOfInvoicesController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        floi.btnObrisiNarudzbu(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = floi.getTblListaNarudzbi().getSelectedRow();
                    if (row != -1) {

                         TableInvoiceUserModel model = (TableInvoiceUserModel) floi.getTblListaNarudzbi().getModel();
                           model.remove(row);
                        //obrisao table refresh
                        JOptionPane.showMessageDialog(floi, "Uspesno brisanje narudzbe", "", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(floi, "Morate da selektujete red", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ListOfInvoicesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    public void setTblAsocijacija(List<InvoiceUser> asocijacije) {

        try {

              TableInvoiceUserModel model = new TableInvoiceUserModel(asocijacije);
             floi.getTblListaNarudzbi().setModel(model);
        } catch (Exception ex) {
            Logger.getLogger(ListOfInvoicesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

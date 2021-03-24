/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.admin;

import communication.Communication;
import communication.Operation;
import communication.Request;
import cordinator.MainCordinatorAdmin;
import domain.InvoiceUser;
import form.admin.FrmListOfInvoiceA;
import form.component.TableInvoiceUserModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author SystemX
 */
public class ListOfInvoiceController {

    FrmListOfInvoiceA frmListOfInvoice;

    public ListOfInvoiceController(FrmListOfInvoiceA frmListOfInvoice) {
        this.frmListOfInvoice = frmListOfInvoice;
        addActionListener();
        addWindowListener();
    }

    public void openForm() throws Exception {
        frmListOfInvoice.setTitle("Lista narudzbi: " + Communication.getInstance().getUser());

        frmListOfInvoice.setLocationRelativeTo(null);
        frmListOfInvoice.setVisible(true);
    }

    private void addActionListener() {
        frmListOfInvoice.addBtnCancelNarudzbaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmListOfInvoice.dispose();
            }
        });
        frmListOfInvoice.addBtnDetaljiNarudzbaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = frmListOfInvoice.getTblListaNarudzbi().getSelectedRow();

                    if (row != -1) {
                        InvoiceUser iu = Communication.getInstance().getUi().get(row);

                        MainCordinatorAdmin.getInstance().openFormNarudzbaDetails(iu);
                    } else {
                        JOptionPane.showMessageDialog(frmListOfInvoice, "Morate da selektujete red", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ListOfInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        frmListOfInvoice.addBtnObrisiNarudzbaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = frmListOfInvoice.getTblListaNarudzbi().getSelectedRow();
                    if (row != -1) {

                        TableInvoiceUserModel model = (TableInvoiceUserModel) frmListOfInvoice.getTblListaNarudzbi().getModel();
                        model.remove(row);
                        
                        JOptionPane.showMessageDialog(frmListOfInvoice, "Uspesno brisanje narudzbe", "", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(frmListOfInvoice, "Morate da selektujete red", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ListOfInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    private void addWindowListener() {

        frmListOfInvoice.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                try {
                    Request request = new Request(Operation.GET_ALL_INVOICEUSERS, null);
                    Communication.getInstance().sendUserRequest(request);
                } catch (Exception ex) {
                    Logger.getLogger(ListOfInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void setTblAsocijacija(List<InvoiceUser> iulist) throws Exception {

        TableInvoiceUserModel model = new TableInvoiceUserModel(iulist);
        frmListOfInvoice.getTblListaNarudzbi().setModel(model);
    }

}

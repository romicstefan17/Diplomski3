/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.admin;

import communication.Communication;
import communication.Operation;
import communication.Request;
import domain.InvoiceItem;
import domain.InvoiceUser;
import form.admin.FrmInvoiceDetails;
import form.component.TableInvoiceDetailsModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author SystemX
 */
public class InvoiceDetailsController {

    FrmInvoiceDetails frmInvoiceDetails;
    InvoiceUser iu;

    public InvoiceDetailsController(FrmInvoiceDetails frmInvoiceDetails) {
        this.frmInvoiceDetails = frmInvoiceDetails;
        addActionListener();
        addWindowListener();
    }

    public void openForm(InvoiceUser ui) throws Exception {
        this.iu=ui;
        frmInvoiceDetails.setTitle("Detalji narudzbe");
        frmInvoiceDetails.setLocationRelativeTo(null);
        frmInvoiceDetails.setVisible(true);
    }

    public void fillItems(List<InvoiceItem>sn) throws Exception {

        List<InvoiceItem> stavke = new ArrayList<>();
        
        int i = 0;
       
       
        while (i < sn.size()) {
            if (Objects.equals(sn.get(i).getId(), iu.getInvoice().getInvoiceID())) {
                System.out.println("jesu jednaki");
                stavke.add(sn.get(i));
            }
            i++;
        }

        frmInvoiceDetails.getTxtDatum().setEnabled(false);
        frmInvoiceDetails.getTxtIme().setEnabled(false);
        frmInvoiceDetails.getTxtId().setEnabled(false);
        frmInvoiceDetails.getTxtPrezime().setEnabled(false);
        frmInvoiceDetails.getTxtSifra().setEnabled(false);
        frmInvoiceDetails.getTxtIznos().setEnabled(false);

        TableInvoiceDetailsModel model = new TableInvoiceDetailsModel(stavke);

        frmInvoiceDetails.getTblFakturaModel().setModel(model);

        frmInvoiceDetails.getTxtId().setText(String.valueOf(iu.getInvoice().getInvoiceID()));
        frmInvoiceDetails.getTxtSifra().setText(String.valueOf(iu.getInvoice().getCode()));
        frmInvoiceDetails.getTxtDatum().setText(iu.getInvoice().getDate().toString());
        frmInvoiceDetails.getTxtIznos().setText(String.valueOf(iu.getInvoice().getPrice()));
        frmInvoiceDetails.getTxtIme().setText(iu.getUser().getFirstName());
        frmInvoiceDetails.getTxtPrezime().setText(iu.getUser().getLastName());

    }

    private void addActionListener() {
        frmInvoiceDetails.btnZatvori(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmInvoiceDetails.dispose();
            }
        });
        frmInvoiceDetails.getTxtSearch().addKeyListener(new KeyListener() {
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
                TableInvoiceDetailsModel model = (TableInvoiceDetailsModel) frmInvoiceDetails.getTblFakturaModel().getModel();
                String search = frmInvoiceDetails.getTxtSearch().getText().trim();
                TableRowSorter<TableInvoiceDetailsModel> tr = new TableRowSorter<TableInvoiceDetailsModel>(model);
                frmInvoiceDetails.getTblFakturaModel().setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter(search));
                if (frmInvoiceDetails.getTblFakturaModel().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(frmInvoiceDetails, "Nema stavki pod tim kriterijumom", "", JOptionPane.INFORMATION_MESSAGE);
                }

            }

        });
    }

    private void addWindowListener() {
        frmInvoiceDetails.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                try {
                    Request request = new Request(Operation.GET_ALL_ITEMS, null);
                    Communication.getInstance().sendUserRequest(request);
                } catch (Exception ex) {
                    Logger.getLogger(InvoiceDetailsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}

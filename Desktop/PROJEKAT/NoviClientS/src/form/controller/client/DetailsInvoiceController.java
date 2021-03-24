/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.client;

import communication.Communication;
import domain.InvoiceItem;
import domain.InvoiceUser;
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
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import communication.Operation;
import communication.Request;
import form.client.FrmDetailsOfInvoice;
import javax.swing.JOptionPane;

/**
 *
 * @author SystemX
 */
public class DetailsInvoiceController {

    FrmDetailsOfInvoice detailsNarudzbe;
    private InvoiceUser a;

    public DetailsInvoiceController(FrmDetailsOfInvoice detailsNarudzbe) {
        this.detailsNarudzbe = detailsNarudzbe;
        addActionListener();
        addWindowListener();
    }

    public void openForm(InvoiceUser a) throws Exception {
        
        detailsNarudzbe.setTitle("narudzba detalji");
        detailsNarudzbe.setLocationRelativeTo(null);
        setA(a);
        detailsNarudzbe.setVisible(true);

    }

    public void fillItems(List<InvoiceItem>sn) throws Exception {

        List<InvoiceItem> stavke = new ArrayList<>();
        
        int i = 0;
       
       
        while (i < sn.size()) {
            if (Objects.equals(sn.get(i).getId(), a.getInvoice().getInvoiceID())) {
                System.out.println("jesu jednaki");
                stavke.add(sn.get(i));
            }
            i++;
        }

        detailsNarudzbe.getTxtDatum().setEnabled(false);
        detailsNarudzbe.getTxtIme().setEnabled(false);
        detailsNarudzbe.getTxtId().setEnabled(false);
        detailsNarudzbe.getTxtPrezime().setEnabled(false);
        detailsNarudzbe.getTxtSifra().setEnabled(false);
        detailsNarudzbe.getTxtIznos().setEnabled(false);

        TableInvoiceDetailsModel model = new TableInvoiceDetailsModel(stavke);

        detailsNarudzbe.getTblFakturaModel().setModel(model);

        detailsNarudzbe.getTxtId().setText(String.valueOf(a.getInvoice().getInvoiceID()));
        detailsNarudzbe.getTxtSifra().setText(String.valueOf(a.getInvoice().getCode()));
        detailsNarudzbe.getTxtDatum().setText(a.getInvoice().getDate().toString());
        detailsNarudzbe.getTxtIznos().setText(String.valueOf(a.getInvoice().getPrice()));
        detailsNarudzbe.getTxtIme().setText(a.getUser().getFirstName());
        detailsNarudzbe.getTxtPrezime().setText(a.getUser().getLastName());

    }

    private void addWindowListener() {
        detailsNarudzbe.addBtnWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {

                try {
                    Request request = new Request(Operation.GET_ALL_ITEMS, null);
                    Communication.getInstance().sendUserRequest(request);
                } catch (Exception ex) {
                    Logger.getLogger(DetailsInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    private void addActionListener() {
        detailsNarudzbe.btnZatvori(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detailsNarudzbe.dispose();
            }
        });
        detailsNarudzbe.getTxtSearch().addKeyListener(new KeyListener() {
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
                TableInvoiceDetailsModel model = (TableInvoiceDetailsModel) detailsNarudzbe.getTblFakturaModel().getModel();
                String search = detailsNarudzbe.getTxtSearch().getText().trim();
                TableRowSorter<TableInvoiceDetailsModel> tr = new TableRowSorter<TableInvoiceDetailsModel>(model);
                detailsNarudzbe.getTblFakturaModel().setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter(search));
               if (detailsNarudzbe.getTblFakturaModel().getRowCount()==0) {
                    JOptionPane.showMessageDialog(detailsNarudzbe, "Nema stavki pod tim kriterijumom","", JOptionPane.INFORMATION_MESSAGE);
                }

            }

        });
    }

    public void setA(InvoiceUser a) {
        this.a = a;
    }

    public InvoiceUser getA() {
        return a;
    }

}

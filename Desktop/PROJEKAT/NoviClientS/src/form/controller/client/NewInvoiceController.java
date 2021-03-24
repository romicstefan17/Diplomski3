/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.client;

import communication.Communication;
import domain.Invoice;
import form.component.TableInvoiceModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import communication.Operation;
import communication.Request;
import cordinator.MainCordinatorClient;
import domain.InvoiceItem;
import domain.InvoiceUser;
import domain.Product;
import domain.Supplier;
import domain.User;
import form.client.FrmNewInvoice;

/**
 *
 * @author SystemX
 */
public class NewInvoiceController {

    FrmNewInvoice frmFaktura;
    private static final String ALLOWED_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private List<Supplier> suppliers = new ArrayList<>();
    Product poslednjeObrisanProizvod;
    Product p;

    private static String randomSifra(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);

        for (int i = 0; i < sizeOfRandomString; ++i) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }
        return sb.toString();
    }

    public NewInvoiceController(FrmNewInvoice frmFaktura) {
        this.frmFaktura = frmFaktura;
        addWindowListener();

        addActionListener();

    }

    public void openForm() throws Exception {
        prepareView();
        frmFaktura.setTitle("Faktura");

        frmFaktura.setLocationRelativeTo(null);
        frmFaktura.setVisible(true);

    }

    private void addWindowListener() {
        frmFaktura.frmFakturaWidnowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    Request request = new Request(Operation.GET_ALL_PROIZVODI_CB, null);
                    Communication.getInstance().sendUserRequest(request);

                    Request request1 = new Request(Operation.GET_USER, null);
                    Communication.getInstance().sendUserRequest(request1);
                    Request request2 = new Request(Operation.GET_ALL_INVOICES, null);
                    Communication.getInstance().sendUserRequest(request2);
                } catch (Exception ex) {
                    Logger.getLogger(NewInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    private void prepareView() throws Exception {

        frmFaktura.getTxtSifra().setText(randomSifra(8));
        frmFaktura.getTxtSifra().setEnabled(false);

        fillDate();
        setTblFaktura(new Invoice());

        frmFaktura.getTxtNarudzbaId().setEnabled(false);
        frmFaktura.getTxtUkupanIznos().setText("0");

        frmFaktura.Naziv();
        frmFaktura.getTxtNarudzbaId().setText("Auto");

    }

    private void setComboBox() throws Exception {
        TableColumnModel tcm = frmFaktura.getTblStavkeNarudzbenice().getColumnModel();

        TableColumn tc2 = tcm.getColumn(5);
        Request request = new Request(Operation.GET_ALL_SUPPLIERS, null);
        Communication.getInstance().sendUserRequest(request);
        JComboBox cb2 = new JComboBox(suppliers.toArray());
        tc2.setCellEditor(new DefaultCellEditor(cb2));
    }

    public void fillZaposleni(User zaposleni) throws Exception {

        System.out.println("" + zaposleni);
        frmFaktura.getTxtImeZaposlenog1().setText(zaposleni.getFirstName());
        frmFaktura.getTxtPrezimeZaposlenog1().setText(zaposleni.getLastName());
        frmFaktura.getTxtImeZaposlenog1().setEditable(false);
        frmFaktura.getTxtPrezimeZaposlenog1().setEditable(false);

    }

    private void fillDate() {
        String db = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        frmFaktura.getTxtDatum().setText(db);
        frmFaktura.getTxtDatum().setEditable(false);
    }

    private void setTblFaktura(Invoice n) {
        try {
            TableInvoiceModel model = new TableInvoiceModel(n);
            frmFaktura.getTblStavkeNarudzbenice().setModel(model);

            setComboBox();
        } catch (Exception ex) {
            Logger.getLogger(NewInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addActionListener() {
        frmFaktura.addActionListenerListOfProducts(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCordinatorClient.getInstance().openFormListOfProducts();
                } catch (Exception ex) {
                    Logger.getLogger(NewInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        frmFaktura.btnCancel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmFaktura.dispose();
            }
        });

        frmFaktura.saveNarudzbu(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    TableInvoiceModel model = (TableInvoiceModel) frmFaktura.getTblStavkeNarudzbenice().getModel();
                    Invoice n = model.getNarudzba();
                    n.setDate(new Date());
                    n.setCode(frmFaktura.getTxtSifra().getText().trim());
                    frmFaktura.getTxtNarudzbaId().setText(String.valueOf(n.getInvoiceID()));

                    for (InvoiceItem ii : n.getListOfInvoice()) {
                        ii.setInvoice(n);
                    }

                    InvoiceUser a = new InvoiceUser();
                    a.setUser(Communication.getInstance().getUser());

                    if (!frmFaktura.getTxtUkupanIznos().getText().equals("0")) {
                        int k = JOptionPane.showConfirmDialog(frmFaktura, "da li ste sigurni", "narudzba", JOptionPane.YES_NO_OPTION);
                        if (k == JOptionPane.YES_OPTION) {
                            Request request = new Request(Operation.ADD_INVOICE, n);
                            Communication.getInstance().sendUserRequest(request);

                            Request request2 = new Request(Operation.ADD_INVOICEUSER, a);
                            Communication.getInstance().sendUserRequest(request2);

                            for (InvoiceItem item : n.getListOfInvoice()) {
                                Request request3 = new Request(Operation.UPDATE_PRODUXT, item.getProduct());
                                Communication.getInstance().sendUserRequest(request3);
                            }

                            MainCordinatorClient.getInstance().getMainController().updateTableProduct(p);
                            isprazni(n);
                            JOptionPane.showMessageDialog(frmFaktura, "Sistem је sacuvao narudzbu");

                        }
                    } else {
                        JOptionPane.showMessageDialog(frmFaktura, "Narudzba ne sme biti prazna", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmFaktura, "Sistem ne moze da unese narudzbu ! " + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);

                }
            }

            private void isprazni(Invoice n) {
                frmFaktura.getTxtUkupanIznos().setText("");
                frmFaktura.getTxtSifra().setText("");
                frmFaktura.getTxtUkupanIznos().setText("0");
                frmFaktura.getTxtNarudzbaId().setText("Auto");

            }
        });
        frmFaktura.obrisi(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmFaktura.getTblStavkeNarudzbenice().getSelectedRow();
                TableInvoiceModel model = (TableInvoiceModel) frmFaktura.getTblStavkeNarudzbenice().getModel();
                if (row >= 0) {
                    int k = JOptionPane.showConfirmDialog(frmFaktura, "Da li ste sigurni", "Brisanje", JOptionPane.YES_NO_OPTION);
                    if (k == JOptionPane.YES_OPTION) {

                        model.obrisi(row);

                        BigDecimal iznos = model.getNarudzba().getPrice();
                        frmFaktura.getTxtUkupanIznos().setText(String.valueOf(iznos));
                        JOptionPane.showMessageDialog(frmFaktura, "Uspesno ste obrsisali", "Brisanje", JOptionPane.INFORMATION_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(frmFaktura, "Morate da oznacite red", "Brisanje", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    public void setSuppliers(List<Supplier> dobavljaci) {
        this.suppliers = dobavljaci;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public Product getPoslednjeObrisanProizvod() {
        return poslednjeObrisanProizvod;
    }

    public void setPoslednjeObrisanProizvod(Product poslednjeObrisanProizvod) {
        this.poslednjeObrisanProizvod = poslednjeObrisanProizvod;
    }

    public void addInvoiceItem(InvoiceItem item) {
        TableInvoiceModel model = (TableInvoiceModel) frmFaktura.getTblStavkeNarudzbenice().getModel();
        try {

            if (!model.sadrzi(item)) {
                model.DodajStavku(item);
                BigDecimal iznos = model.getNarudzba().getPrice();
                frmFaktura.getTxtUkupanIznos().setText(String.valueOf(iznos));
            } else {
                model.update(item);
                BigDecimal iznos = model.getNarudzba().getPrice();
                frmFaktura.getTxtUkupanIznos().setText(String.valueOf(iznos));
            }
        } catch (Exception ex) {
            Logger.getLogger(NewInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

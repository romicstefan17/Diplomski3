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
import domain.InvoiceItem;
import domain.Product;
import form.client.FrmListOfProductsInvoice;
import form.component.TableProductModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class ClientProductInvoiceController {

    FrmListOfProductsInvoice productInvoice;

    public ClientProductInvoiceController(FrmListOfProductsInvoice productInvoice) throws Exception {
        this.productInvoice = productInvoice;
        addActionListener();
        addWindowListener();
    }

    public void openForm() throws Exception {

        productInvoice.setTitle("lista proizvoda");
        productInvoice.setLocationRelativeTo(null);
        productInvoice.getTxtMernaJedinica().setText("komad");
        productInvoice.setVisible(true);

    }

    private void addActionListener() {
        productInvoice.addBtnListenerAddProduct(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = productInvoice.getTblProducts().getSelectedRow();
                Product product = ((TableProductModel) productInvoice.getTblProducts().getModel()).getProizvod(row);
                String kolicina = productInvoice.getTxtKolicinaProizvoda().getText().trim();
                if (row >= 1) {
                    try {
                        if (provera(kolicina, product).equals("0")) {
                            InvoiceItem item = new InvoiceItem();
                           
                            item.setProduct(product);
                            item.setAmount(Integer.parseInt(productInvoice.getTxtKolicinaProizvoda().getText().trim()));
                            item.setPrice(product.getPrice().multiply(new BigDecimal(productInvoice.getTxtKolicinaProizvoda().getText())));
                            product.setAmount(product.getAmount() - item.getAmount());

                            MainCordinatorClient.getInstance().getNewInvoiceController().addInvoiceItem(item);
                            productInvoice.getTxtKolicinaProizvoda().setText("1");
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ClientProductInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(productInvoice, ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(productInvoice, "Morate da selektujete red");
                }
            }

            private String provera(String kolicina, Product p) throws Exception {
                String i = "0";
                String sb = "";

                if (Integer.parseInt(kolicina) < 1) {

                    sb += "Polje kolicina ne sme biti manje od 1\n";
                    i = "1";
                }
                if (Integer.parseInt(kolicina) > p.getAmount()) {

                    sb += "Na stanju imamo " + p.getAmount() + " komada izabranog proizvoda\n";
                    i = "1";
                }

                if (i.equals("1")) {
                    throw new Exception(sb);
                }
                return i;

            }
        });
        productInvoice.addBtnListenerDetailProduct(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = productInvoice.getTblProducts().getSelectedRow();
                Product product = ((TableProductModel) productInvoice.getTblProducts().getModel()).getProizvod(row);
                if (row >= 1) {
                    try {
                        MainCordinatorClient.getInstance().openFormDetailsProizvod(product);
                    } catch (Exception ex) {
                        Logger.getLogger(ClientProductInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(productInvoice, "Morate da selektujete red");
                }
            }
        });

    }

    private void addWindowListener() throws Exception {
        Request request = new Request(Operation.GET_ALL_PRODUCTS, null);
        Communication.getInstance().sendUserRequest(request);
    }

    public void fillTblProducts(List<Product> proizvodi) throws Exception {
        TableProductModel model = new TableProductModel(proizvodi);
        productInvoice.getTblProducts().setModel(model);
    }

}

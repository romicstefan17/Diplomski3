/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.client;

import domain.Product;
import form.client.FrmDetailProduct;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

/**
 *
 * @author SystemX
 */
public class ProductDetailController {

    FrmDetailProduct frmNewProduct;

    public ProductDetailController(FrmDetailProduct frmNewProduct, Product p) throws Exception {
        this.frmNewProduct = frmNewProduct;
        addActionListener();
        mode(p);
    }

    public void openForm() {
        frmNewProduct.setTitle("Informacije o proizvodu");
        frmNewProduct.setLocationRelativeTo(null);
        frmNewProduct.setVisible(true);
    }

    private void addActionListener() {
        frmNewProduct.getBtnCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmNewProduct.dispose();
            }
        });
    }

    private void mode(Product p) throws Exception {

        frmNewProduct.getTxtProizvodjac().setEnabled(false);
        frmNewProduct.getTxtDobavljac().setEnabled(false);
        frmNewProduct.getTxtVrsta().setEnabled(false);
        frmNewProduct.getTxtCena().setEnabled(false);
        frmNewProduct.getTxtModel().setEnabled(false);
        frmNewProduct.getTxtProizvodjacId().setEnabled(false);
        frmNewProduct.getTxtKolicina().setEnabled(false);
        frmNewProduct.getTxtOcena().setEnabled(false);

        frmNewProduct.getTxtCena().setText(String.valueOf(p.getPrice()));
        frmNewProduct.getTxtModel().setText(String.valueOf(p.getModel()));
        frmNewProduct.getTxtProizvodjacId().setText(String.valueOf(p.getProductID()));
        frmNewProduct.getTxtDobavljac().setText(String.valueOf(p.getSupplier().getName()));
        frmNewProduct.getTxtProizvodjac().setText(String.valueOf(p.getManufacturer().getName()));
        frmNewProduct.getTxtVrsta().setText(String.valueOf(p.getType().getNazivVrste()));
        frmNewProduct.getTxtKolicina().setText(String.valueOf(p.getAmount()));
        frmNewProduct.getTxtOcena().setText(String.valueOf(p.getOcena()));
        frmNewProduct.getLblPoster().setIcon(new ImageIcon(
                p.getPicture().getPosterImage().getScaledInstance(frmNewProduct.getLblPoster().getWidth(),
                        frmNewProduct.getLblPoster().getHeight(), Image.SCALE_SMOOTH)));

    }

}

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
import domain.Manufacturer;
import domain.Product;
import domain.ProductPicture;
import domain.ProductType;
import domain.Supplier;
import form.admin.FrmNewProduct;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.FormMode;

/**
 *
 * @author SystemX
 */
public class ProductNewController {

    FrmNewProduct frmNewProduct;
    Product product;
    boolean postoji = false;
    String nalepi = "LISTA KOMENTARA:\n";
    List<Product> proizvodi;
    FormMode formMode;

    public ProductNewController(FrmNewProduct frmNewProduct, FormMode formMode, Product p) throws Exception {
        this.frmNewProduct = frmNewProduct;
        this.product = new Product();
        this.proizvodi = new ArrayList<>();
        prepareView();
        addActionListener();
        addWindowListener();
        if (p != null) {
            this.product = p;
        }
        mode(formMode, p);
        this.formMode = formMode;
    }

    public void openForm() {
        frmNewProduct.setTitle("Proizvod");
        frmNewProduct.setLocationRelativeTo(null);
        frmNewProduct.getTxtProizvodId().setEnabled(false);
        frmNewProduct.setVisible(true);
    }

    public void fillcbProizvodjac() throws Exception {

        frmNewProduct.getCbProizvodjac().removeAllItems();

        for (Manufacturer proizvodjac : Communication.getInstance().getManufacturers()) {
            frmNewProduct.getCbProizvodjac().addItem(proizvodjac);
        }
    }

    public void fillCbVrsta() throws Exception {

        frmNewProduct.getCbVrsta().removeAllItems();

        for (ProductType vrsta : Communication.getInstance().getType()) {
            frmNewProduct.getCbVrsta().addItem(vrsta);
        }
    }

    private void prepareView() throws Exception {
        fillCbDobavljac();
        fillCbVrsta();
        fillcbProizvodjac();
        this.nalepi += Communication.getInstance().getKomentari();
    }

    private void addActionListener() {
        frmNewProduct.addBtnUploadPosterActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");

                chooser.setFileFilter(filter);
                chooser.setDialogTitle("Upload sliku");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnValue = chooser.showOpenDialog(frmNewProduct);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File poster = chooser.getSelectedFile();
                    BufferedImage bi;
                    try {
                        bi = ImageIO.read(poster);
                        frmNewProduct.getLblPoster().setIcon(new ImageIcon(bi.getScaledInstance(frmNewProduct.getLblPoster().getWidth(),
                                frmNewProduct.getLblPoster().getHeight(), Image.SCALE_SMOOTH)));

                        ProductPicture pp = new ProductPicture();
                        pp.setPictureID(0l);
                        pp.setPosterImage(bi);

                        product.setPicture(pp);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        frmNewProduct.btnDeleteProduct(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    provera();
                    brisanje();
                    frmNewProduct.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmNewProduct, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void brisanje() {
                try {
                    int k = JOptionPane.showConfirmDialog(frmNewProduct, "Da li ste sigurni", "Brisanje", JOptionPane.YES_NO_OPTION);
                    if (k == JOptionPane.YES_OPTION) {
                        Request request = new Request(Operation.DELETE_PRODUCT, product);
                        Communication.getInstance().sendUserRequest(request);
                        JOptionPane.showMessageDialog(frmNewProduct, "Sistem је uklonio proizvod", "Brisanje", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            private void provera() throws Exception {
                List<InvoiceItem> items = Communication.getInstance().getItems();
                for (InvoiceItem item : items) {
                    if (Objects.equals(item.getProduct().getModel(), product.getModel())) {
                        throw new Exception("Ne mozete da obrisete proizvod koji je deo narudzbe");
                    }
                }
            }
        });
        frmNewProduct.btnEditProduct(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmNewProduct.getCbProizvodjac().setEnabled(true);
                frmNewProduct.getCbDobavljac().setEnabled(true);
                frmNewProduct.getCbVrsta().setEnabled(true);
                frmNewProduct.getTxtCena().setEnabled(true);
                frmNewProduct.getTxtModel().setEnabled(true);
                frmNewProduct.getTxtProizvodjacId().setEnabled(false);
                frmNewProduct.getBtnSave().setEnabled(true);
                frmNewProduct.getBtnDelete().setEnabled(true);
                frmNewProduct.getTxtKolicina().setEnabled(true);
                frmNewProduct.getBtnUploadPoster().setEnabled(true);
                frmNewProduct.getTxtOcena().setEnabled(false);

            }
        });
        frmNewProduct.btnCancelProduct(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmNewProduct.dispose();
            }
        });
        frmNewProduct.btnSaveProduct(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    provera();
                    save();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmNewProduct, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void save() throws Exception {
                product.setModel(String.valueOf(frmNewProduct.getTxtModel().getText().trim()));
                for (Product pro : Communication.getInstance().getAllProducts()) {
                    if (Objects.equals(pro.getModel(), product.getModel())) {
                        throw new Exception("Postoji proizvod sa tim imenom");
                    }
                }
                if (frmNewProduct.getLblPoster().getIcon() == null) {
                    throw new Exception("Morate da dodate sliku");
                }

                product.setPrice(new BigDecimal(String.valueOf(frmNewProduct.getTxtCena().getText().trim())));
                product.setManufacturer((Manufacturer) frmNewProduct.getCbProizvodjac().getModel().getSelectedItem());
                product.setType((ProductType) frmNewProduct.getCbVrsta().getModel().getSelectedItem());
                product.setSupplier((Supplier) frmNewProduct.getCbDobavljac().getModel().getSelectedItem());
                product.setAmount(Integer.parseInt(frmNewProduct.getTxtKolicina().getText().trim()));
                product.setOcena(0.0);
                if (formMode.equals(FormMode.FORM_ADD)) {
                    product.setProductID(0l);
                    Request request = new Request(Operation.ADD_PRODUCT, product);
                    Communication.getInstance().sendUserRequest(request);
                }
                if (formMode.equals(FormMode.FORM_DETAIL)) {
                    product.setProductID(Long.parseLong(frmNewProduct.getTxtProizvodId().getText().trim()));
                    Request request = new Request(Operation.UPDATE_PRODUXT, product);
                    Communication.getInstance().sendUserRequest(request);
                }
                JOptionPane.showMessageDialog(frmNewProduct, "Sistem је sacuvao proizvod", "Cuvanje proizvoda", JOptionPane.INFORMATION_MESSAGE);
                frmNewProduct.dispose();
            }

            private void provera() throws Exception {
                String cena = frmNewProduct.getTxtCena().getText().trim();
                String model = frmNewProduct.getTxtModel().getText().trim();
                String kolicina = frmNewProduct.getTxtKolicina().getText().trim();
                Character ch;

                if (cena.isEmpty()) {
                    throw new Exception("Polje cena ne sme biti prazno");
                }
                if (model.isEmpty()) {
                    throw new Exception("Polje model ne sme biti prazno");
                }
                if (kolicina.isEmpty()) {
                    throw new Exception("Polje kolicina ne sme biti prazno");
                }
                for (int j = 0; j < cena.length(); j++) {
                    ch = cena.charAt(j);
                    if (!Character.isDigit(ch)) {
                        throw new Exception("Polje cena ne sme imati slova");
                    }
                }
                for (int j = 0; j < kolicina.length(); j++) {
                    ch = kolicina.charAt(j);
                    if (!Character.isDigit(ch)) {
                        throw new Exception("Polje kolicina ne sme imati slova");
                    }
                }
            }
        }
        );
    }

    public void getReviewText(String reviews) {
        try {
            this.nalepi += reviews;
        } catch (Exception ex) {
            Logger.getLogger(ProductNewController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillProizvodi(List<Product> proi) {
        proizvodi = proi;
    }

    private void mode(FormMode formMode, Product p) throws Exception {

        switch (formMode) {

            case FORM_ADD:
                frmNewProduct.getBtnEdit().setEnabled(false);
                frmNewProduct.getBtnDelete().setEnabled(false);
                frmNewProduct.getTxtOcena().setEnabled(false);
                frmNewProduct.getTxtProizvodId().setText("Auto");
                break;

            case FORM_DETAIL:
                frmNewProduct.getCbProizvodjac().setEnabled(false);
                frmNewProduct.getCbDobavljac().setEnabled(false);
                frmNewProduct.getCbVrsta().setEnabled(false);
                frmNewProduct.getTxtCena().setEnabled(false);
                frmNewProduct.getTxtModel().setEnabled(false);
                frmNewProduct.getTxtProizvodjacId().setEnabled(false);
                frmNewProduct.getBtnEdit().setEnabled(true);
                frmNewProduct.getBtnDelete().setEnabled(false);
                frmNewProduct.getBtnSave().setEnabled(false);
                frmNewProduct.getTxtKolicina().setEnabled(false);
                frmNewProduct.getBtnUploadPoster().setEnabled(false);
                frmNewProduct.getTxtOcena().setEnabled(false);

                frmNewProduct.getTxtListaKomentara().setText(nalepi);
                frmNewProduct.getTxtListaKomentara().setEnabled(false);
                frmNewProduct.getTxtCena().setText(String.valueOf(p.getPrice()));
                frmNewProduct.getTxtModel().setText(String.valueOf(p.getModel()));
                frmNewProduct.getTxtProizvodjacId().setText(String.valueOf(p.getProductID()));

                frmNewProduct.getCbVrsta().getModel().setSelectedItem(p.getType());
                frmNewProduct.getCbProizvodjac().getModel().setSelectedItem(p.getManufacturer());
                frmNewProduct.getCbDobavljac().getModel().setSelectedItem(p.getSupplier());

                frmNewProduct.getTxtKolicina().setText(String.valueOf(p.getAmount()));
                frmNewProduct.getTxtOcena().setText(p.getOcena() + "");
                frmNewProduct.getLblPoster().setIcon(new ImageIcon(
                        p.getPicture().getPosterImage().getScaledInstance(frmNewProduct.getLblPoster().getWidth(),
                                frmNewProduct.getLblPoster().getHeight(), Image.SCALE_SMOOTH)));

                break;
        }
    }

    public void fillCbDobavljac() throws Exception {
        frmNewProduct.getCbDobavljac().removeAllItems();

        for (Supplier dobavljac : Communication.getInstance().getSuppliers()) {
            frmNewProduct.getCbDobavljac().addItem(dobavljac);
        }
    }

    private void addWindowListener() {
        frmNewProduct.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                try {
                    Request request = new Request(Operation.GET_ALL_SUPPLIERS, null);
                    Communication.getInstance().sendUserRequest(request);
                    Request request1 = new Request(Operation.GET_ALL_MANUFACTURERS, null);
                    Communication.getInstance().sendUserRequest(request1);
                    Request request2 = new Request(Operation.GET_ALL_TYPE, null);
                    Communication.getInstance().sendUserRequest(request2);
                    Request request4 = new Request(Operation.GET_ALL_PRODUCTS, null);
                    Communication.getInstance().sendUserRequest(request4);

                } catch (Exception ex) {
                    Logger.getLogger(ProductNewController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    public void fillcbProizvodjac(List<Manufacturer> manufacturers) {
        frmNewProduct.getCbProizvodjac().removeAllItems();

        for (Manufacturer manu : manufacturers) {
            frmNewProduct.getCbProizvodjac().addItem(manu);
        }
    }

    public void fillCbDobavljac(List<Supplier> dobavljaci) {
        frmNewProduct.getCbDobavljac().removeAllItems();

        for (Supplier sup : dobavljaci) {
            frmNewProduct.getCbDobavljac().addItem(sup);
        }
    }

    public void fillCbVrsta(List<ProductType> productTypes) {
        frmNewProduct.getCbVrsta().removeAllItems();

        for (ProductType vrsta : productTypes) {
            frmNewProduct.getCbVrsta().addItem(vrsta);
        }
    }

}

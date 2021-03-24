/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.admin;

import communication.Communication;
import communication.Operation;
import communication.Request;
import domain.Product;
import domain.Supplier;
import form.admin.FrmNewSupplier;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;

import util.FormMode;

/**
 *
 * @author SystemX
 */
public class NewSupplierController {

    FrmNewSupplier frmNewSupplier;
    FormMode formMode;

    public NewSupplierController(FrmNewSupplier frmNewSupplier, FormMode formMode, Supplier d) throws Exception {
        this.frmNewSupplier = frmNewSupplier;
        addActionListener();
        this.formMode = formMode;
        mode(formMode, d);
    }

    public void openForm() {
        frmNewSupplier.setTitle("Dobavljac");
        frmNewSupplier.setLocationRelativeTo(null);
        frmNewSupplier.setVisible(true);
    }

    private void addActionListener() {
        frmNewSupplier.addBtnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmNewSupplier.dispose();
            }

        });
        frmNewSupplier.addBtnEditActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmNewSupplier.getTxtDobavljacId().setEnabled(false);
                frmNewSupplier.getTxtNaziv().setEnabled(true);
                frmNewSupplier.getBtnSave().setEnabled(true);
                frmNewSupplier.getBtnObrisi().setEnabled(true);

            }

        });
        frmNewSupplier.addBtnDeleteActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Supplier s = new Supplier();
                    s.setSupplierID(Long.parseLong(frmNewSupplier.getTxtDobavljacId().getText().trim()));
                    s.setName(frmNewSupplier.getTxtNaziv().getText().trim());
                    proveraBrisanje(s);
                    cuvanje(s);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmNewSupplier, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void proveraBrisanje(Supplier s) throws Exception {
                List<Product> products = Communication.getInstance().getAllProducts();
                for (Product product : products) {
                    if (Objects.equals(product.getSupplier().getName(), s.getName())) {
                        throw new Exception("Ne mozete da obrisete dobavljaca," + "\n" + "postoji proizvod tog dobavljaca");
                    }
                }
            }

            private void cuvanje(Supplier s) throws Exception {
                int k = JOptionPane.showConfirmDialog(frmNewSupplier, "Da li ste sigurni?", "brisanje", JOptionPane.YES_NO_OPTION);
                if (k == JOptionPane.YES_OPTION) {
                    Request request = new Request(Operation.DELETE_SUPPLIER, s);
                    Communication.getInstance().sendUserRequest(request);
                    JOptionPane.showMessageDialog(frmNewSupplier, "Uspesno ste obrisali dobavljaca");
                    frmNewSupplier.dispose();
                }
            }
        });

        frmNewSupplier.addBtnSaveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    provera();
                    save();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmNewSupplier, ex.getMessage(), "Greska pri unosu", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void save() throws Exception {
                try {

                    Supplier supplier = new Supplier();
                    supplier.setName(String.valueOf(frmNewSupplier.getTxtNaziv().getText().trim()));

                    int k = JOptionPane.showConfirmDialog(frmNewSupplier, "Da li ste sigurni?", "cuvanje", JOptionPane.YES_NO_OPTION);
                    if (k == JOptionPane.YES_OPTION) {
                        if (formMode.equals(FormMode.FORM_ADD)) {
                            supplier.setSupplierID(0l);
                            Request request = new Request(Operation.ADD_SUPPLIER, supplier);
                            Communication.getInstance().sendUserRequest(request);
                            frmNewSupplier.dispose();
                        }
                        if (formMode.equals(FormMode.FORM_DETAIL)) {
                            supplier.setSupplierID(Long.parseLong(frmNewSupplier.getTxtDobavljacId().getText().trim()));
                            Request request = new Request(Operation.UPDATE_SUPPLIER, supplier);
                            Communication.getInstance().sendUserRequest(request);
                            frmNewSupplier.dispose();
                        }
                    }

                    JOptionPane.showMessageDialog(frmNewSupplier, "Uspesno ste sacuvali dobavljaca", "Dodavanje dobavljaca", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    ex.printStackTrace();

                }

            }

            private void provera() throws Exception {
                List<Supplier> suppliers = Communication.getInstance().getSuppliers();
                String naziv = frmNewSupplier.getTxtNaziv().getText().trim();

                Supplier supplier = new Supplier();
                supplier.setName(String.valueOf(frmNewSupplier.getTxtNaziv().getText().trim()));
                for (Supplier supplier1 : suppliers) {
                    if (Objects.equals(supplier1.getName().toUpperCase(), supplier.getName().toUpperCase())) {
                        throw new Exception("Postoji dobavljac sa tim imenom");
                    }
                }
                if (naziv.isEmpty()) {
                    throw new Exception("Polje naziv ne sme biti prazno");
                }
            }
        });
    }

    private void mode(FormMode formMode, Supplier d) throws Exception {

        switch (formMode) {

            case FORM_ADD:
                frmNewSupplier.getBtnEdit().setEnabled(false);
                frmNewSupplier.getBtnObrisi().setEnabled(false);
                frmNewSupplier.getTxtDobavljacId().setText("Auto");
                frmNewSupplier.getTxtDobavljacId().setEnabled(false);

                break;

            case FORM_DETAIL:
                frmNewSupplier.getTxtDobavljacId().setEnabled(false);
                frmNewSupplier.getTxtNaziv().setEnabled(false);
                frmNewSupplier.getBtnSave().setEnabled(false);
                frmNewSupplier.getBtnObrisi().setEnabled(false);

                frmNewSupplier.getTxtNaziv().setText(String.valueOf(d.getName()));
                frmNewSupplier.getTxtDobavljacId().setText(String.valueOf(d.getSupplierID()));

                break;

        }

    }
}

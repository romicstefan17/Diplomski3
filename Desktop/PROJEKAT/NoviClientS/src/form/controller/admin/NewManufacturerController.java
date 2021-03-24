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
import domain.Manufacturer;
import domain.Product;
import form.admin.FrmNewManufacturer;
import form.component.TableProductModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import util.FormMode;

/**
 *
 * @author SystemX
 */
public class NewManufacturerController {

    FrmNewManufacturer frmNewManufacturer;
    FormMode formMode;

    public NewManufacturerController(FrmNewManufacturer frmNewManufacturer, FormMode formMode, Manufacturer m) throws Exception {
        this.frmNewManufacturer = frmNewManufacturer;
        this.formMode = formMode;
        mode(formMode, m);
        addActionListener();
    }

    public void openForm() {
        frmNewManufacturer.setTitle("Proizvodjac");
        frmNewManufacturer.setLocationRelativeTo(null);
        frmNewManufacturer.setVisible(true);
    }

    private void addActionListener() {
        frmNewManufacturer.addBtnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmNewManufacturer.dispose();
            }

        });
        frmNewManufacturer.addBtnEditActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmNewManufacturer.getTxtProizvodjacId().setEnabled(false);
                frmNewManufacturer.getTxtNaziv().setEnabled(true);
                frmNewManufacturer.getBtnObrisi().setEnabled(true);
                frmNewManufacturer.getBtnSacuvaj().setEnabled(true);
            }

        });
        frmNewManufacturer.addBtnDeleteActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    proveraBrisanje();
                    cuvanje();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmNewManufacturer, ex.getMessage(), "greska prilikom brisanja", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void proveraBrisanje() throws Exception {
                Manufacturer m = new Manufacturer();
                m.setManufacturerID(Long.parseLong(frmNewManufacturer.getTxtProizvodjacId().getText().trim()));
                m.setName(frmNewManufacturer.getTxtNaziv().getText().trim());
                List<Product> products = Communication.getInstance().getAllProducts();
                for (Product product : products) {
                    if (Objects.equals(product.getManufacturer().getName(), m.getName())) {
                        throw new Exception("Ne mozete da obrisete proizvodjaca," + "\n" + "postoji proizvod tog proizvodjaca");
                    }
                }
            }

            private void cuvanje() throws Exception {
                Manufacturer m = new Manufacturer();
                m.setManufacturerID(Long.parseLong(frmNewManufacturer.getTxtProizvodjacId().getText().trim()));
                m.setName(frmNewManufacturer.getTxtNaziv().getText().trim());
                int k = JOptionPane.showConfirmDialog(frmNewManufacturer, "Da li ste sigurni?", "brisanje", JOptionPane.YES_NO_OPTION);
                if (k == JOptionPane.YES_OPTION) {
                    Request request = new Request(Operation.DELETE_MANUFACTURER, m);
                    Communication.getInstance().sendUserRequest(request);
                    JOptionPane.showMessageDialog(frmNewManufacturer, "Uspesno ste obrisali proizvodjaca");
                    frmNewManufacturer.dispose();
                }
            }
        });

        frmNewManufacturer.addActionListenerSacuvaj(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    provera();
                    save();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmNewManufacturer, ex.getMessage(), "Greska prilikom cuvanja proizvodjaca", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void save() throws Exception {
                try {
                    Manufacturer m = new Manufacturer();
                    m.setName(String.valueOf(frmNewManufacturer.getTxtNaziv().getText().trim()));

                    int k = JOptionPane.showConfirmDialog(frmNewManufacturer, "Da li ste sigurni?", "Cuvanje", JOptionPane.YES_NO_OPTION);
                    if (k == JOptionPane.YES_OPTION) {
                        if (formMode.equals(FormMode.FORM_DETAIL)) {
                            m.setManufacturerID(Long.parseLong(frmNewManufacturer.getTxtProizvodjacId().getText().trim()));
                            Request request = new Request(Operation.UPDATE_MANUFACTURER, m);
                            Communication.getInstance().sendUserRequest(request);
                            frmNewManufacturer.dispose();
                        }
                        if (formMode.equals(FormMode.FORM_ADD)) {
                            m.setManufacturerID(0l);
                            Request request = new Request(Operation.ADD_MANUFACTURER, m);
                            Communication.getInstance().sendUserRequest(request);
                            frmNewManufacturer.dispose();
                        }
                    }
                    JOptionPane.showMessageDialog(frmNewManufacturer, "Uspesno ste sacuvali proizvodjaca", "Dodavanje dobavljaca", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmNewManufacturer, "Greska pri unosu proizvodjaca", "Dodavanje dobavljaca", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void provera() throws Exception {

                String naziv = frmNewManufacturer.getTxtNaziv().getText().trim();
                Manufacturer m = new Manufacturer();
                m.setName(String.valueOf(frmNewManufacturer.getTxtNaziv().getText().trim()));
                List<Manufacturer> manufacturers = Communication.getInstance().getManufacturers();

                for (Manufacturer manuf : manufacturers) {
                    if (Objects.equals(manuf.getName().toUpperCase(), m.getName().toUpperCase())) {
                        throw new Exception("Postoji proizvodjac sa tim imenom");
                    }
                }
                if (naziv.isEmpty()) {
                    throw new Exception("Polje naziv ne sme biti prazno");
                }
            }
        });
    }

    private void mode(FormMode formMode, Manufacturer p) throws Exception {

        switch (formMode) {

            case FORM_ADD:
                frmNewManufacturer.getBtnEdit().setEnabled(false);
                frmNewManufacturer.getTxtProizvodjacId().setText("Auto");
                frmNewManufacturer.getTxtProizvodjacId().setEnabled(false);
                frmNewManufacturer.getBtnObrisi().setEnabled(false);
                break;

            case FORM_DETAIL:
                frmNewManufacturer.getTxtProizvodjacId().setEnabled(false);
                frmNewManufacturer.getTxtNaziv().setEnabled(false);
                frmNewManufacturer.getTxtNaziv().setText(String.valueOf(p.getName()));
                frmNewManufacturer.getTxtProizvodjacId().setText(String.valueOf(p.getManufacturerID()));
                frmNewManufacturer.getBtnObrisi().setEnabled(false);
                frmNewManufacturer.getBtnSacuvaj().setEnabled(false);
                break;
        }
    }
}

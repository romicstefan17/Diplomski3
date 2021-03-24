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
import form.admin.FrmListOfManufacturers;
import form.component.TableManufacturerModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author SystemX
 */
public class ListOfManufacturerController {

    FrmListOfManufacturers frmListOfManufacturers;

    public ListOfManufacturerController(FrmListOfManufacturers frmListOfManufacturers) {
        this.frmListOfManufacturers = frmListOfManufacturers;

        addActionListeners();
        addWindowListener();

    }

    public void openForm() {
        frmListOfManufacturers.setTitle("lista proizvodjaca");
        frmListOfManufacturers.setLocationRelativeTo(null);
        frmListOfManufacturers.setVisible(true);
    }

    private void addActionListeners() {
        frmListOfManufacturers.addBtnDetailsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = frmListOfManufacturers.getTblProizvodjac().getSelectedRow();
                    if (row >= 0) {
                        Manufacturer manufacturer = Communication.getInstance().getManufacturers().get(row);
                        Request request=new Request(Operation.GET_ALL_PRODUCTS,null);
                        Communication.getInstance().sendUserRequest(request);
                        MainCordinatorAdmin.getInstance().openFormProizvodjacDetail(manufacturer);
                    } else {
                        JOptionPane.showMessageDialog(frmListOfManufacturers, "Morate da oznacite proizvodjaca", "Paznja", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        });

        frmListOfManufacturers.getTxtSearch().addKeyListener(new KeyListener() {
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
                TableManufacturerModel model = (TableManufacturerModel) frmListOfManufacturers.getTblProizvodjac().getModel();
                String search = frmListOfManufacturers.getTxtSearch().getText().trim();
                TableRowSorter<TableManufacturerModel> tr = new TableRowSorter<TableManufacturerModel>(model);
                frmListOfManufacturers.getTblProizvodjac().setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter(search));
                if (frmListOfManufacturers.getTblProizvodjac().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(frmListOfManufacturers, "Nema proizvodjaca pod tim kriterijumom", "", JOptionPane.INFORMATION_MESSAGE);
                }

            }

        });

    }

    public void fillTblProizvodjac(List<Manufacturer> manufacturers) throws Exception {
        TableManufacturerModel model = new TableManufacturerModel(manufacturers);
        frmListOfManufacturers.getTblProizvodjac().setModel(model);

    }

    private void addWindowListener() {
        frmListOfManufacturers.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                try {
                    Request request = new Request(Operation.GET_ALL_MANUFACTURERS, null);
                    Communication.getInstance().sendUserRequest(request);
                } catch (Exception ex) {
                }
            }
        });
    }
}

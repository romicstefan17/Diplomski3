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
import domain.Product;
import domain.Supplier;
import form.admin.FrmListOfSuppliers;
import form.component.TableSupplierModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author SystemX
 */
public class ListOfSuppliersController {

    FrmListOfSuppliers frmListOfSuppliers;

    public ListOfSuppliersController(FrmListOfSuppliers frmListOfSuppliers) {
        this.frmListOfSuppliers = frmListOfSuppliers;
        addActionListeners();
        addWindowListener();
    }

    public void openForm() throws Exception {
        frmListOfSuppliers.setTitle("lista dobavljaca");
        frmListOfSuppliers.setLocationRelativeTo(null);
        frmListOfSuppliers.setVisible(true);
    }

    private void addActionListeners() {
        frmListOfSuppliers.addBtnDetailsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = frmListOfSuppliers.getTblDobavljac().getSelectedRow();
                    if (row >= 0) {
                        Supplier supplier = Communication.getInstance().getSuppliers().get(row);
                        Request request = new Request(Operation.GET_ALL_PRODUCTS, null);
                        Communication.getInstance().sendUserRequest(request);
                        MainCordinatorAdmin.getInstance().openFormDobavljacDetail(supplier);
                    } else {
                        JOptionPane.showMessageDialog(frmListOfSuppliers, "Morate da oznacite dobavljaca", "Paznja", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        });

        frmListOfSuppliers.getTxtSearch().addKeyListener(new KeyListener() {
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
                TableSupplierModel model = (TableSupplierModel) frmListOfSuppliers.getTblDobavljac().getModel();
                String search = frmListOfSuppliers.getTxtSearch().getText().trim();
                TableRowSorter<TableSupplierModel> tr = new TableRowSorter<TableSupplierModel>(model);
                frmListOfSuppliers.getTblDobavljac().setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter(search));
                if (frmListOfSuppliers.getTblDobavljac().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(frmListOfSuppliers, "Nema dobavljaca pod tim kriterijumom", "", JOptionPane.INFORMATION_MESSAGE);
                }

            }

        });

    }

    public void fillTblDobavljac(List<Supplier> suppliers) throws Exception {
        TableSupplierModel model = new TableSupplierModel(suppliers);
        frmListOfSuppliers.getTblDobavljac().setModel(model);

    }

    private void addWindowListener() {
        frmListOfSuppliers.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                try {
                    Request request = new Request(Operation.GET_ALL_SUPPLIERS, null);
                    Communication.getInstance().sendUserRequest(request);
                } catch (Exception ex) {
                }
            }

        });
    }
}

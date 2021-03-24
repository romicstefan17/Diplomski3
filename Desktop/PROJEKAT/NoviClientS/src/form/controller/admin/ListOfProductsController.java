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
import form.admin.FrmListOfProducts;
import form.component.TableProductModel;
import javax.swing.RowFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author SystemX
 */
public class ListOfProductsController {

    FrmListOfProducts frmListOfProducts;
    boolean provera = false;

    public ListOfProductsController(FrmListOfProducts frmListOfProducts) {
        this.frmListOfProducts = frmListOfProducts;
        addActionListeners();
        addWindowListener();

    }

    public void openForm() throws Exception {
        frmListOfProducts.setTitle("Lista proizvoda");
        frmListOfProducts.setLocationRelativeTo(null);
        frmListOfProducts.setVisible(true);
    }

    private void addActionListeners() {
        frmListOfProducts.btnDetailProducts(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = frmListOfProducts.getTblProducts().getSelectedRow();
                    if (row >= 0) {
                        Product proizvod = Communication.getInstance().getAllProducts().get(row);
                        Request request1 = new Request();
                        request1.setOperation(Operation.GET_ALL_REVIEWS_ADMIN);
                        request1.setArgument(proizvod);
                        Communication.getInstance().sendUserRequest(request1);
                        MainCordinatorAdmin.getInstance().openFormProductDetail(proizvod);

                    } else {
                        JOptionPane.showMessageDialog(frmListOfProducts, "Morate da oznacite prozivod", "Paznja", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        });

        frmListOfProducts.getTxtSearch().addKeyListener(new KeyListener() {
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
                TableProductModel model = (TableProductModel) frmListOfProducts.getTblProducts().getModel();
                String search = frmListOfProducts.getTxtSearch().getText().trim();
                TableRowSorter<TableProductModel> tr = new TableRowSorter<TableProductModel>(model);
                frmListOfProducts.getTblProducts().setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter(search));
                if (frmListOfProducts.getTblProducts().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(frmListOfProducts, "Sistem ne moze da nadje proizvode po zadatoj vrednosti", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        });

    }

    public void fillTblProducts(List<Product> products) throws Exception {
        TableProductModel model = new TableProductModel(products);
        frmListOfProducts.getTblProducts().setModel(model);
        setComboBox();
    }

    private void setComboBox() throws Exception {

        TableColumnModel tcm = frmListOfProducts.getTblProducts().getColumnModel();
        TableColumn tc1 = tcm.getColumn(3);
        TableColumn tc2 = tcm.getColumn(4);
        TableColumn tc3 = tcm.getColumn(5);

        JComboBox cb1 = new JComboBox(Communication.getInstance().getManufacturers().toArray());
        JComboBox cb2 = new JComboBox(Communication.getInstance().getType().toArray());
        JComboBox cb3 = new JComboBox(Communication.getInstance().getSuppliers().toArray());

        tc1.setCellEditor(new DefaultCellEditor(cb1));
        tc2.setCellEditor(new DefaultCellEditor(cb2));
        tc3.setCellEditor(new DefaultCellEditor(cb3));
    }

    private void addWindowListener() {
        frmListOfProducts.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                try {
                    Request request = new Request(Operation.GET_ALL_PRODUCTS, null);
                    Communication.getInstance().sendUserRequest(request);

                } catch (Exception ex) {
                }
            }
        });
    }

    public FrmListOfProducts getFrmListOfProducts() {
        return frmListOfProducts;
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.component;

import domain.Manufacturer;
import domain.Supplier;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author SystemX
 */
public class TableSupplierModel extends AbstractTableModel {

    List<Supplier> suppliers;

    String[] columnNames = new String[]{"ID", "Naziv"};
    Object[] columnClasses = new Object[]{long.class, String.class};

    public TableSupplierModel(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public int getRowCount() {
        return suppliers.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Supplier s = suppliers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return s.getSupplierID();
            case 1:
                return s.getName();

            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return (Class<?>) columnClasses[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        try {
            switch (columnIndex) {
                case 0:
                    suppliers.get(rowIndex).setSupplierID((Long) value);
                    break;
                case 1:
                    suppliers.get(rowIndex).setName(String.valueOf(value));
                    break;

            }

          //  Controller.getInstance().updateSupplier(suppliers.get(rowIndex));

        } catch (Exception ex) {
            Logger.getLogger(TableProductModel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public void save(Manufacturer m) throws Exception {

     //   Controller.getInstance().addManufacturer(m);

        fireTableRowsInserted(suppliers.size() - 1, suppliers.size() - 1);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.component;


import domain.Manufacturer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author SystemX
 */
public class TableManufacturerModel extends AbstractTableModel {

    List<Manufacturer> manufacturers;

    String[] columnNames = new String[]{"ID", "Naziv"};
    Object[] columnClasses = new Object[]{long.class, String.class};

    public TableManufacturerModel(List<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    @Override
    public int getRowCount() {
        return manufacturers.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Manufacturer m = manufacturers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return m.getManufacturerID();
            case 1:
                return m.getName();

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
                    manufacturers.get(rowIndex).setManufacturerID((Long) value);
                    break;
                case 1:
                    manufacturers.get(rowIndex).setName(String.valueOf(value));
                    break;
            }
        //    Controller.getInstance().updateManufacturer(manufacturers.get(rowIndex));

        } catch (Exception ex) {
            Logger.getLogger(TableProductModel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public void save(Manufacturer p) throws Exception {

       // Controller.getInstance().addManufacturer(p);

        fireTableRowsInserted(manufacturers.size() - 1, manufacturers.size() - 1);
    }

}

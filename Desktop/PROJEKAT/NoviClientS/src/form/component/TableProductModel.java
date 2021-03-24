/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.component;

import domain.Manufacturer;
import domain.Product;
import domain.ProductType;
import domain.Supplier;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author SystemX
 */
public class TableProductModel extends AbstractTableModel {

    List<Product> proizvodi;

    String[] columnNames = new String[]{"ID", "Model", "Cena", "Proizvodjac", "Vrsta", "Dobavljac", "Kolicina", "Prosecna ocena"};
    Object[] columnClasses = new Object[]{long.class, String.class, BigDecimal.class, Manufacturer.class, ProductType.class, Supplier.class, Integer.class, Double.class};

    public TableProductModel(List<Product> proizvodi) throws Exception {
        this.proizvodi = proizvodi;
    }

    public TableProductModel() {
    }
    

    @Override
    public int getRowCount() {
        return proizvodi.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product proizvod = proizvodi.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return proizvod.getProductID();
            case 1:
                return proizvod.getModel();
            case 2:
                return proizvod.getPrice();
            case 3:
                return proizvod.getManufacturer();
            case 4:
                return proizvod.getType();
            case 5:
                return proizvod.getSupplier();
            case 6:
                return proizvod.getAmount();
            case 7:
                return proizvod.getOcena();

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
        return false;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                proizvodi.get(rowIndex).setProductID((Long) value);
                break;
            case 1:
                proizvodi.get(rowIndex).setModel(String.valueOf(value));
                break;
            case 2:
                proizvodi.get(rowIndex).setPrice(new BigDecimal(String.valueOf(value)));
                break;
            case 3:
                proizvodi.get(rowIndex).setManufacturer((Manufacturer) value);
                break;
            case 4:
                proizvodi.get(rowIndex).setType((ProductType) value);
                break;
            case 5:
                proizvodi.get(rowIndex).setSupplier((Supplier) value);
                break;
            case 6:
                proizvodi.get(rowIndex).setAmount((Integer) value);
                break;
            case 7:
                proizvodi.get(rowIndex).setOcena((Double) value);
                break;
        }

    }

    public Product getProizvod(int row) {
        return proizvodi.get(row);
    }

    public List<Product> getProizvodi() {
        return proizvodi;
    }
    
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.component;

import domain.InvoiceItem;
import domain.Supplier;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author SystemX
 */
public class TableInvoiceDetailsModel extends AbstractTableModel {

    List<InvoiceItem> stavkaNarudzbes;

    public TableInvoiceDetailsModel(List<InvoiceItem> sn) throws Exception {
        stavkaNarudzbes = sn;
    }

    String columnNames[] = new String[]{"ID", "RedniBroj", "Naziv", "Cena", "Kolicina", "Total", "Dobavljac"};
    Class[] columnClasses = new Class[]{Long.class, Integer.class, String.class, BigDecimal.class, Integer.class, BigDecimal.class, Supplier.class};

    @Override
    public int getRowCount() {
        return stavkaNarudzbes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceItem sn = stavkaNarudzbes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return sn.getId();
            case 1:
                return sn.getOrderNumber();
            case 2:
                return sn.getProduct().getModel();
            case 3:
                return sn.getProduct().getPrice();
            case 4:
                return sn.getAmount();
            case 5:
                return sn.getPrice();
            case 6:
               return sn.getProduct().getSupplier().getName();

            default:
                return "N/A";

        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        InvoiceItem sn = stavkaNarudzbes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                sn.setId(Long.parseLong(value.toString()));
                break;
            case 1:
                sn.setOrderNumber((int) Long.parseLong(value.toString()));
                break;
            case 2:
                sn.getProduct().setModel(String.valueOf(value));
                break;
            case 3:
                sn.getProduct().setPrice(new BigDecimal(String.valueOf(value)));
                break;
            case 4:
                sn.setAmount(Integer.parseInt(value.toString()));
                break;
            case 5:
                sn.setPrice(sn.getProduct().getPrice().multiply(new BigDecimal(sn.getAmount())));
                break;

            case 6:
                sn.getProduct().getSupplier().setName(String.valueOf(value));
                break;

        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

   
    
    

}

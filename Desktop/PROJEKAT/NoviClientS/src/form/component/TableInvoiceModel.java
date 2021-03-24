/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.component;

import domain.Invoice;
import domain.InvoiceItem;
import domain.Supplier;
import java.math.BigDecimal;
import java.util.Objects;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author SystemX
 */
public class TableInvoiceModel extends AbstractTableModel {

    private final Invoice narudzba;

    public TableInvoiceModel(Invoice n) {
        this.narudzba = n;

    }

    String columnNames[] = new String[]{"ID", "RedniBroj", "Naziv", "Cena", "Kolicina", "Total", "Dobavljac"};
    Class[] columnClasses = new Class[]{Long.class, Integer.class, String.class, BigDecimal.class, Integer.class, BigDecimal.class, Supplier.class};

    @Override
    public int getRowCount() {
        return narudzba.getListOfInvoice().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceItem sn = narudzba.getListOfInvoice().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return "Auto";
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
        return columnIndex == 4;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        InvoiceItem sn = narudzba.getListOfInvoice().get(rowIndex);
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

    public void DodajStavku(InvoiceItem sn) throws Exception {

        narudzba.getListOfInvoice().add(sn);
        narudzba.setPrice(narudzba.getPrice().add(sn.getProduct().getPrice().multiply(new BigDecimal(sn.getAmount()))));
        setRedniBroj();
        fireTableRowsInserted(narudzba.getListOfInvoice().size() - 1, narudzba.getListOfInvoice().size() - 1);

    }

    public Invoice getNarudzba() {
        return narudzba;
    }

    public void obrisi(int rowIndex) {
        InvoiceItem st = narudzba.getListOfInvoice().get(rowIndex);
        narudzba.getListOfInvoice().remove(rowIndex);
        narudzba.setPrice(narudzba.getPrice().subtract(st.getProduct().getPrice().multiply(new BigDecimal(st.getAmount()))));
        setRedniBroj();
        fireTableRowsDeleted(narudzba.getListOfInvoice().size() - 1, narudzba.getListOfInvoice().size() - 1);
    }

    private void setRedniBroj() {
        int orderNo = 0;
        for (InvoiceItem st : narudzba.getListOfInvoice()) {
            st.setOrderNumber(++orderNo);
        }
    }

    public void update(InvoiceItem item) {
        for (InvoiceItem pom : narudzba.getListOfInvoice()) {
            if (Objects.equals(pom.getProduct().getModel(), item.getProduct().getModel())) {
                pom.setAmount(pom.getAmount() + item.getAmount());
                pom.setPrice(pom.getPrice().add(item.getPrice().multiply(new BigDecimal(item.getAmount()))));
                narudzba.setPrice(pom.getPrice());
            }
             fireTableDataChanged();
        }
    }

    public void deleteStavka(InvoiceItem item) {
         
        narudzba.getListOfInvoice().remove(item);
        narudzba.setPrice(narudzba.getPrice().subtract(item.getProduct().getPrice().multiply(new BigDecimal(item.getAmount()))));
        setRedniBroj();
        fireTableRowsDeleted(narudzba.getListOfInvoice().size() - 1, narudzba.getListOfInvoice().size() - 1);
    }

    public boolean sadrzi(InvoiceItem item){
        for (InvoiceItem ii : narudzba.getListOfInvoice()) {
             if (Objects.equals(ii.getProduct().getModel(), item.getProduct().getModel())) {
                      return true;
            }
        }
        return false;
    }
        

}

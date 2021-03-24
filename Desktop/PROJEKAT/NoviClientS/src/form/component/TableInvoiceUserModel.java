/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.component;

import communication.Communication;
import communication.Operation;
import communication.Request;
import domain.InvoiceUser;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author SystemX
 */
public class TableInvoiceUserModel extends AbstractTableModel {

    private List<InvoiceUser> asocijacije;

    public TableInvoiceUserModel(List<InvoiceUser> asocijacije) throws Exception {

        this.asocijacije = asocijacije;
    }

    String columnNames[] = new String[]{"IDNarudzba", "Ime", "Prezime"};
    Class[] columnClasses = new Class[]{Long.class, String.class, String.class};

    @Override
    public int getRowCount() {
        return asocijacije.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceUser a = asocijacije.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return a.getInvoice().getInvoiceID();
            case 1:
                return a.getUser().getFirstName();
            case 2:
                return a.getUser().getLastName();

            default:
                return "N/A";

        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        InvoiceUser a = asocijacije.get(rowIndex);
        switch (columnIndex) {
            case 0:
                a.getInvoice().setInvoiceID((Long) value);
                break;
            case 1:
                a.getUser().setFirstName((String) value);
                break;
            case 2:
                a.getUser().setLastName((String) value);
                break;

        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    public void remove(int row) throws Exception {

        Request request = new Request(Operation.DELETE_INVOICEUSER, asocijacije.get(row));
        fireTableDataChanged();
        Communication.getInstance().sendUserRequest(request);

    }

    public InvoiceUser getAsocijacija(int row) {
        return asocijacije.get(row);
    }
}

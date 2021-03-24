/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.component;

import controller.Controller;
import domain.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author SystemX
 */
public class TableClientStatusiModel extends AbstractTableModel {

    List<User> users;

    String[] columnNames = new String[]{"ID", "Ime", "Prezime", "Uloga", "Status"};

    public TableClientStatusiModel(List<User> users) throws Exception {

        this.users = users;

    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User u = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return u.getUserID();
            case 1:
                return u.getFirstName();
            case 2:
                return u.getLastName();
            case 3:
                return u.getStatus();
            case 4:
                return u.getUloga();

            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
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
                    users.get(rowIndex).setUserID((Long) value);
                    break;
                case 1:
                    users.get(rowIndex).setFirstName(String.valueOf(value));
                    break;
                case 2:
                    users.get(rowIndex).setLastName(String.valueOf(value));
                    break;
                case 3:
                    users.get(rowIndex).setStatus(String.valueOf(value));
                    break;
                case 4:
                    users.get(rowIndex).setUloga(String.valueOf(value));
                    break;

            }

            Controller.getInstance().updateUser(users.get(rowIndex));

        } catch (Exception ex) {
            Logger.getLogger(TableClientStatusiModel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}

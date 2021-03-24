/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.component;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class TableUserCellRenderer extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (table.getValueAt(row, column).equals("ulogovan")) {
                setText("ULOGOVAN");
                setForeground(Color.green.darker());
            } else if (table.getValueAt(row, column).equals("izlogovan")){
                setText("IZLOGOVAN");
                setForeground(Color.red);
            }
            return this;
    }
    
}

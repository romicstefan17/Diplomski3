/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.client;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author SystemX
 */
public class FrmMainClient extends javax.swing.JFrame {

    /**
     * Creates new form FrmMainN
     */
    public FrmMainClient() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLogout = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProizvodi = new javax.swing.JTable();
        btnDetails = new javax.swing.JButton();
        btnReview = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        miNarudzba = new javax.swing.JMenuItem();
        miListaN = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnLogout.setText("Logout");

        tblProizvodi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblProizvodi);

        btnDetails.setText("Details");

        btnReview.setText("Oceni proizvod");

        jMenu1.setText("Narudzbe");

        miNarudzba.setText("Nova narudzba");
        jMenu1.add(miNarudzba);

        miListaN.setText("Lista narudzbi");
        miListaN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miListaNActionPerformed(evt);
            }
        });
        jMenu1.add(miListaN);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDetails)
                        .addGap(46, 46, 46)
                        .addComponent(btnReview)
                        .addGap(40, 40, 40)
                        .addComponent(btnLogout))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDetails)
                    .addComponent(btnLogout)
                    .addComponent(btnReview))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void miListaNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miListaNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_miListaNActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetails;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnReview;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem miListaN;
    private javax.swing.JMenuItem miNarudzba;
    private javax.swing.JTable tblProizvodi;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

    public void btnOtvoriNarudzbeActionListener(ActionListener actionListener) {
        miNarudzba.addActionListener(actionListener);
    }
    
    public void btnListaNActionListener(ActionListener actionListener) {
        miListaN.addActionListener(actionListener);
    }
    
    public javax.swing.JButton getBtnLogout() {
        return btnLogout;
    }
    
    public JTable getTblProizvodi() {
        return tblProizvodi;
    }
    
    public void addMainWindowListeenr(WindowListener windowListener) {
        this.addWindowListener(windowListener);
    }
    
    public void addBtnDetailActionListener(ActionListener actionListener) {
        btnDetails.addActionListener(actionListener);
    }

    public void addBtnReviewActionListener(ActionListener actionListener) {
        btnReview.addActionListener(actionListener);
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }
    
}
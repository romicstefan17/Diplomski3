/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.client;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

/**
 *
 * @author SystemX
 */
public class FrmListOfInvoices extends javax.swing.JDialog {

    /**
     * Creates new form FrmListOfInvoices
     */
    public FrmListOfInvoices(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblListaNarudzbi = new javax.swing.JTable();
        btnNovaNarudzba = new javax.swing.JButton();
        btnObrisiNarudzbu = new javax.swing.JButton();
        btnDetaljanPrikaz = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblListaNarudzbi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblListaNarudzbi);

        btnNovaNarudzba.setText("Nova narudzva");

        btnObrisiNarudzbu.setText("Obrisi narudzbu");

        btnDetaljanPrikaz.setText("Detalji narudzbe");

        btnClose.setText("Zatvori");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNovaNarudzba)
                        .addGap(18, 18, 18)
                        .addComponent(btnObrisiNarudzbu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDetaljanPrikaz)
                        .addGap(18, 18, 18)
                        .addComponent(btnClose)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovaNarudzba)
                    .addComponent(btnObrisiNarudzbu)
                    .addComponent(btnDetaljanPrikaz)
                    .addComponent(btnClose))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDetaljanPrikaz;
    private javax.swing.JButton btnNovaNarudzba;
    private javax.swing.JButton btnObrisiNarudzbu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblListaNarudzbi;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JButton getBtnClose() {
        return btnClose;
    }

    public void setBtnClose(javax.swing.JButton btnClose) {
        this.btnClose = btnClose;
    }

    public javax.swing.JButton getBtnDetaljanPrikaz() {
        return btnDetaljanPrikaz;
    }

    public void setBtnDetaljanPrikaz(javax.swing.JButton btnDetaljanPrikaz) {
        this.btnDetaljanPrikaz = btnDetaljanPrikaz;
    }

    public javax.swing.JButton getBtnNovaNarudzba() {
        return btnNovaNarudzba;
    }

    public void setBtnNovaNarudzba(javax.swing.JButton btnNovaNarudzba) {
        this.btnNovaNarudzba = btnNovaNarudzba;
    }

    public javax.swing.JButton getBtnObrisiNarudzbu() {
        return btnObrisiNarudzbu;
    }

    public void setBtnObrisiNarudzbu(javax.swing.JButton btnObrisiNarudzbu) {
        this.btnObrisiNarudzbu = btnObrisiNarudzbu;
    }

    public javax.swing.JTable getTblListaNarudzbi() {
        return tblListaNarudzbi;
    }

    public void setTblListaNarudzbi(javax.swing.JTable tblListaNarudzbi) {
        this.tblListaNarudzbi = tblListaNarudzbi;
    }

    public void btnNovaNarudzbaa(ActionListener actionListener) {
        btnNovaNarudzba.addActionListener(actionListener);
    }

    public void btnObrisiNarudzbu(ActionListener actionListener) {
        btnObrisiNarudzbu.addActionListener(actionListener);
    }

    public void btnCancel(ActionListener actionListener) {
        btnClose.addActionListener(actionListener);
    }

    public void btnDetalji(ActionListener actionListener) {
        btnDetaljanPrikaz.addActionListener(actionListener);
    }

    public void frmListaNarudzbiWindowListener(WindowListener windowListener) {
        this.addWindowListener(windowListener);
    }

}

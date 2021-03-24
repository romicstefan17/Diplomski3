/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.PreparedStatement;

/**
 *
 * @author SystemX
 */
public class InvoiceUser implements GenericEntity {

    private Invoice invoice;
    private User user;

    public InvoiceUser() {
    }

    public InvoiceUser(Invoice invoice, User user) {
        this.invoice = invoice;
        this.user = user;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getTableName() {
        return "narudzbauser";
    }
    

    @Override
    public String getColumnNamesForInsert() {
        return "narudzbaid,zaposleniid";
    }

    @Override
    public void getInsertValues(PreparedStatement statement) throws Exception {
        statement.setLong(1, invoice.getInvoiceID());
        statement.setLong(2, user.getUserID());

    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public void setId(Long id) {
        
    }

    @Override
    public String getConditionForDelete() {
        return "narudzbaid=" + invoice.getInvoiceID();
    }

    @Override
    public String getColumnNamesForSelect() {
        return "nu.narudzbaid AS nun,nu.zaposleniid AS nuz"
                + ",n.datum,n.sifra,n.iznos"
                + ",z.ime,z.prezime,z.username,z.password,z.adresa";
    }

    @Override
    public String getTableForSelect() {
        return "narudzbauser nu JOIN narudzba n ON(nu.narudzbaid=n.narudzbaid)"
                + " JOIN korisnici z ON(nu.zaposleniid=z.zaposleniid) ";
    }

    @Override
    public String getConditionForSelect() {
        return "";
    }

    @Override
    public String getConditionForSelectSpecific() {
        return "z.zaposleniid="+user.getUserID();
    }

    @Override
    public String getColumnNamesForUpdate() {
        return "";
    }

    @Override
    public String getConditionForUpdate() {
        return "";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.Objects;

/**
 *
 * @author SystemX
 */
public class InvoiceItem implements GenericEntity {

    private Invoice invoice;
    private Long id;
    private int orderNumber;
    private Product product;
    private int amount=0;
    private BigDecimal price;

    public InvoiceItem() {
        this.product = new Product();
    }

    public InvoiceItem(Invoice invoice, Long id, int orderNumber, Product product, int amount, BigDecimal price) {
        this.invoice = invoice;
        this.id = id;
        this.orderNumber = orderNumber;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InvoiceItem other = (InvoiceItem) obj;
        if (!Objects.equals(this.invoice, other.invoice)) {
            return false;
        }
        return true;
    }

   
    

    @Override
    public String toString() {
        return "InvoiceItem{" + "invoice=" + invoice + ", id=" + id + ", orderNumber=" + orderNumber + ", product=" + product + ", amount=" + amount + ", price=" + price + '}';
    }

    @Override
    public String getTableName() {
        return "stavkanarudzbe";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "stavkaid, rednibroj, cena, kolicina, iznos, proizvodid";
    }

    @Override
    public void getInsertValues(PreparedStatement statement) throws Exception {
        statement.setLong(1, invoice.getInvoiceID());
        statement.setInt(2, orderNumber);
        statement.setBigDecimal(3, product.getPrice());
        statement.setInt(4, amount);
        statement.setBigDecimal(5, price);
        statement.setLong(6, product.getProductID());
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnNamesForUpdate() {
        return "";
    }

    @Override
    public String getConditionForUpdate() {
        return "";
    }

    @Override
    public String getConditionForDelete() {
        return "";
    }

    @Override
    public String getColumnNamesForSelect() {
        return "s.stavkaid, s.iznos, s.kolicina as sk, s.rednibroj, "
                + "p.proizvodid, p.model, p.cena, p.kolicina as pk, p.slikaid,p.dobavljacid as pd, jpg.id,jpg.poster, "
                + "d.dobavljacid as dd, d.naziv";
    }

    @Override
    public String getTableForSelect() {
        return "stavkanarudzbe s JOIN proizvod p ON (s.proizvodid=p.proizvodid) "
                + "JOIN dobavljac d ON (p.dobavljacid=d.dobavljacid) JOIN slika jpg ON(p.slikaid=jpg.id) order by p.model";
    }

    @Override
    public String getConditionForSelect() {
        return "";
    }

    @Override
    public String getConditionForSelectSpecific() {
        return "";
    }

}

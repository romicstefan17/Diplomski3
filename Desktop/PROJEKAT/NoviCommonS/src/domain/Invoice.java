/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author SystemX
 */
public class Invoice implements GenericEntity{

    private Long invoiceID;
    private String code;
    private Date date;
    private BigDecimal price = BigDecimal.ZERO;
    private List<InvoiceItem> listOfInvoice;

    public Invoice() {
        listOfInvoice = new ArrayList<>();
    }

    public Invoice(Long invoiceID, String code, Date date, BigDecimal price, List<InvoiceItem> listOfInvoice) {
        this.invoiceID = invoiceID;
        this.code = code;
        this.date = date;
        this.price = price;
        this.listOfInvoice = new ArrayList<>();
    }

    public Long getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(Long invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<InvoiceItem> getListOfInvoice() {
        return listOfInvoice;
    }

    public void setListOfInvoice(List<InvoiceItem> listOfInvoice) {
        this.listOfInvoice = listOfInvoice;
    }

    @Override
    public String toString() {
        return code;
    }

     @Override
    public String getTableName() {
        return "narudzba";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "datum, sifra, iznos";
    }

    @Override
    public void getInsertValues(PreparedStatement statement) throws Exception {
        statement.setDate(1, new java.sql.Date(date.getTime()));
        statement.setString(2, code);
        statement.setBigDecimal(3, price);
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public void setId(Long id) {
        this.invoiceID = id;
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
        return "narudzbaid, datum, sifra, iznos";
    }

    @Override
    public String getTableForSelect() {
        return "narudzba ";
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

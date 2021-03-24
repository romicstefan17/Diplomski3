/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.invoice;

import domain.Invoice;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author SystemX
 */
public class GetAllInvoice extends AbstractGenericOperation{
     private final List<Invoice> invoices = new ArrayList<>();
    
    @Override
    protected void preconditions(Object param) throws Exception {

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.getAll((Invoice)param);
        
         while (rs.next()) {
            Invoice n = new Invoice();
            n.setInvoiceID(rs.getLong("narudzbaid"));
            n.setDate(rs.getDate("datum"));
            n.setCode(rs.getString("sifra"));
            n.setPrice(rs.getBigDecimal("iznos"));

            invoices.add(n);
        }
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    
}

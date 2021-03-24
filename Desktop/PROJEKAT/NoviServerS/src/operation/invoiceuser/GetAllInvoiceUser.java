/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.invoiceuser;

import domain.Invoice;
import domain.InvoiceUser;
import domain.User;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

public class GetAllInvoiceUser extends AbstractGenericOperation {

    private final List<InvoiceUser> invoiceUsers = new ArrayList<>();

    @Override
    protected void preconditions(Object param) throws Exception {

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.getAll((InvoiceUser) param);

        while (rs.next()) {
            InvoiceUser a = new InvoiceUser();

            User z = new User();
            z.setUserID(rs.getLong("nuz"));
            z.setFirstName(rs.getString("ime"));
            z.setLastName(rs.getString("prezime"));
            z.setPassword(rs.getString("password"));
            z.setUserName(rs.getString("username"));
            z.setAdress(rs.getString("adresa"));

            a.setUser(z);

            Invoice n = new Invoice();
            n.setInvoiceID(rs.getLong("nun"));
            n.setDate(rs.getDate("datum"));
            n.setCode(rs.getString("sifra"));
            n.setPrice(rs.getBigDecimal("iznos"));
            a.setInvoice(n);

            invoiceUsers.add(a);
        }
    }

    public List<InvoiceUser> getInvoiceUsers() {
        return invoiceUsers;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.invoiceuser;

import domain.InvoiceUser;
import operation.AbstractGenericOperation;


public class InsertInvoiceUser extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.add((InvoiceUser)param);
    }
    
}

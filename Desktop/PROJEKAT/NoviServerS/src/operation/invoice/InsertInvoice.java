/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.invoice;

import domain.Invoice;
import domain.InvoiceItem;
import operation.AbstractGenericOperation;

/**
 *
 * @author SystemX
 */
public class InsertInvoice extends AbstractGenericOperation {
    @Override
    protected void preconditions(Object param) throws Exception {

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.add((Invoice)param);
        
        for (InvoiceItem ii : ((Invoice)param).getListOfInvoice()) {
            repository.add(ii);
        }
    }
}

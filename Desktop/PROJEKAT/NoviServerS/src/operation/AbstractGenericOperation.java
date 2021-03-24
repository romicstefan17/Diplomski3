/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation;

import repository.db.Repository;
import repository.db.impl.GenericDBRepository;


public abstract class AbstractGenericOperation {
    protected final Repository repository;

    public AbstractGenericOperation() {
        this.repository = new GenericDBRepository();
    }
    
    public final void execute (Object param) throws Exception{
        try {
            preconditions(param);
            startTransaction();
            executeOperation(param);
            commitTransaction();
        } catch(Exception e) {
            rollbackTransaction();
            e.printStackTrace();
            throw e;
        } finally {
            disconnect();
        }
    }

    protected abstract void preconditions(Object param) throws Exception;

    private void startTransaction() throws Exception{
        ((GenericDBRepository)repository).connect();
    }

    protected abstract void executeOperation(Object param)throws Exception;

    private void commitTransaction() throws Exception{
        ((GenericDBRepository)repository).commit();
    }

    private void rollbackTransaction() throws Exception{
        ((GenericDBRepository)repository).rollback();
    }

    private void disconnect() throws Exception{
        ((GenericDBRepository)repository).disconnect();
    }
}

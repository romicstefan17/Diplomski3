/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.supplier;

import domain.Supplier;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author SystemX
 */
public class GetAllSuppliers extends AbstractGenericOperation {

    List<Supplier> pro = new ArrayList();

    @Override
    protected void preconditions(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        ResultSet rs = repository.getAll((Supplier) param);
        while (rs.next()) {
            Supplier d = new Supplier();

            d.setSupplierID(rs.getLong("dobavljacid"));
            d.setName(rs.getString("naziv"));

            pro.add(d);

        }
    }

    public List<Supplier> getPro() {
        return pro;
    }

}

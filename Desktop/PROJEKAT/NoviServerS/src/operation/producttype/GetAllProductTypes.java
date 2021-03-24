/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.producttype;

import domain.ProductType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author SystemX
 */
public class GetAllProductTypes extends AbstractGenericOperation {

    List<ProductType> pro = new ArrayList();

    @Override
    protected void preconditions(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        ResultSet rs = repository.getAll((ProductType) param);
        while (rs.next()) {
            ProductType v = new ProductType();
            v.setVrstaID(rs.getLong("vrstaid"));
            v.setNazivVrste(rs.getString("naziv"));

            pro.add(v);

        }

    }

    public List<ProductType> getPro() {
        return pro;
    }

}

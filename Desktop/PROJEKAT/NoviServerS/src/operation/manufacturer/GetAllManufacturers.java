/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.manufacturer;

import domain.Manufacturer;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author SystemX
 */
public class GetAllManufacturers extends AbstractGenericOperation {

    List<Manufacturer> pro = new ArrayList();

    @Override
    protected void preconditions(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        ResultSet rs = repository.getAll((Manufacturer) param);
        while (rs.next()) {
            Manufacturer p = new Manufacturer();
            p.setManufacturerID(rs.getLong("proizvodjacid"));
            p.setName(rs.getString("naziv"));

            pro.add(p);
        }
    }

    public List<Manufacturer> getPro() {
        return pro;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.user;

import domain.User;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author SystemX
 */
public class GetAllUsers extends AbstractGenericOperation {

    List<User> users = new ArrayList();

    @Override
    protected void preconditions(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

         ResultSet rs = repository.getAll((User)param);
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getLong(1));
                u.setFirstName(rs.getString(2));
                u.setLastName(rs.getString(3));
                u.setPassword(rs.getString(5));
                u.setUserName(rs.getString(4));
                u.setAdress(rs.getString(6));
                u.setStatus(rs.getString(7));
                u.setUloga(rs.getString(8));

                users.add(u);

            }
    }

    public List<User> getUsers() {
        return users;
    }

}

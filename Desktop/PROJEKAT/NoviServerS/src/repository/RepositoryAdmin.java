/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domain.Admin;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SystemX
 */
public class RepositoryAdmin {
     List<Admin> admins;

    public RepositoryAdmin() {
        this.admins = new ArrayList<Admin>() {
            {
                add(new Admin("Admin1", "Admin1", "Stefan", "Romic"));
            }
        };
    }

    public List<Admin> getAllAdmins() {
        return admins;
    }
}

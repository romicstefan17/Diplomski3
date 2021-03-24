/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public interface DbRepository<Object> extends Repository<Object> {

    default public void connect() {
        try {
            DbConnectionFactory.getInstance().getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DbRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    default public void disconnect() {
        try {
            DbConnectionFactory.getInstance().getConnection().close();
        } catch (SQLException ex) {
            Logger.getLogger(DbRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    default public void rollback() {
        try {
            DbConnectionFactory.getInstance().getConnection().rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DbRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    default public void commit() {
        try {
            DbConnectionFactory.getInstance().getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(DbRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

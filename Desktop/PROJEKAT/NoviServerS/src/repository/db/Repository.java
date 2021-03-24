/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;


import java.sql.ResultSet;


/**
 *
 * @author SystemX
 */
public interface Repository<T> {
    ResultSet getAll(T obj)throws Exception;
    void add(T t) throws Exception;
    void edit(T t) throws Exception;
    void delete(T t)throws Exception;
    ResultSet get(T t) throws Exception;
}

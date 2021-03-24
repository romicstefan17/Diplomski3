/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.PreparedStatement;

/**
 *
 * @author SystemX
 */
public class Supplier implements GenericEntity {

    private Long supplierID;
    private String name;

    public Supplier() {

    }

    public Supplier(Long supplierID, String name) {
        this.supplierID = supplierID;
        this.name = name;

    }

    public Long getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Long supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getTableName() {
        return "dobavljac";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "dobavljacid,naziv";
    }

    @Override
    public void getInsertValues(PreparedStatement statement) throws Exception {
        statement.setLong(1, supplierID);
        statement.setString(2, name);
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public void setId(Long id) {
        this.supplierID=id;
    }

    @Override
    public String getColumnNamesForUpdate() {
        return "dobavljacid=?,naziv=?";
    }

    @Override
    public String getConditionForUpdate() {
        return "dobavljacid=" + supplierID;
    }

    @Override
    public String getConditionForDelete() {
         return "dobavljacid=" + supplierID;
    }

    @Override
    public String getColumnNamesForSelect() {
        return "*";
    }

    @Override
    public String getTableForSelect() {
        return "dobavljac";
    }

    @Override
    public String getConditionForSelect() {
        return "";
    }

    @Override
    public String getConditionForSelectSpecific() {
        return "";
    }

}

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
public class Manufacturer implements GenericEntity{
    
    private Long manufacturerID;
    private String name;

    public Manufacturer() {
    }

    public Manufacturer(Long manufacturerID, String name) {
        this.manufacturerID = manufacturerID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(Long manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getTableName() {
        return "proizvodjac";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "proizvodjacid,naziv";
    }

    @Override
    public void getInsertValues(PreparedStatement statement) throws Exception {
        statement.setLong(1, manufacturerID);
        statement.setString(2, name);
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public void setId(Long id) {
        this.manufacturerID=id;
    }

    @Override
    public String getColumnNamesForUpdate() {
         return "proizvodjacid=?,naziv=?";
    }

    @Override
    public String getConditionForUpdate() {
        return "proizvodjacid="+manufacturerID;
    }

    @Override
    public String getConditionForDelete() {
          return "proizvodjacid="+manufacturerID;
    }

    @Override
    public String getColumnNamesForSelect() {
        return "*";
    }

    @Override
    public String getTableForSelect() {
        return "proizvodjac";
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

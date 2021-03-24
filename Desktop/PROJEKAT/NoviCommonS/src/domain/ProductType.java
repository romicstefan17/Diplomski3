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
public class ProductType implements GenericEntity {

    private Long vrstaID;
    private String nazivVrste;

    public ProductType() {
    }

    public ProductType(Long vrstaID, String nazivVrste) {
        this.vrstaID = vrstaID;
        this.nazivVrste = nazivVrste;
    }

    public String getNazivVrste() {
        return nazivVrste;
    }

    public void setNazivVrste(String nazivVrste) {
        this.nazivVrste = nazivVrste;
    }

    public Long getVrstaID() {
        return vrstaID;
    }

    public void setVrstaID(Long vrstaID) {
        this.vrstaID = vrstaID;
    }

    @Override
    public String toString() {
        return nazivVrste;
    }

    @Override
    public String getTableName() {
        return "vrsta";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "";
    }

    @Override
    public void getInsertValues(PreparedStatement statement) throws Exception {
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public void setId(Long id) {
        this.vrstaID = id;
    }

    @Override
    public String getColumnNamesForUpdate() {
        return "";
    }

    @Override
    public String getConditionForUpdate() {
        return "";
    }

    @Override
    public String getConditionForDelete() {
        return "";
    }

    @Override
    public String getColumnNamesForSelect() {
        return "*";
    }

    @Override
    public String getTableForSelect() {
        return "vrsta";
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

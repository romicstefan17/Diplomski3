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
public class User implements GenericEntity {

    private Long userID;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String adress;
    private String status = "izlogovan";
    private String uloga = "user";

    public User() {
    }

    public User(Long userID, String firstName, String lastName, String userName, String password, String adress) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.adress = adress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String mesto) {
        this.adress = mesto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public String getTableName() {
        return "korisnici";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "zaposleniid,ime,prezime,username,password,adresa,ulogovan,uloga";
    }

    @Override
    public void getInsertValues(PreparedStatement statement) throws Exception {
        statement.setLong(1, userID);
        statement.setString(2, firstName);
        statement.setString(3, lastName);
        statement.setString(4, userName);
        statement.setString(5, password);
        statement.setString(6, adress);
        statement.setString(7, status);
        statement.setString(8, uloga);
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public void setId(Long id) {
        this.userID = id;
    }

    @Override
    public String getColumnNamesForUpdate() {
        return "zaposleniid=?,ime=?,prezime=?,username=?,password=?,adresa=?,ulogovan=?,uloga=?";
    }

    @Override
    public String getConditionForUpdate() {
        return "zaposleniid=" + userID;
    }

    @Override
    public String getConditionForDelete() {
        return "zaposleniid=" + userID;
    }

    @Override
    public String getColumnNamesForSelect() {
        return "*";
    }

    @Override
    public String getTableForSelect() {
        return "korisnici";
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

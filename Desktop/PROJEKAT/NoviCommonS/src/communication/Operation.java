/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.Serializable;

/**
 *
 * @author SystemX
 */
public class Operation implements Serializable {

    public static final int LOGIN = 1;
    public static final int GET_ALL_PRODUCTS = 2;
    public static final int GET_ALL_MANUFACTURERS = 3;
    public static final int LOGOUT = 4;
    public static final int LOGOUT_ALL = 5;
    public static final int GET_ALL_USERS = 6;
    public static final int GET_ALL_TYPE = 7;
    public static final int GET_USER = 8;
    public static final int GET_POSLEDNJE_OBRISAN_PROIZVOD = 10;
    public static final int SET_POSLEDNJE_OBRISAN_PROIZVOD = 11;
    public static final int ADD_PRODUCT = 12;
    public static final int DELETE_PRODUCT = 13;
    public static final int UPDATE_PRODUXT = 14;
    public static final int GET_ALL_SUPPLIERS = 16;
    public static final int GET_ALL_INVOICES = 17;
    public static final int ADD_USER = 18;
    public static final int ADD_INVOICE = 20;
    public static final int ADD_INVOICEUSER = 21;
    public static final int GET_ALL_INVOICEUSERS = 22;
    public static final int DELETE_INVOICEUSER = 23;
    public static final int GET_ALL_ITEMS = 24;
    public static final int GET_INVOICEUSER = 25;
    public static final int UPDATE_SUPPLIER = 26;
    public static final int ADD_SUPPLIER = 27;
    public static final int UPDATE_MANUFACTURER = 28;
    public static final int ADD_MANUFACTURER = 29;
    public static final int DELETE_MANUFACTURER = 30;
    public static final int GET_LAST_ID = 32;
    public static final int GET_ALL_PROIZVODI_CB = 33;
    public static final int SIGN_OUT = 34;
    public static final int INSERT_REVIEW = 35;
    public static final int GET_ALL_REVIEWS = 36;
    public static final int UPDATE_TABLE = 37;
    public static final int GET_REVIEWS = 38;
    public static final int FILL_REVIEW = 39;
    public static final int SEND_REVIEW = 40;
    public static final int UPDATE_REVIEW = 41;
    public static final int DELETE_SUPPLIER = 42;
    public static final int UPDATE_TABLE_INVOICE = 43;
    public static final int GET_ALL_REVIEWS_ADMIN = 44;
}

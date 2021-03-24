/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.InvoiceItem;
import domain.InvoiceUser;
import domain.Manufacturer;
import domain.Product;
import domain.ProductType;
import domain.Review;
import domain.Supplier;
import domain.User;
import java.util.ArrayList;
import thread.ServerResponseHandler;

public class Communication {

    private final Socket socket;
    private static Communication instance;
    private List<User> useri = new ArrayList<>();
    private List<InvoiceUser> ui = new ArrayList<>();
    private InvoiceUser invoice;
    private User user;
    private List<Product> products = new ArrayList<>();
    private List<InvoiceItem> items = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private List<Manufacturer> manufacturers = new ArrayList<>();
    private List<Supplier> suppliers = new ArrayList<>();
    private List<ProductType> type = new ArrayList<>();
    private String komentari = "";
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ServerResponseHandler srh;

    private Communication() throws Exception {
        socket = new Socket("localhost", 9000);
        srh = new ServerResponseHandler();
        srh.start();
    }

    public static Communication getInstance() throws Exception {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public void stopCommunication() throws IOException {
        instance = null;
    }

    public void sendUserRequest(Request request) {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Response receiveServerResponse() {
        Response response = new Response();
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            response = (Response) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    public List<User> getAllUsers() {
        return useri;
    }

    public void setAllUsers(List<User> sviZaposleni) {
        this.useri = sviZaposleni;
    }

    public void setUser(User z) {
        this.user = z;
    }

    public User getUser() {
        return user;
    }

    public void setInvoiceUser(List<InvoiceUser> asocijacije) {
        ui = asocijacije;

    }

    public List<InvoiceUser> getUi() {
        return ui;
    }

    public InvoiceUser getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceUser invoice) {
        this.invoice = invoice;
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setManufacturers(List<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setUi(List<InvoiceUser> ui) {
        this.ui = ui;
    }

    public void setType(List<ProductType> type) {
        this.type = type;
    }

    public List<ProductType> getType() {
        return type;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public void setReviewsText(String reviews) {
        this.komentari = reviews;
    }

    public String getKomentari() {
        return komentari;
    }

    public void stopClientCommunication() {
        try {
            socket.close();
            ois.close();
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

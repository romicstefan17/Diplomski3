/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import communication.Response;
import domain.Admin;
import domain.Invoice;
import domain.InvoiceItem;
import domain.InvoiceUser;
import domain.Manufacturer;
import domain.Product;
import domain.ProductType;
import domain.Review;
import domain.Supplier;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import operation.AbstractGenericOperation;
import operation.invoice.GetAllInvoice;
import operation.invoice.InsertInvoice;
import operation.invoiceitem.GetAllInvoiceItems;
import operation.invoiceuser.DeleteInvoiceUser;
import operation.invoiceuser.GetAllInvoiceUser;
import operation.invoiceuser.GetInvoiceUser;
import operation.invoiceuser.InsertInvoiceUser;
import operation.manufacturer.DeleteManufacturer;
import operation.manufacturer.GetAllManufacturers;
import operation.manufacturer.InsertManufacturer;
import operation.manufacturer.UpdateManufacturer;
import operation.product.DeleteProduct;
import operation.product.GetAllProducts;
import operation.product.InsertProduct;
import operation.product.UpdateProduct;
import operation.producttype.GetAllProductTypes;
import operation.review.GetAllReviews;
import operation.review.GetReview;
import operation.review.InsertReview;
import operation.review.UpdateReview;
import operation.supplier.DeleteSupplier;
import operation.supplier.GetAllSuppliers;
import operation.supplier.InsertSupplier;
import operation.supplier.UpdateSupplier;
import operation.user.GetAllUsers;
import operation.user.InsertUser;
import operation.user.UpdateUser;
import repository.RepositoryAdmin;
import server.Server;
import thread.ClientRequestHandler;

/**
 *
 * @author SystemX
 */
public class Controller {

    private static Controller instance;

    private Admin admin;
    private Server server;
    private final RepositoryAdmin repositoryAdmin;
    private Product product;
    private final List<ClientRequestHandler> crhList;
    private User loggedInUser;
    private Double ocena;
    private Response response;

    private Controller() {

        repositoryAdmin = new RepositoryAdmin();
        product = new Product();
        crhList = new ArrayList<>();
        response = new Response();
        loggedInUser = new User();

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Admin> getAllAdmins() {
        return repositoryAdmin.getAllAdmins();
    }

    public Admin loginAdmin(String username, String password) throws Exception {
        List<Admin> admini = repositoryAdmin.getAllAdmins();
        for (Admin admin : admini) {
            if (admin.getUserName().equals(username)) {
                if (admin.getPassword().equals(password)) {
                    return admin;
                } else {
                    throw new Exception("Pogresna sifra");
                }
            }
        }
        throw new Exception("Nepostojeci admin");
    }

    public List<Manufacturer> getAllManufacturers() throws Exception {

        AbstractGenericOperation operation = new GetAllManufacturers();
        operation.execute(new Manufacturer());

        List<Manufacturer> manufacturers = ((GetAllManufacturers) operation).getPro();
        return manufacturers;

    }

    public List<Product> getAllProducts() throws Exception {
        AbstractGenericOperation operation = new GetAllProducts();
        operation.execute(new Product());

        List<Product> products = ((GetAllProducts) operation).getPro();
        return products;
    }

    public List<User> getAllUsers() throws Exception {
        AbstractGenericOperation operation = new GetAllUsers();
        operation.execute(new User());

        List<User> users = ((GetAllUsers) operation).getUsers();
        return users;
    }

    public List<ProductType> getAllProductType() throws Exception {
        AbstractGenericOperation operation = new GetAllProductTypes();
        operation.execute(new ProductType());

        List<ProductType> types = ((GetAllProductTypes) operation).getPro();
        return types;
    }

    public User login(String username, String password) throws Exception {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public Product getPoslednjeObrisanProizvod() {
        return product;
    }

    public void setPoslednjeObrisanProzivod(Product product) {
        this.product = product;
    }

    public void addProduct(Product product) throws Exception {
        AbstractGenericOperation operation = new InsertProduct();
        operation.execute(product);
    }

    public void deleteProduct(Product p) throws Exception {
        AbstractGenericOperation operation = new DeleteProduct();
        operation.execute(p);
    }

    public void updateProduct(Product p) throws Exception {
        AbstractGenericOperation operation = new UpdateProduct();
        operation.execute(p);
    }

    public List<Supplier> getAllSuppliers() throws Exception {
        AbstractGenericOperation operation = new GetAllSuppliers();
        operation.execute(new Supplier());

        List<Supplier> suppliers = ((GetAllSuppliers) operation).getPro();
        return suppliers;
    }

    public List<Invoice> getAllInvoices() throws Exception {
        AbstractGenericOperation operation = new GetAllInvoice();
        operation.execute(new Invoice());

        List<Invoice> invoices = ((GetAllInvoice) operation).getInvoices();
        return invoices;

    }

    public void addUser(User user) throws Exception {
        AbstractGenericOperation operation = new InsertUser();
        operation.execute(user);
    }

    public void addInvoice(Invoice invoice) throws Exception {
        AbstractGenericOperation operation = new InsertInvoice();
        operation.execute(invoice);
    }

    public void addInvoiceUser(InvoiceUser iu) throws Exception {
        AbstractGenericOperation operation = new InsertInvoiceUser();
        operation.execute(iu);
    }

    public List<InvoiceUser> getAllInvoiceUser() throws Exception {
        AbstractGenericOperation operation = new GetAllInvoiceUser();
        operation.execute(new InvoiceUser());
        List<InvoiceUser> invoiceUserList = ((GetAllInvoiceUser) operation).getInvoiceUsers();
        return invoiceUserList;
    }

    public void deleteInvoiceUser(InvoiceUser iu) throws Exception {
        AbstractGenericOperation operation = new DeleteInvoiceUser();
        operation.execute(iu);
    }

    public List<InvoiceItem> getAllInvoiceItems() throws Exception {
        AbstractGenericOperation operation = new GetAllInvoiceItems();
        operation.execute(new InvoiceItem());
        List<InvoiceItem> invoiceList = ((GetAllInvoiceItems) operation).getStavke();
        return invoiceList;
    }

    public List<InvoiceUser> getInvoiceUser(InvoiceUser iu) throws Exception {
        AbstractGenericOperation operation = new GetInvoiceUser();
        operation.execute(iu);
        List<InvoiceUser> collections = ((GetInvoiceUser) operation).getAs();

        return collections;
    }

    public void updateSupplier(Supplier sup) throws Exception {
        AbstractGenericOperation operation = new UpdateSupplier();
        operation.execute(sup);
    }

    public void addSupplier(Supplier sup) throws Exception {
        AbstractGenericOperation operation = new InsertSupplier();
        operation.execute(sup);
    }

    public void updateManufacturer(Manufacturer manu) throws Exception {
        AbstractGenericOperation operation = new UpdateManufacturer();
        operation.execute(manu);
    }

    public void addManufacturer(Manufacturer manu) throws Exception {
        AbstractGenericOperation operation = new InsertManufacturer();
        operation.execute(manu);
    }

    public void deleteSupplier(Supplier sup) throws Exception {
        AbstractGenericOperation operation = new DeleteSupplier();
        operation.execute(sup);
    }

    public void deleteManufacturer(Manufacturer manu) throws Exception {
        AbstractGenericOperation operation = new DeleteManufacturer();
        operation.execute(manu);
    }

    public void updateUser(User user) throws Exception {
        AbstractGenericOperation operation = new UpdateUser();
        operation.execute(user);
    }

    public void startServer() {
        server = new Server();
        server.start();
    }

    public void stopServer() throws Exception {
        for (ClientRequestHandler clientHandlerRequest : crhList) {
            clientHandlerRequest.logoutAll();
        }
        if (server != null) {
            server.stopServer();
        }
    }

    public void addChr(ClientRequestHandler crh) {
        crhList.add(crh);
    }

    public List<ClientRequestHandler> getCrhList() {
        return crhList;
    }

    public void setUser(User ulogovaniZaposleni) {
        this.loggedInUser = ulogovaniZaposleni;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void changeActivation(User zaposleni) throws Exception {
        zaposleni.setStatus("izlogovan");
        Controller.getInstance().updateUser(zaposleni);
        removeChr(zaposleni);
    }

    public void insertReview(Review review) throws Exception {
        AbstractGenericOperation operation = new InsertReview();
        operation.execute(review);
    }

    public List<Review> selectAllReviews() throws Exception {
        AbstractGenericOperation operation = new GetAllReviews();
        operation.execute(new Review());
        List<Review> reviews = ((GetAllReviews) operation).getRevies();
        return reviews;
    }

    public void setOcena(Double d) {
        this.ocena = d;
    }

    public Double getOcena() {
        return ocena;
    }

    public void updateTableOfProducts() throws Exception {
        for (ClientRequestHandler clientHandlerRequest : crhList) {
            clientHandlerRequest.UpdateTableOfProducts();
        }
    }

    public void updateTableOfInvoice() throws Exception {
        for (ClientRequestHandler clientHandlerRequest : crhList) {
            clientHandlerRequest.UpdateTableOfInvoice();
        }
    }

    public List<Review> getReview(Review review) throws Exception {
        AbstractGenericOperation operation = new GetReview();
        operation.execute(review);
        List<Review> reviews = ((GetReview) operation).getRevies();

        return reviews;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void editReview(Review reviewUpdate) throws Exception {
        AbstractGenericOperation operation = new UpdateReview();
        operation.execute(reviewUpdate);
    }

    private void removeChr(User zaposleni) {
        for (ClientRequestHandler clientRequestHandler : Controller.getInstance().getCrhList()) {
            if (Objects.equals(clientRequestHandler.getZaposleni(), zaposleni)) {
                System.out.println("izlogovan");
                crhList.remove(clientRequestHandler);
            }
        }
    }

}

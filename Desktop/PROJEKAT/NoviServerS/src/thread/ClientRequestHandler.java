/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import communication.Operation;
import communication.Request;
import communication.Response;
import controller.Controller;
import cordinator.MainCordinator;
import domain.Invoice;
import domain.InvoiceUser;
import domain.Manufacturer;
import domain.Product;
import domain.Review;
import domain.Supplier;
import domain.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;

/**
 *
 * @author SystemX
 */
public class ClientRequestHandler extends Thread {

    private final Socket socket;
    private boolean stop = false;
    private User zaposleni;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public ClientRequestHandler(Socket socket, User user) {
        this.socket = socket;
        this.zaposleni = user;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                Request request = receiveUserRequest();
                Response response = new Response();
                response.setOperation(request.getOperation());
                try {

                    switch (request.getOperation()) {
                        case Operation.LOGIN:
                            User user = (User) request.getArgument();

                            User ulogovaniZaposleni = Controller.getInstance().login(user.getUserName(), user.getPassword());

                            if (ulogovaniZaposleni != null) {

                                response.setResult(ulogovaniZaposleni);
                                ulogovaniZaposleni.setStatus("ulogovan");
                                System.out.println("" + ulogovaniZaposleni.getUloga());
                                Controller.getInstance().setUser(ulogovaniZaposleni);
                                Controller.getInstance().updateUser(ulogovaniZaposleni);
                                MainCordinator.getInstance().getMc().fillTbl(Controller.getInstance().getAllUsers());
                            } else {
                                response.setResult(null);
                            }

                            sendServerResponse(socket, response);

                            break;

                        case Operation.GET_ALL_INVOICEUSERS:
                            response.setResult(Controller.getInstance().getAllInvoiceUser());
                            sendServerResponse(socket, response);
                            break;

                        case Operation.GET_ALL_SUPPLIERS:
                            response.setResult(Controller.getInstance().getAllSuppliers());
                            sendServerResponse(socket, response);

                            break;
                        case Operation.GET_ALL_MANUFACTURERS:
                            response.setResult(Controller.getInstance().getAllManufacturers());
                            sendServerResponse(socket, response);

                            break;

                        case Operation.GET_ALL_ITEMS:
                            response.setResult(Controller.getInstance().getAllInvoiceItems());
                            sendServerResponse(socket, response);

                            break;
                        case Operation.GET_ALL_USERS:
                            response.setResult(Controller.getInstance().getAllUsers());
                            sendServerResponse(socket, response);
                            break;
                        case Operation.GET_ALL_TYPE:
                            response.setResult(Controller.getInstance().getAllProductType());
                            sendServerResponse(socket, response);
                            break;
                        case Operation.GET_ALL_INVOICES:
                            response.setResult(Controller.getInstance().getAllInvoices());
                            sendServerResponse(socket, response);
                            break;
                        case Operation.GET_ALL_PRODUCTS:
                            response.setResult(Controller.getInstance().getAllProducts());
                            sendServerResponse(socket, response);
                            break;

                        case Operation.GET_INVOICEUSER:
                            InvoiceUser a = (InvoiceUser) request.getArgument();
                            response.setResult(Controller.getInstance().getInvoiceUser(a));
                            sendServerResponse(socket, response);
                            break;
                        case Operation.GET_POSLEDNJE_OBRISAN_PROIZVOD:
                            response.setResult(Controller.getInstance().getPoslednjeObrisanProizvod());
                            sendServerResponse(socket, response);
                            break;
                        case Operation.GET_USER:
                            response.setResult(Controller.getInstance().getLoggedInUser());
                            sendServerResponse(socket, response);
                            break;
                        case Operation.ADD_INVOICEUSER:
                            InvoiceUser a1 = (InvoiceUser) request.getArgument();
                            a1.setInvoice(Controller.getInstance().getAllInvoices().get(Controller.getInstance().getAllInvoices().size() - 1));
                            Controller.getInstance().addInvoiceUser(a1);
                            Controller.getInstance().updateTableOfInvoice();

                            break;
                        case Operation.ADD_SUPPLIER:
                            Supplier d = (Supplier) request.getArgument();
                            Controller.getInstance().addSupplier(d);
                            break;
                        case Operation.ADD_INVOICE:
                            Invoice n = (Invoice) request.getArgument();
                            Controller.getInstance().addInvoice(n);
                            Controller.getInstance().updateTableOfInvoice();
                            Controller.getInstance().updateTableOfProducts();
                            response.setResult(n);

                            break;
                        case Operation.ADD_PRODUCT:
                            Product p = (Product) request.getArgument();
                            Controller.getInstance().addProduct(p);
                            Controller.getInstance().updateTableOfProducts();
                            break;
                        case Operation.ADD_MANUFACTURER:
                            Manufacturer pr = (Manufacturer) request.getArgument();
                            Controller.getInstance().addManufacturer(pr);
                            break;
                        case Operation.ADD_USER:
                            User z = (User) request.getArgument();
                            Controller.getInstance().addUser(z);

                            break;
                        case Operation.DELETE_PRODUCT:
                            Product p1 = (Product) request.getArgument();
                            Controller.getInstance().deleteProduct(p1);
                            Controller.getInstance().updateTableOfProducts();

                            break;
                        case Operation.DELETE_INVOICEUSER:
                            InvoiceUser a2 = (InvoiceUser) request.getArgument();
                            Controller.getInstance().deleteInvoiceUser(a2);
                            Controller.getInstance().updateTableOfInvoice();
                            break;
                        case Operation.DELETE_MANUFACTURER:
                            Manufacturer pr1 = (Manufacturer) request.getArgument();
                            Controller.getInstance().deleteManufacturer(pr1);
                            break;
                        case Operation.DELETE_SUPPLIER:
                            Supplier sup = (Supplier) request.getArgument();
                            Controller.getInstance().deleteSupplier(sup);
                            break;
                        case Operation.SET_POSLEDNJE_OBRISAN_PROIZVOD:
                            Product proizvod = (Product) request.getArgument();
                            Controller.getInstance().setPoslednjeObrisanProzivod(proizvod);
                            break;

                        case Operation.UPDATE_SUPPLIER:
                            Supplier d3 = (Supplier) request.getArgument();
                            Controller.getInstance().updateSupplier(d3);

                            break;
                        case Operation.UPDATE_PRODUXT:
                            Product p4 = (Product) request.getArgument();
                            Controller.getInstance().updateProduct(p4);
                            Controller.getInstance().updateTableOfProducts();

                            break;
                        case Operation.UPDATE_MANUFACTURER:
                            Manufacturer p5 = (Manufacturer) request.getArgument();
                            Controller.getInstance().updateManufacturer(p5);
                            break;

                        case Operation.SIGN_OUT:
                            User z12 = (User) request.getArgument();
                            Controller.getInstance().changeActivation(z12);
                            break;
                        case Operation.GET_ALL_PROIZVODI_CB:
                            response.setResult(Controller.getInstance().getAllProducts());
                            sendServerResponse(socket, response);
                            break;
                        case Operation.INSERT_REVIEW:
                            Review reviewInsert = (Review) request.getArgument();
                            Controller.getInstance().insertReview(reviewInsert);
                            Controller.getInstance().updateTableOfProducts();

                            break;
                        case Operation.FILL_REVIEW:
                            Review review = (Review) request.getArgument();

                            List<Review> r = Controller.getInstance().getReview(review);

                            response.setOperation(Operation.FILL_REVIEW);
                            response.setResult(r.get(0));
                            sendServerResponse(socket, response);
                            break;
                        case Operation.UPDATE_REVIEW:
                            Review reviewUpdate = (Review) request.getArgument();
                            Controller.getInstance().editReview(reviewUpdate);

                            Controller.getInstance().updateTableOfProducts();

                            break;
                        case Operation.GET_ALL_REVIEWS_ADMIN:
                            Product product = (Product) request.getArgument();
                            List<Review> reviews = Controller.getInstance().selectAllReviews();
                            String nalepi = "";
                            for (Review review1 : reviews) {
                                if (Objects.equals(review1.getProduct().getProductID(), product.getProductID())) {
                                    for (User userr : Controller.getInstance().getAllUsers()) {
                                        if (Objects.equals(userr.getUserID(), review1.getUser().getUserID())) {
                                            nalepi += userr + ": " + review1.getReviewText() + "\n";
                                        }

                                    }
                                }
                            }
                            System.out.println("sex" + nalepi);
                            response.setOperation(Operation.GET_ALL_REVIEWS_ADMIN);
                            response.setResult(nalepi);
                            sendServerResponse(socket, response);
                            break;

                        case Operation.GET_ALL_REVIEWS:

                            List<Review> reviewss = Controller.getInstance().selectAllReviews();
                            List<User> userss = Controller.getInstance().getAllUsers();
                            String nalepi2 = "";
                            for (Review review1 : reviewss) {
                                for (User userr : userss) {
                                    if (Objects.equals(review1.getUser().getUserID(), userr.getUserID())) {
                                        nalepi2 += userr + ": " + review1.getReviewText() + "\n";
                                    }
                                }
                            }
                            response.setOperation(Operation.GET_ALL_REVIEWS);
                            response.setResult(nalepi2);
                            sendServerResponse(socket, response);
                            break;

                    }

                } catch (Exception ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

                }
            } catch (Exception ex) {
                Logger.getLogger(ClientRequestHandler.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

    }

    private Request receiveUserRequest() {
        Request request = new Request();
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            request = (Request) ois.readObject();

        } catch (IOException ex) {
            Logger.getLogger(ClientRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return request;
    }

    private void sendServerResponse(Socket s, Response response) {
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(response);

        } catch (IOException ex) {
            Logger.getLogger(ClientRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Response logoutAll() throws Exception {
        stop = true;
        Response response = new Response();
        response.setOperation(Operation.LOGOUT_ALL);
        response.setPoruka("SERVER JE UGASEN!");
        sendServerResponse(socket, response);
        return response;
    }

    public Response UpdateTableOfProducts() throws Exception {
        Response response = new Response();
        response.setOperation(Operation.UPDATE_TABLE);
        response.setResult(Controller.getInstance().getAllProducts());
        sendServerResponse(socket, response);
        return response;
    }

    public User getZaposleni() {
        return zaposleni;
    }

    public Response UpdateTableOfInvoice() throws Exception {
        Response response = new Response();
        response.setOperation(Operation.UPDATE_TABLE_INVOICE);
        response.setResult(Controller.getInstance().getAllInvoiceUser());
        sendServerResponse(socket, response);
        return response;
    }

    private void stopConnection() {
        try {
            socket.close();
            ois.close();
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

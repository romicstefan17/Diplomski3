/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.util.List;
import javax.swing.JOptionPane;
import communication.Communication;
import communication.Response;
import communication.Operation;
import cordinator.MainCordinatorAdmin;
import cordinator.MainCordinatorClient;
import domain.Invoice;
import domain.InvoiceItem;
import domain.InvoiceUser;
import domain.Manufacturer;
import domain.Product;
import domain.ProductType;
import domain.Review;
import domain.Supplier;
import domain.User;
import java.util.Objects;

public class ServerResponseHandler extends Thread {

    private boolean kraj = false;

    @Override
    public void run() {
        try {
            while (!kraj) {
                Response response = Communication.getInstance().receiveServerResponse();
                switch (response.getOperation()) {
                    case Operation.GET_ALL_ITEMS:
                        List<InvoiceItem> sn = (List<InvoiceItem>) response.getResult();
                        Communication.getInstance().setItems(sn);
                        if (MainCordinatorClient.getInstance().getDetailsInvoiceController() != null) {
                            MainCordinatorClient.getInstance().getDetailsInvoiceController().fillItems(sn);
                        }
                        if (MainCordinatorAdmin.getInstance().getDetailsController() != null) {
                            MainCordinatorAdmin.getInstance().getDetailsController().fillItems(sn);
                        }
                        if (MainCordinatorAdmin.getInstance().getMainController() != null) {
                            MainCordinatorAdmin.getInstance().getMainController().fillTblInvoiceItem(sn);
                        }
                        break;
                    case Operation.GET_ALL_INVOICES:
                        List<Invoice> narudzbe = (List<Invoice>) response.getResult();
                        break;
                    case Operation.GET_ALL_PRODUCTS:
                        List<Product> proizvodi = (List<Product>) response.getResult();
                        Communication.getInstance().setProducts(proizvodi);

                        if (MainCordinatorClient.getInstance().getMainController() != null) {
                            MainCordinatorClient.getInstance().getMainController().fillTbl(proizvodi);
                        }

                        if (MainCordinatorAdmin.getInstance().getProductListController() != null) {
                            MainCordinatorAdmin.getInstance().getProductListController().fillTblProducts(proizvodi);
                        }
                        if (MainCordinatorClient.getInstance().getClientProductInvoiceController() != null) {
                            MainCordinatorClient.getInstance().getClientProductInvoiceController().fillTblProducts(proizvodi);
                        }

                        break;
                    case Operation.GET_ALL_MANUFACTURERS:
                        List<Manufacturer> manufacturers = (List<Manufacturer>) response.getResult();
                        Communication.getInstance().setManufacturers(manufacturers);

                        if (MainCordinatorAdmin.getInstance().getListOfManufacturerController() != null) {
                            MainCordinatorAdmin.getInstance().getListOfManufacturerController().fillTblProizvodjac(manufacturers);
                        }
                        if (MainCordinatorAdmin.getInstance().getProductAddController() != null) {
                            MainCordinatorAdmin.getInstance().getProductAddController().fillcbProizvodjac(manufacturers);
                        }

                        break;
                    case Operation.GET_ALL_SUPPLIERS:
                        List<Supplier> dobavljaci = (List<Supplier>) response.getResult();
                        Communication.getInstance().setSuppliers(dobavljaci);
                        if (MainCordinatorAdmin.getInstance().getListOfSupplierController() != null) {
                            MainCordinatorAdmin.getInstance().getListOfSupplierController().fillTblDobavljac(dobavljaci);
                        }

                        if (MainCordinatorAdmin.getInstance().getProductAddController() != null) {
                            MainCordinatorAdmin.getInstance().getProductAddController().fillCbDobavljac(dobavljaci);
                        }

                        break;
                    case Operation.GET_USER:
                        User zaposleni = (User) response.getResult();
                        System.out.println("" + zaposleni);
                        if (MainCordinatorClient.getInstance().getNewInvoiceController() != null) {
                            MainCordinatorClient.getInstance().getNewInvoiceController().fillZaposleni(zaposleni);
                        }

                        break;
                    case Operation.GET_POSLEDNJE_OBRISAN_PROIZVOD:
                        Product proizvod = (Product) response.getResult();

                        break;
                    case Operation.GET_ALL_INVOICEUSERS:
                        List<InvoiceUser> asocijacije = (List<InvoiceUser>) response.getResult();
                        Communication.getInstance().setUi(asocijacije);
                        if (MainCordinatorClient.getInstance().getListOfInvoicesController() != null) {
                            MainCordinatorClient.getInstance().getListOfInvoicesController().setTblAsocijacija(asocijacije);
                        }

                        if (MainCordinatorAdmin.getInstance().getInvoiceController() != null) {
                            MainCordinatorAdmin.getInstance().getInvoiceController().setTblAsocijacija(asocijacije);
                        }
                        break;
                    case Operation.GET_INVOICEUSER:
                        List<InvoiceUser> asocijacije1 = (List<InvoiceUser>) response.getResult();
                        if (MainCordinatorClient.getInstance().getListOfInvoicesController() != null) {
                            MainCordinatorClient.getInstance().getListOfInvoicesController().setTblAsocijacija(asocijacije1);
                        }
                        break;
                    case Operation.LOGIN:
                        User z = (User) response.getResult();

                        if (z != null && z.getUloga().equals("user")) {
                            Communication.getInstance().setUser(z);
                            JOptionPane.showMessageDialog(MainCordinatorClient.getInstance().getLoginController().getFrmLogin(),
                                    "Uspesno ste se prijavili: " + z.getFirstName(), "Client", JOptionPane.INFORMATION_MESSAGE);
                            MainCordinatorClient.getInstance().openMainForm();
                            MainCordinatorClient.getInstance().getLoginController().getFrmLogin().dispose();
                        } else if (z != null && z.getUloga().equals("admin")) {
                            Communication.getInstance().setUser(z);
                            JOptionPane.showMessageDialog(MainCordinatorClient.getInstance().getLoginController().getFrmLogin(),
                                    "Uspesno ste se prijavili: " + z.getFirstName(), "Admin", JOptionPane.INFORMATION_MESSAGE);
                            MainCordinatorAdmin.getInstance().openMainForm();
                            MainCordinatorClient.getInstance().getLoginController().getFrmLogin().dispose();

                        } else {
                            JOptionPane.showMessageDialog(MainCordinatorClient.getInstance().getLoginController().getFrmLogin(), "Pogresili ste lozinku ili korisnicko ime");
                        }
                        break;
                    case Operation.GET_ALL_USERS:
                        List<User> sviZaposleni = (List<User>) response.getResult();

                        Communication.getInstance().setAllUsers(sviZaposleni);
                        break;
                    case Operation.LOGOUT_ALL:
                        List<User> users = (List<User>) response.getResult();
                        String poruka = response.getPoruka();

                        if (MainCordinatorClient.getInstance().getLoginController() != null) {
                            MainCordinatorClient.getInstance().getLoginController().getFrmLogin().dispose();
                        }
                        if (MainCordinatorClient.getInstance().getSignInController() != null) {
                            MainCordinatorClient.getInstance().getSignInController().getIn().dispose();
                        }
                        if (MainCordinatorClient.getInstance().getMainController().getFmn() != null && Communication.getInstance().getUser().getUloga().equals("user")) {
                            System.out.println("user forma");
                            JOptionPane.showMessageDialog(MainCordinatorClient.getInstance().getMainController().getFmn(), poruka);
                            MainCordinatorClient.getInstance().getMainController().getFmn().dispose();
                        }
                        if (MainCordinatorAdmin.getInstance().getLoginController() != null) {
                            MainCordinatorAdmin.getInstance().getLoginController().getFrmLogin().dispose();
                        }
                        if (MainCordinatorAdmin.getInstance().getMainController().getFrmMain() != null && Communication.getInstance().getUser().getUloga().equals("admin")) {
                            System.out.println("admin forma");
                            JOptionPane.showMessageDialog(MainCordinatorAdmin.getInstance().getMainController().getFrmMain(), poruka);
                            MainCordinatorAdmin.getInstance().getMainController().getFrmMain().dispose();
                        }
                        Communication.getInstance().stopCommunication();
                        kraj = true;
                        break;
                    case Operation.GET_ALL_PROIZVODI_CB:
                        List<Product> proizvodicb = (List<Product>) response.getResult();

                        System.out.println("" + proizvodicb);
                        break;
                    case Operation.ADD_PRODUCT:
                        List<Product> p = (List<Product>) response.getResult();
                        if (MainCordinatorClient.getInstance().getMainController() != null) {
                            MainCordinatorClient.getInstance().getMainController().fillTbl(p);
                        }
                        break;

                    case Operation.UPDATE_TABLE:
                        List<Product> products = (List<Product>) response.getResult();
                        if (MainCordinatorClient.getInstance().getMainController() != null) {
                            MainCordinatorClient.getInstance().getMainController().fillTbl(products);
                        }
                        Communication.getInstance().setProducts(products);
                        break;
                    case Operation.FILL_REVIEW:
                        Review review = (Review) response.getResult();

                        if (MainCordinatorClient.getInstance().getReviewController() != null) {
                            MainCordinatorClient.getInstance().getReviewController().fillComponents(review);
                        }

                        break;
                    case Operation.GET_ALL_TYPE:
                        List<ProductType> productTypes = (List<ProductType>) response.getResult();
                        Communication.getInstance().setType(productTypes);

                        if (MainCordinatorAdmin.getInstance().getProductAddController() != null) {
                            MainCordinatorAdmin.getInstance().getProductAddController().fillCbVrsta(productTypes);
                        }

                        break;
                    case Operation.GET_ALL_REVIEWS_ADMIN:
                        String reviews = (String) response.getResult();
                        Communication.getInstance().setReviewsText(reviews);

                        if (MainCordinatorAdmin.getInstance().getProductDetailController() != null) {
                            MainCordinatorAdmin.getInstance().getProductDetailController().getReviewText(reviews);
                        }

                        break;
                    case Operation.UPDATE_TABLE_INVOICE:
                        List<InvoiceUser> invoices = (List<InvoiceUser>) response.getResult();
                        if (MainCordinatorClient.getInstance().getListOfInvoicesController() != null) {
                            MainCordinatorClient.getInstance().getListOfInvoicesController().setTblAsocijacija(invoices);
                        }
                        Communication.getInstance().setInvoiceUser(invoices);
                        break;

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}

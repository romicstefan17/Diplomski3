/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cordinator;

import domain.InvoiceUser;
import domain.Product;
import form.client.FrmClientLogin;
import form.client.FrmDetailProduct;
import form.client.FrmDetailsOfInvoice;
import form.client.FrmListOfInvoices;
import form.client.FrmListOfProductsInvoice;
import form.client.FrmMainClient;
import form.client.FrmNewInvoice;
import form.client.FrmReview;
import form.client.FrmSignIn;
import form.controller.client.ClientLoginController;
import form.controller.client.ClientMainController;
import form.controller.client.ClientProductInvoiceController;
import form.controller.client.DetailsInvoiceController;
import form.controller.client.ListOfInvoicesController;
import form.controller.client.NewInvoiceController;
import form.controller.client.ProductDetailController;
import form.controller.client.ReviewController;
import form.controller.client.SignInController;
import util.FormMode;


public class MainCordinatorClient {

    public static MainCordinatorClient instance;
    public ClientMainController cmc;
    private DetailsInvoiceController detailsInvoiceController;
    private NewInvoiceController newInvoiceController;
    private ListOfInvoicesController listOfInvoicesController;
    private ClientLoginController loginController;
    private SignInController signInController;
    private ProductDetailController productDetailController;
    private ReviewController reviewController;
    private ClientProductInvoiceController clientProductInvoiceController;

    private MainCordinatorClient() {
        cmc = new ClientMainController(new FrmMainClient());
}

    public static MainCordinatorClient getInstance() {
        if (instance == null) {
            instance = new MainCordinatorClient();
        }
        return instance;
    }

    public void openLogIn() {
        FrmClientLogin fcl = new FrmClientLogin();
        this.loginController = new ClientLoginController(fcl);
        loginController.openForm();
    }

    public void openSignIn() throws Exception {
        FrmSignIn frmSignIn = new FrmSignIn();
        this.signInController = new SignInController(frmSignIn);
        signInController.openForm();
    }

    public void openFormNarudzba() throws Exception {
        FrmNewInvoice fni = new FrmNewInvoice(cmc.getFmn(), true);
        this.newInvoiceController = new NewInvoiceController(fni);
        newInvoiceController.openForm();
    }

    public void openFormListaNarudzbi() throws Exception {
        FrmListOfInvoices floi = new FrmListOfInvoices(cmc.getFmn(), true);
        this.listOfInvoicesController = new ListOfInvoicesController(floi);
        listOfInvoicesController.openForm();

    }

    public void openFormDetailsProizvod(Product p) throws Exception {
        FrmDetailProduct fdp = new FrmDetailProduct(cmc.getFmn(), true);
        this.productDetailController = new ProductDetailController(fdp, p);
        productDetailController.openForm();
    }

    public void openFormNarudzbaDetails(InvoiceUser iu) throws Exception {
        FrmDetailsOfInvoice fdoi = new FrmDetailsOfInvoice(cmc.getFmn(), true);
        this.detailsInvoiceController = new DetailsInvoiceController(fdoi);
        detailsInvoiceController.openForm(iu);
    }

    public void openMainForm() throws Exception {
        cmc.openForm();
    }

    public void openFormReview(Product p) throws Exception {
        FrmReview fr = new FrmReview(cmc.getFmn(), true);
        this.reviewController = new ReviewController(fr);
        reviewController.openForm(FormMode.FORM_ADD, p);
    }

    public DetailsInvoiceController getDetailsInvoiceController() {
        return detailsInvoiceController;
    }

    public ListOfInvoicesController getListOfInvoicesController() {
        return listOfInvoicesController;
    }

    public NewInvoiceController getNewInvoiceController() {
        return newInvoiceController;
    }

    public SignInController getSignInController() {
        return signInController;
    }

    public ClientLoginController getLoginController() {
        return loginController;
    }

    public ClientMainController getMainController() {
        return cmc;
    }

    public ProductDetailController getProductDetailController() {
        return productDetailController;
    }

    public ReviewController getReviewController() {
        return reviewController;
    }

    public void openFormListOfProducts() throws Exception {
        FrmListOfProductsInvoice flopi = new FrmListOfProductsInvoice(cmc.getFmn(),true);
        this.clientProductInvoiceController = new ClientProductInvoiceController(flopi);
        clientProductInvoiceController.openForm();
    }

    public ClientProductInvoiceController getClientProductInvoiceController() {
        return clientProductInvoiceController;
    }

}

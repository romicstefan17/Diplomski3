/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cordinator;

import domain.InvoiceUser;
import domain.Manufacturer;
import domain.Product;
import domain.Supplier;
import form.admin.FrmInvoiceDetails;
import form.admin.FrmListOfInvoiceA;
import form.admin.FrmListOfManufacturers;
import form.admin.FrmListOfProducts;
import form.admin.FrmListOfSuppliers;
import form.admin.FrmMain;
import form.admin.FrmNewManufacturer;
import form.admin.FrmNewProduct;
import form.admin.FrmNewSupplier;
import form.client.FrmClientLogin;
import form.controller.admin.InvoiceDetailsController;
import form.controller.admin.ListOfInvoiceController;
import form.controller.admin.ListOfManufacturerController;
import form.controller.admin.ListOfProductsController;
import form.controller.admin.ListOfSuppliersController;
import form.controller.admin.MainController;
import form.controller.admin.NewManufacturerController;
import form.controller.admin.NewSupplierController;
import form.controller.admin.ProductNewController;
import form.controller.client.ClientLoginController;
import util.FormMode;

public class MainCordinatorAdmin {

    private static MainCordinatorAdmin instance;
    private final MainController mainController;
    private ListOfProductsController productListController;
    private ListOfInvoiceController invoiceController;
    private ProductNewController productAddController;
    private ProductNewController productDetailController;
    private NewSupplierController supController;
    private NewManufacturerController manuAddController;
    private ListOfSuppliersController listOfSupplierController;
    private ListOfManufacturerController listOfManufacturerController;
    private NewSupplierController supAddController;
    private NewManufacturerController manucoController;
    private InvoiceDetailsController detailsController;
    private ClientLoginController loginController;

    private MainCordinatorAdmin() {
        this.mainController = new MainController(new FrmMain());
    }

    public static MainCordinatorAdmin getInstance() {
        if (instance == null) {
            instance = new MainCordinatorAdmin();
        }
        return instance;
    }

    public void openLogIn() {
        FrmClientLogin fcl = new FrmClientLogin();
        this.loginController = new ClientLoginController(fcl);
        loginController.openForm();
    }

    public void openFormProductAdd() throws Exception {
        FrmNewProduct frmNewProduct = new FrmNewProduct(mainController.getFrmMain(), true);
        this.productAddController = new ProductNewController(frmNewProduct, FormMode.FORM_ADD, new Product());
        productAddController.openForm();

    }

    public void openFormProductDetail(Product p) throws Exception {
        FrmNewProduct fnp = new FrmNewProduct(mainController.getFrmMain(), true);
        this.productDetailController = new ProductNewController(fnp, FormMode.FORM_DETAIL, p);
        productDetailController.openForm();

    }

    public void openListOfProducts() throws Exception {
        FrmListOfProducts flop = new FrmListOfProducts(mainController.getFrmMain(), true);
        this.productListController = new ListOfProductsController(flop);
        productListController.openForm();

    }

    public void openFormNoviDobavljac() throws Exception {
        FrmNewSupplier fns = new FrmNewSupplier(mainController.getFrmMain(), true);
        this.supAddController = new NewSupplierController(fns, FormMode.FORM_ADD, new Supplier());
        supAddController.openForm();

    }

    public void openFormNoviProizvodjac() throws Exception {
        FrmNewManufacturer fnm = new FrmNewManufacturer(mainController.getFrmMain(), true);
        this.manuAddController = new NewManufacturerController(fnm, FormMode.FORM_ADD, new Manufacturer());
        manuAddController.openForm();

    }

    public void openListOfSuppliers() throws Exception {
        FrmListOfSuppliers flom = new FrmListOfSuppliers(mainController.getFrmMain(), true);
        this.listOfSupplierController = new ListOfSuppliersController(flom);
        listOfSupplierController.openForm();

    }

    public void openListOfManufacturers() {
        FrmListOfManufacturers flom = new FrmListOfManufacturers(mainController.getFrmMain(), true);
        this.listOfManufacturerController = new ListOfManufacturerController(flom);
        listOfManufacturerController.openForm();
    }

    public void openFormDobavljacDetail(Supplier s) throws Exception {
        FrmNewSupplier fns = new FrmNewSupplier(mainController.getFrmMain(), true);
        this.supController = new NewSupplierController(fns, FormMode.FORM_DETAIL, s);
        supController.openForm();

    }

    public void openFormProizvodjacDetail(Manufacturer m) throws Exception {
        FrmNewManufacturer fnm = new FrmNewManufacturer(mainController.getFrmMain(), true);
        this.manucoController = new NewManufacturerController(fnm, FormMode.FORM_DETAIL, m);
        manucoController.openForm();

    }

    public void openFormNarudzbaDetails(InvoiceUser iu) throws Exception {
        FrmInvoiceDetails fid = new FrmInvoiceDetails(mainController.getFrmMain(), true);
        this.detailsController = new InvoiceDetailsController(fid);
        detailsController.openForm(iu);

    }

    public void openMainForm() throws Exception {
        mainController.openForm();
    }

    public void openFormListaNarudzbi() throws Exception {
        FrmListOfInvoiceA floi = new FrmListOfInvoiceA(mainController.getFrmMain(), true);
        this.invoiceController = new ListOfInvoiceController(floi);
        invoiceController.openForm();

    }

    public ListOfInvoiceController getInvoiceController() {
        return invoiceController;
    }

    public ListOfProductsController getProductListController() {
        return productListController;
    }

    public ProductNewController getProductAddController() {
        return productAddController;
    }

    public InvoiceDetailsController getDetailsController() {
        return detailsController;
    }

    public ListOfManufacturerController getListOfManufacturerController() {
        return listOfManufacturerController;
    }

    public ListOfSuppliersController getListOfSupplierController() {
        return listOfSupplierController;
    }

    public NewManufacturerController getManuAddController() {
        return manuAddController;
    }

    public NewManufacturerController getManucoController() {
        return manucoController;
    }

    public MainController getMainController() {
        return mainController;
    }

    public ProductNewController getProductDetailController() {
        return productDetailController;
    }

    public NewSupplierController getSupAddController() {
        return supAddController;
    }

    public NewSupplierController getSupController() {
        return supController;
    }

    public ClientLoginController getLoginController() {
        return loginController;
    }

}

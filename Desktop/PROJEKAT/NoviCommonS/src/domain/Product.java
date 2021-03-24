/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;



import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SystemX
 */


public class Product implements GenericEntity {

    private Long productID;
    private String model;
    private BigDecimal price = BigDecimal.ZERO;
    private Manufacturer manufacturer;
    private ProductType type;
    private Supplier supplier;
    private Integer amount = 0;
    private ProductPicture picture;
    private List<Review> review;
    private double ocena = 0;

    public Product() {
        this.type = new ProductType();
        this.manufacturer = new Manufacturer();
        this.supplier = new Supplier();
        this.picture = new ProductPicture();
        this.review = new ArrayList<>();

    }

    public Product(Long productID, String model, Manufacturer manufacturer, ProductType type, Supplier supplier, ProductPicture picture) {
        this.productID = productID;
        this.model = model;
        this.manufacturer = manufacturer;
        this.type = type;
        this.supplier = supplier;
        this.picture = picture;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier dobavljacid) {
        this.supplier = dobavljacid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ProductPicture getPicture() {
        return picture;
    }

    public void setPicture(ProductPicture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return model;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public double getOcena() {
        return ocena;
    }

    public void setOcena(double ocena) {
        this.ocena = ocena;
    }

    @Override
    public String getTableName() {
        return "proizvod";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "proizvodid,model,cena,proizvodjacid,vrstaid,dobavljacid,kolicina,slikaid,ocena";
    }

    @Override
    public void getInsertValues(PreparedStatement statement) throws Exception {
       

        statement.setLong(1, productID);
        statement.setString(2, model);
        statement.setBigDecimal(3, price);
        statement.setLong(4, manufacturer.getManufacturerID());
        statement.setLong(5, type.getVrstaID());
        statement.setLong(6, supplier.getSupplierID());
        statement.setInt(7, amount);
        statement.setLong(8, picture.getPictureID());
        statement.setDouble(9, ocena);
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public void setId(Long id) {
        this.productID = id;
    }

    @Override
    public String getColumnNamesForUpdate() {
        return "proizvodid=?,model=?,cena=?,proizvodjacid=?,vrstaid=?,dobavljacid=?,kolicina=?,slikaid=?,ocena=?";
    }

    @Override
    public String getConditionForUpdate() {
        return "proizvodid=" + productID;
    }

    @Override
    public String getConditionForDelete() {
        return "proizvodid=" + productID;
    }

    @Override
    public String getColumnNamesForSelect() {
        return "p.ocena, p.kolicina,p.proizvodid, p.model, p.cena, v.naziv AS vnz, pr.naziv AS prnvz, v.vrstaid, pr.proizvodjacid,d.dobavljacid,d.naziv as dnz, jpg.id,jpg.poster"
                + ",(SELECT AVG(score) FROM review r WHERE r.productid=p.proizvodid) AS scr ";
    }

    @Override
    public String getTableForSelect() {
        return "proizvod p JOIN proizvodjac pr "
                + "ON(p.proizvodjacid=pr.proizvodjacid) JOIN vrsta v ON(p.vrstaid=v.vrstaid)"
                + "join dobavljac d on(p.dobavljacid=d.dobavljacid) JOIN slika jpg on(p.slikaid=jpg.id) ORDER BY p.proizvodid ASC";
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

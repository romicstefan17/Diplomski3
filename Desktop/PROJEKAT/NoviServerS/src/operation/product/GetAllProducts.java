/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.product;

import domain.Manufacturer;
import domain.Product;
import domain.ProductPicture;
import domain.ProductType;
import domain.Supplier;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import operation.AbstractGenericOperation;

/**
 *
 * @author SystemX
 */
public class GetAllProducts extends AbstractGenericOperation {

    List<Product> pro = new ArrayList();

    @Override
    protected void preconditions(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        ResultSet rs = repository.getAll((Product) param);

        while (rs.next()) {
            Product p = new Product();
            p.setProductID(rs.getLong("proizvodid"));
            p.setModel(rs.getString("model"));
            p.setPrice(rs.getBigDecimal("cena"));
            p.setAmount(rs.getInt("kolicina"));
            p.setOcena(rs.getDouble("scr"));

            Manufacturer pr = new Manufacturer();
            pr.setManufacturerID(rs.getLong("proizvodjacid"));
            pr.setName(rs.getString("prnvz"));
            p.setManufacturer(pr);

            ProductType v = new ProductType();
            v.setVrstaID(rs.getLong("vrstaid"));
            v.setNazivVrste(rs.getString("vnz"));
            p.setType(v);

            Supplier d = new Supplier();
            d.setSupplierID(rs.getLong("dobavljacid"));
            d.setName(rs.getString("dnz"));
            p.setSupplier(d);

            ProductPicture pp = new ProductPicture();
            pp.setPictureID(rs.getLong("id"));

            java.sql.Blob blob = rs.getBlob("poster");
            InputStream in = blob.getBinaryStream();
            BufferedImage image = ImageIO.read(in);
            pp.setPosterImage(image);

            p.setPicture(pp);
            pro.add(p);

        }
    }

    public List<Product> getPro() {
        return pro;
    }

}

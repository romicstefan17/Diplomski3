/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.invoiceitem;

import domain.InvoiceItem;
import domain.Product;
import domain.ProductPicture;
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
public class GetAllInvoiceItems extends AbstractGenericOperation {

    List<InvoiceItem> stavke = new ArrayList();

    @Override
    protected void preconditions(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.getAll((InvoiceItem) param);

        while (rs.next()) {
            InvoiceItem sn = new InvoiceItem();
            sn.setId(rs.getLong("stavkaid"));
            sn.setPrice(rs.getBigDecimal("iznos"));
            sn.setAmount(rs.getInt("sk"));
            sn.setOrderNumber(rs.getInt("rednibroj"));

            Product p = new Product();
            p.setProductID(rs.getLong("proizvodid"));

            p.setModel(rs.getString("model"));
            p.setPrice(rs.getBigDecimal("cena"));
            p.setAmount(rs.getInt("pk"));

            Supplier m = new Supplier();
            m.setSupplierID(rs.getLong("dd"));
            m.setName(rs.getString("naziv"));
            p.setSupplier(m);
            ProductPicture pp = new ProductPicture();
            pp.setPictureID(rs.getLong("id"));

            java.sql.Blob blob = rs.getBlob("poster");
            InputStream in = blob.getBinaryStream();
            BufferedImage image = ImageIO.read(in);
            pp.setPosterImage(image);
            p.setPicture(pp);

            sn.setProduct(p);

            stavke.add(sn);
        }
    }

    public List<InvoiceItem> getStavke() {
        return stavke;
    }

}

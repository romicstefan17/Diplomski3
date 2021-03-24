/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.review;

import domain.Product;
import domain.ProductPicture;
import domain.Review;
import domain.User;
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
public class GetReview extends AbstractGenericOperation {

    List<Review> reviews = new ArrayList<>();

    @Override
    protected void preconditions(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.get((Review) param);
        while (rs.next()) {
            Review review = new Review();
            Product p = new Product();
            p.setProductID(rs.getLong("proizvodid"));
            ProductPicture pp = new ProductPicture();
            pp.setPictureID(rs.getLong("id"));

            java.sql.Blob blob = rs.getBlob("poster");
            InputStream in = blob.getBinaryStream();
            BufferedImage image = ImageIO.read(in);
            pp.setPosterImage(image);

            p.setPicture(pp);
            User u = new User();
            u.setUserID(rs.getLong("zaposleniid"));

            review.setReviewDate(rs.getDate("date"));
            review.setReviewID(rs.getLong("id"));
            review.setReviewScore(rs.getInt("score"));
            review.setReviewText(rs.getString("text"));
            review.setProduct(p);
            review.setUser(u);
            reviews.add(review);

        }

    }

    public List<Review> getRevies() {
        return reviews;
    }

}

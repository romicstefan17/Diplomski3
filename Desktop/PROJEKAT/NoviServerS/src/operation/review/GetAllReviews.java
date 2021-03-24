/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.review;


import domain.Product;
import domain.Review;
import domain.User;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;


public class GetAllReviews extends AbstractGenericOperation{
    private final List<Review> reviews = new ArrayList<>();
    
    @Override
    protected void preconditions(Object param) throws Exception {

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.getAll((Review)param);
        
         while (rs.next()) {
                Review review = new Review();
                Product p = new Product();
                p.setProductID(rs.getLong("proizvodid"));
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

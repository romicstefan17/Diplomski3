/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.PreparedStatement;
import java.util.Date;


public class Review implements GenericEntity {

    private Long reviewID;
    private String reviewText;
    private int reviewScore;
    private Date reviewDate;
    private Product product;
    private User user;

    public Review() {
        product = new Product();
        user = new User();
    }

    public Review(Long reviewID, String reviewText, int reviewScore, Product product, User user) {
        this.reviewID = reviewID;
        this.reviewText = reviewText;
        this.reviewScore = reviewScore;
        this.product = product;
        this.user = user;
        this.reviewDate = new Date();
    }

    public Long getReviewID() {
        return reviewID;
    }

    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Review{" + "reviewID=" + reviewID + ", reviewText=" + reviewText + ", reviewScore=" + reviewScore + ", reviewDate=" + reviewDate + ", product=" + product + ", user=" + user + '}';
    }

    @Override
    public String getTableName() {
        return "review";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "id, text, score, date, productid, userid";
    }

    @Override
    public void getInsertValues(PreparedStatement statement) throws Exception {
        statement.setLong(1, reviewID);
        statement.setString(2, reviewText);
        statement.setInt(3, reviewScore);
        statement.setDate(4, new java.sql.Date(reviewDate.getTime()));
        statement.setLong(5, product.getProductID());
        statement.setLong(6, user.getUserID());
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public void setId(Long id) {
        this.reviewID = id;
    }

    @Override
    public String getColumnNamesForUpdate() {
        return "id=?, text=?, score=?, date=?, productid=?, userid=?";
    }

    @Override
    public String getConditionForUpdate() {
        return "userid=" + user.getUserID()
                + " AND productid=" + product.getProductID();
    }

    @Override
    public String getConditionForDelete() {
        return "";
    }

    @Override
    public String getColumnNamesForSelect() {
        return "r.*,p.proizvodid,k.zaposleniid, jpg.id, jpg.poster";
    }

    @Override
    public String getTableForSelect() {
        return "review r join proizvod p on(r.productid=p.proizvodid)"
                    + "join korisnici k on(r.userid=k.zaposleniid) JOIN slika jpg on(p.slikaid=jpg.id)";
    }

    @Override
    public String getConditionForSelect() {
        return "";
    }

    @Override
    public String getConditionForSelectSpecific() {
        return "userid=" + user.getUserID()
                + " AND productid=" + product.getProductID();
    }

}

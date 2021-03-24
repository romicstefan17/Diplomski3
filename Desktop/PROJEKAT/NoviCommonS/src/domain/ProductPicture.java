/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import javax.imageio.ImageIO;


public class ProductPicture implements GenericEntity {

    private Long pictureID;
    private transient BufferedImage posterImage;

    public ProductPicture() {
    }

    public ProductPicture(Long pictureID, BufferedImage posterImage) {
        this.pictureID = pictureID;
        this.posterImage = posterImage;
    }

    public Long getPictureID() {
        return pictureID;
    }

    public void setPictureID(Long pictureID) {
        this.pictureID = pictureID;
    }

    public BufferedImage getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(BufferedImage posterImage) {
        this.posterImage = posterImage;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ImageIO.write(this.posterImage, "png", out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.posterImage = ImageIO.read(in);
    }

    @Override
    public String getTableName() {
        return "slika";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "poster";
    }

    @Override
    public void getInsertValues(PreparedStatement statement) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(posterImage, "jpg", baos);
            InputStream imageStream = new ByteArrayInputStream(baos.toByteArray());
            
            statement.setBlob(1, imageStream);
        
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public void setId(Long id) {
        this.pictureID = id;
    }

    @Override
    public String getColumnNamesForUpdate() {
        return "";
    }

    @Override
    public String getConditionForUpdate() {
        return "";
    }

    @Override
    public String getConditionForDelete() {
        return "";
    }

    @Override
    public String getColumnNamesForSelect() {
        return "id,poster";
    }

    @Override
    public String getTableForSelect() {
        return "slika";
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

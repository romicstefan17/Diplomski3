/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.client;

import communication.Communication;
import communication.Operation;
import communication.Request;
import domain.Product;
import domain.Review;
import domain.User;
import form.client.FrmReview;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.FormMode;


public class ReviewController {

    private final FrmReview frmReview;
    private Review r;
    private final boolean isFormModeAdd = true;
    private Product p;
    private boolean ocenjen = false;

    public ReviewController(FrmReview frmReview) {
        this.frmReview = frmReview;
        addActionListener();
        addKeyListener();
        addWindowListener();
    }

    public void openForm(FormMode formMode, Product p) {
        prepareView(p);
        frmReview.setTitle("Oceni proizvod");
        this.p = p;
        frmReview.setLocationRelativeTo(null);
        frmReview.setVisible(true);

    }
    
    private void addWindowListener() {
        frmReview.addwindowListenerOnOpen(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                try {
                    User user = Communication.getInstance().getUser();
                    Review review = new Review();
                    review.setUser(user);
                    review.setProduct(p);
                    
                    Request request = new Request();
                    request.setArgument(review);
                    request.setOperation(Operation.FILL_REVIEW);
                    Communication.getInstance().sendUserRequest(request);
                    
                } catch (Exception ex) {
                    Logger.getLogger(ReviewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }

    private void addKeyListener() {
        frmReview.txtReviewTextKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int reviewLength = frmReview.getTxtReviewText().getText().length();
                int charsRemaining = 300 - reviewLength;
                frmReview.getLblCharactersRemaining().setText("Preostali broj karaktera: " + charsRemaining);

                if (charsRemaining < 0) {
                    if (isFormModeAdd) {
                        frmReview.getBtnSave().setEnabled(false);
                    }
                } else if (isFormModeAdd) {
                    frmReview.getBtnSave().setEnabled(true);
                }
            }
        });
    }

    ;
    
    private void addActionListener() {
        frmReview.btnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmReview.dispose();
            }
        });
        frmReview.btnSaveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    validateForm();

                    Review review = new Review();
                    review.setReviewID(0l);
                    review.setReviewText(frmReview.getTxtReviewText().getText().trim());
                    review.setReviewScore(frmReview.getStarRater().getSelection());
                    review.setReviewDate(new Date());
                    review.setUser(Communication.getInstance().getUser());
                    review.setProduct(p);
                    ocenjen = true;
                    
                    if (r == null) {
                        Request request = new Request(Operation.INSERT_REVIEW, review);
                        Communication.getInstance().sendUserRequest(request);
                        JOptionPane.showMessageDialog(frmReview, p.getModel() + " Uspesno ocenjeno!",
                                "Cestitamo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        Request request = new Request(Operation.UPDATE_REVIEW, review);
                        Communication.getInstance().sendUserRequest(request);
                        JOptionPane.showMessageDialog(frmReview, p.getModel() + " Uspesna promena ocene!",
                                "Cestitamo", JOptionPane.INFORMATION_MESSAGE);
                    }
                  
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmReview, "Greska prilikom ocenjivanja!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    private void prepareView(Product p) {
       
        setUpComponents(p);
    }

    private void setUpComponents(Product p) {
      
            frmReview.getStarRater().setSelection(0);
            frmReview.getTxtReviewText().setText("");
            frmReview.getLblCharactersRemaining().setText("Preostali broj karaktera: 300");
            frmReview.getBtnSave().setEnabled(true);
            frmReview.getBtnCancel().setEnabled(true);
       

    }
    
    public void fillComponents(Review r) {
        frmReview.getStarRater().setSelection(r.getReviewScore());
        frmReview.getTxtReviewText().setText(r.getReviewText());
        frmReview.getLblCharactersRemaining().setText(300 - r.getReviewText().length() + "");
        frmReview.getBtnSave().setEnabled(true);
        frmReview.getBtnCancel().setEnabled(true);
        this.r = r;
    }

    private void validateForm() throws Exception {
        String reviewText = frmReview.getTxtReviewText().getText();

        if (frmReview.getStarRater().getSelection() == 0) {
            throw new Exception("Ocena ne moze da bude 0");
        }
        if (reviewText.isEmpty()) {
            throw new Exception("Polje ne sme biti prazno");
        }
        if (reviewText.length() > 300) {
            throw new Exception("Opis ne sme imati vise od 300 karaktera");
        }

    }

    public void setR(Review r) {
        this.r = r;
    }
    
    
}

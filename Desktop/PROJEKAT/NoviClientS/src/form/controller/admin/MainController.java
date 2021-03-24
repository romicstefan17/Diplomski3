
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller.admin;

import communication.Communication;
import communication.Operation;
import communication.Request;
import cordinator.MainCordinatorAdmin;
import cordinator.MainCordinatorClient;
import domain.InvoiceItem;
import form.admin.FrmMain;
import form.component.TableInvoiceDetailsModelStatistics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author SystemX
 */
public class MainController {

    FrmMain frmMain;
    List<InvoiceItem> pomocna = new ArrayList<>();
    DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
    String nalepi = "";
    private boolean k = true;

    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;
        addActionListener();
        addWindowListener();

    }

    public void openForm() throws Exception {

        frmMain.setTitle("Admin: " + Communication.getInstance().getUser());

        frmMain.setLocationRelativeTo(null);
        frmMain.setVisible(true);

    }

    private void addActionListener() {

        frmMain.btnJMenuNewProduct(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCordinatorAdmin.getInstance().openFormProductAdd();
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        frmMain.btnJMenuShowProducts(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCordinatorAdmin.getInstance().openListOfProducts();
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        frmMain.btnListaNarudzbi(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCordinatorAdmin.getInstance().openFormListaNarudzbi();
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        frmMain.btnNoviDobavljac(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCordinatorAdmin.getInstance().openFormNoviDobavljac();
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        frmMain.btnNoviProizvodjac(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCordinatorAdmin.getInstance().openFormNoviProizvodjac();
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        frmMain.btnListaDobavljaca(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCordinatorAdmin.getInstance().openListOfSuppliers();
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        frmMain.btnListaProizvodjaca(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinatorAdmin.getInstance().openListOfManufacturers();
            }
        });
        frmMain.getBtnLogOut().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int k = JOptionPane.showConfirmDialog(frmMain, "Da li ste sigurni?", "LogOut", JOptionPane.YES_OPTION);
                if (k == JOptionPane.YES_OPTION) {

                    frmMain.dispose();
                    JOptionPane.showMessageDialog(frmMain, "Uspesno ste izlogovali");
                    MainCordinatorClient.getInstance().openLogIn();

                    try {
                        Request request = new Request(Operation.SIGN_OUT, Communication.getInstance().getUser());
                        Communication.getInstance().sendUserRequest(request);
                        System.out.println("" + request.getArgument());
                    } catch (Exception ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        frmMain.btnBarChart(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (k) {
                        List<InvoiceItem> invoices = Communication.getInstance().getItems();
                        for (InvoiceItem invoice : invoices) {
                            if (!sadrzi(pomocna, invoice)) {
                                pomocna.add(invoice);
                            } else {
                                update(pomocna, invoice);
                            }
                        }
                        for (InvoiceItem invoice : pomocna) {
                            dataSet.setValue(invoice.getAmount(), invoice.getAmount() + "", invoice.getProduct().getModel());
                        }
                        k = false;
                        JFreeChart chart = ChartFactory.createBarChart("Kolicina prodatih proizvoda", "Naziv proizvoda", "Kolicina", dataSet, PlotOrientation.VERTICAL, false, true, false);

                        CategoryPlot p = chart.getCategoryPlot();
                        p.setRangeGridlinePaint(Color.black);
                        ChartFrame frame = new ChartFrame("Bar chart prodaje proizvoda", chart);
                        frame.setSize(1150, 650);
                        frame.setLocationRelativeTo(frmMain);
                        frame.setVisible(true);

                    } else {
                        System.out.println("" + k);
                        JFreeChart chart = ChartFactory.createBarChart("Kolicina prodatih proizvoda", "Naziv proizvoda", "Kolicina", dataSet, PlotOrientation.VERTICAL, false, true, false);

                        CategoryPlot p = chart.getCategoryPlot();
                        p.setRangeGridlinePaint(Color.black);
                        ChartFrame frame = new ChartFrame("Bar chart prodaje proizvoda", chart);
                        frame.setSize(1150, 650);
                        frame.setLocationRelativeTo(frmMain);
                        frame.setVisible(true);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void update(List<InvoiceItem> pomocna, InvoiceItem item) {
                for (InvoiceItem invoiceItem : pomocna) {
                    if (Objects.equals(invoiceItem.getProduct().getModel(), item.getProduct().getModel())) {
                        invoiceItem.setAmount(invoiceItem.getAmount() + item.getAmount());
                    }
                }
            }

            private boolean sadrzi(List<InvoiceItem> pomocna, InvoiceItem item) {
                for (InvoiceItem invoiceItem : pomocna) {
                    if (Objects.equals(invoiceItem.getProduct().getModel(), item.getProduct().getModel())) {
                        return true;
                    }
                }
                return false;
            }

        }
        );

    }

    private void addWindowListener() {

        frmMain.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    frmMain.dispose();
                    JOptionPane.showMessageDialog(frmMain, "Dovidjenja");
                    try {
                        Request request = new Request(Operation.SIGN_OUT, Communication.getInstance().getUser());
                        Communication.getInstance().sendUserRequest(request);
                        System.out.println("" + request.getArgument());
                    } catch (Exception ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });

        frmMain.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    Request request = new Request(Operation.GET_ALL_ITEMS, null);
                    Communication.getInstance().sendUserRequest(request);
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        );

    }

    public FrmMain getFrmMain() {
        return frmMain;
    }

    public void fillTblInvoiceItem(List<InvoiceItem> items) throws Exception {
        TableInvoiceDetailsModelStatistics model = new TableInvoiceDetailsModelStatistics(items);
        frmMain.getTblInvoiceItem().setModel(model);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Image;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import Controller.Test2;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JLabel;

/**
 *
 * @author Sony
 */
public class generate_shape extends javax.swing.JFrame {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    /**
     * Creates new form generate_shape
     */
    public generate_shape() {
        initComponents();
        int index = shapes_list.getSelectedIndex();
        PrintShape_btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {
                    if (index != -1) {

//***************Retrive Part*************************
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/tactrace", USERNAME, PASSWORD);
                        Statement state = con.createStatement();
                        ResultSet rs = state.executeQuery("select ShapeBarCode from shape where ShapeID='" + index + "'");
                        if (rs.next()) {
                            byte[] img = rs.getBytes("ShapeBarCode");
                            ImageIcon icon = new ImageIcon(img);
                            Image image = icon.getImage();
                            Image myimg = image.getScaledInstance(jLabel2.getWidth(), jLabel2.getHeight(), Image.SCALE_SMOOTH);
                            ImageIcon newImg = new ImageIcon(myimg);
                            jLabel2.setIcon(newImg);//to see the retrived image
                            //jLabel2.hide();
//************************PRINT PART*******************
                            PrinterJob job = PrinterJob.getPrinterJob();
                            job.setJobName("Print Barcode");
                            job.setPrintable(new Printable() {
                                public int print(Graphics pg, PageFormat pf, int pagenum) {
                                    if (pagenum > 0) {
                                        return Printable.NO_SUCH_PAGE;
                                    }
                                    Graphics2D g = (Graphics2D) pg;
                                    g.translate(pf.getImageableX(), pf.getImageableY());
                                    g.scale(1, 1);
                                    jLabel2.paint(g);
                                    return Printable.PAGE_EXISTS;
                                }//print
                            });//setPrintable
                            boolean ok = job.printDialog();
                            if (ok) {
                                try {
                                    job.print();
                                    JOptionPane.showMessageDialog(null, "Printing Barcode...\nDon't forget to stick it at the bottom of the shape ", "Print barcode", JOptionPane.WARNING_MESSAGE);
                                } catch (PrinterException px) {

                                }
                            }

                        }//if next
                    }//if index=!-1
                    else {
                        JOptionPane.showMessageDialog(null, "Please Select Shape First !!..", "Print Shape Barcode", JOptionPane.WARNING_MESSAGE);
                    }
                }//try
                catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });//action listener button print barcode

        //if the user click print temp
        PrintTemp_btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    if (index != -1) {

//***************Retrive Part*************************
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/tactrace", USERNAME, PASSWORD);
                        Statement state = con.createStatement();
                        ResultSet rs = state.executeQuery("select ShapeTemplate from shape where ShapeID='" + index + "'");
                        if (rs.next()) {
                            byte[] img = rs.getBytes("ShapeTemplate");
                            ImageIcon icon = new ImageIcon(img);
                            Image image = icon.getImage();
                            Image myimg = image.getScaledInstance(jLabel2.getWidth(), jLabel2.getHeight(), Image.SCALE_SMOOTH);
                            ImageIcon newImg = new ImageIcon(myimg);
                            jLabel2.setIcon(newImg);//to see the retrived image
//************************PRINT PART*******************
                            PrinterJob job = PrinterJob.getPrinterJob();
                            job.setJobName("Print Shape Template");
                            job.setPrintable(new Printable() {
                                public int print(Graphics pg, PageFormat pf, int pagenum) {
                                    if (pagenum > 0) {
                                        return Printable.NO_SUCH_PAGE;
                                    }
                                    Graphics2D g = (Graphics2D) pg;
                                    g.translate(pf.getImageableX(), pf.getImageableY());
                                    g.scale(1, 1);
                                    jLabel2.paint(g);
                                    return Printable.PAGE_EXISTS;
                                }//print
                            });//setPrintable
                            boolean ok = job.printDialog();
                            if (ok) {
                                try {
                                    job.print();
                                    JOptionPane.showMessageDialog(null, "Printing template...\nDon’t forget to cut it out of textured paper.", "Print barcode", JOptionPane.WARNING_MESSAGE);

                                } catch (PrinterException px) {

                                }
                            }

                        }//if next
                    }//if index=!-1
                    else {
                        JOptionPane.showMessageDialog(null, "Please Select Shape First !!..", "Print Shape Templte", JOptionPane.WARNING_MESSAGE);
                    }
                }//try
                catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });//action listener button print Temp

    }//generate_shape()

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        shapes_list = new javax.swing.JList<>();
        PrintTemp_btn = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        PrintShape_btn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Generate Shape", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        jPanel1.setToolTipText("");
        jPanel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        shapes_list.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Triangle", "Square", "Circle", "Rectangle" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        shapes_list.setSelectedIndex(0);
        jScrollPane1.setViewportView(shapes_list);

        PrintTemp_btn.setBackground(new java.awt.Color(255, 255, 255));
        PrintTemp_btn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        PrintTemp_btn.setText("Print template");
        PrintTemp_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PrintTemp_btnMouseClicked(evt);
            }
        });
        PrintTemp_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintTemp_btnActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setText("Back to Control panel");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        PrintShape_btn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        PrintShape_btn.setText("Print Barcode");
        PrintShape_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintShape_btnActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Select a shape:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PrintShape_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(PrintTemp_btn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(89, 89, 89))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PrintTemp_btn)
                    .addComponent(PrintShape_btn))
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(27, 27, 27)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PrintTemp_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrintTemp_btnMouseClicked
        // TODO add your handling code here:
        //JOptionPane.showMessageDialog(null,"Printing template...\nDon’t forget to cut it out of textured paper.","Print barcode", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_PrintTemp_btnMouseClicked

    private void PrintTemp_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintTemp_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrintTemp_btnActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        //new NewJFrame5().setVisible(true);
    }//GEN-LAST:event_jButton3MouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void PrintShape_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintShape_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrintShape_btnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(generate_shape.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(generate_shape.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(generate_shape.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(generate_shape.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new generate_shape().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PrintShape_btn;
    private javax.swing.JButton PrintTemp_btn;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> shapes_list;
    // End of variables declaration//GEN-END:variables
}

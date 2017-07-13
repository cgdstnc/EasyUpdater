/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import System.common.Updater;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class VersionDialog extends javax.swing.JFrame {

    private boolean dontExit = false;
    private String project;
    private Integer portBindingToForceKillProcess;

    public VersionDialog(String project, Integer portBindingToForceKillProcess) {
        setUndecorated(true);
        initComponents();
        decorate();
        jlProje.setText(project.toUpperCase());
        this.project = project;
        this.portBindingToForceKillProcess = portBindingToForceKillProcess;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbAccept = new javax.swing.JButton();
        jbCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jlCountdown = new javax.swing.JLabel();
        jlProje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(51, 51, 51));

        jbAccept.setBackground(new java.awt.Color(51, 51, 51));
        jbAccept.setForeground(new java.awt.Color(153, 153, 153));
        jbAccept.setText("Yükle");
        jbAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAcceptActionPerformed(evt);
            }
        });

        jbCancel.setBackground(new java.awt.Color(51, 51, 51));
        jbCancel.setForeground(new java.awt.Color(153, 153, 153));
        jbCancel.setText("Vazgeç");
        jbCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Kullanılabilir yeni bir sürüm bulunmakta...");

        jlCountdown.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlCountdown.setForeground(new java.awt.Color(204, 204, 204));
        jlCountdown.setText("(5)");

        jlProje.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlProje.setForeground(new java.awt.Color(0, 255, 102));
        jlProje.setText("metaJviewer");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbAccept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbCancel)
                    .addComponent(jlCountdown))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jlProje))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jlProje)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jlCountdown))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAccept)
                    .addComponent(jbCancel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 4; i > 0; i--) {
                        jlCountdown.setText("(" + i + ")");
                        Thread.sleep(1000);
                    }

                    if (!dontExit) {
                        System.exit(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }).start();
    }

    private void jbCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbCancelActionPerformed

    private void jbAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAcceptActionPerformed
        update();
    }//GEN-LAST:event_jbAcceptActionPerformed

    public void update() {
        dontExit = true;
        try {
            Updater.update(project, portBindingToForceKillProcess);
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage(), "Hata", JOptionPane.PLAIN_MESSAGE);
            System.exit(1);
        }
    }

    private void decorate() {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            //height of the task bar
            Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
            int taskBarSize = scnMax.bottom;
            //available size of the screen 
            setLocation(screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());

            BufferedImage bi = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.getGraphics();
            g.setColor(Color.BLUE);
            g.fillArc(0, 0, 30, 30, 0, 180);
            g.setColor(Color.WHITE);
            g.fillArc(7, 5, 6, 6, 0, 360);
            g.fillArc(23, 5, 6, 6, 0, 360);
            g.setColor(Color.BLUE);
            g.fillRect(0, 15, 30, 15);
            g.setColor(Color.MAGENTA);
            g.fillArc(0, 23, 8, 13, 0, 180);
            g.fillArc(10, 23, 10, 13, 0, 180);
            g.fillArc(22, 23, 8, 13, 0, 180);
            setIconImage(bi);
        } catch (Exception e) {
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbAccept;
    private javax.swing.JButton jbCancel;
    private javax.swing.JLabel jlCountdown;
    private javax.swing.JLabel jlProje;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.forms;

import java.awt.event.ActionListener;
import javax.swing.JLabel;

/**
 *
 * @author rastko
 */
public class FrmLawyer extends javax.swing.JFrame {

    /**
     * Creates new form FrmLawyer
     */
    public FrmLawyer() {
        setExtendedState(MAXIMIZED_BOTH);
        
    //    setLocationRelativeTo(null);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWelcome = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuClient = new javax.swing.JMenu();
        jMenuItemAddClient = new javax.swing.JMenuItem();
        jMenuItemShowClients = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemAddObligation = new javax.swing.JMenuItem();
        jMenuItemShowObligation = new javax.swing.JMenuItem();
        jMenuInvoice = new javax.swing.JMenu();
        jMenuItemAddInvoice = new javax.swing.JMenuItem();
        jMenuItemViewInvoices = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Law Office Manager - Main menu");

        lblWelcome.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lblWelcome.setText("Welcome");

        jMenuClient.setText("Client");

        jMenuItemAddClient.setText("Add");
        jMenuClient.add(jMenuItemAddClient);

        jMenuItemShowClients.setText("Show my clients");
        jMenuClient.add(jMenuItemShowClients);

        jMenuBar1.add(jMenuClient);

        jMenu2.setText("Obligation");

        jMenuItemAddObligation.setText("Add");
        jMenu2.add(jMenuItemAddObligation);

        jMenuItemShowObligation.setText("Show office obligations");
        jMenuItemShowObligation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemShowObligationActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemShowObligation);

        jMenuBar1.add(jMenu2);

        jMenuInvoice.setText("Invoice");

        jMenuItemAddInvoice.setText("Add");
        jMenuInvoice.add(jMenuItemAddInvoice);

        jMenuItemViewInvoices.setText("View invoices");
        jMenuInvoice.add(jMenuItemViewInvoices);

        jMenuBar1.add(jMenuInvoice);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblWelcome)
                .addContainerGap(285, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblWelcome)
                .addContainerGap(201, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemShowObligationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemShowObligationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemShowObligationActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuClient;
    private javax.swing.JMenu jMenuInvoice;
    private javax.swing.JMenuItem jMenuItemAddClient;
    private javax.swing.JMenuItem jMenuItemAddInvoice;
    private javax.swing.JMenuItem jMenuItemAddObligation;
    private javax.swing.JMenuItem jMenuItemShowClients;
    private javax.swing.JMenuItem jMenuItemShowObligation;
    private javax.swing.JMenuItem jMenuItemViewInvoices;
    private javax.swing.JLabel lblWelcome;
    // End of variables declaration//GEN-END:variables

    public JLabel getLblWelcome() {
        return lblWelcome;
    }

    public void jmiClientNewAddActionListener(ActionListener actionListener){
        jMenuItemAddClient.addActionListener(actionListener);
    }
    
    public void jmiClientViewAddActionListener(ActionListener actionListener){
        jMenuItemShowClients.addActionListener(actionListener);
    }
    
    public void jmiAddInvoiceAddActionListener(ActionListener actionListener){
        jMenuItemAddInvoice.addActionListener(actionListener);
    }
    
    public void jmiViewInvoiceAddActionListener(ActionListener actionListener){
        jMenuItemViewInvoices.addActionListener(actionListener);
    }
   
    public void jmiAddObligationAddActionListener(ActionListener actionListener){
        jMenuItemAddObligation.addActionListener(actionListener);
    }
    
    public void jmiShowObligationsAddActionListener(ActionListener actionListener){
        jMenuItemShowObligation.addActionListener(actionListener);
    }
}
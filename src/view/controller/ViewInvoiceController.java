/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Invoice;
import domain.Lawyer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.constants.Constants;
import view.coordinator.MainCoordinator;
import view.forms.FrmViewInvoices;
import view.tablemodels.ViewInvoiceTableModel;

/**
 *
 * @author rastko
 */
public class ViewInvoiceController {

    private final FrmViewInvoices frmViewInvoices;

    public ViewInvoiceController(FrmViewInvoices frmViewInvoices) {
        this.frmViewInvoices = frmViewInvoices;
    }

    public void openForm() {
        frmViewInvoices.setLocationRelativeTo(frmViewInvoices.getParent());
        prepareView();
        addActionListeners();
        frmViewInvoices.setVisible(true);
    }

    private void addActionListeners() {
        frmViewInvoices.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                prepareView();
            }

        });
        frmViewInvoices.btnUpdateAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewInvoiceTableModel model = (ViewInvoiceTableModel) frmViewInvoices.getTblInvoices().getModel();
                int row = frmViewInvoices.getTblInvoices().getSelectedRow();
                Invoice selectedInvoice = model.getSelectedInvoice(row);
                MainCoordinator.getInstance().addParam(Constants.SELECTED_INVOICE, selectedInvoice);
                MainCoordinator.getInstance().openUpdateInvoiceForm();
            }
        });
    }

    private void prepareView() {
        try {
            Lawyer lawyer = (Lawyer) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
            List<Invoice> invoices = Communication.getInstance().getInvoicesOfLawyer(lawyer);
            ViewInvoiceTableModel model = new ViewInvoiceTableModel(invoices);
            frmViewInvoices.getTblInvoices().setModel(model);
        } catch (Exception ex) {
            Logger.getLogger(ViewInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

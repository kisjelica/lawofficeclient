/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import domain.Lawyer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.constants.Constants;
import view.coordinator.MainCoordinator;
import view.forms.FrmLawyer;

/**
 *
 * @author rastko
 */
public class LawyerController {
    private final FrmLawyer frmLawyer;

    public LawyerController(FrmLawyer frmLawyer) {
        this.frmLawyer = frmLawyer;
        addActionListeners();
    }

    public void openForm() {
        Lawyer lawyer = (Lawyer) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        frmLawyer.getLblWelcome().setText("Current user: " + (lawyer.toString()));
        frmLawyer.setVisible(true);
    }

    private void addActionListeners() {
        frmLawyer.jmiClientNewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAddClientForm();
            }
        });
        
        frmLawyer.jmiClientViewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openViewClientsForm();
            }
        });
        
        frmLawyer.jmiAddInvoiceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAddInvoiceForm();
            }
        });
        
        frmLawyer.jmiViewInvoiceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openViewInvoiceForm();
            }
           
        });
        frmLawyer.jmiAddObligationAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openObligationForm();
            }
        });
        
        frmLawyer.jmiShowObligationsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openViewObligationsForm();
            }
        });
    }

    public FrmLawyer getFrmLawyer() {
        return frmLawyer;
    }
    
    
            
}

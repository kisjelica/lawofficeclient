/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import view.coordinator.MainCoordinator;
import domain.Administrator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.constants.Constants;
import view.forms.FrmAdministrator;

/**
 *
 * @author rastko
 */
public class AdministratorController {
    private final FrmAdministrator frmAdministrator;

    public AdministratorController(FrmAdministrator frmAdministrator) {
        this.frmAdministrator = frmAdministrator;
        addActionListeners();
    }
    
    public void openForm(){
        Administrator administrator = (Administrator) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        frmAdministrator.getLblWelcome().setText("Current user: " + administrator.getUsername());
        frmAdministrator.setVisible(true);
    }

    private void addActionListeners() {
        frmAdministrator.jmiLawyerNewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAddLawyerForm();
            }
        });
        
        frmAdministrator.jmiLawyerViewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openViewLawyersForm();
            }
        });
    }

    public FrmAdministrator getFrmAdministrator() {
        return frmAdministrator;
    }

  
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import view.coordinator.MainCoordinator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.constants.AppMode;
import view.constants.Constants;
import view.forms.FrmWelcome;

/**
 *
 * @author rastko
 */
public class StartController {

    private final FrmWelcome frmWelcome;

    public StartController(FrmWelcome frmWelcome) {
        this.frmWelcome = frmWelcome;
        addActionListeners();
    }

    public void openForm() {
        frmWelcome.setVisible(true);
    }

    private void addActionListeners() {
        frmWelcome.lawyerModeAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().addParam(Constants.APP_MODE, AppMode.LAWYER);
                goToLogin();
            }
        });

        frmWelcome.adminModeAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().addParam(Constants.APP_MODE, AppMode.ADMINISTRATOR);
                goToLogin();
            }
        });

    }

    private void goToLogin() {
        frmWelcome.dispose();
        MainCoordinator.getInstance().openLoginForm();
    }
}

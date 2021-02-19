/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import view.coordinator.MainCoordinator;
import domain.Administrator;
import domain.Lawyer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.constants.AppMode;
import view.constants.Constants;
import view.forms.FrmLogin;

/**
 *
 * @author rastko
 */
public class LoginController {

    private final FrmLogin frmLogin;

    public LoginController(FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        addActionListener();
    }

    public void openLoginForm() {
        frmLogin.setVisible(true);
    }

    public FrmLogin getFrmLogin() {
        return frmLogin;
    }

    private void addActionListener() {
        frmLogin.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loginUser(actionEvent);
            }

            private void loginUser(ActionEvent actionEvent) {
                resetForm();
                try {
                    String username = frmLogin.getTxtUsername().getText().trim();
                    String password = String.copyValueOf(frmLogin.getTxtPassword().getPassword());

                    validateForm(username, password);

                    if (MainCoordinator.getInstance().getParam(Constants.APP_MODE).equals(AppMode.ADMINISTRATOR)) {
                        
                        Administrator administrator = Communication.getInstance().adminLogin(username, password);
                        JOptionPane.showMessageDialog(frmLogin, "Welcome administrator: " + administrator.getUsername(), "Successful login", JOptionPane.INFORMATION_MESSAGE);
                        MainCoordinator.getInstance().addParam(Constants.CURRENT_USER, administrator);
                        frmLogin.dispose();
                        MainCoordinator.getInstance().openAdministratorForm();

                    }

                    if (MainCoordinator.getInstance().getParam(Constants.APP_MODE).equals(AppMode.LAWYER)) {
                        
                        Lawyer lawyer = Communication.getInstance().lawyerLogin(username, password);
                        JOptionPane.showMessageDialog(frmLogin, "Welcome " + lawyer.toString() + "!", "Successful login", JOptionPane.INFORMATION_MESSAGE);
                        MainCoordinator.getInstance().addParam(Constants.CURRENT_USER, lawyer);
                        
                        frmLogin.dispose();
                        MainCoordinator.getInstance().openLawyerForm();
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmLogin, e.getMessage(), "Login error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void resetForm() {
                frmLogin.getLblUsernameValidation().setText("");
                frmLogin.getLblPasswordValidation().setText("");
            }

            private void validateForm(String username, String password) throws Exception {
                String errorMessage = "";
                if (username.isEmpty()) {
                    frmLogin.getLblUsernameValidation().setText("Username can not be empty!");
                    errorMessage += "Username can not be empty!\n";
                }
                if (password.isEmpty()) {
                    frmLogin.getLblPasswordValidation().setText("Password can not be empty!");
                    errorMessage += "Password can not be empty!\n";
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
            }

        });
    }
}

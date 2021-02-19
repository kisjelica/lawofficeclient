/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Lawyer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.coordinator.MainCoordinator;
import view.forms.util.FormMode;
import view.forms.FrmAddLawyer;

/**
 *
 * @author rastko
 */
public class AddLawyerController {

    private final FrmAddLawyer frmAddLawyer;
    private Lawyer lawyer;

    public AddLawyerController(FrmAddLawyer frmAddLawyer) {
        this.frmAddLawyer = frmAddLawyer;
        addActionListeners();
    }

    public void openForm() {
        frmAddLawyer.setLocationRelativeTo(frmAddLawyer.getParent());
        frmAddLawyer.setVisible(true);
    }

    private void addActionListeners() {
        frmAddLawyer.btnCreateAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    lawyer = Communication.getInstance().createNewLawyer();
                    frmAddLawyer.getTxtID().setText(String.valueOf(lawyer.getLawyerID()));
                    JOptionPane.showMessageDialog(frmAddLawyer, "A new lawyer has been created.", "Succesful creation", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAddLawyer, "Cannot create lawyer.","Create error",JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AddLawyerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            private void clearForm() {
                frmAddLawyer.getTxtFirstName().setText("");
                frmAddLawyer.getTxtLastName().setText("");
                frmAddLawyer.getTxtUsername().setText("");
                frmAddLawyer.getTxtPassword().setText("");
            }
        });

        frmAddLawyer.btnSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = frmAddLawyer.getTxtUsername().getText().trim();
                String password = frmAddLawyer.getTxtPassword().getText().trim();
                String firstName = frmAddLawyer.getTxtFirstName().getText().trim();
                String lastName = frmAddLawyer.getTxtLastName().getText().trim();
                LocalDateTime dateOfEmployment = frmAddLawyer.getPickerDateOfEmployment().getSelectedDate().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                try {
                    if (!validateForm(username, password, firstName, lastName, dateOfEmployment)) {
                        return;
                    }
                    lawyer.setUsername(username);
                    lawyer.setPassword(password);
                    lawyer.setFirstName(firstName);
                    lawyer.setLastName(lastName);
                    lawyer.setDateOfEmployment(dateOfEmployment);
                    Communication.getInstance().saveLawyer(lawyer);
                    JOptionPane.showMessageDialog(frmAddLawyer, "Lawyer has been succesfully saved!", "Successful save", JOptionPane.INFORMATION_MESSAGE);
                    frmAddLawyer.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAddLawyer, "Cannot save lawyer!", "Save error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AddLawyerController.class.getName()).log(Level.SEVERE, null, ex);
                    
                }

            }

            private boolean validateForm(String username, String password, String firstName, String lastName, LocalDateTime dateOfEmployment) throws Exception {
                if (lawyer == null) {
                    JOptionPane.showMessageDialog(frmAddLawyer, "Error: ID is empty.\n Please create a new lawyer.", "Save error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                String errorMessage = "";
                if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                    JOptionPane.showMessageDialog(frmAddLawyer, "Error: All fields must be filled.", "Save error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (password.length() < 8) {
                    errorMessage += "Error: Password must be at least 8 characters long.\n";
                }

                if (Character.isLowerCase(firstName.codePointAt(0))) {
                    errorMessage += "Error: First name must start with a capital letter\n";
                }

                if (dateOfEmployment.isAfter(LocalDateTime.now())) {
                    errorMessage += "Error: The date of employment must be today or before today\n";
                }
                if (!errorMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(frmAddLawyer, errorMessage, "Save error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (usernameExists(username)) {
                    JOptionPane.showMessageDialog(frmAddLawyer, "This username is already taken. Please enter another username.\n", "Username taken", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                return true;
            }

            private boolean usernameExists(String username) throws Exception {
                boolean usernameExists = Communication.getInstance().checkUsernameExists(username);
                return usernameExists;
            }
        });
    }

}

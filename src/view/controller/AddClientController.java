/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Client;
import domain.Lawyer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.constants.Constants;
import view.coordinator.MainCoordinator;
import view.forms.util.FormMode;
import view.forms.FrmAddLawyer;
import view.forms.FrmAddNewClient;

/**
 *
 * @author rastko
 */
public class AddClientController {
    
    private final FrmAddNewClient frmAddNewClient;
    private Client client;
    
    public AddClientController(FrmAddNewClient frmAddNewClient) {
        
        this.frmAddNewClient = frmAddNewClient;
        addActionListener();
    }
    
    public void openForm(FormMode formMode) {
        frmAddNewClient.setLocationRelativeTo(frmAddNewClient.getParent());
        setupComponents(formMode);
        frmAddNewClient.setVisible(true);
    }
    
    private void addActionListener() {
        frmAddNewClient.btnAddClientAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client = Communication.getInstance().createNewClient();
                    client.setLawyer((Lawyer) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
                    frmAddNewClient.getTxtID().setText(String.valueOf(client.getClientID()));
                    JOptionPane.showMessageDialog(frmAddNewClient, "A new client has been created.", "Succesful creation", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAddNewClient, "Cannot create client!", "Creation error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AddClientController.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
            }
            
            private void clearForm() {
                frmAddNewClient.getTxtFirstName().setText("");
                frmAddNewClient.getTxtLastName().setText("");
                frmAddNewClient.getTxtPhoneNumber().setText("");
                frmAddNewClient.getTxtEmail().setText("");
            }
        });
        
        frmAddNewClient.btnSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String firstName = frmAddNewClient.getTxtFirstName().getText().trim();
                    String lastName = frmAddNewClient.getTxtLastName().getText().trim();
                    String phoneNumber = frmAddNewClient.getTxtPhoneNumber().getText().trim();
                    String email = frmAddNewClient.getTxtEmail().getText().trim();
                    if (!validateForm(firstName, lastName, phoneNumber, email)) {
                        return;
                    }
                    
                    client.setFirstName(firstName);
                    client.setLastName(lastName);
                    client.setPhoneNumber(phoneNumber);
                    client.setEmail(email);
                    Communication.getInstance().saveClient(client);
                    JOptionPane.showMessageDialog(frmAddNewClient, "Client data has been succesfully saved!", "Save success", JOptionPane.INFORMATION_MESSAGE);
                    frmAddNewClient.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAddNewClient, "Cannot save client.", "Save error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AddClientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            private boolean validateForm(String firstName, String lastName, String phoneNumber, String email) {
                if (client == null) {
                    JOptionPane.showMessageDialog(frmAddNewClient, "Error: ID is empty.\n Please create a new client.", "Save error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                
                String errorMessage = "";
                if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(frmAddNewClient, "Error: All fields must be filled.", "Save error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                
                if (Character.isLowerCase(firstName.codePointAt(0))) {
                    errorMessage += "Error: First name must start with a capital letter\n";
                }
                
                if (!email.contains("@")) {
                    errorMessage += "Error: Invalid e-mail format.\n";
                }
                
                if (!errorMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(frmAddNewClient, errorMessage, "Save error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                return true;
            }
        });
        
        frmAddNewClient.btnUpdateAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String phoneNumber = frmAddNewClient.getTxtPhoneNumber().getText();
                    String email = frmAddNewClient.getTxtEmail().getText();
                    if (!email.contains("@")) {
                        JOptionPane.showConfirmDialog(frmAddNewClient, "Invalid email format!", "Update error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    client.setPhoneNumber(phoneNumber);
                    client.setEmail(email);
                    Communication.getInstance().saveClient(client);
                    JOptionPane.showMessageDialog(frmAddNewClient, "Succesful update!");
                    frmAddNewClient.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAddNewClient, "Cannot update client!", "Update error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AddClientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmAddNewClient.getBtnUpdate().setVisible(false);
                break;
            case FORM_EDIT:
                frmAddNewClient.setTitle("Law Office Manager - Edit client");
                frmAddNewClient.getBtnAddClient().setVisible(false);
                frmAddNewClient.getTxtFirstName().setEnabled(false);
                frmAddNewClient.getTxtLastName().setEnabled(false);
                frmAddNewClient.getBtnSaveData().setVisible(false);
                client = (Client) MainCoordinator.getInstance().getParam(Constants.SELECTED_CLIENT);
                frmAddNewClient.getTxtID().setText(client.getClientID().toString());
                frmAddNewClient.getTxtFirstName().setText(client.getFirstName());
                frmAddNewClient.getTxtLastName().setText(client.getLastName());
                frmAddNewClient.getTxtPhoneNumber().setText(client.getPhoneNumber());
                frmAddNewClient.getTxtEmail().setText(client.getEmail());
                break;
        }
    }
    
}

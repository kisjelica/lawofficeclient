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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.constants.Constants;
import view.coordinator.MainCoordinator;
import view.forms.FrmViewClients;
import view.tablemodels.ClientTableModel;

/**
 *
 * @author rastko
 */
public class ViewClientsController {

    private final FrmViewClients frmViewClients;

    public ViewClientsController(FrmViewClients frmViewClients) {
        this.frmViewClients = frmViewClients;
        addActionListeners();
    }

    public void openForm() {
        frmViewClients.setLocationRelativeTo(frmViewClients.getParent());
        // prepareView();
        frmViewClients.setVisible(true);
    }

    private void prepareView() {
        try {
            List<Client> myClients = Communication.getInstance().getClientsOfLawyer((Lawyer) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
            ClientTableModel model = new ClientTableModel(myClients);
            frmViewClients.getTblClients().setModel(model);
        } catch (Exception ex) {
            Logger.getLogger(ViewClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void addActionListeners() {
        frmViewClients.btnFindAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String searchFilter = frmViewClients.getTxtFindByName().getText();
                    List<Client> filteredList = new ArrayList<>();
                    List<Client> myClients = Communication.getInstance().getClientsOfLawyer((Lawyer) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
                    for (Client myClient : myClients) {
                        if (myClient.toString().trim().toLowerCase().contains(searchFilter.toLowerCase())) {
                            filteredList.add(myClient);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        JOptionPane.showMessageDialog(frmViewClients, "Cannot find clients by this name.");
                        frmViewClients.getTxtFindByName().setText("");
                    } else {
                        ClientTableModel model = new ClientTableModel(filteredList);
                        frmViewClients.getTblClients().setModel(model);
                       // JOptionPane.showMessageDialog(frmViewClients, "Clients have been found.");
                       
                    }

                } catch (Exception ex) {
                    Logger.getLogger(ViewClientsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        frmViewClients.btnUpdateSelectedAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientTableModel model = (ClientTableModel) frmViewClients.getTblClients().getModel();
                int row = frmViewClients.getTblClients().getSelectedRow();
                Client selectedClient = model.getSelectedClient(row);
                MainCoordinator.getInstance().addParam(Constants.SELECTED_CLIENT, selectedClient);
                MainCoordinator.getInstance().openEditClientForm();
            }
        });

        frmViewClients.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                prepareView();
            }

        });
    }
}

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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.forms.FrmViewAllLawyers;
import view.tablemodels.LawyerTableModel;

/**
 *
 * @author rastko
 */
public class ViewLawyersController {

    private final FrmViewAllLawyers frmViewAllLawyers;

    public ViewLawyersController(FrmViewAllLawyers frmViewAllLawyers) {
        this.frmViewAllLawyers = frmViewAllLawyers;
        addActionListeners();
    }

    public void openForm() {
        frmViewAllLawyers.setLocationRelativeTo(frmViewAllLawyers.getParent());
        prepareView();
        frmViewAllLawyers.setVisible(true);
    }

    private void addActionListeners() {
        frmViewAllLawyers.btnFindAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchFilter = frmViewAllLawyers.getTxtFindByName().getText();
                try {
                    List<Lawyer> filteredList = new ArrayList<>();
                    List<Lawyer> lawyers = Communication.getInstance().getLawyers();
                    for (Lawyer lawyer : lawyers) {
                        if (lawyer.getFirstName().toLowerCase().contains(searchFilter.toLowerCase()) || lawyer.getLastName().toLowerCase().contains(searchFilter.toLowerCase())) {
                            filteredList.add(lawyer);
                        }
                    }

                    LawyerTableModel model = new LawyerTableModel(filteredList);
                    frmViewAllLawyers.getTblLawyers().setModel(model);
                    if (filteredList.isEmpty()) {
                        JOptionPane.showMessageDialog(frmViewAllLawyers, "Cannot find lawyers.");
                    } else {
                        JOptionPane.showMessageDialog(frmViewAllLawyers, "Lawyers found.");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ViewLawyersController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        frmViewAllLawyers.btnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = frmViewAllLawyers.getTblLawyers().getSelectedRow();
                    LawyerTableModel model = (LawyerTableModel) frmViewAllLawyers.getTblLawyers().getModel();
                    Lawyer lawyer = model.getLawyerAtSelectedRow(row);
                    Communication.getInstance().deleteLawyer(lawyer);
                    model.removeLawyer(row);
                    JOptionPane.showMessageDialog(frmViewAllLawyers, "The selected lawyer has been deleted.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmViewAllLawyers, "Cannot delete lawyer.", "Deletion error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ViewLawyersController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    private void prepareView() {
        try {
            List<Lawyer> lawyers = Communication.getInstance().getLawyers();
            LawyerTableModel model = new LawyerTableModel(lawyers);
            frmViewAllLawyers.getTblLawyers().setModel(model);
        } catch (Exception ex) {
            Logger.getLogger(ViewLawyersController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

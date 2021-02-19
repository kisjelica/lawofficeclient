/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Obligation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.constants.Constants;
import view.coordinator.MainCoordinator;
import view.forms.FrmViewObligations;
import view.tablemodels.ObligationTableModel;

/**
 *
 * @author rastko
 */
public class ViewObligationController {

    private final FrmViewObligations frmViewObligations;

    public ViewObligationController(FrmViewObligations frmViewObligations) {
        this.frmViewObligations = frmViewObligations;
    }

    public void openForm() {
        frmViewObligations.setLocationRelativeTo(frmViewObligations.getParent());
        prepareView();
        addActionListeners();
        

        frmViewObligations.setVisible(true);

    }

    private void prepareView() {
        try {
            List<Obligation> obligations = Communication.getInstance().getObligations();
            ObligationTableModel obligationTableModel = new ObligationTableModel(obligations);
            frmViewObligations.getTblObligations().setModel(obligationTableModel);
        } catch (Exception ex) {
            Logger.getLogger(ViewObligationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addActionListeners() {
        frmViewObligations.btnSearchAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Calendar selectedDate = frmViewObligations.getPickerDateOfObligation().getSelectedDate();
                    LocalDate searchDateTime = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    List<Obligation> filteredList = new ArrayList<>();
                    List<Obligation> obligations = Communication.getInstance().getObligations();
                    for (Obligation obligation : obligations) {
                        if (obligation.getDateOfObligation().toLocalDate().equals(searchDateTime)) {
                            filteredList.add(obligation);
                        }
                    }
                    if(filteredList.isEmpty()){
                        JOptionPane.showMessageDialog(frmViewObligations, "Cannot find obligations by this date.");
                    }else{
                        JOptionPane.showMessageDialog(frmViewObligations, "Obligations found!");
                    }
                    
                    ObligationTableModel model = new ObligationTableModel(filteredList);
                    frmViewObligations.getTblObligations().setModel(model);
                } catch (Exception ex) {
                    Logger.getLogger(ViewObligationController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        frmViewObligations.btnAttendanceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewObligations.getTblObligations().getSelectedRow();
                ObligationTableModel model = (ObligationTableModel) frmViewObligations.getTblObligations().getModel();
                Obligation selectedObligation = model.getSelectedObligation(row);
                MainCoordinator.getInstance().addParam(Constants.SELECTED_OBLIGATION, selectedObligation);
                MainCoordinator.getInstance().openObligationUpdateForm();
            }
        });
    }

}

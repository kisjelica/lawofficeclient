/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Lawyer;
import domain.Obligation;
import domain.ObligationAttendance;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.constants.Constants;
import view.coordinator.MainCoordinator;
import view.forms.FrmObligation;
import view.forms.util.FormMode;

/**
 *
 * @author rastko
 */
public class ObligationController {

    private final FrmObligation frmObligation;
    private Obligation obligation;

    public ObligationController(FrmObligation frmObligation) {
        this.frmObligation = frmObligation;
    }

    public void openForm(FormMode formMode) {
        frmObligation.setLocationRelativeTo(frmObligation.getParent());
        addActionListeners();
        prepareView(formMode);
        frmObligation.setVisible(true);
    }

    private void addActionListeners() {
        frmObligation.btnSaveObligationAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String subject = frmObligation.getTxtSubject().getText();
                    Date dat = (Date) frmObligation.getSpDateTime().getValue();
                    LocalDateTime time = dat.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    String description = frmObligation.getTxtDescription().getText();
                    if (!validate(subject, description)) {
                        return;
                    }
                    obligation = new Obligation(null, subject, description, time);
                    obligation = Communication.getInstance().createObligation(obligation);
                    JOptionPane.showMessageDialog(frmObligation, "Obligation succesfully created and saved!", "Create success", JOptionPane.INFORMATION_MESSAGE);
                    frmObligation.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmObligation, "Cannot create obligation.","Create error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ObligationController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            private boolean validate(String subject, String description) {
                if (subject.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(frmObligation, "Fields can not be empty");
                    return false;
                }
                return true;
            }
        });
        frmObligation.btnConfirmAttendanceAddActionListener(new ActionListener() {
            
            
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Lawyer currentLawyer = (Lawyer) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                    
                    ObligationAttendance obligationAttendance = null;
                    
                    if (frmObligation.getRbAttended().isSelected()) {
                        obligationAttendance = new ObligationAttendance(currentLawyer, obligation, true);
                    }
                    if (frmObligation.getRbDidNotAttend().isSelected()) {
                        obligationAttendance = new ObligationAttendance(currentLawyer, obligation, false);
                    }
                    if(obligationAttendanceExists(obligationAttendance)){
                        JOptionPane.showMessageDialog(frmObligation, "You have already set your attendance for this obligation.");
                        frmObligation.dispose();
                        return;
                    }
                    int showConfirmDialog = JOptionPane.showConfirmDialog(frmObligation, "Are you sure that you want to save your choice?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (showConfirmDialog == 0) {
                        try {
                            Communication.getInstance().saveObligationAttendance(obligationAttendance);
                            JOptionPane.showMessageDialog(frmObligation, "Attendance has been saved", "Success", JOptionPane.INFORMATION_MESSAGE);
                            frmObligation.dispose();
                        } catch (Exception ex) {
                            Logger.getLogger(ObligationController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        return;
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ObligationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private boolean obligationAttendanceExists(ObligationAttendance obligationAttendance) throws Exception {
                return Communication.getInstance().obligationAttendanceExists(obligationAttendance);
            }
        }
        );

    }

    private void prepareView(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmObligation.remove(frmObligation.getPanelAttendance());
                frmObligation.remove(frmObligation.getLblDateTime());
                frmObligation.pack();
                frmObligation.setResizable(false);
                break;
            case FORM_EDIT:
                frmObligation.getBtnSaveData().setVisible(false);
                frmObligation.getTxtSubject().setEnabled(false);
                frmObligation.getTxtDescription().setEditable(false);
                frmObligation.getSpDateTime().setEnabled(false);
                obligation = (Obligation) MainCoordinator.getInstance().getParam(Constants.SELECTED_OBLIGATION);
                frmObligation.getTxtSubject().setText(obligation.getObligationSubject());
                frmObligation.getTxtDescription().setText(obligation.getObligationDescription());
                frmObligation.getSpDateTime().setVisible(false);
                frmObligation.getLblDateTime().setText(obligation.getDateOfObligation().toString());
                frmObligation.pack();

        }
    }
}

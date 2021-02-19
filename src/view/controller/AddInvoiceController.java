/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Client;
import domain.Invoice;
import domain.Lawyer;
import domain.Service;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.converter.LocalDateTimeStringConverter;
import javax.swing.JOptionPane;
import view.constants.Constants;
import view.coordinator.MainCoordinator;
import view.forms.util.FormMode;
import view.forms.FrmAddInvoice;
import view.tablemodels.InvoiceTableModel;

/**
 *
 * @author rastko
 */
public class AddInvoiceController {

    private final FrmAddInvoice frmAddInvoice;
    private Invoice invoice;

    public AddInvoiceController(FrmAddInvoice frmAddInvoice) {
        this.frmAddInvoice = frmAddInvoice;
    }

    public void openForm(FormMode formMode) {
        frmAddInvoice.setLocationRelativeTo(frmAddInvoice.getParent());
        addActionListeners();
        prepareView(formMode);

        frmAddInvoice.setVisible(true);

    }

    private void addActionListeners() {
        
        frmAddInvoice.btnCreateInvoiceAddActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    invoice = Communication.getInstance().createNewInvoice();
                    JOptionPane.showMessageDialog(frmAddInvoice, "A new invoice has been created!", "Succesful creation", JOptionPane.INFORMATION_MESSAGE);
                    frmAddInvoice.getTxtID().setText(invoice.getInvoiceID().toString());
                    InvoiceTableModel model = new InvoiceTableModel(invoice);
                    frmAddInvoice.getTblItems().setModel(model);
                    clearInvoiceForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAddInvoice, "Cannot create invoice." , "Creation error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AddInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void clearInvoiceForm() {
                frmAddInvoice.getTxtSerial().setText("" + LocalDate.now().getYear() + frmAddInvoice.getTxtID().getText());
                frmAddInvoice.getCbClients().setSelectedIndex(-1);
                frmAddInvoice.getTxtTotal().setText("");
                frmAddInvoice.getCbService().setSelectedIndex(-1);
                frmAddInvoice.getTxtQuantity().setText("");
                frmAddInvoice.getTxtPrice().setText("");
            }
        });
        frmAddInvoice.btnAddToInvoiceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InvoiceTableModel model = (InvoiceTableModel) frmAddInvoice.getTblItems().getModel();
                Service service = (Service) frmAddInvoice.getCbService().getSelectedItem();
                int quantity = Integer.parseInt(frmAddInvoice.getTxtQuantity().getText());

                if (!validateItemData(service, quantity)) {
                    return;
                }
                model.addInvoiceItem(service, quantity);
                frmAddInvoice.getTxtTotal().setText(invoice.getInvoiceTotal().toString());

            }

            private boolean validateItemData(Service service, int quantity) {
                if (service == null) {
                    JOptionPane.showMessageDialog(frmAddInvoice, "You must select a service");
                    return false;
                }
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(frmAddInvoice, "Quantity must be more than 0!");
                    return false;
                }
                return true;
            }
        });

        frmAddInvoice.btnRemoveFromInvoiceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                InvoiceTableModel model = (InvoiceTableModel) frmAddInvoice.getTblItems().getModel();
                int row = frmAddInvoice.getTblItems().getSelectedRow();
                if (row >= 0) {
                    model.removeInvoiceItem(row);
                    frmAddInvoice.getTxtTotal().setText(invoice.getInvoiceTotal().toString());
                } else {
                    JOptionPane.showMessageDialog(frmAddInvoice, "You must select a service to remove.");
                }
            }
        });

        frmAddInvoice.btnSaveInvoiceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (invoice == null) {
                        JOptionPane.showMessageDialog(frmAddInvoice, "You must create an invoice!", "Save error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (invoice.getItems().isEmpty()) {
                        JOptionPane.showMessageDialog(frmAddInvoice, "There must be at least 1 item in the invoice!");
                        return;
                    }
                    String serialNumber = frmAddInvoice.getTxtSerial().getText();
                    Client client = (Client) frmAddInvoice.getCbClients().getSelectedItem();
                    if (client == null) {
                        JOptionPane.showMessageDialog(frmAddInvoice, "You must select a client!");
                        return;
                    }
                    LocalDateTime invoiceDate = LocalDateTime.now();
                    Lawyer currentLawyer = (Lawyer) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                    invoice.setInvoiceNumber(serialNumber);
                    invoice.setClient(client);
                    invoice.setLawyer(currentLawyer);
                    invoice.setInvoiceDate(invoiceDate);
                    Communication.getInstance().saveInvoice(invoice);
                    JOptionPane.showMessageDialog(frmAddInvoice, "Invoice has been saved!", "Succesful save", JOptionPane.INFORMATION_MESSAGE);
                    frmAddInvoice.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAddInvoice, "Cannot save invoice!", "Save error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AddInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        frmAddInvoice.btnCancelInvoiceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (invoice.isCancelled()) {
                        JOptionPane.showMessageDialog(frmAddInvoice, "Invoice is already cancelled!");
                        return;
                    }
                    if (invoice.isProcessed()) {
                        JOptionPane.showMessageDialog(frmAddInvoice, "Invoice has been processed and it cannot be cancelled.");
                        return;
                    }
                    invoice.setCancelled(true);
                    Communication.getInstance().saveInvoice(invoice);
                    JOptionPane.showMessageDialog(frmAddInvoice, "Invoice has been succesfully cancelled.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frmAddInvoice.getChkCancelled().setSelected(true);
                    frmAddInvoice.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(AddInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        frmAddInvoice.btnProcessInvoiceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (invoice.isCancelled()) {
                        JOptionPane.showMessageDialog(frmAddInvoice, "Invoice has been cancelled and it cannot be processed.");
                        return;
                    }
                    if (invoice.isProcessed()) {
                        JOptionPane.showMessageDialog(frmAddInvoice, "Invoice has already been processed!");
                        return;
                    }
                    invoice.setProcessed(true);
                    Communication.getInstance().saveInvoice(invoice);
                    JOptionPane.showMessageDialog(frmAddInvoice, "Invoice has been succesfully processed", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frmAddInvoice.getChkProcessed().setSelected(true);
                    frmAddInvoice.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(AddInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
            }
        });
    }

    private void prepareView(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                // frmAddInvoice.getPanelStatus().setVisible(false);
                frmAddInvoice.remove(frmAddInvoice.getPanelStatus());
                frmAddInvoice.pack();
                frmAddInvoice.setResizable(false);
                fillFormForInsert();
                break;
            case FORM_EDIT:
                frmAddInvoice.remove(frmAddInvoice.getPnlTbl());
                frmAddInvoice.remove(frmAddInvoice.getPanelService());
                frmAddInvoice.remove(frmAddInvoice.getBtnSave());
                frmAddInvoice.remove(frmAddInvoice.getBtnCreate());
                frmAddInvoice.remove(frmAddInvoice.getBtnRemove());
                frmAddInvoice.getTxtSerial().setEnabled(false);
                frmAddInvoice.getTxtTotal().setEnabled(false);

                frmAddInvoice.pack();
                frmAddInvoice.setResizable(false);
                invoice = (Invoice) MainCoordinator.getInstance().getParam(Constants.SELECTED_INVOICE);

                frmAddInvoice.getTxtID().setText(invoice.getInvoiceID().toString());
                frmAddInvoice.getTxtSerial().setFormatterFactory(null);
                frmAddInvoice.getTxtSerial().setText("" + invoice.getInvoiceNumber());
                frmAddInvoice.getTxtTotal().setText(invoice.getInvoiceTotal().toString());
                frmAddInvoice.getCbClients().addItem(invoice.getClient());
                frmAddInvoice.getCbClients().setSelectedIndex(0);
                frmAddInvoice.getCbClients().setEnabled(false);
                frmAddInvoice.getChkCancelled().setSelected(invoice.isCancelled());
                frmAddInvoice.getChkProcessed().setSelected(invoice.isProcessed());
                break;

        }
    }

    private void fillFormForInsert() {
        try {
            List<Client> clients = Communication.getInstance().getClientsOfLawyer((Lawyer) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
            for (Client client : clients) {
                frmAddInvoice.getCbClients().addItem(client);
            }
            frmAddInvoice.getCbClients().setSelectedIndex(-1);
            List<Service> services = Communication.getInstance().getServices();
            for (Service service : services) {
                frmAddInvoice.getCbService().addItem(service);
            }
            frmAddInvoice.getCbService().setSelectedIndex(-1);

            frmAddInvoice.getCbService().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    changeProduct(e);
                }

                private void changeProduct(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        Service service = (Service) e.getItem();
                        frmAddInvoice.getTxtPrice().setText(String.valueOf(service.getFee()));
                        frmAddInvoice.getTxtQuantity().setText("1");
                        frmAddInvoice.getTxtQuantity().grabFocus();

                    }
                }
            });

        } catch (Exception ex) {
            Logger.getLogger(AddInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

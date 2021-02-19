/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.coordinator;

import view.controller.LoginController;
import view.controller.StartController;
import java.util.HashMap;
import java.util.Map;
import view.constants.Constants;
import view.controller.AddClientController;
import view.controller.AddInvoiceController;

import view.controller.AddLawyerController;
import view.controller.AdministratorController;
import view.controller.LawyerController;
import view.controller.ObligationController;
import view.controller.ViewClientsController;
import view.controller.ViewInvoiceController;
import view.controller.ViewLawyersController;
import view.controller.ViewObligationController;
import view.forms.util.FormMode;
import view.forms.FrmAddInvoice;
import view.forms.FrmAddLawyer;
import view.forms.FrmAddNewClient;
import view.forms.FrmAdministrator;
import view.forms.FrmLawyer;
import view.forms.FrmLogin;
import view.forms.FrmObligation;
import view.forms.FrmViewAllLawyers;
import view.forms.FrmViewClients;
import view.forms.FrmViewInvoices;
import view.forms.FrmViewObligations;
import view.forms.FrmWelcome;

/**
 *
 * @author rastko
 */
public class MainCoordinator {

    private AdministratorController administratorController;
    private LawyerController lawyerController;

    public static MainCoordinator getInstance() {
        return MainCoordinatorHolder.INSTANCE;
    }

    public Object getParam(String name) {
        return params.get(name);
    }

   
    

    private static class MainCoordinatorHolder {

        private static final MainCoordinator INSTANCE = new MainCoordinator();
    }

    private final Map<String, Object> params;

    private MainCoordinator() {
        params = new HashMap<>();
    }

    public void addParam(String key, Object value) {
        params.put(key, value);
    }

    public void openWelcomeForm() {
        StartController startController = new StartController(new FrmWelcome());
        startController.openForm();
    }

    public void openLoginForm() {
        LoginController loginController = new LoginController(new FrmLogin());
        loginController.openLoginForm();

    }

    public void openAdministratorForm() {
        administratorController = new AdministratorController(new FrmAdministrator());
        administratorController.openForm();

    }

    public void openLawyerForm() {
        lawyerController = new LawyerController(new FrmLawyer());
        lawyerController.openForm();
    }

    public void openAddLawyerForm() {
        AddLawyerController addLawyerController = new AddLawyerController(new FrmAddLawyer(administratorController.getFrmAdministrator(), true));
        addLawyerController.openForm();
    }

    public void openViewLawyersForm() {
        ViewLawyersController viewLawyersController = new ViewLawyersController(new FrmViewAllLawyers(administratorController.getFrmAdministrator(), true));
        viewLawyersController.openForm();
    }

    public void openAddClientForm() {
        AddClientController addClientController = new AddClientController(new FrmAddNewClient(lawyerController.getFrmLawyer(), true));
        addClientController.openForm(FormMode.FORM_ADD);
    }

    public void openEditClientForm() {
        AddClientController addClientController = new AddClientController(new FrmAddNewClient(lawyerController.getFrmLawyer(), true));
        addClientController.openForm(FormMode.FORM_EDIT);
    }

    public void openViewClientsForm() {
        ViewClientsController viewClientsController = new ViewClientsController(new FrmViewClients(lawyerController.getFrmLawyer(), true));
        viewClientsController.openForm();
    }

    public void openAddInvoiceForm() {
        AddInvoiceController addInvoiceController = new AddInvoiceController(new FrmAddInvoice(lawyerController.getFrmLawyer(), true));
        addInvoiceController.openForm(FormMode.FORM_ADD);
    }

    public void openUpdateInvoiceForm() {
        AddInvoiceController addInvoiceController = new AddInvoiceController(new FrmAddInvoice(lawyerController.getFrmLawyer(), true));
        addInvoiceController.openForm(FormMode.FORM_EDIT);
    }

    public void openViewInvoiceForm() {
        ViewInvoiceController viewInvoiceController = new ViewInvoiceController(new FrmViewInvoices(lawyerController.getFrmLawyer(), true));
        viewInvoiceController.openForm();
    }

    public void openObligationForm() {
        ObligationController obligationController = new ObligationController(new FrmObligation(lawyerController.getFrmLawyer(), true));
        obligationController.openForm(FormMode.FORM_ADD);
    }
     public void openObligationUpdateForm() {
        ObligationController obligationController = new ObligationController(new FrmObligation(lawyerController.getFrmLawyer(), true));
        obligationController.openForm(FormMode.FORM_EDIT);
     }

    
    public void openViewObligationsForm() {
        ViewObligationController viewObligationController = new ViewObligationController(new FrmViewObligations(lawyerController.getFrmLawyer(), true));
        viewObligationController.openForm();
    }

}

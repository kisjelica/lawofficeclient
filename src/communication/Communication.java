/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import domain.Administrator;
import domain.Client;
import domain.Invoice;
import domain.Lawyer;
import domain.Obligation;
import domain.ObligationAttendance;
import domain.Service;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rastko
 */
public class Communication {

    Socket socket;
    Sender sender;
    Receiver receiver;

    private Communication() {
        try {
            socket = new Socket("localhost", 1389);
            sender = new Sender(socket);
            receiver = new Receiver(socket);
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Communication getInstance() {
        return CommunicationHolder.INSTANCE;
    }

    public Administrator adminLogin(String username, String password) throws Exception {
        Administrator administrator = new Administrator();
        administrator.setUsername(username);
        administrator.setPassword(password);
        Request request = new Request(Operation.LOGIN_ADMIN, administrator);
        sender.send(request);
        Response response = (Response) receiver.receive();
        System.out.println("GOOD");
        if (response.getException() == null) {
            return (Administrator) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public Lawyer lawyerLogin(String username, String password) throws Exception {
        Lawyer lawyer = new Lawyer();
        lawyer.setUsername(username);
        lawyer.setPassword(password);
        Request request = new Request(Operation.LOGIN_LAWYER, lawyer);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (Lawyer) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public Lawyer createNewLawyer() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.CREATE_LAWYER);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (Lawyer) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public boolean checkUsernameExists(String username) throws Exception {
        Request request = new Request(Operation.CHECK_USERNAME_EXISTS, username);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (boolean) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void saveLawyer(Lawyer lawyer) throws Exception {
        Request request = new Request(Operation.SAVE_LAWYER, lawyer);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
        } else {
            throw response.getException();
        }
    }

    public List<Lawyer> getLawyers() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.GET_LAWYERS);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Lawyer>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void deleteLawyer(Lawyer lawyer) throws Exception {
        Request request = new Request(Operation.DELETE_LAWYER, lawyer);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {

        } else {
            throw response.getException();
        }
    }

    public Client createNewClient() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.CREATE_CLIENT);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (Client) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void saveClient(Client client) throws Exception {
        Request request = new Request(Operation.SAVE_CLIENT, client);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
        } else {
            throw response.getException();
        }
    }

    public List<Client> getClientsOfLawyer(Lawyer lawyer) throws Exception {
        Request request = new Request(Operation.GET_CLIENTS_OF_LAWYER,lawyer);
        
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Client>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public Invoice createNewInvoice() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.CREATE_INVOICE);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (Invoice) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Service> getServices() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.GET_SERVICES);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Service>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void saveInvoice(Invoice invoice) throws Exception {
        Request request = new Request(Operation.SAVE_INVOICE, invoice);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
        }else{
            throw response.getException();
        }
        
    }

    public List<Invoice> getInvoicesOfLawyer(Lawyer lawyer) throws Exception {
        Request request = new Request(Operation.GET_INVOICES_OF_LAWYER, lawyer);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
            return (List<Invoice>) response.getResult();
        }else{
            throw response.getException();
        }
    }

    public Obligation createObligation(Obligation obligation) throws Exception {
        Request request = new Request(Operation.CREATE_OBLIGATION,obligation);       
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (Obligation) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Obligation> getObligations() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.GET_OBLIGATIONS);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Obligation>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void saveObligationAttendance(ObligationAttendance obligationAttendance) throws Exception {
        Request request = new Request(Operation.SAVE_ATTENDANCE, obligationAttendance);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
        }else{
            throw response.getException();
        }
    }

    public boolean obligationAttendanceExists(ObligationAttendance obligationAttendance) throws Exception {
        Request request = new Request(Operation.ATTENDANCE_EXISTS, obligationAttendance);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (boolean) response.getResult();
        } else {
            throw response.getException();
        }
    }

    

    private static class CommunicationHolder {

        private static final Communication INSTANCE = new Communication();
    }
}

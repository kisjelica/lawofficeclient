/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.tablemodels;

import domain.Client;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rastko
 */
public class ClientTableModel extends AbstractTableModel{
    
    private final List<Client> clients;
    private final String[] columnNames = new String[]{"Client name","Phone number","E-mail"};
    public ClientTableModel(List<Client> clients) {
        this.clients = clients;
    }
    
    
    @Override
    public int getRowCount() {
        return clients.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Client client = clients.get(rowIndex);
        switch(columnIndex){
            case 0:
                return client.toString();
            case 1:
                return client.getPhoneNumber();
            case 2:
                return client.getEmail();
            default:
                return "n/a";
             
        }
    }
    
    public Client getSelectedClient(int row){
        return clients.get(row);
    }
}

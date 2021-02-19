/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.tablemodels;

import domain.Invoice;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rastko
 */
public class ViewInvoiceTableModel extends AbstractTableModel {

    private final List<Invoice> invoices;
    private final String[] columnNames = new String[]{"Invoice date and time", "Client","Status"};

    public ViewInvoiceTableModel(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public int getRowCount() {
        return invoices.size();
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
        Invoice invoice = invoices.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return invoice.getInvoiceDate();
            case 1:
                return invoice.getClient();
            case 2:
                if (invoice.isCancelled()) {
                    return "CANCELLED";
                } else if (invoice.isProcessed()) {
                    return "PROCESSED";
                } else {
                    return "NONE";
                }
            default:
                return "n/a";
        }
    }

    
    public Invoice getSelectedInvoice(int row){
        return invoices.get(row);
    }
}

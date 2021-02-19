/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.tablemodels;

import domain.Invoice;
import domain.InvoiceItem;
import domain.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rastko
 */
public class InvoiceTableModel extends AbstractTableModel{
    private final Invoice invoice;
    private final String[] columnNames = new String[]{"Order number","Name","Price","Quantity","Amount"};
    public InvoiceTableModel(Invoice invoice) {
        this.invoice = invoice;
        invoice.setItems(new ArrayList<>());
    }

    @Override
    public int getRowCount() {
        return invoice.getItems().size();
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
        InvoiceItem item = invoice.getItems().get(rowIndex);
        switch(columnIndex){
            case 0:
                return item.getOrderNumber();
            case 1:
                return item.getService();
            case 2:
                return item.getPrice();
            case 3:
                return item.getQuantity();
            case 4:
                return item.getAmount();
            default:
                return "n/a";
        }
    }
    
    public void addInvoiceItem(Service service, int quantity){
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoice(invoice);
        invoiceItem.setService(service);
        invoiceItem.setOrderNumber(invoice.getItems().size() + 1);
        invoiceItem.setQuantity(quantity);
        invoiceItem.setPrice(service.getFee());
        invoiceItem.setAmount(BigDecimal.valueOf(invoiceItem.getQuantity()).multiply(invoiceItem.getPrice()));
        invoice.getItems().add(invoiceItem);
        invoice.setInvoiceTotal(invoice.getInvoiceTotal().add(invoiceItem.getAmount()));
        fireTableRowsInserted(invoice.getItems().size() -1, invoice.getItems().size() -1);
        
    }
    
    public void removeInvoiceItem(int rowIndex) {
        InvoiceItem item = invoice.getItems().get(rowIndex);
        invoice.getItems().remove(rowIndex);
        invoice.setInvoiceTotal(invoice.getInvoiceTotal().subtract(item.getAmount()));
        setOrderNumbers();
        fireTableRowsDeleted(invoice.getItems().size() - 1, invoice.getItems().size() - 1);
    }

    private void setOrderNumbers() {
        int orderNo = 0;
        for (InvoiceItem item : invoice.getItems()) {
            item.setOrderNumber(++orderNo);
        }
    }

    
}

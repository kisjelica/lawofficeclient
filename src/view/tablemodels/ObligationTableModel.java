/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.tablemodels;

import domain.Obligation;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rastko
 */
public class ObligationTableModel extends AbstractTableModel{

    private final List<Obligation> obligations;
    private final String[] columnNames = new String[]{"Obligation subject","Meeting date"};
    public ObligationTableModel(List<Obligation> obligations) {
        this.obligations = obligations;
    }
    
    @Override
    public int getRowCount() {
        return obligations.size();
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
        Obligation obligation = obligations.get(rowIndex);
        switch(columnIndex){
            case 0:
                return obligation.getObligationSubject();
            case 1:
                return obligation.getDateOfObligation();
            default:
                return "n/a";
        }
    }
    
    public Obligation getSelectedObligation(int row){
        return obligations.get(row);
    }

    public List<Obligation> getObligations() {
        return obligations;
    }
    
    
}

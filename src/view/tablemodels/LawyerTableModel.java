/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.tablemodels;

import domain.Lawyer;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rastko
 */
public class LawyerTableModel extends AbstractTableModel {

    private final List<Lawyer> lawyers;
    private final String[] columnNames = {"Name", "Username", "Date of employment"};

    public LawyerTableModel(List<Lawyer> lawyers) {
        this.lawyers = lawyers;
    }

    @Override
    public int getRowCount() {
        return lawyers.size();
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
        Lawyer lawyer = lawyers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return lawyer.toString();
            case 1:
                return lawyer.getUsername();
            case 2:
                return lawyer.getDateOfEmployment();
            default:
                return "n/a";
        }
    }

    public void removeLawyer(int row) {
        lawyers.remove(row);
        fireTableRowsDeleted(lawyers.size() - 1, lawyers.size() - 1);
    }

    public Lawyer getLawyerAtSelectedRow(int row) {
        return lawyers.get(row);
    }

}

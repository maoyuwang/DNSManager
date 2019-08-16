import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.*;
import javax.swing.table.TableColumnModel;

/**
 * A ButtonColumn Component provides Edit and Delete action for every record.
 */
public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private JTable table;
    private JButton renderButton;
    private JButton editButton;
    private String text;
    private RecordForm reCordFm;

    public ButtonColumn(RecordForm _reCordFm,JTable table, int column) {
        super();
        this.table = table;
        this.reCordFm=_reCordFm;
        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }

    /**
     *  Get the selected Rendered Table Cell component
     * @param table The table to deal with.
     * @param value The value of the text.
     * @param isSelected    Check if this table is selected.
     * @param hasFocus  Check if the table has focus.
     * @param row   The row of the table
     * @param column The column of the table.
     * @return  The Rendered component.
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (hasFocus) {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        } else if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        renderButton.setText((value == null) ? " " : value.toString());
        return renderButton;
    }

    /**
     * Get the Edit component of the specific cell.
     * @param table The table to deal with.
     * @param value The value of the text
     * @param isSelected    Check if the cell is selected
     * @param row The row of the table.
     * @param column    The column of the table.
     * @return  The edit component.
     */
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        text = (value == null) ? " " : value.toString();
        editButton.setText(text);
        return editButton;
    }

    /**
     * Get the Edit Value of the cell.
     * @return
     */
    public Object getCellEditorValue() {
        return text;
    }

    /**
     * Handle all actions to this Component.
     * @param e The action performed on this component.
     */
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
        int i=table.getSelectedRow();
        if(e.getActionCommand()=="Edit")
        {
            EditRecordForm editFm=new EditRecordForm(reCordFm,i,new Record(table.getValueAt(i,0).toString(),table.getValueAt(i,1).toString(),table.getValueAt(i,2).toString(),table.getValueAt(i,3).toString()));
            editFm.setVisible(true);
            this.reCordFm.getContentPane().add(editFm);
        }
        else if (e.getActionCommand()=="Delete")
        {
            reCordFm.DeleteRow(i);
        }
        System.out.println(e.getActionCommand() + "   :    "
                + table.getSelectedRow());
    }
}

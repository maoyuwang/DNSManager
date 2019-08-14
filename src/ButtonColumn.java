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
public class ButtonColumn extends AbstractCellEditor implements
        TableCellRenderer, TableCellEditor, ActionListener {
    JTable table;
    JButton renderButton;
    JButton editButton;
    String text;
    RecordForm reCordFm;

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

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        text = (value == null) ? " " : value.toString();
        editButton.setText(text);
        return editButton;
    }

    public Object getCellEditorValue() {
        return text;
    }

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

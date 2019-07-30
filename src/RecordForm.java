import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
public class RecordForm extends JDialog {
    public JPanel panel1;
    private JPanel panel3;
    private JButton btAdd;
    private JScrollPane spanel1;
    private JTable table1;
    private DefaultTableModel model;
    private TableColumn tc;
    private MainForm mainFm;
    private String Company;
    public RecordForm() {
        init();
    }
    public RecordForm(MainForm _mainFm,String _company) {
        this.mainFm=_mainFm;
        this.Company=_company;
        init();
    }
    private void OpenNewRecordForm(){
        NewRecordForm newRecordForm=new NewRecordForm(this);
        newRecordForm.setVisible(true);
        getContentPane().add(newRecordForm);
    }
    public void AddRow(String name,String type,String value,String ttl){
        model.addRow(new Object[]{name,type,value,ttl,"Edit","Delete"});
        table1.setModel(model);
    }
    public void EditRow(String value,int row,int column){
        model.setValueAt(value,row,column);
    }
    public void DeleteRow(int row){
        model.removeRow(row);
    }
    private void init(){
        setTitle("Domain Manager-"+Company);
        setContentPane(panel1);
        setModal(true);
        //getRootPane().setDefaultButton(buttonOK);
        int Width=600;
        int Height=600;
        // setSize(Width,Height);
        Dimension dim =new Dimension(Width,Height);
        setMaximumSize(dim);
        setMinimumSize(dim);
        setLocationRelativeTo(this.getOwner());
        model = new DefaultTableModel(//
                new Object[][] {},// 数据
                new Object[] { "Name","Type", "Value","TTL","Edit","Delete" } // 表头
        );
        table1.setModel(model);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.setRowHeight(25);
        JTableHeader tableHeader = table1.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 30));
        ButtonColumn editButtonColumn = new ButtonColumn(this,table1, 4);
        ButtonColumn deleteButtonColumn = new ButtonColumn(this,table1, 5);
        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // JOptionPane.showMessageDialog(null, "在对话框内显示的描述性的文字", "标题", JOptionPane.ERROR_MESSAGE);
                OpenNewRecordForm();
            }
        });
    }
}

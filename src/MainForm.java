import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
public class MainForm extends JFrame {
    public JPanel panel1;
    private JPanel panel3;
    private JButton btAdd;
    private JButton btConfig;
    private JButton btDelete;
    private JScrollPane spanel1;
    private JTable table1;
    private DefaultTableModel model;
    private TableColumn tc;
    public MainForm() {
       // setBounds(300,300,800,600);
        setTitle("DNS Manager");
        setContentPane(panel1);
        Toolkit tk = this.getToolkit();// 得到窗口工具条
        int width = 600;
        int height = 500;
        Dimension dm = tk.getScreenSize();
        setSize(width, height);// 设置程序的大小
        setLocation((int) (dm.getWidth() - width) / 2,
                (int) (dm.getHeight() - height) / 2);// 显示在屏幕中央

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         model = new DefaultTableModel(//
                new Object[][] {  },// 数据
                new Object[] { "","Company", "Public","Private key" } // 表头
        );
        table1.setModel(model);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.setRowHeight(25);
        JTableHeader tableHeader = table1.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 30));
        tc = table1.getColumnModel().getColumn(0);
        tc.setCellEditor(table1.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table1.getDefaultRenderer(Boolean.class));
        tc.setMaxWidth(20);
        tc.setMinWidth(20);
        tc.setPreferredWidth(20);
        tc.setResizable(false);
        //btDelete.setVisible(false);
        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               // JOptionPane.showMessageDialog(null, "在对话框内显示的描述性的文字", "标题", JOptionPane.ERROR_MESSAGE);
                OpenDNSForm();
            }
        });
        btDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--)
                {
                    if(model.getValueAt(i,0).toString()=="true")
                    {
                        model.removeRow(i);
                    }
                }
                //table1.setModel(model);
            }
        });
        btConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                tc.setMaxWidth(20);
//                tc.setMinWidth(20);
//                tc.setPreferredWidth(20);
//                tc.setResizable(false);
//                btDelete.setVisible(true);
                int selectedRow = table1.getSelectedRow();//获得选中行的索引
                if(selectedRow!= -1)   //是否存在选中行
                {
                    OpenRecordForm(model.getValueAt(selectedRow,1).toString());
                    //修改指定的值：
                   // tableModel.setValueAt(aTextField.getText(), selectedRow, 0);
                   // tableModel.setValueAt(bTextField.getText(), selectedRow, 1);
                    //table.setValueAt(arg0, arg1, arg2)
                }
            }
        });
    }
    private void OpenDNSForm(){
        DNSProviderForm dnsFm=new DNSProviderForm(this);
        dnsFm.setVisible(true);
        getContentPane().add(dnsFm);
    }
    private void OpenRecordForm(String company){
        RecordForm recordFm=new RecordForm(this,company);
        recordFm.setVisible(true);
        getContentPane().add(recordFm);
    }
    public void AddRow(String company,String publicKey,String privateKy){
        model.addRow(new Object[]{false,company,publicKey,privateKy});
        table1.setModel(model);
    }
}

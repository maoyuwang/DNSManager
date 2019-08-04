import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        Toolkit tk = this.getToolkit();// get the menu bar
        int width = 600;
        int height = 500;
        Dimension dm = tk.getScreenSize();
        setSize(width, height);// set the size of GUI
        setLocation((int) (dm.getWidth() - width) / 2,
                (int) (dm.getHeight() - height) / 2);// show in the middle of screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model = new DefaultTableModel(//
                new Object[][]{},// data
                new Object[]{"", "Company", "Public", "Private key"} // name of values
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
                // JOptionPane.showMessageDialog(null, "comments", "title", JOptionPane.ERROR_MESSAGE);
                OpenDNSForm();
            }
        });
        btDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int rowCount = model.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    if (model.getValueAt(i, 0).toString() == "true") {
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
                int selectedRow = table1.getSelectedRow();//get the index of chosen line
                if (selectedRow != -1)   //whether choose the particular line
                {
                    OpenRecordForm(model.getValueAt(selectedRow, 1).toString());
                    //修改指定的值：
                    // tableModel.setValueAt(aTextField.getText(), selectedRow, 0);
                    // tableModel.setValueAt(bTextField.getText(), selectedRow, 1);
                    //table.setValueAt(arg0, arg1, arg2)
                }
            }
        });
    }

    private void OpenDNSForm() {
        DNSProviderForm dnsFm = new DNSProviderForm(this);
        dnsFm.setVisible(true);
        getContentPane().add(dnsFm);
    }

    private void OpenRecordForm(String company) {
        RecordForm recordFm = new RecordForm(this, company);
        recordFm.setVisible(true);
        getContentPane().add(recordFm);
    }

    public void AddRow(String company, String publicKey, String privateKy) {
        model.addRow(new Object[]{false, company, publicKey, privateKy});
        table1.setModel(model);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel1.add(panel3, BorderLayout.SOUTH);
        btAdd = new JButton();
        btAdd.setHorizontalAlignment(0);
        btAdd.setHorizontalTextPosition(0);
        btAdd.setLabel("Add");
        btAdd.setText("Add");
        panel3.add(btAdd);
        btConfig = new JButton();
        btConfig.setLabel("Config");
        btConfig.setText("Config");
        panel3.add(btConfig);
        btDelete = new JButton();
        btDelete.setLabel("Delete");
        btDelete.setText("Delete");
        panel3.add(btDelete);
        spanel1 = new JScrollPane();
        panel1.add(spanel1, BorderLayout.CENTER);
        table1 = new JTable();
        spanel1.setViewportView(table1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}

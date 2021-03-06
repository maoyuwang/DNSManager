import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * class to deal with records for a specific company
 * @author rwu
 *
 */
public class RecordForm extends JDialog {
    public JPanel panel1;
    private JPanel panel3;
    private JButton btAdd;
    private JScrollPane spanel1;
    private JTable table1;
    private DefaultTableModel model;
    private TableColumn tc;
    private config conf;
    private MainForm mainFm;
    public DNSProvider dp;

    /**
     * default constructor
     */
    public RecordForm() {
        init();
    }

    /**
     * constructor
     * @param _mainFm main form 
     * @param _conf informations for the company
     */
    public RecordForm(MainForm _mainFm, config _conf) {
        this.mainFm = _mainFm;
        this.conf = _conf;
        init();
    }

    /**
     * open frame for adding a new record
     */
    private void OpenNewRecordForm() {
        NewRecordForm newRecordForm = new NewRecordForm(this);
        newRecordForm.setVisible(true);
        getContentPane().add(newRecordForm);
    }

    /**
     * show the new record in the frame 
     * @param _record the new record need to be added
     */
    public void AddRow(Record _record) {
        model.addRow(new Object[]{_record.domain, _record.type, _record.name, _record.value, "Edit", "Delete"});
        dp.addRecord(_record);
        table1.setModel(model);
    }

    /**
     * update a exist record
     * @param rec the record that will be update
     * @param row which row the record is on
     */
    public void UpdateRecord(Record rec, int row) {
        model.setValueAt(rec.domain, row, 0);
        model.setValueAt(rec.type, row, 1);
        model.setValueAt(rec.name, row, 2);
        model.setValueAt(rec.value, row, 3);
        dp.updateRecord(rec);
    }

    /**
     * method to delete a row
     * @param row row to delete
     */
    public void DeleteRow(int row) {
        
        System.out.println(">>>>>>DeleteRow");
        Record tmp_r = new Record(model.getValueAt(row, 0).toString(), model.getValueAt(row, 1).toString(), model.getValueAt(row, 2).toString(), model.getValueAt(row, 3).toString());
        System.out.println(tmp_r.toString() );
        dp.deleteRecord( tmp_r );
        model.removeRow(row);
    }

    private void init() {
        setTitle("Domain Manager-" + conf.getName());
        setContentPane(panel1);
        setModal(true);
        //getRootPane().setDefaultButton(buttonOK);
        int Width = 800;
        int Height = 600;
        // setSize(Width,Height);
        Dimension dim = new Dimension(Width, Height);
        setMaximumSize(dim);
        setMinimumSize(dim);
        setLocationRelativeTo(this.getOwner());
        model = new DefaultTableModel(//
                new Object[][]{},//data
                new Object[]{"Domain", "Type", "Name", "Value", "Edit", "Delete"} // name of value
        );
        table1.setModel(model);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.setRowHeight(25);
        JTableHeader tableHeader = table1.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 30));
        FillingRecord(conf.getName());
        ButtonColumn editButtonColumn = new ButtonColumn(this, table1, 4);
        ButtonColumn deleteButtonColumn = new ButtonColumn(this, table1, 5);
        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // JOptionPane.showMessageDialog(null, "comments", "title", JOptionPane.ERROR_MESSAGE);
                OpenNewRecordForm();
            }
        });
    }

    /**
     * find records for the selected company
     * @param name name of the company
     */
    private void FillingRecord(String name) {
        switch (name) {
            case "CloudFlare":
                dp = new CloudFlare(conf.getPublicKey(), conf.getPrivateKey());
                break;
            case "DigitalOcean":
                dp = new DigitalOcean(conf.getPublicKey(), conf.getPrivateKey());
                break;
            case "GoDaddy":
                dp = new GoDaddy(conf.getPublicKey(), conf.getPrivateKey());
                break;
            case "NameCheap":
                dp = new NameCheap(conf.getPublicKey(), conf.getPrivateKey());
                break;
            case "NameSilo":
                dp = new NameSilo(conf.getPublicKey(), conf.getPrivateKey());
                break;
            case "Name.com":
                dp = new NameCom(conf.getPublicKey(), conf.getPrivateKey());
                break;
            case "Gandi":
                dp = new Gandi(conf.getPublicKey(), conf.getPrivateKey());
                break;
            default:
                dp = null;
                break;
        }
        if (dp != null) {
            Record[] records = dp.getRecords();
            for (Record rc : records) {
                AddRow(rc);
            }
        }

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
        Font btAddFont = this.$$$getFont$$$("Microsoft YaHei UI", -1, 12, btAdd.getFont());
        if (btAddFont != null) btAdd.setFont(btAddFont);
        btAdd.setHideActionText(true);
        btAdd.setHorizontalAlignment(0);
        btAdd.setHorizontalTextPosition(0);
        btAdd.setLabel("Add");
        btAdd.setText("Add");
        panel3.add(btAdd);
        spanel1 = new JScrollPane();
        panel1.add(spanel1, BorderLayout.CENTER);
        table1 = new JTable();
        table1.setFillsViewportHeight(false);
        Font table1Font = this.$$$getFont$$$("Microsoft YaHei UI", -1, 12, table1.getFont());
        if (table1Font != null) table1.setFont(table1Font);
        spanel1.setViewportView(table1);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * Return the Root Component.
     * @return The Root Component.
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }


}

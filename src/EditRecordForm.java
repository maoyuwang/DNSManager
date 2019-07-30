import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditRecordForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tFName;
    private JTextField tFType;
    private JTextField tFValue;
    private JTextField tFTTL;
    private RecordForm reCordFm;
    private int index;
    public EditRecordForm() {
        init();
    }
    public EditRecordForm(RecordForm _reCordFm,int _index,String name,String type,String value,String ttl){
        this.reCordFm =_reCordFm;
        this.index=_index;
        this.tFName.setText(name);
        this.tFType.setText(type);
        this.tFValue.setText(value);
        this.tFTTL.setText(ttl);
        init();
    }
    private void onOK() {
        // add your code here
        reCordFm.EditRow(tFName.getText(),index,0);
        reCordFm.EditRow(tFType.getText(),index,1);
        reCordFm.EditRow(tFValue.getText(),index,2);
        reCordFm.EditRow(tFTTL.getText(),index,3);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    private void init(){
        setContentPane(contentPane);
        setModal(true);
        setTitle("Edit Domain");
        getRootPane().setDefaultButton(buttonOK);
        int Width=300;
        int Height=200;
        // setSize(Width,Height);
        Dimension dim =new Dimension(Width,Height);
        setMaximumSize(dim);
        setMinimumSize(dim);
        setLocationRelativeTo(this.getOwner());
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

}

import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NewRecordForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tFName;
    private JTextField tFType;
    private JTextField tFValue;
    private JTextField tFTTL;
    private RecordForm mainFm;
    public NewRecordForm() {
        init();
    }
    public NewRecordForm(RecordForm _mainFm){
        this.mainFm=_mainFm;
        init();
    }
    private void onOK() {
        // add your code here
        mainFm.AddRow(tFName.getText(),tFType.getText(),tFValue.getText(),tFTTL.getText());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    private void init(){
        setContentPane(contentPane);
        setModal(true);
        setTitle("New Domain");
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

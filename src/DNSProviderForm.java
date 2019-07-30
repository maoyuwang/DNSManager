import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DNSProviderForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tFCompany;
    private JTextField tFPubKey;
    private JTextField tFPrivKey;
    private MainForm mainFm;
    public DNSProviderForm() {
        init();
    }
    public DNSProviderForm(MainForm _mainFm){
        this.mainFm=_mainFm;
        init();
    }
    private void onOK() {
        // add your code here
        mainFm.AddRow(tFCompany.getText(),tFPubKey.getText(),tFPrivKey.getText());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

//    private void createUIComponents() {
//        // TODO: place custom component creation code here
//    }
    private void init(){
        setContentPane(contentPane);
        setModal(true);
        setTitle("New DNS Provider");
        getRootPane().setDefaultButton(buttonOK);
        int Width=300;
        int Height=180;
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

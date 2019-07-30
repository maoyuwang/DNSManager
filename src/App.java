import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class App {

    public static void main(String[] args) {
/*        JFrame frame = new JFrame("DNS Manager");
        frame.setVisible(true);
        frame.setContentPane(new MainForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(400, 400, 800, 600);//父窗口的坐标和大小*/
        MainForm mainForm=new MainForm();
        mainForm.setVisible(true);
    }
}

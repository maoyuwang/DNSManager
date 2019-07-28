import javax.swing.*;
/* Main App Frame */
public class AppFrame extends JFrame {
    AppMenuBar menubar;
    AppFrame(){
        setJMenuBar(menubar);
        pack();
        setSize(400,300);
        setVisible(true);
    }
}

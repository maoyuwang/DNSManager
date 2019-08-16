import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


/** 
 * class for configuration panel
 * available DNSManager:
 * 1)CloudFlare
 * 2)DigitalOcean
 * 3)Godaddy
 * 4)NameCheap
 * 5)NameSilo
 * 6)Name.com
 * 7)Gandi
 * @author rwu
 *
 */
public class Configuration extends JFrame {
	public JPanel panel1;
	public String[] provider_name = {"CloudFlare","DigitalOcean","Godaddy","NameCheap","NameSilo","Name.com","Gandi"};
	public ArrayList<config> provider_list;
	public ArrayList<JPanel> panel_list; 
	
	/**
	 * Set each small panel for each dns provider
	 * @param provider_name	name of each provider
	 * @return the panel for each DNS Provider
	 */
	public JPanel setEach(String provider_name) {
		JPanel return_p = new JPanel();
		return_p.setLayout(new BoxLayout(return_p, BoxLayout.Y_AXIS));
		JPanel name = new JPanel();
		name.setLayout(new BorderLayout());
		JCheckBox name_checkbox = new JCheckBox(provider_name);
		name_checkbox.setFont (new java.awt.Font("Berlin Sans FB", java.awt.Font.BOLD, 22));
		
		name_checkbox.setSelected(false);
		name.add(name_checkbox,BorderLayout.WEST);
		return_p.add(name);
		//return_p.add(Box.createVerticalStrut(50));
		JPanel key = new JPanel();
		key.setLayout(new BoxLayout(key, BoxLayout.X_AXIS));
		JLabel publickey_label = new JLabel("PublicKey: ", JLabel.TRAILING);
		key.add(publickey_label);
		JTextArea publickey_text = new JTextArea(10,5);
	    key.add(publickey_text);
	    key.add(Box.createVerticalStrut(20));
	    JLabel privatekey_label = new JLabel("PrivateKey: ", JLabel.TRAILING);
	    key.add(privatekey_label);
	    JTextArea privatekey_text = new JTextArea(10,5);
	    key.add(privatekey_text);
		return_p.add(key);
		
		name_checkbox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                JCheckBox checkBox = (JCheckBox) e.getSource();
                System.out.println(checkBox.getText() + " whether selected or not: " + checkBox.isSelected());
                
            }
        });
	
		return return_p;

	}

	/**
	 * default constructor
	 */
    public Configuration() {
    	provider_list = new ArrayList<config>();
    	panel_list = new ArrayList<JPanel>();
    	for(int i=0; i<provider_name.length;i++) {
    		config dp = new config("","",provider_name[i]);
    		provider_list.add(dp);
    	}
    	setTitle("DNS Manager");
    	panel1 = new JPanel();
    	panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        setContentPane(panel1);
        Toolkit tk = this.getToolkit();// get the menu bar
        int width = 1000;
        int height = 800;
        Dimension dm = tk.getScreenSize();
        setSize(width, height);// set the size of GUI
        setLocation((int) (dm.getWidth() - width) / 2,
                (int) (dm.getHeight() - height) / 2);// show in the middle of screen
        for(int i=0; i<provider_name.length;i++) {
        	JPanel each = setEach(provider_name[i]);
        	panel_list.add(i,each);
        	panel1.add(panel_list.get(i));
        	panel1.add(Box.createVerticalStrut(40));
        }
    	JPanel button = new JPanel();
		button.setLayout(new BoxLayout(button, BoxLayout.X_AXIS));
		JButton save = new JButton("Save");
		JButton cancel = new JButton("cancel");
		button.add(save);
		save.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            	for(int i=0; i<panel_list.size();i++) {
            		JPanel check = (JPanel)panel_list.get(i).getComponent(0);
            		JCheckBox c = (JCheckBox)check.getComponent(0);
            		if(c.isSelected()) {
            			System.out.println(c.getText()+" is selected");
            			JPanel key_panel = (JPanel)panel_list.get(i).getComponent(1);
            			JTextArea public_text = (JTextArea)key_panel.getComponent(1);
            			String public_string = public_text.getText();
            			System.out.println("the public key: "+public_string);
            			JTextArea private_text = (JTextArea)key_panel.getComponent(4);
            			String private_string = private_text.getText();
            			System.out.println("the private key is: "+private_string);
            			if(public_string.contentEquals("")) {
            				JOptionPane.showMessageDialog(null, "Please enter the public key for "+c.getText()+"!", "ERROR",JOptionPane.WARNING_MESSAGE);  
            			}
            			else if(private_string.contentEquals("")) {
            				JOptionPane.showMessageDialog(null, "Please enter the private key for "+c.getText()+"!", "ERROR",JOptionPane.WARNING_MESSAGE);  
            			}
            			else {
            				for(int j=0; j<provider_list.size();j++) {
            					if(provider_list.get(j).getName().contentEquals(c.getText())) {
            						provider_list.get(j).setPublicKey(public_string);
            						provider_list.get(j).setPrivateKey(private_string);
            					}
            				}
            			}
            		
            		}
            	}
            }
                        
        });
		button.add(cancel);
		cancel.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            	 setVisible(false);
            }
                        
        });
		panel1.add(button);
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
//    public static void main(String[] args) {
//    	Configuration panel = new Configuration();
//    	panel.setVisible(true);
//    }
}

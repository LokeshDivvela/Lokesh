import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class UserRegister extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JTextField addressField;
	private JTextField phoneField;


	/**
	 * Create the frame.
	 */
	public UserRegister() {
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(200, 152, 100));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJobApplicationPortal = new JLabel("Token Dispenser !!");
		lblJobApplicationPortal.setHorizontalAlignment(SwingConstants.CENTER);
		lblJobApplicationPortal.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 40));
		lblJobApplicationPortal.setBounds(0, 45, 1000, 48);
		contentPane.add(lblJobApplicationPortal);
		
		nameField = new JTextField();
		nameField.setBounds(472, 148, 275, 48);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(472, 227, 275, 48);
		contentPane.add(emailField);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(280, 156, 115, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(280, 227, 115, 32);
		contentPane.add(lblEmail);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(472, 306, 275, 48);
		contentPane.add(passwordField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblPassword.setBounds(280, 306, 115, 32);
		contentPane.add(lblPassword);
		
		JLabel lblState = new JLabel("Phone");
		lblState.setHorizontalAlignment(SwingConstants.CENTER);
		lblState.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblState.setBounds(280, 384, 115, 32);
		contentPane.add(lblState);
		
		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(472, 454, 275, 48);
		contentPane.add(addressField);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddress.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblAddress.setBounds(280, 459, 115, 32);
		contentPane.add(lblAddress);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String email = emailField.getText();
				String password = new String(passwordField.getPassword());
				String phone = phoneField.getText();
				String address = addressField.getText();
				if(name.equals("") || email.equals("") || password.equals("")  || phone.equals("") 
						 || address.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill all the details");
				}else {
					BookHubImpl obj = new BookHubImpl();
					if(obj.add(name, email, password, phone, address, 2)) {
						dispose();
						BookHubMain book = new BookHubMain();
						book.frame.setVisible(true);
					}
					
				}
			}
		});
		btnNewButton.setBounds(395, 575, 207, 42);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Register");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(425, 95, 141, 25);
		contentPane.add(lblNewLabel_1);
		
		phoneField = new JTextField();
		phoneField.setColumns(10);
		phoneField.setBounds(472, 379, 275, 48);
		contentPane.add(phoneField);
		
		JButton btnNewButton_1 = new JButton("<-back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				BookHubMain obj = new BookHubMain();
				obj.frame.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(33, 6, 117, 42);
		contentPane.add(btnNewButton_1);
	}


}

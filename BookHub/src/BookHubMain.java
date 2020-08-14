import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class BookHubMain {

	public JFrame frame;
	private JTextField txtUsername;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		BookHubImpl obj = new BookHubImpl();
		obj.initializeDb();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookHubMain window = new BookHubMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookHubMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(200, 100, 100));
		frame.getContentPane().setLayout(null);
		
		JLabel lblJobApplicationPortal = new JLabel("BookHub Token Dispenser !!");
		lblJobApplicationPortal.setHorizontalAlignment(SwingConstants.CENTER);
		lblJobApplicationPortal.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 40));
		lblJobApplicationPortal.setBounds(0, 38, 1000, 48);
		frame.getContentPane().add(lblJobApplicationPortal);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(0, 124, 1000, 48);
		frame.getContentPane().add(lblLogin);
		
		txtUsername = new JTextField();
		txtUsername.setToolTipText("Email");
		txtUsername.setBounds(333, 227, 401, 48);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Email");
		lblUsername.setBounds(342, 200, 85, 24);
		frame.getContentPane().add(lblUsername);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(333, 337, 401, 46);
		frame.getContentPane().add(passwordField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(342, 307, 61, 16);
		frame.getContentPane().add(lblPassword);
		
		JButton btnNewButton = new JButton("Sign In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = txtUsername.getText();
				String password = new String(passwordField.getPassword());
				if(!email.equals("") && !password.equals("")) {
					BookHubImpl obj = new BookHubImpl();
					if(obj.login(email, password)) {
						frame.dispose();
						if(obj.loggedInUserGroup.equals("admin")) {
							AdminDashboard admin = new AdminDashboard();
							admin.setVisible(true);
						}else {
							UserDashboard user = new UserDashboard();
							user.setVisible(true);
						}
					}else {
						JOptionPane.showMessageDialog(null, "OOPS ! Incorrect Credentials");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Please fill in all the details.");
				}
			}
		});
		btnNewButton.setBounds(425, 441, 214, 55);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Dont have an account ? SIGN UP -->");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblNewLabel.setBounds(224, 583, 448, 40);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Sign Up");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				UserRegister user = new UserRegister();
				user.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(684, 587, 131, 40);
		frame.getContentPane().add(btnNewButton_1);
		
		
		
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class UserDashboard extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public UserDashboard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(202, 102, 155));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJobApplicationPortal = new JLabel("Token Dispenser !!");
		lblJobApplicationPortal.setHorizontalAlignment(SwingConstants.CENTER);
		lblJobApplicationPortal.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 40));
		lblJobApplicationPortal.setBounds(6, 48, 1000, 48);
		contentPane.add(lblJobApplicationPortal);
		
		JLabel lblNewLabel = new JLabel("Hi User !. Welcome");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblNewLabel.setBounds(371, 99, 274, 28);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				BookHubMain obj = new BookHubMain();
				obj.frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(849, 6, 117, 41);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Place an order");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ManageBooks manage = new ManageBooks("user");
				manage.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnNewButton_1.setBounds(236, 245, 200, 200);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("View Orders");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ViewBookings view = new ViewBookings("user");
				view.setVisible(true);
			}
		});
		btnNewButton_1_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnNewButton_1_1.setBounds(578, 245, 200, 200);
		contentPane.add(btnNewButton_1_1);
	}

}

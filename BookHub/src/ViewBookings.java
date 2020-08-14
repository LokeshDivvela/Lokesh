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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ViewBookings extends JFrame {

	private JPanel contentPane;
	private JTable table;

	
	/**
	 * Create the frame.
	 */
	public ViewBookings(String userGroup) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(202, 102, 155));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJobApplicationPortal = new JLabel("Token Dispenser !!");
		lblJobApplicationPortal.setBounds(0, 43, 1000, 48);
		lblJobApplicationPortal.setHorizontalAlignment(SwingConstants.CENTER);
		lblJobApplicationPortal.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 40));
		contentPane.add(lblJobApplicationPortal);
		
		JButton btnNewButton = new JButton("<-back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				if(userGroup.equals("admin")) {
					AdminDashboard admin = new AdminDashboard();
					admin.setVisible(true);
				}else {
					UserDashboard user = new UserDashboard();
					user.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(32, 6, 117, 37);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 183, 913, 443);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		if(userGroup.equals("admin")) {
			refreshtable();
		}else {
			BookHubImpl book = new BookHubImpl();
			refreshtable(book.loggedInUserId);
		}
		
		JLabel lblNewLabel = new JLabel("All Tokens");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setBounds(336, 114, 317, 32);
		contentPane.add(lblNewLabel);
	}
	
	public void refreshtable(String userID) {
		BookHubImpl book = new BookHubImpl();	
		table.setModel(book.getBookingsDataWithId(Integer.parseInt(userID)));
	}
	
	public void refreshtable() {
		BookHubImpl book = new BookHubImpl();		
		table.setModel(book.getBookingsData());
	}
}

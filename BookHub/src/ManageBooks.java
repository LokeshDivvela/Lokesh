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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ManageBooks extends JFrame {

	private JPanel contentPane;
	private JTable table;


	/**
	 * Create the frame.
	 */
	public ManageBooks(String userGroup) {
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
		lblJobApplicationPortal.setBounds(0, 51, 1000, 48);
		contentPane.add(lblJobApplicationPortal);
		
		JButton button = new JButton("<-back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userGroup.equals("admin")) {
					dispose();
					AdminDashboard admin = new AdminDashboard();
					admin.setVisible(true);
				}else {
					dispose();
					UserDashboard user = new UserDashboard();
					user.setVisible(true);
					
				}
			}
		});
		button.setBounds(25, 6, 117, 29);
		contentPane.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 227, 950, 405);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		if(userGroup.equals("admin")) {
			refreshtableForAdmin();
		}else {
			refreshtableForUser();
		}
		
		if(userGroup.equals("admin")) {
			JButton btnAdd = new JButton("Add");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					AddBooks add = new AddBooks("");
					add.setVisible(true);
				}
			});
			btnAdd.setBounds(25, 170, 117, 40);
			contentPane.add(btnAdd);
			
			JButton btnUpdate = new JButton("Update");
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						int column = 0;
						int row = table.getSelectedRow();
						String value = table.getModel().getValueAt(row, column).toString();
						System.out.println(value);
						dispose();
						AddBooks add = new AddBooks(value);
						add.setVisible(true);
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Please select a row to update !.");
					}
				}
			});
			btnUpdate.setBounds(706, 168, 117, 40);
			contentPane.add(btnUpdate);
			
			JButton btnDelete = new JButton("Delete");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						int column = 0;
						int row = table.getSelectedRow();
						String value = table.getModel().getValueAt(row, column).toString();
						BookHubImpl book = new BookHubImpl();
						if(book.deleteBookDataWithId(Integer.parseInt(value))) {
							refreshtableForAdmin();
						}
						
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Please select a row to delete !.");
					}
				}
			});
			btnDelete.setBounds(855, 167, 117, 40);
			contentPane.add(btnDelete);
		}else {
			JButton btnDelete = new JButton("Book");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						int column = 0;
						int row = table.getSelectedRow();
						String value = table.getModel().getValueAt(row, column).toString();
						String quant = JOptionPane.showInputDialog(null, "Enter number of books");
						int quantity = 0;
						if(!quant.equals("")) {
							quantity = Integer.parseInt(quant);
						}
						if(quantity>0) {
							int stock = Integer.parseInt(table.getModel().getValueAt(row, 6).toString());
							if(quantity<=stock) {
								BookHubImpl book = new BookHubImpl();
								int totalPrise = quantity*(Integer.parseInt(table.getModel().getValueAt(row, 5).toString()));
								if(book.userBooking(Integer.parseInt(value), Integer.parseInt(book.loggedInUserId), 
										quantity, totalPrise, (stock-quantity),Integer.parseInt(value))) {
									String message = "Booked successfully!.\n Your total prise is "+totalPrise;
									
									JOptionPane.showMessageDialog(null, message );
									refreshtableForUser();
								}
							}else {
								JOptionPane.showMessageDialog(null, "Number of books should not exceed the stock.\nPlese enter correct value and book again.");
							}
							
						}else {
							JOptionPane.showMessageDialog(null, "Plese enter correct value and book again.");
						}
						
						
					} catch (Exception e2) {
						System.out.println(e2);
						JOptionPane.showMessageDialog(null, "Please select a row to Book !.");
					}
				}
			});
			btnDelete.setBounds(855, 167, 117, 40);
			contentPane.add(btnDelete);
		}
	}
	
	public void refreshtableForAdmin() {
		BookHubImpl book = new BookHubImpl();		
		table.setModel(book.getBooksDataForAdmin());
	}
	
	public void refreshtableForUser() {
		BookHubImpl book = new BookHubImpl();		
		table.setModel(book.getBooksDataForUsers());
	}

}

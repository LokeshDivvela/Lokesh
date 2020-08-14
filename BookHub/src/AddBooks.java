import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AddBooks extends JFrame {

	private JPanel contentPane;
	private JTextField titleField;
	private JTextField authorField;
	private JTextField descField;
	private JTextField typeField;
	private JTextField costField;
	private JTextField stockField;

	/**
	 * Create the frame.
	 */
	public AddBooks(String value) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(202, 102, 155));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJobApplicationPortal = new JLabel("BOOK HUB");
		lblJobApplicationPortal.setHorizontalAlignment(SwingConstants.CENTER);
		lblJobApplicationPortal.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 40));
		lblJobApplicationPortal.setBounds(0, 51, 1000, 48);
		contentPane.add(lblJobApplicationPortal);
		
		titleField = new JTextField();
		titleField.setBounds(527, 166, 238, 35);
		contentPane.add(titleField);
		titleField.setColumns(10);
		
		authorField = new JTextField();
		authorField.setColumns(10);
		authorField.setBounds(527, 223, 238, 35);
		contentPane.add(authorField);
		
		descField = new JTextField();
		descField.setColumns(10);
		descField.setBounds(527, 273, 238, 35);
		contentPane.add(descField);
		
		typeField = new JTextField();
		typeField.setColumns(10);
		typeField.setBounds(527, 329, 238, 35);
		contentPane.add(typeField);
		
		costField = new JTextField();
		costField.setColumns(10);
		costField.setBounds(527, 386, 238, 35);
		contentPane.add(costField);
		
		stockField = new JTextField();
		stockField.setColumns(10);
		stockField.setBounds(527, 439, 238, 35);
		contentPane.add(stockField);
		
		JLabel lblNewLabel = new JLabel("Title");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblNewLabel.setBounds(215, 166, 300, 35);
		contentPane.add(lblNewLabel);
		
		JLabel lblSource = new JLabel("Author");
		lblSource.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblSource.setBounds(215, 223, 300, 35);
		contentPane.add(lblSource);
		
		JLabel lblDestination = new JLabel("Description");
		lblDestination.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblDestination.setBounds(215, 273, 300, 35);
		contentPane.add(lblDestination);
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblType.setBounds(215, 329, 300, 35);
		contentPane.add(lblType);
		
		JLabel lblDepartureddmmyyyhhmm = new JLabel("Cost");
		lblDepartureddmmyyyhhmm.setFont(new Font("Comic Sans MSe", Font.PLAIN, 20));
		lblDepartureddmmyyyhhmm.setBounds(215, 386, 300, 35);
		contentPane.add(lblDepartureddmmyyyhhmm);
		
		JLabel lblArrivalddmmyyyhhmm = new JLabel("Stock");
		lblArrivalddmmyyyhhmm.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblArrivalddmmyyyhhmm.setBounds(215, 439, 300, 35);
		contentPane.add(lblArrivalddmmyyyhhmm);
		
		if(value.equals("")) {
			JButton btnNewButton = new JButton("Add Books");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String title = titleField.getText();
					String author = authorField.getText();
					String description = descField.getText();
					String type = typeField.getText();
					String cost = costField.getText();
					String stock = stockField.getText();
					if(title.equals("") && author.equals("") && description.equals("") && type.equals("") && cost.equals("") && stock.equals("")) {
						JOptionPane.showMessageDialog(null, "Please fill all the fields.!");
					}else {
						BookHubImpl book = new BookHubImpl();
						if(book.add(title, author, description, type, Integer.parseInt(cost), Integer.parseInt(stock))) {
							JOptionPane.showMessageDialog(null, "Successfully added.!");
							titleField.setText("");
							authorField.setText("");
							descField.setText("");
							typeField.setText("");
							costField.setText("");
							stockField.setText("");
						}
					}
				}
			});
			btnNewButton.setBounds(421, 588, 211, 41);
			contentPane.add(btnNewButton);
		}else {
			BookHubImpl book = new BookHubImpl();
			Map<String, String> map = book.getBookDataWithId(Integer.parseInt(value));
			titleField.setText(map.get("title"));
			authorField.setText(map.get("author"));
			descField.setText(map.get("description"));
			typeField.setText(map.get("type"));
			costField.setText(map.get("cost"));
			stockField.setText(map.get("stock"));
			
			JButton btnNewButton = new JButton("Update Book");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String title = titleField.getText();
					String author = authorField.getText();
					String description = descField.getText();
					String type = typeField.getText();
					String cost = costField.getText();
					String stock = stockField.getText();
					if(title.equals("") && author.equals("") && description.equals("") && type.equals("") && cost.equals("") && stock.equals("")) {
						JOptionPane.showMessageDialog(null, "Please fill all the fields.!");
					}else {
						BookHubImpl book = new BookHubImpl();
						if(book.updateBookData(Integer.parseInt(value), title, author, description, type, Integer.parseInt(cost), Integer.parseInt(stock))) {
							JOptionPane.showMessageDialog(null, "Successfully updated.!");
							dispose();
							ManageBooks manage = new ManageBooks("admin");
							manage.setVisible(true);
						}
					}
				}
			});
			btnNewButton.setBounds(421, 588, 211, 41);
			contentPane.add(btnNewButton);
		}
		
		JButton button = new JButton("<-back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ManageBooks manage = new ManageBooks("admin");
				manage.setVisible(true);
			}
		});
		button.setBounds(37, 18, 117, 29);
		contentPane.add(button);
	}

}

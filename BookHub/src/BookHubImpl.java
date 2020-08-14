import java.util.Map;

import javax.swing.table.TableModel;

// Inheritance
public class BookHubImpl extends SecurityManager {
	DatabaseManager dbm = new DatabaseManager();
	String loggedInUserName = "";
	String loggedInUserGroup = "";
	static String loggedInUserId = "";
	
	
	public void initializeDb() {
		dbm.checkAndCreateTables();
		dbm.checkAndInsertIntoUsergroupTable("admin");
		dbm.checkAndInsertIntoUsergroupTable("user");
		
		// create admin
		checkAndAddIntoUsersTable("admin", "admin@admin.com", "admin123", "", "", 1);
		
	}
	
	public void checkAndAddIntoUsersTable(String name, String email, String password, String phone, String address, int groupId) {
		
		String hashedPassword = encryptPassword(password);
		
		dbm.checkAndInsertIntoUsersTable(name, email, hashedPassword, phone, address, groupId);
		
	}
	
	//Method overloading
	
	public boolean add(String name, String email, String password, String phone, String address, int groupId) {
		
		String hashedPassword = encryptPassword(password);
		
		return dbm.insertIntoUsersTable(name, email, hashedPassword, phone, address, groupId);
		
	}
	
	public boolean add(String title, String author, String description,String type,int cost, int stock) {
		return dbm.insertIntoBooksTable(title, author, description, type, cost, stock);
	}
	
	public boolean login(String email, String password) {
		try {
			
			Map<String, String> map = dbm.getUserDetails(email, password);
			String dbPassword = map.get("password");
			if(!dbPassword.equals("") && dbPassword != null) {
				if(verifyPassword(password, dbPassword)) {
					loggedInUserName = map.get("name");
					loggedInUserId = map.get("id");
					loggedInUserGroup = dbm.getGroupName(Integer.parseInt((map.get("groupId"))));
					return true;
				}
			}else {
				return false;
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return false;
	}
	
	public TableModel getBooksDataForUsers() {
		return dbm.getBookDetailsForUsers();
	}
	
	public TableModel getBooksDataForAdmin() {
		return dbm.getBookDetailsForAdmin();
	}
	
	public Map<String, String> getBookDataWithId(int id){
		return dbm.getBookDetailsWithId(id);
		
	}
	
	public String getEmailWithUserId(int id){
		return dbm.getEmailWithUserId(id);
		
	}
	
	public TableModel getBookingsData() {
		return dbm.getBookingDetails();
	}
	
	public TableModel getBookingsDataWithId(int id) {
		return dbm.getBookingDetailsWithId(id);
	}
	
	public boolean updateBookData(int id, String title, String author, String description,String type,int cost, int stock) {
		return dbm.updateBookData(id, title, author, description, type, cost, stock);
	}
	
	public boolean deleteBookDataWithId(int id) {
		return dbm.deleteBookDataWithId(id);
	}
	
	public boolean userBooking(int bookId, int userId, int numberOfBooks, int price, int updatedStock, int token_no) {
		dbm.updateStockData(bookId, updatedStock);
		if(dbm.insertIntoBookingsTable(bookId, userId, numberOfBooks, price)) {
			Map<String, String> map = getBookDataWithId(bookId);
			String email = getEmailWithUserId(userId);
			String header = "Booking Successful";
			String message = "Book Title : "+map.get("title")+"\nBook Type : "+map.get("type")+"\nNumber of books ordered : "+numberOfBooks+"\nTotal price : "+price;
			
			Mailer mail = new Mailer();
			mail.send(email, header, message);
			
			return true;
		}
		return false;
	}
}

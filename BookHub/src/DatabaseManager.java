import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

public class DatabaseManager {
	
	public static String dbPath = "jdbc:sqlite:bookhub.db";
	
	private Connection connect() {
        // SQLite connection string
		 String url = dbPath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	
	public void checkAndCreateTables() {
        
        // SQL statement for creating a new table
        String userGroupsTable = "CREATE TABLE IF NOT EXISTS usergroups (\n"
        		+ "    id int PRIMARY KEY,\n"
                + "    name varchar(20) NOT NULL\n"
                + ");";
        
        String usersTable = "CREATE TABLE IF NOT EXISTS users (\n"
        		+ "    id int PRIMARY KEY,\n"
                + "    name varchar(20) NOT NULL,\n"
                + "    email varchar(40) NOT NULL,\n"
                + "    password varchar(20) NOT NULL,\n"
                + "    phone varchar(20),\n"
                + "    address varchar(50),\n"
                + "    group_id integer NOT NULL,\n"
                + "    FOREIGN KEY (group_id) REFERENCES usergroups (id) \n"
                + ");";
        
        String booksTable = "CREATE TABLE IF NOT EXISTS currentTokens (\n"
        		+ "    id int PRIMARY KEY,\n"
                + "    name varchar(20) NOT NULL,\n"
                + "    email varchar(20) NOT NULL,\n"
                + "    phone varchar(20) NOT NULL,\n"
                + "    address varchar(20) NOT NULL,\n"
                + ");";
        
        
        String bookingsTable = "CREATE TABLE IF NOT EXISTS tokenHistory (\n"
        		+ "    currentTokens_id int PRIMARY KEY,\n"
                + "    name varchar(20),\n"
                + "    email varchar(40),\n"
                + "    phone int,\n"
                + "    address varchar(50),\n"
                + "    FOREIGN KEY (currentTokens_id) REFERENCES currentTokens (id) \n"
                + "    FOREIGN KEY (name) REFERENCES users (name) \n"
                + ");";
        
        try  (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(userGroupsTable);
            stmt.execute(usersTable);
            stmt.execute(booksTable);
            stmt.execute(bookingsTable);
            
            System.out.println("tables created....");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void checkAndInsertIntoUsergroupTable(String name) {
		
        String sql = "SELECT name from usergroups where name = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1,name);
		     //
		     ResultSet rs   = pstmt.executeQuery();
		     
		     String groupName = "";
		     while(rs.next()) {
		    	 groupName = rs.getString("name");
		     }
		     
		     if(groupName.equals("") || groupName == null) {
		    	 insertIntoUsergroupTable(name);
		     }
		     
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void insertIntoUsergroupTable(String name) {
        String sql = "INSERT INTO usergroups(name) VALUES(?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void checkAndInsertIntoUsersTable(String name, String email, String password, String phone, String address, int groupId) {
		
        String sql = "SELECT name from users where name = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1,name);
		     //
		     ResultSet rs   = pstmt.executeQuery();
		     
		     String userName = "";
		     while(rs.next()) {
		    	 userName = rs.getString("name");
		     }
		     
		     if(userName.equals("") || userName == null) {
		    	 insertIntoUsersTable(name, email, password, phone, address, groupId);
		     }
		     
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
    
	
	public boolean insertIntoUsersTable(String name, String email, String password, String phone, String address, int groupId) {
        String sql = "INSERT INTO users(name,email,password,phone,address,group_id) VALUES(?,?,?,?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, phone);
            pstmt.setString(5, address);
            pstmt.setInt(6, groupId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
	
	public boolean insertIntoBooksTable(String title, String author, String description,String type,int cost, int stock) {
		
        String sql = "INSERT INTO books(title,author,description,type,cost,stock) VALUES(?,?,?,?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3,description);
            pstmt.setString(4,type);
            pstmt.setInt(5, cost);
            pstmt.setInt(6, stock);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
	
	public boolean insertIntoBookingsTable(int bookId, int userId, int numberOfBooks, int price) {
        //String sql1 = "SELECT count(*) from bookings";
        
		//String sql = "INSERT INTO bookings(book_id,user_id,numberofbooks,total_price,token_no) VALUES(?,?,?,?,?)";
		String sql = "INSERT INTO bookings(book_id,user_id,numberofbooks,total_price) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	//ResultSet rs = pstmt.executeQuery(sql1);
        	//rs.next();
        	//int count = rs.getInt(1);
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, numberOfBooks);
            pstmt.setInt(4, price);
            //pstmt.setInt(5, count+1);
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
	
	public Map<String, String> getUserDetails(String username, String password) {
		
		String sql = "SELECT * from users where email = ?";
		
		Map<String, String> map = new HashMap<String, String>();
		
		try (Connection conn = this.connect();
			      PreparedStatement pstmt  = conn.prepareStatement(sql)){
			     
			     // set the value
			     pstmt.setString(1,username);
			     //
			     ResultSet rs   = pstmt.executeQuery();
			     
			    while(rs.next()) {
			    	
			    	map.put("id",Integer.toString(rs.getInt("id")));
			    	map.put("name",rs.getString("name"));
				    map.put("email", rs.getString("email"));
				    map.put("password",rs.getString("password"));
				    map.put("groupId",Integer.toString(rs.getInt("group_id")));
				    
				    break;
			    	
			    }
			     
			     
			 } catch (SQLException e) {
			     System.out.println(e.getMessage());
			 }
		return map;
	}
	
	public String getGroupName(int groupId) {
		String sql = "SELECT name from usergroups where id = ?";
		
		try (Connection conn = this.connect();
			      PreparedStatement pstmt  = conn.prepareStatement(sql)){
			     
			     // set the value
			     pstmt.setInt(1,groupId);
			     //
			     ResultSet rs   = pstmt.executeQuery();
			     
			     while(rs.next()) {
			    	 return rs.getString("name");
			     }
			     
			     
			 } catch (SQLException e) {
			     System.out.println(e.getMessage());
			 }
		return "";
	}
	
	public TableModel getBookDetailsForUsers() {
		String sql = "SELECT * from books where stock>0";
		
		TableModel tableModel = null;
		
		try (Connection conn = this.connect();
			      PreparedStatement pstmt  = conn.prepareStatement(sql)){
			     
			     //
			     ResultSet rs   = pstmt.executeQuery();
			     
			     tableModel = DbUtils.resultSetToTableModel(rs);
			     
			     
			 } catch (SQLException e) {
			     System.out.println(e.getMessage());
			 }
		
		return tableModel;
	}
	
	public TableModel getBookDetailsForUsers2() {
		String sql = "SELECT * from books, bookings";
		
		TableModel tableModel = null;
		
		try (Connection conn = this.connect();
			      PreparedStatement pstmt  = conn.prepareStatement(sql)){
			     
			     //
			     ResultSet rs   = pstmt.executeQuery();
			     
			     tableModel = DbUtils.resultSetToTableModel(rs);
			     
			     
			 } catch (SQLException e) {
			     System.out.println(e.getMessage());
			 }
		
		return tableModel;
	}
	
	
	public TableModel getBookDetailsForAdmin() {
		String sql = "SELECT * from books";
		
		TableModel tableModel = null;
		
		try (Connection conn = this.connect();
			      PreparedStatement pstmt  = conn.prepareStatement(sql)){
			     
			     //
			     ResultSet rs   = pstmt.executeQuery();
			     
			     tableModel = DbUtils.resultSetToTableModel(rs);
			     
			     
			 } catch (SQLException e) {
			     System.out.println(e.getMessage());
			 }
		
		return tableModel;
	}
	
	public Map<String, String> getBookDetailsWithId(int id){
		String sql = "SELECT * from books where id = ?";
		
		Map<String, String> map = new HashMap<String, String>();
		
		try (Connection conn = this.connect();
			      PreparedStatement pstmt  = conn.prepareStatement(sql)){
			     
			     // set the value
			     pstmt.setInt(1,id);
			     //
			     ResultSet rs   = pstmt.executeQuery();
			     
			    while(rs.next()) {
			    	
			    	map.put("title",rs.getString("title"));
			    	map.put("author",rs.getString("author"));
			    	map.put("description",rs.getString("description"));
			    	map.put("type",rs.getString("type"));
				    map.put("cost",Integer.toString(rs.getInt("cost")));
				    map.put("stock",Integer.toString(rs.getInt("stock")));
				    
				    break;
			    	
			    }
			     
			     
			 } catch (SQLException e) {
			     System.out.println(e.getMessage());
			 }
		return map;
	}
	
	public String getEmailWithUserId(int id){
		String sql = "SELECT * from users where id = ?";
		
		String email = "";
		
		try (Connection conn = this.connect();
			      PreparedStatement pstmt  = conn.prepareStatement(sql)){
			     
			     // set the value
			     pstmt.setInt(1,id);
			     //
			     ResultSet rs   = pstmt.executeQuery();
			     
			    while(rs.next()) {
			    	
			    	email = rs.getString("email");
				    
				    break;
			    	
			    }
			     
			     
			 } catch (SQLException e) {
			     System.out.println(e.getMessage());
			 }
		return email;
	}
	
	public boolean updateBookData(int id, String title, String author, String description,String type,int cost, int stock) {
        String sql = "UPDATE books SET title = ? , "
                + "author = ?, "
                + "description = ?, "
                + "type = ?, "
                + "cost = ?, "
                + "stock = ? "
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
        	pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, description);
            pstmt.setString(4, type);
            pstmt.setInt(5, cost);
            pstmt.setInt(6, stock);
            pstmt.setInt(7, id);
            // update 
            pstmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
	
	
	public boolean updateStockData(int id, int stock) {
        String sql = "UPDATE books SET stock = ? "
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setInt(1, stock);
            pstmt.setInt(2, id);
            // update 
            pstmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
	
	public boolean deleteBookDataWithId(int id) {
		String sql = "DELETE FROM books WHERE id = ?";
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return false;
	}
	
	public TableModel getBookingDetails() {
		String sql = "SELECT b.id,b.numberofbooks,b.total_price,g.title,g.author,g.description,u.name,u.email,u.phone,u.address,b.token_no from bookings b inner join books g on b.book_id=g.id inner join users u on b.user_id=u.id";
		
		TableModel tableModel = null;
		
		try (Connection conn = this.connect();
			      PreparedStatement pstmt  = conn.prepareStatement(sql)){
			     
			     //
			     ResultSet rs   = pstmt.executeQuery();
			     
			     tableModel = DbUtils.resultSetToTableModel(rs);
			     
			     
			 } catch (SQLException e) {
			     System.out.println(e.getMessage());
			 }
		
		return tableModel;
	}
	
	public TableModel getBookingDetailsWithId(int id) {
		String sql = "select b.id,b.numberofbooks,b.total_price,g.title,g.author,g.description,g.type,b.token_no from bookings b inner join books g on b.book_id=g.id where b.id in(select id from bookings where user_id=?)";
		
		TableModel tableModel = null;
		
		try (Connection conn = this.connect();
			      PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 	  pstmt.setInt(1, id);
			     //
			     ResultSet rs   = pstmt.executeQuery();
			     
			     tableModel = DbUtils.resultSetToTableModel(rs);
			     
			     
			 } catch (SQLException e) {
			     System.out.println(e.getMessage());
			 }
		
		return tableModel;
	}
	
}


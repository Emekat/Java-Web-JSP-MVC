package booksApp.books.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import booksApp.books.model.Author;
import booksApp.books.model.Book;
import booksApp.books.model.Category;

public class BookDAOImpl implements BookDAO{
	
	
	private Connection getConnection() throws SQLException {
		// Ensure the MySQL driver class is loaded so it registers with DriverManager.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// wrap and rethrow as SQLException so callers only deal with SQLExceptions
			throw new SQLException("MySQL JDBC driver not found in classpath", e);
		}
		
		// Diagnostic: list drivers known to DriverManager
		System.out.println("[DEBUG] Registered JDBC drivers:");
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver d = drivers.nextElement();
			System.out.println("  - " + d.getClass().getName() + " (classloader=" + d.getClass().getClassLoader() + ")");
		}
		
		String url = "jdbc:mysql://localhost:3306/books?useSSL=false&serverTimezone=UTC";
		try {
			Connection conn = DriverManager.getConnection(url, "root", "root");
			return conn;
		} catch (SQLException ex) {
			// Diagnostic: list drivers at failure
			System.out.println("[DEBUG] Connection failed. Drivers currently registered:");
			drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				Driver d = drivers.nextElement();
				System.out.println("  - " + d.getClass().getName() + " (classloader=" + d.getClass().getClassLoader() + ")");
			}
			throw ex;
		}
	}
	
	private void closeConnection(Connection connection) {
		if(connection == null) {
			return;
		}
		try {
			connection.close();
		}catch(SQLException ex) {
			
		}
	}

	// A small wrapper to avoid classloader problems when registering drivers with DriverManager
	private static class DriverShim implements Driver {
		private final Driver driver;
		DriverShim(Driver d) {
			this.driver = d;
		}
		public boolean acceptsURL(String u) throws SQLException { return driver.acceptsURL(u); }
		public Connection connect(String u, java.util.Properties p) throws SQLException { return driver.connect(u, p); }
		public int getMajorVersion() { return driver.getMajorVersion(); }
		public int getMinorVersion() { return driver.getMinorVersion(); }
		public DriverPropertyInfo[] getPropertyInfo(String u, java.util.Properties p) throws SQLException { return driver.getPropertyInfo(u, p); }
		public boolean jdbcCompliant() { return driver.jdbcCompliant(); }
		public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException { return driver.getParentLogger(); }
	}

	@Override
	public List<Book> findAllBooks() {
		List<Book> result = new ArrayList<>();
		List<Author> authorList = new ArrayList<>();
		
		String sql = "select * from BOOK inner join AUTHOR on BOOK.id = AUTHOR.book_id";
		
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
			    Book book = new Book();
			    Author author = new Author();
			    
			    book.setId(resultSet.getLong("id"));
			    book.setBookTitle(resultSet.getString("book_title"));
			    book.setCategoryId(resultSet.getLong("category_id"));
			    
			    author.setBookId(resultSet.getLong("book_Id"));
			    author.setFirstName(resultSet.getString("first_name"));
			    author.setLastName(resultSet.getString("last_name"));
			    
			    authorList.add(author);
			    book.setAuthors(authorList);
			    
			    book.setPublisherName(resultSet.getString("publisher"));
			    result.add(book);
			}
		}catch(SQLException ex) {
			ex.printStackTrace(); 
		}
		finally {
			closeConnection(connection);
		}
		return result;
	}

	@Override
	public List<Book> searchBooksByKeyword(String keyWord) {
		// TODO Auto-generated method stub
		List<Book> result = new ArrayList<>();
		List<Author> authorList = new ArrayList<>();
		
		String sql = "select * from BOOK inner join AUTHOR on BOOK.id = AUTHOR.book_id"
				+ " where book_title like '%"
		    + keyWord.trim()
			+ "%'"
			+ " or first_name like '%"
			+ keyWord.trim()
			+ "%'"
			+ " or last_name like '%" + keyWord.trim() + "%'";
		
		Connection connection = null;
		try {
	
		    connection = getConnection();
		    PreparedStatement statement = connection.prepareStatement(sql);
		    ResultSet resultSet = statement.executeQuery();
		    
		    while (resultSet.next()) {
		    	Book book = new Book();
		    	Author author = new Author();
		    	book.setId(resultSet.getLong("id"));
		    	book.setBookTitle(resultSet.getString("book_title"));
		    	book.setPublisherName(resultSet.getString("publisher"));
		    	
		    	author.setFirstName(resultSet.getString("first_name"));
		    	author.setLastName(resultSet.getString("last_name"));
		    	author.setBookId(resultSet.getLong("book_id"));
		    	authorList.add(author);
			
		    	book.setAuthors(authorList);
		    	result.add(book);
		    	}
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    } finally {
			   closeConnection(connection);
			}
	
		return result;
	}

	@Override
	public List<Category> findAllCategories() {
		// TODO Auto-generated method stub
		List<Category> result = new ArrayList<>();
		String sql = "select * from CATEGORY";
		
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			 ResultSet resultSet = statement.executeQuery();
			 
			 while(resultSet.next()) {
				 Category category = new Category();
				 
				 category.setId(resultSet.getLong("id"));
				 category.setCategoryDescription(resultSet.getString("category_description"));
				 result.add(category);
			 }
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnection(connection);
		}
		return result;
	}

	@Override
	public void insert(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long bookId) {
		// TODO Auto-generated method stub
		
	}

}

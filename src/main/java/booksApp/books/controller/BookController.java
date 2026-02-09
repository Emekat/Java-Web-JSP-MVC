package booksApp.books.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
		
import java.util.ArrayList;
import java.util.List;

import booksApp.books.dao.BookDAO;
import booksApp.books.dao.BookDAOImpl;
import booksApp.books.model.Book;
import booksApp.books.model.Category;


/**
 * Servlet implementation class BookController
 */
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    ServletContext context = config.getServletContext();
	    try {
	        BookDAO bookDao = new BookDAOImpl();
	        // calling DAO method to retrieve bookList from Database
	        List<Category> categoryList = bookDao.findAllCategories();
	        context.setAttribute("categoryList", categoryList);
	    } catch (Exception e) {
	        // Log error but don't prevent servlet from initializing
	        System.err.println("[WARN] Failed to load categories during init: " + e.getMessage());
	        context.setAttribute("categoryList", new ArrayList<Category>());
	    }
	    }
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String base = "/jsp/";
		String url = base + "home.jsp";
		
		String action = request.getParameter("action");
		String category = request.getParameter("category");
		String keyWord = request.getParameter("keyWord");
		
		if (action != null) {
		switch (action) {
		case "allBooks":
	        findAllBooks(request, response);
		    url = base + "listOfBooks.jsp";
		    break;
		case "category":
		    findAllBooks(request, response);
		    url = base + "category.jsp?category=" + category;
		break;
		case "search":
		    searchBooks(request, response, keyWord);
		    url = base + "searchResult.jsp";
		break;
		
		 }
		}
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
		requestDispatcher.forward(request, response);
		
	}
	
	private void findAllBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   try {
			BookDAO bookDao = new BookDAOImpl();
			List<Book> bookList = bookDao.findAllBooks();
			request.setAttribute("bookList", bookList);
			
		} catch (Exception e) {
		  System.out.println(e);
		}
	}
	
	private void searchBooks(HttpServletRequest request, HttpServletResponse response, String keyWord) throws ServletException, IOException {
		   try {
				BookDAO bookDao = new BookDAOImpl();
				List<Book> bookList = bookDao.searchBooksByKeyword(keyWord);
				request.setAttribute("bookList", bookList);
				
			} catch (Exception e) {
			  System.out.println(e);
			}
		}

}
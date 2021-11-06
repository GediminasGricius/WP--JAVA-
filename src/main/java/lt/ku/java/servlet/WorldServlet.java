package lt.ku.java.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lt.ku.java.database.MySQLDatabase;
import lt.ku.java.models.Country;
import lt.ku.java.orm.CountryORM;
import lt.ku.java.testai.Vykdymas;

/**
 * Servlet implementation class WorldServlet
 */
@WebServlet("/")
public class WorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorldServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action")==null) {
			List<Country> countries=(new CountryORM()).getCountryList();
			RequestDispatcher dispatcher=request.getRequestDispatcher("/countries.jsp");
			
			
			HttpSession session= request.getSession();
			int nr2=0;
			if (session.getAttribute("nr2")!=null) {
				nr2=(Integer)session.getAttribute("nr2");
			}
			nr2++;
			session.setAttribute("nr2", nr2);
			
			request.setAttribute("nr2", nr2);
			request.setAttribute("countries", countries);
			
			request.setAttribute("nr", Vykdymas.padidinkIrGauk());
			dispatcher.forward(request, response);
			return;
		}
		String action=request.getParameter("action");
		if (action.equals("insert")) {
			RequestDispatcher dispatcher=request.getRequestDispatcher("/new_country.jsp");
			dispatcher.forward(request, response);
		}
		if (action.equals("show")) {
			int id=Integer.parseInt(request.getParameter("id"));
			Country country=(new CountryORM()).getCountry(id);
			request.setAttribute("country", country);
			RequestDispatcher dispatcher=request.getRequestDispatcher("/country.jsp");
			dispatcher.forward(request, response);
		}
		if (action.equals("delete")) {
			int id=Integer.parseInt(request.getParameter("id"));
			(new CountryORM()).deleteCountry(id);
			response.sendRedirect("/WP/");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action")!=null) {
			String action=request.getParameter("action");
			if (action.equals("insert")) {
				Country country=new Country(
							null,
							request.getParameter("code"),
							request.getParameter("name"),
							request.getParameter("region"),
							Integer.parseInt(request.getParameter("population"))
						);
				(new CountryORM()).addCountry(country);
				this.doGet(request, response);
			}
			if (action.equals("update")) {
				Country country=new Country(
						Integer.parseInt(request.getParameter("id")),
						request.getParameter("code"),
						request.getParameter("name"),
						request.getParameter("region"),
						Integer.parseInt(request.getParameter("population"))
				);
				
				(new CountryORM()).updateCountry(country);
				response.sendRedirect("/WP/?action=show&id="+country.getId());
			}
		}
		
	}

}

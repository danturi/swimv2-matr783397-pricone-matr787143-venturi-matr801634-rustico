package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sessionbeans.logic.SwimResponse;
import sessionbeans.logic.UserBeanRemote;

/**
 * Servlet implementation class Control
 */
@WebServlet(name = "Control", urlPatterns = {"/Control"})
public class Control extends HttpServlet {
	
	private static final long serialVersionUID = 1L;   
	
	@EJB
	private UserBeanRemote userBean;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		System.out.println("**** CONTROL: Principal = "+request.getUserPrincipal().getName());
		
		if(request.getParameter("actionType").equals("replyToFriendReq")) replyToFriendshipReq(request, response);
		
		
		
	}
	
	public void replyToFriendshipReq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		boolean replyValue = false;
	
		SwimResponse swimResponse = null;
		
		if(request.getParameter("value").equals("approve")) replyValue = true;
		
		swimResponse = userBean.replyToFriendshipReq(request.getParameter("toUser"),request.getUserPrincipal().getName(),  replyValue);
		if(swimResponse!=null){
			if(swimResponse.getStatus()==SwimResponse.SUCCESS){
				request.setAttribute("ReplyResult", "ok");
				System.out.println("******CONTROL : QUI ARRIVA ******");
			} else {
				request.setAttribute("ReplyResult", "fail");
			}
		} else {
			request.setAttribute("ReplyResult", "fail");
		}
		System.out.println("******CONTROL: qui ReplyResult: "+request.getAttribute("ReplyResult"));
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/friendReq.jsp");
        dispatcher.forward(request, response);
		
	}



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}


	@Override
	public String getServletInfo() {
		return "Short description";
	}

}

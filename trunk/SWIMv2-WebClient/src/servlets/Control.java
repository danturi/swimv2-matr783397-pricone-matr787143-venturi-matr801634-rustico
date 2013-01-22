package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;

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

		if(request.getParameter("actionType")!=null){
			if(request.getParameter("actionType").equals("sendFriendReq")) sendFriendshipReq(request, response);
			if(request.getParameter("actionType").equals("replyToFriendReq")) replyToFriendshipReq(request, response);
		} else {

			request.setAttribute("ReplyResult", "fail");
		}
		
		if(request.getAttribute("FoundResult").equals("searching")) searchUserMatching(request, response);
		




	}

	public void sendFriendshipReq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		SwimResponse swimResponse = null;

		if(request.getParameter("toUser")!=null){

			swimResponse = userBean.sendFriendshipReq(request.getUserPrincipal().getName(), request.getParameter("toUser"));
			if(swimResponse!=null){
				if(swimResponse.getStatus()==SwimResponse.SUCCESS){
					request.setAttribute("SendFriendshipReplyResult", "ok");
				} else {
					request.setAttribute("SendFriendshipReplyResult", "fail");
				}
			} else {
				request.setAttribute("SendFriendshipReplyResult", "fail");
			}
		}
		request.setAttribute("LastProfileUserView", request.getParameter("toUser"));
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/profile.jsp");
		dispatcher.forward(request, response);
	}



	public void replyToFriendshipReq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean replyValue = false;

		SwimResponse swimResponse = null;

		if(request.getParameter("toUser")!=null && request.getParameter("value")!=null){

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
		} else{
			request.setAttribute("ReplyResult", "fail");
		}

	}
	
	public void searchUserMatching(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		SwimResponse swimResponse = null;
		System.out.println("\n**** CONTROL: QUI SEARCH_USER_MATCHING***\n");
		String lastname = request.getParameter("lastname");
		String firstname = request.getParameter("firstname");
		String city = request.getParameter("place");
		String ability = request.getParameter("ability");
		String ability2 = request.getParameter("ability2");
		String onlyfriends = request.getParameter("friends");
		
		swimResponse = userBean.searchUserMatching(request.getUserPrincipal().getName(), lastname, firstname, city, ability, ability2, onlyfriends);
		
		
		if(swimResponse!=null){
			if(swimResponse.getStatus()==SwimResponse.SUCCESS){
				request.setAttribute("FoundResult", "ok");
				System.out.println("\n**** CONTROL: QUI SEARCH_USER_MATCHING FoundResult ok***\n");
				request.setAttribute("MatchingList", swimResponse.getData());
				System.out.println("\n**** CONTROL: MATCH LIST: "+(List<User>)swimResponse.getData());
			} else {
				request.setAttribute("FoundResult", "fail");
			}
		} else {
			request.setAttribute("FoundResult", "fail");
		}
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/result.jsp");
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

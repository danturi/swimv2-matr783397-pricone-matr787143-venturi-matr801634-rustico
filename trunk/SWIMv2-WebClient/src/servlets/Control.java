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
			if(request.getParameter("actionType").equals("replyToAbilityReq")) {
				if(request.isUserInRole("ADMINISTRATOR")){
					replyToAbilityReq(request, response); 
				} else {
					request.setAttribute("ReplyResult", null);
				}

			}

		} else {

			request.setAttribute("ReplyResult", "fail");
		}
		if(request.getAttribute("FoundResult")!=null){
			if(request.getAttribute("FoundResult").equals("searching")) searchUserMatching(request, response);
		}
		if(request.getAttribute("AbilityReqSent")!=null){
			if(request.getAttribute("AbilityReqSent").equals("sending")) sendAbilityRequest(request, response);
		}
		if(request.getAttribute("SendInfoForm")!=null){
			if(request.getAttribute("SendInfoForm").equals("sending")) changeUserInfo(request, response);
		}
		if(request.getAttribute("HelpReqSent")!=null){
			if(request.getAttribute("HelpReqSent").equals("sending")) sendHelpRequest(request, response);
		}

		//QUI SETTARE A NULL TUTTI GLI ATTRIBUTI PRIMA DELLA REDIRECT
		//RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/index.jsp");
		//dispatcher.forward(request, response);



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
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/friendReq.jsp");
			dispatcher.forward(request, response);
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

	public void sendAbilityRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		SwimResponse swimResponse = null;

		System.out.println("\n**** CONTROL: QUI SEND_ABILITY_REQ***\n");
		String abilityId = request.getParameter("abilityrequested");

		String description = request.getParameter("comments");

		swimResponse = userBean.sendAbilityReq(request.getUserPrincipal().getName(), Long.valueOf(abilityId), description);

		if(swimResponse!=null){
			if(swimResponse.getStatus()==SwimResponse.SUCCESS){
				request.setAttribute("AbilityReqSent", "ok");
				System.out.println("\n**** CONTROL: QUI SEND_ABILITY_REQ FoundResult AbilityReqSent***\n");
			} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("reqAlreadySent")){

				request.setAttribute("AbilityReqSent", "reqAlreadySent");

			} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("abilityAlreadyOwned")){

				request.setAttribute("AbilityReqSent", "abilityAlreadyOwned");

			} else {
				request.setAttribute("AbilityReqSent", "fail");
			}
		} else {
			request.setAttribute("AbilityReqSent", "fail");
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/abilityreqsent.jsp");
		dispatcher.forward(request, response);
	}

	public void replyToAbilityReq(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		boolean replyValue = false;

		SwimResponse swimResponse = null;

		if(request.getParameter("toUser")!=null && request.getParameter("abilityId")!=null && request.getParameter("value")!=null){
			
			if(request.getParameter("value").equals("approve")) replyValue = true;

			swimResponse = userBean.replyToAbilityReq(request.getParameter("toUser"), Long.valueOf(request.getParameter("abilityId")), replyValue);
			if(swimResponse!=null){
				if(swimResponse.getStatus()==SwimResponse.SUCCESS){
					request.setAttribute("ReplyResult", "ok");
					System.out.println("******CONTROL : QUI ARRIVA SUCCESS ok ******");
				} else {
					request.setAttribute("ReplyResult", swimResponse.getStatusMsg());
				}
			} else {
				request.setAttribute("ReplyResult", "systemfail");
			}
			System.out.println("******CONTROL: qui ReplyResult: "+request.getAttribute("ReplyResult"));
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/admin/showAbilityReqAdmin.jsp");
			dispatcher.forward(request, response);
	
		} else{
			request.setAttribute("ReplyResult", "urlfail");
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/admin/showAbilityReqAdmin.jsp");
			dispatcher.forward(request, response);
		}

	}
	
	public void changeUserInfo(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		SwimResponse swimResponse = null;
		System.out.println("\n**** CONTROL: QUI CHANGE_USER_INFO***\n");
		String lastname = request.getParameter("lastname");
		String firstname = request.getParameter("firstname");
		String city = request.getParameter("place");
		String sex = request.getParameter("sex");
		String age = request.getParameter("age");
		String job = request.getParameter("job");
		String tel = request.getParameter("tel");
		
		swimResponse = userBean.changeUserInfo(request.getUserPrincipal().getName(), lastname, firstname, city, sex, age, job, tel);

		if(swimResponse!=null){
			if(swimResponse.getStatus()==SwimResponse.SUCCESS){
				request.setAttribute("SendInfoForm", "ok");
				System.out.println("\n**** CONTROL: QUI CHANGEINFO SendInfoForm ok***\n");
			} else {
				request.setAttribute("SendInfoForm", "fail");
			}
		} else {
			request.setAttribute("SendInfoForm", "fail");
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/changeInfo.jsp?sendinfoform=false");
		dispatcher.forward(request, response);
		
	}

	public void sendHelpRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		
		SwimResponse swimResponse = null;

		System.out.println("\n**** CONTROL: QUI SEND_HELP_REQ***\n");
		String toUser = request.getParameter("toUser");
		String abilityId = request.getParameter("helprequested");
		if(abilityId!=null){
		String description = request.getParameter("comments");

		swimResponse = userBean.sendHelpReq(request.getUserPrincipal().getName(), toUser, Long.valueOf(abilityId), description);

		if(swimResponse!=null){
			if(swimResponse.getStatus()==SwimResponse.SUCCESS){
				request.setAttribute("HelpReqSent", "ok");
			} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("reqAlreadySent")){
				request.setAttribute("HelpReqSent", "reqAlreadySent");
			} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("noValidAbility")){
				request.setAttribute("HelpReqSent", "noValidAbility");
			} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("noValidUser")){
				request.setAttribute("HelpReqSent", "noValidUser");
			}  else {
				request.setAttribute("HelpReqSent", "fail");
			}
		} else {
			request.setAttribute("HelpReqSent", "fail");
		}
		} else {
			request.setAttribute("HelpReqSent", "fail");
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/helpreqsent.jsp");
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

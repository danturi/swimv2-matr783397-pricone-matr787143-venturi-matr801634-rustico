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
			if(request.getParameter("actionType").equals("replyToHelpReq")) replyToHelpReq(request, response);

			if(request.getParameter("actionType").equals("replyToAbilityReq")) { //ADMIN FUNCTION
				if(request.isUserInRole("ADMINISTRATOR")){
					replyToAbilityReq(request, response); 
				} else {
					request.setAttribute("ReplyResult", null);
				}
			}
			
			if(request.getParameter("actionType").equals("removeUser")) { //ADMIN FUNCTION
				if(request.isUserInRole("ADMINISTRATOR")){
					removeUser(request, response); 
				} else {
					request.setAttribute("RemoveUser", null);
				}
			}

		} else {

			request.setAttribute("ReplyResult", "fail");
		}
		if(request.getAttribute("FoundResult")!=null){
			if(request.getAttribute("FoundResult").equals("searching")) searchUserMatching(request, response);
		}

		if(request.getAttribute("FoundResultAdmin")!=null){ //ADMIN FUNCTION
			if(request.getAttribute("FoundResultAdmin").equals("searching")) searchUserMatchingAdmin(request, response);
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
		if(request.getAttribute("FeedSent")!=null){
			if(request.getAttribute("FeedSent").equals("sending")) sendFeedback(request, response);
		}


		if(request.getParameter("AddAbility")!=null){
			if(request.getParameter("AddAbility").equals("sending")) addAbility(request, response);
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

	public void searchUserMatchingAdmin(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		SwimResponse swimResponse = null;
		System.out.println("\n**** CONTROL: QUI SEARCH_USER_MATCHING_ADMIN***\n");
		String lastname = request.getParameter("lastname");
		String firstname = request.getParameter("firstname");
		String city = request.getParameter("place");
		String ability = request.getParameter("ability");
		String ability2 = request.getParameter("ability2");
		String onlyfriends = request.getParameter("friends");

		swimResponse = userBean.searchUserMatching(request.getUserPrincipal().getName(), lastname, firstname, city, ability, ability2, onlyfriends);

		if(request.isUserInRole("ADMINISTRATOR")){
			if(swimResponse!=null){
				if(swimResponse.getStatus()==SwimResponse.SUCCESS){
					request.setAttribute("FoundResultAdmin", "ok");
					System.out.println("\n**** CONTROL: QUI SEARCH_USER_MATCHING FoundResultAdmin ok***\n");
					request.setAttribute("MatchingList", swimResponse.getData());
					System.out.println("\n**** CONTROL: MATCH LIST: "+(List<User>)swimResponse.getData());
				} else {
					request.setAttribute("FoundResultAdmin", "fail");
				}
			} else {
				request.setAttribute("FoundResultAdmin", "fail");
			}
		} else {
			request.setAttribute("FoundResultAdmin", "fail");
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/admin/resultAdmin.jsp");
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

	public void replyToHelpReq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean replyValue = false;

		SwimResponse swimResponse = null;

		if(request.getParameter("toUser")!=null && request.getParameter("reqId")!=null && request.getParameter("value")!=null){

			if(request.getParameter("value").equals("approve")) replyValue = true;

			String toUser = request.getParameter("toUser");
			String reqId = request.getParameter("reqId");

			swimResponse = userBean.replyToHelpReq(toUser, request.getUserPrincipal().getName(), Long.valueOf(reqId), replyValue);

			if(swimResponse!=null){
				if(swimResponse.getStatus()==SwimResponse.SUCCESS){
					request.setAttribute("ReplyResult", "ok");
				} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("reqAlreadyEvaluated")){
					request.setAttribute("ReplyResult", "reqAlreadyEvaluated");
				} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("noValidUser")){
					request.setAttribute("ReplyResult", "noValidUser");
				} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("noSuchRequestFound")){
					request.setAttribute("ReplyResult", "noSuchRequestFound");
				} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("noValidReq")){
					request.setAttribute("ReplyResult", "noValidReq");
				} else {
					request.setAttribute("ReplyResult", "fail");
				}
			} else {
				request.setAttribute("ReplyResult", "fail");
			}


		} else{
			request.setAttribute("ReplyResult", "urlfail");

		}
		System.out.println("******CONTROL: qui ReplyResult: "+request.getAttribute("ReplyResult"));
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/showHelpReq.jsp");
		dispatcher.forward(request, response);
	}

	public void sendFeedback(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		SwimResponse swimResponse = null;

		System.out.println("\n**** CONTROL: QUI SEND_FEED***\n");
		String toUser = request.getParameter("toUser");
		String reqId = request.getParameter("reqId");
		String vote = request.getParameter("vote") + ".0f";
		String description = request.getParameter("comments");

		if(toUser!=null && reqId!=null && vote!=null){

			swimResponse = userBean.sendFeedback(request.getUserPrincipal().getName(), toUser, reqId, vote, description);

			if(swimResponse!=null){
				System.out.println("*******CONTROL: QUI FEEDSENT = "+swimResponse.getStatusMsg());
				if(swimResponse.getStatus()==SwimResponse.SUCCESS){
					request.setAttribute("FeedSent", "ok");
				} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("reqNotSuitable")){
					request.setAttribute("FeedSent", "reqNotSuitable");
				} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("noValidReq")){
					request.setAttribute("FeedSent", "noValidReq");
				} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("noValidUser")){
					request.setAttribute("FeedSent", "noValidUser");
				} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("noSuchRequestFound")){
					request.setAttribute("FeedSent", "noSuchRequestFound");
				} else if(swimResponse.getStatus()==SwimResponse.FAILED && swimResponse.getStatusMsg().equals("feedAlreadySent")){
					request.setAttribute("FeedSent", "feedAlreadySent");
				}  else {
					request.setAttribute("FeedSent", "fail");
				}
			} else {
				request.setAttribute("FeedSent", "fail");
			}
		} else {
			request.setAttribute("FeedSent", "fail");
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/feedbacksent.jsp");
		dispatcher.forward(request, response);


	}

	public void addAbility(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		SwimResponse swimResponse = null;
		String newAbility = request.getParameter("newAbility");
		System.out.println("\n**** CONTROL: QUI ADD_ABILITY***\n");

		swimResponse = userBean.addAbility(newAbility);
		if(swimResponse!=null){
			if(swimResponse.getStatus()==SwimResponse.SUCCESS){
				request.setAttribute("AddAbility", "ok");
			} else {
				request.setAttribute("AddAbility", "noValidAbilityName");
			}

		} else {
			request.setAttribute("AddAbility", "fail");
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/admin/abilityAdmin.jsp");
		dispatcher.forward(request, response);


	}
	
	
	public void removeUser(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		

		SwimResponse swimResponse = null;
		String user = request.getParameter("user");
		System.out.println("\n**** CONTROL: QUI REMOVE_USER***\n");

		swimResponse = userBean.removeUser(user);
		if(swimResponse!=null){
			if(swimResponse.getStatus()==SwimResponse.SUCCESS){
				request.setAttribute("RemoveUser", "ok");
			} else {
				request.setAttribute("RemoveUser", "noValidUser");
			}

		} else {
			request.setAttribute("RemoveUser", "fail");
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/admin/userSearchAdmin.jsp");
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

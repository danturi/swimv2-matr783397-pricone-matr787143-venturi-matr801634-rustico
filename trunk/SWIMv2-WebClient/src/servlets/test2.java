
package servlets;

import entities.FriendshipRequest;
import entities.HelpRequest;
import entities.User;
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

import sessionbeans.logic.SwimResponse;
import sessionbeans.logic.UserBeanRemote;


/**
 *
 * @author MARCO
 */
@WebServlet(name = "test2", urlPatterns = {"/test2"})
public class test2 extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private UserBeanRemote userBean;



	/**
	 * Processes requests for both HTTP
	 * <code>GET</code> and
	 * <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		/**
		 * 
		 * PROVA RICHIESTE DI AMICIZIA
		 */
		userBean.retrievePicture("prova");
		//userBean.replyToAbilityReq("aa", Long.valueOf("7"), true);
		//userBean.sendFriendshipReq("aa","cc");
		//userBean.sendFriendshipReq("aa","dd");
		//userBean.sendFriendshipReq("aa","ee");
		//userBean.sendFriendshipReq("aa","ff");
		//userBean.sendFriendshipReq("aa","ee");
		/*
		userBean.sendFriendshipReq("aa","cc");
		userBean.sendFriendshipReq("cc","bb");
		userBean.sendFriendshipReq("bb","aa");
		userBean.sendFriendshipReq("aa","aa");
		List<FriendshipRequest> aaFList = (List<FriendshipRequest>) userBean.getFriendshipReqList("aa").getData();
		List<FriendshipRequest> aa1FList = (List<FriendshipRequest>) userBean.getSentFriendshipReqList("aa").getData();
		List<FriendshipRequest> bbFList = (List<FriendshipRequest>) userBean.getFriendshipReqList("bb").getData();
		List<FriendshipRequest> bb1FList = (List<FriendshipRequest>) userBean.getSentFriendshipReqList("bb").getData();
		List<FriendshipRequest> ccFList = (List<FriendshipRequest>) userBean.getFriendshipReqList("cc").getData();
		List<FriendshipRequest> cc1FList = (List<FriendshipRequest>) userBean.getSentFriendshipReqList("cc").getData();
	
		for(FriendshipRequest friendReq : aaFList){
			System.out.println("Richiesta DI AMICIZIA trovata in aa: ID "+friendReq.getFriendReqId()+"	MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}
		for(FriendshipRequest friendReq : aa1FList){
			System.out.println("Richiesta DI AMICIZIA trovata in aa1: ID "+friendReq.getFriendReqId()+"	MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}
		for(FriendshipRequest friendReq : bbFList){
			System.out.println("Richiesta DI AMICIZIA trovata in bb: ID "+friendReq.getFriendReqId()+"	MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}
		for(FriendshipRequest friendReq : bb1FList){
			System.out.println("Richiesta DI AMICIZIA trovata in bb1: ID "+friendReq.getFriendReqId()+"	MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}
		for(FriendshipRequest friendReq :ccFList){
			System.out.println("Richiesta DI AMICIZIA trovata in cc: ID "+friendReq.getFriendReqId()+"	MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}
		for(FriendshipRequest friendReq :cc1FList){
			System.out.println("Richiesta DI AMICIZIA trovata in cc1: ID "+friendReq.getFriendReqId()+"	MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}
		
		System.out.println("***** QUI RISPOSTA A RICHIESTA DI AMICIZIA***");
		
		
		userBean.replyToFriendshipReq("cc", "bb", true);

		/**
		 * 
		 * PROVA RICHIESTE DI AIUTO
		 */
		/*
		userBean.sendHelpReq("aa","bb","qui va descrizione");
		userBean.sendHelpReq("aa","cc","qui va descrizione");
		userBean.sendHelpReq("cc","bb","qui va descrizione");
		userBean.sendHelpReq("bb","aa","qui va descrizione");
		userBean.sendHelpReq("aa","aa","qui va descrizione");
		List<HelpRequest> aaHList = (List<HelpRequest>) userBean.getHelpReqList("aa").getData();
		List<HelpRequest> aa1HList = (List<HelpRequest>) userBean.getSentHelpReqList("aa").getData();
		List<HelpRequest> bbHList = (List<HelpRequest>) userBean.getHelpReqList("bb").getData();
		List<HelpRequest> bb1HList = (List<HelpRequest>) userBean.getSentHelpReqList("bb").getData();
		List<HelpRequest> ccHList = (List<HelpRequest>) userBean.getHelpReqList("cc").getData();
		List<HelpRequest> cc1HList = (List<HelpRequest>) userBean.getSentHelpReqList("cc").getData();
	
		for(HelpRequest helpReq : aaHList){
			System.out.println("Richiesta DI AIUTO trovata in aa: ID "+helpReq.getHelpReqId()+"	MITTENTE = "+helpReq.getFromUser()+"	DESTINATARIO = "+helpReq.getToUser()+"\n");
		}
		for(HelpRequest helpReq : aa1HList){
			System.out.println("Richiesta DI AIUTO trovata in aa1: ID "+helpReq.getHelpReqId()+"	MITTENTE = "+helpReq.getFromUser()+"	DESTINATARIO = "+helpReq.getToUser()+"\n");
		}
		for(HelpRequest helpReq : bbHList){
			System.out.println("Richiesta DI AIUTO trovata in bb: ID "+helpReq.getHelpReqId()+"	MITTENTE = "+helpReq.getFromUser()+"	DESTINATARIO = "+helpReq.getToUser()+"\n");
		}
		for(HelpRequest helpReq : bb1HList){
			System.out.println("Richiesta DI AIUTO trovata in bb1: ID "+helpReq.getHelpReqId()+"	MITTENTE = "+helpReq.getFromUser()+"	DESTINATARIO = "+helpReq.getToUser()+"\n");
		}
		for(HelpRequest helpReq :ccHList){
			System.out.println("Richiesta DI AIUTO trovata in cc: ID "+helpReq.getHelpReqId()+"	MITTENTE = "+helpReq.getFromUser()+"	DESTINATARIO = "+helpReq.getToUser()+"\n");
		}
		for(HelpRequest helpReq :cc1HList){
			System.out.println("Richiesta DI AIUTO trovata in cc1: ID "+helpReq.getHelpReqId()+"	MITTENTE = "+helpReq.getFromUser()+"	DESTINATARIO = "+helpReq.getToUser()+"\n");
		}*/
		
		
		
		/**
		 * 
		 * PROVA RICHIESTE AGGIUNTA ABILITA'
		 */
		
		
		//userBean.sendAbilityReq("aa",Long.valueOf("1"),"qui va descrizione richiesta abilita");
		//userBean.sendAbilityReq("aa","Elettricista","qui va descrizione richiesta abilita");
		//userBean.sendAbilityReq("aa","prova","qui va descrizione richiesta abilita");
		
		SwimResponse swimResponse = userBean.findAll();
		
		

		try {
			/* TODO output your page here. You may use following sample code. */
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet test2</title>");            
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet test2 at " + request.getContextPath() + "</h1>");
			if(swimResponse.getStatus()== SwimResponse.SUCCESS){
				List<User> list = (List<User>) swimResponse.getData();
				while(list.iterator().hasNext()){
					User currentUser = list.iterator().next();
					out.println("<p>Users found: "+ currentUser.getEmail()+"</p>");
					list.remove((User)currentUser);
				}
			} else {
				out.println("<p>Errore lista utenti</p>");
			}
			//out.println("<p>User byEmail found: "+ userBean.findUserByEmail("prova@a.com").getEmail() +"</p>");
			out.println("<p>Attributi passati:  USER = "+request.getParameter("user")+" scelta = "+request.getParameter("value")+"</p>");
			out.println("</body>");
			out.println("</html>");
		} finally {            
			out.close();
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP
	 * <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP
	 * <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}

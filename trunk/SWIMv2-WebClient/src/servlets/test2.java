/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.FriendshipRequest;
import entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionbeans.logic.SwimResponse;
import sessionbeans.logic.UserBeanLocal;


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
	private UserBeanLocal userBean;



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

		// User usr1 = new User();
		//usr1.setEmail("prova11@a.com");
		//userBean.createUser(usr1);
		userBean.sendFriendshipReq("aa","bb");
		userBean.sendFriendshipReq("aa","cc");
		userBean.sendFriendshipReq("cc","bb");
		userBean.sendFriendshipReq("bb","aa");
		List<FriendshipRequest> aaList = (List<FriendshipRequest>) userBean.getFriendshipReqList("aa").getData();
		List<FriendshipRequest> aa1List = (List<FriendshipRequest>) userBean.getFriendshipReqList1("aa").getData();
		List<FriendshipRequest> bbList = (List<FriendshipRequest>) userBean.getFriendshipReqList("bb").getData();
		List<FriendshipRequest> bb1List = (List<FriendshipRequest>) userBean.getFriendshipReqList1("bb").getData();
		List<FriendshipRequest> ccList = (List<FriendshipRequest>) userBean.getFriendshipReqList("cc").getData();
		List<FriendshipRequest> cc1List = (List<FriendshipRequest>) userBean.getFriendshipReqList1("cc").getData();
		//System.out.println("LISTA: "+aaList);
		/*while(aaList.iterator().hasNext()){
			FriendshipRequest friendReq = aaList.iterator().next();
			System.out.println("RICHIESTA TROVATA : "+friendReq.getToUser()+"\n");
			aaList.remove(friendReq);
		}*/
		for(FriendshipRequest friendReq : aaList){
			System.out.println("RICHIESTA TROVATA in aa: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}
		for(FriendshipRequest friendReq : aa1List){
			System.out.println("RICHIESTA TROVATA in aa1: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}
		for(FriendshipRequest friendReq : bbList){
			System.out.println("RICHIESTA TROVATA in bb: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}
		for(FriendshipRequest friendReq : bb1List){
			System.out.println("RICHIESTA TROVATA in bb1: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}
		for(FriendshipRequest friendReq :ccList){
			System.out.println("RICHIESTA TROVATA in cc: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}
		for(FriendshipRequest friendReq :cc1List){
			System.out.println("RICHIESTA TROVATA in cc1: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
		}

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
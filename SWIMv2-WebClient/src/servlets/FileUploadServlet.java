package servlets;



import entities.FileStorageEntity;
import entities.User;
import sessionbeans.logic.FileStorageBeanRemote;
import sessionbeans.logic.SwimResponse;
import sessionbeans.logic.UserBeanRemote;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private FileStorageBeanRemote fileStorageBean;

	@EJB
	private UserBeanRemote userBean;

	protected void processRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{

		if(ServletFileUpload.isMultipartContent( request )){
			uploadPicture(request,response);
		} else {
			System.out.println("******QUI NON CARICA FOTO\n");
		}


	}


	public void uploadPicture( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{

		if(request.getUserPrincipal().getName()!=null){

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload();
			User usrPrincipal = userBean.find(request.getUserPrincipal().getName());
			if(usrPrincipal!=null){
				 
				
				try{
					// Parse the request
					FileItemIterator iter = upload.getItemIterator( request );

					while ( iter.hasNext() ){
						FileItemStream item = iter.next();
						String fieldName = item.getFieldName();
						String name = item.getName();

						if ( fieldName.equals( "selectedFile" ) ){
							byte[] bytes = IOUtils.toByteArray( item.openStream() );
							if(bytes.length>100 && bytes.length<10490000){
								
								FileStorageEntity fsEntity = new FileStorageEntity( name, bytes );
								Long newPicId = fileStorageBean.create(fsEntity);
								
								
								SwimResponse addPicRsp = userBean.bindPictureToUser(usrPrincipal.getEmail(), newPicId);
								if(addPicRsp!=null){
									if(addPicRsp.getStatus()==SwimResponse.SUCCESS){
										request.setAttribute("FileUpload", "ok");
									} else {
										request.setAttribute("FileUpload", "fail");
									}
									
								} else {
									request.setAttribute("FileUpload", "fail");
								}
								
							} else {
								request.setAttribute("FileUpload", "noFile");
							}
							
						}
						break;
					}

					//response.sendRedirect( "ok.jsp" );
					
				}
				catch ( IOException ex ) {
					throw ex;
				}
				catch ( Exception ex ) {
					throw new ServletException( ex );
				}
			} else {
				request.setAttribute("FileUpload", "noValidUser");
			}

		} else {
			request.setAttribute("FileUpload", "fail");
		}


		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/secure/profile.jsp?user="+request.getUserPrincipal().getName());
		dispatcher.forward(request, response);
	}



	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		processRequest( request, response );
	}
}


package servlets;



import entities.FileStorageEntity;
import sessionbeans.logic.FileStorageBeanRemote;
import java.io.IOException;
import javax.ejb.EJB;
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

    protected void processRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        
    	if(ServletFileUpload.isMultipartContent( request )){
    		uploadPicture(request,response);
    	} else {
    		System.out.println("******QUI NON CARICA FOTO\n");
    	}
    	
    	
    }
    
    
    public void uploadPicture( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
    	boolean isMultipart = ServletFileUpload.isMultipartContent( request );

        if ( isMultipart ){
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload();

            try{
                // Parse the request
                FileItemIterator iter = upload.getItemIterator( request );

                while ( iter.hasNext() ){
                    FileItemStream item = iter.next();
                    String fieldName = item.getFieldName();
                    String name = item.getName();

                    if ( fieldName.equals( "selectedFile" ) ){
                        byte[] bytes = IOUtils.toByteArray( item.openStream() );
                        FileStorageEntity fsEntity = new FileStorageEntity( name, bytes );
                        fileStorageBean.create( fsEntity );
                    }
                }

                response.sendRedirect( "ok.jsp" );
                }
            catch ( IOException ex ) {
                throw ex;
            }
            catch ( Exception ex ) {
                throw new ServletException( ex );
            }
        }
    	
    	
    }
    
    

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        processRequest( request, response );
    }
}


package entities;



import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table( name = "FILE_STORAGE" )
@NamedQueries( 
{
    @NamedQuery( name = "FileStorage.findAll", query = "SELECT f FROM FileStorageEntity f" ),
    @NamedQuery( name = "FileStorage.findByPictureId", query = "SELECT f FROM FileStorageEntity f WHERE f.pictureId = :pictureId" ),
    @NamedQuery( name = "FileStorage.findByFileName", query = "SELECT f FROM FileStorageEntity f WHERE f.fileName = :fileName" )
} )
public class FileStorageEntity implements Serializable
{
    private static final long serialVersionUID = -4796720242338042828L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long pictureId;
   

    @Column( name = "FILE_NAME" )
    private String fileName;

    @Lob
    @Column( name = "CONTENT" )
    private byte[] content;

    public FileStorageEntity(){
    	
    }

    public FileStorageEntity( String fileName, byte[] content ){
        this.fileName = fileName;
        this.content = content;
    }

    public Long getPictureId(){
        return pictureId;
    }

    public void setPictureId( Long pictureId )
    {
        this.pictureId = pictureId;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName( String fileName )
    {
        this.fileName = fileName;
    }

    public byte[] getContent()
    {
        return content;
    }

    public void setContent( byte[] content )
    {
        this.content = content;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (pictureId != null ? pictureId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals( Object object )
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if ( !(object instanceof FileStorageEntity) )
        {
            return false;
        }
        FileStorageEntity other = ( FileStorageEntity ) object;
        if ( (this.pictureId == null && other.pictureId != null) || (this.pictureId != null && !this.pictureId.equals( other.pictureId )) )
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entities.FileStorage[ fsPk=" + pictureId + " ]";
    }
}


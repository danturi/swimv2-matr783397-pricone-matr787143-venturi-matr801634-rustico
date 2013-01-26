package sessionbeans.logic;



import entities.FileStorageEntity;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface FileStorageBeanRemote
{
    public Long create( FileStorageEntity fileStorageEntity );

    public void edit( FileStorageEntity fileStorageEntity );

    public void remove( FileStorageEntity fileStorageEntity );

    public FileStorageEntity find( Object id );

    public List<FileStorageEntity> findAll();

   
}


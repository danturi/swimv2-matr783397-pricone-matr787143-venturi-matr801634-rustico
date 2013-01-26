package sessionbeans.logic;



import java.util.List;

import sessionbeans.logic.FileStorageBeanRemote;
import entities.FileStorageEntity;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class FileStorageBean implements FileStorageBeanRemote{

	@PersistenceContext( unitName = "SWIMv2-ejbPU" )
	private EntityManager em;


	public FileStorageBean(){
	}


	@Override
	public void create(FileStorageEntity fileStorageEntity) {
		em.persist(fileStorageEntity);

	}

	@Override
	public void edit(FileStorageEntity fileStorageEntity) {
		em.merge(fileStorageEntity);

	}

	@Override
	public void remove(FileStorageEntity fileStorageEntity) {
		if (fileStorageEntity != null && fileStorageEntity.getPictureId()!=null && em.contains(fileStorageEntity)) {
			em.remove(fileStorageEntity);
		}

	}

	@Override
	public FileStorageEntity find(Object id) {
		return em.find(FileStorageEntity.class, id);
	}

	@Override
	public List<FileStorageEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	


}


package com.aabhaarobata.dao.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aabhaarobata.dao.entities.ImageUploadDetailsEntity;

@Repository
@Transactional

public class ImageUploadDetailsRepository implements ImageUploadDetailsRepo  {
	private static final Logger log = LoggerFactory.getLogger(ImageUploadDetailsRepository.class);
	@PersistenceContext
	private EntityManager entityManager;
	
	/* (non-Javadoc)
	 * @see com.aabhaarobata.dao.repositories.ImageUploadDetailsRepo#insert(com.aabhaarobata.dao.entities.ImageUploadDetailsEntity)
	 */
	@Override
	public long insert(ImageUploadDetailsEntity imageUploadDetailsEntity) {
		entityManager.persist(imageUploadDetailsEntity);
		log.debug("persisted ImageUploadDetailsEntity ..");
		return imageUploadDetailsEntity.getImageID();
	}


	/* (non-Javadoc)
	 * @see com.aabhaarobata.dao.repositories.ImageUploadDetailsRepo#findAllbyEmail(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ImageUploadDetailsEntity> findAllbyEmail(String emailID) {
		Query query = entityManager.createQuery("from ImageUploadDetailsEntity where emailID = :emailID").setParameter("emailID", emailID);
		log.debug("fetched ImageUploadDetailsEntity ..");
		return query.getResultList();
	}


	@Override
	public String getImageFileName(Long imageID) {
		ImageUploadDetailsEntity imageUploadDetailsEntity = entityManager.find(ImageUploadDetailsEntity.class,imageID);
		if(imageUploadDetailsEntity!=null)
			return imageUploadDetailsEntity.getOriginalFileName() ;
		else 
			return "" ;
	}

}
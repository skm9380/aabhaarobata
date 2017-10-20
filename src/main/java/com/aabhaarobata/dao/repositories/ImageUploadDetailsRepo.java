package com.aabhaarobata.dao.repositories;

import java.util.List;

import com.aabhaarobata.dao.entities.ImageUploadDetailsEntity;

public interface ImageUploadDetailsRepo {

	long insert(ImageUploadDetailsEntity imageUploadDetailsEntity);

	List<ImageUploadDetailsEntity> findAllbyEmail(String emailID);
	
	String getImageFileName(Long imageID);

}
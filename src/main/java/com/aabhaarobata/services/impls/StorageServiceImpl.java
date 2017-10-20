package com.aabhaarobata.services.impls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aabhaarobata.dao.entities.ImageUploadDetailsEntity;
import com.aabhaarobata.dao.repositories.ImageUploadDetailsRepo;
import com.aabhaarobata.services.StorageService;

@Service
public class StorageServiceImpl implements StorageService {
	private static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);
	@Autowired
	ImageUploadDetailsRepo repo;
	private static String dirName = System.getProperty("java.io.tmpdir") + File.separator + "aabhaarobata";

	public long store(MultipartFile multipartfile, String userID, String emailID) throws IOException {
		log.debug("Storage Service working ..");
		String filename = multipartfile.getOriginalFilename();
		log.debug("Storing file::{}", filename);
		File dir = new File(dirName);
		if (!dir.exists())
			dir.mkdirs();

		File file = new File(dirName + File.separator + filename);

		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(multipartfile.getBytes());
			fos.close();
		}
		
		ImageUploadDetailsEntity imageUploadDetailsEntity = new ImageUploadDetailsEntity();
		imageUploadDetailsEntity.setCreateTime(LocalDateTime.now());
		imageUploadDetailsEntity.setUserID(userID);
		imageUploadDetailsEntity.setEmailID(emailID);
		imageUploadDetailsEntity.setFile(file.getAbsolutePath());
		imageUploadDetailsEntity.setOriginalFileName(multipartfile.getOriginalFilename());
		imageUploadDetailsEntity.setContentType(multipartfile.getContentType());
		imageUploadDetailsEntity.setContentSize(multipartfile.getSize());
		
		return repo.insert(imageUploadDetailsEntity);
	}

	@Override
	public byte[] getImage(long imageid, String imagetype) throws IOException {

		File file;
		if (("original").equals(imagetype))
			file = new File(dirName + File.separator + repo.getImageFileName(imageid));
		else
			file = new File(dirName + File.separator + repo.getImageFileName(imageid) + "." + imagetype + ".png");
		InputStream in = new FileInputStream(file);
		log.debug("Retrieving file::{}", file.getAbsolutePath());
		return IOUtils.toByteArray(in);

	}

}
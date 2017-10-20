package com.aabhaarobata.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	long store(MultipartFile file, String userID, String emailID) throws IOException;

	byte[] getImage(long imageid, String imagetype) throws IOException;

	// Stream<Path> loadAll();

	// Path load(String filename);

	// Resource loadAsResource(String filename);

	// void deleteAll();

}
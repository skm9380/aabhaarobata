package com.aabhaarobata.services.impls;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aabhaarobata.dao.repositories.ImageUploadDetailsRepo;
import com.aabhaarobata.imageprocessor.AnalyseImage;
import com.aabhaarobata.services.ImageProcessService;
import com.aabhaarobata.web.forms.ImageProcessForm;

@Service
public class ImageProcessServiceImpl implements ImageProcessService {
	private static final Logger log = LoggerFactory.getLogger(ImageProcessServiceImpl.class);
	@Autowired
	ImageUploadDetailsRepo repo;
	private static String dirName = System.getProperty("java.io.tmpdir") + File.separator + "aabhaarobata";

	@Override
	public void process(long imageid, ImageProcessForm imageProcessForm) throws IOException {

		BufferedImage image = null;

		BufferedImage processedimage = null;
		AnalyseImage engine = new AnalyseImage();
		String imagePath = dirName + File.separator + repo.getImageFileName(imageid);
		image = engine.readImage(imagePath);


		processedimage = engine.processSelectiveColor(image, imageProcessForm);
		log.debug("Saved the image:: {}",
				engine.writeImage(imagePath + ".processedselectedcolor.png", processedimage, "png"));

	}

}

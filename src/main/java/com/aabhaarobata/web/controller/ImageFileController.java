package com.aabhaarobata.web.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aabhaarobata.services.ImageProcessService;
import com.aabhaarobata.services.StorageService;
import com.aabhaarobata.web.beans.ResponseObjectBean;
import com.aabhaarobata.web.forms.ImageProcessForm;

//https://www.mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
@Controller
public class ImageFileController {
	private static final Logger log = LoggerFactory.getLogger(ImageFileController.class);

	@Autowired
	private StorageService storageService;

	@Autowired
	private ImageProcessService imageProcessService;

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObjectBean> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("userid") String userID, @RequestParam("emailid") String emailID, ModelMap modelMap) {

		log.debug("userID:{}", userID);
		log.debug("emailID:{}", emailID);
		log.debug("fileName:ORIGINALNAME::{}", file.getOriginalFilename());
		log.debug("fileName:{}", file.getName());
		ResponseObjectBean response = new ResponseObjectBean();

		if (emailID.isEmpty() || userID.isEmpty() || file.isEmpty()) {
			response.setFileID("-1");
			if (emailID.isEmpty())
				response.setStatusMsg("Please enter EmailID");

			if (userID.isEmpty())
				response.setStatusMsg("Please enter userID");

			if (file.isEmpty())
				response.setStatusMsg("Please select file");

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		modelMap.put("userID", userID);
		modelMap.put("emailID", emailID);
		try {
			response.setFileID(Long.toString(storageService.store(file, userID, emailID)));
			response.setStatusMsg("OK");
		} catch (IOException e) {

			log.error("ERROR:", e);
			response.setFileID("-1");
			response.setStatusMsg(e.getMessage());
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/processedimages/{imageid}/{imagetype}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable long imageid, @PathVariable String imagetype) {
		try {
			log.debug("imageid::{}", imageid);
			log.debug("imagetype::{}", imagetype);

			byte[] media = storageService.getImage(imageid, imagetype);

			HttpHeaders headers = new HttpHeaders();
			headers.setCacheControl(CacheControl.noCache().getHeaderValue());
			headers.setContentType(MediaType.IMAGE_PNG);
			return new ResponseEntity<>(media, headers, HttpStatus.OK);
		} catch (IOException e) {
			log.error("Error::{}", e);
		}
		return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value = "/processimage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObjectBean> processImage(@Valid ImageProcessForm imageProcessForm,
			@RequestParam("imageid") String imageid, BindingResult bindingResult) {
		log.debug("Processing Image ...{}", imageid);
		log.debug("imageProcessForm::{}", imageProcessForm.toString());
		ResponseObjectBean response = new ResponseObjectBean();
		if (bindingResult.hasErrors()) {
			response.setFileID("-1");
			response.setStatusMsg("Color 1 cannot be Blank");
		} else {
			try {
				imageProcessService.process(Long.parseLong(imageid), imageProcessForm);
				response.setFileID("1");
				response.setStatusMsg("OK");
			} catch (NumberFormatException | IOException e) {

				log.error("Error::{}",e);
				response.setFileID("-1");
				response.setStatusMsg("Server Exception");
			}
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
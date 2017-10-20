package com.aabhaarobata.dao.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "imageuploaddetails", schema = "image")
public class ImageUploadDetailsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "image_id")
	private Long imageID;

	@Column(name = "user_id")
	private String userID;

	@Column(name = "email_id")
	private String emailID;

	@Column(name = "file")
	private String file;

	@Column(name = "createtime")
	private LocalDateTime createTime;

	@Column(name = "originalfilename")
	private String originalFileName;

	
	@Column(name = "contenttype")
	private String contentType;	
	
	
	
	@Column(name = "contentsize")
	private long contentSize;	
	
	public ImageUploadDetailsEntity() {
	}

	public ImageUploadDetailsEntity(String userID, String emailID, String file, LocalDateTime createTime) {
		super();
		this.userID = userID;
		this.emailID = emailID;
		this.file = file;
		this.createTime = createTime;

	}
	// getters, setters, toString, hashCode, equals

	public Long getImageID() {
		return imageID;
	}

	public void setImageID(Long imageID) {
		this.imageID = imageID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getContentSize() {
		return contentSize;
	}

	public void setContentSize(long contentSize) {
		this.contentSize = contentSize;
	}


}
package com.topcoder.catalog.entity;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * The entity class to contain
 * 
 * @author Margarita
 * @version 1.0
 * @since BUGR-1600
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compUploadedFile", propOrder = { "fileData", "uploadedFileName"})
public class CompUploadedFile implements Serializable {
	
	/**
	 * Serial version id 
	 */
	private static final long serialVersionUID = 2831427723758720887L;

	private byte[] fileData;
	
	private String uploadedFileName;

	public CompUploadedFile() {
		
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}
}

/**
 * 
 */
package com.technojade.allybot.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.technojade.allybot.entity.FileInformation;
import com.technojade.allybot.repository.FileUploadRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ugam.sharma
 *
 */

@Service
@Slf4j
public class FileUploadService {

	@Autowired
	private FileUploadRepository fileUploadRepository;

	@Value("${file.upload.url}")
	private String uploadUrl;

	public FileInformation uploadFile(MultipartFile file, String identifier, Integer fileTypeId) throws Exception {
		FileInformation fileInformation = null;
		fileInformation = fileUploadRepository.findByFileTypeIdAndFileIdentifier(fileTypeId, identifier);

		if(null == fileInformation)
			fileInformation = new FileInformation();
		
		file.transferTo(new File(uploadUrl.concat("/").concat(file.getOriginalFilename())));
		fileInformation.setContentType(file.getContentType());
		fileInformation.setFileTypeId(fileTypeId);
		fileInformation.setFileIdentifier(String.valueOf(identifier));
		fileInformation.setFileName(file.getOriginalFilename());
		fileInformation.setIsApproved("N");
		fileInformation.setUrl(uploadUrl);
		fileUploadRepository.save(fileInformation);
		return fileInformation;

	}

	
	public FileInformation getFile(String identifier, Integer fileTypeId) throws Exception {
		FileInformation fileInformation = fileUploadRepository.findByFileTypeIdAndFileIdentifier(fileTypeId, identifier);
		return fileInformation;
	}
	

}

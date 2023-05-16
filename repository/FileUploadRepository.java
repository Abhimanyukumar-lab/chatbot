package com.technojade.allybot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.FileInformation;

@Repository
public interface FileUploadRepository extends JpaRepository<FileInformation, Long> {

	@Query(value = "select fi from FileInformation fi where fileTypeId=:fileTypeId and fi.fileIdentifier=:fileIdentifier")
	public FileInformation findByFileTypeIdAndFileIdentifier(@Param("fileTypeId") Integer fileTypeId, @Param("fileIdentifier") String prjId);
	
}

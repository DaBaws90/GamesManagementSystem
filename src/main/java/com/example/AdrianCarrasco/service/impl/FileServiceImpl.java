package com.example.AdrianCarrasco.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.AdrianCarrasco.service.FileService;

@Service("fileServiceImpl")
public class FileServiceImpl implements FileService{

	@Override
	public String saveFile(MultipartFile img) throws IOException{
		if(!img.isEmpty()) {
			byte[] bytes = img.getBytes();
			String location = ".//src//main//resources//static//images//";
			String extendedFilename = String.valueOf(Instant.now().toEpochMilli());
			Path path = Paths.get(location + extendedFilename + img.getOriginalFilename());
			Files.write(path, bytes);
			return extendedFilename + img.getOriginalFilename();
		}
		return null;
	}

	@Override
	public void deleteFile(String caratula) throws IOException{
		if(!caratula.isEmpty()) {
			String location = ".//src//main//resources//static//images//";
			Path path = Paths.get(location + caratula);
			Files.deleteIfExists(path);
		}
	}
	
}

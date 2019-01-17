package com.example.AdrianCarrasco.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public abstract String saveFile(MultipartFile img) throws IOException;
	public abstract void deleteFile(String file) throws IOException;
}

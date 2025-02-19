package com.laundry.BE_Laundry.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileStorageService {
	
	private final String uploadDir = "uploads/";
	
	public String saveFile(MultipartFile file) throws IOException {
		
		if (file.isEmpty()) {
			throw new IOException("File Tidak boleh kosong");
		}
		
		String fileName = UUID.randomUUID().toString()+ "_" + file.getOriginalFilename();
		Path filePath = Paths.get(uploadDir, fileName);
		
		Files.createDirectories(filePath.getParent());
		Files.write(filePath, file.getBytes());
		return fileName;
		
	}

}

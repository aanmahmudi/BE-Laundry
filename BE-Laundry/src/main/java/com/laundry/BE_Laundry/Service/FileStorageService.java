package com.laundry.BE_Laundry.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {

	private final String UPLOAD_DIR = "uploads";

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Paths.get(UPLOAD_DIR));
			System.out.println("Folder upload disiapkan di: " + UPLOAD_DIR);
		} catch (IOException e) {
			throw new RuntimeException("Gagal membuat folder upload", e);
		}
	}

}

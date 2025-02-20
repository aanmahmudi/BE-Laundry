package com.laundry.BE_Laundry.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class FileUploadController {

	private static final String UPLOAD_DIR = "uploads/";

	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
		System.out.println(">>> File diterima: " + file.getOriginalFilename());
		Map<String, String> response = new HashMap<>();

		try {
			Files.createDirectories(Paths.get(UPLOAD_DIR));

			String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR + filename);
			Files.write(filePath, file.getBytes());

			String fileUrl = "http://localhost:8080/uploads/" + filename;

			response.put("message", "Upload successful!");
			response.put("fileUrl", fileUrl);
			return ResponseEntity.ok(response);
		} catch (IOException e) {
			response.put("error", "Could not upload file: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

}

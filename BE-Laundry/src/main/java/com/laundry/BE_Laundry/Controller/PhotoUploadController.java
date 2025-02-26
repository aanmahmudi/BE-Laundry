package com.laundry.BE_Laundry.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.laundry.BE_Laundry.Service.PhotoStorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class PhotoUploadController {

	private final PhotoStorageService photoStorageService;

	@PostMapping(value = "/upload/photo", consumes = "multipart/form-data")
	public ResponseEntity<Map<String, String>> uploadFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("customerId") Long customerId) {
		System.out.println(">>> File diterima: " + file.getOriginalFilename());
		Map<String, String> response = new HashMap<>();

		try {

			String fileUrl = photoStorageService.uploadPhoto(file, customerId);

			response.put("message", "Upload successful!");
			response.put("fileUrl", fileUrl);
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			response.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (IOException e) {
			response.put("error", "Could not upload file: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

}

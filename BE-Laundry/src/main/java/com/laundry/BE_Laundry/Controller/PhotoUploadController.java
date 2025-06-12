package com.laundry.BE_Laundry.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.laundry.BE_Laundry.DTO.ApiMesDocUpload;
import com.laundry.BE_Laundry.Service.PhotoStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class PhotoUploadController {

	private final PhotoStorageService photoStorageService;

	@PostMapping(value = "/upload/photo", consumes = "multipart/form-data")
	public ResponseEntity<ApiMesDocUpload<Map<String, String>>> uploadFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("customerId") Long customerId) {
		System.out.println(">>> File diterima: " + file.getOriginalFilename());
		Map<String, String> response = new HashMap<>();

		try {

			String fileUrl = photoStorageService.uploadPhoto(file, customerId);

			log.info("Photo uploaded for CustomerId {}", customerId);
			return ResponseEntity.ok(
					ApiMesDocUpload.success("Upload successfull", Map.of("fileUrl", fileUrl)));
		} catch (RuntimeException e) {
			log.error("Upload failed for CustomerId {}: {}", customerId, e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiMesDocUpload.fail("Upload gagal: " + e.getMessage(), "UPLOAD_RUNTIME_ERROR"));
		} catch (IOException e) {
			log.error("IO Error uploading Photo for CustomerId {}: {} ", customerId, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiMesDocUpload.error("Terjadi kesalahan saat upload file"));
		}
	}
		
	@GetMapping("/upload-photo")
	public String showUploadForm(@RequestParam ("id")Long customerId, Model model) {
		model.addAttribute("customerId", customerId);
		return "upload-photo";

	}

}

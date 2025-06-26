package com.laundry.BE_Laundry.Controller;

import java.io.IOException;
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
import com.laundry.BE_Laundry.Service.DocumentStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class DocumentUploadController {

	private final DocumentStorageService documentService;

	@PostMapping(value = "/upload/pdf", consumes = "multipart/form-data")
	public ResponseEntity<ApiMesDocUpload<Map<String, String>>> uploadFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("customerId") Long customerId) {

		try {
			String fileUrl = documentService.uploadPDF(file, customerId);
			
			log.info("PDF uploaded for CustomerId {}", customerId);
			return ResponseEntity.ok(
					ApiMesDocUpload.success("Upload successfull", Map.of("fileUrl", fileUrl)));
		} catch (RuntimeException e) {
			log.error("Upload failed for CustomerId {}: {}", customerId, e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiMesDocUpload.fail("Upload gagal: " + e.getMessage(), "UPLOAD_RUNTIME_ERROR"));
		} catch (IOException e) {
			log.error("IO Error uploading PDF for CustomerId {}: {} ", customerId, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiMesDocUpload.error("Terjadi kesalahan saat upload file"));
		}

	}
	
	@GetMapping("/upload-pdf")
	public String showUploadForm(@RequestParam ("email")String email, Model model) {
		model.addAttribute("email", email);
		return "upload";

	}

}

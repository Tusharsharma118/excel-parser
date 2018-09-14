package com.ts.XML.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ts.XML.service.EmployeeService;

@RestController
public class ParserController {

	@Autowired
	private EmployeeService service;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadXML(@RequestParam("file") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String response = "FAILED";
		if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
			response = service.saveToDb(file);
		}
		
		return new ResponseEntity<String>(response,HttpStatus.OK);

	}
}

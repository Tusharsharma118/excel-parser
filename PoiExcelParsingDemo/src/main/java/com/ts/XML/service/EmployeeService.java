package com.ts.XML.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {

	public List<List<String>> parseXML(MultipartFile file);

	public String saveToDb(MultipartFile file);
}

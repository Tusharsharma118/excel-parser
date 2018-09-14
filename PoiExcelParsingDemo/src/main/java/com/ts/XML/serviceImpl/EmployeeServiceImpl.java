package com.ts.XML.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ts.XML.dtos.EmployeeDTO;
import com.ts.XML.mapper.EmployeeMapper;
import com.ts.XML.repository.EmployeeRepository;
import com.ts.XML.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final String datePattern = "yyyy-MM-dd";
	public static final String CASE_UPDATE = "CASE_UPDATE";
	public static final String CASE_VIEWVALID = "ALL";
	public static final String NOT_FOUND = "NOT_FOUND";
	public static final String FAILED = "FAILED";
	public static final String UPDATED_ALREADY = "UPDATED_ALREADY";
	public static final String SUCCESS = "SUCCESS";
	public static final String ALREADY_EXISTS = "ALREADY_EXISTS";
	public static final String STATUS_ACTIVE = "ACTIVE";
	public static final String STATUS_INACTIVE = "INACTIVE";

	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeRepository repository;

	
	//  IMPROVEMENT :- LOOK FOR FINDING A WAY TO NOT PARSE THOSE ROWS WITH PRIMARY KEY EMPTY 
	@Override
	public List<List<String>> parseXML(MultipartFile file) {
		try {
			List<List<String>> allRowList = new ArrayList<>();
			List<String> rows = new ArrayList<String>();
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			logger.debug(sheet.getSheetName() + "SHEET!!");
			Iterator<Row> rowIterator = sheet.rowIterator();
			Row headerRow = rowIterator.next();
			final int numberOfColumns = headerRow.getPhysicalNumberOfCells();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				rows = new ArrayList<>();
				Iterator<Cell> cellIterator = row.cellIterator();
				for (int counter = 0; counter < numberOfColumns; counter++) {

					try {
						Cell cell = cellIterator.next();

						rows.add((String) getCellValue(cell));

					} catch (NoSuchElementException e) {
						rows.add("NULL");
					}

				}
				allRowList.add(rows);
			}

			
			 logger.debug("LOGGING ARRAY!!!"); System.out.println("DISPLAYING ARRAY!");
			 for (List<String> str : allRowList) { logger.debug(str.toString()); }
			

			return allRowList;
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// NEEDS TO HAVE FIXED ORDER AND PROPER STRUCTURE IN THE EXCEL FILE FOR THE PARSING TO WORK CORRECTLY
	// EXCEL DOESN"T READ EMPTY UN-FORMATTED CELLS SO ALL DATA CELLS(EMPTY OR NOT) NEED TO HAVE A FIXED TYPE
	@Override
	public String saveToDb(MultipartFile file) {
		List<EmployeeDTO> empList = new ArrayList<>();
		EmployeeDTO emp = new EmployeeDTO();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
		try {
			List<List<String>> allRowData = parseXML(file);
			if (allRowData == null || allRowData.size() < 1) {
				return FAILED;
			}
			for (List<String> row : allRowData) {
				emp = new EmployeeDTO();
				if (row.get(0).equalsIgnoreCase("NULL") || row.get(0).equalsIgnoreCase(" ")
						|| row.get(1).equalsIgnoreCase("NULL") || row.get(1).equalsIgnoreCase(" ")) {
					throw new Exception("EMPTY DPA AND/OR EMPLOYEE ID");
				} else {
					try {
						// Add data to the object
						emp.setStatus(true);
						emp.setLastModified(LocalDateTime.now());
						emp.setAssetList(new ArrayList<>());
						emp.setEmpId(new Double((double) checkAndReturnCell(row.get(0))).longValue());
						emp.setName((checkAndReturnCell(row.get(1)).toString().matches("|NULL")) ? null
								: checkAndReturnCell(row.get(1)).toString());
						emp.setAccount((checkAndReturnCell(row.get(2)).toString().matches("|NULL")) ? null
								: checkAndReturnCell(row.get(2)).toString());
						emp.setDateOfBirth((checkAndReturnCell(row.get(3)).toString().matches("|NULL")) ? null
								: LocalDate.parse(checkAndReturnCell(row.get(3)).toString(), formatter));
						emp.setContactNumber((checkAndReturnCell(row.get(4)).toString().matches("|NULL")) ? null
								: new Double((double) checkAndReturnCell(row.get(4))).longValue());
						
					} catch (Exception exception) {
						logger.debug(exception.getMessage());
						return FAILED;
					}
				}
				empList.add(emp);

			}
			empList.forEach(e -> System.out.println(e));
			repository.saveAll(EmployeeMapper.INSTANCE.employeeDTOListToEmployeeList(empList));
			return SUCCESS;

		} catch (Exception e) {
			logger.warn(e.getMessage());
			return FAILED;
		}

	}

	private Object getCellValue(Cell cell) {

		logger.debug(cell.getAddress().toString() + ":" + cell.getCellTypeEnum());
		switch (cell.getCellTypeEnum()) {
		case STRING:
			return cell.getStringCellValue();

		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			} else {
				Double cellValue = cell.getNumericCellValue();
				return cellValue.toString();
			}
		case BLANK:
			return "NULL";
		case BOOLEAN: {
			if (cell.getBooleanCellValue()) {
				return "TRUE";
			} else
				return "FALSE";

		}
		default:
			return "NULL";
		}
	}

	private Object checkAndReturnCell(String cell) {
		if (cell.equalsIgnoreCase("NULL") || cell.equalsIgnoreCase(" ")) {
			return "";
		} else {
			try {
				// Long value = Long.parseLong(cell);
				return Double.parseDouble(cell);

			} catch (NumberFormatException e) {
				try {
					logger.debug(e.getMessage());
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
					// LocalDate date = LocalDate.parse(cell, formatter);
					return LocalDate.parse(cell, formatter);

				} catch (DateTimeParseException exception) {
					logger.debug(exception.getMessage());
					return cell;
				}
			}
		}

	}
}

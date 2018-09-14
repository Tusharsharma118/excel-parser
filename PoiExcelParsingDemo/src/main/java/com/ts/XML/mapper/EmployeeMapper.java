package com.ts.XML.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ts.XML.dtos.AssetsDTO;
import com.ts.XML.dtos.EmployeeDTO;
import com.ts.XML.model.Assets;
import com.ts.XML.model.Employee;


/**
 * @author tsharma
 *
 */
@Mapper(componentModel="spring")
public interface EmployeeMapper {

	EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
	
	EmployeeDTO employeeToEmployeeDTO(Employee entity);
	
	Employee employeeDTOToEmployee(EmployeeDTO entity);
	
	AssetsDTO assetsToAssetsDTO(Assets asset);
	
	Assets assetsDTOToAssets(AssetsDTO assetsDTO);
	
	List<AssetsDTO> assetsListToAssetDTOList(List<Assets> assetList);
	
	List<Assets> assetsDTOListToAssetsList(List<AssetsDTO> assetDTOList);
	
	List<EmployeeDTO> employeeListToEmployeeDTOList(List<Employee> employeeList);

	List<Employee> employeeDTOListToEmployeeList(List<EmployeeDTO> employeeDTOList);

}

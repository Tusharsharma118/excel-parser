package com.ts.XML.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ts.XML.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

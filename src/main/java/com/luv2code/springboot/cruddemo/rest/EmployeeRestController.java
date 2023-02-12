package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	private EmployeeService employeeService; 
	
	
	//quick and dirty:inject employee DAO use constructor injection
	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService) {
		theEmployeeService=employeeService;
	}
	
	
	//expose "/employee" and return list of employee
	@GetMapping("/employees")
	public List<Employee> findAll(){
		return employeeService.findAll();
	}
	
	//add mapping for get/employees /{employee Id}
	
	@GetMapping("/employees /{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		 
		Employee theEmployee =employeeService.findById(employeeId);
		
		 // throw expect if null
		if(theEmployee==null) {
			throw new RuntimeException("Employee id not found - "+employeeId);
			
		}
		return theEmployee;
	}
	
	//add mapping for Post/employees add new employees
	
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		
		//also just in case they pass an id in JSON ...set id to 0
		//this is to force save of new item ...instead of update 
		theEmployee.setId(0);
		
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	//add mapping for put /employees updated new employees
	
	@PutMapping("/employees")
	public Employee updatedEmployee(@RequestBody Employee theEmployee) {
		
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	//add mapping for deleted /employees delete  employees
	@DeleteMapping
	public String deleteEmployee(@PathVariable int employeeId ) {
		
		Employee tempEmployee =employeeService.findById(employeeId);
		
		//// throw expect if null
		if(tempEmployee==null) {
			throw new RuntimeException("Employee id not found - "+employeeId);
			
		}
		employeeService.deleteById(employeeId);
		
		return "deleted employee id -"+employeeId;
	}
	

}

package com.example.myapp.hr.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.hr.model.Emp;
import com.example.myapp.hr.model.EmpAssembler;
import com.example.myapp.hr.service.IEmpService;


@RestController
@RequestMapping("/api/employees")
public class EmpController {
	
	@Autowired
	IEmpService empService;
	
	@Autowired
	private EmpAssembler assembler;
	
//	@GetMapping
//	public ResponseEntity<CollectionModel<EntityModel<Emp>>> getAllEmps() {
//		List<EntityModel<Emp>> emps = empService.getEmpList().stream()
//				.map(assembler::toModel)
//				.collect(Collectors.toList());
//		return ResponseEntity.ok(
//				CollectionModel.of(emps,
//						linkTo(methodOn(EmpController.class).getAllEmps()).withSelfRel()));
//	}
	
	@GetMapping
	public ResponseEntity<List<Emp>> getAllEmps() {
	    return ResponseEntity.ok(empService.getEmpList());
	}	
	
	@GetMapping("/{employeeId}")
	public ResponseEntity<Emp> getEmpInfo(@PathVariable int employeeId) {
		try {
			Emp emp = empService.getEmpInfo(employeeId);
			return ResponseEntity
				.ok()
				.eTag("\"" + emp.hashCode() + "\"")
				.body(emp);
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Emp> insertEmp(@RequestBody Emp emp) {
		empService.insertEmp(emp);
		return ResponseEntity.ok(emp);
	}
	
	@PutMapping
	public ResponseEntity<Emp> updateEmp(@RequestBody Emp emp) {
		try {
			empService.updateEmp(emp);
			return ResponseEntity.ok(emp);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteEmp(@RequestBody Emp emp) {
		try {
			int deleteRow = empService.deleteEmp(emp.getEmployeeId(), emp.getEmail());
			if (deleteRow > 0) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/jobids")
	public List<Map<String, Object>> getAllJobId() {
		return empService.getAllJobId();
	}
	
	@GetMapping("/deptids")
	public List<Map<String, Object>> getAllDeptId() {
		return empService.getAllDeptId();
	}
	
	@GetMapping("/mgrids")
	public List<Map<String, Object>> getAllManagerId() {
		return empService.getAllManagerId();
	}
}

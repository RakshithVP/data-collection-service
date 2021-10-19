package com.example.dataCollectionService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.dataCollectionService.amqp.RabbitMQSender;
import com.example.dataCollectionService.crypto.utils.ProtoUtils;

@RestController
@RequestMapping("employee")
public class EmployeeController {

	@Autowired
	RabbitMQSender rabbitMQSender;

	public enum Filetype {
		CSV, XML
	};

	public static final String DEFAULT_FILE_TYPE = Filetype.CSV.toString();

	@PostMapping(path = "/store", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee,
			@RequestParam(name = "fileType", defaultValue = "CSV") String fileType) throws Exception {
		rabbitMQSender.send(employee, fileType, "Create");
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@GetMapping(path = "/read/{empName}")
	public ResponseEntity<String> getEmployee(@PathVariable String empName,
			@RequestParam(name = "fileType", defaultValue = "CSV") String fileType) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(
				"http://localhost:8081/data-storage/employee/read/" + empName + "?fileType=" + fileType, String.class);
		
		return new ResponseEntity<String>(ProtoUtils.deserializeFromProto(responseEntity.getBody()), HttpStatus.OK);
	}
	
	@PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateEmployee(@RequestBody Employee employee,
			@RequestParam(name = "fileType", defaultValue = "CSV") String fileType) throws Exception {
		rabbitMQSender.send(employee, fileType, "Update");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

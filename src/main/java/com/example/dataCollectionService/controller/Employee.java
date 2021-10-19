package com.example.dataCollectionService.controller;

import java.time.LocalDate;

import com.opencsv.bean.CsvBindByName;

import io.protostuff.Tag;

public class Employee {

	@CsvBindByName(column = "Name")
	@Tag(value = 1)
	private String name;

	@CsvBindByName(column = "DateOfBirth")
	@Tag(value = 2)
	private LocalDate dateOfBirth;

	@CsvBindByName(column = "Salary")
	@Tag(value = 3)
	private Double salary;

	@CsvBindByName(column = "Age")
	@Tag(value = 4)
	private Integer age;

	public Employee() {

	}

	public Employee(String name, LocalDate dateOfBirth, Double salary, Integer age) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.salary = salary;
		this.age = age;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", dateOfBirth=" + dateOfBirth + ", salary=" + salary + ", age=" + age + "]";
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

package com.example.dataCollectionService.crypto.utils;

import java.time.LocalDate;

import com.example.dataCollectionService.controller.Employee;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class ProtoUtils {

	private static final String PASSWORD = "this is a password";

	private static byte[] serializeToProto(Employee employee) {
		RuntimeSchema.register(Employee.class);
		RuntimeSchema.createFrom(LocalDate.class);
		Schema<Employee> schema = RuntimeSchema.getSchema(Employee.class);
		LinkedBuffer buffer = LinkedBuffer.allocate(512);

		byte[] protoMessage = ProtostuffIOUtil.toByteArray(employee, schema, buffer);
		buffer.clear();

		Employee empParsed = new Employee();
		ProtostuffIOUtil.mergeFrom(protoMessage, empParsed, schema);

		return protoMessage;
	}

	public static String encryptProto(Employee employee) throws Exception {
		String PASSWORD = "this is a password";
		return EncryptorAesGcmPassword.encrypt(serializeToProto(employee), PASSWORD);
	}

	public static String encryptProto(String employeeFileData) throws Exception {
		String PASSWORD = "this is a password";
		return EncryptorAesGcmPassword.encrypt(serializeToProto(employeeFileData), PASSWORD);
	}

	private static byte[] serializeToProto(String employeeFileData) {
		Schema<String> schema = RuntimeSchema.getSchema(String.class);
		LinkedBuffer buffer = LinkedBuffer.allocate(512);
		byte[] protoMessage = ProtostuffIOUtil.toByteArray(employeeFileData, schema, buffer);
		buffer.clear();
		return protoMessage;
	}

	public static String deserializeFromProto(String encryptedTextBase64) throws Exception {
		byte[] protoMessage = decryptProto(encryptedTextBase64);
		String employeeFileDataParsed = new String();
		Schema<String> schema = RuntimeSchema.getSchema(String.class);
		ProtostuffIOUtil.mergeFrom(protoMessage, employeeFileDataParsed, schema);

		return employeeFileDataParsed;
	}

	public static Employee deserializeProto(String encryptedTextBase64) throws Exception {
		byte[] protoMessage = decryptProto(encryptedTextBase64);
		Employee empParsed = new Employee();
		Schema<Employee> schema = RuntimeSchema.getSchema(Employee.class);
		ProtostuffIOUtil.mergeFrom(protoMessage, empParsed, schema);

		return empParsed;
	}

	public static byte[] decryptProto(String encryptedTextBase64) throws Exception {
		return EncryptorAesGcmPassword.decrypt(encryptedTextBase64, PASSWORD);
	}
}

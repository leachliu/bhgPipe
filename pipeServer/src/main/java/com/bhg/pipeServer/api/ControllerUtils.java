package com.bhg.pipeServer.api;

import java.util.Collection;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bhg.pipeServer.exception.PipeException;
import com.bhg.pipeServer.service.IPipeEntity;
import com.bhg.pipeServer.service.ServiceBase;

public class ControllerUtils {
	public static <T extends IPipeEntity> ResponseEntity<Collection<T>> index(ServiceBase<T> service) {
		System.out.println("index");
		Map<String, T> entities = service.all();
		return new ResponseEntity<Collection<T>>(entities.values(), HttpStatus.OK);
	}

	public static <T extends IPipeEntity> ResponseEntity<T> create(T input, ServiceBase<T> service) {
		T entity = service.add(input);
		return new ResponseEntity<T>(entity, HttpStatus.CREATED);
	}

	public static <T extends IPipeEntity> ResponseEntity<T> update(String id, T input, ServiceBase<T> service) {
		Map<String, T> entities = service.all();
		T entity = entities.get(id);
		if (entity == null) {
			throw new PipeException(1);
		}
		entity = service.update(id, input);
		return new ResponseEntity<T>(entity, HttpStatus.ACCEPTED);
	}

	public static <T extends IPipeEntity> ResponseEntity<T> destroy(String id, ServiceBase<T> service) {
		Map<String, T> entities = service.all();
		T entity = entities.get(id);
		if (entity == null) {
			throw new PipeException(1);
		}
		service.delete(id);
		return new ResponseEntity<T>(HttpStatus.NO_CONTENT);
	}

	public static <T extends IPipeEntity> ResponseEntity<T> view(String id, ServiceBase<T> service) {
		Map<String, T> entities = service.all();
		T eintry = entities.get(id);
		if (eintry == null) {
			throw new PipeException(1);
//			return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<T>(eintry, HttpStatus.OK);
	}
}

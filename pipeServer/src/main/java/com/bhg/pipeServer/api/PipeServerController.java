package com.bhg.pipeServer.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhg.pipeServer.service.SchemaService;
import com.bhg.pipeServer.vo.SchemaVo;

@RestController
public class PipeServerController {

	@Autowired
	SchemaService service;

	@RequestMapping(value = "/schemas", method = RequestMethod.GET)
	public ResponseEntity<Collection<SchemaVo>> index() {
		return ControllerUtils.index(service);
	}

	@RequestMapping(value = "/schemas", method = RequestMethod.POST)
	public ResponseEntity<SchemaVo> create(@RequestBody SchemaVo input) {
		return ControllerUtils.create(input, service);
	}

	@RequestMapping(value = "/schemas/{id}", method = RequestMethod.PUT)
	public ResponseEntity<SchemaVo> update(@PathVariable("id") String id, @RequestBody SchemaVo input) {
		return ControllerUtils.update(id, input, service);
	}

	@RequestMapping(value = "/schemas/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<SchemaVo> destroy(@PathVariable("id") String id) {
		return ControllerUtils.destroy(id, service);
	}

	@RequestMapping(value = "/schemas/{id}", method = RequestMethod.GET)
	public ResponseEntity<SchemaVo> view(@PathVariable("id") String id) {
		return ControllerUtils.view(id, service);
	}

}

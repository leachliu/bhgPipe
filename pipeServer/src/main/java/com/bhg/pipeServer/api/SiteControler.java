package com.bhg.pipeServer.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhg.pipeServer.service.SiteService;
import com.bhg.pipeServer.vo.SiteVo;

@RestController

public class SiteControler {
	@Autowired
	SiteService service;

	@RequestMapping(value = "/sites", method = RequestMethod.GET)
	public ResponseEntity<Collection<SiteVo>> index() {
		return ControllerUtils.index(service);
	}

	@RequestMapping(value = "/sites", method = RequestMethod.POST)
	public ResponseEntity<SiteVo> create(@RequestBody SiteVo input) {
		return ControllerUtils.create(input, service);
	}

	@RequestMapping(value = "/sites" + "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<SiteVo> update(@PathVariable("id") String id, @RequestBody SiteVo input) {
		return ControllerUtils.update(id, input, service);
	}

	@RequestMapping(value = "/sites" + "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<SiteVo> destroy(@PathVariable("id") String id) {
		return ControllerUtils.destroy(id, service);
	}

	@RequestMapping(value = "/sites" + "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SiteVo> view(@PathVariable("id") String id) {
		return ControllerUtils.view(id, service);
	}

}

package com.bhg.pipeServer.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhg.pipeServer.vo.SiteConfigVo;

@RestController
public class KafkaConfigController {

	@RequestMapping(value = "/kafkaConfig", method = RequestMethod.GET)
	public ResponseEntity<SiteConfigVo> index() {
		return new ResponseEntity<SiteConfigVo>(new SiteConfigVo(), HttpStatus.OK);
	}
}

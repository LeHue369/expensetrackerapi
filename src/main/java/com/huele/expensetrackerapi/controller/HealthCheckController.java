package com.huele.expensetrackerapi.controller;

import com.huele.expensetrackerapi.entity.VersionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

	@GetMapping("/version")
	public ResponseEntity<VersionModel> version(){
		VersionModel versionModel = new VersionModel();
		versionModel.setVersion("1.0");
		return new ResponseEntity<VersionModel>(versionModel, HttpStatus.OK);
	}
}

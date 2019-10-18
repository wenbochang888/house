package com.tianya.web;

import com.tianya.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Chang
 * @Date: 2018/11/2
 */
@RestController
public class HouseWeb {

	@Autowired
	private HouseService houseService;

	@GetMapping("/")
	public String index() {
		return "Hello World";
	}

	@GetMapping("/comment")
	public String comment() {
		houseService.writeFile();
		return "ok";
	}
}













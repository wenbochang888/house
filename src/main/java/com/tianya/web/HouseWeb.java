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
	HouseService test;

	@GetMapping("/")
	public String index() {
		return "Hello World";
	}

	@GetMapping("/test")
	public String test() {
		String res = test.test();
		return res;
	}

	@GetMapping("/comment")
	public String comment() {
		test.writeFile();
		return "ok";
	}
}













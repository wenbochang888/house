package com.tianya.controller;

import com.tianya.service.HouseService;
import com.tianya.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Auther: Chang
 * @Date: 2018/11/12
 */
@Controller
@Slf4j
public class HouseController {

	@Autowired
	private HouseService houseService;

	@RequestMapping("/")
	public String index() {
		return "Hello World";
	}

	@RequestMapping(value = "/get/comment")
	public String getComments() {
		// 第一步获取评论
		List<String> comment = houseService.getComment();
		// 第二步，转化为md格式
		List<String> commentMd = houseService.transferStrToMD(comment);
		// 第三步，写入文件。默认写入D盘中，文件名为house.md
		FileUtils.writeFile(commentMd);
		return "ok";
	}
}

package com.tianya.controller;

import com.tianya.service.HouseService;
import com.tianya.util.FileUtils;
import com.tianya.util.SyncUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: Chang
 * @Date: 2018/11/12
 */
@RestController
@Slf4j
public class HouseController {

	@Autowired
	private HouseService houseService;

	@Autowired
	private SyncUtils syncUtils;

	@RequestMapping("/")
	public String index(String url, String page) {
		log.info("url = {}, page = {}", url, page);
		syncUtils.submit(() -> getComments(url, page));
		log.info("已经提交申请，请求后续去查看");
		return "已经提交申请，请求后续去查看";
	}

	@RequestMapping(value = "/get/comment")
	public String getComments(String url, String page) {
		// 第一步获取评论
		List<String> comment = houseService.getComment(url, NumberUtils.toInt(page));
		// 第二步，转化为md格式
		List<String> commentMd = houseService.transferStrToMD(comment);
		// 第三步，写入文件。默认写入D盘中，文件名为house.md
		FileUtils.writeFile(commentMd, url);
		return "Hello World";
	}
}

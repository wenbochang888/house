package com.tianya.controller;

import com.tianya.service.HouseService;
import com.tianya.util.FileUtils;
import com.tianya.util.SyncUtils;
import com.youbenzi.md2.export.FileFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

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
		try {
			// 第一步获取评论
			List<String> comment = houseService.getComment(url, NumberUtils.toInt(page));
			// 第二步，转化为md格式
			List<String> commentMd = houseService.transferStrToMD(comment);
			// 第三步，写入文件。默认写入D盘中，文件名为house.md
			String fromPath = FileUtils.writeFile(commentMd, url);
			String toPath = fromPath.substring(0, fromPath.length() - 2) + "pdf";
			log.info("fromPath = {}, toPath = {}", fromPath, toPath);
			FileFactory.produce(new File(fromPath), toPath);
		} catch (Exception e) {
			log.info("爬虫失败.....请联系微信公众号: 程序员博博");
		}
		return "Hello World";
	}

	public static void main(String[] args) {
		try {
			FileFactory.produce(new File("f://house.md"), "f://house.pdf");
		} catch (FileNotFoundException e) {
			log.info("file no found e= {}", e);
		} catch (UnsupportedEncodingException e) {
			log.info("UnsupportedEncodingException e = {}", e);
		}
	}
}

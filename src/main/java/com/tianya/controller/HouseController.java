package com.tianya.controller;

import com.tianya.service.HouseService;
import com.tianya.util.FileUtils;
import com.tianya.util.SyncUtils;
import com.youbenzi.md2.export.FileFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
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
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(page)) {
			return "请提供正确的参数";
		}
		String uuid = url + "-" + ThreadLocalRandom.current().nextInt(100, 999);
		log.info("url = {}, page = {}, uuid = {}", url, page, uuid);
		syncUtils.submit(() -> getComments(url, page, uuid));
		log.info("已经提交申请，请求后续去查看");
		return "已经提交申请，请求后续去查看 请您在十分钟后访问 120.78.159.149:8089/download?name=" + uuid;
	}

	@RequestMapping(value = "/get/comment")
	public String getComments(String url, String page, String uuid) {
		try {
			// 第一步获取评论
			List<String> comment = houseService.getComment(url, NumberUtils.toInt(page));
			if (CollectionUtils.isEmpty(comment)) {
				throw new Exception("URL有误，请确认后在上传");
			}
			// 第二步，转化为md格式
			List<String> commentMd = houseService.transferStrToMD(comment);
			// 第三步，写入文件。默认写入D盘中，文件名为house.md
			String fromPath = FileUtils.writeFile(commentMd, uuid);
			String toPath = fromPath.substring(0, fromPath.length() - 2) + "pdf";
			log.info("fromPath = {}, toPath = {}", fromPath, toPath);
			FileFactory.produce(new File(fromPath), toPath);
		} catch (Exception e) {
			log.info("爬虫失败.....请联系微信公众号: 程序员博博 e = {}", e);
		}
		return "Hello World";
	}

	@RequestMapping("/download")
	public void readImgUrl(HttpServletResponse response, String name) {
		try {
			File file = new File("/home/tomcat/apache-tomcat-8.5.23/workspace/download/" + name + ".pdf");
			byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray(file);
			if (bytes == null) {
				log.error("读取文件出错,该文件可能不存在");
			}
			// 文件进度条
			response.setHeader("Content-Length", file.length() + "");
			// 文件名字
			response.setHeader("Content-Disposition", "attachment;fileName=" + name + ".pdf");
			// 弹出下载框
			response.setContentType("application/octet-stream");
			// 写入流
			response.getOutputStream().write(bytes);
			// 刷新
			response.flushBuffer();
			log.info("成功读取到了文件，位置为：{}", name);
		} catch (IOException e) {
			log.error("读取文件出错，e = {}", e);
		}
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

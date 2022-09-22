package com.tianya.controller;

import com.tianya.service.HouseService;
import com.tianya.util.FileUtils;
import com.tianya.util.MDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class HouseController {

	@Autowired
	private HouseService houseService;


	/**
	 * 以KK帖子为例：https://bbs.tianya.cn/m/post_author-house-252774-1.shtml
	 * url = 252774
	 * page = 626
	 *
	 *
	 * @param id
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/get")
	public String get(String id, String page) {
		try {
			// 第一步获取评论
			List<String> comment = houseService.getComment(id, NumberUtils.toInt(page));
			if (CollectionUtils.isEmpty(comment)) {
				throw new Exception("URL有误，请确认后在上传");
			}
			// 第二步，转化为md格式
			List<String> commentMd = MDUtils.transferStrToMD(comment);
			// 第三步，写入文件。默认写入D盘中，文件名为house.md
			String fromPath = FileUtils.writeFile(commentMd, "file");
			String toPath = fromPath.substring(0, fromPath.length() - 2) + "pdf";
			log.info("fromPath = {}, toPath = {}", fromPath, toPath);
			//FileFactory.produce(new File(fromPath), toPath);
		} catch (Exception e) {
			log.warn("e = {}", e.getMessage(), e);
		}
		return "Hello World";
	}

}

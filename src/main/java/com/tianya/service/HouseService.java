package com.tianya.service;

import com.tianya.controller.HouseController;
import com.tianya.util.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: Chang
 * @Date: 2018/11/2
 */
@Service
@Slf4j
public class HouseService {

	public static final String PREFIX = "https://bbs.tianya.cn/m/post_author-house-447880-";
	public static final String SUFFIX = ".shtml";

	/**
	 * 转化为markdown格式
	 * @return
	 */
	public List<String> transferStrToMD(List<String> comment) {
		if (CollectionUtils.isEmpty(comment)) {
			return Collections.EMPTY_LIST;
		}
		List<String> res = comment;
		for (int i = 0; i < res.size(); i++) {
			res.set(i, "    " + "    " + res.get(i) + "\n");
		}
		return res;
	}

	/**
	 * 获取评论
	 * @return
	 */
	public List<String> getComment() {
		List<String> res = new ArrayList<>();
		// 92是固定的，帖子的总数
		log.info("开始请求天涯帖子.....");
		for (int i = 1; i <= 167; i++) {
			String url = getUrl(i);
			String content = HttpMethod.get(url);
			getParse(content, res);
			log.info("请求帖子第 " + i + "  行");
		}
		log.info("结束请求天涯帖子.....");
		return res;
	}

	/**
	 * 得到url地址
	 * @param i
	 * @return
	 */
	private String getUrl(int i) {
		int pos = 5 * i - 4;
		String res = PREFIX + pos + SUFFIX;
		return res;
	}

	/**
	 * 进行数据清理
	 * @param content
	 * @param res
	 */
	private void getParse(String content, List<String> res) {
		Document doc = Jsoup.parse(content);
		Elements links = doc.getElementsByClass("bd");
		for (Element link : links) {
			String str = link.toString();
			Pattern pattern = Pattern.compile("<p>[.\\s\\S]+?div");
			Matcher m = pattern.matcher(str);
			while (m.find()) {
				String s = m.group();
				s = s.replaceAll("<", "");
				s = s.replaceAll(">", "");
				s = s.replaceAll("/", "");
				s = s.replaceAll("p", "");
				s = s.replaceAll("div", "");
				s = s.replaceAll("\n", "");
				res.add(s);
			}
		}
	}
}









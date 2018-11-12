package com.tianya.controller;

import com.tianya.util.HttpMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: Chang
 * @Date: 2018/11/12
 */
@Controller
public class HouseController {

	public static final String PREFIX = "https://bbs.tianya.cn/m/post_author-house-252774-";
	public static final String SUFFIX = ".shtml";

	@Autowired
	HttpMethod httpMethod;

	/**
	 * 获取评论
	 * @return
	 */
	public List<String> getComment() {
		List<String> res = new ArrayList<>();
		// 92是固定的，帖子的总数
		for (int i = 1; i <= 92; i++) {
			String url = getUrl(i);
			String content = httpMethod.get(url);
			getParse(content, res);
		}
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

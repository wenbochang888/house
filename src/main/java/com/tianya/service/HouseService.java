package com.tianya.service;

import com.tianya.util.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class HouseService {

	public static final String PREFIX = "https://bbs.tianya.cn/m/post_author-house-";
	public static final String SUFFIX = ".shtml";


	/**
	 * 获取评论
	 * @return
	 */
	public List<String> getComment(String id, int wholePage) {
		List<String> res = new ArrayList<>();
		log.info("开始请求天涯帖子.....");
		int page = (wholePage + 4) / 5;

		for (int i = 1; i <= page; i++) {
			String url = getUrl(i, id);
			String content = HttpMethod.get(url);
			if (StringUtils.isEmpty(content)) {
				continue;
			}
			parse(content, res);
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
	private String getUrl(int i, String id) {
		int pos = 5 * i - 4;
		String res = PREFIX + id + "-" + pos + SUFFIX;
		return res;
	}

	/**
	 * 进行数据清理
	 * @param content
	 * @param res
	 */
	private void parse(String content, List<String> res) {
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









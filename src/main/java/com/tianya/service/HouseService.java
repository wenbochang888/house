package com.tianya.service;

import com.tianya.util.HttpMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: Chang
 * @Date: 2018/11/2
 */
@Service
public class HouseService {

	public static final String PREFIX = "https://bbs.tianya.cn/m/post_author-house-252774-";
	public static final String SUFFIX = ".shtml";

	@Autowired
	HttpMethod http;

	public String test() {
		String url = "https://bbs.tianya.cn/m/post_author-house-252774-1.shtml";
		String res = http.get(url);
		return res;
	}

	public void writeFile() {
		String PREFIX = "### ==**";
		String SUFFIX = "楼: **==" + "\n";

		List<String> res = getMarkDown();
		FileOutputStream fos = null;
		int cnt = 1;
		try {
			File f1 = new File("E:/house2.txt");
			fos = new FileOutputStream(f1, true);
			for (String s : res) {
				// markdown格式
				String ss = PREFIX + cnt + SUFFIX + "\n";
				fos.write(ss.getBytes());
				// 真正数据
				fos.write(s.getBytes());
				cnt++;
			}
			fos.close();
			System.out.println("输入完成");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getMarkDown() {

		List<String> res = getContent();
		for (int i = 0; i < res.size(); i++) {
			res.set(i, "    " + "    " + res.get(i) + "\n");
		}
		return res;
	}

	public List<String> getContent() {

		List<String> res = new ArrayList<>();
		// 92是固定的，帖子的总数
		for (int i = 1; i <= 92; i++) {
			String url = getUrl(i);
			String content = http.get(url);
			getParse(content, res);
		}
		return res;
	}

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

	private String getUrl(int i) {
		int pos = 5 * i - 4;
		String res = PREFIX + pos + SUFFIX;
		return res;
	}
}









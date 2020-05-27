package com.tianya.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author changwenbo
 * @date 2019/10/18 16:58
 */
@Slf4j
public class FileUtils {

	private static final String PREFIX = "### **";
	private static final String SUFFIX = "楼: **" + "\n";

	/** 写入文件中，转化为PDF文档 */
	public static String writeFile(List<String> res, String uuid) {
		String path = "/home/tomcat/apache-tomcat-8.5.23/workspace/download/" + uuid + ".md";
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(path)))) {
			int cnt = 1;
			log.info("开始写入磁盘....uuid = {}", uuid);
			for (String s : res) {
				// markdown格式
				String ss = PREFIX + cnt + SUFFIX + "\n";
				bos.write(ss.getBytes());
				// 真正数据
				bos.write(s.getBytes());
				cnt++;
			}
			log.info("写入磁盘成功...uuid = {}", uuid);
		} catch (Exception e) {
			log.error("写入文件出错：" + e.getMessage());
		}
		return path;
	}
}

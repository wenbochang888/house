package com.tianya.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @author changwenbo
 * @date 2019/10/18 16:58
 */
@Slf4j
public class FileUtils {

	private static final String PREFIX = "### ==**";
	private static final String SUFFIX = "楼: **==" + "\n";

	/** 写入文件中，转化为PDF文档 */
	public static void writeFile(List<String> res) {
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("f:\\house.md")))) {
			int cnt = 1;
			for (String s : res) {
				// markdown格式
				String ss = PREFIX + cnt + SUFFIX + "\n";
				bos.write(ss.getBytes());
				// 真正数据
				bos.write(s.getBytes());
				cnt++;
			}
			System.out.println("success");
		} catch (Exception e) {
			log.error("写入文件出错：" + e.getMessage());
			e.printStackTrace();
		}
	}
}

package com.tianya.service;

import com.tianya.controller.HouseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: Chang
 * @Date: 2018/11/2
 */
@Service
@Slf4j
public class HouseService {

	@Autowired
	private HouseController houseController;

	/** 写入文件中，转化为PDF文档 */
	public void writeFile() {
		String PREFIX = "### ==**";
		String SUFFIX = "楼: **==" + "\n";
		List<String> res = getMarkDown();
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("f:\\house.txt")))) {
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
			e.printStackTrace();
		}
	}

	/**
	 * 转化为markdown格式
	 * @return
	 */
	public List<String> getMarkDown() {
		List<String> res = houseController.getComment();
		for (int i = 0; i < res.size(); i++) {
			res.set(i, "    " + "    " + res.get(i) + "\n");
		}
		return res;
	}
}









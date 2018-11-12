package com.tianya.service;

import com.tianya.controller.HouseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: Chang
 * @Date: 2018/11/2
 */
@Service
public class HouseService {

	@Autowired
	HouseController houseController;

	/**
	 * 写入文件中，转化为PDF文档
	 */
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









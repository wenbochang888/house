package com.tianya.util;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class MDUtils {

	/**
	 * 转化为markdown格式
	 * @return
	 */
	public static List<String> transferStrToMD(List<String> comment) {
		if (CollectionUtils.isEmpty(comment)) {
			return Collections.EMPTY_LIST;
		}
		List<String> res = comment;
		for (int i = 0; i < res.size(); i++) {
			res.set(i, "    " + "    " + res.get(i) + "\n");
		}
		return res;
	}

}

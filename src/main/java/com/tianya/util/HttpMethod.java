package com.tianya.util;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;

/**
 * @Auther: Chang
 * @Date: 2018/10/3
 */
public class HttpMethod {

	public static String get(String url) {
		HttpConfig config = getConfig();
		String content = null;
		try {
			content = HttpClientUtil.get(config.url(url));
		} catch (HttpProcessException e) {
			e.printStackTrace();
		}
		return content;
	}

	private static HttpConfig getConfig() {
		String cookies = "JSESSIONID=abcUIPubjzh36dpJGfk_w; __guid=1200512923; __guid2=1200512923; deid=a4f1521763def5a36abdc1b524a1be7e; sso=r=696161994&sid=&wsid=2C275020FCBB2CF94EA1AAF8D1CA21D9; user=w=wenber888&id=137776589&f=1; temp=k=282632486&s=&t=1578569735&b=e18fe0b747008162dfc019ab54d61254&ct=1578569735&et=1581161735; right=web4=n&portal=n; temp4=rm=8346ced28c886d2dc2be32e645d2afab; u_tip=; vip=282632486%3D0; __u_a=v2.3.0; time=ct=1578569874.206; __ptime=1578569874256; ty_msg=1578570149252_137776589_0_0_0_0_0_2_0_0_0_0_0; bbs_msg=1578570149481_137776589_0_0_0_0";
		String referer = "https://www.baidu.com";
		Header[] headers= HttpHeader.custom().cookie(cookies).referer(referer).build();
		HttpConfig config = HttpConfig.custom().headers(headers);
		return config;
	}
}





package com.tianya.util;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.springframework.stereotype.Component;

/**
 * @Auther: Chang
 * @Date: 2018/10/3
 */
@Component
public class HttpMethod {

	public String get(String url) {
		HttpConfig config = getConfig();
		String content = null;
		try {
			content = HttpClientUtil.get(config.url(url));
		} catch (HttpProcessException e) {
			e.printStackTrace();
		}
		return content;
	}

	private HttpConfig getConfig() {
		String cookies = "__guid=414814951; __guid2=414814951; user=w=wenber888&id=137776589&f=1; right=web4=n&portal=n; __u_a=v2.2.6; sso=r=1648448059&sid=&wsid=B45353929B80073F357A507D1C991623; temp=k=447189638&s=&t=1541148185&b=4192447767437b928489e63e3913ef52&ct=1541148185&et=1543740185; temp4=rm=16a6cb6e3414a9655ac76f341c44e1e4; u_tip=137776589=0; vip=447189638%3D0; JSESSIONID=abc_q2y-0bQSr8Zw1LtBw; time=ct=1541148308.213; __ptime=1541148308504; ty_msg=1541148523985_137776589_2_0_0_0_0_0_2_0_0_0_0";
		String referer = "https://www.baidu.com";
		Header[] headers= HttpHeader.custom().cookie(cookies).referer(referer).build();
		HttpConfig config = HttpConfig.custom().headers(headers);
		return config;
	}
}





package com.tianya.util;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;

@Slf4j
public class HttpMethod {

	public static String get(String url) {
		HttpConfig config = getConfig();
		String content = null;
		try {
			content = HttpClientUtil.get(config.url(url));
		} catch (Exception e) {
			log.error("http get失败 e = {}", e);
		}
		return content;
	}

	private static HttpConfig getConfig() {
		String cookies = "Hm_lvt_bc5755e0609123f78d0e816bf7dee255=1590380369; __auc=eeb9a7fd1724a0e962f9fb718b4; __cid=CN; __guid=469014980; __guid2=469014980; deid=57432c15994418404fc637b4418eb34c; sso=r=1609006141&sid=&wsid=9ECDE10F1335A0C76BE47F3815679F80; user=w=wenber888&id=137776589&f=1; temp=k=751567138&s=&t=1590380376&b=0e281318d4992eae9d4b0486a1687b28&ct=1590380376&et=1592972376; right=web4=n&portal=n; temp4=rm=9d0be7aa8e62f78dfa10caaa528ef2da; u_tip=; vip=751567138%3D0; JSESSIONID=abcYhud_K6Iwv_Iis1ljx; __asc=079a9f731724bd526f10e329f0d; __u_a=v2.3.0; time=ct=1590410572.735; __ptime=1590410572786; Hm_lpvt_bc5755e0609123f78d0e816bf7dee255=1590410573; ty_msg=1590410602941_137776589_0_0_0_0_0_0_0_0_0_0_0; bbs_msg=1590410603184_137776589_0_0_0_0";
		String referer = "https://www.baidu.com";
		Header[] headers= HttpHeader.custom().cookie(cookies).referer(referer).build();
		HttpConfig config = HttpConfig.custom().headers(headers);
		return config;
	}
}





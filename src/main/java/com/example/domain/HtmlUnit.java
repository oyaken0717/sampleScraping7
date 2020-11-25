package com.example.domain;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class HtmlUnit {

	// ■ https://qiita.com/mix/items/67c8c307c2c954bfe9f8
	public Document getAjaxPage(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.waitForBackgroundJavaScript(10000);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getCookieManager().setCookiesEnabled(true);
		String page = webClient.getPage(url);
		// 後はごにょごにょしても良いけど、getメソッドって書いたのでpage.asXml()で終わってみる
		return Jsoup.parse(page);
//		return page.asXml();
	}

}

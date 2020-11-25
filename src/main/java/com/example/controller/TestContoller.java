package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.HtmlUnit;

@Controller
@RequestMapping("")
public class TestContoller {
	
	@Autowired
	private HtmlUnit htmlUnit;

	@RequestMapping("")
	public String toStart() {
		
		
		String siteUrl;
		Document documents;
		Elements noJobMessage;
		Elements elementTotalJobCount;
		String siteName;
		Elements companyName;
		Elements jobType;
		String codingLanguages;
		
		Elements table;
		Elements rows;
		Element row;
		Elements location;
		
		String phoneNumber;
		Elements businessDetails;
		Elements url;
		String firstUrl;
		String latterUrl;
		String published;
		
		Integer count = 1;
		Integer totalJobCount = 0;
		Integer displayCount = 50;
		Job job;
		List<Job> jobList = new ArrayList<>();

		String codingLanguage = null;
		List<String> codingLanguageNameList = Arrays.asList("Java");
		
		// ■ 検索言語の設定(for1個目)
		try {
			for (int k = 0; k < codingLanguageNameList.size(); k++) {
				codingLanguage = codingLanguageNameList.get(k);;

				// ■ for2個目
				for (int j = 1; j <= count; j++) {
//■ 検索URLの設定
					siteUrl = "https://job.j-sen.jp/search/?s%5Boccupation%5D%5Bfirst%5D%5B0%5D=7&s%5Bcity%5D%5Bpref%5D%5B0%5D=13&s%5Bemployment%5D%5B0%5D=permanent&s%5Bemployment%5D%5B1%5D=temporary&s%5Binhellowork%5D=no&s%5Bindustry%5D%5Bbig%5D%5B0%5D=1&s%5Bsalary%5D=&s%5Bfreeword%5D="+codingLanguage;
					if (count >= 2) {
						siteUrl = "https://job.j-sen.jp/search/?s%5Boccupation%5D%5Bfirst%5D%5B0%5D=7&s%5Bcity%5D%5Bpref%5D%5B0%5D=13&s%5Bemployment%5D%5B0%5D=permanent&s%5Bemployment%5D%5B1%5D=temporary&s%5Binhellowork%5D=no&s%5Bindustry%5D%5Bbig%5D%5B0%5D=1&s%5Bsalary%5D=&s%5Bfreeword%5D="+codingLanguage+"&page="+count;
					}

//■ jsoupの実行
//					documents = Jsoup.connect(siteUrl).get();
//■ ■
					documents = htmlUnit.getAjaxPage(siteUrl);
					
					noJobMessage = documents.select(".l-contents .l-main .js-searchWork .c-card .c-card__inner--size_m .c-card__row--size_m .p-heading--a p");
					// ■ 「条件に合った求人が 0件でした」が表示されなければ抽出
					if (noJobMessage.isEmpty()) {
//■ ページ数の取得
						elementTotalJobCount = documents.select("#search-works .l-main .search-pager-wrap");
						System.out.println(elementTotalJobCount.first().children());
//						elementTotalJobCount = documents.select(".l-contents .l-main .search-pager-wrap .js-searchPager .u-vertical-gutter .p-paging__text .u-text-size-l");

						String result = elementTotalJobCount.text().replace(" ", "").replace("件", "");
						System.out.println(result);
//						totalJobCount = Integer.parseInt(result);

						float totalPage = totalJobCount / displayCount;
						if (totalJobCount / displayCount != 0) {
							totalPage++;
						}
						count = (int) totalPage;

//■ 抽出結果を要素ごとに分ける			
//						siteName = "転職ナビ";
//						companyName = documents.select(".l-contents .l-main .js-searchWork .u-vertical-gutter .p-xl-media .p-xl-media__header .p-xl-media__row .p-xl-media__sub a");
//						jobType = documents.select(".l-contents .l-main .js-searchWork .u-vertical-gutter .p-xl-media .p-xl-media__header .p-xl-media__row .js-jstaffSearchCassetteItem");
//
//						codingLanguages = codingLanguage;
//■ table						
//						table = documents.select(".l-contents .l-main .js-searchWork .u-vertical-gutter .p-xl-media .p-xl-media__body .p-xl-media__row .c-table");
//						rows = table.select("tr");
//						row = rows.get(3);
//						location = row.select("td");
//						phoneNumber = "";

//						row = rows.get(0);
//						businessDetails = row.select("td");

//						url = documents.select(".l-contents .l-main .js-searchWork .u-vertical-gutter .p-xl-media .p-xl-media__header .p-xl-media__row .js-jstaffSearchCassetteItem");
//						firstUrl = "https://job.j-sen.jp/";
//						latterUrl = "&aroute=0&caroute=0701";

//						published = "";

//■ Jobオブジェクトに格納(for3個目)
//						for (int i = 0; i < companyName.size(); i++) {
//
//							job = new Job();
//
//							job.setSiteName(siteName);
//							job.setCompanyName(companyName.text().replace(" ", ""));
//							job.setJobType(jobType.text().replace(" ", ""));
//
//							job.setCodingLanguages(codingLanguages);
//							job.setLocation(location.text().replace(" ", ""));
//							job.setPhoneNumber(phoneNumber);
//
//							job.setBusinessDetails(businessDetails.text());
//
//							job.setUrl(firstUrl + url.attr("href"));
////							if (url.get(i).attr("href").contains("caroute=0701")) {
////
////							} else {
////								job.setUrl(firstUrl + url.attr("href") + latterUrl);
////							}
//							job.setPublished(published);
//
////■ リストにJobオブジェクトを格納
//							jobList.add(job);
//
//						}// ■ for3個目の終わり
					}// ■ ifの終わり
				}// ■ for2個目の終わり
				count = 1;
			}// ■ for1個目の終わり
		} catch (IOException e) {
			e.printStackTrace();
		}

	return"start";

	}

	@RequestMapping("/finish")
	public String finish() {
		return "finish";
	}
}

package colt.jan_brilliantsys.realestatechecker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MyAPR5168Crawler {
//	private String fullUrl="https://price.houseprice.tw/list/新北市_city/住宅大樓%5E華廈-無電梯公寓-套房-透天厝%5E別墅-店面-辦公-廠辦%5E工廠_use/"
//			+ "新北市板橋區府中路_kw/date-desc_sort/";
//	private String miauLiFullUrl="https://price.houseprice.tw/list/苗栗縣_city/無電梯公寓-套房-透天厝%5E別墅_use/苗栗縣頭份市忠孝一路_kw/date-desc_sort/";
	private static final String url="https://price.houseprice.tw/list/";
	//案件地區, 案件地址
	private static String caseRegion, caseAddress;
	//案件單價
	private static double caseUnitPrice;
	//案件用途標籤陣列
	private static final String[] usageTag = {"住宅大樓%5E華廈", "無電梯公寓", "套房", "透天厝%5E別墅", "店面", "辦公", "廠辦%5E工廠", "土地", "車位"};
	//案件用途標籤索引
	private static int caseUsageTagIndex;
	private static boolean isCaseUsage=false;
	
	// TODO: Block this method after crawlers modified.
	public void crawleTest() {
		double APRunitPrice=Double.MAX_VALUE;
		Document doc;
		try {
			doc = SSLHelper.getConnection(url+"高雄市_city/住宅大樓%5E華廈_use/左營區果峰街_kw/date-desc_sort/").get();
			System.out.println(url+"高雄市_city/住宅大樓%5E華廈_use/左營區果峰街_kw/date-desc_sort/");
			Elements datas=doc.getElementsByTag("tbody");
			System.out.println("Got <tbody>?: "+!datas.isEmpty());
			try {
			APRunitPrice=Double.parseDouble(datas.get(0).getElementsByTag("tr")
					.get(0).getElementsByTag("td")
					.get(4).getElementsByTag("div")
					.get(0).getElementsByTag("span")
					.get(0).text());
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Test APR case info not been crawled!");
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("APRunitPrice:"+APRunitPrice);
	}

	//接收端收到null需警告"無符合比對條件APR案件"
	//接收RealEstateCase物件，依其成員決定生成爬取對象網址
	public static RealEstateCase checkNewestAPRCase(RealEstateCase checkCase) {
		String caseName = null, caseAddress = null, caseUrlId = null, caseRegion = null, convertCaseAddress=null, convertCaseRegion;
		int caseUsageTagIndex=Integer.MAX_VALUE;
		double unitPrice=Double.MAX_VALUE;
		Document doc;
		//轉換地址編碼為UTF-8，證實無效
		try{
		convertCaseAddress= URLEncoder.encode(checkCase.getCaseAddress().substring(3),"UTF-8");
		convertCaseRegion= URLEncoder.encode(checkCase.getCaseRegion(),"UTF-8");
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		try {
			if(checkCase.getCaseUsageTagIndex()!=Integer.MAX_VALUE) {
				doc = SSLHelper.getConnection(url+checkCase.getCaseRegion()+"_city/"+
												usageTag[checkCase.getCaseUsageTagIndex()]+"_use/"+
												convertCaseAddress+"_kw/date-desc_sort/").get();
				System.out.println(url+checkCase.getCaseRegion()+"_city/"+
									usageTag[checkCase.getCaseUsageTagIndex()]+"_use/"+
									convertCaseAddress+"_kw/date-desc_sort/");
				}else {
				doc = SSLHelper.getConnection(url+checkCase.getCaseRegion()+"_city/"+
												convertCaseAddress+"_kw/date-desc_sort/").get();
				System.out.println(url+checkCase.getCaseRegion()+"_city/"+
									convertCaseAddress+"_kw/date-desc_sort/");
			}
			try {
				Elements datas=doc.getElementsByTag("tbody");
				//問題就會發生在這裡，某些convertCaseAddress導入doc後<tbody>就撈不到東西
				System.out.println("Is this <tbody>Elements empty: "+datas.isEmpty());
				unitPrice=Double.parseDouble(datas.get(0).getElementsByTag("tr")
						.get(0).getElementsByTag("td")
						.get(4).getElementsByTag("div")
						.get(0).getElementsByTag("p")
						.get(0).getElementsByTag("span")
						.get(0).text());
				caseAddress=datas.get(0).getElementsByTag("tr")
						.get(0).getElementsByTag("td")
						.get(1).getElementsByTag("div")
						.get(0).getElementsByTag("p")
						.get(0).text();
				String rawInnerHtml=datas.get(0).getElementsByTag("tr").get(0).html();
				caseUrlId=rawInnerHtml.substring(rawInnerHtml.indexOf("id=")+4, rawInnerHtml.indexOf("id=")+11);
				caseName=caseAddress;
				caseRegion=caseAddress.substring(0, 3);
				caseUsageTagIndex=checkCase.getCaseUsageTagIndex();
			}catch(IndexOutOfBoundsException e) {
//				System.out.println("APR case info not been crawled!");
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(caseAddress==null||unitPrice==Double.MAX_VALUE) {
//			System.out.println("No any APR case been crawled!");
			return null;
		}
		else {
			RealEstateCase myAPRCase=new RealEstateCase(caseName, caseAddress, "https://price.houseprice.tw/dealcase/"+
														caseUrlId, caseRegion, caseUsageTagIndex,unitPrice);
			return myAPRCase;
		}
	}

	//Won't use this.
	public static double upsOrDowns(RealEstateCase checkCase) {
		caseRegion=checkCase.getCaseRegion();
		caseUnitPrice=checkCase.getUnitPrice();
		caseAddress=checkCase.getCaseAddress().substring(3);
		if(checkCase.getCaseUsageTagIndex()!=Integer.MAX_VALUE) {
			caseUsageTagIndex=checkCase.getCaseUsageTagIndex();
			isCaseUsage=true;
		}else {
			isCaseUsage=false;
		}
		double APRunitPrice=Double.MAX_VALUE;
		Document doc;
		try {
			if(isCaseUsage){
				doc = SSLHelper.getConnection(url+caseRegion+"_city/"+usageTag[caseUsageTagIndex]+"_use/"+caseAddress+
						"_kw/date-desc_sort/").get();
			}else {
				doc = SSLHelper.getConnection(url+caseRegion+"_city/"+caseAddress+"_kw/date-desc_sort/").get();
			}
			Elements datas=doc.getElementsByTag("tbody");
			try {
				APRunitPrice=Double.parseDouble(datas.get(0).getElementsByTag("tr")
						.get(0).getElementsByTag("td")
						.get(4).getElementsByTag("div")
						.get(0).getElementsByTag("span")
						.get(0).text());
			}catch(IndexOutOfBoundsException e) {
//				System.out.println("APR case info not been crawled!");
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("APRunitPrice:"+APRunitPrice);
		double discount=0;
		if(APRunitPrice!=Double.MAX_VALUE) {
			discount=(APRunitPrice-caseUnitPrice)/APRunitPrice;
		}
		return discount;
	}
}

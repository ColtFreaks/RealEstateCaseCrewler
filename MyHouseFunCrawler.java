package colt.jan_brilliantsys.realestatechecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyHouseFunCrawler implements Runnable{

		private ArrayList<RealEstateCase> cheapest10=new ArrayList<RealEstateCase>();
		private ArrayList<RealEstateCase> houseFunCases=new ArrayList<RealEstateCase>();
		private final String url="https://buy.housefun.com.tw/";
		//生成目標網址用的 案件地區標籤, 案件類型標籤, 案件用途標籤
		private String regionTag,typeTag,usageTag;
 		public static final String[] reg1 = {"台北市","新北市","基隆市","宜蘭縣","新竹市","新竹縣","桃園市","苗栗縣","台中市","彰化縣","南投縣","嘉義市",
								"嘉義縣","雲林縣","台南市","高雄市","澎湖縣","金門縣","屏東縣","台東縣","花蓮縣","連江縣"};
		public static final String[][] reg2 = {{"北投區","士林區","大同區","中山區","松山區","內湖區","南港區","萬華區","中正區","大安區","信義區","文山區"},
				{"中和區","永和區","板橋區","新店區","新莊區","土城區","三重區","蘆洲區","五股區","泰山區","林口區","鶯歌區","三峽區",
						"樹林區","八里區","淡水區","三芝區","石門區","金山區","萬里區","汐止區","平溪區","瑞芳區","雙溪區","貢寮區","石碇區",
						"深坑區","坪林區","烏來區"},
				{"七堵區","安樂區","中正區","暖暖區","信義區","仁愛區","中山區"},
				{"宜蘭市","羅東鎮","蘇澳鎮","頭城鎮","三星鄉","礁溪鄉","冬山鄉","員山鄉","壯圍鄉","五結鄉","大同鄉","南澳鄉"},
				{"東　區","北　區","香山區"},
				{"竹北市","竹東鎮","新埔鎮","關西鎮","湖口鄉","新豐鄉","寶山鄉","芎林鄉","橫山鄉","北埔鄉","峨眉鄉","尖石鄉","五峰鄉"},
				{"桃園區","中壢區","平鎮區","八德區","楊梅區","大溪區","蘆竹區","龜山區","龍潭區","大園區","觀音區","新屋區","復興區"},
				{"苗栗市","頭份市","竹南鎮","後龍鎮","苑裡鎮","卓蘭鎮","通霄鎮","造橋鄉","南庄鄉","三灣鄉","三義鄉","大湖鄉","公館鄉",
						"獅潭鄉","頭屋鄉","銅鑼鄉","泰安鄉","西湖鄉"},
				{"西屯區","南屯區","北屯區","北　區","西　區","中　區","東　區","南　區","沙鹿區","龍井區","大肚區","烏日區","霧峰區",
						"大里區","太平區","豐原區","石岡區","新社區","東勢區","和平區","后里區","神岡區","大雅區","潭子區","大甲區","大安區",
						"外埔區","清水區","梧棲區"},
				{"彰化市","花壇鄉","芬園鄉","和美鎮","鹿港鎮","伸港鄉","線西鄉","福興鄉","秀水鄉","二林鎮","溪湖鎮","北斗鎮","芳苑鄉",
						"埔鹽鄉","埔心鄉","大城鄉","竹塘鄉","埤頭鄉","溪州鄉","員林市","田中鎮","大村鄉","永靖鄉","社頭鄉","田尾鄉","二水鄉"},
				{"信義鄉","南投市","草屯鎮","埔里鎮","竹山鎮","集集鎮","名間鄉","中寮鄉","國姓鄉","水里鄉","魚池鄉","仁愛鄉","鹿谷鄉"},
				{"東　區","西　區"},
				{"朴子市","太保市","布袋鎮","六腳鄉","東石鄉","義竹鄉","鹿草鄉","水上鄉","大林鎮","溪口鄉","梅山鄉","新港鄉","民雄鄉",
						"竹崎鄉","中埔鄉","番路鄉","大埔鄉","阿里山鄉"},
				{"斗六市","土庫鎮","虎尾鎮","麥寮鄉","台西鄉","東勢鄉","褒忠鄉","四湖鄉","元長鄉","口湖鄉","北港鎮","西螺鎮","斗南鎮",
						"水林鄉","崙背鄉","二崙鄉","莿桐鄉","林內鄉","大埤鄉","古坑鄉"},
				{"安南區","北　區","中西區","安平區","南　區","東　區","永康區","仁德區","歸仁區","關廟區","龍崎區","七股區","佳里區",
						"麻豆區","善化區","大內區","玉井區","楠西區","西港區","安定區","新市區","山上區","新化區","左鎮區","南化區","後壁區",
						"白河區","北門區","學甲區","鹽水區","新營區","柳營區","東山區","將軍區","下營區","六甲區","官田區"},
				{"那瑪夏區","楠梓區","左營區","鼓山區","鹽埕區","旗津區","三民區","前金區","新興區","苓雅區","前鎮區","小港區","鳳山區",
						"仁武區","鳥松區","大寮區","林園區","茄萣區","湖內區","路竹區","永安區","岡山區","彌陀區","梓官區","橋頭區","桃源區",
						"甲仙區","內門區","杉林區","六龜區","阿蓮區","田寮區","旗山區","美濃區","茂林區","燕巢區","大社區","大樹區"},
				{"馬公市","湖西鄉","西嶼鄉","白沙鄉","望安鄉","七美鄉"},
				{"金城鎮","金沙鎮","金湖鎮","金寧鄉","烈嶼鄉","烏坵鄉"},
				{"屏東市","潮州鎮","東港鎮","恆春鎮","枋寮鄉","內埔鄉","長治鄉","萬丹鄉","九如鄉","高樹鄉","佳冬鄉","竹田鄉","新園鄉",
						"麟洛鄉","里港鄉","鹽埔鄉","萬巒鄉","新埤鄉","南州鄉","林邊鄉","崁頂鄉","滿州鄉","枋山鄉","車城鄉","三地門鄉",
						"牡丹鄉","瑪家鄉","琉球鄉","獅子鄉","春日鄉","霧台鄉","泰武鄉","來義鄉"},
				{"台東市","成功鎮","關山鎮","卑南鄉","東河鄉","鹿野鄉","長濱鄉","太麻里鄉","池上鄉","綠島鄉","延平鄉","大武鄉","蘭嶼鄉",
						"海端鄉","金峰鄉","達仁鄉"},
				{"花蓮市","鳳林鎮","玉里鎮","吉安鄉","新城鄉","壽豐鄉","光復鄉","秀林鄉","瑞穗鄉","富里鄉","萬榮鄉","豐濱鄉","卓溪鄉"},
				{"南竿鄉","北竿鄉","莒光鄉","東引鄉"}
						};
		public static final String[] usage = {"住宅","店面","辦公室","廠房","停車位","土地","農舍","倉庫","其他"};
		public static final String[] type = {"電梯大樓","無電梯公寓","別墅,透天厝","套房"};
		private int priceOpenCount=0, priceNotOpenCount=0, caseUsageIndex=Integer.MAX_VALUE;
//		String fullUrl="https://buy.housefun.com.tw/region/新北市-蘆洲區_c/電梯大樓,無電梯公寓,別墅,透天厝_type/店面,住宅_use/?od=UpdateDown";
		
	//TODO: Block this constructor after crawlers modified.
	public MyHouseFunCrawler(int regionIndex1, int regionIndex2) {
		regionTag="region/"+reg1[regionIndex1]+"-"+reg2[regionIndex1][regionIndex2]+"_c/";
		crawlCases();
	}
	//TODO: Block this constructor after crawlers modified.
	public MyHouseFunCrawler() {
		crawlCases();
	}
	
	//call crawlCases inside.--modify a runnable.
	//引入MyHouseFunFilterSetting物件來決定生成爬取對象網址
	public MyHouseFunCrawler(MyHouseFunFilterSetting filter) {
		try {
			//Filter有設定縣市與地區標籤時，依Filter成員組裝案件地區標籤
			if(filter.regionIndex1!=Integer.MAX_VALUE&&filter.regionIndex2!=Integer.MAX_VALUE) {
				regionTag="region/"+reg1[filter.regionIndex1]+"-"+reg2[filter.regionIndex1][filter.regionIndex2]+"_c/";
			}
			if(filter.regionIndex1!=Integer.MAX_VALUE&&filter.regionIndex2==Integer.MAX_VALUE) {
				regionTag="region/"+reg1[filter.regionIndex1]+"_c/";
			}
		}catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
		};
		StringBuilder sbi=new StringBuilder();
		//Filter有設定類型標籤時，依Filter成員設定類型標籤
		if(filter.typeIndex!=Integer.MAX_VALUE) {
			sbi.append(type[filter.typeIndex]);
			sbi.append("_type/");
			typeTag=sbi.toString();
			}
		sbi.setLength(0);
		//Filter有設定使用標籤時，依Filter成員設定使用標籤
		if(filter.usageIndex!=Integer.MAX_VALUE) {
			sbi.append(usage[filter.usageIndex]);
			sbi.append("_use/");
			usageTag=sbi.toString();
			}
		if(filter.getUsageTagIndex()!=Integer.MAX_VALUE) {
		caseUsageIndex=filter.getUsageTagIndex();
		}else {
			//TODO 警告
		}
		//modify a runnable.
		crawlCases();
	}

	private void crawlCases() {
		//TODO: Optimize crawler.
		//最多爬10頁
		for(int i=1; i<=10; i++) {
			try {
				Document doc=SSLHelper.getConnection(url+regionTag+typeTag+usageTag+"?od=UpdateDown&pg="+i).get();
				Elements datas=doc.getElementsByClass("m-list-data");
				for(Element data : datas) {
					//案件坪單價
					String pricetag=data.getElementsByClass("price").get(0).getElementsByClass("Unit-price")
									.get(0).getElementsByClass("wording").get(0).text();
					//案件原始網址
					String toUrl=data.getElementsByClass("casename").get(0).getElementsByTag("a").get(0)
									.attr("href");
					//案件名稱
					String casename=data.getElementsByClass("casename").get(0).getElementsByTag("a").get(0).text();
					//案件地址(之後爬實價用地址作為kw)
					String caseAddress=data.getElementsByClass("address-map").get(0).getElementsByTag("address").get(0).text();
					//案件地區(地址的頭三字)
					String caseRegion=caseAddress.substring(0, 3);
					//TODO: Try crawl usage&type.
					double price=0d;
					//生成RealEstateCase物件並加到houseFunCases ArrayList<RealEstateCase>
					RealEstateCase myCase;
					if(pricetag.contains("萬/坪")) {
						char[] pc =pricetag.toCharArray();
						int cutindex=0;
						for(int j=0; j<pc.length;j++) {
							if (pc[j]=='/') {
								cutindex=j-1;
								break;
							}
						}
						char[] newPc =new char[cutindex];
						for(int k=0; k<cutindex; k++) {
							newPc[k]=pc[k];
						}
						StringBuilder sb = new StringBuilder();
				        for (int l = 0; l < newPc.length; l++) {
				            sb.append(newPc[l]);
				        }
				        try{
				        	price =Double.parseDouble(sb.toString());
				        }catch(NumberFormatException e) {
				        	// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println(sb);
				        }
				        priceOpenCount++;
				        if(caseUsageIndex==Integer.MAX_VALUE) {
				        	myCase=new RealEstateCase(casename, caseAddress, "https://buy.housefun.com.tw"+
				        			toUrl,caseRegion,typeTag+","+usageTag, price);
				        }else {
				        	myCase=new RealEstateCase(casename, caseAddress, "https://buy.housefun.com.tw"+
				        			toUrl,caseRegion,caseUsageIndex, price);
				        }
				        houseFunCases.add(myCase);
				    }else {
				    	priceNotOpenCount++;
				    }
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(i+url+regionTag+typeTag+usageTag);
			}
		}
		//houseFunCases依坪單價排序
		Collections.sort(houseFunCases, new Comparator<RealEstateCase>() {
			@Override
			public int compare(RealEstateCase o1, RealEstateCase o2) {
				return Double.compare(o1.getUnitPrice(), o2.getUnitPrice());
			}
		});
		//拿houseFunCases坪單價前10個便宜的加入cheapest10 ArrayList<RealEstateCase>
		for(int i=0; i<10&&i<houseFunCases.size(); i++) {
			cheapest10.add(houseFunCases.get(i));
		}
	}
	//看爬到的資料比對用
	public static void printListInfo(ArrayList<RealEstateCase> cases) {
		for(RealEstateCase c:cases) {
		String caseName ,caseRegion ,caseAddress, caseUrl;
		int caseUsageIndex;
		double unitPrice;
		caseName=c.getCaseName();
		caseAddress=c.getCaseAddress();
		caseRegion=c.getCaseRegion();
		caseUrl=c.getCaseUrl();
		unitPrice=c.getUnitPrice();
		caseUsageIndex=c.getCaseUsageTagIndex();
		System.out.println("Case Name: "+caseName+", Case Region: "+caseRegion+", Case Address: "+caseAddress+
				", Case URL: "+caseUrl+", Unit Price: "+unitPrice+", Case Usage Tag Index:"+caseUsageIndex);
		}
//		System.out.println("Price Open: "+priceOpenCount+" Price NOT Open: "+priceNotOpenCount);
	}
	public ArrayList<RealEstateCase> getCheapest10() {
		return cheapest10;
	}
	public ArrayList<RealEstateCase> getHouseFunCases() {
		return houseFunCases;
	}

	@Override
	public void run() {
		crawlCases();
	}
}

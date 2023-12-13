類別說明：
1.類別使用目的：
  1.1 RealEstateCase: 儲存房地產案件關鍵資訊用類別
  1.2 FilterSetting: 設計專屬過濾器用的抽象類別，用於後續爬取各別網站時寫的爬蟲類別。目前Project中僅有MyHouseFunFilterSetting一個子類別
  1.3 MyHouseFunFilterSetting: 用於MyHouseFunCrawler建構元，來組裝生成爬取目標網址的類別
  1.4 MyHouseFunCrawler: 透過引入MyHouseFunFilterSetting爬取指定屬性的資訊，組成RealEstateCase物件，存入ArrayList<RealEstateCase>並依RealEstateCase的unitPrice升冪排序
      為得以在Android APP中執行，設計為Runnable
  1.5 MyAPR5168Crawler: 以Static方法checkNewestAPRCase將RealEstateCase導入，爬取位於相同地址（精度只到街道）的時價登錄案例，回傳最近一筆案例的RealEstateCase
  1.6 SSLHelper: 通過SSL憑證
2.操作程序：
  2.1 生成MyHouseFunFilterSetting物件，依好房網過濾選項設定「縣市」、「地區」、「用途」，「類型」，設定後呼叫usageTagAdapter()轉換用於爬取實價登錄的用途索引
  2.2 MyHouseFunCrawler建構元引入MyHouseFunFilterSetting生成MyHouseFunCrawler物件，呼叫crawlCases()生成cheapest10 ArrayList<RealEstateCase>
  2.3 cheapest10 ArrayList<RealEstateCase>以MyAPR5168Crawler.checkNewestAPRCase(RealEstateCase)遍歷回傳對應的最近一筆案例RealEstateCase，生成另一個ArrayList<RealEstateCase>
3. 問題發生：
  執行2.3時，根據導入RealEstateCase的caseAddress，會於MyAPR5168Crawler Line.82發生元素集合為空，無資料可爬取。但以Line.67生成的網址從瀏覽器開啟，又可以找到想爬取的Tag內容
  確定會發生元素集合為空的其中一個設定是MyHouseFunFilterSetting物件regionIndex1=15, regionIndex2=2(高雄市左營區) 

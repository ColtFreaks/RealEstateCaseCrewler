package colt.jan_brilliantsys.realestatechecker;

public class RealEstateCase {

	//案件名稱, 案件地址, 案件來源網址, 案件縣市, 案件使用類型
	protected String caseName, caseAddress, caseUrl, caseRegion, caseUsage;
	//案件使用類型指引(整數最大值為無效)
	protected int caseUsageTagIndex=Integer.MAX_VALUE;
	//案件坪單價
	protected double unitPrice;
	
	public RealEstateCase(String caseName, String caseAddress, String caseUrl, String caseRegion,int caseUsageTagIndex,
							double unitPrice) {
		super();
		this.caseName = caseName;
		this.caseAddress = caseAddress;
		this.caseUrl = caseUrl;
		this.caseRegion = caseRegion;
		this.unitPrice = unitPrice;
		this.caseUsageTagIndex = caseUsageTagIndex;
	}
	
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseAddress() {
		return caseAddress;
	}
	public void setCaseAddress(String caseAddress) {
		this.caseAddress = caseAddress;
	}
	public String getCaseUrl() {
		return caseUrl;
	}
	public void setCaseUrl(String caseUrl) {
		this.caseUrl = caseUrl;
	}
	public String getCaseRegion() {
		return caseRegion;
	}
	public void setCaseRegion(String caseRegion) {
		this.caseRegion = caseRegion;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getCaseUsage() {
		return caseUsage;
	}
	public void setCaseUsage(String caseUsage) {
		this.caseUsage = caseUsage;
	}
	public int getCaseUsageTagIndex() {
		return caseUsageTagIndex;
	}
	public void setCaseUsageTagIndex(int caseUsageTag) {
		this.caseUsageTagIndex = caseUsageTag;
	}

	public void printCase() {
		System.out.println("Case Name:"+caseName+", Case Address:"+caseAddress+", Case URL:"+caseUrl+", Case Region:"+
							caseRegion+", Case Usage Tag Index:"+caseUsageTagIndex+", Unit Price:"+unitPrice);
	}
}

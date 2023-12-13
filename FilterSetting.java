package colt.jan_brilliantsys.realestatechecker;

public abstract class FilterSetting {
	public int regionIndex1=Integer.MAX_VALUE, regionIndex2=Integer.MAX_VALUE; 
	public int usageTagIndex=Integer.MAX_VALUE;
	
	public int getRegionIndex1() {
		return regionIndex1;
	}
	public void setRegionIndex1(int regionIndex1) {
		this.regionIndex1 = regionIndex1;
	}
	public int getRegionIndex2() {
		return regionIndex2;
	}
	public void setRegionIndex2(int regionIndex2) {
		this.regionIndex2 = regionIndex2;
	}
	public int getUsageTagIndex() {
		return usageTagIndex;
	}
	public void setUsageTagIndex(int usageIndex) {
		this.usageTagIndex = usageIndex;
	}
	
	public abstract void usageTagAdapter();
}

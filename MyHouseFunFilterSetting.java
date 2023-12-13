package colt.jan_brilliantsys.realestatechecker;

public class MyHouseFunFilterSetting extends FilterSetting{
	int typeIndex, usageIndex;

	public int getTypeIndex() {
		return typeIndex;
	}
	public void setTypeIndex(int typeIndex) {
		this.typeIndex = typeIndex;
	}
	public int getUsageIndex() {
		return usageIndex;
	}
	public void setUsageIndex(int usageIndex) {
		this.usageIndex = usageIndex;
	}
	
	public void usageTagAdapter() {	//建立Filter後一定要呼叫
		switch(usageIndex) {
			case 0:
				switch(typeIndex) {
					case 0:
						usageTagIndex=0;
						break;
					case 1:
						usageTagIndex=1;
						break;
					case 2:
						usageTagIndex=3;
						break;
					case 3:
						usageTagIndex=2;
						break;
				}
				break;
			case 1:
				usageTagIndex=4;
				break;
			case 2:
				usageTagIndex=5;
				break;
			case 3:
				usageTagIndex=6;
				break;
			case 4:
				usageTagIndex=8;
				break;
			case 5:
				usageTagIndex=7;
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
		}
		
	}
}

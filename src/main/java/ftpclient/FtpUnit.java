package ftpclient;

public class FtpUnit {
	private String unitName;
	private String unitType;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	@Override
	public String toString() {
		return unitType + ": " + unitName;
	}

	public FtpUnit() {

	}

	public FtpUnit(String unitName, String unitType) {
		this.unitName = unitName;
		this.unitType = unitType;
	}

	@Override
	public boolean equals(Object obj) {
		FtpUnit x = (FtpUnit) obj;
		return ((x.unitName.equals(unitName)) && (x.unitType.equals(unitType)));
	}
}

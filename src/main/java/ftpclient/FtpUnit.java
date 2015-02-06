package ftpclient;

public class FtpUnit {
	private String UnitName;
	private String UnitType;

	public String getUnitName() {
		return UnitName;
	}

	public void setUnitName(String unitName) {
		UnitName = unitName;
	}

	public String getUnitType() {
		return UnitType;
	}

	public void setUnitType(String unitType) {
		UnitType = unitType;
	}

	@Override
	public String toString() {
		return UnitType + ": " + UnitName;
	}

	public FtpUnit() {

	}

	public FtpUnit(String unitName, String unitType) {
		UnitName = unitName;
		UnitType = unitType;
	}

	@Override
	public boolean equals(Object obj) {
		FtpUnit x = (FtpUnit) obj;
		return ((x.UnitName.equals(UnitName)) && (x.UnitType.equals(UnitType)));
	}
}

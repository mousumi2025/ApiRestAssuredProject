package RequestBodyData;


public class AddressInfo {
	
	private String plotNumber, street, state, country, zipCode;
	
	public AddressInfo(String Plot, String Street, String  State,String Country,String Zip) {
		this.plotNumber = Plot;
		this.street = Street;
		this.state = State;
		this.country = Country;
		this.zipCode = Zip;
	}

	public String getPlotNumber() {
		return plotNumber;
	}

	public void setPlotNumber(String plotNumber) {
		this.plotNumber = plotNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
}

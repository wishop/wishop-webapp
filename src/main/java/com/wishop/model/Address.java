package com.wishop.model;

import javax.persistence.Embeddable;

import org.apache.commons.lang.StringUtils;

@Embeddable
public class Address implements BaseConstants {
	
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String county;
	private String postcode;
	private String country;
	
	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	
	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}
	
	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	
	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}
	
	/**
	 * @param addressLine3 the addressLine3 to set
	 */
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	
	/**
	 * @return the addressLine3
	 */
	public String getAddressLine3() {
		return addressLine3;
	}
	
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}
	
	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}
	
	/**
	 * @param postcode the postcode to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Returns the full user address
	 * @return String - Full user address 
	 */
	public String getFullAddress() {
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotEmpty(getAddressLine1())){
			sb.append(getAddressLine1());
			sb.append(BaseConstants.NEW_LINE);
		}
		if(StringUtils.isNotEmpty(getAddressLine2())){
			sb.append(getAddressLine2());
			sb.append(BaseConstants.NEW_LINE);
		}
		if(StringUtils.isNotEmpty(getAddressLine3())){
			sb.append(getAddressLine3());
			sb.append(BaseConstants.NEW_LINE);
		}
		if(StringUtils.isNotEmpty(getCity())){
			sb.append(getCity());
			sb.append(BaseConstants.NEW_LINE);
		}
		if(StringUtils.isNotEmpty(getCounty())){
			sb.append(getCounty());
			sb.append(BaseConstants.NEW_LINE);
		}
		if(StringUtils.isNotEmpty(getPostcode())){
			sb.append(getPostcode());
			sb.append(BaseConstants.NEW_LINE);
		}
		if(StringUtils.isNotEmpty(getCountry())){
			sb.append(getCountry());
			sb.append(BaseConstants.NEW_LINE);
		}
		return sb.toString();
	}
}

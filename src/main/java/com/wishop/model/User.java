package com.wishop.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * Class that represents a wishop User.
 * @author HTran
 *
 */

@Entity
@Table(name="users")
public class User extends BaseObject<User, Long> {
	
	private static final long serialVersionUID = 911350799009583349L;
	
	@Column(unique=true, nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String password;
	private String title;
	private String firstName;
	private String lastName;
	private String telephone;
	private String mobile;
	private String fax;
	private Date dateOfBirth;
	
	private String profile;
	
	@Column(nullable=false, columnDefinition="bool default true")
	private boolean accountActive = true;
	
	@Transient
	private String emailConfirmation;
	
	@Transient
	private String passwordConfirmation;

	@Transient
	private String currentPassword;
	
	@Embedded
	private Address address;
	
	@OneToMany
	private Collection<Wishlist> listOfWishlists = new ArrayList<Wishlist>();

	public Collection<Wishlist> getListOfWishlists() {
		return listOfWishlists;
	}
	public void setListOfWishlists(Collection<Wishlist> listOfWishlists) {
		this.listOfWishlists = listOfWishlists;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param firstName the User first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return the User first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @param lastName the User last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the User last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @return the User full name
	 */
	public String getFullName() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getFirstName());
		sb.append(" ");
		sb.append(this.getLastName());
		return sb.toString();
	}
	
	/**
	 * @param dateOfBirth the date of birth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	/**
	 * @return the date of birth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}

	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}
	
	/**
	 * Blocks or Unblocks the user account
	 * @param accountActive the accountActive to set
	 */
	public void setAccountActive(boolean accountActive) {
		this.accountActive = accountActive;
	}

	/**
	 * Checks to see if the user account is active, i.e., is not blocked
	 * @return the accountActive
	 */
	public boolean isAccountActive() {
		return accountActive;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Used when registering a user and for validation purposes.
	 * @return the emailConfirmation
	 */
	public String getEmailConfirmation() {
		return emailConfirmation;
	}

	/**
	 * Used when registering a user and for validation purposes.
	 * @param emailConfirmation the emailConfirmation to set
	 */
	public void setEmailConfirmation(String emailConfirmation) {
		this.emailConfirmation = emailConfirmation;
	}

	/**
	 * Used when registering a user and for validation purposes.
	 * @return the passwordConfirmation
	 */
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	/**
	 * Used when registering a user and for validation purposes.
	 * @param passwordConfirmation the passwordConfirmation to set
	 */
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}


	/**
	 * Method only used in the Change Password form.
	 * @param currentPassword the current password to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	/**
	 * Method only used in the Change Password form.
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}
	
	/**
	 * Method to return the User Address
	 * @return Address
	 */
	public Address getAddress() {
		return address;
	}
	
	/**
	 * Method to set the User Address
	 * @param Address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	
	/**
	 * Method that retrieves the User title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Method that sets the user Title
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}

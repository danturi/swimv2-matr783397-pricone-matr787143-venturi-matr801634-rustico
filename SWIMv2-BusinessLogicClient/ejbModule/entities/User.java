/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

//import org.apache.commons.codec.digest.DigestUtils;



import sessionbeans.logic.Group;
import sessionbeans.logic.MySHA512DigestClass;
import sessionbeans.logic.UserDTO;

/**
 *
 * @author MARCO
 */
@Entity
@Table(name = "users")
@NamedQueries({
	@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId"),
	@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
	@NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
	@NamedQuery(name = "User.findByRegisteredOn", query = "SELECT u FROM User u WHERE u.registeredOn = :registeredOn"),
	@NamedQuery(name = "User.findByFirstname", query = "SELECT u FROM User u WHERE u.firstname = :firstname"),
	@NamedQuery(name = "User.findByLastname", query = "SELECT u FROM User u WHERE u.lastname = :lastname"),
	@NamedQuery(name = "User.findBySex", query = "SELECT u FROM User u WHERE u.sex = :sex"),
	@NamedQuery(name = "User.findByCity", query = "SELECT u FROM User u WHERE u.city = :city"),
	@NamedQuery(name = "User.findByCountry", query = "SELECT u FROM User u WHERE u.country = :country"),
	@NamedQuery(name = "User.findByOccupation", query = "SELECT u FROM User u WHERE u.occupation = :occupation"),
	@NamedQuery(name = "User.findByTel", query = "SELECT u FROM User u WHERE u.tel = :tel"),
	@NamedQuery(name = "User.findByRating", query = "SELECT u FROM User u WHERE u.rating = :rating")})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
	@Column(unique=true, nullable=false, length=128)
	@Size(min = 1, max = 128)
	private String email;
	@Column(nullable=false, length=128)
	private String password;
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	//@NotNull
	@Column
	private Long userId;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date registeredOn;
	@Column(length=128)
	private String firstname;
	@Column(length=128)
	private String lastname;
	@Column(length=16)
	private String sex;
	@Column(length=8)
	private String age;
	@Column(length=128)
	private String city;
	@Column(length=128)
	private String country;
	@Column(length=128)
	private String occupation;
	@Column(length=128)
	private String tel;
	@Max(value=5)  @Min(value=0)//if you know range of your decimal fields consider using these annotations to enforce field validation
	private Float rating;
	@Lob
	private byte[] profilePicture;
	@JoinTable(name = "abilities_holded", joinColumns = {
			@JoinColumn(name = "Email", referencedColumnName = "Email")}, inverseJoinColumns = {
			@JoinColumn(name = "AbilityId", referencedColumnName = "AbilityId")})
	@ManyToMany
	private List<Ability> abilityList;
	@JoinTable(name = "friendships", joinColumns = {
			@JoinColumn(name = "User_1", referencedColumnName = "Email")}, inverseJoinColumns = {
			@JoinColumn(name = "User_2", referencedColumnName = "Email")})
	@ManyToMany
	private List<User> userList;
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "userList")
	private List<User> userList1;
	@OneToMany(orphanRemoval=true, cascade = CascadeType.ALL, mappedBy = "authorUser")
	private List<Feedback> feedbackList;
	@OneToMany(orphanRemoval=true, cascade = CascadeType.ALL, mappedBy = "user")
	private List<AbilityRequest> abilityRequestList;
	@OneToMany(orphanRemoval=true, cascade = CascadeType.ALL,mappedBy = "toUser")
	private List<FriendshipRequest> friendshipRequestList;
	@OneToMany(orphanRemoval=true, cascade = CascadeType.ALL,mappedBy = "fromUser")
	private List<FriendshipRequest> friendshipRequestList1;
	@OneToMany(orphanRemoval=true, cascade = CascadeType.ALL,mappedBy = "toUser")
	private List<HelpRequest> helpRequestList;
	@OneToMany(orphanRemoval=true, cascade = CascadeType.ALL,mappedBy = "fromUser")
	private List<HelpRequest> helpRequestList1;
	@ElementCollection(targetClass = Group.class)
	@CollectionTable(name = "users_groups", 
	joinColumns       = @JoinColumn(name = "email", nullable=false), 
	uniqueConstraints = { @UniqueConstraint(columnNames={"email","groupname"}) } )
	@Enumerated(EnumType.STRING)
	@Column(name="groupname", length=64, nullable=false)
	private List<Group> groups;

	public User() {
	}

	public User(UserDTO user){

		if (user.getPassword1() == null || user.getPassword1().length() == 0
				|| !user.getPassword1().equals(user.getPassword2()) )
			throw new RuntimeException("Password 1 and Password 2 have to be equal (typo?)");


		/*try {
			MessageDigest md = null;
			md = MessageDigest.getInstance("SHA-512");
			md.update(user.getPassword1().getBytes());
			byte byteData[] = md.digest();
			//convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

			}
			this.password = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		this.password = MySHA512DigestClass.sha512HexDigest(user.getPassword1());
		this.email        = user.getEmail();
		this.firstname    = user.getFName();
		this.lastname     = user.getLName();        
		this.registeredOn = new Date();
	}
	public User(Long userId) {
		this.userId = userId;
	}

	public User(Long userId, String email) {
		this.userId = userId;
		this.email = email;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<Ability> getAbilityList() {
		return abilityList;
	}

	public void setAbilityList(List<Ability> abilityList) {
		this.abilityList = abilityList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<User> getUserList1() {
		return userList1;
	}

	public void setUserList1(List<User> userList1) {
		this.userList1 = userList1;
	}

	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<Feedback> feedbackList) {
		this.feedbackList = feedbackList;
	}

	public List<AbilityRequest> getAbilityRequestList() {
		return abilityRequestList;
	}

	public void setAbilityRequestList(List<AbilityRequest> abilityRequestList) {
		this.abilityRequestList = abilityRequestList;
	}

	public List<FriendshipRequest> getFriendshipRequestList() {
		return friendshipRequestList;
	}

	public void setFriendshipRequestList(List<FriendshipRequest> friendshipRequestList) {
		this.friendshipRequestList = friendshipRequestList;
	}

	public List<FriendshipRequest> getSentFriendshipRequestList() {
		return friendshipRequestList1;
	}

	public void setSentFriendshipRequestList(List<FriendshipRequest> friendshipRequestList1) {
		this.friendshipRequestList1 = friendshipRequestList1;
	}

	public List<HelpRequest> getHelpReqList() {
		return helpRequestList;
	}

	public void setHelpReqList(List<HelpRequest> helpRequestList) {
		this.helpRequestList = helpRequestList;
	}

	public List<HelpRequest> getSentHelpReqList() {
		return helpRequestList1;
	}

	public void setSentHelpReqList(List<HelpRequest> helpRequestList1) {
		this.helpRequestList1 = helpRequestList1;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (email != null ? email.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof User)) {
			return false;
		}
		User other = (User) object;
		if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
			return false;
		}
		return true;
	}

	@Override
	/*public String toString() {
		return "User [email=" + email + ", firstName=" + firstname
				+ ", lastName=" + lastname + ", password=" + password
				+ ", registeredOn=" + registeredOn + ", groups=" + groups + "]";
	}*/
	public String toString() {
		return "User [email=" + email + "]";
	}

}

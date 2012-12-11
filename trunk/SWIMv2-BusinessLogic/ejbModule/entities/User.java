package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId;
	private String city;
	private String country;
	private String email;
	private String firstname;
	private String lastname;
	private String occupation;
	private String password;
	private Object profilePicture;
	private float rating;
	private Date regTime;
	private String sex;
	private String tel;
	private List<AbilitiesSet> abilitiesSets;
	private List<AbilityRequest> abilityRequests;
	private List<Feedback> feedbacks;
	private List<FriendshipRequest> friendshipRequests1;
	private List<FriendshipRequest> friendshipRequests2;
	private List<User> users1;
	private List<User> users2;
	private List<HelpRequest> helpRequests1;
	private List<HelpRequest> helpRequests2;

	public User() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	@Column(length=128)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	@Column(length=64)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	@Column(nullable=false, length=128)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(length=128)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	@Column(length=128)
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	@Column(length=128)
	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}


	@Column(length=128)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Object getProfilePicture() {
		return this.profilePicture;
	}

	public void setProfilePicture(Object profilePicture) {
		this.profilePicture = profilePicture;
	}


	public float getRating() {
		return this.rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}


	@Column(length=1)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}


	@Column(length=64)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


	//bi-directional many-to-many association to AbilitiesSet
	@ManyToMany(mappedBy="users")
	public List<AbilitiesSet> getAbilitiesSets() {
		return this.abilitiesSets;
	}

	public void setAbilitiesSets(List<AbilitiesSet> abilitiesSets) {
		this.abilitiesSets = abilitiesSets;
	}


	//bi-directional many-to-one association to AbilityRequest
	@OneToMany(mappedBy="user")
	public List<AbilityRequest> getAbilityRequests() {
		return this.abilityRequests;
	}

	public void setAbilityRequests(List<AbilityRequest> abilityRequests) {
		this.abilityRequests = abilityRequests;
	}


	//bi-directional many-to-one association to Feedback
	@OneToMany(mappedBy="user")
	public List<Feedback> getFeedbacks() {
		return this.feedbacks;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}


	//bi-directional many-to-one association to FriendshipRequest
	@OneToMany(mappedBy="user1")
	public List<FriendshipRequest> getFriendshipRequests1() {
		return this.friendshipRequests1;
	}

	public void setFriendshipRequests1(List<FriendshipRequest> friendshipRequests1) {
		this.friendshipRequests1 = friendshipRequests1;
	}


	//bi-directional many-to-one association to FriendshipRequest
	@OneToMany(mappedBy="user2")
	public List<FriendshipRequest> getFriendshipRequests2() {
		return this.friendshipRequests2;
	}

	public void setFriendshipRequests2(List<FriendshipRequest> friendshipRequests2) {
		this.friendshipRequests2 = friendshipRequests2;
	}


	//bi-directional many-to-many association to User
	@ManyToMany
	@JoinTable(
		name="friendships"
		, joinColumns={
			@JoinColumn(name="UserId_2", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="UserId_1", nullable=false)
			}
		)
	public List<User> getUsers1() {
		return this.users1;
	}

	public void setUsers1(List<User> users1) {
		this.users1 = users1;
	}


	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="users1")
	public List<User> getUsers2() {
		return this.users2;
	}

	public void setUsers2(List<User> users2) {
		this.users2 = users2;
	}


	//bi-directional many-to-one association to HelpRequest
	@OneToMany(mappedBy="user1")
	public List<HelpRequest> getHelpRequests1() {
		return this.helpRequests1;
	}

	public void setHelpRequests1(List<HelpRequest> helpRequests1) {
		this.helpRequests1 = helpRequests1;
	}


	//bi-directional many-to-one association to HelpRequest
	@OneToMany(mappedBy="user2")
	public List<HelpRequest> getHelpRequests2() {
		return this.helpRequests2;
	}

	public void setHelpRequests2(List<HelpRequest> helpRequests2) {
		this.helpRequests2 = helpRequests2;
	}

}
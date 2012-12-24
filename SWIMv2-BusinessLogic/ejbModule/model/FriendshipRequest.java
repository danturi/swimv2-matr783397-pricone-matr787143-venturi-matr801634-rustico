package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the friendship_requests database table.
 * 
 */
@Entity
@Table(name="friendship_requests")
public class FriendshipRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String friendReqId;
	private Object acceptanceStatus;
	private Timestamp date_time;
	private User user1;
	private User user2;

	public FriendshipRequest() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public String getFriendReqId() {
		return this.friendReqId;
	}

	public void setFriendReqId(String friendReqId) {
		this.friendReqId = friendReqId;
	}


	public Object getAcceptanceStatus() {
		return this.acceptanceStatus;
	}

	public void setAcceptanceStatus(Object acceptanceStatus) {
		this.acceptanceStatus = acceptanceStatus;
	}


	@Column(nullable=false)
	public Timestamp getDate_time() {
		return this.date_time;
	}

	public void setDate_time(Timestamp date_time) {
		this.date_time = date_time;
	}


	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="FromUserId")
	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}


	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="ToUserId")
	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}
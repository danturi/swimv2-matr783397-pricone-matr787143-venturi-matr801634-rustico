package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the feedback database table.
 * 
 */
@Entity
@Table(name="feedback")
public class Feedback implements Serializable {
	private static final long serialVersionUID = 1L;
	private String feedbackId;
	private java.math.BigInteger helpReqId;
	private User user;

	public Feedback() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public String getFeedbackId() {
		return this.feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}


	@Column(nullable=false)
	public java.math.BigInteger getHelpReqId() {
		return this.helpReqId;
	}

	public void setHelpReqId(java.math.BigInteger helpReqId) {
		this.helpReqId = helpReqId;
	}


	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="AuthorUserId", nullable=false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
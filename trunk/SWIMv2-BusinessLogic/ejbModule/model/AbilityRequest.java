package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ability_requests database table.
 * 
 */
@Entity
@Table(name="ability_requests")
public class AbilityRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String abilityReqId;
	private Object acceptanceStatus;
	private Timestamp date_time;
	private AbilitiesSet abilitiesSet;
	private User user;

	public AbilityRequest() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public String getAbilityReqId() {
		return this.abilityReqId;
	}

	public void setAbilityReqId(String abilityReqId) {
		this.abilityReqId = abilityReqId;
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


	//bi-directional many-to-one association to AbilitiesSet
	@ManyToOne
	@JoinColumn(name="AbilityId", nullable=false)
	public AbilitiesSet getAbilitiesSet() {
		return this.abilitiesSet;
	}

	public void setAbilitiesSet(AbilitiesSet abilitiesSet) {
		this.abilitiesSet = abilitiesSet;
	}


	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="UserId", nullable=false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the abilities_set database table.
 * 
 */
@Entity
@Table(name="abilities_set")
public class AbilitiesSet implements Serializable {
	private static final long serialVersionUID = 1L;
	private String abilityId;
	private String description;
	private List<User> users;
	private List<AbilityRequest> abilityRequests;

	public AbilitiesSet() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public String getAbilityId() {
		return this.abilityId;
	}

	public void setAbilityId(String abilityId) {
		this.abilityId = abilityId;
	}


	@Column(nullable=false, length=10000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	//bi-directional many-to-many association to User
	@ManyToMany
	@JoinTable(
		name="abilities_holded"
		, joinColumns={
			@JoinColumn(name="AbilityId", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="UserId", nullable=false)
			}
		)
	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}


	//bi-directional many-to-one association to AbilityRequest
	@OneToMany(mappedBy="abilitiesSet")
	public List<AbilityRequest> getAbilityRequests() {
		return this.abilityRequests;
	}

	public void setAbilityRequests(List<AbilityRequest> abilityRequests) {
		this.abilityRequests = abilityRequests;
	}

}
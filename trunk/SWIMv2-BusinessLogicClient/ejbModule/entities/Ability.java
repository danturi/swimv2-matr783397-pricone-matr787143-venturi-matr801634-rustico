/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author MARCO
 */
@Entity
@Table(name = "abilities_set")
@NamedQueries({
    @NamedQuery(name = "Ability.findAll", query = "SELECT a FROM Ability a"),
    @NamedQuery(name = "Ability.findByAbilityId", query = "SELECT a FROM Ability a WHERE a.abilityId = :abilityId"),
    @NamedQuery(name = "Ability.findByDescription", query = "SELECT a FROM Ability a WHERE a.description = :description")})
public class Ability implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long abilityId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10000)
    private String description;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "abilityList")
    private List<User> userList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "abilityId")
    private List<AbilityRequest> abilityRequestList;

    public Ability() {
    }

    public Ability(Long abilityId) {
        this.abilityId = abilityId;
    }

    public Ability(Long abilityId, String description) {
        this.abilityId = abilityId;
        this.description = description;
    }

    public Long getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(Long abilityId) {
        this.abilityId = abilityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<AbilityRequest> getAbilityRequestList() {
        return abilityRequestList;
    }

    public void setAbilityRequestList(List<AbilityRequest> abilityRequestList) {
        this.abilityRequestList = abilityRequestList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (abilityId != null ? abilityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ability)) {
            return false;
        }
        Ability other = (Ability) object;
        if ((this.abilityId == null && other.abilityId != null) || (this.abilityId != null && !this.abilityId.equals(other.abilityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ability[ abilityId=" + abilityId + " ]";
    }
    
}

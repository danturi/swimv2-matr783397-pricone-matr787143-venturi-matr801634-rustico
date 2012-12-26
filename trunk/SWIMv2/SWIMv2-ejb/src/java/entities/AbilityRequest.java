/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author MARCO
 */
@Entity
@Table(name = "ability_requests")
@NamedQueries({
    @NamedQuery(name = "AbilityRequest.findAll", query = "SELECT a FROM AbilityRequest a"),
    @NamedQuery(name = "AbilityRequest.findByAbilityReqId", query = "SELECT a FROM AbilityRequest a WHERE a.abilityReqId = :abilityReqId"),
    @NamedQuery(name = "AbilityRequest.findByAcceptanceStatus", query = "SELECT a FROM AbilityRequest a WHERE a.acceptanceStatus = :acceptanceStatus"),
    @NamedQuery(name = "AbilityRequest.findByDatetime", query = "SELECT a FROM AbilityRequest a WHERE a.datetime = :datetime")})
public class AbilityRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long abilityReqId;
    private Boolean acceptanceStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "AbilityId", referencedColumnName = "AbilityId")
    @ManyToOne(optional = false)
    private Ability abilityId;

    public AbilityRequest() {
    }

    public AbilityRequest(Long abilityReqId) {
        this.abilityReqId = abilityReqId;
    }

    public AbilityRequest(Long abilityReqId, Date datetime) {
        this.abilityReqId = abilityReqId;
        this.datetime = datetime;
    }

    public Long getAbilityReqId() {
        return abilityReqId;
    }

    public void setAbilityReqId(Long abilityReqId) {
        this.abilityReqId = abilityReqId;
    }

    public Boolean getAcceptanceStatus() {
        return acceptanceStatus;
    }

    public void setAcceptanceStatus(Boolean acceptanceStatus) {
        this.acceptanceStatus = acceptanceStatus;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Ability getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(Ability abilityId) {
        this.abilityId = abilityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (abilityReqId != null ? abilityReqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AbilityRequest)) {
            return false;
        }
        AbilityRequest other = (AbilityRequest) object;
        if ((this.abilityReqId == null && other.abilityReqId != null) || (this.abilityReqId != null && !this.abilityReqId.equals(other.abilityReqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AbilityRequest[ abilityReqId=" + abilityReqId + " ]";
    }
    
}

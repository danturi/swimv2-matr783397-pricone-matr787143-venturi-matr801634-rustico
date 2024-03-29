/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author MARCO
 */
@Entity
@Table(name = "help_requests")
@NamedQueries({
    @NamedQuery(name = "HelpRequest.findAll", query = "SELECT h FROM HelpRequest h"),
    @NamedQuery(name = "HelpRequest.findByHelpReqId", query = "SELECT h FROM HelpRequest h WHERE h.helpReqId = :helpReqId"),
    @NamedQuery(name = "HelpRequest.findByAcceptanceStatus", query = "SELECT h FROM HelpRequest h WHERE h.acceptanceStatus = :acceptanceStatus"),
    @NamedQuery(name = "HelpRequest.findByDatetime", query = "SELECT h FROM HelpRequest h WHERE h.datetime = :datetime")})
public class HelpRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long helpReqId;
    private Boolean acceptanceStatus;
    private Boolean isEvaluated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @Column(length = 10000)
	private String description;
    @JoinColumn(name = "FeedbackId", referencedColumnName = "FeedbackId")
    @OneToOne(optional = false)
    private Feedback feedbackId;
    @JoinColumn(name = "ToUser", referencedColumnName = "Email")
    @ManyToOne(optional = false)
    private User toUser;
    @JoinColumn(name = "FromUser", referencedColumnName = "Email")
    @ManyToOne(optional = false)
    private User fromUser;
    @JoinColumn(name = "AbilityId", referencedColumnName = "AbilityId")
    @ManyToOne(optional = false)
    private Ability abilityId;

    public HelpRequest() {
    }


    public Long getHelpReqId() {
        return helpReqId;
    }

    public void setHelpReqId(Long helpReqId) {
        this.helpReqId = helpReqId;
    }

    public Boolean getAcceptanceStatus() {
        return acceptanceStatus;
    }

    public void setAcceptanceStatus(Boolean acceptanceStatus) {
        this.acceptanceStatus = acceptanceStatus;
    }
    
    public Boolean getIsEvaluated() {
        return isEvaluated;
    }

    public void setIsEvaluated(Boolean isEvaluated) {
        this.isEvaluated = isEvaluated;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    
    public String getDescription(){
    	return description;
    }
    public void setDescription(String description){
    	this.description = description;
    }
    public Feedback getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Feedback feedbackId) {
        this.feedbackId = feedbackId;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
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
        hash += (helpReqId != null ? helpReqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HelpRequest)) {
            return false;
        }
        HelpRequest other = (HelpRequest) object;
        if ((this.helpReqId == null && other.helpReqId != null) || (this.helpReqId != null && !this.helpReqId.equals(other.helpReqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.HelpRequest[ helpReqId=" + helpReqId + " ]";
    }
    
}

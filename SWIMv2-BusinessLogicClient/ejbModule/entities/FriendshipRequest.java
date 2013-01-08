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
@Table(name = "friendship_requests")
@NamedQueries({
    @NamedQuery(name = "FriendshipRequest.findAll", query = "SELECT f FROM FriendshipRequest f"),
    @NamedQuery(name = "FriendshipRequest.findByFriendReqId", query = "SELECT f FROM FriendshipRequest f WHERE f.friendReqId = :friendReqId"),
    @NamedQuery(name = "FriendshipRequest.findByAcceptanceStatus", query = "SELECT f FROM FriendshipRequest f WHERE f.acceptanceStatus = :acceptanceStatus"),
    @NamedQuery(name = "FriendshipRequest.findByDatetime", query = "SELECT f FROM FriendshipRequest f WHERE f.datetime = :datetime")})
public class FriendshipRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long friendReqId;
    private Boolean acceptanceStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @JoinColumn(name = "ToUser", referencedColumnName = "Email")
    @ManyToOne
    private User toUser;
    @JoinColumn(name = "FromUser", referencedColumnName = "Email")
    @ManyToOne
    private User fromUser;

    public FriendshipRequest() {
    }


    public Long getFriendReqId() {
        return friendReqId;
    }

    public void setFriendReqId(Long friendReqId) {
        this.friendReqId = friendReqId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (friendReqId != null ? friendReqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FriendshipRequest)) {
            return false;
        }
        FriendshipRequest other = (FriendshipRequest) object;
        if ((this.friendReqId == null && other.friendReqId != null) || (this.friendReqId != null && !this.friendReqId.equals(other.friendReqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FriendshipRequest[ friendReqId=" + friendReqId + " ]";
    }
    
}

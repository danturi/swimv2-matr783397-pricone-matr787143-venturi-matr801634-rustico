/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 *
 * @author MARCO
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Feedback.findAll", query = "SELECT f FROM Feedback f"),
    @NamedQuery(name = "Feedback.findByFeedbackId", query = "SELECT f FROM Feedback f WHERE f.feedbackId = :feedbackId")})
public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long feedbackId;
    @Column(length = 10000)
	private String comment;
    @Max(value=5)  @Min(value=0)
	private Float rating;
    @JoinColumn(name = "AuthorUser", referencedColumnName = "Email")
    @ManyToOne(optional = false)
    private User authorUser;
    @JoinColumn(name = "HelpReqId", referencedColumnName = "HelpReqId")
    @OneToOne(optional = false)
    private HelpRequest helpReqId;
    

    public Feedback() {
    }

    public Feedback(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
    public User getAuthorUser() {
        return authorUser;
    }

    public void setAuthorUser(User authorUser) {
        this.authorUser = authorUser;
    }

    public HelpRequest getHelpReqId() {
        return helpReqId;
    }

    public void setHelpReqId(HelpRequest helpReqId) {
        this.helpReqId = helpReqId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (feedbackId != null ? feedbackId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feedback)) {
            return false;
        }
        Feedback other = (Feedback) object;
        if ((this.feedbackId == null && other.feedbackId != null) || (this.feedbackId != null && !this.feedbackId.equals(other.feedbackId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Feedback[ feedbackId=" + feedbackId + " ]";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.back.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author petri
 */
@Entity
@Table(name = "reservations")
@NamedQueries({
    @NamedQuery(name = "Reservations.findAll", query = "SELECT r FROM Reservations r"),
    @NamedQuery(name = "Reservations.findByReservationId", query = "SELECT r FROM Reservations r WHERE r.reservationId = :reservationId"),
    @NamedQuery(name = "Reservations.findByReservationDate", query = "SELECT r FROM Reservations r WHERE r.reservationDate = :reservationDate"),
    @NamedQuery(name = "Reservations.findByNumberOfGuests", query = "SELECT r FROM Reservations r WHERE r.numberOfGuests = :numberOfGuests"),
    @NamedQuery(name = "Reservations.findByStatus", query = "SELECT r FROM Reservations r WHERE r.status = :status"),
    @NamedQuery(name = "Reservations.findByCreatedAt", query = "SELECT r FROM Reservations r WHERE r.createdAt = :createdAt"),
    @NamedQuery(name="Reservations.findActiveReservationsWithUsers", query="SELECT r FROM Reservations r WHERE (r.status = 'Active' OR r.status = 'Pending')  AND r.userId = :userId ORDER BY r.reservationDate DESC"),
    @NamedQuery(name="Reservations.findInactiveReservationsWithUsers", query="SELECT r FROM Reservations r WHERE (r.status = 'Canceled' OR r.status = 'Done' OR r.status = 'Rejected' OR r.status='Expired')  AND r.userId = :userId ORDER BY r.reservationDate DESC "),

})
public class Reservations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reservation_id")
    private Integer reservationId;
    @Basic(optional = false)
    @Column(name = "reservation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reservationDate;
    @Basic(optional = false)
    @Column(name = "number_of_guests")
    private int numberOfGuests;
    @Lob
    @Column(name = "special_request")
    private String specialRequest;
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "rejection_comment")
    private String rejectionComment;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Users userId;

    @JoinColumn(name = "waiter_id", referencedColumnName = "user_id")
    @ManyToOne
    private Users waiterId;

    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @ManyToOne
    private Restaurants restaurantId;
    @JoinColumn(name = "table_id", referencedColumnName = "table_id")
    @ManyToOne
    private RestaurantTables tableId;
    @OneToMany(mappedBy = "reservationId")
    @JsonIgnore
    private List<Reviews> reviewsList;

    public Reservations() {
    }

    public Reservations(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Reservations(Integer reservationId, Date reservationDate, int numberOfGuests, String specialRequest, String status, Date createdAt, String rejectionComment) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.numberOfGuests = numberOfGuests;
        this.specialRequest = specialRequest;
        this.status = status;
        this.createdAt = createdAt;
        this.rejectionComment = rejectionComment;
    }

    public Reservations(Integer reservationId, Date reservationDate, int numberOfGuests, Date createdAt) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.numberOfGuests = numberOfGuests;
        this.createdAt = createdAt;
    }

    public Users getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Users waiterId) {
        this.waiterId = waiterId;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getRejectionComment() {
        return rejectionComment;
    }

    public void setRejectionComment(String rejectionComment) {
        this.rejectionComment = rejectionComment;
    }

    public Restaurants getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Restaurants restaurantId) {
        this.restaurantId = restaurantId;
    }

    public RestaurantTables getTableId() {
        return tableId;
    }

    public void setTableId(RestaurantTables tableId) {
        this.tableId = tableId;
    }

    public List<Reviews> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservationId != null ? reservationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservations)) {
            return false;
        }
        Reservations other = (Reservations) object;
        if ((this.reservationId == null && other.reservationId != null) || (this.reservationId != null && !this.reservationId.equals(other.reservationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.back.entities.Reservations[ reservationId=" + reservationId + " ]";
    }
    
}

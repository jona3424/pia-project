/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entities;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author petri
 */
@Entity
@Table(name = "restaurant_worker")
@NamedQueries({
    @NamedQuery(name = "RestaurantWorker.findAll", query = "SELECT r FROM RestaurantWorker r"),
    @NamedQuery(name = "RestaurantWorker.findByRwId", query = "SELECT r FROM RestaurantWorker r WHERE r.rwId = :rwId")})
public class RestaurantWorker implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rw_id")
    private Integer rwId;
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @ManyToOne
    private Restaurants restaurantId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Users userId;

    public RestaurantWorker() {
    }

    public RestaurantWorker(Integer rwId) {
        this.rwId = rwId;
    }

    public Integer getRwId() {
        return rwId;
    }

    public void setRwId(Integer rwId) {
        this.rwId = rwId;
    }

    public Restaurants getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Restaurants restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rwId != null ? rwId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RestaurantWorker)) {
            return false;
        }
        RestaurantWorker other = (RestaurantWorker) object;
        if ((this.rwId == null && other.rwId != null) || (this.rwId != null && !this.rwId.equals(other.rwId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.entities.RestaurantWorker[ rwId=" + rwId + " ]";
    }
    
}

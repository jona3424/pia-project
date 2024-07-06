/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.back.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author petri
 */
@Entity
@Table(name = "restaurant_tables")
@NamedQueries({
    @NamedQuery(name = "RestaurantTables.findAll", query = "SELECT r FROM RestaurantTables r"),
    @NamedQuery(name = "RestaurantTables.findByTableId", query = "SELECT r FROM RestaurantTables r WHERE r.tableId = :tableId"),
    @NamedQuery(name = "RestaurantTables.findByMaxSeats", query = "SELECT r FROM RestaurantTables r WHERE r.maxSeats = :maxSeats"),
    @NamedQuery(name = "RestaurantTables.findByTableShape", query = "SELECT r FROM RestaurantTables r WHERE r.tableShape = :tableShape")})
public class RestaurantTables implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "table_id")
    private Integer tableId;
    @Basic(optional = false)
    @Column(name = "max_seats")
    private int maxSeats;
    @Basic(optional = false)
    @Column(name = "table_shape")
    private String tableShape;
    @OneToMany(mappedBy = "tableId")
    @JsonIgnore
    private List<Reservations> reservationsList;
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @ManyToOne

    private Restaurants restaurantId;

    public RestaurantTables() {
    }

    public RestaurantTables(Integer tableId) {
        this.tableId = tableId;
    }

    public RestaurantTables(Integer tableId, int maxSeats, String tableShape) {
        this.tableId = tableId;
        this.maxSeats = maxSeats;
        this.tableShape = tableShape;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public String getTableShape() {
        return tableShape;
    }

    public void setTableShape(String tableShape) {
        this.tableShape = tableShape;
    }

    public List<Reservations> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(List<Reservations> reservationsList) {
        this.reservationsList = reservationsList;
    }

    public Restaurants getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Restaurants restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tableId != null ? tableId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RestaurantTables)) {
            return false;
        }
        RestaurantTables other = (RestaurantTables) object;
        if ((this.tableId == null && other.tableId != null) || (this.tableId != null && !this.tableId.equals(other.tableId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.back.entities.RestaurantTables[ tableId=" + tableId + " ]";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.back.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "restaurants")
@NamedQueries({
    @NamedQuery(name = "Restaurants.findAll", query = "SELECT r FROM Restaurants r"),
    @NamedQuery(name = "Restaurants.findByRestaurantId", query = "SELECT r FROM Restaurants r WHERE r.restaurantId = :restaurantId"),
    @NamedQuery(name = "Restaurants.findByName", query = "SELECT r FROM Restaurants r WHERE r.name = :name"),
    @NamedQuery(name = "Restaurants.findByType", query = "SELECT r FROM Restaurants r WHERE r.type = :type"),
    @NamedQuery(name = "Restaurants.findByAddress", query = "SELECT r FROM Restaurants r WHERE r.address = :address"),
    @NamedQuery(name = "Restaurants.findByContactPerson", query = "SELECT r FROM Restaurants r WHERE r.contactPerson = :contactPerson"),
    @NamedQuery(name = "Restaurants.findByPhoneNumber", query = "SELECT r FROM Restaurants r WHERE r.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Restaurants.findByEmail", query = "SELECT r FROM Restaurants r WHERE r.email = :email"),
    @NamedQuery(name = "Restaurants.findByCreatedAt", query = "SELECT r FROM Restaurants r WHERE r.createdAt = :createdAt")})
public class Restaurants implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "restaurant_id")
    private Integer restaurantId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "contact_person")
    private String contactPerson;
    @Basic(optional = false)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "opening_time")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    private LocalTime closingTime;

    @Column(name = "layout_json", columnDefinition = "TEXT")
    private String layoutJson;

    @OneToMany(mappedBy = "restaurantId")
    @JsonIgnore
    private List<Reservations> reservationsList;
    @OneToMany(mappedBy = "restaurantId")
    @JsonIgnore
    private List<Reviews> reviewsList;
    @OneToMany(mappedBy = "restaurantId")
    @JsonIgnore
    private List<MenuItems> menuItemsList;
    @OneToMany(mappedBy = "restaurantId")
    @JsonIgnore
    private List<Orders> ordersList;
    @OneToMany(mappedBy = "restaurantId")
    @JsonIgnore
    private List<RestaurantTables> restaurantTablesList;


    public Restaurants() {
    }

    public Restaurants(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Restaurants(Integer restaurantId, String name, String type, String address, String contactPerson, String phoneNumber, Date createdAt, Double latitude, Double longitude, LocalTime openingTime, LocalTime closingTime, String layoutJson) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.type = type;
        this.address = address;
        this.contactPerson = contactPerson;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.layoutJson = layoutJson;
    }

    public String getLayoutJson() {
        return layoutJson;
    }

    public void setLayoutJson(String layoutJson) {
        this.layoutJson = layoutJson;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Reservations> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(List<Reservations> reservationsList) {
        this.reservationsList = reservationsList;
    }

    public List<Reviews> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }

    public List<MenuItems> getMenuItemsList() {
        return menuItemsList;
    }

    public void setMenuItemsList(List<MenuItems> menuItemsList) {
        this.menuItemsList = menuItemsList;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public List<RestaurantTables> getRestaurantTablesList() {
        return restaurantTablesList;
    }

    public void setRestaurantTablesList(List<RestaurantTables> restaurantTablesList) {
        this.restaurantTablesList = restaurantTablesList;
    }
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (restaurantId != null ? restaurantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Restaurants)) {
            return false;
        }
        Restaurants other = (Restaurants) object;
        if ((this.restaurantId == null && other.restaurantId != null) || (this.restaurantId != null && !this.restaurantId.equals(other.restaurantId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.back.entities.Restaurants[ restaurantId=" + restaurantId + " ]";
    }
    
}

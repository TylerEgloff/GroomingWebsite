/**
 * Represents a service in the MySQL 'services' table.
 * 
 * Services is mapped to the 'services' table in the SQL database.
 * Stores service ID, name, description, and price.
 */

package com.barkybarbers.barkybarbers.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "services")
public class Service {

    @Id // identifies ID as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tells JPA to follow database ID generation (currently auto-incrementing)

    private Integer id;
    private String name;
    private String description;
    private float price;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) {this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
}

package com.ticketbooking.ticketbooking.repository;


import com.ticketbooking.ticketbooking.entity.SystemConfiguration;
//Importing Spring Data JPA's JpaRepository for CRUD and additional operations on entities.
import org.springframework.data.jpa.repository.JpaRepository;
// Annotation to indicate this is a Spring-managed repository component.
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Long> {

}
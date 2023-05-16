/**
 * 
 */
package com.technojade.allybot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.Address;

/**
 * @author ugam.sharma
 *
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
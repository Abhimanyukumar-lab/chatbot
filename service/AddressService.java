package com.technojade.allybot.service;

import java.util.Optional;

import com.technojade.allybot.entity.Address;


public interface AddressService {


	public Address saveAddress(Address address);
	public Optional<Address> fetchAddressById(Long id);

}
package com.technojade.allybot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.Address;
import com.technojade.allybot.repository.AddressRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {


	@Autowired
	private AddressRepository addressRepository;


	@Override
	public Address saveAddress(Address address) {		
		Address save = addressRepository.save(address);		
		return save;
	}


	@Override
	public Optional<Address> fetchAddressById(Long id) {
		return addressRepository.findById(id);
	}






}
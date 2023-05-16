/**
 * 
 */
package com.technojade.allybot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.constant.ConstantHelper;
import com.technojade.allybot.dtos.HotelDto;
import com.technojade.allybot.entity.Address;
import com.technojade.allybot.entity.Employee;
import com.technojade.allybot.entity.Hotel;
import com.technojade.allybot.entity.HotelRating;
import com.technojade.allybot.entity.UserRole;
import com.technojade.allybot.repository.AddressRepository;
import com.technojade.allybot.repository.HotelRatingRepository;
import com.technojade.allybot.repository.HotelRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ugam.sharma
 *
 */

@Service
@Slf4j
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private HotelRatingRepository hotelRatingRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private EmployeeService empl;

	public Hotel createHotel(final Hotel hotel) {
		hotelRepository.save(hotel);
		return hotel;
	}

	public List<HotelDto> findAssociatedHotelByHotelId(Long empId, Long roleId) {

		UserRole userRoleById = userRoleService.userRoleById(roleId);
		List<Hotel> hotels = null;

		if (userRoleById.getName().toUpperCase().equals(ConstantHelper.ADMIN_ROLE.toUpperCase())) {
			hotels = hotelRepository.findHotelsByClientId(empId);
		} else {
			try {
				Optional<Employee> findEmployeeById = empl.findEmployeeById(empId);
				Long hotelId = findEmployeeById.get().getHotelId();
				Hotel hotel = hotelRepository.findById(hotelId).get();
				hotels = new ArrayList<>();
				hotels.add(hotel);
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
		}

		List<HotelRating> hotelRatings = hotelRatingRepository
				.findByHotelIds(hotels.stream().map(Hotel::getHotelId).collect(Collectors.toList()));

		List<HotelDto> hotelDtos = new ArrayList<>();

		hotels.stream().forEach(hotel -> {
			HotelDto hotelDto = new HotelDto();
			hotelDto.setHotelName(hotel.getName());
			hotelDto.setHotelDescription(hotel.getHotelDescription());

			HotelRating hotelRating = hotelRatings.stream()
					.filter(rating -> rating.getHotelId().equals(hotel.getHotelId())).findFirst().orElse(null);
			if (null != hotelRating) {
				hotelDto.setRating(hotelRating.getRating());
			}

			Optional<Address> addressOptional = addressRepository.findById(hotel.getAddressId());
			if (addressOptional.isPresent()) {
				Address address = addressOptional.get();
				StringBuilder addressStr = new StringBuilder();
				if (null != address.getAddress_1() && !address.getAddress_1().isEmpty()) {
					addressStr.append(address.getAddress_1());
				}
				if (null != address.getAddress_2() && !address.getAddress_2().isEmpty()) {
					addressStr.append(" ").append(address.getAddress_2()).append("\n");
				}
				if (null != address.getAddress_3() && !address.getAddress_3().isEmpty()) {
					addressStr.append(" ").append(address.getAddress_3()).append("\n");
				}
				hotelDto.setHotelAddress(addressStr.toString());
				hotelDto.setHotelId(hotel.getHotelId());
				hotelDto.setNumberOfBed(hotel.getNumberOfBed());
				hotelDto.setNumberOfParking(hotel.getNumberOfParking());
				hotelDto.setNumberOfRoom(hotel.getNumberOfRoom());
			}

			hotelDtos.add(hotelDto);
		});
		return hotelDtos;
	}

	public Hotel findByHotelId(Long hotelId) {
		Hotel hotel = hotelRepository.getById(hotelId);
		return hotel;
	}

}

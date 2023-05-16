package com.technojade.allybot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.DeviceTokenMst;
import com.technojade.allybot.repository.DeviceTokenRepository;

@Service
public class DeviceTokenServiceImpl implements DeviceTokenService {

	@Autowired
	private DeviceTokenRepository deviceTokenRepository;

	@Override
	public DeviceTokenMst saveDeviceToken(DeviceTokenMst deviceTokenMst) {

		Optional<DeviceTokenMst> findByUserid = deviceTokenRepository.findByUserid(deviceTokenMst.getUserid());
		if (findByUserid.isPresent()) {
			deviceTokenRepository.deleteById(findByUserid.get().getId());
		}

		deviceTokenRepository.save(deviceTokenMst);
		return deviceTokenMst;
	}

}

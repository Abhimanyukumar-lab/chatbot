package com.technojade.allybot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.BookedRoomInfo;
import com.technojade.allybot.repository.RoomInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoomInfoServiceImpl implements RoomInfoService {

	@Autowired
	private RoomInfoRepository roomInfoRepository;

	@Override
	public BookedRoomInfo saveRoomInfo(BookedRoomInfo roomInfo) {
		roomInfoRepository.save(roomInfo);
		return roomInfo;
	}
	
	@Override
	public List<BookedRoomInfo> saveRoomInfoListData(List<BookedRoomInfo> roomInfo){
		roomInfoRepository.saveAll(roomInfo);
		return roomInfo;
	}

	@Override
	public void deleteRoomInfoByOnlineBId(Long onlineBID) {
		// TODO Auto-generated method stub
		roomInfoRepository.deleteAllByOnlineBookingId(onlineBID);
		log.info("Room info deleted...");
	}
	
	

}
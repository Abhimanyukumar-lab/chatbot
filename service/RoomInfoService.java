package com.technojade.allybot.service;

import java.util.List;

import com.technojade.allybot.entity.BookedRoomInfo;

public interface RoomInfoService {

	public BookedRoomInfo saveRoomInfo(BookedRoomInfo roomInfo);	

	public List<BookedRoomInfo> saveRoomInfoListData(List<BookedRoomInfo> roomInfo);	

	public void deleteRoomInfoByOnlineBId(Long onlineBID);
	
}

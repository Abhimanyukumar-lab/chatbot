package com.technojade.allybot.enpoint;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.NonUniqueResultException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STCellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.technojade.allybot.dtos.DataDto;
import com.technojade.allybot.dtos.HotelBookingDto;
import com.technojade.allybot.dtos.HotelDto;
import com.technojade.allybot.dtos.HotelInfoDto;
import com.technojade.allybot.dtos.HotelReturnDto;
import com.technojade.allybot.dtos.PaginationDto;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.HotelDtoResponce;
import com.technojade.allybot.enpoint.response.HotelResponseByUser;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.BookedRoomInfo;
import com.technojade.allybot.entity.Hotel;
import com.technojade.allybot.entity.HotelInfo;
import com.technojade.allybot.entity.User;
import com.technojade.allybot.entity.UserBookingDetail;
import com.technojade.allybot.entity.UserBookingInfo;
import com.technojade.allybot.service.HotelInfoService;
import com.technojade.allybot.service.HotelService;
import com.technojade.allybot.service.OtpService;
import com.technojade.allybot.service.RoomInfoService;
import com.technojade.allybot.service.UserBookingDetailService;
import com.technojade.allybot.service.UserBookingInfoService;
import com.technojade.allybot.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/hotel_info")
@Slf4j
public class HotelInfoController {
	@Autowired
	HotelInfoService hotelInfoService;

	@Autowired
	RoomInfoService roomInfoService;

	@Autowired
	private HotelService hotelService;

	@Autowired
	UserBookingInfoService userBookingInfoService;

	@Autowired
	UserBookingDetailService userBookingDetailService;

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserService userService;

	@GetMapping("/hotel-checkin-info/hotel/{id}")
	public ResponseEntity<?> getHotelChecckinInfo(@PathVariable("id") long id) throws Exception {

		HotelDtoResponce hotelResponse = hotelInfoService.findByHotelId(id);
		AllyBotResponseDto<HotelDtoResponce> response = new AllyBotResponseDto<>();
		response.setData(hotelResponse);
		response.setServiceStatus(ServiceStatus.SUCCESS);
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setTraceId(Long.MAX_VALUE);
		response.setMsg("Hotel information retireved");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/hotel-checkin-info/user/{id}")
	public ResponseEntity<?> getHotelChecckinInfoByUserId(@PathVariable("id") long id) throws Exception {

		ArrayList<HotelResponseByUser> hotelResponse = hotelInfoService.findByUserId(id);
		AllyBotResponseDto<ArrayList<HotelResponseByUser>> response = new AllyBotResponseDto<>();
		response.setData(hotelResponse);
		response.setServiceStatus(ServiceStatus.SUCCESS);
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setTraceId(Long.MAX_VALUE);
		response.setMsg("Hotel information retireved");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// saving hotel_info data API
	@PostMapping("/hotel-checkin-info/{hotelId}")
	public ResponseEntity<?> saveHotel_Info_Data(@PathVariable("hotelId") Long hotelId, @RequestBody DataDto dataDto) {
		List<HotelInfoDto> dataDtoList = dataDto.getData();

		HotelInfo hotelInfoResponse = null;

		String primaryMobileNumber = null;

		for (HotelInfoDto hotelInfoDto : dataDtoList) {

			Base64.Encoder encoder = Base64.getMimeEncoder();

			UserBookingInfo bookingInfo = hotelInfoDto.getUserBookingInfo();
			bookingInfo.setToken(true);
			bookingInfo.setHotelId(hotelId);
			String bookingTime = bookingInfo.getCheckingDate() + "-" + bookingInfo.getCheckoutDate();
			bookingInfo.setTokenValue(encoder.encodeToString(bookingTime.getBytes()));

//			saving user booking info 
			UserBookingInfo userBookingInfo = userBookingInfoService.saveUserBookingInfo(bookingInfo);

//				saving user booking details
			Optional<List<UserBookingDetail>> bookingDetailChecker = Optional
					.ofNullable(hotelInfoDto.getUserBookingdetails());
			if (bookingDetailChecker.isPresent()) {
				List<UserBookingDetail> userBookingDetailList = new ArrayList<>();
				List<UserBookingDetail> userBookingdetailsDto = hotelInfoDto.getUserBookingdetails();
				for (UserBookingDetail userBookingDetail : userBookingdetailsDto) {
					if (userBookingDetail.isPrimary()) {
						primaryMobileNumber = userBookingDetail.getMobileNumber();
						User user = new User();
						user.setFirstName(userBookingDetail.getFirstName());
						user.setLastName(userBookingDetail.getLastName());
						user.setMobileNumber(userBookingDetail.getMobileNumber());
						user.setEmailId(userBookingDetail.getEmail());
						User saveOrUpdate = userService.saveOrUpdate(user);

						User loginByMobileNumber = userService.loginByMobileNumber(primaryMobileNumber);

//							UserBookingInfo findByUserId = userBookingInfoService
//									.findByUserId(loginByMobileNumber.getUserId());

						UserBookingInfo userBookingInfoData = userBookingInfoService
								.findByUserIdAndCheckingDateAndCheckoutDateAndHotelId(loginByMobileNumber.getUserId(),
										bookingInfo.getCheckingDate(), bookingInfo.getCheckoutDate(), hotelId);

						if (userBookingInfoData != null) {
							userBookingInfoService.deleteById(userBookingInfo.getBookingId());
							AllyBotResponseDto<HotelInfo> addressRes = new AllyBotResponseDto<>();
							addressRes.setData(hotelInfoResponse);
							addressRes.setServiceStatus(ServiceStatus.FAILURE);
							addressRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
							addressRes.setTraceId(Long.MAX_VALUE);
							addressRes.setMsg("User already Booked");
							return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already Booked...");
						}

						if (loginByMobileNumber != null) {
							userBookingInfo.setUserId(loginByMobileNumber.getUserId());

						}

						if (saveOrUpdate != null) {
							userBookingInfo.setUserId(saveOrUpdate.getUserId());
						}
						userBookingInfoService.saveUserBookingInfo(userBookingInfo);

					}
					userBookingDetail.setBookingId(userBookingInfo.getBookingId());
					userBookingDetailList.add(userBookingDetail);
				}
				userBookingDetailService.saveUserBookingDetailList(userBookingDetailList);
			}

//			saving room info data 
			Optional<List<BookedRoomInfo>> roomInfoChecker = Optional.ofNullable(hotelInfoDto.getRommInfoList());
			if (roomInfoChecker.isPresent()) {
				List<BookedRoomInfo> roomInfoSer = new ArrayList<BookedRoomInfo>();
				List<BookedRoomInfo> rommInfoList = hotelInfoDto.getRommInfoList();
				for (BookedRoomInfo roomInfo : rommInfoList) {
					roomInfo.setHotelId(hotelId);
					roomInfo.setBookingId(userBookingInfo.getBookingId());
					roomInfoSer.add(roomInfo);
				}
				roomInfoService.saveRoomInfoListData(roomInfoSer);
			}

			String otp = otpService.sendOtpOnlyWithCheckoutDate(primaryMobileNumber, bookingInfo.getCheckoutDate());
			if (otp != null) {
				log.info("Otp sent successfully");
			}
		}

		AllyBotResponseDto<HotelInfo> addressRes = new AllyBotResponseDto<>();
		addressRes.setData(hotelInfoResponse);
		addressRes.setServiceStatus(ServiceStatus.SUCCESS);
		addressRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		addressRes.setTraceId(Long.MAX_VALUE);
		addressRes.setMsg("HotelInfo has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(dataDtoList);

	}

	public ResponseEntity<?> saveHotel_Info_BulkData(@PathVariable("hotelId") Long hotelId,
			@RequestBody DataDto dataDto) {
		List<HotelInfoDto> dataDtoList = dataDto.getData();

		HotelInfo hotelInfoResponse = null;

		String primaryMobileNumber = null;

		for (HotelInfoDto hotelInfoDto : dataDtoList) {

			boolean isUserBooked = false;

			Base64.Encoder encoder = Base64.getMimeEncoder();

			UserBookingInfo bookingInfo = hotelInfoDto.getUserBookingInfo();

			List<UserBookingInfo> findBookingByOnlineId = userBookingInfoService
					.findBookingByOnlineId(bookingInfo.getOnlineBookingId());
			if (findBookingByOnlineId.size() > 0) {
				continue;
			}

			bookingInfo.setToken(true);
			bookingInfo.setHotelId(hotelId);
			String bookingTime = bookingInfo.getCheckingDate() + "-" + bookingInfo.getCheckoutDate();
			bookingInfo.setTokenValue(encoder.encodeToString(bookingTime.getBytes()));

//			saving user booking info 
			UserBookingInfo userBookingInfo = userBookingInfoService.saveUserBookingInfo(bookingInfo);

//			saving user booking details
			Optional<List<UserBookingDetail>> bookingDetailChecker = Optional
					.ofNullable(hotelInfoDto.getUserBookingdetails());
			if (bookingDetailChecker.isPresent()) {
				List<UserBookingDetail> userBookingDetailList = new ArrayList<>();
				List<UserBookingDetail> userBookingdetailsDto = hotelInfoDto.getUserBookingdetails();
				for (UserBookingDetail userBookingDetail : userBookingdetailsDto) {
					if (userBookingDetail.isPrimary()) {
						primaryMobileNumber = userBookingDetail.getMobileNumber();
						User user = new User();
						user.setFirstName(userBookingDetail.getFirstName());
						user.setLastName(userBookingDetail.getLastName());
						user.setMobileNumber(userBookingDetail.getMobileNumber());
						user.setEmailId(userBookingDetail.getEmail());
						User saveOrUpdate = userService.saveOrUpdate(user);

						User loginByMobileNumber = userService.loginByMobileNumber(primaryMobileNumber);

						UserBookingInfo userBookingInfoData = userBookingInfoService
								.findByUserIdAndCheckingDateAndCheckoutDateAndHotelId(loginByMobileNumber.getUserId(),
										bookingInfo.getCheckingDate(), bookingInfo.getCheckoutDate(), hotelId);

						if (userBookingInfoData != null) {
							userBookingInfoService.deleteById(userBookingInfo.getBookingId());
							isUserBooked = true;
							break;
//							return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addressRes);
						}

						if (loginByMobileNumber != null) {
							userBookingInfo.setUserId(loginByMobileNumber.getUserId());
						}

						if (saveOrUpdate != null) {
							userBookingInfo.setUserId(saveOrUpdate.getUserId());
						}
						userBookingInfoService.saveUserBookingInfo(userBookingInfo);
					}
					userBookingDetail.setBookingId(userBookingInfo.getBookingId());
					userBookingDetailList.add(userBookingDetail);
				}

				if (isUserBooked) {
					continue;
				}

				userBookingDetailService.saveUserBookingDetailList(userBookingDetailList);
			}

//			saving room info data 
			Optional<List<BookedRoomInfo>> roomInfoChecker = Optional.ofNullable(hotelInfoDto.getRommInfoList());
			if (roomInfoChecker.isPresent()) {
				List<BookedRoomInfo> roomInfoSer = new ArrayList<BookedRoomInfo>();
				List<BookedRoomInfo> rommInfoList = hotelInfoDto.getRommInfoList();
				for (BookedRoomInfo roomInfo : rommInfoList) {
					roomInfo.setHotelId(hotelId);
					roomInfo.setBookingId(userBookingInfo.getBookingId());
					roomInfoSer.add(roomInfo);
				}
				roomInfoService.saveRoomInfoListData(roomInfoSer);
			}

			String otp = otpService.sendOtpOnlyWithCheckoutDate(primaryMobileNumber, bookingInfo.getCheckoutDate());
			if (otp != null) {
				log.info("Otp sent successfully");
			}
		}

		AllyBotResponseDto<String> addressRes = new AllyBotResponseDto<>();
		addressRes.setData("Uploaded...");
		addressRes.setServiceStatus(ServiceStatus.SUCCESS);
		addressRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		addressRes.setTraceId(Long.MAX_VALUE);
		addressRes.setMsg("Booking File Data Uploaded");

		return ResponseEntity.status(HttpStatus.CREATED).body(addressRes);

	}

	@PostMapping("/uploadbooking/{hId}")
	public ResponseEntity<?> uploadCsvHotelBookingData(@PathVariable("hId") Long hotelId,
			@RequestParam("hotelCsv") MultipartFile mlFile) {

		try {

			XSSFWorkbook workbook = new XSSFWorkbook(mlFile.getInputStream());
			XSSFSheet userBookingInfo = workbook.getSheet("userBookingInfo");
			XSSFSheet userBookingdetails = workbook.getSheet("userBookingdetails");

			// Setting userbookingInfo data which is coming from
			// excel.....................................

			List<BookedRoomInfo> bookedRoomInfoList = new ArrayList<BookedRoomInfo>();
			List<UserBookingInfo> bookingInfoList = new ArrayList<UserBookingInfo>();

			Iterator<Row> it = userBookingInfo.iterator();
			it.next();

			while (it.hasNext()) {

				UserBookingInfo bookingInfo = new UserBookingInfo();

				Row currentRow = it.next();

				boolean isRowBlank = false;

				for (Cell cell : currentRow) {
					if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
						isRowBlank = true;
					} else {
						isRowBlank = false;
						break;
					}
				}

				if (isRowBlank) {
					break;
				}

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");

				ZonedDateTime dateTime = ZonedDateTime.parse(currentRow.getCell(0).getDateCellValue().toString(),
						formatter);
				LocalDateTime localDateTime = dateTime.toLocalDateTime();
				bookingInfo.setCheckingDate(localDateTime);

				ZonedDateTime dateTime2 = ZonedDateTime.parse(currentRow.getCell(1).getDateCellValue().toString(),
						formatter);

				LocalDateTime localDateTime2 = dateTime2.toLocalDateTime();
				bookingInfo.setCheckoutDate(localDateTime2);
				bookingInfo.setNumberOfRroom((int) currentRow.getCell(2).getNumericCellValue());
				bookingInfo.setActive(true);
				bookingInfo.setOnlineBookingId((long) currentRow.getCell(6).getNumericCellValue());

				bookingInfoList.add(bookingInfo);

				// Setting roomInfo data which is coming from Excel......

				BookedRoomInfo bookedRoomInfo = new BookedRoomInfo();
				bookedRoomInfo.setNumOfTraveller((int) currentRow.getCell(3).getNumericCellValue());
				bookedRoomInfo.setFloor((int) currentRow.getCell(4).getNumericCellValue());
				bookedRoomInfo.setRoomType(currentRow.getCell(5).getStringCellValue());
				bookedRoomInfo.setOnlineBookingId((long) currentRow.getCell(6).getNumericCellValue());

				bookedRoomInfoList.add(bookedRoomInfo);

			}
			// Setting userbookingDetails data which is coming from
			// excel.....................................

			List<UserBookingDetail> userBookingDetailList = new ArrayList<UserBookingDetail>();

			Iterator<Row> it3 = userBookingdetails.iterator();
			it3.next();
			while (it3.hasNext()) {
				Row currentRow = it3.next();

				boolean isRowBlank = false;

				for (Cell cell : currentRow) {
					if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
						isRowBlank = true;
					} else {
						isRowBlank = false;
						break;
					}
				}

				if (isRowBlank) {
					break;
				}

				UserBookingDetail userBookingDetail = new UserBookingDetail();

				userBookingDetail.setFirstName(currentRow.getCell(0).getStringCellValue());
				userBookingDetail.setLastName(currentRow.getCell(1).getStringCellValue());
				userBookingDetail.setGender(currentRow.getCell(2).getStringCellValue());
				userBookingDetail.setAge((int) currentRow.getCell(3).getNumericCellValue());
				userBookingDetail.setEmail(currentRow.getCell(4).getStringCellValue());
				userBookingDetail.setProofName(currentRow.getCell(5).getStringCellValue());
				userBookingDetail.setMobileNumber(String.valueOf((long) currentRow.getCell(6).getNumericCellValue()));
				userBookingDetail.setPrimary(currentRow.getCell(7).getBooleanCellValue());
				userBookingDetail.setOnlineBookingId((long) currentRow.getCell(8).getNumericCellValue());
				userBookingDetail.setActive(true);
				userBookingDetail.setCreatedOn(LocalDateTime.now());

				userBookingDetailList.add(userBookingDetail);
			}

			List<HotelInfoDto> hotelInfoDtolList = new ArrayList<HotelInfoDto>();
//			List<DataDto> dataDtolList = new ArrayList<DataDto>();

			bookingInfoList.forEach(e -> {
				HotelInfoDto hotelInfoDto = new HotelInfoDto();
				hotelInfoDto.setUserBookingInfo(e);

				List<BookedRoomInfo> collectRoom = bookedRoomInfoList.stream()
						.filter(f -> f.getOnlineBookingId().equals(e.getOnlineBookingId()))
						.collect(Collectors.toList());
				hotelInfoDto.setRommInfoList(collectRoom);

				List<UserBookingDetail> collectUserBookingdetails = userBookingDetailList.stream()
						.filter(g -> g.getOnlineBookingId().equals(e.getOnlineBookingId()))
						.collect(Collectors.toList());
				hotelInfoDto.setUserBookingdetails(collectUserBookingdetails);

				hotelInfoDtolList.add(hotelInfoDto);

			});

			DataDto dataDto = new DataDto();
			dataDto.setData(hotelInfoDtolList);

			ResponseEntity<?> saveHotel_Info_Data = saveHotel_Info_BulkData(hotelId, dataDto);

			workbook.close();

			return ResponseEntity.status(HttpStatus.OK).body(saveHotel_Info_Data.getBody());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body("File uploading failed... ");
		}

	}

	// error handling method
	private void badRequest(AllyBotResponseDto<HotelInfo> hotelInfoRes, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10101");
		error.setErrorMessage(errorMessage);
		hotelInfoRes.setErrors(error);
		hotelInfoRes.setMsg(msg);
		hotelInfoRes.setServiceStatus(ServiceStatus.FAILURE);
		hotelInfoRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		hotelInfoRes.setTraceId(Long.MAX_VALUE);
	}

	@GetMapping("/hotels/{empId}")
	public ResponseEntity<?> getHotelsByEmpId(@RequestParam("roleId") Long roleId, @PathVariable("empId") Long empId)
			throws Exception {

		List<HotelDto> hotelResponse = hotelService.findAssociatedHotelByHotelId(empId, roleId);

		AllyBotResponseDto<List<HotelDto>> response = new AllyBotResponseDto<>();
		if (hotelResponse.isEmpty()) {
			response.setData(hotelResponse);
			response.setServiceStatus(ServiceStatus.SUCCESS);
			response.setTimestamp(new Timestamp(System.currentTimeMillis()));
			response.setTraceId(Long.MAX_VALUE);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}
		response.setData(hotelResponse);
		response.setServiceStatus(ServiceStatus.SUCCESS);
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setTraceId(Long.MAX_VALUE);
		response.setMsg("List of hotels successfully retrieved!");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/hotel/{uId}")
	public ResponseEntity<?> checkOutUser(@PathVariable("uId") Long uId) {

		List<UserBookingInfo> bookingInfo = userBookingInfoService.findByUserIdWithToken(uId);

		if (bookingInfo == null || bookingInfo.size() == 0) {
			return ResponseEntity.status(HttpStatus.OK).body("Invalid user Id");
		}

		for (UserBookingInfo e : bookingInfo) {

			String datetimeString = e.getCheckoutDate().toString();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			LocalDateTime datetime = LocalDateTime.parse(datetimeString, formatter);
			LocalDate dates = datetime.toLocalDate();

			LocalDate currentDate = LocalDate.now();
			if (currentDate.isEqual(dates)) {
				e.setToken(false);
				userBookingInfoService.saveUserBookingInfo(e);
				return ResponseEntity.status(HttpStatus.OK).body("User Checkedout............");
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body("There is no any user on this Date");
	}

	@GetMapping("/tokenvalidator/{uId}")
	public ResponseEntity<?> checkTokenValidity(@PathVariable("uId") Long uId) {

		List<UserBookingInfo> bookingInfo = userBookingInfoService.findByUserIdWithToken(uId);

		if (bookingInfo == null || bookingInfo.size() == 0) {
			return ResponseEntity.status(HttpStatus.OK).body("Invalid user Id");
		}

		for (UserBookingInfo e : bookingInfo) {

			LocalDateTime currentDate = LocalDateTime.now();
			boolean isBetween = currentDate.isEqual(e.getCheckingDate()) || currentDate.isEqual(e.getCheckoutDate())
					|| (currentDate.isAfter(e.getCheckingDate()) && currentDate.isBefore(e.getCheckoutDate()));

			if (isBetween) {
				return ResponseEntity.status(HttpStatus.OK).body(e);
			}
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired");
	}

	@GetMapping("/bookinglist/{hId}")
	public ResponseEntity<?> listOfHotelBooking(@PathVariable("hId") Long hId, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {

		Page<UserBookingInfo> listOfUserBooking = userBookingInfoService.findListOfUserBooking(hId, pageNo - 1,
				pageSize);

		List<HotelBookingDto> hotelBookingDto = new ArrayList<HotelBookingDto>();

		listOfUserBooking.forEach(e -> {

			User findByUserID = userService.findByUserID(e.getUserId() != null ? e.getUserId() : 0);

			HotelBookingDto bookingDto = new HotelBookingDto();
			bookingDto.setBookingId(e.getBookingId());
			bookingDto.setOnlineBookingId(e.getOnlineBookingId());
			bookingDto.setCheckingDate(e.getCheckingDate());
			bookingDto.setCheckoutDate(e.getCheckoutDate());
			bookingDto.setMobileNumber(findByUserID != null ? findByUserID.getMobileNumber() : "");
			bookingDto.setBookedBy(
					findByUserID != null ? findByUserID.getFirstName() + " " + findByUserID.getLastName() : "");
			bookingDto.setBookingDate(e.getCreatedOn());

			hotelBookingDto.add(bookingDto);
		});

		PaginationDto<Object> paginationDto = new PaginationDto<>();
		paginationDto.setData(hotelBookingDto);
		paginationDto.setTotalElement(listOfUserBooking.getTotalElements());
		paginationDto.setTotalPage((long) listOfUserBooking.getTotalPages());
		return ResponseEntity.status(HttpStatus.OK).body(paginationDto);
	}

	@PutMapping("/update/{bid}")
	public ResponseEntity<?> updateUserBookingInfo(@PathVariable("bid") Long bookingId,
			@RequestBody UserBookingInfo userBookingInfo) {

		UserBookingInfo updateUserBookingInfo = userBookingInfoService.updateUserBookingInfo(bookingId,
				userBookingInfo.getCheckingDate(), userBookingInfo.getCheckoutDate());

		return ResponseEntity.status(HttpStatus.OK).body(updateUserBookingInfo);
	}

	@GetMapping("/booking")
	public ResponseEntity<?> findUserBookingdetailsByOnlineIdOrMobile(@RequestParam("mobile") String mobile,
			@RequestParam("onlineBId") Long onlineBId) {

		List<UserBookingDetail> mobileOrOnlineBookingId = userBookingDetailService.findByMobileOrOnlineBookingId(mobile,
				onlineBId);
		return ResponseEntity.status(HttpStatus.OK).body(mobileOrOnlineBookingId);
	}

	@GetMapping("/bookingid/{bId}")
	public ResponseEntity<?> listOfUserBookingDetailsByBookingId(@PathVariable("bId") Long bookingId) {

		List<UserBookingDetail> bookingDetail = userBookingDetailService.findListOfBookingDetail(bookingId);
		return ResponseEntity.status(HttpStatus.OK).body(bookingDetail);
	}

	@DeleteMapping("/delete/{onlineBookingID}")
	public ResponseEntity<?> deleteBooking(@PathVariable("onlineBookingID") Long oBId) {
		log.info("Hotel info deleted...");
		userBookingDetailService.deleteUserBooking(oBId);
		return ResponseEntity.status(HttpStatus.OK).body("Deleted...");
	}

}

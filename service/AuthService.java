package com.technojade.allybot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.technojade.allybot.enpoint.response.AuthResponse;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService  {
	
	public ResponseEntity<String> veryFyLogin( HttpEntity<String> requestValue)   {
		ResponseEntity<String> responseEntity = null;
	responseEntity = new RestTemplate().exchange("http://143.110.242.247:8091/auth-service/v1/auth/jwt/verify", HttpMethod.GET, requestValue, String.class);
		return responseEntity;
		
	}
	public ResponseEntity<AuthResponse> login( HttpEntity<AuthResponse> requestValue)   {
		ResponseEntity<AuthResponse> responseEntity = null;
	responseEntity = new RestTemplate().exchange("http://143.110.242.247:8091/auth-service/v1/auth/token?username=vk@gmail.com&password=123", HttpMethod.POST, requestValue, AuthResponse.class);
		return responseEntity;
		
	}
}

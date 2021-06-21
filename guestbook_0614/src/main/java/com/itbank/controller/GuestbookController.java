package com.itbank.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itbank.model.GuestbookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.ObjectMapper;
import com.itbank.service.GuestbookService;

@RestController // 하위 메서드가 @ResponseBody를 포함하도록 처리하는 어노테이션
public class GuestbookController {
	
	// @RequestBody vs @RequestParam
	// 자바스크립트에서 전송한 요청을 자바 객체 형식으로 받으려면 @RequestBody를 쓴다.
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired GuestbookService gbs;
	
	@PostMapping(value="/")
	public int write(@RequestBody HashMap<String, String> map) {
		System.out.println("write!");
//		System.out.println(map);
//		return mapper.writeValueAsString(map);
		
		return gbs.write(map);
	}
	
//	@GetMapping(value="/list", produces = "application/json;charset=utf-8")
//	public String list() throws JsonProcessingException {
//		List<GuestbookDTO> list = gbs.selectList();
//		String result = mapper.writeValueAsString(list);
//		return result;	
//	}
	
//	문자열 반환이 아니면 매퍼가 어느 정도 알아서 해준다.	
	@GetMapping("/list")
	public List<GuestbookDTO> list() {
		return gbs.selectList();
	}
}

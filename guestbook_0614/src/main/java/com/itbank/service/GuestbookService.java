package com.itbank.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbank.model.GuestbookDAO;
import com.itbank.model.GuestbookDTO;

@Service
public class GuestbookService {

	@Autowired private GuestbookDAO dao;

	public int write(HashMap<String, String> map) {
		return dao.write(map);
	}

	public List<GuestbookDTO> selectList() {
		return dao.selectList();
	}
	
}

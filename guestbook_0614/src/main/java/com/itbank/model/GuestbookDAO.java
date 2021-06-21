package com.itbank.model;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface GuestbookDAO {
	
	@Insert("insert into guestbook (writer, content) values (#{writer}, #{content})")
	int write(HashMap<String, String> map);
	
	@Select("select * from guestbook order by idx desc")
	List<GuestbookDTO> selectList();
	
}

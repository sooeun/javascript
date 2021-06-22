package member;

public class MemberDTO {
	   
	// table  : FOOTMEMBER
	
	// ID				VARCHAR2(20 BYTE)
	// PW				VARCHAR2(500 BYTE)
	// NICKNAME			VARCHAR2(20 BYTE)
	// INTRO			VARCHAR2(100 BYTE)
	// PROFILENAME		VARCHAR2(255 BYTE)
	// HEART			HAR(1 BYTE)
		   
	private String id, pw, nickname, intro, profilename; 
	private char heart;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPw() {
		return pw;
	}
	
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getIntro() {
		return intro;
	}
	
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	public char getHeart() {
		return heart;
	}
	
	public void setHeart(char heart) {
		this.heart = heart;
	}
	
	public String getProfilename() {
		return profilename;
	}
	
	public void setProfilename(String profilename) {
		this.profilename = profilename;
	}

}
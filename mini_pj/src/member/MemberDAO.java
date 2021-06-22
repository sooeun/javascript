package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {

	private Connection conn;
	private DataSource ds;
	private Context init;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private static MemberDAO instance;
	
	// singleton
	public static MemberDAO getInstance() {
		if(instance == null) {
			instance = new MemberDAO();
		}
		return instance;
	}
	
	// 생성자
	public MemberDAO() {
		try {
			init = (Context) new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/oracle");
		} catch (Exception e) {
			System.out.println("생성자 예외: " + e);
		} finally {
			if(conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	public void close() {
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			} 
			if(pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) { System.out.println("close: " + e); }
	}

	// 회원가입
	public int join(MemberDTO m) {
		int row = 0;
		String sql = "insert into footmember (id, pw, nickname, intro, profilename)"
					+ "values(?, ?, ?, ?, ?)";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getPw());
			pstmt.setString(3, m.getNickname());
			pstmt.setString(4, "".equals(m.getIntro()) ? "안녕하세요." : m.getIntro());	// 빈문자열과 비교	
			pstmt.setString(5, m.getProfilename() == null ? "default.jpg" : m.getProfilename());
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("join: " + e);
		} finally { close(); }
		return row;
	}
	
	// 로그인
	public MemberDTO login(MemberDTO m) {
		
		MemberDTO login = null;
		
		String sql = "select * from footMember where id=? and pw=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getPw());
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				login = new MemberDTO();
				login.setId(rs.getString("id"));
				login.setPw(rs.getString("pw"));
				login.setNickname(rs.getString("nickname"));
				login.setIntro(rs.getString("intro"));
				login.setProfilename(rs.getString("profilename"));
			}
		} catch (SQLException e) {
			System.out.println("login: " + e);
		} finally { close(); }
		return login;
	}
	
	//회원탈퇴
	 public int delete(MemberDTO m) {
		  int row = 0;
	      String sql = "delete from footmember where id = ?";
	      PreparedStatement pstmt = null;
	      
	      try {
	         conn = ds.getConnection();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, m.getId());
	         
	         row = pstmt.executeUpdate();
	      } catch (SQLException e) {
	    	  System.out.println("delete : " + e);
	      } finally { close(); }
	      
	      return row;
		 }
	 
	// 정보수정
	public int modify(MemberDTO m) {
		int row = 0;
		boolean flag;
		boolean imgflag;

		if("".equals(m.getPw())) {      // 패스워드를 변경 여부 확인
			flag = false;
		}else {
			flag = true;
		}
       
		if(m.getProfilename() == null) {   // 프로필이미지 변경 여부 확인
			imgflag = false;
		} else {
			imgflag = true;
		}

		String sql="update footmember "
				+ "set "
				+ (flag ? "pw=?,":"")
				+ (imgflag ? "profilename=?,":"")
				+ "nickname=?,"
				+ "intro=? "
				+ "where id=?";
          
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
            
			if(flag && imgflag) {      // 패스워드, 프로필이미지 모두 바꿀 경우
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, m.getPw());
				pstmt.setString(2, m.getProfilename());
				pstmt.setString(3, m.getNickname());
				pstmt.setString(4, "".equals(m.getIntro()) ? "안녕하세요." : m.getIntro());
				pstmt.setString(5, m.getId());
				row = pstmt.executeUpdate();
			}
			else if(imgflag) {      // 프로필이미지만 바꿀 경우
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, m.getProfilename());
				pstmt.setString(2, m.getNickname());
				pstmt.setString(3, "".equals(m.getIntro()) ? "안녕하세요." : m.getIntro());
				pstmt.setString(4, m.getId());
				row = pstmt.executeUpdate();
			}
			else if(flag) {      // 패스워드만 바꿀 경우
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, m.getPw());
				pstmt.setString(2, m.getNickname());
				pstmt.setString(3, "".equals(m.getIntro()) ? "안녕하세요." : m.getIntro());
				pstmt.setString(4, m.getId());
				row = pstmt.executeUpdate();
			}
			else {      // 패스워드 프로필이미지 둘다 바꾸지 않을 경우
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, m.getNickname());
				pstmt.setString(2, "".equals(m.getIntro()) ? "안녕하세요." : m.getIntro());
				pstmt.setString(3, m.getId());
				row = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("modify : " + e);
		} finally { close(); }
		return row;
	}
}

package gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import gallery.Paging;


public class GalleryDAO {

	private Connection conn;
	private DataSource ds;
	private Context init;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private static GalleryDAO instance;
	
	// singleton
	public static GalleryDAO getInstance() {
		if(instance == null) {
			instance = new GalleryDAO();
		}
		return instance;
	}
	
	// 생성자
	public GalleryDAO() {
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
	
	// 글쓰기
	public int write(GalleryDTO dto) {
		int row = 0;
		String sql = "insert into gallery (idx, writer, startdate, enddate, content, local, filename, id, profilename)"
				+ "values(gallery_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
	
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getWriter());
			pstmt.setString(2, dto.getStartdate());
			pstmt.setString(3, dto.getEnddate());
			pstmt.setString(4, dto.getContent());
			pstmt.setString(5, dto.getLocal());
			pstmt.setString(6, dto.getFilename());
			pstmt.setString(7, dto.getId());
			pstmt.setString(8, dto.getProfilename());
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("write: " + e);
		} finally { close(); }
	
		return row;
	}
	
	// 갤러리 리스트
	public HashMap<String, Object> galleryList(int page) {
		return galleryList(page, null);
	}
		
	// 갤러리 검색
	public HashMap<String, Object> galleryList(int page, String word) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String sql = "select * from gallery where deleted='n' %s order by idx desc";
		boolean flag = (word != null);
		
		String search = String.format("and writer like '%%%s%%' or content like '%%%s%%' or local like '%%%s%%'", word, word, word);
		sql = String.format(sql, flag ? search : "");		
		
		String offsetSql = " offset %d rows fetch first %d rows only";
		Paging pag = new Paging(page, (flag ? searchCount(word) : galleryCount()));
		offsetSql = String.format(offsetSql, pag.getOffset(), pag.getPerPage());
		sql += offsetSql;
		
		ArrayList<GalleryDTO> list = null;
		

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<GalleryDTO>();
			GalleryDTO dto = null;
			
			while(rs.next()) {
				dto = new GalleryDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setWriter(rs.getString("writer"));
				dto.setFilename(rs.getString("filename"));
				dto.setStartdate(rs.getString("startdate"));
				dto.setEnddate(rs.getString("enddate"));
				dto.setContent(rs.getString("content"));
				dto.setLocal(rs.getString("local"));
				dto.setWritedate(rs.getString("writedate"));
				dto.setId(rs.getString("id"));
				dto.setProfilename(rs.getString("profilename"));
				dto.setViewCount(rs.getInt("viewcount"));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("galleryList: " + e);
		} finally { close(); }
		map.put("galleryList", list);
		map.put("paging", pag);
		return map;	
	}
	
	// 갤러리 정렬
	public HashMap<String, Object> sortList(int page, int sort) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String sql = "select * from gallery where deleted='n' order by %s desc";
  
		if (sort == 1) {
			sql = String.format(sql, "startdate");   
		} else if (sort == 2) {
			sql = String.format(sql, "viewcount");   
		} else {
			System.out.println("sortList : 실패");
		}
  
		String offsetSql = " offset %d rows fetch first %d rows only";
	Paging pag = new Paging(page, galleryCount());
	offsetSql = String.format(offsetSql, pag.getOffset(), pag.getPerPage());
	sql += offsetSql;
     
	ArrayList<GalleryDTO> list = null;
  
	try {
		conn = ds.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
     
		list = new ArrayList<GalleryDTO>();
		GalleryDTO dto = null;
     
	while(rs.next()) {
		dto = new GalleryDTO();
		dto.setIdx(rs.getInt("idx"));
		dto.setWriter(rs.getString("writer"));
		dto.setFilename(rs.getString("filename"));
		dto.setStartdate(rs.getString("startdate"));
		dto.setEnddate(rs.getString("enddate"));
		dto.setContent(rs.getString("content"));
		dto.setLocal(rs.getString("local"));
		dto.setWritedate(rs.getString("writedate"));
		dto.setId(rs.getString("id"));
		dto.setProfilename(rs.getString("profilename"));
		dto.setViewCount(rs.getInt("viewcount"));
		list.add(dto);
	}
	} catch (SQLException e) {
		System.out.println("sortList: " + e);
	} finally { close(); }
	  map.put("galleryList", list);
	  map.put("paging", pag);
	  return map;  
   }
	
	// 마이홈 갤러리 리스트
	public HashMap<String, Object> mygalleryList(int page, String id) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String sql = "select * from gallery where deleted='n' and id=? order by idx desc";
		
		String offsetSql = " offset %d rows fetch first %d rows only";
		Paging pag = new Paging(page, myHomeCount(id));
		offsetSql = String.format(offsetSql, pag.getOffset(), pag.getPerPage());
		sql += offsetSql;
		
		ArrayList<GalleryDTO> list = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<GalleryDTO>();
			GalleryDTO dto = null;
			
			while(rs.next()) {
				dto = new GalleryDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setWriter(rs.getString("writer"));
				dto.setFilename(rs.getString("filename"));
				dto.setStartdate(rs.getString("startdate"));
				dto.setEnddate(rs.getString("enddate"));
				dto.setContent(rs.getString("content"));
				dto.setLocal(rs.getString("local"));
				dto.setWritedate(rs.getString("writedate"));
				dto.setId(rs.getString("id"));
				dto.setProfilename(rs.getString("profilename"));
				dto.setViewCount(rs.getInt("viewcount"));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("mygalleryList: " + e);
		} finally { close(); }
		map.put("galleryList", list);
		map.put("paging", pag);
		return map;	
	}

	// 총 게시글 수
    public int galleryCount() {
       int count = 0;
       
       String sql = "select count(*) from gallery where deleted='n'";

       try {
          conn = ds.getConnection();
          pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();      
          while(rs.next()) {
             count = rs.getInt(1);
          }
       } catch (SQLException e) {
          System.out.println("galleryCount : " + e);
       } finally { close(); }
       
          return count;
    }
    
	// 마이홈 게시글 수
	public int myHomeCount(String id) {
		int count = 0;
		String sql = "select count(*) from gallery where id=? and deleted='n'";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();      
			while(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("selectReplyList : " + e);
		} finally { close(); }
	      return count;
	}
	
	// 검색 게시글 수
	
	public int searchCount(String word) {
		int count = 0;
		String sql = "select count(*) from gallery where deleted='n' %s order by idx desc";
		String search = String.format("and writer like '%%%s%%' or content like '%%%s%%' or local like '%%%s%%'", word, word, word);
		
		sql = String.format(sql, search);
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();      
			while(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("searchCount : " + e);
		} finally { close(); }
	      return count;
	}
	
	// 게시글 보기
	public GalleryDTO selectOne(int idx) {
		
		GalleryDTO dto = null;
		String sql = "select * from gallery where idx=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {				
				dto = new GalleryDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setWriter(rs.getString("writer"));
				dto.setFilename(rs.getString("filename"));
				dto.setStartdate(rs.getString("startdate"));
				dto.setEnddate(rs.getString("enddate"));
				dto.setContent(rs.getString("content"));
				dto.setLocal(rs.getString("local"));
				dto.setWritedate(rs.getString("writedate"));
				dto.setId(rs.getString("id"));
				dto.setProfilename(rs.getString("profilename"));
				dto.setViewCount(rs.getInt("viewcount"));
			}
			
		} catch (SQLException e) {
			System.out.println("selectOne: " + e);
		} finally { close(); }
		
		return dto;
	}

	public int viewCount(int idx) {
		
		int row = 0;
		
		String sql = "update gallery set viewcount = viewcount + 1 where idx = ?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("viewCount: " + e);
		} finally { close(); }
		return row;
	}

	// 댓글 입력 메서드
	public int insertReply(ReplyDTO reply) {

		int row = 0;
		String sql = "insert into footreply (idx, gnum, writer, content, id)" + 
					" values (footreply_seq.nextval, ?, ?, ?, ?)";
  
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getGnum());
			pstmt.setString(2, reply.getWriter());
			pstmt.setString(3, reply.getContent());
			pstmt.setString(4, reply.getId());
			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("insertReply : " + e);
		} finally { close(); }
      return row;
	}

	// 댓글 출력 메서드
	public List<ReplyDTO> selectReplyList(int gnum) {
		List<ReplyDTO> list = null;
		String sql = "select * from footreply where gnum=? order by idx";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gnum);
			rs = pstmt.executeQuery();
			list = new ArrayList<ReplyDTO>();
			
			while(rs.next()) {
				ReplyDTO dto = new ReplyDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setGnum(rs.getInt("gnum"));
				dto.setWriter(rs.getString("writer"));
				dto.setContent(rs.getString("content"));
				dto.setWriteDate(rs.getString("writeDate"));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("selectReplyList : " + e);
		} finally { close(); }
		return list;
	}
	
	
	// 댓글 하나 선택(수정) 메서드
	public ReplyDTO selectReply(int idx) {
		
		ReplyDTO dto = null;
		String sql = "select * from footreply where idx=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {				
				dto = new ReplyDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setContent(rs.getString("content"));
				dto.setGnum(rs.getInt("gnum"));
				dto.setId(rs.getString("id"));
				dto.setWriteDate(rs.getString("writedate"));
				dto.setWriter(rs.getString("writer"));
			}
			
		} catch (SQLException e) {
			System.out.println("selectReply: " + e);
		} finally { close(); }
		
		return dto;
	}
	
	// 댓글 수정 메서드
	public int modifyReply(ReplyDTO dto) {
		int row = 0;
		String sql = "update footreply set content=? where idx=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getContent());
			pstmt.setInt(2, dto.getIdx());
			row = pstmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println("modifyReply : " + e);
		} finally { close(); }
		
		return row;
	}
	
	// 댓글 삭제 메서드
	public int deleteReply(int idx) {
		int row = 0;
		String sql = "delete from footreply where idx=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			row = pstmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println("deleteReply : " + e);
		} finally { close(); }
		
		return row;
	}
	
	// 글 삭제 메서드
	public int delete(int idx) {
		int row=0;
		String sql = "update gallery set deleted = 'y' where idx = ?";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("delete : " + e);
		} finally { close(); }
		return row;
	}

	// 글 수정 메서드
	public int modify(GalleryDTO dto) {
		int row=0;
		boolean imgflag;
		
		if(dto.getFilename() == null) {   // 첨부사진 변경 여부 확인
			imgflag = false;
		} else {
			imgflag = true;            // 첨부사진 바꿨으면 true
		}
		String sql="update gallery "
				+ "set "
				+ (imgflag ? "filename=?, ":"")
				+ "local=?, "
				+ "startdate=?, "
				+ "enddate=?, "
				+ "content=? "
				+ "where idx=?";

		try {
			conn = ds.getConnection();
			if(imgflag) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, dto.getFilename());
				pstmt.setString(2, dto.getLocal());
				pstmt.setString(3, dto.getStartdate());
				pstmt.setString(4, dto.getEnddate());
				pstmt.setString(5, dto.getContent());
				pstmt.setInt(6, dto.getIdx());
				row = pstmt.executeUpdate();
			}
			else {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, dto.getLocal());
				pstmt.setString(2, dto.getStartdate());
				pstmt.setString(3, dto.getEnddate());
				pstmt.setString(4, dto.getContent());
				pstmt.setInt(5, dto.getIdx());
				row = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("modify : " + e);
		} finally { close(); }
			return row;
	}
}


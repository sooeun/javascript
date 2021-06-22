package fileutil;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import gallery.GalleryDTO;
import member.MemberDTO;

public class FileUtil {

	private MultipartRequest mp;
	private String uploadPath;
	
	public MemberDTO getMemberDTO(HttpServletRequest request) throws IOException {
		uploadPath = "profilename";
		MemberDTO dto = null;
		ServletContext application = request.getServletContext();
		String realPath = application.getRealPath(uploadPath);
		File dir = new File(realPath);
		
		mp = new MultipartRequest(request, dir.toString(), 10485760, "UTF-8", new DefaultFileRenamePolicy());
		
		dto = new MemberDTO();
		dto.setId(mp.getParameter("id"));
		dto.setPw(mp.getParameter("pw"));
		dto.setNickname(mp.getParameter("nickname"));
		dto.setProfilename(mp.getFilesystemName("profilename")); // 파일 이름만 바로 가져오기
		dto.setIntro(mp.getParameter("intro"));
		
		return dto;
	}
	
	public GalleryDTO getGalleryDTO(HttpServletRequest request) throws IOException {
		uploadPath = "filename";
		GalleryDTO dto = null;
		ServletContext application = request.getServletContext();
		String realPath = application.getRealPath(uploadPath);
		File dir = new File(realPath);
		
		mp = new MultipartRequest(request, dir.toString(), 10485760, "UTF-8", new DefaultFileRenamePolicy());
		
		dto = new GalleryDTO();
		
		dto.setWriter(mp.getParameter("writer"));
		dto.setId(mp.getParameter("id"));
		dto.setStartdate(mp.getParameter("startdate"));
		dto.setEnddate(mp.getParameter("enddate"));
		dto.setContent(mp.getParameter("content"));
		dto.setLocal(mp.getParameter("local"));
		dto.setFilename(mp.getFilesystemName("filename")); // 파일 이름만 바로 가져오기
		dto.setProfilename(mp.getParameter("profilename"));
		
		return dto;
	}
}

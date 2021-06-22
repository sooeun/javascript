package gallery;

public class ReplyDTO {
//	TABLE : FOOTREPLY	
//	IDX	NUMBER	NO	
//  ID	
//	GNUM	NUMBER	NO	
//	WRITER	VARCHAR2(20 BYTE)	NO	
//	CONTENT	VARCHAR2(500 BYTE)	NO	
//	WRITEDATE	VARCHAR2(50 BYTE)	YES	TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI') 

	private int idx, gnum;
	private String writer, content, writeDate, id;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getGnum() {
		return gnum;
	}
	public void setGnum(int gnum) {
		this.gnum = gnum;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}

package Board;

import java.util.Date;

public class BoardVO {

	    private int boardNo;
	    private String userId;
	    private String title;
	    private String contents;
	    private int viewCnt;
	    private Date boardTime;
	    private int commentCount;
	    public BoardVO() {
	        super();
	    }
	    public int getCommentCount() {
	        return commentCount;
	    }

	    public void setCommentCount(int commentCount) {
	        this.commentCount = commentCount;
	    }
	    public int getBoardNo() {
	        return boardNo;
	    }

	    public void setBoardNo(int boardNo) {
	        this.boardNo = boardNo;
	    }

	    public String getUserId() {
	        return userId;
	    }

	    public void setUserId(String userId) {
	        this.userId = userId;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getContents() {
	        return contents;
	    }

	    public void setContents(String contents) {
	        this.contents = contents;
	    }

	    public int getViewCnt() {
	        return viewCnt;
	    }

	    public void setViewCnt(int viewCnt) {
	        this.viewCnt = viewCnt;
	    }

	    public Date getBoardTime() {
	        return boardTime;
	    }

	    public void setBoardTime(Date boardTime) {
	        this.boardTime = boardTime;
	    }

	    public BoardVO(int boardNo, String userId, String title, String contents, Date boardTime, int viewCnt) {
	        super();
	        this.boardNo = boardNo;
	        this.userId = userId;
	        this.title = title;
	        this.contents = contents;
	        this.boardTime = boardTime;
	        this.viewCnt = viewCnt;
	    }

	    @Override
	    public String toString() {
	        return "BoardVO [boardNo=" + boardNo + ", userId=" + userId + ", title=" + title + ", contents=" + contents
	                + ", viewCnt=" + viewCnt + ", boardTime=" + boardTime + "]";
	    }
	}
	

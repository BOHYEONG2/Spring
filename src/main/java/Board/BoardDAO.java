package Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import util.ConnectionFactory;

@Service
public class BoardDAO {

	 @Autowired
	 private DataSource dataSource;
	
	 public void writeBoard(BoardVO vo) {
	    StringBuilder sql = new StringBuilder();
	    sql.append("INSERT INTO board(board_No, USER_ID, title, contents, viewCnt, board_Time) ");
	    sql.append("VALUES(board_seq.nextval, ?, ?, ?, 0, SYSDATE)");
	     
	    JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);
        
	    template.update(sql.toString(), vo.getUserId(), vo.getTitle(), vo.getContents());
	    
	}

	public List<BoardVO> getBoardList() {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT BOARD_NO, USER_ID, TITLE, CONTENTS, BOARD_TIME, VIEWCNT FROM board ORDER BY BOARD_TIME DESC");
		
		 // spring JDBC
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);
        List<BoardVO> boardList = template.query(sql.toString(), new BeanPropertyRowMapper<>(BoardVO.class));
		
		return boardList;
	}
	public BoardVO getBoardByNo(int boardNo) {
        BoardVO board = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT BOARD_NO, USER_ID, TITLE, CONTENTS, BOARD_TIME, VIEWCNT ");
        sql.append("FROM board ");
        sql.append("WHERE BOARD_NO = ?");

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            pstmt.setInt(1, boardNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int no = rs.getInt("board_No");
                    String userId = rs.getString("user_Id");
                    String title = rs.getString("title");
                    String contents = rs.getString("contents");
                    Date boardTime = rs.getDate("board_Time");
                    int viewCnt = rs.getInt("VIEWCNT");
                    
                    board = new BoardVO();
                    board.setBoardNo(no);
                    board.setUserId(userId);
                    board.setTitle(title);
                    board.setContents(contents);
                    board.setBoardTime(boardTime);
                    board.setViewCnt(viewCnt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return board;
    }
	
	  public void updateBoard(BoardVO vo) {
	        StringBuilder sql = new StringBuilder();
	        sql.append("UPDATE board SET title = ?, contents = ? WHERE  BOARD_NO = ?");

	        try (
	            Connection conn = new ConnectionFactory().getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	        ) {
	            pstmt.setString(1, vo.getTitle());
	            pstmt.setString(2, vo.getContents());
	            pstmt.setInt(3, vo.getBoardNo());

	            pstmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	  public void deleteBoard(int boardNo) {
	        StringBuilder sql = new StringBuilder();
	        sql.append("DELETE FROM board WHERE  BOARD_NO = ?");

	        try (
	            Connection conn = new ConnectionFactory().getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	        ) {
	            pstmt.setInt(1, boardNo);

	            pstmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	  public List<BoardVO> getAllBoards() {
	        List<BoardVO> boardList = new ArrayList<>();

	        try (
	            Connection conn = new ConnectionFactory().getConnection();
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM board");
	        ) {
	            while (rs.next()) {
	                int boardNo = rs.getInt("boardNo");
	                String title = rs.getString("title");
	                String contents = rs.getString("contents");

	                BoardVO board = new BoardVO();
	                board.setBoardNo(boardNo);
	                board.setTitle(title);
	                board.setContents(contents);

	                boardList.add(board);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return boardList;
	    }
	

	  public List<BoardVO> getBoardListPaging(int pageNo, int numPerPage) {
		    List<BoardVO> boardList = new ArrayList<>();

		    int startNum = (pageNo - 1) * numPerPage + 1;
		    int endNum = pageNo * numPerPage;

		    StringBuilder sql = new StringBuilder();
		    sql.append("SELECT BOARD_NO, USER_ID, TITLE, CONTENTS, BOARD_TIME, VIEWCNT ");
		    sql.append("FROM (SELECT ROW_NUMBER() OVER (ORDER BY BOARD_TIME ASC) AS RNUM, BOARD_NO, USER_ID, TITLE, CONTENTS, BOARD_TIME, VIEWCNT ");
		    sql.append("      FROM board) ");
		    sql.append("WHERE RNUM BETWEEN ? AND ?");

		    try (Connection conn = new ConnectionFactory().getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
		        pstmt.setInt(1, startNum);
		        pstmt.setInt(2, endNum);

		        try (ResultSet rs = pstmt.executeQuery()) {
		            while (rs.next()) {
		                int boardNo = rs.getInt("BOARD_NO");
		                String userId = rs.getString("USER_ID");
		                String title = rs.getString("TITLE");
		                String contents = rs.getString("CONTENTS");
		                java.sql.Date boardTime = rs.getDate("BOARD_TIME");
		                int viewCnt = rs.getInt("VIEWCNT");

		                BoardVO board = new BoardVO();
		                board.setBoardNo(boardNo);
		                board.setUserId(userId);
		                board.setTitle(title);
		                board.setContents(contents);
		                board.setBoardTime(boardTime);
		                board.setViewCnt(viewCnt);

		                boardList.add(board);
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return boardList;
		}
	  
	  public void updateViewCount(int boardNo) {
		    StringBuilder sql = new StringBuilder();
		    sql.append("UPDATE board SET viewCnt = viewCnt + 1 WHERE BOARD_NO = ?");

		    try (Connection conn = new ConnectionFactory().getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
		        pstmt.setInt(1, boardNo);
		        pstmt.executeUpdate();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		public int getTotalBoardCount() {
		    int count = 0;

		    StringBuilder sql = new StringBuilder();
		    sql.append("SELECT COUNT(*) AS count FROM board");

		    try (Connection conn = new ConnectionFactory().getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		         ResultSet rs = pstmt.executeQuery()) {

		        while (rs.next()) {
		            count = rs.getInt("count");
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return count;
		}
		

		public List<BoardVO> searchBoards(String userId, String keyword, String string) {
		    String query = "SELECT * FROM board WHERE user_id = ?";
		    
		    try {
		        Connection conn = new ConnectionFactory().getConnection();
		        PreparedStatement pstmt = conn.prepareStatement(query);
		        pstmt.setString(1, userId);
		        ResultSet rs = pstmt.executeQuery();

		        List<BoardVO> boardList = new ArrayList<>();

		        while (rs.next()) {
		            BoardVO board = new BoardVO();
		            board.setBoardNo(rs.getInt("boardNo"));
		            board.setUserId(rs.getString("userId"));
		            board.setTitle(rs.getString("title"));
		            board.setBoardTime(rs.getDate("boardTime"));
		            board.setViewCnt(rs.getInt("viewCnt"));

		            boardList.add(board);
		        }

		        return boardList;
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return null;
		}
	
	    public int getTotalBoardCountByUserId(String userId) {
	        int totalCount = 0;

	        try (Connection conn = new ConnectionFactory().getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM board WHERE user_id = ?")) {
	            pstmt.setString(1, userId);
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                totalCount = rs.getInt(1);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return totalCount;
	    }

	    public List<BoardVO> getBoardListByUserId(String userId, int pageNo, int numPerPage) {
	        String query = "SELECT * FROM board WHERE user_id = ? ORDER BY boardNo DESC LIMIT ?, ?";
	        try {
	            Connection conn = new ConnectionFactory().getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(query);
	            pstmt.setString(1, userId);
	            pstmt.setInt(2, (pageNo - 1) * numPerPage);
	            pstmt.setInt(3, numPerPage);
	            ResultSet rs = pstmt.executeQuery();

	            List<BoardVO> boardList = new ArrayList<>();

	            while (rs.next()) {
	                BoardVO board = new BoardVO();
	                board.setBoardNo(rs.getInt("boardNo"));
	                board.setUserId(rs.getString("userId"));
	                board.setTitle(rs.getString("title"));
	                board.setBoardTime(rs.getDate("boardTime"));
	                board.setViewCnt(rs.getInt("viewCnt"));

	                boardList.add(board);
	            }

	            return boardList;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return null;
	    }

	    // 게시글에 댓글 수를 업데이트하는 메서드
	    public void updateCommentCount(int boardNo, int commentCount) {
	        StringBuilder sql = new StringBuilder();
	        sql.append("UPDATE board SET comment_count = ? WHERE board_no = ?");

	        try (Connection conn = new ConnectionFactory().getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
	            pstmt.setInt(1, commentCount);
	            pstmt.setInt(2, boardNo);

	            pstmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // 게시글의 댓글 수를 가져오는 메서드
	    public int getCommentCount(int boardNo) {
	        int commentCount = 0;

	        StringBuilder sql = new StringBuilder();
	        sql.append("SELECT comment_count FROM board WHERE board_no = ?");

	        try (Connection conn = new ConnectionFactory().getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
	            pstmt.setInt(1, boardNo);

	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    commentCount = rs.getInt("comment_count");
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return commentCount;
	    }
	
}
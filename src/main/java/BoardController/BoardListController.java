package BoardController;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Board.BoardDAO;
import Board.BoardVO;


/*	BoardDAO dao = new BoardDAO();
	List<BoardVO> boardList = dao.getBoardList();
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("boardList", boardList);
		mv.setViewName("index");
	
		
		return mv;
	}
 */

//@Controller
@RestController
public class BoardListController {
	
	@Autowired
	private BoardDAO boardDAO;
	
	
	//전체목록
	@RequestMapping("/board")
	public String boardList(Model model) throws ClassNotFoundException, SQLException {
		List<BoardVO> boardList = boardDAO.getBoardList();
		model.addAttribute("boardList", boardList);
		return "index";
	}
	
	//전체목록API
	@GetMapping("/api/news")
	public List<BoardVO> boardListAPI() throws ClassNotFoundException, SQLException {
		List<BoardVO> boardList = boardDAO.getBoardList();
		return boardList;
	}
	
}

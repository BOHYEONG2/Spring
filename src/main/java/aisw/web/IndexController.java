package aisw.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import Board.BoardDAO;
import Board.BoardVO;

public class IndexController implements Controller {


	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BoardDAO boardDao = new BoardDAO();
		List<BoardVO> boardList = boardDao.getBoardList();
		
		 request.setAttribute("boardList", boardList);
		
		ModelAndView mv = new ModelAndView();
//		mv.addObject("data", "Hello~~~");
		mv.setViewName("index");
		
		return mv;
	}
}

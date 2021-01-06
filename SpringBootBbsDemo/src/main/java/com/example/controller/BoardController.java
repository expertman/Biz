package com.example.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.service.BoardService;
import com.example.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
	@Autowired
	private BoardService boardService;

	@GetMapping("/list")
	public String list(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		this.boardService.readAll(map);
		List<BoardVO> list = (List<BoardVO>)map.get("results");
		list.forEach(board ->{
			String filename = board.getFilename();
			int idx = filename.lastIndexOf(".");
			String ext = filename.substring(idx + 1);
			board.setFilename(ext);
		});
		log.warn("size = " + list.size());
		model.addAttribute("boardlist", list);
		return "list";     //  /templates/list.html
	}
	
	@GetMapping("/write")
	public String write() {
		return "write";     // /templates/write.html
	}
	
	@PostMapping("/write")
	public String write(BoardVO boardVo, @RequestParam("company") String company, @RequestParam("file") MultipartFile file) throws Exception{
		String email = boardVo.getEmail();
		if(!email.equals("")) email += "@" + company;
		boardVo.setEmail(email);
		this.boardService.create(boardVo, file);
		return "redirect:/list";
	}
}

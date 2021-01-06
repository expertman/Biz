package com.example.service;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.BoardDao;
import com.example.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Service("boardService")
@Slf4j
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;

	@Override
	public void create(BoardVO boardVo, MultipartFile file) throws Exception{
		String filename = file.getOriginalFilename();
		/* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
        String savePath = System.getProperty("user.dir") + "/files";
        /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
        if (!new File(savePath).exists()) {
            try{
                new File(savePath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }
        String filePath = savePath + "/" + filename;
        file.transferTo(new File(filePath));
        boardVo.setFilename(filename);
        this.boardDao.insertBoard(boardVo);
	}

	@Override
	public void read(Map map) {
		// TODO Auto-generated method stub
		this.boardDao.selectBoard(map);
	}

	@Override
	public void readAll(Map map) {
		this.boardDao.selectAllBoard(map);
	}

	@Override
	public void update(BoardVO boardVo) {
		this.boardDao.updateBoard(boardVo);
	}

	@Override
	public void delete(int idx) {
		// TODO Auto-generated method stub

	}

}

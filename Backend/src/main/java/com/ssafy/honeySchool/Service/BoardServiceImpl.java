package com.ssafy.honeySchool.Service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.honeySchool.model.BoardDto;
import com.ssafy.honeySchool.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{

	
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> selectBoard(String school, int grade, int classes) throws SQLException {
		return boardMapper.selectBoard(school, grade, classes);
	}

}

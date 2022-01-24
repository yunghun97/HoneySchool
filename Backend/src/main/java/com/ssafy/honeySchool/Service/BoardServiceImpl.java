package com.ssafy.honeySchool.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.honeySchool.model.BoardDto;
import com.ssafy.honeySchool.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private SqlSession sqlSession;
	
//	@Autowired
//	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> selectBoard(String school, int grade, int classes) throws SQLException {
		System.out.println("서비스 진입");
		Map<String, String> map = new HashMap<>();
		map.put("school", school);
		map.put("grade", String.valueOf(grade));
		map.put("classes", String.valueOf(classes));
		List<BoardDto> list = sqlSession.getMapper(BoardMapper.class).selectBoard(map);
		System.out.println("조회 성공");
		return list;
	}

	public void insertBoard(BoardDto boardDto) throws SQLException {
		System.out.println("insert직전");
		sqlSession.getMapper(BoardMapper.class).insertBoard(boardDto);
		return;
	}
	

}

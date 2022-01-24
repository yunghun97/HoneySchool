package com.ssafy.honeySchool.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ssafy.honeySchool.model.BoardDto;
import com.ssafy.honeySchool.model.CommentDto;


public interface BoardMapper {		
		List<BoardDto> selectBoard(Map map) throws SQLException; // 조회	
		void insertBoard(BoardDto boardDto) throws SQLException;
}

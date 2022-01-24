package com.ssafy.honeySchool.Service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.honeySchool.model.BoardDto;

public interface BoardService {

	List<BoardDto> selectBoard(String school, int grade, int classes) throws SQLException;

}

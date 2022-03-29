package com.ssafy.honeySchool.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.honeySchool.api.dto.ClassBoardDto;
import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.ClassBoardFile;
import com.ssafy.honeySchool.db.entity.User;
import com.ssafy.honeySchool.db.repository.ClassBoardFileRepository;
import com.ssafy.honeySchool.db.repository.ClassBoardRepository;

@Service
public class BoardService {
	
    private ClassBoardRepository classBoardRepository;

    private ClassBoardFileRepository classBoardFileRepository;

    private FileHandler fileHandler;

    @Autowired
    public BoardService(ClassBoardRepository classBoardRepository, ClassBoardFileRepository classBoardFileRepository) {
        this.classBoardRepository = classBoardRepository;
        this.classBoardFileRepository = classBoardFileRepository;
        this.fileHandler = new FileHandler();
    }
    
    // 페이지네이션
    // 반 게시판 전체 목록 (페이징)
    public Page<ClassBoardDto> findAll(String school, int grade, int classes, Pageable pageable) {
		return classBoardRepository.findBySchoolAndGradeAndClassesOrderByIdDesc(school, grade, classes, pageable)
        		.map(ClassBoardDto::from);
    }
    // Jpa로 category 구분해서 가져오기
    public Page<ClassBoardDto> findAllByCategory(String school, int grade, int classes, String category, Pageable pageable) {
    	return classBoardRepository.findBySchoolAndGradeAndClassesAndCategoryOrderByIdDesc(school, grade, classes, category, pageable)
    			.map(ClassBoardDto::from);
    }
    // 특정 category에서 특정 user가 쓴 글 모아보기
    public Page<ClassBoardDto> findAllByCategoryAndUser(String school, int grade, int classes, String category, User user, Pageable pageable) {
    	return classBoardRepository.findBySchoolAndGradeAndClassesAndCategoryAndUserOrderByIdDesc(school, grade, classes, category, user, pageable)
    			.map(ClassBoardDto::from);
    }
    
    
    public ClassBoard addBoard(
            ClassBoard board,
            List<MultipartFile> files,
            String rootPath,
            int commentId  // 수정
    ) throws Exception {
    	
        // 파일을 저장하고 그 files 에 대한 list 를 가지고 있는다
    	List<ClassBoardFile> list = fileHandler.parseFileInfo(board.getId(), files, rootPath, commentId);
        
        if(list.isEmpty()){
            // TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
        	return board;
        }
        // 파일에 대해 DB에 저장하고 가지고 있을 것
        else{
        	// 원본 코드에서는 여기서 게시물의 pk가 뜨지 않아서 board_id가 0이 됨
            List<ClassBoardFile> fileBeans = new ArrayList<>();
            for(ClassBoardFile classBoardFile : list) {
            	fileBeans.add(classBoardFileRepository.save(classBoardFile));
            }
//            board.setPictures(fileBeans);
        }

//        board.setReported_date(new Date().toString());

//        return classBoardRepository.save(board);
        return board;
    }
}
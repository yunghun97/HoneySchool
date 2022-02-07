package com.ssafy.honeySchool.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.db.entity.ClassBoard;
import com.ssafy.db.entity.ClassBoardFile;
import com.ssafy.db.repository.ClassBoardFileRepository;
import com.ssafy.db.repository.ClassBoardRepository;

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

    public ClassBoard addBoard(
            ClassBoard board,
            List<MultipartFile> files,
            String rootPath
    ) throws Exception {
    	
    	// 먼저 게시물 정보를 DB에 저장한다 -> board.getId()도 pk값을 갖게 된다.
    	ClassBoard saveBoard = classBoardRepository.save(board);
//    	System.out.println("여기에 pk가 있을까요" + saveBoard.getId());
//    	System.out.println("여기에 pk가 있을까요" + board.getId());
    	
        // 파일을 저장하고 그 BoardPicture 에 대한 list 를 가지고 있는다
    	List<ClassBoardFile> list = fileHandler.parseFileInfo(board.getId(), files, rootPath);
        
        if(list.isEmpty()){
            // TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
        	return saveBoard;
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
        return saveBoard;
    }
}
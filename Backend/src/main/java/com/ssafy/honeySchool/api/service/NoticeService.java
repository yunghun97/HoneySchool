package com.ssafy.honeySchool.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.honeySchool.api.dto.ClassBoardDto;
import com.ssafy.honeySchool.api.dto.NoticeDto;
import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.ClassBoardFile;
import com.ssafy.honeySchool.db.entity.Notice;
import com.ssafy.honeySchool.db.entity.NoticeFile;
import com.ssafy.honeySchool.db.repository.ClassBoardFileRepository;
import com.ssafy.honeySchool.db.repository.ClassBoardRepository;
import com.ssafy.honeySchool.db.repository.NoticeFileRepository;
import com.ssafy.honeySchool.db.repository.NoticeRepository;

@Service
public class NoticeService {
	
	private NoticeRepository noticeRepository;

    private NoticeFileRepository noticeFileRepository;

    private FileHandler fileHandler;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository, NoticeFileRepository noticeFileRepository) {
        this.noticeRepository = noticeRepository;
        this.noticeFileRepository = noticeFileRepository;
        this.fileHandler = new FileHandler();
    }
    
    
    // 페이지네이션
    // 반 게시판 전체 목록 (페이징)
    public Page<NoticeDto> findAll(Pageable pageable) {
		return noticeRepository.findAll(pageable)
        		.map(NoticeDto::from);
    }
    

    public Notice addNotice(
            Notice notice,
            List<MultipartFile> files,
            String rootPath
    ) throws Exception {
    	
        // 파일을 저장하고 그 files 에 대한 list 를 가지고 있는다
    	List<NoticeFile> list = fileHandler.parseNoticeFileInfo(notice.getId(), files, rootPath);
        
        if(list.isEmpty()){
            // TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
        	return notice;
        }
        // 파일에 대해 DB에 저장하고 가지고 있을 것
        else{
        	// 원본 코드에서는 여기서 게시물의 pk가 뜨지 않아서 board_id가 0이 됨
            List<NoticeFile> fileBeans = new ArrayList<>();
            for(NoticeFile noticeFile : list) {
            	fileBeans.add(noticeFileRepository.save(noticeFile));
            }
//            board.setPictures(fileBeans);
        }

//        board.setReported_date(new Date().toString());

//        return classBoardRepository.save(board);
        return notice;
    }
	
}

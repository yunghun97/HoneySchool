package com.ssafy.honeySchool.api.controller;

import java.io.File;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.honeySchool.api.dto.ClassBoardDto;
import com.ssafy.honeySchool.api.dto.CommentDto;
import com.ssafy.honeySchool.api.dto.NoticeDto;
import com.ssafy.honeySchool.api.service.BoardService;
import com.ssafy.honeySchool.api.service.NoticeService;
import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.ClassBoardFile;
import com.ssafy.honeySchool.db.entity.Comment;
import com.ssafy.honeySchool.db.entity.DeleteYn;
import com.ssafy.honeySchool.db.entity.Notice;
import com.ssafy.honeySchool.db.entity.NoticeFile;
import com.ssafy.honeySchool.db.entity.User;
import com.ssafy.honeySchool.db.repository.ClassBoardFileRepository;
import com.ssafy.honeySchool.db.repository.ClassBoardRepository;
import com.ssafy.honeySchool.db.repository.CommentRepository;
import com.ssafy.honeySchool.db.repository.NoticeFileRepository;
import com.ssafy.honeySchool.db.repository.NoticeRepository;
import com.ssafy.honeySchool.db.repository.UserRepository;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/board")
public class NoticeController {
		
	@Autowired
	private NoticeRepository noticeRepository;

	@Autowired
	private NoticeFileRepository noticeFileRepository;
	
	// 서버 상대경로 얻을 때 사용
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private NoticeService noticeService;
	
	// 공지사항 전체 목록
	@GetMapping("/notice")
	public ResponseEntity<?> selectNotice(Pageable pageable) throws SQLException{
//		List<NoticeDto> noticeDtos = noticeService.findAll(pageable).getContent();				
		Page<NoticeDto> noticeDtos = noticeService.findAll(pageable);				
		return new ResponseEntity<Page<NoticeDto>>(noticeDtos, HttpStatus.OK);
	}
	// 공지사항 쓰기
	@PostMapping("/notice")
	public ResponseEntity<?> createNotice(
			HttpServletRequest req,
			@RequestPart(value="files", required = false) List<MultipartFile> files
	) throws Exception{
		// 상대경로 찾기
		String rootPath = request.getSession().getServletContext().getRealPath("/uploads");
		// 저장 경로 변경 부분
		String resourcesPath = rootPath.substring(0, rootPath.length()-14) + "resources\\static\\uploads";	
		rootPath = resourcesPath;
		
		// User 수정 함
        User user = userRepository.findByUserId(req.getParameter("userId")).get();
               
        // notice 저장
        Notice notice = noticeRepository.save(Notice.builder()
				.title(req.getParameter("title"))
				.content(req.getParameter("content"))
				.user(user)
				.viewcount(0)
				.build());
        // 파일 저장
        Notice sameNotice = noticeService.addNotice(notice, files, rootPath);

		URI uriLocation = new URI("/board/" + notice.getId());
        return ResponseEntity.created(uriLocation).body("{}");	
	}
	// 공지사항 상세 (+ 파일 같이 가져옴)
	@Transactional
	@GetMapping("/notice/{noticeId}")
	public ResponseEntity<Map<String, Object>> detailNotice(@PathVariable int noticeId, HttpServletRequest req) {
		Notice notice = noticeRepository.findById(noticeId);
		noticeRepository.updateView(noticeId);  // 조회수 증가
		// notice dto로 바꾸기
		NoticeDto noticeDto = NoticeDto.from(notice);
		
		List<NoticeFile> files = noticeFileRepository.findAllByNoticeIdAndIsDeleted(noticeId, DeleteYn.N);
		// Map 사용해서 묶기
		Map<String, Object> map = new HashMap<>();
		map.put("board", noticeDto);
		map.put("files", files);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	// 공지사항 삭제 - 첨부파일도 같이 지워짐
	@DeleteMapping("/notice/{noticeId}")
	public HttpStatus deleteBoard(@PathVariable int noticeId) {
		// 첨부파일 같이 지우기
		noticeFileRepository.deleteFile(noticeId);
			
		Notice notice = noticeRepository.findById(noticeId);
		noticeRepository.delete(notice);
		return HttpStatus.OK;
	}
	// 공지사항 수정
	@Transactional
	@PutMapping("/notice/{noticeId}")
	public ResponseEntity<?> updateNotice(
			@PathVariable int noticeId,
			HttpServletRequest req, 
			@RequestPart(value="files", required = false) List<MultipartFile> files) 
	throws Exception{
		Notice notice = noticeRepository.findById(noticeId);
		// 기존에 존재하던 첨부파일 모두 삭제 (댓글 빼고 글만)
		noticeFileRepository.deleteFile(noticeId);
		// 현재 추가하는 첨부파일 저장
		String rootPath = request.getSession().getServletContext().getRealPath("/uploads");
		String resourcesPath = rootPath.substring(0, rootPath.length()-14) + "resources\\static\\uploads";	
		rootPath = resourcesPath;
		Notice sameNotice = noticeService.addNotice(notice, files, rootPath);
		// 수정 내용 저장
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		notice.update(title, content);
		// Dto로 변경
		NoticeDto noticeDto = NoticeDto.from(notice);
		return new ResponseEntity<NoticeDto>(noticeDto, HttpStatus.OK);
	}
}

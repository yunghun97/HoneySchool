package com.ssafy.honeySchool.api.controller;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.honeySchool.api.dto.ClassBoardDto;
import com.ssafy.honeySchool.api.dto.CommentDto;
import com.ssafy.honeySchool.api.service.BoardService;
import com.ssafy.honeySchool.api.service.CommentService;
import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.ClassBoardFile;
import com.ssafy.honeySchool.db.entity.Comment;
import com.ssafy.honeySchool.db.entity.DeleteYn;
import com.ssafy.honeySchool.db.entity.User;
import com.ssafy.honeySchool.db.repository.ClassBoardFileRepository;
import com.ssafy.honeySchool.db.repository.ClassBoardRepository;
import com.ssafy.honeySchool.db.repository.CommentRepository;
import com.ssafy.honeySchool.db.repository.UserRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
		
	@Autowired
	private ClassBoardRepository classBoardRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ClassBoardFileRepository classBoardFileRepository;

	@Autowired
	private CommentService commentService;
	
	// 서버 상대경로 얻을 때 사용
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
    private BoardService boardService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	// 반 게시판 전체 목록 (페이징)
	@GetMapping("/class")
    public ResponseEntity selectBoard(HttpServletRequest req, Pageable pageable) {
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		Page<ClassBoardDto> boardDtos = boardService.findAll(school, grade, classes, pageable);
//		List<ClassBoardDto> boardDtos = boardService.findAll(school, grade, classes, pageable).getContent();
        
        return new ResponseEntity<>(boardDtos, HttpStatus.OK);
    }
//	// 반 게시판 전체 목록 (페이징 전)
//	@GetMapping("/class")
//	public ResponseEntity<?> selectBoard(HttpServletRequest req) throws SQLException{
//		String school = req.getParameter("school");		
//		int grade = Integer.parseInt(req.getParameter("grade"));
//		int classes = Integer.parseInt(req.getParameter("classes"));
//		List<ClassBoard> classBoards = classBoardRepository.findBySchoolAndGradeAndClassesOrderByIdDesc(school, grade, classes);
//		// dto로 묶기
//		List<ClassBoardDto> classBoardDtos = new ArrayList<ClassBoardDto>();
//		for(int i = 0; i < classBoards.size(); i++) {
//			classBoardDtos.add(ClassBoardDto.from(classBoards.get(i)));
//		}				
//		return new ResponseEntity<List<ClassBoardDto>>(classBoardDtos, HttpStatus.OK);
//	}
	// 반 게시판 글쓰기
	@PostMapping("/class")
	public ResponseEntity<?> createBoard(
			ClassBoard body, 
			HttpServletRequest req,
			// validation 라이브러리가 없어서 @Valid를 뺌 
//			@Valid @RequestParam("files") List<MultipartFile> files
//			@RequestParam("files") List<MultipartFile> files
			@RequestPart(value="files", required = false) List<MultipartFile> files
	) throws Exception{
		System.out.println("insert 집입");
		
		// 원래 코드에서는 FileHandler 에서 절대경로를 얻었지만, 상대경로로 바꿔야해서 controller에서 생성 -> service로 전달 -> FileHandler로 전달 -> 파일 저장
		String rootPath = request.getSession().getServletContext().getRealPath("/uploads");
		System.out.println("바뀐 static path?? : " + rootPath);
		// 저장 경로 변경 부분
		String resourcesPath = rootPath.substring(0, rootPath.length()-14) + "resources\\static\\uploads";	
//		System.out.println("자른 거: " + resourcesPath + "resources\\static\\uploads");
		
		rootPath = resourcesPath;
		System.out.println("루트패스 수정: " + rootPath);
		
		// User 수정 함
        User user = userRepository.findByUserId(req.getParameter("userId")).get();
               
        // 파일 저장과 board 저장을 분리하기
        // board 저장
        ClassBoard board = classBoardRepository.save(ClassBoard.builder()
				.category(body.getCategory())
				.title(body.getTitle())
				.content(body.getContent())
				.user(user)
				.school(body.getSchool())
				.grade(body.getGrade())
				.classes(body.getClasses())
				.viewcount(0)
				.build());
        // 파일 저장
        ClassBoard sameBoard = boardService.addBoard(board, files, rootPath, 0);
        
		// BoardService에서는 전달한 게시글의 id를 못찾는다. -> id를 DB에서 생성하기 때문에, 데이터가 DB에 들어가고 난 후에만 getId가 가능하다. 그전까지는 0으로 되는듯
//		System.out.println("보드 id : " + board.getId());
		URI uriLocation = new URI("/board/" + board.getId());
        return ResponseEntity.created(uriLocation).body("{}");	
	}
	// Jpa로 category 구분해서 가져오기 (페이징)
	@GetMapping("/class/category")
    public ResponseEntity selectCategory(HttpServletRequest req, Pageable pageable) {
		String category = req.getParameter("category");
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		Page<ClassBoardDto> boardDtos = boardService.findAllByCategory(school, grade, classes, category, pageable);
//		List<ClassBoardDto> boardDtos = boardService.findAllByCategory(school, grade, classes, category, pageable).getContent();
        
        return new ResponseEntity<>(boardDtos, HttpStatus.OK);
	}
//	// Jpa로 category 구분해서 가져오기 (pk 내림차순)
//	@GetMapping("/class/category")
//	public ResponseEntity<?> selectCategory(HttpServletRequest req) {
//		String category = req.getParameter("category");
//		String school = req.getParameter("school");		
//		int grade = Integer.parseInt(req.getParameter("grade"));
//		int classes = Integer.parseInt(req.getParameter("classes"));	
//		List<ClassBoard> classBoards = classBoardRepository.findBySchoolAndGradeAndClassesAndCategoryOrderByIdDesc(school, grade, classes, category);
//		// dto로 묶기
//		List<ClassBoardDto> classBoardDtos = new ArrayList<ClassBoardDto>();
//		for(int i = 0; i < classBoards.size(); i++) {
//			classBoardDtos.add(ClassBoardDto.from(classBoards.get(i)));
//		}		
//		return new ResponseEntity<List<ClassBoardDto>>(classBoardDtos, HttpStatus.OK);
//	}
	// 특정 category에서 특정 user가 쓴 글 모아보기 (페이징, pk 내림차순)
	// User 부분 수정함
	@GetMapping("/class/category/user")
	public ResponseEntity<?> selectCategoryAndUser(HttpServletRequest req, Pageable pageable) throws SQLException{
		String category = req.getParameter("category");
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		// User 수정 함
		User user = userRepository.findByUserId(req.getParameter("userId")).get();
//		List<ClassBoardDto> boardDtos = boardService.findAllByCategoryAndUser(school, grade, classes, category, user, pageable).getContent();			
		Page<ClassBoardDto> boardDtos = boardService.findAllByCategoryAndUser(school, grade, classes, category, user, pageable);			
		return new ResponseEntity<>(boardDtos, HttpStatus.OK);
	}
	// 전체게시판 글 상세 (+ 댓글 같이 가져옴)
	@Transactional
	@GetMapping("/class/detail")
	public ResponseEntity<Map<String, Object>> detailBoard(HttpServletRequest req) {
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		int id = Integer.parseInt(req.getParameter("id"));
		classBoardRepository.updateView(id);
		// 글정보, 댓글, 파일정보 같이 묶어서 응답
		ClassBoard detail = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		// ClassBoard dto로 바꾸기
		ClassBoardDto detailDto = ClassBoardDto.from(detail);
		
		// question 파라미터 있는지 확인
		List<Comment> comments = new ArrayList<Comment>(); 
        if (req.getParameterMap().containsKey("userId")) {  // 숙제게시판
        	User user = userRepository.findByUserId(req.getParameter("userId")).get();
        	comments = commentRepository.findCommentByClassBoardAndUserAndParentId(detail, user, 0).get();    		        	
    	} else {  // 다른 게시판
    		comments = commentRepository.findCommentByClassBoardOrderByParentIdAsc(detail).get();    		
    	}
		
		
		// 댓글 dto로 바꾸기
		List<CommentDto> commentdtos = new ArrayList<CommentDto>();
		for(int i = 0; i < comments.size(); i++) {
			commentdtos.add(CommentDto.from(comments.get(i)));
		}		
		List<ClassBoardFile> files = classBoardFileRepository.findByBoardIdAndIsDeleted(id, DeleteYn.N);
		// 댓글, 대댓글 정렬
		List<CommentDto> sortCommentDtos = commentService.sortCommentDtos(commentdtos);
		
		// Map 사용해서 묶기
		Map<String, Object> map = new HashMap<>();
		map.put("board", detailDto);
		map.put("comments", sortCommentDtos);
		map.put("files", files);
		System.out.println("파일 주소 !!");
		System.out.println(files.toString());
		return new ResponseEntity<>(map, HttpStatus.OK);
//		return new ResponseEntity<ClassBoard>(detail, HttpStatus.OK);
	}
	// 전체게시판 글 삭제 - 댓글, 첨부파일도 같이 지워짐
	@DeleteMapping("/class")
	public HttpStatus deleteBoard(HttpServletRequest req) {
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		int id = Integer.parseInt(req.getParameter("id"));
		// 첨부파일 같이 지우기
		classBoardFileRepository.deleteFile(id);
		
		ClassBoard board = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		classBoardRepository.delete(board);  // 원래는 여기서 에러났는데, comment에서 board_id 외래키를 on delete cascade로 수정해주니 댓글도 다같이 자동 삭제된다.
		return HttpStatus.OK;
	}
	// 전체게시판 글 수정
	// API 명세서랑 다르게 일단 entity 자체를 리턴했습니다
	// Request로 첨부파일 수정 여부 주시면 됩니다 (여기서는 String 'Y', 'N'으로 써놨어요)
	@Transactional
	@PutMapping("/class")
	public ResponseEntity<?> updateBoard(
			ClassBoard body, 
			HttpServletRequest req, 
			@RequestPart(value="files", required = false) List<MultipartFile> files) 
	throws Exception{
		String school = body.getSchool();		
		int grade = body.getGrade();
		int classes = body.getClasses();
		int id = body.getId();
		ClassBoard board = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		
		// 첨부파일 수정 여부
		String fileIsChanged = req.getParameter("fileIsChanged");
		if (fileIsChanged.equals("Y")) {
			// 기존에 존재하던 첨부파일 모두 삭제 (댓글 빼고 글만)
			classBoardFileRepository.deleteBoardFile(id);
			// 현재 추가하는 첨부파일 저장
			String rootPath = request.getSession().getServletContext().getRealPath("/uploads");
			String resourcesPath = rootPath.substring(0, rootPath.length()-14) + "resources\\static\\uploads";	
			rootPath = resourcesPath;
			ClassBoard sameBoard = boardService.addBoard(board, files, rootPath, 0);
			System.out.println("첨부파일 저장 됐어");
		}
		// 수정 내용 저장
		String category = body.getCategory();
		String title = body.getTitle();
		String content = body.getContent();
		board.update(category, title, content);
		// Dto로 변경
		ClassBoardDto classBoardDto = ClassBoardDto.from(board);
		System.out.println("글 수정 됐어");
		return new ResponseEntity<ClassBoardDto>(classBoardDto, HttpStatus.OK);
	}
	@GetMapping("/file")
	public ResponseEntity<Resource> getFile() throws IOException{
		System.out.println("동작1");
		// 받아올 폴더 위치
		Resource resource = resourceLoader.getResource("file:C:/Users/GWON/Desktop/test.png");
		System.out.println("동작2");
		File file = resource.getFile();
		Tika tika = new Tika();
		String mediaType = tika.detect(file);
		
		System.out.println("동작3");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"")
				.header(HttpHeaders.CONTENT_TYPE, mediaType)
				.header(HttpHeaders.CONTENT_LENGTH, file.length()+"")
				.body(resource);
	}
}

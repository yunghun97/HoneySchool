package com.ssafy.honeySchool.api.controller;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.db.entity.ClassBoard;
import com.ssafy.db.repository.ClassBoardRepository;
import com.ssafy.honeySchool.service.BoardService;
@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
		
	@Autowired
	private ClassBoardRepository classBoardRepository;
	
	// 서버 상대경로 얻을 때 사용
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
    private BoardService boardService;
	
	// 반 게시판 전체 목록
	@GetMapping("/class")
	public ResponseEntity<List<ClassBoard>> selectBoard(HttpServletRequest req) throws SQLException{
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		return new ResponseEntity<List<ClassBoard>>(classBoardRepository.findBySchoolAndGradeAndClasses(school, grade, classes),HttpStatus.OK);
	}
//	@PostMapping("/class")
//	public HttpStatus insertBoard(ClassBoard body) throws SQLException{
//		System.out.println("insert 집입");				
//		// 데이터 저장하기
//		classBoardRepository.save(ClassBoard.builder()
//				.category(body.getCategory())
//				.title(body.getTitle())
//				.content(body.getContent())
//				.writer(body.getWriter())
//				.school(body.getSchool())
//				.grade(body.getGrade())
//				.classes(body.getClasses())
//				.file_link(body.getFile_link())
//				.viewcount(0)
//				.build());				
//		return HttpStatus.OK;
//	}
	@PostMapping("/class")
	public ResponseEntity<?> createBoard(
			ClassBoard body, 
			// validation 라이브러리가 없어서 @Valid를 뺌 
//			@Valid @RequestParam("files") List<MultipartFile> files
//			@RequestParam("files") List<MultipartFile> files
			@RequestPart(value="files", required = false) List<MultipartFile> files
	) throws Exception{
		System.out.println("insert 집입");
		
		// 원래 코드에서는 FileHandler 에서 절대경로를 얻었지만, 상대경로로 바꿔야해서 controller에서 생성 -> service로 전달 -> FileHandler로 전달 -> 파일 저장
		String rootPath = request.getSession().getServletContext().getRealPath("/uploads");
		String resourcesPath = rootPath.substring(0, rootPath.length()-14) + "resources\\static\\uploads";
//		System.out.println("자른 거: " + resourcesPath + "resources\\static\\uploads");
		rootPath = resourcesPath;
		System.out.println("루트패스 수정: " + rootPath);
		
		// 데이터 저장하기
		ClassBoard board = boardService.addBoard(ClassBoard.builder()
				.category(body.getCategory())
				.title(body.getTitle())
				.content(body.getContent())
				.writer(body.getWriter())
				.school(body.getSchool())
				.grade(body.getGrade())
				.classes(body.getClasses())
				.file_link(body.getFile_link())
				.viewcount(0)
				.build(), files, rootPath);
		// BoardService에서는 전달한 게시글의 id를 못찾는다. -> id를 DB에서 생성하기 때문에, 데이터가 DB에 들어가고 난 후에만 getId가 가능하다. 그전까지는 0으로 되는듯
//		System.out.println("보드 id : " + board.getId());
		URI uriLocation = new URI("/board/" + board.getId());
        return ResponseEntity.created(uriLocation).body("{}");	
	}
	// Jpa로 category 구분해서 가져오기
	@GetMapping("/class/category")
	public ResponseEntity<List<ClassBoard>> selectCategory(HttpServletRequest req) {
		String school = req.getParameter("school");		
		String category = req.getParameter("category");
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));	
		
		return new ResponseEntity<List<ClassBoard>>(classBoardRepository.findBySchoolAndGradeAndClassesAndCategory(school, grade, classes, category),HttpStatus.OK);
	}	
	// 전체게시판 글 상세
	@Transactional
	@GetMapping("/class/detail")
	public ResponseEntity<ClassBoard> detailBoard(HttpServletRequest req) {
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		int id = Integer.parseInt(req.getParameter("id"));
		classBoardRepository.updateView(id);
		ClassBoard detail = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		return new ResponseEntity<ClassBoard>(detail, HttpStatus.OK);
	}
	// 전체게시판 글 삭제
	@DeleteMapping("/class")
	public HttpStatus deleteBoard(HttpServletRequest req) {
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		int id = Integer.parseInt(req.getParameter("id"));
		ClassBoard board = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		classBoardRepository.delete(board);
		return HttpStatus.OK;
	}
	// 전체게시판 글 수정
	// API 명세서랑 다르게 일단 entity 자체를 리턴했습니다
	@Transactional
	@PatchMapping("/class")
	public ResponseEntity<ClassBoard> updateBoard(ClassBoard body) throws SQLException{
		String school = body.getSchool();		
		int grade = body.getGrade();
		int classes = body.getClasses();
		int id = body.getId();
		ClassBoard board = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		String category = body.getCategory();
		String title = body.getTitle();
		String content = body.getContent();
		String file_link = body.getFile_link();
		board.update(category, title, content, file_link);
		return new ResponseEntity<ClassBoard>(board, HttpStatus.OK);
	}
	// 로컬 폴더에 파일 업로드 - 잘 작동
	@PostMapping("/upload")
    public ResponseEntity upload(@RequestPart MultipartFile file) {
//		String rootPath = request.getSession().getServletContext().getRealPath("/resources/uploadFiles/");
//		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String rootPath = request.getServletContext().getRealPath("/");
		System.out.println(rootPath);
//		String savePath = rootPath + file.getOriginalFilename();
//		System.out.println(savePath);
//		String originalFileName = file.getOriginalFilename();
        File uploadFile = new File(rootPath + file.getOriginalFilename());
//        try {
//            file.transferTo(destination);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
//        }
        return ResponseEntity.status(HttpStatus.CREATED).body(file.getOriginalFilename());
    }
}

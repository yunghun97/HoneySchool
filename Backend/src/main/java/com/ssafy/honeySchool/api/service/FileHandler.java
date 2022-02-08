package com.ssafy.honeySchool.api.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.honeySchool.db.entity.ClassBoardFile;
import com.ssafy.honeySchool.db.entity.DeleteYn;

@Component
public class FileHandler {
	
	@Autowired
	private HttpServletRequest request;
	
    public List<ClassBoardFile> parseFileInfo(
    		int boardID,
            List<MultipartFile> multipartFiles,
            String rootPath
    ) throws Exception{
    	
    	// 반환을 할 파일 리스트
    	List<ClassBoardFile> fileList = new ArrayList<>();
    	
    	// 밑에 코드 대신 씀
    	if (multipartFiles == null) {
    		System.out.println("파일이 널이야");
    		return fileList;
    	}
    	
        // 파일이 빈 것이 들어오면 빈 것을 반환
        // if 조건문에서 오류뜬다. null한테 isEmpty()하면 오류뜨나보다 (java.lang.NullPointerException: null)
    	// 오류나는데 굳이 놔둘 필요 없을 것 같아서 주석 처리
//        if(multipartFiles.isEmpty()){
//        	System.out.println("파일이 널이야");
//            return fileList;
//        }

        // 파일 이름을 업로드 한 날짜로 바꾸어서 저장할 것이다
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());
//        // 반환을 할 파일 리스트
//        List<BoardPicture> fileList = new ArrayList<>();

        // 파일이 빈 것이 들어오면 빈 것을 반환
        if(multipartFiles.isEmpty()){
            return fileList;
        }

//        // 파일 이름을 업로드 한 날짜로 바꾸어서 저장할 것이다
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        String current_date = simpleDateFormat.format(new Date());

//        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
//        String absolutePath = new File("").getAbsolutePath() + "\\";
        
//        // 위에 절대경로 대신 상대경로로 설정한다
//        System.out.println(request);
//        String absolutePath =  request.getSession().getServletContext().getRealPath("/");
        
        // 경로를 지정하고 그곳에다가 저장할 심산이다
        // images 폴더 없으면 추가
        String imagePath = "images/" + current_date;
        File file = new File(rootPath + "/" + imagePath);  // 날짜 폴더가 존재하지 않을 때 만들어주기 위해서 rootPath 추가
        // 저장할 위치의 디렉토리가 존재하지 않을 경우
        if(!file.exists()){
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }
        // audios 폴더 없으면 추가 (귀찮아서 코드 복붙, 시간 많으면 중복 안나게 수정)
        String audioPath = "audios/" + current_date;
        File file2 = new File(rootPath + "/" + audioPath);  // 날짜 폴더가 존재하지 않을 때 만들어주기 위해서 rootPath 추가
        // 저장할 위치의 디렉토리가 존재하지 않을 경우
        if(!file2.exists()){
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file2.mkdirs();
        }

        // 파일들을 이제 만져볼 것이다
        for (MultipartFile multipartFile : multipartFiles){
            // 파일이 비어 있지 않을 때 작업을 시작해야 오류가 나지 않는다
            if(!multipartFile.isEmpty()){
                // jpeg, png, gif 파일들만 받아서 처리할 예정
                String contentType = multipartFile.getContentType();
                System.out.println(contentType);
                String originalFileExtension;
                String FileExtensionCategory;
                    // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
                if (ObjectUtils.isEmpty(contentType)){
                    break;
                }
                // 추가하지 않은 확장자를 파일로 넣으면 오류 뜨지 않고 글만 저장된다.
                else{
                    if(contentType.contains("image/jpeg")){
                        originalFileExtension = ".jpg";
                        FileExtensionCategory = "images";
                    }
                    else if(contentType.contains("image/png")){
                        originalFileExtension = ".png";
                        FileExtensionCategory = "images";
                    }
                    else if(contentType.contains("image/gif")){
                        originalFileExtension = ".gif";
                        FileExtensionCategory = "images";
                    }
                    else if(contentType.contains("audio/mp4")){
                    	originalFileExtension = ".mp4";
                    	FileExtensionCategory = "audios";
                    }
                    else if(contentType.contains("audio/mpeg")){
                    	originalFileExtension = ".mpeg";
                    	FileExtensionCategory = "audios";
                    }
                    else if(contentType.contains("audio/ogg")){
                    	originalFileExtension = ".ogg";
                    	FileExtensionCategory = "audios";
                    }
                    // 다른 파일 명이면 아무 일 하지 않는다
                    else{
                        break;
                    }
                }
                // 카테고리 별로 저장할 폴더 정하기
                String path = "";
                if (FileExtensionCategory == "images") {
                	path = imagePath;
                } else if (FileExtensionCategory == "audios") {
                	path = audioPath;
                }
                // 각 이름은 겹치면 안되므로 나노 초까지 동원하여 지정
                String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;
                // 생성 후 리스트에 추가
                ClassBoardFile classBoardFile = ClassBoardFile.builder()
                        .boardId(boardID)
                        .original_file_name(multipartFile.getOriginalFilename())
                        .stored_file_path(path + "/" + new_file_name)
                        .file_size(multipartFile.getSize())
                        .isDeleted(DeleteYn.N)
                        .build();
                fileList.add(classBoardFile);
                
                // 받아온 상대경로를 절대경로로 변경
                String fullPath = rootPath + "/" + path + "/" + new_file_name;
                Path resultPath = Paths.get(fullPath).toAbsolutePath();
                
                // 저장된 파일로 변경하여 이를 보여주기 위함
//                file = new File(rootPath + "/" + path + "/" + new_file_name);
//                System.out.println(rootPath + "/" + path + "/" + new_file_name);
                file = new File(resultPath.toString());
                System.out.println(resultPath);
                multipartFile.transferTo(file);
            }
        }

        return fileList;
    }
}
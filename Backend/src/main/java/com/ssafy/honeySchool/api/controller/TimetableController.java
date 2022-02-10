package com.ssafy.honeySchool.api.controller;

import java.io.BufferedReader;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.Timetable;
import com.ssafy.honeySchool.db.repository.TimetableRepository;

@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/v1/timetable")
public class TimetableController {
	
	@Autowired
	private TimetableRepository timetableRepository;
	
	@Autowired
	private HttpServletRequest request;
	
	// 오늘 시간표 보기
	@GetMapping("/today")
	public ResponseEntity<List<Timetable>> todaysTimetable(HttpServletRequest req) throws Exception{
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		// string -> date 변환
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = dateFormat.parse(req.getParameter("date") + " 00:00:00");
		// Date 1일 더하기
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(startDate);
		cal.add(Calendar.DATE, 1); //1일 더하기
		Date endDate = cal.getTime();
		// startDate-endDate 사이의 1일동안 시간표 불러오기
		List<Timetable> todaysTimetables = 
				timetableRepository.findAllBySchoolAndGradeAndClassesAndStartTimeBetweenOrderByStartTimeAsc(school, grade, classes, startDate, endDate);
		
//		// 테스트
//		System.out.println("date 타입은? : " + date.getClass().getSimpleName());
//		System.out.println("startdate? : " + startDate);
//		System.out.println("enddate? : " + endDate);
		
		return new ResponseEntity<List<Timetable>>(todaysTimetables, HttpStatus.OK);
	}
	// 일주일 시간표 보기
	@GetMapping("/week")
	public ResponseEntity<List<Timetable>> thisWeeksTimetable(HttpServletRequest req) throws Exception{
		String school = req.getParameter("school");
		System.out.println("req : " + school);
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		// string -> date 변환
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Date startDate = dateFormat.parse(req.getParameter("date") + " 00:00:00");
		// Date 5일 더하기 (평일만 수업)
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(startDate);
		cal.add(Calendar.DATE, 5); //1일 더하기
		Date endDate = cal.getTime();
		// startDate-endDate 사이의 5일동안 시간표 불러오기
		List<Timetable> thisWeeksTimetables = 
				timetableRepository.findAllBySchoolAndGradeAndClassesAndStartTimeBetweenOrderByStartTimeAsc(school, grade, classes, startDate, endDate);
		
//		// 테스트
//		System.out.println("date 타입은? : " + startDate.getClass().getSimpleName());
//		System.out.println("startdate? : " + startDate);
//		System.out.println("enddate? : " + endDate);
		
		return new ResponseEntity<List<Timetable>>(thisWeeksTimetables, HttpStatus.OK);
	}
	// 일주일 시간표 만들기
	@PostMapping("/week")
	public HttpStatus createTimetable(HttpServletRequest req) throws Exception{
		
		// json -> string
		StringBuffer stringBuffer = new StringBuffer();
	    String line = null;
	 
	    try {
	        BufferedReader reader = req.getReader();
	        while((line = reader.readLine()) != null) {
	        	stringBuffer.append(line);
	        }
	    }catch(Exception e) {
	        System.out.println("Error reading JSON string: " + e.toString());
	    }
	    String json = stringBuffer.toString();
	    
	    //JSON데이터를 넣어 JSON Object 로 만들어 준다.
	    JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        
        // 공통되는 school, grade, classes를 빼온다.
        String school = jsonObject.get("school").toString();
        int grade = Integer.parseInt(jsonObject.get("grade").toString());
        int classes = Integer.parseInt(jsonObject.get("classes").toString());
        // 연도와 연도의 몇주차인지 불러온다
        int year = Integer.parseInt(jsonObject.get("year").toString());
        int week = Integer.parseInt(jsonObject.get("week").toString());
        // 연도의 ?주차의 일요일을 찾기
        Calendar c = Calendar.getInstance();
        c.set(year, 0, 1); //연도 설정   
        c.add(Calendar.WEEK_OF_MONTH, week); //추가하려는 주
//        c.set(Calendar.DAY_OF_WEEK,c.getActualMinimum(Calendar.DAY_OF_WEEK)); //해당 주의 첫번째 날로 이동
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //해당 주의 월요일로 이동
        Date sunday = c.getTime();
        System.out.println("2022년 1주차의 첫번째 날 : " + sunday);
        
       
        // 1(월)부터 5(금)까지 순회하여 수업의 수만큼 insert한다.
        for(int i = 1; i < 6; i++) {
        	
        	// i가 1일 때 월, 5일 때 금
        	c.add(Calendar.DATE, 1); //1일 더하기

        	// 요일 만들기
    		String day = "";
    		switch (i) {
              case 1: i = 1;
              		day = "월";
                    break;
              case 2: i = 2;
	                day = "화";
                    break;
              case 3: i = 3;
                    day = "수";
                    break;
              case 4: i = 4;
              		day = "목";
                    break;
              case 5: i = 5;
              		day = "금";
                    break;
            default:
                  break;
            }
        	
        	String dayInt = Integer.toString(i);
        	//1의 배열을 추출
        	JSONArray dayArray = (JSONArray) jsonObject.get(dayInt);
        	for(int j = 0; j < dayArray.size(); j++) {
        		
        		//배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
        		JSONObject dayObject = (JSONObject) dayArray.get(j);
//        		System.out.println(day + " - subject : "+ dayObject.get("subject"));
//        		System.out.println(day + " - start_time : "+ dayObject.get("start_time"));
//        		System.out.println(day + " - end_time : "+ dayObject.get("end_time"));
        		String subject = dayObject.get("subject").toString();
        		String start_time = dayObject.get("start_time").toString();
        		String end_time = dayObject.get("end_time").toString();
        		
        		// startTime Date 객체 생성
        		c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(start_time.substring(0, 2)));
        		c.set(Calendar.MINUTE, Integer.parseInt(start_time.substring(3, 5)));
        		c.set(Calendar.SECOND, 0);
        		Date startTimeDate = c.getTime();
        		System.out.println("첫번째 날짜 : " + startTimeDate);
        		// endTime Date 객체 생성
        		c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(end_time.substring(0, 2)));
        		c.set(Calendar.MINUTE, Integer.parseInt(end_time.substring(3, 5)));
        		c.set(Calendar.SECOND, 0);
        		Date endTimeDate = c.getTime();
        		System.out.println("첫번째 날짜 (end): " + endTimeDate);
        		     		
        		// 저장
        		Timetable timetable = timetableRepository.save(Timetable.builder()
        				.subject(subject)
        				.startTime(startTimeDate)
        				.endTime(endTimeDate)
        				.day(day)
        				.school(school)
        				.grade(grade)
        				.classes(classes)
        				.build());
        	}
        }
	    
		return HttpStatus.OK;
	}
	// 일주일 시간표 수정 - 기존 수업들 모두 delete하고 새로 생성
	@Transactional
	@PutMapping("/week")
	public HttpStatus updateTimetable(HttpServletRequest req) throws Exception{
		
		// json -> string
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		
		try {
			BufferedReader reader = req.getReader();
			while((line = reader.readLine()) != null) {
				stringBuffer.append(line);
			}
		}catch(Exception e) {
			System.out.println("Error reading JSON string: " + e.toString());
		}
		String json = stringBuffer.toString();
		
		//JSON데이터를 넣어 JSON Object 로 만들어 준다.
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
		
		// 공통되는 school, grade, classes를 빼온다.
		String school = jsonObject.get("school").toString();
		int grade = Integer.parseInt(jsonObject.get("grade").toString());
		int classes = Integer.parseInt(jsonObject.get("classes").toString());
		// 연도와 연도의 몇주차인지 불러온다
		int year = Integer.parseInt(jsonObject.get("year").toString());
		int week = Integer.parseInt(jsonObject.get("week").toString());
		// 연도의 ?주차의 일요일을 찾기
		Calendar c = Calendar.getInstance();
		c.set(year, 0, 1); //연도 설정   
		c.add(Calendar.WEEK_OF_MONTH, week); //추가하려는 주
//        c.set(Calendar.DAY_OF_WEEK,c.getActualMinimum(Calendar.DAY_OF_WEEK)); //해당 주의 첫번째 날로 이동
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //해당 주의 월요일로 이동
		// 시,분,초 00:00:00으로 설정
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date sunday = c.getTime();
		System.out.println("2022년 1주차의 첫번째 날 : " + sunday);
		
		
		// 해당 주차의 기존 수업들을 delete (일요일부터 토요일 00:00:00시 까지)
		Calendar c2 = (Calendar) c.clone();
		c2.add(Calendar.DATE, 6);
		Date saturday = c2.getTime();
		System.out.println("일요일 : " + sunday + " / " + "금요일 : " + saturday);
		timetableRepository.deleteAllBySchoolAndGradeAndClassesAndStartTimeBetween(school, grade, classes, sunday, saturday);
		
		
		// 여기부터 create와 동일
		// 1(월)부터 5(금)까지 순회하여 수업의 수만큼 insert한다.
		for(int i = 1; i < 6; i++) {
			
			// i가 1일 때 월, 5일 때 금
			c.add(Calendar.DATE, 1); //1일 더하기
			
			// 요일 만들기
			String day = "";
			switch (i) {
			case 1: i = 1;
			day = "월";
			break;
			case 2: i = 2;
			day = "화";
			break;
			case 3: i = 3;
			day = "수";
			break;
			case 4: i = 4;
			day = "목";
			break;
			case 5: i = 5;
			day = "금";
			break;
			default:
				break;
			}
			
			String dayInt = Integer.toString(i);
			//1의 배열을 추출
			JSONArray dayArray = (JSONArray) jsonObject.get(dayInt);
			for(int j = 0; j < dayArray.size(); j++) {
				
				//배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
				JSONObject dayObject = (JSONObject) dayArray.get(j);
//        		System.out.println(day + " - subject : "+ dayObject.get("subject"));
//        		System.out.println(day + " - start_time : "+ dayObject.get("start_time"));
//        		System.out.println(day + " - end_time : "+ dayObject.get("end_time"));
				String subject = dayObject.get("subject").toString();
				String start_time = dayObject.get("start_time").toString();
				String end_time = dayObject.get("end_time").toString();
				
				// startTime Date 객체 생성
				c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(start_time.substring(0, 2)));
				c.set(Calendar.MINUTE, Integer.parseInt(start_time.substring(3, 5)));
				c.set(Calendar.SECOND, 0);
				Date startTimeDate = c.getTime();
				System.out.println("첫번째 날짜 : " + startTimeDate);
				// endTime Date 객체 생성
				c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(end_time.substring(0, 2)));
				c.set(Calendar.MINUTE, Integer.parseInt(end_time.substring(3, 5)));
				c.set(Calendar.SECOND, 0);
				Date endTimeDate = c.getTime();
				System.out.println("첫번째 날짜 (end): " + endTimeDate);
				
				// 저장
				Timetable timetable = timetableRepository.save(Timetable.builder()
						.subject(subject)
						.startTime(startTimeDate)
						.endTime(endTimeDate)
						.day(day)
						.school(school)
						.grade(grade)
						.classes(classes)
						.build());
			}
		}
		
		return HttpStatus.OK;
	}
}

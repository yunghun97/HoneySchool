package com.ssafy.honeySchool.db.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ssafy.honeySchool.db.entity.NoticeFile;

public interface NoticeFileRepository extends CrudRepository<NoticeFile, Integer>{
	
	// 공지사항 삭제할 때 첨부파일 함께 삭제 (deleteYn 컬럼을 Y로 바꾼다. 반대는 불가능)
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE notice_file m set m.delete_yn = 'Y' where m.notice_id = :notice_id and m.delete_yn != 'Y'", nativeQuery = true)
    void deleteFile(@Param("notice_id")int notice_id);
}

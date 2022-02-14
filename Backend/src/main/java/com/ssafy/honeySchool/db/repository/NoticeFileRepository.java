package com.ssafy.honeySchool.db.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.honeySchool.db.entity.DeleteYn;
import com.ssafy.honeySchool.db.entity.NoticeFile;

@Repository
public interface NoticeFileRepository extends CrudRepository<NoticeFile, Integer>{
	
	// 공지사항 상세
	List<NoticeFile> findAllByNoticeIdAndIsDeleted(int notice_id, DeleteYn isDeleted); 
	
	// 공지사항 삭제할 때 첨부파일 함께 삭제 (deleteYn 컬럼을 Y로 바꾼다. 반대는 불가능)
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE notice_file m set m.delete_yn = 'Y' where m.notice_id = :notice_id and m.delete_yn != 'Y'", nativeQuery = true)
    void deleteFile(@Param("notice_id")int notice_id);

}

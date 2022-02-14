package com.ssafy.honeySchool.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.honeySchool.api.dto.CommentDto;

@Service
public class CommentService {
	
	// findCommentByClassBoardOrderByParentIdAsc 로 정렬한 리스트가 들어와야한다. 아니면 결과 부정확
	public List<CommentDto> sortCommentDtos(List<CommentDto> commentDtos) {
		
		// 댓글, 대댓글 정렬
		List<CommentDto> cmtDtos = new ArrayList<CommentDto>();
		for (CommentDto cmt : commentDtos) {  // 댓글만 리스트에 넣기
			if (cmt.getParent_id() != 0) {
				break;
			} else {
				cmtDtos.add(cmt);
			}
		}
		List<CommentDto> results = new ArrayList<CommentDto>();
		for (CommentDto cmt : cmtDtos) {
			results.add(cmt);
			int id = cmt.getId();
			for (CommentDto rpy : commentDtos) {
				if (rpy.getParent_id() == id) {
					results.add(rpy);
				} else if (rpy.getParent_id() > id) {
					break;
				}
			}
		}
		return results;
	}
	
}

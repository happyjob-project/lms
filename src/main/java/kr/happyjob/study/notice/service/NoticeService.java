package kr.happyjob.study.notice.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.happyjob.study.login.model.UserInfo;
import kr.happyjob.study.notice.model.Noticevo;

public interface NoticeService {

	/** 목록 조회 */
	public List<Noticevo> noticelist(Map<String, Object> paramMap) throws Exception;
	
	/** 카운트 조회 */
	public int noticecnt(Map<String, Object> paramMap) throws Exception;
	
	/** 등록 */
	public int noticesave(Map<String, Object> paramMap) throws Exception;
	
	/** 수정 */
	public int noticeupdate(Map<String, Object> paramMap) throws Exception;	
	
	/** 상세 조회 */
	public Noticevo noticedtl(Map<String, Object> paramMap) throws Exception;
	
	/** 조회수 수정 */
	public int noticeviewsupdate(Map<String, Object> paramMap) throws Exception;	
	
	/** 삭제 */
	public int noticedelete(Map<String, Object> paramMap) throws Exception;
	
	/** 등록 파일 */
	public int noticeinsertfile(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	/** 수정 파일*/
	public int noticeupdatefile(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	/** 삭제 */
	public int noticedeletefile(Map<String, Object> paramMap) throws Exception;

}

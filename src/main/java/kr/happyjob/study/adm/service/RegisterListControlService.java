package kr.happyjob.study.adm.service;

import java.util.List;
import java.util.Map;

import kr.happyjob.study.adm.model.ClassroomVO;
import kr.happyjob.study.adm.model.RegisterListControlVO;

public interface RegisterListControlService {

	/** 목록 조회 */
	public List<RegisterListControlVO> registerListControlPlan(Map<String, Object> paramMap) throws Exception;
	
	/** 유저정보 단건조회 조회 */
	public RegisterListControlVO lecUserinfo(Map<String, Object> paramMap) throws Exception;
	
	/** 목록 조회 */
	public List<RegisterListControlVO> registerListControlList(Map<String, Object> paramMap) throws Exception;
	
	/** 카운트 조회 */
	public int registerListControlCnt(Map<String, Object> paramMap) throws Exception;
	
	/** 상세 조회 */
	public RegisterListControlVO registerListControlDetail(Map<String, Object> paramMap) throws Exception;

	/** 등록 */
	public int registerListControlSave(Map<String, Object> paramMap) throws Exception;
	
	/** 수정 */
	public int registerListControlUpdate(Map<String, Object> paramMap) throws Exception;	
	
	/** 삭제 */
	public int registerListControlDelete(Map<String, Object> paramMap) throws Exception;
		
	/** 강의실정보 **/
	public List<ClassroomVO> classroomList(Map<String, Object> paramMap) throws Exception;
}
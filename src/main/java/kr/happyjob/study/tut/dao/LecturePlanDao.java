package kr.happyjob.study.tut.dao;

import java.util.List;
import java.util.Map;

import kr.happyjob.study.adm.model.ClassroomVO;
import kr.happyjob.study.tut.model.LecturePlanVO;

public interface LecturePlanDao {

	/** 목록 조회 */
	public List<LecturePlanVO> lecturePlan(Map<String, Object> paramMap);
	
	/** 유저정보 단건조회 조회 */
	public LecturePlanVO lecUserinfo(Map<String, Object> paramMap) throws Exception;
	
	/** 목록 조회 */
	public List<LecturePlanVO> lecturePlanList(Map<String, Object> paramMap);
 
	/** 카운트 조회 */
	public int lecturePlanCnt(Map<String, Object> paramMap) throws Exception;
	
	/** 상세 조회 */
	public LecturePlanVO lecturePlanDetail(Map<String, Object> paramMap) throws Exception;
	
	/** 등록 */
	public int lecturePlanSave(Map<String, Object> paramMap) throws Exception;
		
	/** 수정 */
	public int lecturePlanUpdate(Map<String, Object> paramMap) throws Exception;	
	
	/** 삭제 */
	public int lecturePlanDelete(Map<String, Object> paramMap) throws Exception;	
	
	/** 주간계획 목록확인 **/
	public List<LecturePlanVO> weekList(Map<String,Object> paramMap) throws Exception;
	
	/** 주간계획 여부확인 **/
	public int checkWeek(Map<String,Object> paramMap) throws Exception;
	
	/** 주간계획 등록 **/
	public int weekRegister(Map<String,Object> paramMap) throws Exception;
	
	/** 주간계획 수정 **/
	public int weekUpdate(Map<String,Object> paramMap) throws Exception;
	
	/** 주간계획 삭제 **/
	public int weekDelete(Map<String,Object> paramMap) throws Exception;

}
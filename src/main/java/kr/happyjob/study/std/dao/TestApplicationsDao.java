package kr.happyjob.study.std.dao;

import java.util.List;
import java.util.Map;

import kr.happyjob.study.std.model.LecListVO;
import kr.happyjob.study.std.model.SurveyQueVO;
import kr.happyjob.study.std.model.TestApplicationsQueVO;
import kr.happyjob.study.std.model.TestApplicationsVO;




public interface TestApplicationsDao {
	
	/** 목록 조회 */
	public List<TestApplicationsVO> testList(Map<String, Object> paramMap) throws Exception;
	
	/** 리스트 갯수 조회 */
	public int testListCnt(Map<String, Object> paramMap) throws Exception;
	
	/** 단건 목록 상세 조회 */
	public LecListVO lecListDtl(Map<String, Object> paramMap) throws Exception;
	
	/** 학생 설문 리뷰 등록*/
	public int surveyAnswerInsert(Map<String, Object> paramMap) throws Exception;
	
	/** 설문 질문 단건 조회 */
	public SurveyQueVO selectOneSurveyQuestion(Map<String, Object> paramMap) throws Exception;
	
	/** 설문 답변 단건 조회 */
	public SurveyQueVO selectOneSurveyAnswer(Map<String, Object> paramMap) throws Exception;

	/** 시험 문제 응시 조회 */
	public List<TestApplicationsQueVO> testQueList(Map<String, Object> paramMap) throws Exception;
	
	/** 테스트 상세 카운트 */
	public int testDetailCnt(Map<String, Object> paramMap) throws Exception;
	
	/** 테스트 응시 */
	public int testAnswerUpdate(Map<String, Object> paramMap) throws Exception;
	
	
}

package kr.happyjob.study.adm.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.happyjob.study.adm.dao.RegisterListControlDao;
import kr.happyjob.study.adm.model.ClassroomVO;
import kr.happyjob.study.adm.model.RegisterListControlVO;

@Service
public class RegisterListControlServiceImpl implements RegisterListControlService {

   // Set logger
   private final Logger logger = LogManager.getLogger(this.getClass());
   
   // Get class name for logger
   private final String className = this.getClass().toString();
   
   @Autowired
   RegisterListControlDao registerListControlDao;
   	
   /** 목록 조회 */
	public List<RegisterListControlVO> registerListControlPlan(Map<String, Object> paramMap) throws Exception {
		
		List<RegisterListControlVO> listData = registerListControlDao.registerListControlPlan(paramMap);
		
		return listData;
	}
   
	/** 유저정보 단건조회 조회 */
	public RegisterListControlVO lecUserinfo(Map<String, Object> paramMap) throws Exception {
		
		RegisterListControlVO listUserInfo = registerListControlDao.lecUserinfo(paramMap);
		
		return listUserInfo;
	}
	
   /** 목록 조회 */
	public List<RegisterListControlVO> registerListControlList(Map<String, Object> paramMap) throws Exception {
		
		List<RegisterListControlVO> listData = registerListControlDao.registerListControlList(paramMap);
		
		return listData;
	}
	
	/** 카운트 조회 */
	public int registerListControlCnt(Map<String, Object> paramMap) throws Exception {
		
		int totalCount = registerListControlDao.registerListControlCnt(paramMap);
		
		return totalCount;
	}
	
	/** 상세 조회 */
	public RegisterListControlVO registerListControlDetail(Map<String, Object> paramMap) throws Exception {
		return registerListControlDao.registerListControlDetail(paramMap);
	}
	
	/** 등록 */
	public int registerListControlSave(Map<String, Object> paramMap) throws Exception {		
		return registerListControlDao.registerListControlSave(paramMap);
	}
	
	/** 수정 */
	public int registerListControlUpdate(Map<String, Object> paramMap) throws Exception {
		return registerListControlDao.registerListControlUpdate(paramMap);
	}
	
	/** 삭제 */
	public int registerListControlDelete(Map<String, Object> paramMap) throws Exception {
		return registerListControlDao.registerListControlDelete(paramMap);
	}
	
	/** 강의실정보 **/
	public List<ClassroomVO> classroomList(Map<String, Object> paramMap) throws Exception {
		
		List<ClassroomVO> classroomList = registerListControlDao.classroomList(paramMap);
		
		return classroomList;
	}
		
}
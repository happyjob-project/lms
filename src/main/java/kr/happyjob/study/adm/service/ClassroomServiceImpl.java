package kr.happyjob.study.adm.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.happyjob.study.adm.dao.ClassroomDao;
import kr.happyjob.study.adm.model.ClassroomVO;
import kr.happyjob.study.notice.model.Noticevo;
import kr.happyjob.study.system.model.ComnDtlCodModel;
import kr.happyjob.study.tut.dao.LecturePlanDao;
import kr.happyjob.study.tut.model.LecturePlanVO;

@Service
public class ClassroomServiceImpl implements ClassroomService {

	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());

	// Get class name for logger
	private final String className = this.getClass().toString();

	@Autowired
	ClassroomDao classroomDao;

	/** 목록 조회 */
	@Override
	public List<ClassroomVO> classroomList(Map<String, Object> paramMap) throws Exception {
		List<ClassroomVO> classroomData = classroomDao.classroomList(paramMap);
		return classroomData;
	}

	@Override
	public int classroomCnt(Map<String, Object> paramMap) throws Exception {
		int totalCount = classroomDao.classroomCnt(paramMap);
		return totalCount;
	}

	@Override
	public int classroomSave(Map<String, Object> paramMap) {
		return classroomDao.classroomSave(paramMap);
	}

	@Override
	public int classroomUpdate(Map<String, Object> paramMap) {
		return classroomDao.classroomUpdate(paramMap);
	}

	@Override
	public int classroomDelete(Map<String, Object> paramMap) {

		return classroomDao.classroomDelete(paramMap);
	}

	@Override
	public ClassroomVO classroomdtl(Map<String, Object> paramMap) {
		return classroomDao.classroomdtl(paramMap);
	}

	@Override
	public int lectureClassroomNoNull(Map<String, Object> paramMap) {
		return classroomDao.lectureClassroomNoNull(paramMap);
	}

	@Override
	public List<ClassroomVO> listClassroomEquipment(Map<String, Object> paramMap) {
		List<ClassroomVO> classroomData = classroomDao.classroomEquipList(paramMap);

		return classroomData;
	}

	@Override
	public int equipmentCnt(Map<String, Object> paramMap) {
		int totalCount = classroomDao.equipmentCnt(paramMap);
		return totalCount;
	}

}
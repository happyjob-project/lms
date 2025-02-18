package kr.happyjob.study.tut.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.happyjob.study.common.comnUtils.FileUtilCho;
import kr.happyjob.study.tut.dao.ReportControlDao;
import kr.happyjob.study.tut.model.ReportControlVO;


@Service
public class ReportControlServiceImpl implements ReportControlService{
	
	// 파일 업로드 위한 어노테이션 
	// Root path for file upload 
	@Value("${fileUpload.rootPath}")
	private String rootPath;
	
	@Value("${fileUpload.tutPath}")
	private String itemPath;
	
	@Value("${fileUpload.virtualRootPath}")
	private String virtualRootPath;	
	
	@Autowired
	private ReportControlDao reportControlDao;
	
	// 강의 리스트
	@Override
	public List<ReportControlVO> showLectureList(Map<String, Object> paramMap) throws Exception {
		return reportControlDao.showLectureList(paramMap);
	}
	// 강의 리스트 카운트
	@Override
	public int lecListcnt(Map<String, Object> paramMap) throws Exception {
		int totalLecccnt = reportControlDao.lecListcnt(paramMap);
		return totalLecccnt;
	}
	//과제 리스트
	@Override
	public List<ReportControlVO> showProjectInfo(Map<String, Object> paramMap) throws Exception {
		return reportControlDao.showProjectInfo(paramMap);
	}
	// 과제 리스트 카운트
	@Override
	public int reportLisCnt(Map<String, Object> paramMap) throws Exception {
		int totalLecccnt2 = reportControlDao.reportLisCnt(paramMap);
		return totalLecccnt2;
	}
	// 과제 생성
	@Override
	public int makeProject(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
		
		int ret = 0;
		
		String lec_no_str = (String) paramMap.get("cccc");
		if (lec_no_str != null && !lec_no_str.isEmpty()) {
		    int lec_no = Integer.parseInt(lec_no_str);
		    paramMap.put("lec_no", lec_no);
		} else {
		    // 예외 처리 또는 기본값 설정 등 필요한 작업 수행
		    // 여기서는 간단히 0 또는 다른 기본값으로 설정
		    paramMap.put("lec_no", 0); // 또는 다른 기본값 설정
		}
//		String lec_no = (String) paramMap.get("cccc");
//		paramMap.put("lec_no", Integer.parseInt(lec_no));
//		int lec_no= (int) paramMap.get("cccc");
		
		String itemFilePath = File.separator + "project" + File.separator + lec_no_str + File.separator + File.separator;
		
		FileUtilCho fileUtilCho = new FileUtilCho(mpRequest, rootPath, virtualRootPath, itemFilePath);
		Map<String, Object> fileinfo = fileUtilCho.uploadFiles();
		
		paramMap.put("task_filename", fileinfo.get("file_nm"));
		paramMap.put("task_path", fileinfo.get("file_loc"));
		paramMap.put("task_filesize", fileinfo.get("file_size"));
		paramMap.put("task_relative_path", fileinfo.get("vrfile_loc"));
		
		try{
			ret = reportControlDao.makeProject(paramMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}

	
	@Override
	public List<ReportControlVO> showLectureInfo(Map<String, Object> paramMap) throws Exception {
		return reportControlDao.showLectureInfo(paramMap);
	}


	@Override
	public List<ReportControlVO> showSubmitInfo(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return reportControlDao.showSubmitInfo(paramMap);
	}

	@Override
	public ReportControlVO showStudentCon(Map<String, Object> paramMap) throws Exception {	
		
		return reportControlDao.showStudentCon(paramMap);
	}

	@Override
	public ReportControlVO showProjectInfo2(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return reportControlDao.showProjectInfo2(paramMap);
	}
	

	/* 과제 수정 */
	
	@Override
	public int updateProject(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
		int ret = 0;
		
		String lec_no = (String) paramMap.get("lec_no");
		paramMap.put("lec_no", Integer.parseInt(lec_no));
		
		String task_id = (String) paramMap.get("task_id");
		paramMap.put("task_id", Integer.parseInt(task_id));
		
		String itemFilePath = "project" + File.separator + File.separator + lec_no + File.separator + File.separator;
		
		FileUtilCho fileUtilCho = new FileUtilCho(mpRequest, rootPath, itemFilePath, itemFilePath);
		Map<String, Object> listFileUtilModel = fileUtilCho.uploadFiles();
		
		paramMap.put("task_filename", listFileUtilModel.get("file_nm"));
		paramMap.put("task_path", listFileUtilModel.get("file_loc"));
		paramMap.put("task_filesize", listFileUtilModel.get("file_size"));
		
		System.out.println("업로드한 파일 이름 : "+paramMap.get("task_filename"));
		System.out.println("업로드한 파일 url : "+paramMap.get("task_path"));
		System.out.println("업로드한 파일 사이즈 : "+paramMap.get("task_filesize"));
		
		try{
			ret = reportControlDao.updateProject(paramMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}

}

package kr.happyjob.study.std.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.happyjob.study.std.dao.SubmitReportDao;
import kr.happyjob.study.std.model.SubmitReportVO;
import kr.happyjob.study.common.comnUtils.FileUtil;
import kr.happyjob.study.common.comnUtils.FileUtilCho;
import kr.happyjob.study.common.comnUtils.FileUtilModel;


@Service
public class SubmitReportServiceImpl implements SubmitReportService{
	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());
	// Get class name for logger
	private final String className = this.getClass().toString();

	@Autowired
	SubmitReportDao submitReportDao;
	
	// Root path for file upload 
	@Value("${fileUpload.rootPath}")
	private String rootPath;
	
	// comment path for file upload
	@Value("${fileUpload.stdPath}")
	private String subPath;
	
	@Value("${fileUpload.tutPath}")
	private String itemPath;
	
	@Value("${fileUpload.virtualRootPath}")
	private String virtualRootPath;	
	
	
	/**과제 목록 조회*/
	@Override
	public List<SubmitReportVO> selReportList(Map<String, Object> paramMap) throws Exception {
		List<SubmitReportVO> reportlist = submitReportDao.selReportList(paramMap);
		return reportlist;
	}
	
	/**과제 카운트 조회*/
	@Override
	public int countReportList(Map<String, Object> paramMap) throws Exception {
		int totalCount = submitReportDao.countReportList(paramMap);
		return totalCount;
	}

	/** 과제리스트 아이디로 상세 조회 (모달)*/
	@Override
	public SubmitReportVO choiceReportList(Map<String, Object> paramMap) throws Exception {
		SubmitReportVO choiceReportList = submitReportDao.choiceReportList(paramMap);
		return choiceReportList;
	}

	/** 과제 입력 (모달)*/
	@Override
	public int insertReport(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
		int ret = 0;
		int task_id = (int)paramMap.get("task_id");
		int user_no= (int) paramMap.get("user_no");
		// user_no = 작성자 id , task_id = 과제 id  
		String itemFilePath = subPath + File.separator + user_no + File.separator + task_id + File.separator ;
		FileUtilCho fileUtilCho = new FileUtilCho(mpRequest, rootPath, virtualRootPath, itemFilePath);
		Map<String, Object> listFileUtilModel = fileUtilCho.uploadFiles();
		try{
			submitReportDao.insertReport(paramMap); //첨부파일 없이 과제 내용 입력
			System.out.println("확인작업"+listFileUtilModel);	
			listFileUtilModel.put("user_no", paramMap.get("user_no"));
			listFileUtilModel.put("task_id", paramMap.get("task_id"));
			System.out.println("임플"+mpRequest );
			if(mpRequest.getFile("bbs_files_1").getSize()>0){
				ret = submitReportDao.updateReportSubFil(listFileUtilModel);
			}else{
				System.out.println("첨부파일 없어요");
			}
		}catch(Exception e){
			throw e;
		}
		return ret;
	}

	/** 과제 첨부파일 입력 (모달)*/
	public int updateReportSubFil(Map<String, Object>paramMap, HttpServletRequest  request) throws Exception{
		MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
		int ret = 0;
		// 과제아이디 task_id, 학생아이디 user_no 
		int task_id = (int)paramMap.get("task_id");
		int user_no= (int) paramMap.get("user_no");
		// 파일 저장  user_no = 작성자 id , task_id = 과제 id  
		String itemFilePath = subPath + File.separator + user_no + File.separator + task_id + File.separator ;
		FileUtilCho fileUtilCho = new FileUtilCho(mpRequest, rootPath, virtualRootPath, itemFilePath);
		Map<String, Object> listFileUtilModel = fileUtilCho.uploadFiles();
		
		try{
			submitReportDao.updateReport(paramMap); //과제 내용 수정
			//System.out.println("확인작업"+listFileUtilModel);	
			listFileUtilModel.put("user_no", paramMap.get("user_no"));
			listFileUtilModel.put("task_id", paramMap.get("task_id"));
			//System.out.println("임플"+mpRequest );
			SubmitReportVO model = submitReportDao.deleteList(paramMap);
			String exName = model.getSub_filename();
			System.out.println("기존"+exName);
			System.out.println("이후"+mpRequest.getFile("bbs_files_1"));
			
			if(mpRequest.getFile("bbs_files_1").getSize()>0){
				System.out.println("있어요");
				// 기존의 정보를 꺼내와서 삭제
				Map<String, Object> deleteFile = new HashMap<>();
				deleteFile.put("file_nm","\\homework\\"+paramMap.get("loginID")+"\\"+paramMap.get("hwk_id")+"\\"+model.getSub_filename());
				fileUtilCho.deleteFiles(deleteFile);
				// 과제 DB 삭제
				int deleteret = submitReportDao.deleteFileInfo(paramMap);
				//첨부파일 업데이트 실행
				if(deleteret > 0 ){
					ret = submitReportDao.updateReportSubFil(listFileUtilModel); //파일 저장
				}
				
			}else{
				System.out.println("비어있어요");
			}
			
		}catch(Exception e){
			// 기존 파일 삭제
			fileUtilCho.deleteFiles(listFileUtilModel);
			throw e;
		}
		return ret;
	}

	/** 과제 삭제 */
	public int deleteHwkSub(Map<String, Object>paramMap) throws Exception{
	
		int ret = 0;

		try{
			FileUtilCho fileUtilCho = new FileUtilCho();
			
			SubmitReportVO model = submitReportDao.deleteList(paramMap);
			
			//						  homework\sasa023\2\1109.txt
			// D:\FileRepository\\    homework\sasa023\2\1106.txt
			Map<String, Object> deleteFile = new HashMap<>();
			deleteFile.put("file_nm","\\homework\\"+paramMap.get("user_no")+"\\"+paramMap.get("task_id")+"\\"+model.getSub_filename());
			fileUtilCho.deleteFiles(deleteFile);
			// \homework\sasa023\2\1111.txt
			
			// 과제 DB 삭제
			ret = submitReportDao.deleteHwkSub(paramMap);
			// 파일 삭제
			
			fileUtilCho.deleteFiles(paramMap);
			
		}catch(Exception e){
			throw e;
		}
		return ret;
	}

}

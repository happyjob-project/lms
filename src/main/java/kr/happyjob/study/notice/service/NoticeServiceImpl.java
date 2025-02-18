package kr.happyjob.study.notice.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.happyjob.study.system.model.ComnCodUtilModel;
import kr.happyjob.study.common.comnUtils.FileUtilCho;
import kr.happyjob.study.login.model.UserInfo;
import kr.happyjob.study.notice.dao.NoticeDao;
import kr.happyjob.study.notice.model.Noticevo;

@Service
public class NoticeServiceImpl implements NoticeService {

	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	// Get class name for logger
	private final String className = this.getClass().toString();
	
	@Autowired
	NoticeDao noticeDao;
	
	@Value("${fileUpload.rootPath}")
	private String rootPath;
	
	@Value("${fileUpload.noticePath}")
	private String itemPath;
	
	@Value("${fileUpload.virtualRootPath}")
	private String virtualRootPath;	
	
	
	/** 목록 조회 */
	public List<Noticevo> noticelist(Map<String, Object> paramMap) throws Exception {
		
		List<Noticevo> listdata = noticeDao.noticelist(paramMap);
		
		return listdata;
	}
	
	/** 카운트 조회 */
	public int noticecnt(Map<String, Object> paramMap) throws Exception {
		
		int totalCount = noticeDao.noticecnt(paramMap);
		
		return totalCount;
	}
	
	/** 등록 */
	public int noticesave(Map<String, Object> paramMap) throws Exception {		
		return noticeDao.noticesave(paramMap);
	}
	
	/** 수정 */
	public int noticeupdate(Map<String, Object> paramMap) throws Exception {
		return noticeDao.noticeupdate(paramMap);
	}
	
	/** 상세 조회 */
	public Noticevo noticedtl(Map<String, Object> paramMap) throws Exception {
		return noticeDao.noticedtl(paramMap);
	}
	
	/** 조회수 수정 */
	public int noticeviewsupdate(Map<String, Object> paramMap) throws Exception {
		return noticeDao.noticeviewsupdate(paramMap);
	}
	
	/** 삭제 */
	public int noticedelete(Map<String, Object> paramMap) throws Exception {
		return noticeDao.noticedelete(paramMap);
	}
	
	/** 등록 파일 */
	public int noticeinsertfile(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		// 1. 업로드 된 파일 저장		
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		
		String itemFilePath = itemPath + File.separator;  
		FileUtilCho fileup = new FileUtilCho(multipartHttpServletRequest, rootPath, virtualRootPath, itemFilePath);
		Map<String, Object> fileinfo = fileup.uploadFiles();
	
		
		// 2. 업로드 된 파일의 정보 (파일명, 논리경로, 물리경로, 사이즈, 확장자)  ===> DB 처리
		//map.put("file_nm", file_nm);
        //map.put("file_size", file_Size);
        //map.put("file_loc", file_loc);
        //map.put("vrfile_loc", vrfile_loc);
        //map.put("fileExtension", fileExtension);
        //map.put("file_nm_uuid", file_nm_uuid);
		
		if(fileinfo.get("file_nm") == null) {
			paramMap.put("fileyn", "N");
			paramMap.put("fileinfo", null);
		} else {
			paramMap.put("fileyn", "Y");
			paramMap.put("fileinfo", fileinfo);
		}
		// #{fileinfo.file_nm}
		
		return noticeDao.noticeinsertfile(paramMap);
		
	}
	
	/** 수정 파일*/
	public int noticeupdatefile(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
	
		// 0. 기존에 올렸던 파일 있으면 삭제      유치 체크 박스 클릭 되면 삭제 안되게....
		String checkval = (String) paramMap.get("fcheck");
		
		Noticevo orginfo = noticeDao.noticedtl(paramMap);
		if(orginfo.getFilename() != null) {
			if(!"on".equals(checkval)) {
				File orgfile = new File(orginfo.getPhsycalpath());
				orgfile.delete();
			}
		}
		
		// 1. 업로드 된 파일 저장		
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		
		String itemFilePath = itemPath + File.separator;  
		FileUtilCho fileup = new FileUtilCho(multipartHttpServletRequest, rootPath, virtualRootPath, itemFilePath);
		Map<String, Object> fileinfo = fileup.uploadFiles();
		
		// 2. 업로드 된 파일의 정보 (파일명, 논리경로, 물리경로, 사이즈, 확장자)  ===> DB 처리
		//map.put("file_nm", file_nm);
        //map.put("file_size", file_Size);
        //map.put("file_loc", file_loc);
        //map.put("vrfile_loc", vrfile_loc);
        //map.put("fileExtension", fileExtension);
        //map.put("file_nm_uuid", file_nm_uuid);
		
		if(fileinfo.get("file_nm") == null) {
			paramMap.put("fileyn", "N");
			paramMap.put("fileinfo", null);
		} else {
			paramMap.put("fileyn", "Y");
			paramMap.put("fileinfo", fileinfo);
		}
		// #{fileinfo.file_nm}
		
		if("on".equals(checkval)) {
			paramMap.put("fileyn", "N");
			
			System.out.println(paramMap.get("fileyn"));
		} else {
			paramMap.put("fileyn", "Y");
		}
		
		return noticeDao.noticeupdatefile(paramMap);		
		
	}
	
	/** 삭제 */
	public int noticedeletefile(Map<String, Object> paramMap) throws Exception {
		
		// 1. 현재 공지사항에 첨부 파일이 있으면 삭제
		Noticevo orginfo = noticeDao.noticedtl(paramMap);
		if(orginfo.getFilename() != null) {
			File orgfile = new File(orginfo.getPhsycalpath());
			orgfile.delete();
		}
		
		// 2. 테이블에서 삭제
		return noticeDao.noticedelete(paramMap);		
	}
	
}

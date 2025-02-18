package kr.happyjob.study.tut.controller;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.happyjob.study.common.comnUtils.ComnCodUtil;
import kr.happyjob.study.notice.model.Noticevo;
import kr.happyjob.study.tut.model.ReportControlVO;
import kr.happyjob.study.tut.service.ReportControlService;

@Controller
@RequestMapping("/tut/")
public class ReportControlController {
	         
	@Autowired
	ReportControlService reportControlService;
	
	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());

	// Get class name for logger
	private final String className = this.getClass().toString();
	
	/**
	 * 과제관리 초기화면
	 */
	@RequestMapping("reportControl.do")
	public String reportControl( @RequestParam Map<String, Object> paramMap, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		
		logger.info("+ Start " + className + ".reportControl");
		logger.info("   - paramMap : " + paramMap);
		
		logger.info("+ End " + className + ".reportControl");

		return "tut/reportControl";
	}
	
	/* 과제관리 초기화면  및 강의 & 과제 리스트 불러오기*/
	@RequestMapping("reportLecListControl.do")
	public String reportLecListControl( @RequestParam Map<String, Object> paramMap, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {		
		
		int userno = (int) session.getAttribute("user_no");
		paramMap.put("user_no", userno);

		logger.info("+ Start " + className + ".reportLecListControl");
		logger.info("   - paramMap : " + paramMap);
		System.out.println("cpage:::"+paramMap.get("cpage"));
		System.out.println("pagesize:::"+paramMap.get("pagesize"));		
		int cpage = Integer.valueOf((String)paramMap.get("cpage"));
		int pagesize = Integer.valueOf((String)paramMap.get("pagesize"));
		int startpos = (cpage - 1) * pagesize;
		
		// paramMap에서 "cpage" 키를 사용하여 값을 가져옵니다.
//		String cpageString = (String) paramMap.get("cpage");
//		String pagesizeString = (String) paramMap.get("pagesize");
//
//		int cpage = cpageString != null && !cpageString.isEmpty() ? Integer.valueOf(cpageString) : 1;
//		int pagesize = pagesizeString != null && !pagesizeString.isEmpty() ? Integer.valueOf(pagesizeString) : 5;


		System.out.println("cpage:::"+cpage);
		System.out.println("pagesize:::"+pagesize);
//		int startpos = (cpage - 1) * pagesize;
		
		paramMap.put("startpos", startpos);
		paramMap.put("pagesize", pagesize);
		
		List<ReportControlVO> lectureInfo = reportControlService.showLectureList(paramMap);
		
		System.out.println("lectureInfo ::::" + lectureInfo);
		int totalLecccnt = reportControlService.lecListcnt(paramMap);

		
		model.addAttribute("lectureInfo", lectureInfo);
		model.addAttribute("totalLecccnt", totalLecccnt);
		
		model.addAttribute("cccc", lectureInfo.get(0).getLec_no());
//		System.out.println("cccc ::::" + cccc);
//		if (lectureInfo.isEmpty()) {
//		    // lectureInfo가 비어있는 경우에 대한 처리
//		    // 예: 오류 메시지 출력 또는 기본값 설정 등
//		    model.addAttribute("cccc", "No lecture information available");
//		} else {
//		    // lectureInfo가 비어있지 않은 경우에는 정상적으로 처리
//		    model.addAttribute("cccc", lectureInfo.get(0).getLec_no());
//		}
		
		logger.info("+ End " + className + ".reportLecListControl");

		return "tut/reportLecListControl";
	}
	
	/* 과제관리 초기화면  및 강의 & 과제 리스트 불러오기*/
	@RequestMapping("reportListControl.do")
	public String reportListControl( @RequestParam Map<String, Object> paramMap, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {		
		
		int userno = (int) session.getAttribute("user_no");
		paramMap.put("user_no", userno);
		
		logger.info("+ Start " + className + ".reportListControl");
		logger.info("   - paramMap : " + paramMap);
		System.out.println("cpage:::"+paramMap.get("cpage"));
		System.out.println("pagesize:::"+paramMap.get("pagesize"));
		int cpage = Integer.valueOf((String)paramMap.get("cpage"));
		int pagesize = Integer.valueOf((String)paramMap.get("pagesize"));
		int startpos = (cpage - 1) * pagesize;
		
		// paramMap에서 "cpage" 키를 사용하여 값을 가져옵니다.
//		String cpageString = (String) paramMap.get("cpage");
//		String pagesizeString = (String) paramMap.get("pagesize");
//		
//		int cpage = cpageString != null ? Integer.valueOf(cpageString) : 1;
//		int pagesize = pagesizeString != null ? Integer.valueOf(pagesizeString) : 5;

//		int startpos = (cpage - 1) * pagesize;
		
		paramMap.put("startpos", startpos);
		paramMap.put("pagesize", pagesize);
				List<ReportControlVO> projectInfo = reportControlService.showProjectInfo(paramMap);
		
		int totalLecccnt2 = reportControlService.reportLisCnt(paramMap);
		
		
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("totalLecccnt2", totalLecccnt2);
		
		logger.info("+ End " + className + ".reportListControl");
		
		return "tut/reportListControl";
	}

	//과제만들기 파일업로드포함
	@RequestMapping("makeProject.do")
	@ResponseBody
	public Map<String, Object> makeProject(Model model, @RequestParam Map<String, Object> paramMap,
	         HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {		
		
//		int lec_no = (int) session.getAttribute("cccc");
//		paramMap.put("cccc", lec_no);
//		String lec_no = (String) paramMap.get("lec_no");
//		model.addAttribute("cccc", Integer.parseInt(lec_no));
		
		String lec_no = (String) paramMap.get("lec_no");
		if (lec_no != null && !lec_no.isEmpty()) {
		    model.addAttribute("cccc", Integer.parseInt(lec_no));
		} else {
		    // 예외 처리 또는 기본값 설정 등 필요한 작업 수행
		}
		
		logger.info("+ Start " + className + ".makeProejct");
		logger.info("   - paramMap : " + paramMap);
		
		int complete = reportControlService.makeProject(paramMap, request);
		
		System.out.println(complete);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result","SUCCESS");

		logger.info("+ End " + className + ".makeProejct");


		return resultMap;
	}

	//제출한 학생 불러오기
	@RequestMapping("submitInfo.do")
	public String submitInfo(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		String task_id = (String) paramMap.get("task_id");
		paramMap.put("task_id", Integer.parseInt(task_id));
		
	
			System.out.println("task_id::::::"+task_id);
	
		    List<ReportControlVO> submitInfo = reportControlService.showSubmitInfo(paramMap);
		    
		    System.out.println("submitInfo:::: " + submitInfo);

		model.addAttribute("submitInfo", submitInfo);
		
		return "tut/reportControl_submitName";
	}
//	//제출한 학생의 과제 자세히 보기
	@RequestMapping("studentProjectCon.do")

	public String studentProjectCon(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		
		String user_no = (String) paramMap.get("user_no");
		String task_id = (String) paramMap.get("task_id");
		
		
		
		paramMap.put("user_no", user_no);
		paramMap.put("task_id", Integer.parseInt(task_id));
		
		ReportControlVO studentCon = reportControlService.showStudentCon(paramMap);
		model.addAttribute("studentCon", studentCon);
		

		System.out.println("task_id::::" + task_id +"studentCon:::::" + studentCon);
		return "tut/reportControl_studentCon";
	}


//	//강사가 올린 과제 정보 자세히 보기
	@RequestMapping("projectInfo2.do")
	public String projectInfo2(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		String task_id = (String) paramMap.get("task_id");
		paramMap.put("task_id", Integer.parseInt(task_id));
		ReportControlVO projectInfo = reportControlService.showProjectInfo2(paramMap);
		model.addAttribute("projectInfo", projectInfo);
		
		return "tut/reportControl_reportInfo";
	}
//	
//	
//	//과제를 수정하기위해 강의아이디와 과제아이디 가져오기
	@RequestMapping("newInputModal.do")
	public String newInputModal(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		String task_id = (String) paramMap.get("task_id");
		
		String lec_no = (String) paramMap.get("lec_no");

		ReportControlVO selectone = reportControlService.showProjectInfo2(paramMap);
		model.addAttribute("abcd", Integer.parseInt(lec_no));
		model.addAttribute("abc", Integer.parseInt(task_id));
		model.addAttribute("selectupdate",selectone);
		

		return "tut/reportControl_updateTask";
	}

//	//과제수정하기 파일다운로드기능 포함
	@RequestMapping("updateProject.do")
	@ResponseBody
	public Map<String, Object> updateProject(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		
		int complete = reportControlService.updateProject(paramMap, request);
		System.out.println(complete);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result","SUCCESS");
		
		return resultMap;
	}


//	//학생이 제출한 과제 다운로드하기
	@RequestMapping("downloadHwk.do")
	public void downloadHwk(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		
		String task_id = (String) paramMap.get("task_id");
		paramMap.put("task_id", Integer.parseInt(task_id));		
		
		
		System.out.println("TASK_ID::::" + paramMap.get("task_id"));

//		String file = (String) paramMap.get("sub_path");
//		String fileFilename = (String) paramMap.get("sub_filename");
		
		ReportControlVO studentCon = reportControlService.showStudentCon(paramMap);
		
		System.out.println("studentCon ::::" + studentCon);

		String file = studentCon.getSub_path();
		
		System.out.println("filefile ::::" + file);
		
		byte fileByte[] = FileUtils.readFileToByteArray(new File(file));
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(studentCon.getSub_filename(),"UTF-8")+"\";");//file대신에 파일이름칼럼넣어라
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		     
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		logger.info("+ End " + className + ".downloadHwk");

		return ;
	}

	//강사가 올린 과제 다운로드하기
	@RequestMapping("downloadTutor.do")
	public void downloadTutor(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		
		
		
		String task_id = (String) paramMap.get("task_id");
		paramMap.put("task_id", Integer.parseInt(task_id));		
		
		ReportControlVO selectone = reportControlService.showProjectInfo2(paramMap);
		
		System.out.println("TASK_ID::::" + paramMap.get("task_id"));

		//물리경로 조회해서 담기 
		String file = selectone.getTask_path();
		byte fileByte[] = FileUtils.readFileToByteArray(new File(file));
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(selectone.getTask_filename(),"UTF-8")+"\";");//file대신에 파일이름칼럼넣어라
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		     
		response.getOutputStream().flush();
		response.getOutputStream().close();
//		 OutputStream out = response.getOutputStream();
//		    out.write(fileByte);
//		    out.flush();
//		    out.close(); // 닫은 후에 추가 작업을 수행하지 않도록 수정

		logger.info("+ End " + className + ".downloadTutor");

		return;
	}
	//셀렉트박스 클릭하면 lec_id넘겨서 수업정보, 과제정보 불러오기
//	@RequestMapping("showLectureList.do")
//	public String showLectureList(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
////	String lec_no = (String) request.getParameter("lec_no");
////		paramMap.put("lec_no", Integer.parseInt(lec_no));
//		
//		
//		logger.info("+ Start " + className + ".showLectureList");
//		logger.info("   - paramMap : " + paramMap);
//		
//
//
//		logger.info("+ End " + className + ".showLectureList");
//
//		return "tut/reportControl";
//	}
//	
}
package kr.happyjob.study.std.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.happyjob.study.std.model.LecListVO;
import kr.happyjob.study.std.model.SurveyQueVO;
import kr.happyjob.study.std.service.LecListService;





@Controller
//@RequestMapping("/std/")
public class LecRegisterController {

	/* 2024.04.01 이경진
	 * 학생 - 수강목록 작성
	 * 
	 * */	
	@Autowired
	LecListService lecListService;	
	
	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());

	// Get class name for logger
	private final String className = this.getClass().toString();

	
	//초기화면
	@RequestMapping("/std/registerList.do")
	public String registerList(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		
		logger.info("+ Start " + className + ".registerList");
		logger.info("   - paramMap : " + paramMap);
		

		
		logger.info("+ End " + className + ".registerList");


		return "std/learn_mng/registerList";
	}
	
	
	@RequestMapping("/std/registerListBody.do")
	public String registerListBody(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		
		logger.info("+ Start " + className + ".registerListBody");
		logger.info("   - paramMap : " + paramMap);
		
		System.out.println("cp:::"+paramMap.get("currentPage"));
		System.out.println("pageSize:::"+paramMap.get("currentPage"));
		String currentPageStr = (String) paramMap.get("currentPage");
		String pageSizeStr = (String) paramMap.get("pageSize");

		int currentPage = currentPageStr != null ? Integer.valueOf(currentPageStr) : 1;
		int pageSize = pageSizeStr != null ? Integer.valueOf(pageSizeStr) : 10; // 기본값을 설정하세요
		
		int startPos = (currentPage - 1) * pageSize;
		
		paramMap.put("startPos", startPos);
		paramMap.put("pageSize", pageSize);
		
		List<LecListVO> listdata = lecListService.lecList(paramMap);
		int listcnt = lecListService.lecListCnt(paramMap);
		
		model.addAttribute("listdata", listdata);
		model.addAttribute("listcnt",listcnt);
		
		
		logger.info("+ End " + className + ".registerListBody");


		return "std/learn_mng/registerListBody";
	}
	
	//특정 과목 내용 불러오기
	@ResponseBody
	@RequestMapping("/std/registerListDtl.do")
	public Map<String, Object> registerListDtl(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		
		logger.info("+ Start " + className + ".registerListDtl");
		logger.info("   - paramMap : " + paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
//		int cpage = Integer.valueOf((String)paramMap.get("cpage"));
//		int pagesize = Integer.valueOf((String)paramMap.get("pagesize"));
//		int startpos = (cpage - 1) * pagesize;
//		
//		paramMap.put("startpos", startpos);
//		paramMap.put("pagesize", pagesize);
//		
		
		LecListVO dtlData = lecListService.lecListDtl(paramMap);
		SurveyQueVO surveyQueData = lecListService.selectOneSurveyQuestion(paramMap);
		SurveyQueVO surveyAnswer = lecListService.selectOneSurveyAnswer(paramMap);
		
		resultMap.put("dtlData", dtlData);
		resultMap.put("surveyQueData", surveyQueData);
		resultMap.put("surveyAnswer", surveyAnswer);
		

//		model.addAttribute("dtlData", dtlData);
//		model.addAttribute("listcnt",listcnt);
		
		
		logger.info("+ End " + className + ".registerListDtl");


		return resultMap;
	}
	
	
	// 강의 리뷰 등록하기
	@ResponseBody
	@RequestMapping("/std/surveyInsert.do")
	public Map<String, Object> surveyInsert(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		
		logger.info("+ Start " + className + ".surveyInsert");
		logger.info("   - paramMap : " + paramMap);
		
		String result="";
		String resultmsg="";

		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println("surveyInsert:: lec_no::"+ paramMap.get("lec_no"));
		
		int surveyInsertResult = lecListService.surveyAnswerInsert(paramMap);

		
		if(surveyInsertResult > 0){
			result = "SUCCESS";
			resultmsg = "설문조사 제출이 완료되었습니다.";
		}else {
			result = "FAIL";
			resultmsg = "설문조사 제출이 실패하였습니다.";
		}
		
		resultMap.put("result", result);
		resultMap.put("resultmsg", resultmsg);
		
		logger.info("+ End " + className + ".surveyInsert");


		return resultMap;
	}
	


	//과목 리스트 불러오기
	@RequestMapping("selectLecList.do")
	public String searchLec(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
	/*	
		String lec_name = (String) request.getAttribute("keyword");
		paramMap.put("lec_name",lec_name);
		lecListService.searchLec(paramMap);
		
		List<LecListModel> searchLec = lecListService.searchLec(paramMap);
		model.addAttribute("searchLec", searchLec);*/
		
		int currentPage = Integer.parseInt((String) paramMap.get("currentPage")); // 현재페이지
	    int pageSize = Integer.parseInt((String) paramMap.get("pageSize"));
	    int pageIndex = (currentPage - 1) * pageSize;
		
		paramMap.put("pageIndex", pageIndex);
		paramMap.put("pageSize", pageSize);
		
		
		
//		System.out.println("doskdosk"+paramMap);
//		List<LecListModel> listLec = lecListService.listLec(paramMap);
//		
//		int totalCount = lecListService.countListLec(paramMap);
		
//		model.addAttribute("listLec", listLec);
//	    model.addAttribute("totalCntLec", totalCount);
//	    model.addAttribute("pageSize", pageSize);
//	    model.addAttribute("currentPageLec",currentPage);
	    
	    
		return "std/learn_sup/LectureListCallback";
	}
	
	//과목 상세조회
	@RequestMapping("detailLecList.do")
	public String lectureInfo(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		
		String loginID = (String) session.getAttribute("loginId");
		paramMap.put("loginID", loginID);
		
//		LecListModel lecInfo = lecListService.lecInfo(paramMap);
//		List<LecListModel> lecWeekPlan = lecListService.lecWeekPlan(paramMap);
//		LecListModel idCheck = lecListService.idCheck(paramMap);
//		
//		model.addAttribute("lecInfo", lecInfo);
//		model.addAttribute("lecWeekPlan", lecWeekPlan);
//		model.addAttribute("idCheck", idCheck);
		
		
		
				
		return "std/learn_sup/LectureListCallback1";
	}
	
	//수강신청
	@RequestMapping("applyLecture.do")
	@ResponseBody
	public Map<String, Object> applyLec(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)throws Exception{
		
		String loginID = (String) session.getAttribute("loginId");
		paramMap.put("loginID", loginID);
		
//		LecListModel numCheck = lecListService.numCheck(paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
//		
//		if ((numCheck.getPre_pnum()<numCheck.getMax_pnum())){
//		
//			//1이 온다
//			int applyVal = lecListService.lecApply(paramMap);
//			int applyVal2 = lecListService.lecApply2(paramMap);
//		
//		
//		resultMap.put("result","수강신청이 완료되었습니다.");
//		}
		
//		else
//		{
//			resultMap.put("result","최대 수강인원보다 많은 인원이 신청할 수 없습니다.");
//		}
		
		
		return resultMap;
	}
	
	//수강취소
	@RequestMapping("cancelLecture.do")
	@ResponseBody
	public Map<String, Object> cancelLec(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)throws Exception{
		
		String loginID = (String) session.getAttribute("loginId");
		paramMap.put("loginID", loginID);
		int lec_id = Integer.parseInt((String) paramMap.get("lec_id"));
		
//		LecListModel okCheck = lecListService.okCheck(paramMap);
//		LecListModel idCheck = lecListService.idCheck(paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
		
//		if (lec_id == idCheck.getLec_id() && okCheck.getApv().equals("N")){
//		
//			//1이 온다
//			int cancelVal = lecListService.lecCancel(paramMap);
//			int cancelVal2 = lecListService.lecCancel2(paramMap);
//			
//			System.out.println(lec_id);
//			System.out.println(idCheck.getLec_id());
//			System.out.println(okCheck.getApv());
//			
//		
//		
//		resultMap.put("result","수강취소가 완료되었습니다.");
//		}
//		
//		else {
//			resultMap.put("result","수강취소가 불가능합니다.");
//		}
		
		
		return resultMap;
	}

}

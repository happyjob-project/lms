package kr.happyjob.study.std.controller;


import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.happyjob.study.std.model.SubmitReportVO;
import kr.happyjob.study.std.service.SubmitReportService;
import kr.happyjob.study.tut.model.ReportControlVO;



@Controller
@RequestMapping("/std")
public class SubmitReportController {
	@Autowired
	SubmitReportService submitReportService;	
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	private final String className = this.getClass().toString();
	
	//화면이동 
	@RequestMapping("/submitReport.do")
	public String submitReport (Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception{
		
	      int userno = (int) session.getAttribute("user_no");
	      paramMap.put("user_no", userno);
		
			logger.info("+ Start " + className + ".submitReport");
			logger.info("   - paramMap : " + paramMap);
			logger.info("+ End " + className + ".submitReport");
			
		return "/std/learn_mng/submitReport";
	}

	//과제목록조회하기
	@RequestMapping("/selReportList.do")
	public String selReportList(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		
//		String user_no = (String) paramMap.get("user_no"); //로그인 세션 값 가져오기
//		paramMap.put("user_no", Integer.parseInt(user_no));
//			
		
			int userno = (int) session.getAttribute("user_no");
			paramMap.put("user_no", userno);
		
			logger.info("+ Start " + className + ".selReportList");
			logger.info("   - paramMap : " + paramMap);

			int currentPage = Integer.parseInt((String)paramMap.get("currentPage"));	// 현재 페이지 번호
			int pageSize = Integer.parseInt((String)paramMap.get("pageSize"));			// 페이지 사이즈
			int pageIndex = (currentPage-1)*pageSize;									// 페이지 시작 row 번호
					
			paramMap.put("pageIndex", pageIndex);
			paramMap.put("pageSize", pageSize);
			
			List<SubmitReportVO> selReportList = submitReportService.selReportList(paramMap);  //과제목록 가져오기
			int totalCount = submitReportService.countReportList(paramMap);  //카운트
			
			model.addAttribute("pageSize",pageSize);
			model.addAttribute("currentPage",currentPage);
			
			model.addAttribute("selReportList",selReportList);
			model.addAttribute("totalCount",totalCount);
			
			logger.info("+ End " + className + ".selReportList");
			
			return "/std/learn_mng/submitReportList";
	}
	
	//선택 과제 data가져오기
	@RequestMapping("/choiceReportList.do")
	@ResponseBody
		public Map<String, Object> choiceReportList (Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
				HttpServletResponse response, HttpSession session) throws Exception {
			//세션에서 로그인 값 가져오기.
			int user_no= (int) session.getAttribute("user_no");
			String task_id = (String) paramMap.get("task_id");
			
			paramMap.put("user_no", user_no);
			paramMap.put("task_id", Integer.parseInt(task_id));
			
		
				System.out.println("user_no::::::"+user_no);
				System.out.println("task_id::::::"+task_id);
		
			logger.info("+ Start " + className + ".choiceReportList");
			logger.info("   - paramMap : " + paramMap);
			
			String result = "SUCCESS";
			String resultMsg = "조회 되었습니다.";
			
			SubmitReportVO choiceReportList = submitReportService.choiceReportList(paramMap);

			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			resultMap.put("result", result);
			resultMap.put("resultMsg", resultMsg);
			
			resultMap.put("choiceHwkList", choiceReportList);
			
			return resultMap;
		}
	
	//선택 과제 data insert-update
	@RequestMapping("/saveReport.do")
	@ResponseBody
		public Map<String, Object> saveReport(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
				HttpServletResponse response, HttpSession session) throws Exception {
			//세션에서 로그인 값 가져오기.
		int user_no= (int) session.getAttribute("user_no");
		String task_id = (String) paramMap.get("task_id");
		
		paramMap.put("user_no", user_no);
		paramMap.put("task_id", Integer.parseInt(task_id));
			
			String action = (String)paramMap.get("action");
			String result = "SUCCESS";
			String resultMsg = "저장 되었습니다.";
			
			logger.info("+ Start " + className + ".saveReport");
			logger.info("   - paramMap : " + paramMap);
			
			if ("I".equals(action)) {
				
				submitReportService.insertReport(paramMap, request); // 과제 신규 저장 
				
			} else if("U".equals(action)) {
				
				int saveReportSubFil = submitReportService.updateReportSubFil(paramMap, request);
				
			} else {
				
				result = "FALSE";
				resultMsg = "알수 없는 요청 입니다.";
			}
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("result",result);
			resultMap.put("resultMsg", resultMsg);
			
			return resultMap;
		}
	
	
	//과제 제출 삭제
	@RequestMapping("/deleteHwk.do")
	@ResponseBody
	public Map<String, Object> deleteHwk(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		//세션에서 로그인 값 가져오기.
		int user_no= (int) session.getAttribute("user_no");
		String task_id = (String) paramMap.get("task_id");
		
		paramMap.put("user_no", user_no);
		paramMap.put("task_id", Integer.parseInt(task_id));
			
		
		System.out.println(paramMap.get("task_id"));
		
		logger.info("+ Start " + className + ".deleteHwk");

		String result = "SUCCESS";
		String resultMsg = "삭제 되었습니다.";

		logger.info("   - paramMap : " + paramMap);
		// 게시글 삭제
		submitReportService.deleteHwkSub(paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		resultMap.put("resultMsg", resultMsg);
		
		logger.info("+ End " + className + ".deleteHwk");
		
		return resultMap;
	}
	//다운로드_by 창규창규 - 과제 목록 화면으로 이동
	@RequestMapping("/downloadHwk.do")
	   public void downloadHwk(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
			
		int user_no= (int) session.getAttribute("user_no");
		String task_id = (String) paramMap.get("task_id");
		String lec_no = (String) paramMap.get("lec_no");
		
		paramMap.put("user_no", user_no);
		paramMap.put("task_id", Integer.parseInt(task_id));
		paramMap.put("lec_no", Integer.parseInt(lec_no));
		
		SubmitReportVO choiceReportList = submitReportService.choiceReportList(paramMap);
		
		String fileName = choiceReportList.getTask_filename();
		String file = choiceReportList.getTask_path(); //다운url설정 과제목록
		

		System.out.println("userno::::::" + user_no);
		System.out.println("filename:::::::" + fileName);
		System.out.println("file:::::::" + file);
			

		
			//String file = "D:\\FileRepository\\project\\hwk_id\\testfile.txt";

			
		byte fileByte[] = FileUtils.readFileToByteArray(new File(file));
		System.out.println("fileByte::::::" + fileByte);
			response.setContentType("application/octet-stream");
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8")+"\";");
//			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(((SubmitReportVO) downTask).getTask_filename(),"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);
	           
			response.getOutputStream().flush();
			response.getOutputStream().close();
	      
	      
		    return;
	   }

}

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>상담관리</title>

<jsp:include page="/WEB-INF/view/common/common_include.jsp"></jsp:include>

<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>

<script type="text/javascript">
	//상담목록 페이징 설정
	var pageSize = 5;
	var pageSize_adv = 10;
	var pageBlockSize = 5;

	/** OnLoad event */
	$(document).ready(function() {
		// 강의목록 조회
		flist_lec();

		// 버튼 이벤트 등록
		fRegisterButtonClickEvent();

		$("#Datepicker").datepicker({});
		
		$(".refresh").click(function(){
			$("#searchWord_adv").val('');
		 	$("#searchWord_lec").val('');
			$("#option1").prop("selected",true); 
		});
	 	
		$('#summernote').summernote({
			  height: 300,                 // 에디터 높이
			  minHeight: null,             // 최소 높이
			  maxHeight: null,             // 최대 높이
			  focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
			  lang: "ko-KR",					// 한글 설정
			  placeholder: '상담내용을 입력해주세요.',	//placeholder 설정
			  toolbar: [
			            // [groupName, [list of button]]
			            ['style', ['bold', 'italic', 'underline', 'clear']],
			            ['font', ['strikethrough']],
			            ['fontsize', ['fontsize']],
			            ['color', ['color']],
			            ['para', ['ul', 'ol', 'paragraph']],
			            ['height', ['height']]
			          ]
		}); 
		
	});

	/** 버튼 이벤트 등록 */
	function fRegisterButtonClickEvent() {
		$('a[name=btn]').click(function(e) {
			e.preventDefault();

			var btnId = $(this).attr('id');

			switch (btnId) {
			case 'btnSave':
				fsave_adv();
				break;
			case 'btnUpdate':
				fsave_adv();
				break;
			case 'btnClose':
				gfCloseModal();
				break;
			case 'btnDel':
				fDelete_adv();
				break;
			}
		});
	}

	/** 강의목록 조회 */
	function flist_lec(currentPage) {

		currentPage = currentPage || 1;
		searchWord = $("#searchWord_lec").val();
		console.log("currentPage : " + currentPage);
		console.log("searchWord : " + searchWord);

		var param = {
			searchWord_lec : searchWord,
			currentPage : currentPage,
			pageSize : pageSize
		}

		var resultCallback = function(data) {
			flist_lec_result(data, currentPage);
		};
		//Ajax실행 방식
		//callAjax("Url",type,return,async or sync방식,넘겨준거,값,Callback함수 이름)
		callAjax("/adm/lecAdList.do", "post", "text", true, param,
				resultCallback);
	}

	/** 강의목록 조회 콜백 함수 */
	function flist_lec_result(data, currentPage) {

		//alert(data);
		console.log(data);

		// 기존 목록 삭제
		$('#lecAdList').empty();

		// 신규 목록 생성
		$("#lecAdList").append(data);

		// 총 개수 추출
		var totalCnt_lec = $("#totalCnt_lec").val();

		// 페이지 네비게이션 생성
		var paginationHtml = getPaginationHtml(currentPage, totalCnt_lec,
				pageSize, pageBlockSize, 'flist_lec');
		console.log("paginationHtml : " + paginationHtml);
		//alert(paginationHtml);
		$("#Pagination_lec").empty().append(paginationHtml);

		// 현재 페이지 설정
		$("#currentPage_lec").val(currentPage);

	//	flist_advice();

	}

	/** 상담 목록 조회 */
	function flist_advice(currentPage, lec_no) {

		currentPage = currentPage || 1;
		searchWord = $("#searchWord_adv").val();
		searchKey = $("#searchKey_adv").val();
		/* searchWord = '';
		searchKey = '';

		if (lec_id == null || lec_id == '') {
			searchWord = $("#searchWord_adv").val();
			searchKey = $("#searchKey_adv").val();
		} */

		console.log("lec_no:" + lec_no);
		console.log("searchWord_adv:" + searchWord_adv);
		console.log("searchKey_adv_adv:" + searchKey_adv);

		// 강의 정보 설정
		$("#tmp_lec").val(lec_no);

		var param = {
			searchKey_adv : searchKey,
			searchWord_adv : searchWord,
			lec_no : lec_no,
			currentPage : currentPage,
			pageSize_adv : pageSize_adv
		}

		var resultCallback = function(data) {
			flist_advice_result(data, currentPage);
		};

		callAjax("/adm/AdviceList.do", "post", "text", true, param,
				resultCallback);
	}

	/** 상담 조회 콜백 함수 */
	function flist_advice_result(data, currentPage) {

		// 기존 목록 삭제
		$('#advice_list').empty();

		// 신규 목록 생성
		$('#advice_list').append(data);

		// 총 개수 추출
		var totalCnt_adv = $("#totalCnt_adv").val();

		// 페이지 네비게이션 생성
		var lec_no = $("#tmp_lec").val();
		var paginationHtml = getPaginationHtml(currentPage, totalCnt_adv,
				pageSize_adv, pageBlockSize, 'flist_advice', [ lec_no ]);
		$("#Pagination_advice").empty().append(paginationHtml);

		// 현재 페이지 설정
		$("#currentPage_advice").val(currentPage);

		//$('#searchWord_adv').val('');
	
	}
	/** 상담 모달 실행 */
	function fPopModal_adv(adv_no, lec_no) {

		// 신규 저장
		if (adv_no == null || adv_no == "") {

			// Tranjection type 설정
			$("#action").val("I");

			// 상담 모달 폼 초기화
			fInit_adv();
			flist_lecture();
			// 모달 팝업
			gfModalPop("#layer1");

			// 수정 저장
		} else {

			flist_lecture();
			// Tranjection type 설정
			$("#action").val("U");

			// 그룹코드 단건 조회
			fSelec_adv(adv_no, lec_no);
		}
	}
	/** 모달 강의 목록 조회 */
	function flist_lecture() {

		var param = {}

		var resultCallback = function(data) {
			flist_lecture_result(data);
		};

		callAjax("/adm/mlist_lecture.do", "post", "text", true, param,
				resultCallback);
	}

	/** 모달 강의목록 콜백 함수 */
	function flist_lecture_result(data) {
		console.log(data);

		// 기존 목록 삭제
		$('#list_lecture').empty();

		// 신규 목록 생성
		$('#list_lecture').append(data);
	}

	/** 모달 학생 목록 조회 */
	function flist_student() {
		var lec_no = $("#list_lecture").val();

		var param = {
			lec_no : lec_no,
		}

		var resultCallback = function(data) {
			flist_student_result(data);
		};

		callAjax("/adm/mlist_student.do", "post", "text", true, param,
				resultCallback);
	}

	/** 모달 학생목록 콜백 함수 */
	function flist_student_result(data) {
		console.log(data);

		// 기존 목록 삭제
		$('#list_student').empty();

		// 신규 목록 생성
		$('#list_student').append(data);
	}

	/** 상담 저장 validation */
	function fValidate_adv() {

		var chk = checkNotEmpty([ [ "summernote", "상담내용을 입력해 주세요." ] ]);

		if (!chk) {
			return;
		}

		return true;
	}

	/** 상담 저장 */
	function fsave_adv() {

		// vaildation 체크
		if (!fValidate_adv()) {
			return;
		}

		var resultCallback = function(data) {
			fsave_adv_result(data);
		};

		callAjax("/adm/save_adv.do", "post", "json", true, $("#myForm")
				.serialize(), resultCallback);
	}

	/** 상담 저장 콜백 함수 */
	function fsave_adv_result(data) {

		// 목록 조회 페이지 번호
		var currentPage = "1";
		if ($("#action").val() != "I") {
			currentPage = $("#currentPage_adv").val();
		}

		if (data.result == "SUCCESS") {

			// 응답 메시지 출력
			alert(data.resultMsg);

			// 모달 닫기
			gfCloseModal();

			// 목록 조회
			if ($("#action").val() == "I") {
				var lec_no = $("#list_lecture").val();
			} else {
				var lec_no = $("#tmp_lec").val();
			}
			flist_advice(currentPage, lec_no);

		} else {
			// 오류 응답 메시지 출력
			alert(data.resultMsg);
		}

		// 입력폼 초기화
		fInit_adv();
	}

	/** 상담 폼 초기화 */
	function fInit_adv(object) {

		if (object == "" || object == null || object == undefined) {
			$(".tr_info").hide();
			$("#lec_nm").hide();
			$("#std_name").hide();
			$("#list_lecture").show();
			$("#list_student").show();
			$("#list_lecture").val("");
			$("#list_student").val("");
			$("#adv_place").val("");
			$("#Datepicker").val("");
			$('#summernote').summernote('reset');
			$("#up_date").val("");

			$("#btnSave").show();
			$("#btnUpdate").hide();
			$("#btnDel").hide();

		} else {
			//$("#adv_info").find('input, textarea, button, select').attr("readonly",true);
			$(".tr_info").hide();
			$("#lec_nm").show();
			$("#std_name").show();
			$("#list_lecture").hide();
			$("#list_student").hide();
			$("#lec_nm").val(object.lec_nm);
			$("#std_name").val(object.std_name + " (" + object.user_no + ")");
			$("#tut_name").val(object.tut_name + " (" + object.tut_id + ")");
		
			
			$("#adv_place").val(object.adv_place);
			$("#Datepicker").val(object.adv_date);
			$("#summernote").summernote('code', object.adv_content);
			$("#up_date").val(object.up_date);
			$("#btnSave").hide();
			$("#btnUpdate").show();
			$("#btnDel").show();
		}
	}
	
	/** 상담 삭제 */
	function fDelete_adv() {

		var resultCallback = function(data) {
			fDelete_adv_result(data);
		};

		callAjax("/adm/delete_adv.do", "post", "json", true, $("#myForm")
				.serialize(), resultCallback);
	}

	/** 상담 삭제 콜백 함수 */
	function fDelete_adv_result(data) {

		var currentPage = $("#currentPage_adv").val();
		var lec_no = $("#tmp_lec").val();

		if (data.result == "SUCCESS") {

			// 응답 메시지 출력
			alert(data.resultMsg);

			// 모달 닫기
			gfCloseModal();

			// 그룹코드 목록 조회
			flist_advice(currentPage, lec_no);

		} else {
			alert(data.resultMsg);
		}
	}
	/** 상담 단건 조회 */
	function fSelec_adv(adv_no, lec_no) {
		$("#tmp_adv").val(adv_no);

		var param = {
			adv_no : adv_no,
			lec_no : lec_no
		};

		var resultCallback = function(data) {
			fSelect_adv_result(data);
		};

		callAjax("/adm/adv_one.do", "post", "json", true, param, resultCallback);
	}

	/** 상담 단건 조회 콜백 함수*/
	function fSelect_adv_result(data) {

		if (data.result == "SUCCESS") {

			// 모달 팝업
			gfModalPop("#layer1");

			// 그룹코드 폼 데이터 설정
			fInit_adv(data.adv_model);

		} else {
			alert(data.resultMsg);
		}
	}

	
	
	
</script>


</head>
<body>
	<form id="myForm" action="" method="">
		<input type="hidden" id="currentPage_lec" value="1"> <input
			type="hidden" id="currentPage_adv" value="1"> <input
			type="hidden" id="tmp_lec" value=""> <input type="hidden"
			id="tmp_adv" name="adv_no" value=""> <input type="hidden"
			name="action" id="action" value="">

		<!-- 모달 배경 -->
		<div id="mask"></div>

		<div id="wrap_area">

			<h2 class="hidden">header 영역</h2>
			<jsp:include page="/WEB-INF/view/common/header.jsp"></jsp:include>

			<h2 class="hidden">컨텐츠 영역</h2>
			<div id="container">
				<ul>
					<li class="lnb">
						<!-- lnb 영역 --> <jsp:include
							page="/WEB-INF/view/common/lnbMenu.jsp"></jsp:include> <!--// lnb 영역 -->
					</li>
					<li class="contents">
						<!-- contents -->
						<h3 class="hidden">contents 영역</h3> <!-- content -->
						<div class="content">

							<p class="Location">
								<a href="/notice/aNotice.do" class="btn_set home">메인으로</a> <a href=""
									class="btn_nav">학습 관리</a> <span class="btn_nav bold">상담
									관리</span> <a href="javascript:flist_lec()" class="btn_set refresh">새로고침</a>
							</p>

							<p class="conTitle" id="searcharea">
								<span>상담관리</span> <span class="fr"> <span>강의명:</span> <input
									type="text" id="searchWord_lec" name="searchWord_lec"
									onKeyDown="if(event.keyCode == 13) flist_lec()"
									style="height: 28px;"> <a class="btnType blue"
									href="javascript:flist_lec()" name="search"><span
										id="searchEnter">검색</span></a></span>
							</p>


							<!--강의 리스트  -->
							<div class="div_list_advice" id="div_list_advice">
								<table class="col">
									<caption>caption</caption>
									<colgroup>
										<col width="5%">
										<col width="30%">
										<col width="10%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">과정 ID</th>
											<th scope="col">과정명 명</th>
											<th scope="col">기간</th>
										</tr>
									</thead>
									<tbody id="lecAdList"></tbody>
								</table>
							</div>

							<div class="paging_area" id="Pagination_lec"></div>
							
							<!--상담목록  -->
																	
							
							<p class="conTitle mt50">
								<span>상담목록</span>
								<span class="fr"> <select id="searchKey_adv"
									name="searchKey_adv" style="width: 80px;">
										<option value="all" id="option1" selected="selected">전체</option>
										<option value="stdNm" id="option1">학생명</option>
										<option value="stdId" id="option2">id</option>
								</select><input type="text" id="searchWord_adv" name="searchWord_adv"
									onKeyDown="if(event.keyCode == 13) flist_advice()"
									style="height: 28px;"> <a class="btnType blue"
									href="javascript:flist_advice()" name="search"><span
										id="searchEnter">검색</span></a> <a class="btnType blue"
									href="javascript:fPopModal_adv();" name="modal"><span>상담
											등록</span></a>
								</span>
							</p>
							<p class="Location">
								 <a href="javascript:flist_advice()" class="btn_set refresh">새로고침</a>
							</p>
							<!--상담 리스트  -->
							<div class="div_list_advice" id="div_list_advice">
								<table class="col">
									<caption>caption</caption>
									<colgroup>
										<col width="5%">
										<col width="10%">
										<col width="20%">
										<col width="10%">
										<col width="20%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">상담 번호</th>
											<th scope="col">수강 강의</th>
											<th scope="col">학생 명 (ID)</th>
											<th scope="col">상담일자</th>
											<th scope="col">강사 (ID)</th>
										</tr>
									</thead>
									<tbody id="advice_list"></tbody>
								</table>
							</div>

							<div class="paging_area" id="Pagination_advice"></div>
								

						</div> <!--// content -->

						<h3 class="hidden">풋터 영역</h3> <jsp:include
							page="/WEB-INF/view/common/footer.jsp"></jsp:include>
					</li>
				</ul>
			</div>
		</div>
		
<!-- 모달팝업 -->
		<div id="layer1" class="layerPop layerType2" style="width:800px; height:700px; overflow:auto;">
			<dl>
				<dt>
					<strong>상담 관리</strong>
				</dt>
				<dd class="content">
					<!-- s : 여기에 내용입력 -->
					<table class="row" id="adv_info">
						<caption>caption</caption>
						<colgroup>
							<col width="120px">
							<col width="*">
							<col width="120px">
							<col width="*">
						</colgroup>

						<tbody>
							<tr>
								<th scope="row">과정명</th>
								<td><select name="lec_no" id="list_lecture"
									onchange="flist_student()" class="inputTxt p100">
								</select> <input type="text" class="inputTxt p100" name="lec_nm"
									id="lec_nm" disabled="disabled" /></td>
								<th scope="row">학생명</th>
								<td><select name="user_no" id="list_student" class="inputTxt p100"></select> <input
									type="text" class="inputTxt p100" name="std_name" id="std_name"
									disabled="disabled" /></td>
							</tr>
							<tr class="tr_info">
								<th scope="row">시험점수</th>
								<td><input type="text" class="inputTxt p100"
									id="last_score" disabled="disabled" />
								<th scope="row">상담자</th>
								<td><input type="text" class="inputTxt p100" id="tut_name"
									disabled="disabled" />
							</tr>
							<tr>
								<th scope="row">상담일자</th>
								<td><input type="text" id="Datepicker" name="adv_date"
									class="inputTxt p100" data-date-format='yyyy.mm.dd' readonly></td>
								<th scope="row">상담장소</th>
								<td><input type="text" class="inputTxt p100" name="adv_place"
									id="adv_place" />
							</tr>
							<tr class="tr_info">
								<th scope="row">최종 수정일자</th>
								<td colspan="6"><input type="text" id="up_date"
									name="up_date" class="inputTxt p100"
									data-date-format='yyyy.mm.dd' readonly></td>
							</tr>
							<tr>
								<td colspan="6"><textarea id="summernote" name="adv_content"
										style="height: 300px"></textarea></td>
							</tr>
						</tbody>
					</table>

					<!-- e : 여기에 내용입력 -->

					<div class="btn_areaC mt30">
						<a href="" class="btnType blue" id="btnSave" name="btn"><span>저장</span></a>
						<a href="" class="btnType blue" id="btnUpdate" name="btn"><span>수정</span></a>
						<a href="" class="btnType red" id="btnDel" name="btn"><span>삭제</span></a>
						<a href="" class="btnType gray" id="btnClose" name="btn"><span>취소</span></a>
					</div>
				</dd>
			</dl>
			<a href="" class="closePop"><span class="hidden">닫기</span></a>
		</div>

</form>
</body>
</html>
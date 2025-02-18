<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Chain Maker :: Login</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

<jsp:include page="/WEB-INF/view/common/common_include.jsp"></jsp:include>

<link rel="stylesheet" type="text/css"
	href="${CTX_PATH}/css/admin/login.css" />

<!-- 우편번호 조회 -->
  
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${CTX_PATH}/js/popFindZipCode.js"></script>
	<!-- sweet alert import -->
<script src='${CTX_PATH}/js/sweetalert/sweetalert.min.js'></script>

<script type="text/javascript" src="${CTX_PATH}/js/login_pub.js"></script>
<script type="text/javascript">

var check;

/* OnLoad Event */
$(document).ready(function() {
	$("#confirm").hide();
	$("#loginRegister").hide();
	$("#loginEmail").hide();
	$("#loginPwd").hide();

	var cookie_user_id = getCookie('EMP_ID');
	if (cookie_user_id != "") {
		$("#EMP_ID").val(cookie_user_id);
		$("#cb_saveId").attr("checked", true);
	}

	$("#EMP_ID").focus();
	
	init();
	
});


function fcancleModal(){
	gfCloseModal();
	}

/* 회원가입 모달창 실행 */
function fRegister() {
	var div_cd;
	$("#action").val("I");
	// 모달 팝업
	gfModalPop("#layer1");
	instaffRegister();
	
}

function init() {
	check = new Vue({
		el: '#layer1',
		data : {
			langitems: [],
			langitems1: [],
			langitems2: [],
			langitems3: [],	
			langitemss: [],
			langitemarea1: [],
			langitemarea2: [],
			langitemarea3: [],
			listlistCod: '',
			weblistCod:'',
			dblistCod:'',
			wslistCod:'',
			sklcdlistCod:'',
			areacdlistCod:'',
			skillgrpcd:'',
			skilldtlcd:''
			
		}
	})
 }

 /*체크리스트 콜백함수*/
 function checklistResult(data){ 	
	
	/*callAjax시 로그인 여부 확인 하므로 ajax 함수 직접 작성*/
 	$.ajax({
		url : '/checklist.do',
		type : 'post',
		data : data,
		dataType : 'json',
		async : true,
		success : function(data) {
			check.check = [];
			check.langitems = data.listlistCod;
			check.langitems1 = data.weblistCod;
			check.langitems2 = data.dblistCod;
			check.langitems3 = data.wslistCod;
			check.langitemss = data.sklcdlistCod;
			check.langitemarea1 = data.areacdlistCod;
			check.langitemarea2 = data.areacdlistCod;
			check.langitemarea3 = data.areacdlistCod;
			}
		});
		}

$("input[v-model=chkbox]:checked").each(function(){
	var chk = $(this).val();
})

var chk_arr = [];
$("input[v-model=chkbox]:checked").each(function(){
	var chk = $(this).val();
	chk_arr.push(chk);
})

/*일반 회원가입 폼 초기화*/
function instaffRegister(){
	
	$("#user_type").val("");
	$("#user_type_th").show();
	$("#user_type_td").show();
	$("#div_cd").val("CommonMember");
	$("#user_type_li").hide();
	$("#registerId").val("");
    $("#registerPwd").val("");
	$("#registerPwdOk").val("");
	$("#idMatch").hide("");
	$("#passwordMatch").hide("");
	$("#passwordOkMatch").hide("");
	$("#rggender_th").show();
	$("#rggender_td").show();
	$("#registerName").val("");
	$("#registerName").show();
	$("#registerName_th").show();
	$("#name").val("");
 	$("#user_gender").val("");
 	$("#user_birth").val("");
 	$("#user_location").val("");
 	$("#user_location_td").show();
	$("#user_location_th").show();
    $("#registerEmail1").val("");
    $("#registerEmail2").val("");
	$("#detailaddr").val("");
	$("#loginaddr").val("");
	$("#loginaddr1").val("");
	$("#tel1").val("");
	$("#tel2").val("");
	$("#tel3").val("");
	$("#del_cd").val("n");
	$("#approval_cd").val("n");
	$("#ckIdcheckreg").val("0");
	$("#birthday1").show();
	$("#email_cop2").hide();
	$("#consult_yn").show();
	$("#user_describe").show();
	$("#user_contents1").show();		
	$("#salary").val("");
	$("#user_hope_work").val("");
	$("#user_describe").val("");
	$("#grade").val("");
 	$("#area1").val("");
	$("#area2").val("");
	$("#area3").val(""); 
	$("#user_contents").val("");
	$("#singular_facts").val("");
	$("#item.dtl_cod").val("");

	console.log($("#div_cd").val());
	
	//체크리스트출력
	checklistResult(); 
		
}

/* 회원가입 validation */
function RegisterVal(){
	  
	var div_cd = $('#div_cd').val();
	var user_type = $('#user_type').val();
	var rgid = $('#registerId').val();
	var rgpwd = $('#registerPwd').val();
	var rgpwdok = $('#registerPwdOk').val();
	var rgname = $('#registerName').val();
	var user_gender = $('#user_gender').val();
	var user_birth = $('#user_birth').val();
	var rgemail1 = $('#registerEmail1').val();
	var rgemail2 = $('#registerEmail2').val();
	var tel1 = $('#tel1').val();
	var tel2 = $('#tel2').val();
	var tel3 = $('#tel3').val();
	var user_location = $('#user_location').val();

	
	if(user_type == ""){
		swal("회원구분을 선택해주세요.").then(function() {
			$("#user_type").focus();
		  });
		return false;
	}
	
	if(rgid.length < 1){
		swal("아이디를 입력하세요.").then(function() {
			$('#registerId').focus();
		  });
		return false;
	}
	
	if(rgpwd.length < 1){
		swal("비밀번호를 입력하세요.").then(function() {
			$('#registerPwd').focus();
		  });
		return false;
	}
	
	if(rgpwdok.length < 1){
		swal("비밀번호 확인을 입력하세요.").then(function() {
			$('#registerPwdOk').focus();
		  });
		return false;
	}
	
	if(rgpwd != rgpwdok){
		swal("비밀번호가 맞지 않습니다.").then(function() {
			$('#registerPwd').focus();
		  });
		return false;
	}
	
	if(rgname.length < 1){
		swal("이름을 입력하세요.").then(function() {
			$('#registerName').focus();
		  });
		return false;
	}
	
	if(user_gender == ""){
		swal("성별을 선택해주세요.").then(function() {
			$("#user_gender").focus();
		  });
		return false;
	}
	
	if(user_birth == ""){
		swal("생년월일을 선택해주세요.").then(function() {
			$("#user_birth").focus();
		  });
		return false;
	}
	
	//사용자가 선택한 생년월일을 JavaScript Date 객체로 변환합니다.
	var selectedDate = new Date(user_birth);
	// 현재 날짜를 가져옵니다.
	var currentDate = new Date();

	// 선택한 날짜가 현재 날짜보다 미래인지 확인합니다.
	if (selectedDate > currentDate) {
	    swal("미래 날짜는 선택할 수 없습니다.").then(function() {
	        $("#user_birth").focus();
	    });
	    return false;
	}
	
	if(rgemail1.length < 1){
		swal("이메일을 입력하세요.").then(function() {
			$('#registerEmail1').focus();
		  });
		return false;
	}
	
	if(rgemail2 == ""){
		swal("이메일을 선택하세요.").then(function() {
			$('#registerEmail2').focus();
		  });
		return false;
	}
	
	if(tel1 == ""){
		swal("전화번호를 선택하세요.").then(function() {
			$('#tel1').focus();
		  });
		return false;
	}
	
	if(tel2.length < 1){
		swal("전화번호를 입력하세요.").then(function() {
			$('#tel2').focus();
		  });
		return false;
	}
	
	if(tel3.length < 1){
		swal("전화번호를 입력하세요.").then(function() {
			$('#tel3').focus();
		  });
		return false;
	}
	
   if(user_location == ""){
		swal("거주지역을 선택하세요.").then(function() {
			$('#user_location').focus();
		  });
		return false;
	} 
	return true;
}

/*loginID 중복체크*/ //아이디정규식수정//
function loginIdCheck(){
	
	var data = {"loginID" : $("#registerId").val()};
	
	var idRules = /^[a-zA-Z](?=.*\d)[a-zA-Z0-9]{5,9}$/;
    var id = $("#registerId").val();

	/*callAjax시 로그인 여부 확인 하므로 ajax 함수 직접 작성*/
	$.ajax({
		url : '/check_loginID.do',
		type : 'post',
		data : data,
		dataType : 'text',
		async : true,
		success : function(data) {
			if($("#registerId").val()==""){
				console.log("입력 아이디 없음");
				swal("아이디를 입력해주세요.").then(function(){
					$("#registerId").focus();
				});
				$("#ckIdcheckreg").val("0");
			}
			 else if (data==1){
				console.log("아이디 있음");
				swal("중복된 아이디가 존재합니다.").then(function(){
					$("#registerId").val("");
					$('#idMatch').hide();
				});
				console.log(data);
				$("#ckIdcheckreg").val("0");
			} else if(!idRules.test($("#registerId").val())){
				swal('아이디는 영문자, 숫자 조합으로 6~20자리를 사용해야 합니다.').then(function(){
					$("#registerId").focus();
				});
				$("#ckIdcheckreg").val("0");
				return false;
			} else if(data == 0){
				console.log("아이디 없음");
				swal("사용할 수 있는 아이디 입니다.");
				$("#ckIdcheckreg").val("1");
			}
		}
	});
}

/*회원가입 버튼 아이디 중복 체크*/
function loginIdCheckComplete(){
	
	var data = {"loginID" : $("#registerId").val()}
	
	var idRules = /^[a-zA-Z](?=.*\d)[a-zA-Z0-9]{5,9}$/;
	var id = $("#registerId").val();

	/*callAjax시 로그인 여부 확인 하므로 ajax 함수 직접 작성*/
	$.ajax({
		url : '/check_loginID.do',
		type : 'post',
		data : data,
		dataType : 'text',
		async : false,
		success : function(data) {
			if (data == 1){
				$("#ckIdcheckreg").val("0");
				console.log("아이디 있음");
				return false;
			} else if(!idRules.test($("#registerId").val())){
				$("#ckIdcheckreg").val("0");
				return false;
			}
		}
	});
}


/*-------  이메일 입력방식 선택  ------*/

/*이메일 중복 체크*/ // data형식 수정
function emailCheck(){
	
	var data = {
		    "user_email": $("#registerEmail1").val() + "@" + $("#registerEmail2").val()
		};

	$.ajax({
		url : '/check_email.do',
		type : 'post',
		data : data,
		dataType : 'text',
		async : false,
		success : function(data) {
			if(data == 1){
				$("#ckEmailcheckreg").val("0");
				console.log("이메일 있음");
				console.log(data);
				return false;
			} else {
				$("#ckEmailcheckreg").val("1");
				console.log(data);
				console.log("이메일 없음");
			}
			
		}
	});
}

/* 회원가입  완료*/ //정규식 수정//
function CompleteRegister() {
	
	// 이메일과 전화번호를 묶어서 하나의 변수에 저장
	var email = $("#registerEmail1").val() + "@" + $("#registerEmail2").val();
	var tel1 = $("#tel1").val();
	var tel2 = $("#tel2").val();
	var tel3 = $("#tel3").val();

	var phoneNumber = tel1 + "-" + tel2 + "-" + tel3;
	
	console.log("phoneNumber"+phoneNumber)

	// 직렬화된 폼 데이터를 가져옴
	var formData = $("#RegisterForm").serialize();
	
    // 전화번호와 이메일을 폼 데이터에 추가
	formData += "&user_email=" + email + "&user_phone=" + phoneNumber;
	
	var param = formData;
	
    /*비밀번호 정규식*/
	var passwordRules =/^[A-Za-z](?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&+])[A-Za-z\d@$!%*?&+]{7,14}$/;
 	var password = $("#registerPwd").val();
 	
 	/*이메일 정규식*/
	var emailRules =/^[A-Za-z][a-zA-Z0-9]*[0-9]+[a-zA-Z0-9]*$/;
	var email = $("#registerEmail1").val();
	
	/*전화번호 정규식*/
	var tel2Rules =/^\d{4}$/;
	var tel2 = $("#tel2").val();
	
	var tel3Rules =/^\d{4}$/;
	var tel3 = $("#tel3").val();
	
	/* validation 체크 */ //전화번호2,3추가
	if(!RegisterVal()){
		return;
	}
		
	loginIdCheckComplete();
	emailCheck();
			 
	if (RegisterForm.ckIdcheckreg.value == "0"){
		swal("아이디 중복확인을 진행해주세요.").then(function() {
			$("#registerId").focus();
		  });
	} else if(!passwordRules.test($("#registerPwd").val())){
		swal('비밀번호는 영문자, 숫자, 특수문자 조합으로 8~15자리').then(function() {
			$("#registerPwd").focus();
		  });
		return false;
	} else if(!emailRules.test($("#registerEmail1").val())){
		swal("이메일 형식을 확인해주세요.\n영문자로 시작하는 영문자, 숫자 조합 5자 이상").then(function() {
			$("#registerEmail1").focus();
		  });
	} else if(RegisterForm.ckEmailcheckreg.value =="0"){
		swal("중복된 이메일이 존재합니다. 다시 입력해주세요.").then(function() {
			$("#registerEmail1").focus();
		  });
	} else if(!tel2Rules.test($("#tel2").val())){
		swal("전화번호를 확인해주세요.").then(function() {
			$("#tel2").focus();
         });
	}else if(!tel3Rules.test($("#tel3").val())){
		swal("전화번호를 확인해주세요.").then(function() {
			$("#tel3").focus();
	    });
	} else{
	
		var resultCallback = function(data) {
		fSaveRegister(data);
		}
	
	callAjax("/register.do", "post", "json", true, param, resultCallback);
	}

}

/* 회원 가입 저장 콜백함수 */
function fSaveRegister(data) {

	if (data.result == "SUCCESS") {
		alert(data.resultMsg);
		gfCloseModal();
	} else {
		alert(data.resultMsg);
	    return false;
	}
}

// 우편번호 api
/* function execDaumPostcode(q) {
	new daum.Postcode({
		oncomplete : function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수
			var extraAddr = ''; // 참고항목 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', '
							+ data.buildingName : data.buildingName);
				}
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('detailaddr').value = data.zonecode;
			document.getElementById("loginaddr").value = addr;
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("loginaddr1").focus();
		}
	}).open({
		q : q
	});
} */


/* 로그인 validation */
function fValidateLogin() {

	var chk = checkNotEmpty([ [ "EMP_ID", "아이디를 입력해 주세요." ],
			[ "EMP_PWD", "비밀번호를 입력해 주세요." ] ]);

	if (!chk) {
		return;
	}
	return true;
}

/* 로그인 */
function fLoginProc() {
	if ($("#cb_saveId").is(":checked")) { // 저장 체크시
		saveCookie('EMP_ID', $("#EMP_ID").val(), 7);
	} else { // 체크 해제시는 공백
		saveCookie('EMP_ID', "", 7);
	}

	// vaildation 체크
	if (!fValidateLogin()) {
		return;
	}

	var resultCallback = function(data) {
		//alert("login : " + JSON.stringify(data));
		fLoginProcResult(data);
	};

	callAjax("/loginProc.do", "post", "json", true, $("#myForm")
			.serialize(), resultCallback);
}

/* 로그인 결과 */
function fLoginProcResult(data) {

	console.log("login : " + JSON.stringify(data));
	
	var lhost = data.serverName;

	if (data.result == "SUCCESS") {
		location.href = "${CTX_PATH}/dashboard/dashboard.do";
	} else {

		$("<div style='text-align:center;'>" + data.resultMsg + "</div>")
				.dialog({
					modal : true,
					resizable : false,
					buttons : [ {
						text : "확인",
						click : function() {
							$(this).dialog("close");
							$("#EMP_ID").val("");
							$("#EMP_PWD").val("");
							$("#EMP_ID").focus();
						}
					} ]
				});
		$(".ui-dialog-titlebar").hide();
	}
}

/* 원팀 아이디 조회 모달창 실행 */
function findIdModal() {

	// 모달 팝업
	gfModalPop("#layer2");
	
}

//원팀 아이디 조회
function findID() {
	
	// 이메일 유효성 검사
    if (!validateEmail()) {
       return;
    } 
  
   alert("정말 조회하시겠습니까?");
   
    $.ajax({
		url : '/selectFindInfoId.do',
		type : 'post',
		data : $("#sendIdForm").serialize(),
		dataType : 'json',
		async : false,
		success: function(data) {
		    console.log(data); 
		    if (data != null && data.resultModel != null && data.resultModel.loginID != null) {
		        console.log(data);
		        alert("귀하의 아이디는: " + data.resultModel.loginID);
		        gfCloseModal();
		        resetForm(); // 폼 초기화
		        return false;
		    } else if (data != null && data.resultMsg != null) {
		        alert(data.resultMsg);
		        gfCloseModal();
		        resetForm(); // 폼 초기화
		    } else {
		       
		    }
		}
	}); 
}

//원팀 아이디 조회 초기화
function resetForm() {
    $("#sendIdForm")[0].reset(); 
}

//원팀 이메일 유효성 검사
function validateEmail() {
    var email = $("#findID").val();
    var emailRegex = /^[a-zA-Z][a-zA-Z0-9._%+-]{4,}@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (!emailRegex.test(email)) {
    	 swal("이메일 형식을 확인해주세요. 이메일은 영어, 숫자 조합 5자 이상입니다.").then(function() {
            $("#findID").focus();
        });
        return false;
    }
    return true;
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// 비밀번호 조회 모달창 실행 
function findPwdModal() {

	// 모달 팝업
	gfModalPop("#layer3");
	
}

//원팀 비밀번호 조회
function findPwd() {
	
	// 이메일 유효성 검사
    if (!validateEmailPwd()) {
       return;
    }  
	
  // 아이디 필드 값 확인
    var id = $("#findPwdID").val();
    if (id === "") {
        alert("ID를 입력하세요.");
        return;
    }
  
   alert("정말 조회하시겠습니까?");
   
    $.ajax({
		url : '/selectFindInfoPw.do',
		type : 'post',
		data : $("#sendPwdForm").serialize(),
		dataType : 'json',
		async : false,
		success: function(data) {
		    console.log(data); 
		    if (data != null && data.resultModel != null && data.resultModel.password != null) {
		        console.log(data);
		        alert("귀하의 임시 비밀번호는: " + data.resultModel.password);
		        alert("개인정보 수정에서 비밀번호 변경을 진행하세요.");
		        gfCloseModal();
		        resetPwdForm(); // 폼 초기화
		        return false;
		    } else if (data != null && data.resultMsg != null) {
		        alert(data.resultMsg);
		        gfCloseModal();
		        resetPwdForm(); // 폼 초기화
		    } else {
		       
		    }
		}
	}); 
}

//원팀 비밀번호 조회 초기화
function resetPwdForm() {
    $("#sendPwdForm")[0].reset(); 
}

//원팀 이메일 유효성 검사
function validateEmailPwd() {
    var email = $("#findPwd").val();
    var emailRegex = /^[a-zA-Z][a-zA-Z0-9._%+-]{4,}@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (!emailRegex.test(email)) {
    	 swal("이메일 형식을 확인해주세요. 이메일은 영어, 숫자 조합 5자 이상입니다.").then(function() {
            $("#findPwd").focus();
        });
        return false;
    }
    return true;
}

//아이디 체크
$(document).ready(function() {
    $('#registerId').keyup(function() {
        var id = $(this).val();
        var idRegex = /^[a-zA-Z](?=.*\d)[a-zA-Z0-9]{5,9}$/;
        
        if (id === '') {
            $('#idMatch').hide(); // 입력란이 비어있으면 메시지를 숨김
        } else {
            // 입력란이 비어있지 않은 경우에만 메시지를 보여줌
            if (idRegex.test(id)) {
                $('#idMatch').text('사용 가능한 아이디입니다.').css('color', 'green').show();
            } else {
                $('#idMatch').text('사용 불가능한 아이디입니다.').css('color', 'red').show();
            }
        }
    });
});

//비밀번호 체크
$(document).ready(function() {
    $('#registerPwd').keyup(function() {
        var registerPwd = $(this).val();
        var registerPwdRegex =/^[A-Za-z](?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&+])[A-Za-z\d@$!%*?&+]{7,14}$/;
        
        if (registerPwd === '') {
            $('#passwordMatch').hide(); // 입력란이 비어있으면 메시지를 숨김
        } else {

	        if (registerPwdRegex.test(registerPwd)) {
	            $('#passwordMatch').text('사용 가능한 비밀번호입니다.').css('color', 'green').show();
	        } else {
	            $('#passwordMatch').text('사용 불가능한 비밀번호입니다.').css('color', 'red').show();
	        }
        }
    });
}); 

//비밀번호 확인체크
$(document).ready(function() {
	$('#registerPwdOk').keyup(function() {
	    var password = $('#registerPwd').val();
	    var confirmPassword = $(this).val();
	    
	    if (registerPwdOk === '') {
	        $('#passwordOkMatch').hide(); // 입력란이 비어있으면 메시지를 숨김
	    } else {
	        if (password === confirmPassword) {
	            $('#passwordOkMatch').text('비밀번호가 일치합니다.').css('color', 'green').show();
	        } else {
	            $('#passwordOkMatch').text('비밀번호가 일치하지 않습니다.').css('color', 'red').show();
	        }
	    }
	});
});
</script>
</head>
<body>
<form id="myForm" action="" method="post">
	<div id="background_board" >
		<div class="login_form shadow" align="center">
		<div class="login-form-right-side">
                <div class="top-logo-wrap">
                <img src="${CTX_PATH}/images/admin/login/logo_img.png">
                </div>
                <h3>안되는 것이 실패가 아니라 포기하는 것이 실패다</h3>
                <p>
						성공은 실패의 꼬리를 물고 온다.
						지금 포기한 것이 있는가?<br>그렇다면 다시 시작해 보자.<br>
						안되는 것이 실패가 아니라 포기하는 것이 실패다.<br>
						포기한 순간이 성공하기 5분전이기 쉽다.<br> 실패에서 더 많이 배운다.<br>
						실패를 반복해서 경험하면 실망하기 쉽다. <br>하지만 포기를 생각해선 안된다.
						실패는 언제나 중간역이지 종착역은 아니다. <br>
               </p>
               <p>- 이대희, ‘1%의 가능성을 희망으로 바꾼 사람들’ 에서</p>
            </div>
		<div class= "login-form-left-side">
			<fieldset>
				<legend>로그인</legend>
				<p class="id">
					<label for="user_id">아이디</label> <input type="text" id="EMP_ID"
						name="lgn_Id" placeholder="아이디"
						onkeypress="if(event.keyCode==13) {fLoginProc(); return false;}"
						style="ime-mode: inactive;" />
				</p>
				<p class="pw">
					<label for="user_pwd">비밀번호</label> <input type="password"
						id="EMP_PWD" name="pwd" placeholder="비밀번호"
						onfocus="this.placeholder=''; return true"
						onkeypress="if(event.keyCode==13) {fLoginProc(); return false;}" />
				</p>
				<p class="member_info" style="font-size: 15px">
					<input type="checkbox" id="cb_saveId" name=""
						onkeypress="if(event.keyCode==13) {fLoginProc(); return false;}">
					<span class="id_save">ID저장</span> <br>
				</p>
				<a class="btn_login" href="javascript:fLoginProc();" id="btn_login"><strong>Login</strong></a>
				<br>
				<a href="javascript:fRegister();" id="RegisterBtn"
					name="modal"><strong>[회원가입]</strong></a> 
					<a href="javascript:findIdModal();"><strong>[아이디 찾기]</strong></a>
					<a href="javascript:findPwdModal();"><strong>[비밀번호 찾기]</strong></a>
			</fieldset>
			</div>
			
		</div>
	</div>
</form>
<!-- 모달팝업 -->


	<div id="layer1" class="layerPosition layerPop layerType2" style="width: 600px;">
      <form id="RegisterForm" action="" method="post">
	      <input type="hidden" name="action" id="action" value="">
	      <input type="hidden" name="ckIdcheckreg" id="ckIdcheckreg" value="0"/>
	      <input type="hidden" name="ckEmailcheckreg" id="ckEmailcheckreg" value="0"/>	
		<dl>
			<dt>
					<br>
					<br>
					<strong style="font-size:120%">&nbsp;&nbsp;&nbsp;&nbsp;[회원가입]</strong>&nbsp;&nbsp;&nbsp;&nbsp;<span class="font_red">*</span>필수입력
					<br>
			</dt>
			<dd class="content">
				<div class="btn_areaC">
				    <br>
					<br>
				</div>
				<!-- s : 여기에 내용입력 -->
				<table class="row">
					<caption>caption</caption>
					<colgroup>
						<col width="120px">
						<col width="*">
						<col width="120px">
						<col width="*">
						<col width="120px">					
					</colgroup>
						<tbody>
							<tr class="hidden">
								<td><input type="text" name="div_cd" id="div_cd" /></td>
								<td><input type="text" name="del_cd" id="del_cd" /></td>
								<td><input type="text" name="approval_cd" id="approval_cd" /></td>
							</tr>
							
							<tr>
							<th scope="row"  id="user_type_th">회원구분<span class="font_red">*</span></th>
								<td id="user_type_td">
								<select name="user_type" id="user_type" style="width: 128px; height: 28px;">
										<option value="" >선택</option>
										<option value="A">학생</option>
										<option value="D">강사</option>
										<option value="D">직원</option>
								</select>
								</td>
							</tr>
							
                          <tr>
								<th scope="row">아이디<span class="font_red">*</span></th>
								<td colspan="3">
								<input type="text" class="inputTxt p100"
									name="loginID" placeholder="영문자로 시작하는 영문자, 숫자 조합 6~10자리 "
									id="registerId" />
								<div id="idMatch"></div></td>
								<td><input type="button" value="중복확인"
									onclick="loginIdCheck()" style="width: 80px; height: 20px;" /></td>
							</tr>
							<tr>
								<th scope="row">비밀번호 <span class="font_red">*</span></th>
								<td colspan="3"><input type="password"
									placeholder="영문자로 시작하는 영문자, 숫자, 특수문자 조합 8~15자리 " class="inputTxt p100"
									name="password" id="registerPwd" />
									<div id="passwordMatch"></div></td>
							</tr>

							<tr>
								<th scope="row" >비밀번호 확인<span
									class="font_red">*</span></th>
								<td colspan="3"><input type="password"
									class="inputTxt p100" name="password" id="registerPwdOk" />
									<div id="passwordOkMatch"></div></td>
							</tr>
							
							<tr>
								<th scope="row" id="registerName_th">이름 <span class="font_red">*</span></th>
								<td id="registerName_td"><input type="text" class="inputTxt p100" name="name"
									id="registerName" /></td>
							
								<th scope="row" id="rggender_th">성별<span class="font_red">*</span></th>
								<td id="rggender_td">
								<select name="user_gender" id="user_gender" style="width: 128px; height: 28px;">
										<option value="" selected="selected">선택</option>
										<option value="M">남자</option>
										<option value="F">여자</option>
								</select></td>
							</tr>
							
							<tr id="birthday1">
								<th scope="row">생년월일 <span class="font_red">*</span></th>
								<td><input type="date" class="inputTxt p100"
									name="user_birth" id="user_birth" style="font-size: 15px" />
							</tr>
							
							<tr>
								<th scope="row">이메일<span class="font_red">*</span></th>
								<td colspan="4">
									<input type="text" class="mail"  id="registerEmail1"  placeholder="영문자, 숫자 조합 5자리 이상 " style="width: 180px; height: 28px;"/>
										&nbsp;&nbsp; @ &nbsp;&nbsp;
									<select id="registerEmail2" style="width: 128px; height: 28px;">
										<option value="">선택</option>
										<option value="naver.com">naver.com</option>
										<option value="daum.net">daum.net</option>
										<option value="gmail.com">gmail.com</option>
								</select>
								</td>
							</tr>
							
							<tr>
								<th scope="row">연락처<span class="font_red">*</span></th>
								<td colspan="5">
									<select id="tel1" style="width: 128px; height: 28px;">
									    <option value="">선택</option>
										<option value="010">010</option>
										<option value="011">011</option>
										<option value="016">016</option>
										<option value="017">017</option>
										<option value="018">018</option>
										<option value="019">019</option>
								   </select>
									 <b>&nbsp;&nbsp;-&nbsp;&nbsp;</b> 
									<input type="text" class="phone" id="tel2" placeholder="0000" style="width: 128px; height: 28px;" /> 
									 <b>&nbsp;&nbsp;-&nbsp;&nbsp;</b> 
									<input type="text" class="phone"  id="tel3" placeholder="0000" style="width: 128px; height: 28px;"/> 
								</td>
							</tr>
							
							<tr>
                               <th scope="row"  id="user_location_th">거주지역<span class="font_red">*</span></th>
								<td  id="user_location_td">
								<select name="user_location" id="user_location" style="width: 128px; height: 28px;">
										<option value="">선택</option>
										<option value="서울">서울</option>
										<option value="경기">경기</option>
										<option value="강원">강원</option>
										<option value="충청">충청</option>
										<option value="전라">전라</option>
										<option value="경상">경상</option>
										<option value="제주">제주</option>
								</select>
								</td>
							</tr> 
				</table>
				
				<div class="btn_areaC mt30">
					<a href="javascript:CompleteRegister();" class="btnType blue"
						id="RegisterCom" name="btn"> <span>회원가입</span></a> <a 
						href="javascript:fcancleModal()" class="btnType gray" id="btnCloseLsmCod" name="btn"><span>취소</span></a>
				</div>
			</dd>
		</dl>
		<a href="" class="closePop"><span class="hidden">닫기</span></a>
	</form>	
	</div>


<!-- 아이디 조회 모달 -->
<form id="sendIdForm" action="" method="post">
	<div id="layer2" class="layerPop layerType2" style="width: 400px; height:330px; text-align: center;">
		<dl>
			<dt>
				<strong>아이디 찾기</strong>
			</dt>
			<dd>
				<div class="btn_areaC mt30">
					<div class="font_red" style="margin-bottom: 10px;">
						<h1>* 회원가입 시 입력한 이메일을 입력해주세요. *</h1>
					</div>
					<div style="margin-bottom: 1px;">
						<input type="text"  name="user_email" placeholder="회원가입 시 입력한 이메일을 입력해주세요." id="findID" style="height:28px; width:300px;">
					</div>
					<div style="text-align: center; margin: 20px;">
						<a href="javascript:findID();" class="btnType blue " id="btnUpdatePass"><span>조회</span></a>
						<a href="javascript:gfCloseModal();" class="btnType blue"><span>닫기</span></a>
					</div>
					
				</div>
			</dd>
			</dl>
	</div>
</form>
<!-- 아이디 조회 모달 끝 -->

<!-- 비밀번호 조회 모달 -->
<form id="sendPwdForm" action="" method="post">
	<div id="layer3" class="layerPop layerType2" style="width: 400px; height:330px; text-align: center;">
		<dl>
			<dt>
				<strong>비밀번호 찾기</strong>
			</dt>
			<dd>
				<div class="btn_areaC mt30">
					<div class="font_red" style="margin-bottom: 10px;">
						<h1>* 회원가입 시 입력한 ID, 이메일을 입력해주세요. *</h1>
					</div>
					<div style="margin-bottom: 1px;">
						<input type="text"  name="loginID" placeholder="회원가입 시 입력한 ID을 입력해주세요." id="findPwdID" style="height:28px; width:300px;">
					</div>
					<br>
					<div style="margin-bottom: 1px;">
						<input type="text"  name="user_email" placeholder="회원가입 시 입력한 이메일을 입력해주세요." id="findPwd" style="height:28px; width:300px;">
					</div>
					<div style="text-align: center; margin: 20px;">
						<a href="javascript:findPwd();" class="btnType blue " id="btnUpdatePass"><span>조회</span></a>
						<a href="javascript:gfCloseModal();" class="btnType blue"><span>닫기</span></a>
					</div>
				</div>
			</dd>
		</dl>
	</div>
</form>
<!-- 비밀번호 조회 모달 끝 -->

</body>
</html>
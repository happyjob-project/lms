<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- 학생이 제출한 과제 자세히 보기 -->

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<style type="text/css">
	</style>
	
	<script>
		function showStudentCon(user_no, task_id ) {
			var param = {
					user_no : user_no,
					task_id : task_id
				};
			       
		    var resultCallback = function(param) {
		    	$('#layer1').empty().append(param);
		        gfModalPop("#layer1");
		    };
		   	callAjax("/tut/studentProjectCon.do", "post", "text", true, param, resultCallback);
		}
		
		function downloadHwk(url, fname, task_id, user_no){
	     $("#task_id").val(task_id);
	     $("#user_no").val(user_no);
		
	    
		var params = "<input type='hidden' name='task_id' value='"+ task_id +"' />";
           params += "<input type='hidden' name='user_no' value='"+ user_no +"' />";
           params += "<input type='hidden' name='sub_path' value='"+ url +"' />";
           params += "<input type='hidden' name='sub_filename' value='"+ fname +"' />";
           $("<form action='downloadHwk.do' method='post' id='fileDownload'>" + params + "</form>").appendTo('body').submit().remove();
	        alert("다운로드 성공");
	        }
// 			$.ajax({
// 				success : function(){
// 					var params = "";
// 					params += "<input type='hidden' name='sub_path' value='"+ url+"' />";
// 					params += "<input type='hidden' name='sub_filename' value='"+ fname +"' />";
// 					$("<form action='/tut/downloadHwk.do' method='post' id='fileDownload'>" + params+ "</form>").appendTo('body').submit().remove();
// 					alert("다운로드 성공");
// 				}
// 			});
// 		}
	</script>
</head>




<body>
<dl>
	<dt>
    	<strong>과제 내용</strong>
    </dt>
    <dd class="content">
    	<table class="col2 mb10" id="adv_info">
        	<caption>caption</caption>
            	<colgroup>
                	<col width="60%">
                	<col width="13%">
                	<col width="14%">
                	<col width="13%">
                </colgroup>
				
			<thead>
				<tr>
					<th>내용</th>
					<th>제출일</th>
					<th>파일 이름</th>
					<th>파일 받기</th>
				</tr>
			</thead>
			
            <tbody id="showStudentCon">
	                <tr>
	                	<td>${studentCon.sub_content}</td>
	                	<td>${studentCon.sub_date}</td>
	                	<td>${studentCon.sub_filename}</td>
	                	<td>
	                		<c:if test="${not empty studentCon.sub_path}">
	                		<a href="javascript:downloadHwk('${studentCon.sub_path}', '${studentCon.sub_filename}', '${studentCon.task_id}', '${studentCon.user_no}');">받기</a>          		
	                		</c:if>
	                	</td>
	                </tr> 
           	</tbody>
          </table>
     </dd>
</dl>
<a href="" class="closePop"><span class="hidden">닫기</span></a>
</body>
</html>
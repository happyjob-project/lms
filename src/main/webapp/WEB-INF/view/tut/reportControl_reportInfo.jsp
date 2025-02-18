<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- 강사가 제출한 과제 목록 -->

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />

		<script>
		function updateProject(task_id, lec_no){
			var data = {
				"task_id" : task_id,
				"lec_no" : lec_no
			};
			var resultCallback = function(data) {
			    $('#layer1').empty().append(data);
			    gfModalPop("#layer1");
			};
			callAjax("/tut/newInputModal.do", "post", "text", true, data, resultCallback);
		}
		

		function downloadTutor(url, fname, task_id){
		     $("#task_id").val(task_id);
		    
			var params = "<input type='hidden' name='task_id' value='"+ task_id +"' />";
	            params += "<input type='hidden' name='task_path' value='"+ url +"' />";
	            params += "<input type='hidden' name='task_filename' value='"+ fname +"' />";
	            $("<form action='downloadTutor.do' method='post' id='fileDownload'>" + params + "</form>").appendTo('body').submit().remove();
		        alert("다운로드 성공");


// 		    $.ajax({
// 		    	dataType: "json",
// 		    	url: "/tut/downloadTutor.do",
// 		    	type: "POST",
// 		        data : {"task_id" : task_id},
// 		        success : function(){
// // 				    var task_id = $("#task_id").val();
// // 		            var params ="<input type='hidden' name='task_id' value='"+ task_id +"' />";
// 					var params ="";
// 		            params += "<input type='hidden' name='task_path' value='"+ url +"' />";
// 		            params += "<input type='hidden' name='task_filename' value='"+ fname +"' />";
// 		            $("<form action='/tut/downloadTutor.do' method='post'>" + params + "</form>").appendTo('body').submit().remove();
// 		            alert("다운로드 성공");
// 		        }
// 		    });
		}
	</script>
</head>




<body>
<dl>
	<dt>
    	<strong>과제</strong>
    </dt>
    <dd class="content">
    	<table class="col2 mb10" id="adv_info">
        	<caption>caption</caption>
            	<colgroup>
                	<col width="20%">
                	<col width="40%">
                	<col width="10%">
                	<col width="10%">
                	<col width="20%">
                </colgroup>
				
			<thead>
				<tr>
					<th>제목</th>
					<th>내용</th>
					<th>제출일</th>
					<th>마감일</th>
					<th>파일</th>
				</tr>
			</thead>
			
            <tbody>
	                <tr>
	                	<td>${projectInfo.task_name}</td>
	                	<td>${projectInfo.task_content}</td>
	                	<td>${projectInfo.task_start}</td>
	                	<td>${projectInfo.task_end}</td>
	                	<td>
	                		<c:if test="${not empty projectInfo.task_filename}">
				<a href="javascript:downloadTutor('${projectInfo.task_path}', '${projectInfo.task_filename}', '${projectInfo.task_id}');">${projectInfo.task_filename}</a>
	                		</c:if>
	                	</td>
	                </tr> 
           	</tbody>
          </table>
     
     	<p style="text-align:center;">
     		<a href="javascript:updateProject('${projectInfo.task_id}', '${projectInfo.lec_no}');" class="btnType blue" name="btn" id="btn"><span id="">수정</span></a>
     	</p>
     </dd>
</dl>
<a href="" class="closePop"><span class="hidden">닫기</span></a>
    <input type="hidden"  id="task_id"  name="task_id"  value="" />

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body, html {
	width: 100%;
	height: 100%;
	margin: 0;
	font-family: "微软雅黑";
}

#allmap {
	height: 400px;
}

#r-result {
	width: 100%;
	font-size: 14px;
}
</style>
<link rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<title>车辆位置信息</title>
</head>

<body>
	
	<c:choose>
		<c:when test="${loras.size() == 0 }">
			<h2>没有数据</h2>
		</c:when>
		<c:otherwise>
			

	
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<th>编号</th>
			<th>汽车编号</th>
			<c:forEach var="i" begin="1" end="${columnSize }">
				<th>序列${i }</th>
			</c:forEach>
		</tr>
		<c:forEach items="${loras}" var="lora">
			<tr>
				<td>${lora.id }</td>
				<td>${lora.car_id }</td>
				<c:forEach var="i" begin="1" end="${columnSize }">

					<td>${fn:substring(lora.lora_id, (i-1)*12, i*12)}</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
	<div style="text-align: center;">
		<c:if test="${maxPage>1 }">
			<ul class="pagination pagination-lg" style="margin-top: 0px">
				<c:if test="${currentPage!=1 }">
					<li><a href="index.do?page=1">&laquo;</a></li>
				</c:if>
				<c:forEach items="${pages }" var="px">
					<c:choose>
						<c:when test="${currentPage!=px }">
							<li><a href="index.do?page=${px }">${px }</a></li>
							<a href=""> </a>
						</c:when>
						<c:otherwise>
							<li class="active"><a href="#">${px }</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${currentPage!=maxPage }">
					<li><a href="index.do?page=${maxPage }">&raquo;</a></li>
				</c:if>
			</ul>
		</c:if>
	</div>

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" id="modal-dialog" style="width: 800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">车辆位置</h4>
				</div>
				<div class="modal-body" id="allmap" style="height: 400px;"></div>
				<div class="modal-footer" id="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<div class="modal fade" id="myModalMessage" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" id="modal-dialogMessage"
			style="width: 400px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">提示信息</h4>
				</div>
				<div class="modal-body" id="modal-bodyMessage"
					style="text-align: center;">
					<h5>没有对应信息</h5>
				</div>
				<div class="modal-footer" id="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<!-- /.modal -->
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=fyZ6bTEiwSsmYCrc0yyMtTIUBvweRksb"></script>
<script type="text/javascript">
	$(function() {
		$("tr").click(function() {
			var car_id = $(this).children().first().next().html();
			$.getJSON("findGps.do", {
				car_id : car_id
			}, function(result) {
				if (result.id == 0) {
					$('#myModalMessage').modal();
				} else {
					theLocation(result.gps_longitude, result.gps_latitude);
					$('#myModal').modal();
				}
			});

		});
	});
</script>
	<script type="text/javascript">
		// 百度地图API功能
		var map = new BMap.Map("allmap");
		map.centerAndZoom(new BMap.Point(116.331398, 39.897445), 13);
		map.enableScrollWheelZoom(true);

		// 用经纬度设置地图中心点
		function theLocation(longitude, latitude) {
			map.clearOverlays();
			var new_point = new BMap.Point(longitude, latitude);
			setTimeout(function() {
				var convertor = new BMap.Convertor();
				var pointArr = [];
				pointArr.push(new_point);
				convertor.translate(pointArr, 1, 5, translateCallback)
			}, 1000);

		}

		translateCallback = function(data) {
			if (data.status === 0) {
				var marker = new BMap.Marker(data.points[0]);
				map.addOverlay(marker);
				map.setCenter(data.points[0]);
			}
		}
	</script>

		</c:otherwise>
	</c:choose>
</body>

</html>
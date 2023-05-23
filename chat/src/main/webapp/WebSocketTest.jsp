<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WebSocket Basic</title>
<style>
#screen {
	width:500px;
	height:500px;
	background-color:blue;
	color:white;
}
</style>
</head>

<body>
	<h1 id = "state">websocket test</h1>
	<!-- 버튼이 눌리면 웹 소켓을 통해서 전송하는데  sendmessage함수를 이용한다. -->
	<input id=send type=text><input type="button" value="전송" id="button" onclick=sendmessage()>
	<div id="screen"></div>
	<script>
	/*
	WebSocket 오브젝트 생성 (자동으로 접속 시작한다. - onopen 함수 호출)
	프로토콜의 요청은 「ws://~」로 시작합니다.서블릿의 형태로 접속을 한다.
	*/
		var websocket = new WebSocket("ws://localhost:8080/chat/wsocket");
		//	아래는 웹 소켓에서 사용하는 4가지 이벤트들이다.
		websocket.onopen = function(message){
			document.getElementById("state").innerHTML = "소켓 시작"; 
		}

		websocket.onclose = function(message){}

		websocket.onerror = function(message){}
		//	onmessage는 외부에서 들어온 데이터를 받아서 처리를 하는 이벤트이다.
		websocket.onmessage = function(message){
			var listContainer = document.getElementById("screen");
			var newListItem = document.createElement("li");
			newListItem.textContent = message.data;
			listContainer.appendChild(newListItem);
		}
/*
		var content = "";
		var id = "";
		var output_message = [];
		var count = 0;
		websocket.onmessage = function(message){
			count ++; // message는 홀수로 id는 짝수
			if (count/2 == 0){
				output_message[0] = message.data;
			}else {
				output_message[1] = message.data;
			}
			
		}
		console.log(output_message[0] + output_message[1]);
		if (output_message[1] != ''){
			var listContainer = document.getElementById("screen");
			
			// 새로운 요소 생성
			var newListItem = document.createElement("li");
			newListItem.textContent =  output_message[0] + output_message[1]; 

			// 새로운 요소를 부모 요소에 추가
			listContainer.appendChild(newListItem);
			output_message[0] = '';
			output_message[1] = '';
		}else {
			console.log("대기중 ...");
		}
*/
		
		function sendmessage()
		{
			//	메시지 박스에 있는 데이터를 전송한다.
			msg = document.getElementById("send").value;
			websocket.send(msg);
		}

	</script>
</body>
</html>
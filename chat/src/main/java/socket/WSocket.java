package socket;

import java.util.Collections;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/wsocket")
public class WSocket 
{
	private static final Set<Session> sessions = Collections.synchronizedSet(
			new java.util.HashSet<Session>()
			);
	
	/*
	//	onopen 이벤트가 호출 되면 실행되는 함수
	@OnOpen
	public void handleOpen()
	{
		System.out.println("클라이언트가 접속했습니다.");
	}
	*/
	//	onopen 이벤트가 호출 되면 실행되는 함수
	@OnOpen
	public void handleOpen(Session session)
	{
		System.out.println("클라이언트가 접속했습니다.");
		System.out.println("session id : " +session.getId());
		sessions.add(session);
	}
	
	/*
	//	onclose 이벤트가 호출 되면 실행되는 함수
	@OnClose
	public void handleClose()
	{
		System.out.println("클라이언트가 연결을 해제했습니다.");
	}
	*/
	//	onclose 이벤트가 호출 되면 실행되는 함수
	@OnClose
	public void handleClose(Session session)
	{
		System.out.println(session.getId() + "클라이언트가 연결을 해제했습니다.");
		
		//	세션을 닫는다.
		sessions.remove(session);
	}
	
	//	onerror 이벤트가 호출 되면 실행되는 함수
	@OnError
	public void handleError(Throwable t)
	{
		t.printStackTrace();
	}
	/*
	//	onmessage 이벤트가 호출 되면 실행되는 함수
	@OnMessage
	public String handleMessage(String message)
	{
		//	서버가 받는다.
		System.out.println("클라이언트가 보내온 메시지 : ");
		System.out.println(message);
		
		return "메시지 수신 완료";
	}
	*/
	
	//	onmessage 이벤트가 호출 되면 실행되는 함수
	@OnMessage
	public void handleMessage(String message, Session session)
	{
		//	서버가 받는다.
		System.out.println("클라이언트가 보내온 메시지 : ");
		System.out.println(message);
		
		//String id = this.sendAll(session, message);
		this.sendAll(session, message);
		try {
			final Basic basic = session.getBasicRemote();
			basic.sendText("나 : "+message);
		}catch(Exception e) {}
		//return id;
	}
	
	public void sendAll(Session session, String message)
	{
		System.out.println(session.getId() + ":" +message);
		
		try {
			int i = 1; // 현재 인원수 (서버가 켜져있는 동안 id로 사용할 수 있음)
			//	웹 소켓에 연결되어 있는 모든 아이디를 찾는다.	
			for (Session s : WSocket.sessions) 
			{
				//System.out.println("=====id====");
				//System.out.println(++i);
				if (!s.getId().equals(session.getId()))
				{
					s.getBasicRemote().sendText(session.getId() + ":" +message);
					//s.getBasicRemote().sendText("ms"+message);
				}
			}
			//System.out.println("===========");

		}catch(Exception e) {e.printStackTrace();}
		//return session.getId() + ":" +message;
	}
}
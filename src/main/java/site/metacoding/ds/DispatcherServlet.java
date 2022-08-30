package site.metacoding.ds;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
// /붙이면 welcome-file때문에 안불러짐
//서버저장하고 재시작해야함
public class DispatcherServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	//요청이 올 때는 분기를 시켜야 할거 아냐 어떤 서비스를 때려야 할지

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	//무슨 요청이 들어와도 여기로 모임
	
	private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doProcess 요청됨");
		String httpMethod = req.getMethod();
		System.out.println(httpMethod);
		//이게 헤더값에 다 있어
		
		String indentifier = req.getRequestURI();
		System.out.println(indentifier);
		
		//두메서드를 가지고 라우팅을 할 수 있음
		
		//공통 로직 처리
		
		//HelloController
		
		UserController c = new UserController();
		if(indentifier.equals("/join")) {
			c.join();
		}else if(indentifier.equals("/login")) {
			c.login();
		}else {
			System.out.println("잘못된 요청입니다.");
			
			//문제 : 서로협업해서 때리는건 어렵지않아
			//문제 : 이상태로는 다른 Controller를 만들어도 동작하지 않음
			//문제 : 
		}
	}
}

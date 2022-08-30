package site.metacoding.ds;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;

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
		//System.out.println("doProcess 요청됨");
		String httpMethod = req.getMethod();
		//System.out.println(httpMethod);
		//이게 헤더값에 다 있어
		
		String identifier = req.getRequestURI();
		//System.out.println(identifier);
		
		//두메서드를 가지고 라우팅을 할 수 있음
		
		//공통 로직 처리
		
		//HelloController
		
		UserController c = new UserController();
		
		//Method[] methodList = UserController.class.getDeclaredMethods();
		//class.getDeclaredMethods() 그 클래스에 정의된 메소드들을 반환
		Method[] methodList = c.getClass().getDeclaredMethods();
		//Method-java.lang.reflect(이 메서드가 리플렉트라고 하는 라이브러리임)
		for (Method method : methodList) {
			//type(각 루프에서 나오는 타입) var(사용할 변수명) : iterate(루프를 돌릴 객체)
			//methodList에서 0번지 method에 들어가고 실행,
			//그다음 1번지가 method에 들어가고 실행
			//1. 실행될 때(runtime시) userController에 있는 모든 메서드들을 찾아서
			//2. methodList에 담음
			//System.out.println(method.getName());
			//-모든 메서드들을 찾는게 아니라 어노테이션이 붙은 메서드를 찾아본다.
			Annotation annotation = method.getDeclaredAnnotation(RequestMapping.class);
			//해당 메서드에 어노테이션이 걸려있는지 찾아볼거임
			RequestMapping requestMapping = (RequestMapping) annotation;
			//annotation은 지금 Annotation으로 RequestMapping의 부모라 다운캐스팅해줌
			//method.getDeclaredAnnotation(RequestMapping.class)입장에서는 return 타입이 requestMapping인지
			//모르기 때문에 return 타입을 Annotation으로 주기 때문
			//제네릭으로해서 <RequestMapping>으로 했다면 타입을 알건데
			
			//리플렉션 개념 : 런타임시 그 클래스를 분석하는 기술
			//분석하면서 annotation의 value값을 찾는게 목적
			
			try {
				//System.out.println(requestMapping.value());
				//RequestMapping안붙은 value 못찾아도 되니까 try catch로 잡아줌
				if(identifier.equals(requestMapping.value())){
					//uri(String<-equals사용)이=requestMapping.value와 같으면 
					method.invoke(c);
					//method.invoke(호출하다) (c, methodList)
					//c : 메서드를 실행할 객체
					//두번째 파라미터 : 해당 메서드의 패러미터 값 전달 가능
					//c라는(heap공간에 있는) 메서드를 때림
					//파라매터 넣는 부분은 메서드에 파라매터가 없어서 지워요
				
					//(오류뜸)-> invoke : new를 하려는데 new를 못하겠다는 뜻
					//UserController가 new되지 않고 c가 new되어서 그런거임 UserController->c.getClass()로 수정
					//annotation은 new해서 실행시키는게 아니고 클래스가 실행될 때 분석이 되므로
					//클래스가 new되지 않으면=메모리에 뜨지 않으면 분석이 안 됨
					//그냥 파일만 분석하고 싶으면 방금처럼 해도 되는데
					//실제로 메모리에 뜬 파일을 분석하려면 이 레퍼런스를 사용해야 함 
				}
			} catch (Exception e) {
				System.out.println(method.getName()+"은 어노테이션이 없습니다");
			}
			
		}

		// 이러면 Controller에 무슨 메서드를 만들어도 RequestMapping을 걸면
		// 다 실행시킬 수 있음
		
		//동적분석 : 니가 뭘 만들지 모르겠으니까 내가 어노테이션 몇 개 준비해놨거든
		//위에다가 좀 걸어만 줘 내가 알아서 때려줄게! 이렇게 설계되어 있는 것
		
		//Reflection이란?
		//구체적인 클래스 타입을 알지 못해도 그 클래스의 메소드, 타입, 변수들에 접근할 수 있도록 해주는 자바 API

		//DS이 아무객체나 다 던져줄까? ㄴㄴ request/response (model)만 던져줄 수 있다고
		//request 안에 있는 모든 객체는 다 던져줄 수 있어
		//request 안에 session도 받을 수 있음
	}
}


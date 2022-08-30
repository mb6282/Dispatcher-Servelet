package site.metacoding.ds;

public class UserController {
	@RequestMapping("/join")
	public void join() {
		System.out.println("join 호출됨");
	}
	
	@RequestMapping("/login")
	public void login() {
		System.out.println("login 호출됨");
	}
	
	public void joinForm() {
		System.out.println("joinForm 호출됨ㅌ");
	}
	
	//이제부터 런타임시에 분석할거임
	//DS는 @RequestMapping을 보고 메서드를 때림
}

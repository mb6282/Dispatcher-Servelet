package site.metacoding.ds;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// [] =  {1,2,3} / {} 이게 배열<-
@Target({ElementType.METHOD}) //메서드에서만 동작하게 함 (메서드, 클래스, 필드(변수) 등 넣을 수 있음)
@Retention(RetentionPolicy.RUNTIME)// 동작 타임 결정 : RUNTIME(런타임시), SOURCE(컴파일시)
//RUNTIME시에만 실행됨-> 코드 저장할 때는 실행될 필요없고 request받았을 때 실행되면 되므로
public @interface RequestMapping {
	String value(); //value 메서드 고정, 키값
	//value여야만 함
	//value로 적어야 어노테이션()안에 들어갈 값이 value라는 메서드로 호출됨
}

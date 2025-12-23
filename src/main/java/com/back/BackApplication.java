package com.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BackApplication {

	private BackApplication() {
	}

	/**
	 * JEP 477: Modifier 'public' is redundant for 'main' method on Java 25
	 * <p>
	 * 새로운 자바 실행 프로토콜은 main 메서드의 선언에 있어 훨씬 더 높은 유연성을 허용한다.<br>
	 * • 접근 제어자 완화: 이제 자바 실행기(launcher)는 public뿐만 아니라 protected 또는 기본(package) 접근 수준을 가진 main 메서드도 찾아 실행할 수 있다.<br>
	 * • 매개변수 및 static 생략 가능: String[] args 매개변수가 없는 main 메서드도 허용되며, 심지어 static 수식어가 없는 인스턴스 main 메서드도 실행 가능하다.(요건 아직 안되는 듯) <br>
	 * • 실행 순서: 실행기는 먼저 String[] 매개변수가 있는 main 메서드를 찾고, 없으면 매개변수가 없는 main 메서드를 선택합니다. 만약 선택된 메서드가 인스턴스 메서드라면, 인자가 없는 생성자를 호출하여 객체를 생성한 뒤 메서드를 실행한다.<br>
	 * </p>
	 * <p>
	 * 어떻게 이게 가능한가? <br>
	 *	1. 자바 실행기(Launcher)의 실행 프로토콜 확장<br>
	 * 가장 큰 기술적 변화는 자바 가상 머신(JVM)을 시작하는 java 실행기(launcher)의 동작 방식이 더 유연해진 것입니다. 기존에는 public static void main(String[] args)라는 엄격한 시그니처만 찾았으나, 이제는 다음과 같은 순서로 실행할 메서드를 검색합니다.<br>
	 * • 메서드 선택 우선순위:<br>
	 *     1. String[] 매개변수가 있는 main 메서드를 먼저 찾습니다.<br>
	 *     2. 없다면, 매개변수가 없는 main 메서드를 찾습니다.<br>
	 * • 접근 제어자 허용 범위 확대: 이제 실행기는 public뿐만 아니라 protected나 기본(package) 접근 수준의 main 메서드도 호출할 수 있도록 설계되었습니다.<br>
	 * • 인스턴스 메서드 지원: 선택된 main 메서드가 static이 아닐 경우(인스턴스 메서드일 경우), 실행기는 해당 클래스의 인자가 없는 생성자(zero-parameter constructor)를 호출하여 객체를 생성한 뒤 그 객체에서 main 메서드를 실행합니다.<br>
	 * 2. 컴파일러의 '암시적 클래스' 처리<br>
	 * 소스 파일에 class 선언이 없어도 프로그램이 작동하는 이유는 자바 컴파일러가 이를 **'암시적으로 선언된 클래스(Implicitly Declared Class)'**로 해석하기 때문입니다.<br>
	 * • 자동 클래스 생성: 컴파일러가 클래스 선언 없이 메서드나 필드만 있는 소스 파일을 발견하면, 이를 자동으로 final이며 Object를 상속받는 최상위 클래스의 본문으로 간주합니다.<br>
	 * • 이름 없는 패키지와 모듈: 이 암시적 클래스는 이름이 없기 때문에 다른 클래스에서 참조할 수 없으며, **이름 없는 패키지(unnamed package)**와 **이름 없는 모듈(unnamed module)**에 소속됩니다.<br>
	 * • 동일한 바이트코드 구조: 기술적으로 이는 자바 언어의 기본 구조(모든 코드는 클래스 내에 존재해야 함)를 파괴하는 것이 아니라, 단지 개발자가 써야 할 코드를 컴파일러가 대신 채워주는 것에 가깝습니다.<br>
	 * 3. 자동 임포트(Automatic Imports) 메커니즘<br>
	 * 번거로운 import 구문 없이도 println 등을 바로 쓸 수 있는 것은 컴파일러가 특정 라이브러리를 자동으로 연결해 주기 때문입니다.<br>
	 * • java.io.IO 정적 임포트: 모든 암시적 클래스에는 import static java.io.IO.*; 구문이 자동으로 추가된 것처럼 동작하여 println, readln 등의 메서드를 즉시 사용할 수 있습니다.<br>
	 * • java.base 모듈 자동 임포트: java.util, java.math 등 자바의 가장 기본적인 클래스들이 포함된 java.base 모듈의 모든 공용 클래스들도 별도의 선언 없이 사용할 수 있도록 자동으로 임포트됩니다<br>
	 * </p>
	 */
	static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}

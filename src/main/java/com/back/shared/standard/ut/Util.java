package com.back.shared.standard.ut;

public class Util {
	/**
	 * 리플렉션 관련 유틸리티 기능을 제공하는 내부 클래스
	 */
	public static class reflection {
		/**
		 * 리플렉션을 사용하여 객체의 특정 필드 값을 강제로 설정
		 * <p>
		 * 필드가 private으로 선언되어 있어도 접근하여 값을 변경
		 * </p>
		 *
		 * @param obj 대상 객체
		 * @param fieldName 값을 설정할 필드명
		 * @param value 설정할 값
		 * @throws RuntimeException 필드를 찾을 수 없거나 접근할 수 없는 경우 발생
		 */
		public static void setField(Object obj, String fieldName, Object value) {
			try {
				var field = obj.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(obj, value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}

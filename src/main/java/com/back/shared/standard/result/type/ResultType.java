package com.back.shared.standard.result.type;

public interface ResultType {
	String resultCode();

	String message();

	default <T> T getData() {
		return null;
	}
}

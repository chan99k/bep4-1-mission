package com.back.global.rsdata;

import com.back.shared.standard.result.type.ResultType;

public record RsData<T>(String resultCode, String message, T data) implements ResultType {
	public RsData(String resultCode, String msg) {
		this(resultCode, msg, null);
	}
}

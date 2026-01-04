package com.back.shared.post.event;

import com.back.shared.standard.model.type.CanGetModelTypeCode;

public sealed interface PostEvent extends CanGetModelTypeCode permits PostCommentCreated, PostCreated {
	@Override
	default String getModelTypeCode() {
		return "Post";
	}
}

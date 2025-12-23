package com.back.shared.post.event;

public sealed interface PostEvent permits PostCommentCreated, PostCreated {
}

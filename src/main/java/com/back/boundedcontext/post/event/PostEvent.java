package com.back.boundedcontext.post.event;

public sealed interface PostEvent permits PostCommentCreated, PostCreated {
}

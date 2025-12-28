package com.back.boundedcontext.post.in;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.boundedcontext.post.app.PostFacade;
import com.back.shared.post.dto.PostDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("post/api/v1")
public class ApiV1PostController {
	private final PostFacade postFacade;

	@GetMapping("/post/")
	public List<PostDto> getItems() {
		return postFacade.findByOrderByIdDesc().stream()
			.map(PostDto::from)
			.toList();
	}

	@GetMapping("/posts/{id}")
	public PostDto getItem(@PathVariable int id) {
		return postFacade.findById(id)
			.map(PostDto::from)
			.orElseThrow();
	}
}

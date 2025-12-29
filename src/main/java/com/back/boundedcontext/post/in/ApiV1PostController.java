package com.back.boundedcontext.post.in;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
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

	// TODO: Facade에서 DTO 변환하도록 이동하여 Controller의 @Transactional 제거
	@GetMapping("/posts")
	@Transactional(readOnly = true)
	public List<PostDto> getItems() {
		return postFacade.findByOrderByIdDesc();
	}

	@GetMapping("/posts/{id}")
	@Transactional(readOnly = true)
	public PostDto getItem(@PathVariable int id) {
		return postFacade.findById(id).map(PostDto::from).orElseThrow();
	}
}

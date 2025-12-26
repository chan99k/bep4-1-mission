package com.back.shared.member.event;

import com.back.shared.member.dto.MemberDto;

public record MemberModified(MemberDto member) implements MemberEvent {
}

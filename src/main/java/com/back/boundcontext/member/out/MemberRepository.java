package com.back.boundcontext.member.out;

import com.back.boundcontext.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

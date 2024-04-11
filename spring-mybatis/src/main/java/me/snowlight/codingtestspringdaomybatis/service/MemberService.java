package me.snowlight.codingtestspringdaomybatis.service;

import lombok.RequiredArgsConstructor;
import me.snowlight.codingtestspringdaomybatis.controller.ReqMember;
import me.snowlight.codingtestspringdaomybatis.model.Member;
import me.snowlight.codingtestspringdaomybatis.model.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Map<Integer, String>> getTest() {
        List<Map<Integer, String>> test = memberRepository.getTest();
        System.out.println(test);
        return test;
    }

    public List<Member> getByNameAndAge(ReqMember request) {
        return memberRepository.getByNameAndAge(request.toCommand());
    }
}

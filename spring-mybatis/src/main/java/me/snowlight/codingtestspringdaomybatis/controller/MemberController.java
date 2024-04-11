package me.snowlight.codingtestspringdaomybatis.controller;

import lombok.RequiredArgsConstructor;
import me.snowlight.codingtestspringdaomybatis.model.Member;
import me.snowlight.codingtestspringdaomybatis.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/test")
    public String test(Model model) {
        System.out.println("start");
        List<Map<Integer, String>> test = memberService.getTest();

        model.addAttribute("list", test);

        return "content/main";
    }

    @GetMapping("/member")
    @ResponseBody
    public List<Member> member(ReqMember request) {
        return memberService.getByNameAndAge(request);
    }
}


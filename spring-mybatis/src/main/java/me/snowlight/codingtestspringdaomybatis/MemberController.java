package me.snowlight.codingtestspringdaomybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MemberController {
    @Autowired
    MemberService memberService;

    @GetMapping("/test")
    public String test(Model model) {
        System.out.println("start");
        List<Map<Integer, String>> test = memberService.getTest();

        model.addAttribute("list", test);

        return "content/main";
    }
}

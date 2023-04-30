package me.snowlight.springkakaopayapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Slf4j
@Controller
public class FrontController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("partnerOrderId", UUID.randomUUID());
        return "index";
    }
}

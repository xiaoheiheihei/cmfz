package com.baizhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/remove")
public class RemoveController {
    @RequestMapping("/remove")
    public String remove(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }
}

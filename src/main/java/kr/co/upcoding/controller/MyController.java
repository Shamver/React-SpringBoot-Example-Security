package kr.co.upcoding.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.co.upcoding.mapper.TestMapper;
import kr.co.upcoding.vo.TestVO;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyController {

    // main.bundle.js 로 매핑할 경로
    @GetMapping(value = {"/", "/device", "device/{dId}"})
    public String main(Model model, HttpServletRequest req) {

        model.addAttribute("pageName", "main");
        return "page";
    }

}
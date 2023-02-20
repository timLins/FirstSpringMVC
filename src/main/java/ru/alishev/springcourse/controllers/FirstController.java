package ru.alishev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {
    //HTTPServlerRequest - с пустыми параметрами не выдает ошибку
//    @GetMapping("/hello")
//    public String helloPage(HttpServletRequest request){
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//        System.out.println("Hi! " + name +" "+ surname);
//
//        return "first/hello";
//    }
    @GetMapping("/hello") //с пустыми параметрами = ошибка (or required = false)
    public String helloPage(@RequestParam(value = "name", required = false)
                            String name, @RequestParam(value = "surname", required = false) String surname,
                            Model model) {


        // System.out.println("Hi! " + name + " " + surname);
        model.addAttribute("message", "Hi! " + name + " " + surname);
        return "first/hello";
    }

    @GetMapping("/calculator")
    public String calculator(@RequestParam(value = "a") int a, @RequestParam("b") int b,
                          @RequestParam("action") String action, Model model) {
        double result = 0;
        boolean err = false;
        switch (action) {
            case ("multiplication"):
                result = a * b;
                break;
            case ("addition"):
                result = a + b;
                break;
            case ("subtraction"):
                result = a - b;
                break;
            case ("division"):
                result = a / (double)b;
                break;
            default:
               err = true;
        }
        if (!err) {
            model.addAttribute("answer", "answer = " + result + "");
        }
        else model.addAttribute("answer", "False operation type");

        return "/first/calc";
    }



    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodbye";
    }
}

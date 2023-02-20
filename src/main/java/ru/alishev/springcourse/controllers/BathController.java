package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alishev.springcourse.dao.PersonDAO;

@Controller
@RequestMapping("/test-bath-update")
public class BathController {
    private final PersonDAO personDAO;

    @Autowired
    public BathController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index() {
        return "/batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch() {
    personDAO.testMultipleUpdate();
    return "redirect:/people";
    }


    @GetMapping("/with")
    public String withBatch() {
    personDAO.testBatchUpdate();
    return "redirect:/people";
    }

}

package com.example.libraryspring.controllers;

import com.example.libraryspring.dao.PersonDAO;
import com.example.libraryspring.enitity.Person;
import com.example.libraryspring.service.PeopleService;
import com.example.libraryspring.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private PersonValidator personValidator;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",peopleService.index());

        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable int id,Model model){
        model.addAttribute("person",peopleService.show(id));
        model.addAttribute("books",peopleService.getBooksByPersonId(id));

        return "people/show";
    }

    @GetMapping("/new")
    public String save(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }




}

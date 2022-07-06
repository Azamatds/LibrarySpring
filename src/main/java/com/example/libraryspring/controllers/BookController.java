package com.example.libraryspring.controllers;

import com.example.libraryspring.dao.BookDAO;
import com.example.libraryspring.dao.PersonDAO;
import com.example.libraryspring.enitity.Book;
import com.example.libraryspring.enitity.Person;
import com.example.libraryspring.service.BookService;
import com.example.libraryspring.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model,@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear){

        if (page == null || booksPerPage == null) {
            model.addAttribute("books", bookService.findAll(sortByYear)); // выдача всех книг
        }
        else {
            model.addAttribute("books", bookService.findWithPagination(page, booksPerPage, sortByYear));
        }

        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, @ModelAttribute("person")Person person,Model model){
        model.addAttribute("book",bookService.findOne(id));
        Person bookOwner = bookService.getBookOwner(id);
        if(bookOwner!=null){
            model.addAttribute("owner",bookOwner);
        }else {
            model.addAttribute("people",peopleService.index());
        }
        return "book/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book Book) {
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book Book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new";

        bookService.save(Book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "book/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable int id){
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable int id,@ModelAttribute("person") Person selectedPerson){
        bookService.assign(id,selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "book/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookService.searchByTitle(query));
        return "book/search";
    }
}

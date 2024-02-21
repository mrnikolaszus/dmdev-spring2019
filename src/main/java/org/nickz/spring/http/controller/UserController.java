package org.nickz.spring.http.controller;

import lombok.RequiredArgsConstructor;
import org.nickz.spring.database.entity.Role;
import org.nickz.spring.dto.PageResponse;
import org.nickz.spring.dto.UserCreateEditDto;
import org.nickz.spring.dto.UserFilter;
import org.nickz.spring.dto.UserReadDto;
import org.nickz.spring.service.CompanyService;
import org.nickz.spring.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
    private final CompanyService companyService;

    @GetMapping
    public String findAll(Model model, UserFilter filter, Pageable pageable){
        Page<UserReadDto> page = userService.findAll(filter, pageable);
        model.addAttribute("users", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){

        return userService.findById(id)
                .map(user ->{
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("companies", companyService.findAll());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());
        return "user/registration";
    }
    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute UserCreateEditDto user, RedirectAttributes redirectAttributes){
//        if(true){
////            redirectAttributes.addAttribute("username", user.getUsername());
////            redirectAttributes.addAttribute("firstname", user.getFirstname());
//            redirectAttributes.addFlashAttribute("user", user);
//            return "redirect:/users/registration";
//        }
        return "redirect:/users/" + userService.create(user).getId();

    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute UserCreateEditDto user){
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


    }

//    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        if(!userService.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";

    }


}

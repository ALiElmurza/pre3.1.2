package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.DaoImp;
import web.models.User;

@Controller
@RequestMapping("/user")
public class UsersController {

    private final DaoImp daoImp;

    public UsersController(DaoImp daoImp) {
        this.daoImp = daoImp;
    }

    @GetMapping()
    public String getUsers(Model model) {
        model.addAttribute("users", daoImp.getListUsers());
        return "index";
    }

    @GetMapping("/new")
    public String addUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        daoImp.save(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", daoImp.getUserByID(id));
        return "show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", daoImp.getUserByID(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        daoImp.update(user);
        return "redirect:/user";
    }

    @GetMapping("/update")
    public String getListToUpdate(Model model) {
        model.addAttribute("users", daoImp.getListUsers());
        return "update";
    }

    @GetMapping("/delete")
    public String getListToDelete(Model model) {
        model.addAttribute("users", daoImp.getListUsers());
        return "delete";
    }

    @GetMapping("/{id}/deleteById")
    public String getUserToDelete(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", daoImp.getUserByID(id));
        return "show";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        daoImp.delete(id);
        return "redirect:/user";
    }


}

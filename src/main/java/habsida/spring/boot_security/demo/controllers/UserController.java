package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.models.Role;
import habsida.spring.boot_security.demo.models.User;
import habsida.spring.boot_security.demo.services.RoleService;
import habsida.spring.boot_security.demo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController (UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin/roles")
    @ResponseBody
    public List<Role> getRoles() {
        return roleService.listRoles();
    }


    @GetMapping(value = "/admin/gen")
    public ModelAndView showUsers() {
        ModelAndView mov = new ModelAndView("/gen");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Optional<User> loggedInUserOptional = userService.findByName(username);


        if (loggedInUserOptional.isPresent()) {

            User loggedInUser = loggedInUserOptional.get();

            mov.addObject("loggedInUser", loggedInUser);
        } else {

            mov.addObject("loggedInUser", null);
        }
        mov.addObject("users",userService.listUsers());
        return mov;
    }

    @GetMapping("admin/userData")
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.listUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/admin/new")
    public ModelAndView newPerson(@ModelAttribute("user") User user) {
        ModelAndView mov = new ModelAndView("/new");
        mov.addObject("roles", roleService.listRoles());
        return mov;
    }

    @PostMapping("/admin/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addUser = userService.add(user);
        return new ResponseEntity<>(addUser, HttpStatus.CREATED);
    }

    @PostMapping("/admin/gen")
    public String create(@ModelAttribute("user") User user, Model model) {
        userService.add(user);
        model.addAttribute("user", user);
        model.addAttribute("users", userService.listUsers());
        return "redirect:/admin/gen";
    }



//    @GetMapping("/admin/edit")
//    public ModelAndView edit(@RequestParam long id) {
//        ModelAndView mav = new ModelAndView("/edit");
//        User user = userService.userById(id).get();
//        mav.addObject("user", user);
//        mav.addObject("roles", roleService.listRoles());
//        return mav;
//    }

    @GetMapping("admin/edit")
    public Object editUser(@RequestParam long id, @RequestHeader(value="X-Requested-With", required = false) String requestedWith) {
        Optional<User> userOptional = userService.userById(id);
        if(userOptional.isPresent()) {
            ModelAndView mav = new ModelAndView("/edit");
            mav.addObject("user", userOptional.get());
            mav.addObject("roles", roleService.listRoles());

            if (requestedWith != null && requestedWith.equals("XMLHttpRequest")) {
                return ResponseEntity.ok(userOptional.get());
            } else {
                return mav;
            }
            } else {
                return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping("/admin/edit")
//    public String update(@ModelAttribute("user") User user) {
//        userService.update(user);
//        return "redirect:/admin/gen";
//    }

//    @PostMapping("/admin/delete")
//    public String deleteUser(@RequestParam long id) {
//        userService.remove(id);
//        return "redirect:/admin/gen";
//    }

    @GetMapping("/admin/delete")
    public ModelAndView delete(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("/delete");
        User user = userService.userById(id).get();
        mav.addObject("user", user);
        mav.addObject("roles", roleService.listRoles());
        return mav;
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("/user")
    public ModelAndView user(Principal principal) {
        ModelAndView mov = new ModelAndView("/user");
        mov.addObject("user", userService.findByName(principal.getName()));

        return mov;
    }

//    @GetMapping("/admin/user")
//    public String getUserPage(Model model, Principal principal) {
//        List<User> users = userService.listUsers();
//        model.addAttribute("users", users);
//        model.addAttribute("username", principal.getName());
//        return "user";
//    }

    @PutMapping("/admin/user/update")
    public ResponseEntity<User> updateUser(@RequestParam Long id, @RequestBody User user) {
        User updateUser = userService.update(id, user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }


    @DeleteMapping("admin/user")
    public ResponseEntity<Void> deleteUser(@RequestParam long id) {
        userService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
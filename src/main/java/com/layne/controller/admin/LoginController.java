package com.layne.controller.admin;

import com.layne.pojo.User;
import com.layne.service.UserService;
import com.layne.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    /**
     * 验证登录,用户名,密码
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, MD5Utils.code(password));
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }


    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }



}

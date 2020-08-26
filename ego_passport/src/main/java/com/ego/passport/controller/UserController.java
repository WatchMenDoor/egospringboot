package com.ego.passport.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: liuxw
 * @Date: 2019/8/16
 * @Description: com.ego.controller
 * @version: 1.0
 */
@Controller
public class UserController {

    @Autowired
    private TbUserService tbUserService;
    @RequestMapping("/user/showLogin")
    public String login(Model model,@RequestHeader(name = "Referer",required = false) String referer){
        if (referer!=null&&!referer.equals("http://localhost:8084/user/showRegister")){
            model.addAttribute("redirect", referer);
        }


        return "login";
    }


    @RequestMapping("/user/showRegister")
    public String register(){
        return "register";
    }

    @PostMapping("/user/login")
    @ResponseBody
    public EgoResult loginIn(TbUser user, HttpServletRequest request, HttpServletResponse response){
      return tbUserService.login(user,request,response);
    }

    @RequestMapping("/user/token/{token}")
    @CrossOrigin
    @ResponseBody
    public EgoResult getToken(@PathVariable String token){
        return tbUserService.selectToken(token);
    }


    @RequestMapping("/user/logout/{token}")
    @CrossOrigin
    @ResponseBody
    public EgoResult logOut(@PathVariable String token, HttpServletRequest request, HttpServletResponse response){
        return tbUserService.logout(token, request, response);
    }


    @RequestMapping("/user/check/{username}/1")
    @ResponseBody
    public EgoResult getName(@PathVariable String username){
        return tbUserService.checkName(username);
    }

    @RequestMapping("/user/check/{phone}/2")
    @ResponseBody
    public EgoResult getPhone(@PathVariable String phone){
        return tbUserService.checkPhone(phone);
    }


    @PostMapping("/user/register")
    @ResponseBody
    public EgoResult Register(TbUser user){
        return tbUserService.save(user);
    }
}

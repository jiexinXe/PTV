package com.zmkj.Controller;

import com.zmkj.utils.Code;
import com.zmkj.utils.Rest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping
public class loginController {

    @PostMapping("/login")
    public Rest login(@RequestParam("username") String name, @RequestParam("password") String password){
        if (Objects.equals(name, "21301016") && Objects.equals(password, "123456")){
            return new Rest(Code.rc200.getCode(), "登录成功");
        }else if(Objects.equals(name, "21301016") && !Objects.equals(password, "123456")){
            return new Rest(Code.rc400.getCode(), "密码错误");
        }else if (!Objects.equals(name, "21301016") && Objects.equals(password, "123456")){
            return new Rest(Code.rc400.getCode(), "用户名错误");
        }
        return new Rest(Code.rc400.getCode(), "登录失败");
    }

}

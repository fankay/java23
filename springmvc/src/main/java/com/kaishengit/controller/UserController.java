package com.kaishengit.controller;

import com.kaishengit.entity.User;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fankay on 2017/7/13.
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @GetMapping
    public ModelAndView list(
            @RequestParam(required = false,defaultValue = "1") int page) {
        System.out.println("page:" + page);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/list");
        mav.addObject("name","李司棋");
        return mav;
    }

    /*@RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("name","Jack");
        return "user/list";
    }*/

    @GetMapping("/{id:\\d+}")
    public String show(@PathVariable Integer id) {
        System.out.println("ID:" + id);
        return "user/show";
    }

    @GetMapping("/{className:(java,web)\\d+}/{name}")
    public String showUser(@PathVariable String className,
                           @PathVariable String name) {
        System.out.println("ID:" + className);
        System.out.println("name:" + name);
        return "user/show";
    }

    @GetMapping("/save")
    public String save() {
        return "user/save";
        //return "redirect:/user";
    }

    @PostMapping("/save")
    public String save(User user, String zipCode,
                       RedirectAttributes redirectAttributes,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       HttpSession session) {
        System.out.println("userName:" + user.getUserName() + " address:" + user.getAddress() + " zipCode:" + zipCode);

        redirectAttributes.addFlashAttribute("message","操作成功");
        return "redirect:/user/save";
    }


    @GetMapping(value = "/validate/{userName}",
            produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String validateUser(@PathVariable String userName) {
        if("tom".equals(userName)) {
            return "该账号已被占用";
        } else {
            return "该账号可以使用";
        }
    }

    @GetMapping("/api/show")
    @ResponseBody
    public User showUser() {
        User user = new User();
        user.setId(276);
        user.setAddress("北京");
        user.setUserName("Rose");
        return user;
    }

    @GetMapping("/api/shows")
    @ResponseBody
    public List<User> showUser2() {
        User user = new User();
        user.setId(276);
        user.setAddress("北京");
        user.setUserName("Rose");

        User user2 = new User();
        user2.setId(277);
        user2.setAddress("上海");
        user2.setUserName("张三");

        List<User> userList = Arrays.asList(user,user2);
        return userList;
    }

    @PostMapping("/upload")
    public String upload(MultipartFile doc,String docName) {
        System.out.println("DocName:" + docName);

        if(!doc.isEmpty()) {
            System.out.println(doc.getName()); //表单控件名称
            System.out.println(doc.getOriginalFilename()); //文件名称
            System.out.println(doc.getSize());//文件大小
            System.out.println(doc.getContentType());//文件类型MIMEType

            try {
                InputStream inputStream = doc.getInputStream();
                OutputStream outputStream = new FileOutputStream(new File("D:/temp/upload", doc.getOriginalFilename()));
                IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "redirect:/user/save";
    }

}

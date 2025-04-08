package com.front_end.front_end.MainPage;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");

        if(username != null) {
            model.addAttribute("username", username);
        }

    }

}

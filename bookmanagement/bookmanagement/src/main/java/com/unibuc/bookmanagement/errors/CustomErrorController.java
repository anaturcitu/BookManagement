package com.unibuc.bookmanagement.errors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        Integer statusCode = (Integer) errorDetails.get("status");
        model.addAttribute("code", statusCode);
        
        System.out.println("HANDLE ERROR TRIGGERED" + errorDetails);

        if (statusCode != null) {
            return switch (statusCode) {
                case 404 -> "error/404";
                case 403 -> "error/403";
                case 500 -> "error/500";
                default -> "error/generic";
            };
        }

        return "error/generic";

    }
}

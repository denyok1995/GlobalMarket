package com.pjt.globalmarket;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {

    @GetMapping("/")
    public @ResponseBody String defaultPage() {
        return "this is home";
    }

    @GetMapping("/failure")
    public void failurePage() {
        throw new UsernameNotFoundException("Need To Sign-Up");
    }
}

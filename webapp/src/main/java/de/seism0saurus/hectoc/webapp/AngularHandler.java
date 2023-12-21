package de.seism0saurus.hectoc.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AngularHandler {

    @RequestMapping({"/home", "/howto", "/challenger", "/bot", "/bug", "/participate", "/legal-notice", "/data-protection"})
    public String index() {
        return "forward:/index.html";
    }
}

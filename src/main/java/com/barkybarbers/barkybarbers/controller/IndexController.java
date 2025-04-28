/**
 * Handles requests for the root URL. Redirects to the appointment page.
 */

package com.barkybarbers.barkybarbers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String home() {
        return "appointment.html";
    }
}
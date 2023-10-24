package com.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import com.model.User;
import com.service.EmailService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Candidate;
import com.service.CandidateService;

@Controller
public class CandidateController {

    @Autowired
    private CandidateService cndServ;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @PostMapping("/addcandidate")
    public String addCandidate(@RequestParam("candidate") String selectedCandidate,
            Principal principal, Model model, HttpSession session) {
        String email = principal.getName();

        if (cndServ.getCandidateByUser(email) != null) {
            session.setAttribute("msg", "Already Voted");
        } else {
            Candidate cnd = cndServ.getCandidateByUser(email);
            if (cnd == null) {
                cnd = new Candidate();
            }

            switch (selectedCandidate) {
                case "candidate1":
                    cnd.setCandidate1(email);
                    break;
                case "candidate2":
                    cnd.setCandidate2(email);
                    break;
                case "candidate3":
                    cnd.setCandidate3(email);
                    break;
                case "candidate4":
                    cnd.setCandidate4(email);
                    break;
                default:
                    // Handle unexpected candidate value
            }

            cndServ.addCandidate(cnd);
            User user=userService.getUserByEmail(email);
            emailService.sendVoteConfirmation(user.getName(),user.getEmail());
            session.setAttribute("msg", "Successfully Voted...");
        }

        return "redirect:user/";
    }
}

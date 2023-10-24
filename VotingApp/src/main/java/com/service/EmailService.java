package com.service;

import com.config.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(EmailDto emailDetails)
    {


        try
        {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMsgBody());
            mailMessage.setSubject(emailDetails.getSubject());

            mailSender.send(mailMessage);
            System.out.println("Email sent - Please check ( check SPAM folder also )");
        }
        catch (Exception e)
        {
            System.out.println("Unable to send mail : "+e.getMessage());
        }
    }

    public void sendWelcomeMail(String name, String email)
    {
        String subject="Welcome to Voter App";
        String body="Hi "+name+",\n\nThank you for registering with Voter app. You can go to voter section and vote your candidate\n\n\nThanks,\nTeam Voter";
        EmailDto emailDto=new EmailDto(email,body,subject);
        this.sendMail(emailDto);
    }

    public void sendVoteConfirmation(String name, String email)
    {
        String subject="Congratulations on successful Vote";
        String body="Hi "+name+",\n\nThank you for your vote, we have safely recorded your vote for your candidate.\n\n\nThanks,\nTeam Voter";
        EmailDto emailDto=new EmailDto(email,body,subject);
        this.sendMail(emailDto);
    }

}

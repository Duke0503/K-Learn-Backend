package com.klearn.klearn_website.scheduler;

import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.email.EmailService;
import com.klearn.klearn_website.service.user.UserService;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserReminderJob {

    private final UserService userService;
    private final EmailService emailService;

    // Hard-coded email subject and body
    private final String reminderEmailSubject = "We miss you!";
    private final String reminderEmailBody = "It's been 30 days since your last login. We encourage you to log in and check out what's new!";

    @Scheduled(cron = "0 0 0 * * ?")
    public void sendReminderEmails() {
        List<User> inactiveUsers = userService.findUsersNotLoggedInFor30Days();

        for (User user : inactiveUsers) {
            emailService.sendReminderEmail(user.getEmail(), reminderEmailSubject, reminderEmailBody);
            // System.out.println("Sent reminder email to: " + user.getEmail());
        }
    }
}

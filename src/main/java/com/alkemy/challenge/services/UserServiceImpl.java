package com.alkemy.challenge.services;

import com.alkemy.challenge.data.entities.User;
import com.alkemy.challenge.data.repositories.UserRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    //TODO: en un proyecto real debería guardarse como una variable de entorno.
    private static final String SENDGRID_API_KEY = "Your api key";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return  getUserByEmail(email)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getPassword(), Collections.emptyList()))

                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    @Transactional
    public boolean register(User user) {
        User userWithPasswordEncoded = new User(user.getEmail(), passwordEncoder.encode(user.getPassword()));

        if(getUserByEmail(user.getEmail()).isEmpty()){
            userRepository.save(userWithPasswordEncoded);
            sendWelcomeEmail(user);
            return true;
        }

        return false;
    }

    private void sendWelcomeEmail(User user) {
        Mail mail = prepareMail(user);
        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
        } catch (IOException ignored) {

        }
    }

    private Mail prepareMail(User user){
        Email from = new Email("francozanardi97@gmail.com");
        String subject = "Welcome!";
        Email to = new Email(user.getEmail());
        Content content = new Content("text/plain", ":)");
        return new Mail(from, subject, to, content);
    }
}

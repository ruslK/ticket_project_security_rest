package com.ticketsproject.servisesImpl;

import com.ticketsproject.entities.ConfirmationToken;
import com.ticketsproject.exception.TicketingProjectException;
import com.ticketsproject.repository.ConfirmationTokenRepository;
import com.ticketsproject.servises.ConfirmationTokenService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final JavaMailSender javaMailSender;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository, JavaMailSender javaMailSender) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        return confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    @Override
    public ConfirmationToken readByToken(String token) throws TicketingProjectException {
        ConfirmationToken confToken = confirmationTokenRepository.findByToken(token).orElse(null);

        if (confToken == null) {
            throw new TicketingProjectException("This Token does not exists");
        }

        if (!confToken.isTokenValid(confToken.getExpireDate())) {
            throw new TicketingProjectException("This Token have been expired");
        }

        return confToken;
    }

    @Override
    public void delete(ConfirmationToken confirmationToken) {
        confirmationToken.setIsDeleted(true);
        confirmationTokenRepository.save(confirmationToken);
    }
}

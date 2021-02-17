package com.ticketsproject.servises;

import com.ticketsproject.entities.ConfirmationToken;
import com.ticketsproject.exception.TicketingProjectException;
import org.springframework.mail.SimpleMailMessage;

public interface ConfirmationTokenService {

    ConfirmationToken save(ConfirmationToken confirmationToken);

    void sendEmail(SimpleMailMessage email);

    ConfirmationToken readByToken(String token) throws TicketingProjectException;

    void delete(ConfirmationToken confirmationToken);

}

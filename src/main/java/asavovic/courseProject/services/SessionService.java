package asavovic.courseProject.services;

import asavovic.courseProject.entities.Customer;
import asavovic.courseProject.entities.Session;
import asavovic.courseProject.exceptions.ResourceNotFoundException;
import asavovic.courseProject.repositories.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Long createNewSession(Customer u) {
        Session session = new Session();
        session.setCustomer(u);
        Session savedSession = sessionRepository.save(session);
        return savedSession.getSessionId();
    }
    public Session getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() -> new ResourceNotFoundException("session not found"));
    }
}

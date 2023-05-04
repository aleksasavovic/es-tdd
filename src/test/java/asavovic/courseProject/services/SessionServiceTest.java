package asavovic.courseProject.services;

import asavovic.courseProject.entities.Customer;
import asavovic.courseProject.entities.Session;
import asavovic.courseProject.exceptions.ResourceNotFoundException;
import asavovic.courseProject.repositories.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SessionServiceTest {
    @Mock
    private SessionRepository sessionRepository;
    @InjectMocks
    private SessionService sessionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createNewSession() {
        Customer customer = new Customer();
        customer.setId(1L);
        Session session = new Session();
        session.setSessionId(1L);
        session.setCustomer(customer);

        when(sessionRepository.save(any())).thenReturn(session);

        Long sessionId = sessionService.createNewSession(customer);

        assertNotNull(sessionId);
        assertTrue(sessionId > 0);

        verify(sessionRepository, times(1)).save(any());
        assertEquals(customer, session.getCustomer());
    }

    @Test
    void getSessionById() {
        Long sessionId = 1L;
        Session session = new Session();
        session.setSessionId(sessionId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        assertEquals(session, sessionService.getSessionById(sessionId));
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    void getSessionByIdNotFound() {
        Long sessionId = 1L;

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sessionService.getSessionById(sessionId));
        verify(sessionRepository, times(1)).findById(sessionId);
    }
}
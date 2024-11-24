package by.hospital.accounting.service;

import by.hospital.accounting.model.Request;
import by.hospital.accounting.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }

    public Request getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Request approveRequest(Long id) {
        Request request = getRequestById(id);
        request.setApproved(true);
        return requestRepository.save(request);
    }
}

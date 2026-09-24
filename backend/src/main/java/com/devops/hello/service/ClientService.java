package com.devops.hello.service;

import com.devops.hello.dto.ClientRequest;
import com.devops.hello.dto.ClientResponse;
import com.devops.hello.exception.ClientNotFoundException;
import com.devops.hello.model.Client;
import com.devops.hello.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public List<ClientResponse> findAll() {
        return clientRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        return toResponse(getClientOrThrow(id));
    }

    public ClientResponse create(ClientRequest request) {
        Client client = new Client(request.getName(), request.getEmail());
        return toResponse(clientRepository.save(client));
    }

    public ClientResponse update(Long id, ClientRequest request) {
        Client client = getClientOrThrow(id);
        client.setName(request.getName());
        client.setEmail(request.getEmail());
        return toResponse(clientRepository.save(client));
    }

    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException(id);
        }
        clientRepository.deleteById(id);
    }

    /** Réinitialise la table clients (utile pour les tests). */
    public void clear() {
        clientRepository.deleteAll();
    }

    private Client getClientOrThrow(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    private ClientResponse toResponse(Client client) {
        return new ClientResponse(client.getId(), client.getName(), client.getEmail());
    }
}

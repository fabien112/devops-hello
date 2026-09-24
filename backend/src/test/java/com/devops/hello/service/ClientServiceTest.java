package com.devops.hello.service;

import com.devops.hello.dto.ClientRequest;
import com.devops.hello.dto.ClientResponse;
import com.devops.hello.exception.ClientNotFoundException;
import com.devops.hello.model.Client;
import com.devops.hello.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void create_shouldAssignIdAndStoreClient() {
        Client saved = new Client("Alice", "alice@example.com");
        saved.setId(1L);
        when(clientRepository.save(any(Client.class))).thenReturn(saved);

        ClientResponse created = clientService.create(new ClientRequest("Alice", "alice@example.com"));

        assertThat(created.getId()).isEqualTo(1L);
        assertThat(created.getName()).isEqualTo("Alice");
        assertThat(created.getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void findAll_shouldReturnAllClients() {
        Client alice = new Client("Alice", "alice@example.com");
        alice.setId(1L);
        Client bob = new Client("Bob", "bob@example.com");
        bob.setId(2L);
        when(clientRepository.findAll()).thenReturn(List.of(alice, bob));

        List<ClientResponse> clients = clientService.findAll();

        assertThat(clients).hasSize(2);
        assertThat(clients).extracting(ClientResponse::getName).containsExactlyInAnyOrder("Alice", "Bob");
    }

    @Test
    void findById_shouldReturnClient() {
        Client alice = new Client("Alice", "alice@example.com");
        alice.setId(1L);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(alice));

        ClientResponse found = clientService.findById(1L);

        assertThat(found.getName()).isEqualTo("Alice");
        assertThat(found.getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void findById_whenMissing_shouldThrow() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.findById(99L))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void update_shouldModifyExistingClient() {
        Client alice = new Client("Alice", "alice@example.com");
        alice.setId(1L);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(alice));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ClientResponse updated = clientService.update(
                1L,
                new ClientRequest("Alice Martin", "alice.martin@example.com")
        );

        assertThat(updated.getId()).isEqualTo(1L);
        assertThat(updated.getName()).isEqualTo("Alice Martin");
        assertThat(updated.getEmail()).isEqualTo("alice.martin@example.com");
    }

    @Test
    void update_whenMissing_shouldThrow() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.update(99L, new ClientRequest("X", "x@example.com")))
                .isInstanceOf(ClientNotFoundException.class);
    }

    @Test
    void delete_shouldRemoveClient() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        clientService.delete(1L);

        verify(clientRepository).deleteById(1L);
    }

    @Test
    void delete_whenMissing_shouldThrow() {
        when(clientRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> clientService.delete(99L))
                .isInstanceOf(ClientNotFoundException.class);
    }
}

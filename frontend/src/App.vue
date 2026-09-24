<script setup>
import { onMounted, reactive, ref } from 'vue'
import { createClient, deleteClient, getClients, updateClient } from './api'

const clients = ref([])
const loading = ref(false)
const error = ref('')
const success = ref('')
const editingId = ref(null)

const form = reactive({
  name: '',
  email: ''
})

async function loadClients() {
  loading.value = true
  error.value = ''
  try {
    clients.value = await getClients()
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
}

function resetForm() {
  form.name = ''
  form.email = ''
  editingId.value = null
}

function startEdit(client) {
  editingId.value = client.id
  form.name = client.name
  form.email = client.email
  success.value = ''
  error.value = ''
}

async function submitForm() {
  error.value = ''
  success.value = ''

  const payload = {
    name: form.name.trim(),
    email: form.email.trim()
  }

  try {
    if (editingId.value) {
      await updateClient(editingId.value, payload)
      success.value = 'Client mis à jour'
    } else {
      await createClient(payload)
      success.value = 'Client créé'
    }
    resetForm()
    await loadClients()
  } catch (err) {
    error.value = err.message
  }
}

async function removeClient(id) {
  error.value = ''
  success.value = ''
  try {
    await deleteClient(id)
    if (editingId.value === id) {
      resetForm()
    }
    success.value = 'Client supprimé'
    await loadClients()
  } catch (err) {
    error.value = err.message
  }
}

onMounted(loadClients)
</script>

<template>
  <header>
    <h1>DevOps Clients</h1>
    <p class="subtitle">Frontend Vue pour tester le CRUD Spring Boot + PostgreSQL</p>
  </header>

  <section class="panel">
    <h2>{{ editingId ? 'Modifier un client' : 'Ajouter un client' }}</h2>
    <form class="form-grid" @submit.prevent="submitForm">
      <label>
        Nom
        <input v-model="form.name" type="text" required placeholder="Alice" />
      </label>
      <label>
        Email
        <input v-model="form.email" type="email" required placeholder="alice@example.com" />
      </label>
      <div class="actions">
        <button class="btn-primary" type="submit">
          {{ editingId ? 'Enregistrer' : 'Créer' }}
        </button>
        <button v-if="editingId" class="btn-secondary" type="button" @click="resetForm">
          Annuler
        </button>
      </div>
    </form>
    <p v-if="success" class="success">{{ success }}</p>
    <p v-if="error" class="error">{{ error }}</p>
  </section>

  <section class="panel">
    <h2>Liste des clients</h2>
    <p v-if="loading" class="empty">Chargement...</p>
    <p v-else-if="clients.length === 0" class="empty">Aucun client pour le moment.</p>
    <table v-else>
      <thead>
        <tr>
          <th>ID</th>
          <th>Nom</th>
          <th>Email</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="client in clients" :key="client.id">
          <td>{{ client.id }}</td>
          <td>{{ client.name }}</td>
          <td>{{ client.email }}</td>
          <td>
            <div class="actions">
              <button class="btn-secondary" type="button" @click="startEdit(client)">
                Modifier
              </button>
              <button class="btn-danger" type="button" @click="removeClient(client.id)">
                Supprimer
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

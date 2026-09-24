const API_BASE = import.meta.env.VITE_API_BASE || ''

async function request(path, options = {}) {
  const response = await fetch(`${API_BASE}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {})
    },
    ...options
  })

  if (response.status === 204) {
    return null
  }

  const data = await response.json().catch(() => ({}))

  if (!response.ok) {
    const message = data.error || `Erreur HTTP ${response.status}`
    throw new Error(message)
  }

  return data
}

export function getClients() {
  return request('/api/clients')
}

export function createClient(payload) {
  return request('/api/clients', {
    method: 'POST',
    body: JSON.stringify(payload)
  })
}

export function updateClient(id, payload) {
  return request(`/api/clients/${id}`, {
    method: 'PUT',
    body: JSON.stringify(payload)
  })
}

export function deleteClient(id) {
  return request(`/api/clients/${id}`, {
    method: 'DELETE'
  })
}

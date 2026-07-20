const state = { token: '' };

async function registerUser() {
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const res = await fetch(`/api/auth/register?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`, { method: 'POST' });
  const data = await res.json();
  state.token = data.token || '';
  renderOutput(data);
}

async function loginUser() {
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const res = await fetch(`/api/auth/login?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`, { method: 'POST' });
  const data = await res.json();
  state.token = data.token || '';
  renderOutput(data);
}

async function searchFlights() {
  const origin = document.getElementById('origin').value;
  const destination = document.getElementById('destination').value;
  const departureDate = document.getElementById('departureDate').value;
  const res = await fetch(`/api/flights/search?origin=${encodeURIComponent(origin)}&destination=${encodeURIComponent(destination)}&departureDate=${encodeURIComponent(departureDate)}`);
  const data = await res.json();
  renderOutput(data);
}

async function createBooking() {
  const flightId = document.getElementById('flightId').value;
  const seatNumber = document.getElementById('seatNumber').value;
  const firstName = document.getElementById('firstName').value;
  const lastName = document.getElementById('lastName').value;
  const res = await fetch(`/api/bookings?flightId=${encodeURIComponent(flightId)}&seatNumber=${encodeURIComponent(seatNumber)}&firstName=${encodeURIComponent(firstName)}&lastName=${encodeURIComponent(lastName)}`, {
    method: 'POST',
    headers: { Authorization: `Bearer ${state.token}` }
  });
  const data = await res.json();
  renderOutput(data);
}

function renderOutput(data) {
  document.getElementById('output').textContent = JSON.stringify(data, null, 2);
}

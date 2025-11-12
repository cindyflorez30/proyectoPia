/*
document.addEventListener('DOMContentLoaded', () => {
    fetch('http://localhost:8080/api/productos') // La URL de tu backend
        .then(response => response.json())
        .then(productos => {
            const container = document.getElementById('productos-container');
            productos.forEach(producto => {
                const div = document.createElement('div');
                div.innerHTML = `<strong>${producto.nombre}</strong> - ${producto.precio}€`;
                container.appendChild(div);
            });
        })
        .catch(error => console.error('Error al obtener productos:', error));
});
*/
// Cargar productos desde el backend
function cargarProductos() {
    fetch('/api/productos')
    .then(res => res.json())
    .then(productos => {
        const tbody = document.querySelector('#productosTable tbody');
        tbody.innerHTML = '';
        productos.forEach(p => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${p.nombre}</td>
            <td>${p.precio}</td>
            <td>
            <button onclick="editarProducto(${p.idProducto})">Editar</button>
            <button onclick="eliminarProducto(${p.idProducto})">Eliminar</button>
            </td>`;
        tbody.appendChild(row);
        });
    });
}
cargarProductos();
// Funciones editarProducto(id) y eliminarProducto(id) llaman a PUT/DELETE.

document.getElementById("loginForm").addEventListener("submit", e => {
    e.preventDefault();
    const correo = document.getElementById("correo").value;
    const password = document.getElementById("password").value;
    fetch('/api/usuarios/login?correo=' + encodeURIComponent(correo) +
        '&password=' + encodeURIComponent(password))
    .then(res => {
        if (res.ok) {
        // Login exitoso: procesar datos (por ejemplo guardar token o redirigir)
        return res.json();
        } else {
        alert("Credenciales inválidas");
        throw new Error("Error de autenticación");
        }
    })
    .then(usuario => {
        // Manejar usuario autenticado...
    })
    .catch(err => console.error(err));
});

// Envío del formulario
document.getElementById("productoForm").addEventListener("submit", e => {
    e.preventDefault();
    const id = document.getElementById("idProducto").value;
    const producto = {
    nombre: document.getElementById("nombre").value,
    precio: parseFloat(document.getElementById("precio").value)
    };
    const metodo = id ? 'PUT' : 'POST';
    const url = '/api/productos' + (id ? '/' + id : '');
    fetch(url, {
    method: metodo,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(producto)
    })
    .then(res => res.json())
    .then(data => {
    cargarProductos(); // Refrescar tabla
    document.getElementById("productoForm").reset();
    });
});
// Lógica para cargar datos en el formulario al editar...


const form = document.getElementById('registroForm');
const tabla = document.getElementById('tablaDatos').getElementsByTagName('tbody')[0];

form.addEventListener('submit', function(event) {
    event.preventDefault();

    const nombre = form.nombre.value;
    const correo = form.correo.value;

    const fila = tabla.insertRow();
    const celdaNombre = fila.insertCell();
    const celdaCorreo = fila.insertCell();

    celdaNombre.textContent = nombre;
    celdaCorreo.textContent = correo;

    form.reset();
});
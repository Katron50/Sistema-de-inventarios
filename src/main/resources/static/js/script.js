

function editarCategoria(button) {
    // Obtener el ID desde el atributo data-id del botón
    let id = button.getAttribute('data-id');
    
    // Actualizar el atributo 'action' del formulario con el ID
    let form = document.getElementById('editForm');
    form.setAttribute('action', `/categorias/edit?id=${id}`);
    
    // Hacer una solicitud AJAX para obtener los datos de la categoría
    fetch(`/categorias/${id}`)
    .then(response => response.json())  // Convertir la respuesta en JSON
    .then(data => {
        // Rellenar los campos del formulario con los datos obtenidos
        document.getElementById('nameEdit').value = data.name;
        document.getElementById('descriptionEdit').value = data.description;
    })
    .catch(error => {
        console.error('Error al obtener los datos de la categoría:', error);
    });

}

function borrarCategoria(button){
    let id = button.getAttribute('data-id');

    let form = document.getElementById('deleteForm');
    form.setAttribute('action', `/categorias/delete?id=${id}`)

    fetch(`/categorias/${id}`)
    .then(response => response.json())  // Convertir la respuesta en JSON
    .then(data => {
        // Rellenar los campos del formulario con los datos obtenidos
        document.getElementById('nameDelete').textContent = `¿Seguro que quieres eliminar la categoria ${data.name}?`;
    })
    .catch(error => {
        console.error('Error al obtener los datos de la categoría:', error);
    });

}
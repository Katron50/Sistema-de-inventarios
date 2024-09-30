function editarCategoria(button) {
    // Obtener el ID desde el atributo data-id del botón
    let id = button.getAttribute('data-id');
    
    // Colocar el ID en el campo oculto del formulario
    document.getElementById('categoriaId').value = id;


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
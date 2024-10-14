
// Funcion para editar categorias y productos
function editar(button, entidad) {
    // Obtener el ID desde el atributo data-id del botón
    let id = button.getAttribute('data-id');
    
    // Actualizar el atributo 'action' del formulario con el ID
    let form = document.getElementById('editForm');
    form.setAttribute('action', `/${entidad}/edit?id=${id}`);
    
    // Hacer una solicitud AJAX para obtener los datos de la entidad
    fetch(`/${entidad}/${id}`)
    .then(response => response.json())  // Convertir la respuesta en JSON
    .then(data => {

        // Rellenar los campos del formulario con los datos obtenidos
        document.getElementById('nameEdit').value = data.name;
        document.getElementById('descriptionEdit').value = data.description;
        
        //Si es productos poner los datos que faltan
        if (entidad === 'productos') {
            document.getElementById('productKeyEdit').value = data.productKey;
            document.getElementById('id_category_edit').value = data.categoria.id;
            document.getElementById('toleranceEdit').value = data.tolerance;
            if (data.image) {
                document.getElementById('imageEditShow').src = `/images/${data.image}`;
                document.getElementById('imageContainer').style.display = 'block';
                document.getElementById('btnBorrarImg').setAttribute('data-id', id);
            } else {
                document.getElementById('imageContainer').style.display = 'none';
            }
        }
    })
    .catch(error => {
        console.error(`Error al obtener los datos de la ${entidad}:`, error);
    });
}

function borrar(button, entidad){
    let id = button.getAttribute('data-id');

    let form = document.getElementById('deleteForm');
    form.setAttribute('action', `/${entidad}/delete?id=${id}`)

    fetch(`/${entidad}/${id}`)
    .then(response => response.json())  // Convertir la respuesta en JSON
    .then(data => {
        // Rellenar los campos del formulario con los datos obtenidos
        if(entidad == 'categoria'){
            document.getElementById('nameDelete').textContent = `¿Seguro que quieres eliminar la categoria ${data.name}?`;

        }else if (entidad == 'productos') {
            document.getElementById('nameDelete').textContent = `¿Seguro que quieres eliminar el producto ${data.name}?`;
        }



    })
    .catch(error => {
        console.error('Error al obtener los datos:', error);
    });

}

function borrarImg(button, event) {
    event.preventDefault();

    // Obtener el ID del producto desde un atributo del botón
    let id = button.getAttribute('data-id');

    // Solicitud AJAX para eliminar la imagen del producto
    fetch(`/productos/${id}/imagen_delete`, {
        method: 'DELETE',
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al eliminar la imagen");
        }
        return response.text();
    })
    .then(data => {
        console.log(data);  // Mensaje de éxito

        // Actualizar la interfaz
        document.getElementById('imageContainer').style.display = 'none';
        document.getElementById('imageEditShow').src = ''; // Eliminar la imagen mostrada
        document.getElementById('imgDeleteText').textContent = "Imagen borrada con éxito";
    })
    .catch(error => {
        console.error('Error al eliminar la imagen:', error);
    });
}
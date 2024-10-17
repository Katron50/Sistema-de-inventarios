
function agregar(button, event, entidad){

    validaciones(event, entidad);
}

function validaciones(event, entidad){

    if (entidad == 'productos'){
        let toleranceInput = document.getElementById('tolerance');
        if (toleranceInput.value.trim() === '') {
            toleranceInput.value = 0; // Establece 0 si el campo está vacío
        }else if (isNaN(toleranceInput.value)) {
            // Maneja el caso en que el valor no es un número
            alert('Por favor, ingresa un número válido para la tolerancia.');
            event.preventDefault(); // Previene el envío del formulario
        }
    }
    
}


// Funcion para editar categorias y productos
function editar(button, entidad) {
    // Obtener el ID desde el atributo data-id del botón
    let id = button.getAttribute('data-id');
    
    // Actualizar el atributo 'action' del formulario con el ID
    let form = document.getElementById('editForm');
    form.setAttribute('action', `/${entidad}/edit?id=${id}`);
    
    // Hacer una solicitud AJAX para obtener los datos de la entidad
    fetch(`/${entidad}/${id}`)
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error en la red: ${response.statusText}`);
        }
        return response.json(); // Convertir la respuesta en JSON
    })
    .then(data => {
        // Rellenar los campos del formulario dependiendo de la entidad
        console.log(data);
        switch (entidad) {
            case 'productos':
            case 'categorias':
                rellenarProductoOCategoria(data, entidad);
                break;
            case 'compras':
                rellenarCompra(data);
                break;
            default:
                console.error(`Entidad no reconocida: ${entidad}`);
        }
    })
    .catch(error => {
        console.error(`Error al obtener los datos de la ${entidad}:`, error);
        alert(`No se pudieron cargar los datos: ${error.message}`);
    });
}
//Funciones para rellenar los datos
function rellenarProductoOCategoria(data, entidad) {
    document.getElementById('nameEdit').value = data.name;
    document.getElementById('descriptionEdit').value = data.description;

    if (entidad === 'productos') {
        document.getElementById('productKeyEdit').value = data.productKey;
        document.getElementById('id_category_edit').value = data.categoria.id;
        document.getElementById('toleranceEdit').value = data.tolerance;

        if (data.image) {
            document.getElementById('imageEditShow').src = `/images/${data.image}`;
            document.getElementById('imageContainer').style.display = 'block';
            document.getElementById('btnBorrarImg').setAttribute('data-id', data.id);
        } else {
            document.getElementById('imageContainer').style.display = 'none';
        }
    }
}

function rellenarCompra(data) {

    document.getElementById('addProductoEdit').value = data.producto.name;
    document.getElementById('addProductoIdEdit').value = data.producto.id;
    document.getElementById('cantidadCompradaEdit').value = data.cantidadComprada;
    document.getElementById('cantidadActualEdit').value = data.cantidadActual;
    document.getElementById('costoCompraEdit').value = data.costoCompra;
    document.getElementById('fechaCompraEdit').value = data.fechaCompra;

    //botón de ver comentarios anteriores
    const comentariosBtn = document.getElementById('comentariosCompra');
    comentariosBtn.setAttribute('data-id', data.id);
    comentariosBtn.onclick = () => cargarComentarios(data.id);
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


//Compras

function selectProduct(element, action) {
    var productId = element.getAttribute("data-id");
    var productName = element.textContent;
    
    if (action === 'agregar'){
        // Actualizar el input de producto seleccionado
        document.getElementById("addProducto").value = productName;
        document.getElementById("addProductoId").value = productId;
    }
    if (action === 'editar') {
        document.getElementById("addProductoEdit").value = productName;
        document.getElementById("addProductoIdEdit").value = productId;
    }

}

// Ver Comentarios
function cargarComentarios(compraId) {
    fetch('/compras/comentarios/' + compraId)
        .then(response => response.json())
        .then(data => {
            let comentariosList = document.getElementById('comentarios-list');
            comentariosList.innerHTML = '';  // Limpiar los comentarios previos
            
            if (data.length > 0) {
                data.forEach(comentario => {
                    let listItem = document.createElement('li');
                    listItem.textContent = comentario.comentario + ' - ' + new Date(comentario.date).toLocaleString();
                    comentariosList.appendChild(listItem);
                });
            } else {
                comentariosList.innerHTML = '<li>No hay comentarios para esta compra.</li>';
            }
        })
        .catch(error => {
            console.error('Error al cargar los comentarios:', error);
        });
}
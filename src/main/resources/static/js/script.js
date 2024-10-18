
function agregarModal(event, entidad){
    validaciones(event, entidad, 'agregar');
}
function editarModal(event, entidad){
    validaciones(event, entidad, 'editar');
}



function validaciones(event, entidad, action){
    if (entidad == 'categorias'){
        if(action === 'agregar'){
            let nameInput = document.getElementById('name');
            if (nameInput.value.trim() === ''){
                document.getElementById('errorName').textContent = "No puede estar vacio el nombre";
                document.getElementById('errorName').style.display = "block";
                event.preventDefault();
            }
        } if(action === 'editar'){
            let nameInput = document.getElementById('nameEdit');
            if (nameInput.value.trim() === ''){
                document.getElementById('errorNameEdit').textContent = "No puede estar vacio el nombre";
                document.getElementById('errorNameEdit').style.display = "block";
                event.preventDefault();
            }
        }
    }

    if (entidad == 'productos'){
        if (action === 'agregar'){
            if (document.getElementById('key').value.length < 14){
                document.getElementById('errorKey').textContent = "Deben ser 15 caracteres sin espacios";
                document.getElementById('errorKey').style.display = "block";
                event.preventDefault();
            }
            if (document.getElementById('name').value.trim() === ''){
                document.getElementById('errorName').textContent = "No puede estar vacio el nombre";
                document.getElementById('errorName').style.display = "block";
                event.preventDefault();
            }
            let toleranceInput = document.getElementById('tolerance');
            if (toleranceInput.value.trim() === '') {
                toleranceInput.value = 0; // Establece 0 si el campo está vacío
            }else if (isNaN(toleranceInput.value)) {
                document.getElementById('errorTolerance').textContent = "Ingresa un número";
                document.getElementById('errorTolerance').style.display = "block";
            event.preventDefault(); // Previene el envío del formulario
            }
        }
        if (action === 'editar'){
            if (document.getElementById('nameEdit').value.trim() === ''){
                document.getElementById('errorNameEdit').textContent = "No puede estar vacio el nombre";
                document.getElementById('errorNameEdit').style.display = "block";
                event.preventDefault();
            }
            let toleranceInput = document.getElementById('toleranceEdit');
            if (toleranceInput.value.trim() === '') {
                toleranceInput.value = 0; // Establece 0 si el campo está vacío
            }else if (isNaN(toleranceInput.value)) {
                document.getElementById('errorToleranceEdit').textContent = "Ingresa un número";
                document.getElementById('errorToleranceEdit').style.display = "block";
                event.preventDefault(); // Previene el envío del formulario
            }
        }
    }
    if (entidad == 'compras'){
        if (action === 'agregar'){
            let name = document.getElementById('addProducto').value;
            let id = document.getElementById('addProductoId').value;
            if(name == null || id == null){
                document.getElementById('errorProduct').textContent = "Ingresa un producto";
                document.getElementById('errorProduct').style.display = "block";
                event.preventDefault();
            }
            if(document.getElementById('cantidadComprada').value < 1){
                document.getElementById('errorCantidadComprada').textContent = "La cantidad debe ser minimo 1";
                document.getElementById('errorCantidadComprada').style.display = "block";
                event.preventDefault();
            }if(document.getElementById('cantidadActual').value < 1){
                document.getElementById('errorCantidadActual').textContent = "La cantidad debe ser minimo 1";
                document.getElementById('errorCantidadActual').style.display = "block";
                event.preventDefault();
            }if (document.getElementById('cantidadActual').value > document.getElementById('cantidadComprada').value){
                document.getElementById('errorCantidadActual').textContent = "La cantidad actual no puede ser mayor a la cantidad comprada";
                document.getElementById('errorCantidadActual').style.display = "block";
                event.preventDefault();
            }
        }
        if (action === 'editar'){
            let name = document.getElementById('addProductoEdit').value;
            let id = document.getElementById('addProductoIdEdit').value;
            if(name == null || id == null){
                document.getElementById('errorProductEdit').textContent = "Ingresa un producto";
                document.getElementById('errorProductEdit').style.display = "block";
                event.preventDefault();
            }
            if(document.getElementById('cantidadCompradaEdit').value < 1){
                document.getElementById('errorCantidadCompradaEdit').textContent = "La cantidad debe ser minimo 1";
                document.getElementById('errorCantidadCompradaEdit').style.display = "block";
                event.preventDefault();
            }if(document.getElementById('cantidadActualEdit').value < 1){
                document.getElementById('errorCantidadActualEdit').textContent = "La cantidad debe ser minimo 1";
                document.getElementById('errorCantidadActualEdit').style.display = "block";
                event.preventDefault();
            }if (document.getElementById('cantidadActualEdit').value > document.getElementById('cantidadCompradaEdit').value){
                document.getElementById('errorCantidadActualEdit').textContent = "La cantidad actual no puede ser mayor a la cantidad comprada";
                document.getElementById('errorCantidadActualEdit').style.display = "block";
                event.preventDefault();
            }if (document.getElementById('comentarioEdit').value == null || document.getElementById('comentarioEdit').value === ''){
                document.getElementById('errorComentarioEdit').textContent = "Debes escribir un comentario sobre la edición";
                document.getElementById('errorComentarioEdit').style.display = "block";
                event.preventDefault();
            }
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
        if(entidad === 'categorias'){
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
        let name = document.getElementById("addProducto").value;
        let id = document.getElementById("addProductoId").value;
        
        console.log(name, id)
    }
    if (action === 'editar') {
        document.getElementById("addProductoEdit").value = productName;
        document.getElementById("addProductoIdEdit").value = productId;
        let name = document.getElementById("addProductoEdit").value;
        let id = document.getElementById("addProductoIdEdit").value;
        console.log(name, id)
    }
    if (action === 'agregarProducto') {
        const imgElement = element.closest('.row').querySelector('.mostrarImg');
        fetch(`/productos/${productId}`)
        .then(response => response.json())
        .then(data => {
            if (data.image != null){
                imgElement.style.display = 'block';
                imgElement.src = '/images/' + data.image;
            } else{
                imgElement.style.display = 'none';
            }
        });
    }

}

function handleInput(element, action) {
    const value = element.value;
    const options = document.querySelectorAll('#productosList option');
    options.forEach(option => {
        if (option.value === value) {
            let productId = option.getAttribute('data-id');
            fetch(`/productos/${productId}`)
                        .then(response => response.json())
                        .then(data => {
                            if (action === 'agregar'){
                                document.getElementById("addProductoId").value = productId;
                            } if (action === 'editar'){
                                document.getElementById("addProductoIdEdit").value = productId;

                            } if(action === 'agregarProducto'){
                                const imgElement = element.closest('.row').querySelector('.mostrarImg');
                                
                                    if (data.image != null){
                                        imgElement.style.display = 'block';
                                        imgElement.src = '/images/' + data.image;
                                    } else{
                                        imgElement.style.display = 'none';
                                    }
                                

                                const productoId = element.closest('.row').querySelector('.addProductoId');
                                productoId.value = data.id;
                                const productName = element.closest('.row').querySelector('.addProducto');
                                productName.value = data.name;
                
                
                            }
                            console.log("Producto seleccionado desde datalist:", value, "ID:", productId);
            });
            
            
        }
    });
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

//Cambiar vista

function cambiarVista(){
    let vistaAdmin = document.getElementById('vistaAdmin');
    let vistaUser = document.getElementById('vistaUser');

    if (vistaAdmin.style.display === 'none'){
        vistaAdmin.style.display = 'block';
        vistaUser.style.display = 'none';
        
    }else{
        vistaAdmin.style.display = 'none';
        vistaUser.style.display = 'block';

    }


}

//Añadir más productos a la solicitud
function addRow() {
    // Crear un nuevo contenedor para la nueva fila
    const newRow = document.createElement('div');
    newRow.className = 'row';
    
    // Agregar los campos de entrada
    newRow.innerHTML = `
        <div class="col-2">
            <label for="img" class="form-label">Imagen</label>
            <img class="mostrarImg" name="img" style="display: none;" width="100" height="100">
        </div>
        
        <div class="col-6">
            <label for="addproducto" class="form-label">Producto</label>
            <div class="input-group">
                <input type="text" class="form-control addProducto" list="productosList" placeholder="Elige el producto de la compra" oninput="handleInput(this, 'agregarProducto')" required>
                <input type="text" class="form-control addProductoId" placeholder="id de la compra" hidden readonly>
                <datalist id="productosList">
                    <option th:each="producto : &#36;{productos}" th:value="&#36;{producto.name}" th:data-id="&#36;{producto.id}"></option>
                </datalist>
            </div>
            <p th:if="&#36;{#fields.hasErrors('producto')}" th:errors="*{producto}" th:errorclass="text-danger"></p>
        </div>

        <div class="col-2">
            <label for="cantidad" class="form-label">Cantidad</label>
            <input type="number" class="form-control" id="cantidad" min="1" required>
        </div>
        
        <div class="col-2">
            <p class="form-label">Borrar</p>
            <button type="button" class="btn btn-danger" onclick="removeRow(this)"><i class="bi bi-trash"></i></button>
        </div>
    `;

    // Agregar la nueva fila al contenedor
    document.getElementById('solicitudesContainer').appendChild(newRow);
}

//Eliminar producto de la solicitud
function removeRow(button) {
    // Eliminar la fila que contiene el botón
    const row = button.parentNode.parentNode; // Obtiene el contenedor de la fila
    row.parentNode.removeChild(row); // Elimina la fila del DOM
}

function limpiarCampos(){

}
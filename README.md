# Proyecto Paolicias Sweet

## Descripción del proyecto

En este repositorio se trabajará el proyecto de Paolicias Sweet, el cual consta de una aplicación móvil que abarca distintas perspectivas de usuario.

La aplicación será capaz de manejar lo que ve el cliente sobre la tienda y cómo acceder a lo que ofrece la empresa. También se contempla la parte de administración, donde se abarcarán las funcionalidades que necesita la dueña, como el manejo de inventario, cuentas, costos y administración en general.

Esto significará un gran cambio debido al manejo actual de la empresa basado principalmente en papel, permitiendo avanzar hacia la digitalización de sus procesos.

Otro tipo de usuario serán los trabajadores, ya que de esta manera todos podrán manejar la misma información sobre los eventos que se encuentren reservados. Estos eventos deberán ser previamente coordinados entre el cliente y la dueña para su confirmación.

De esta forma, el trabajador podrá conocer todos los requerimientos asociados al evento, incluyendo comentarios o anotaciones específicas, tales como alergias u otras condiciones especiales.

Por lo tanto, este repositorio administrará el desarrollo de la aplicación que será utilizada por la empresa Paolicias Sweet.

Durante todo el transcurso del proyecto, este repositorio manejará las actualizaciones, implementaciones y componentes de la aplicación, tales como clases, funciones, base de datos, utilización de recursos nativos, diseños visuales mediante Material 3 y animaciones, respetando el modelo arquitectónico MVVM.

## Integrantes

- MATIAS CABRERA
- ADÁN TORRES
- GABRIEL GONZALEZ

## Funcionalidades implementadas

### Registro de usuario

- Registro de nuevos usuarios mediante formulario.
- Validación visual de los campos ingresados.
- Validación de formato de correo electrónico.
- Validación del número telefónico.
- Confirmación de contraseña.
- Prevención de registro con correos ya existentes.
- Almacenamiento de usuarios en base de datos local mediante Room.

### Inicio de sesión

- Inicio de sesión mediante correo electrónico y contraseña.
- Validación de credenciales utilizando la base de datos local.
- Mensajes de error cuando los datos ingresados son incorrectos.
- Validaciones visuales por campo.
- Inicio de sesión biométrico mediante huella digital.
- Gestión de sesión del usuario.
- Vibración como retroalimentación en acciones relevantes.
- Animación del botón de inicio de sesión según la cantidad de intentos fallidos.
- Bloqueo temporal del botón al superar el máximo de intentos permitidos.

### Perfil de usuario

- Visualización de los datos del usuario con sesión iniciada.
- Recuperación de nombre, empresa, teléfono y correo desde la base de datos local.
- Visualización de información por defecto cuando algún dato opcional no está registrado.
- Cierre de sesión desde la pantalla de perfil.
- Eliminación de la sesión activa al cerrar sesión.

### Pantalla de inicio

- Pantalla de bienvenida con imagen principal.
- Acceso a las secciones de Menú y Repostería.
- Acceso al catálogo y al perfil desde la barra inferior.

### Catálogo de productos

- Visualización de productos en tarjetas con imagen, nombre y precio.
- Separación del catálogo en Menú y Repostería.
- Filtros de Repostería: tartas, galletas y bizcochos.
- Filtros de Menú: entradas, platos de fondo y ensaladas.
- Carga de los productos desde una lista local.

### Detalle de productos

- Pantalla de detalle reutilizable para cada producto seleccionado.
- Visualización de imagen ampliada, nombre, precio e ingredientes.
- Acceso a otros productos desde la pantalla de detalle.
- Botón para regresar al catálogo.

### Selección de productos para el evento

- Botón de corazón para seleccionar productos.
- Ventana para indicar la cantidad que se desea agregar.
- Validación de una cantidad mínima de un producto.
- Cambio de color del corazón cuando el producto está agregado.
- Modificación de la cantidad de un producto seleccionado, sin duplicarlo en la lista.
- Guardado temporal de los productos y sus cantidades para integrarlos con la reserva.
- Animación al abrir la ventana de selección de cantidad.

### Registro de reservas

- Creación de reservas de eventos mediante formulario.
- Ingreso de fecha y hora con formato automático.
- Validación de fecha y hora ingresadas.
- Ingreso de cantidad de asistentes con un máximo permitido.
- Indicador visual que señala cuándo cada campo está completo y es válido.
- Mensajes de error cuando faltan datos o los valores ingresados son incorrectos.

### Observaciones especiales

- Pantalla para registrar anotaciones asociadas a condiciones especiales del evento.
- Asociación de las observaciones a la reserva en curso.
- Indicador visual cuando una reserva contiene observaciones.

### Cotización

- Cálculo del costo de cotización según la cantidad de asistentes.
- Suma de los productos seleccionados desde el catálogo.
- Visualización del costo total antes de confirmar la reserva.

### Reservas realizadas

- Almacenamiento de reservas en base de datos local mediante Room.
- Recuperación de reservas almacenadas al cerrar y volver a abrir la aplicación.
- Visualización de reservas mediante tarjetas.
- Visualización del tipo de evento, fecha, hora y cantidad de asistentes.
- Indicación de la existencia de observaciones especiales.
- Botón para crear una nueva reserva.

### Navegación

- Navegación entre creación de reservas, observaciones, catálogo y reservas realizadas.
- Acceso a las pantallas principales mediante barra de navegación inferior.
- Redirección a la pantalla de reservas realizadas después de confirmar una reserva.
- Transiciones animadas entre pantallas.

### Calendario ADMIN

- Navegación entre los días del año y los 12 meses para poder ver las reservas hechas durante el mes.
- Muestra una pequeña información sobre el evento tal como: Correo del cliente, Hora del evento, Cantidad de personas y Id de la reserva

### Productos ADMIN

- Lista con los productos de la empresa.
- Botones de crear, editar y borrar el producto.
- Botones de navegación entre productos, ingredientes, packs, todos, menú, repostería y bebestibles.
- Editar producto contiene los datos del producto, agregar ingredientes, asignar menú y seleccionar foto.
- Crear producto contiene los datos solicitados para la creación exitosa de un producto, agregar ingredientes con cantidades, asignar menú y seleccionar foto.

### Costo ADMIN

- Lista con las reservas de los clientes.
- Cheklist para administrar los eventos ya costeados.
- Al ingresar al evento se muestra un checklist de los platos y su cantidad, al desplegar la información se muestran los ingredientes con sus cantidades y el precio por unidad y abajo el precio total, mas abajo se muestra el total del plato.
- Botón de calcular costo total, muestra una lista con el plato su cantidad y consto total del plato y abajo además el costo total final del costo de ingredientes del evento.
- Botón de volver a la pantalla anterior

### Reserva ADMIN

- Lista de las reservas de los clientes con una breve información y su estado del comprobante.
- Filtro de todas, pendientes, confirmadas, canceladas, finalizadas, búsqueda por texto y actualizar.
- Al seleccionar la reserva se muestran los datos de la misma, su presupuesto y estado del abono, también se incluyen los packs o productos del evento con sus totales y la información del pago y comprobante con el pago del 50% del abono.
- Botones de ver comprobante y cancelar reserva.
- Al presionar ver comprobante se muestra los datos del evento y la foto del comprobante que se agranda el presionarla, con los botones de rechazar o aprobar el comprobante además de una observación,también salen los comprobantes revisados anteriormente. 

### Personal ADMIN

- Lista de los trabajadores de la empresa con el estado de disponibilidad.
- Selector de eventos con la cantidad de trabajadores asociados.
- Botones de crear trabajador y modificar trabajador.
- Al crear un trabajador se muestra un formulario con los datos del trabajador a rellenar y validaciones.
- Al modificar un trabajador se muestra un formulario con los datos del trabajador a rellenar, validaciones y un botón de cancelar asignaciones.
- Al seleccionar un trabajador se muestran sus datos con 3 botones, seleccionar evento que permite seleccionar un evento de la empresa y con el botón de asignar evento se confirma y se bloquea al trabajador para no asignarlo a más de uno, además del bloqueo y la información de su evento asignado, también un botón de eliminar el trabajador

### Navegación ADMMIN

- Navegación entre calendario, administración de productos, administración de costos, administración de reservas y comprobantes y administración de personal.
- Acceso a las pantallas principales mediante barra de navegación inferior.
- Transiciones animadas entre pantallas.

## Pasos para ejecutar el proyecto

1. Clonar el repositorio desde GitHub.
2. Abrir el proyecto en Android Studio.
3. Esperar la sincronización de Gradle.
4. Conectar un dispositivo Android o iniciar un emulador.
5. Ejecutar la aplicación utilizando el botón Run.
6. Para utilizar la autenticación biométrica y vibración, se recomienda ejecutar la aplicación en un dispositivo físico compatible.

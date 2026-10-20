# Proyecto Paolicias Sweet

## Descripción del proyecto

En este repositorio se trabajara el proyecto de Policías-Sweet el cual consta de una aplicación movil que abarca muchos puntos, estos puntos son las perspectivas de los usuarios, esta aplicación será capaz de manejar lo que ve el cliente sobre la tienda y como acceder a lo que ofrece la empresa, también esta la parte de administración donde acá se abarcaran las funcionalidades que necesita la dueña como el manejo de inventario, cuentas, costos, y administración en general lo cual va a significar un gran cambio debido al manejo actual de la dueña en base a papel, logrando la digitalización que necesita su empresa , otro usuarios van a hacer los trabajadores debido a que de esta manera todos manejaran la misma información sobre los eventos que están reservados, los cuales previamente deben coordinar el cliente y a la dueña por la confirmación, de esta forma el trabajador sabrá todo lo que requiere el cliente para el evento incluyendo comentarios o anotaciones específicas tales como alergias, por lo tanto este repositorio va administrar la aplicación que será utilizada por la empresa Paolicías-Sweet.

Durante todo el transcurso del proyecto este repositorio manejara las actualizaciones, implementaciones, y componentes de la aplicación, tales como las clases, funciones, base de datos, utilización de recursos nativos, diseños visuales en Material 3, animaciones, todo esto respetando el modelo arquitectónico MVVM.

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

## Pasos para ejecutar el proyecto

1. Clonar el repositorio desde GitHub.
2. Abrir el proyecto en Android Studio.
3. Esperar la sincronización de Gradle.
4. Conectar un dispositivo Android o iniciar un emulador.
5. Ejecutar la aplicación utilizando el botón Run.
6. Para utilizar la autenticación biométrica y vibración, se recomienda ejecutar la aplicación en un dispositivo físico compatible.

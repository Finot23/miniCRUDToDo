# Documentación del Proyecto: miniCRUDToDo

## Introducción

Este proyecto es una aplicación de escritorio desarrollada en Java que permite gestionar tareas personales o laborales. Ofrece funcionalidades esenciales para crear, modificar, eliminar y visualizar tareas, incluyendo información clave como título, descripción, estado y fecha límite. Su objetivo es proporcionar una herramienta sencilla y efectiva para que los usuarios organicen y controlen sus pendientes.

## Alcance

### Funcionalidades principales:
* **Registrar nuevas tareas:** Permite añadir nuevas entradas de tareas a la base de datos con todos sus atributos.
* **Modificar tareas existentes:** Facilita la edición de los detalles de tareas ya registradas.
* **Eliminar tareas:** Permite la eliminación de tareas específicas de la base de datos.
* **Listar todas las tareas:** Muestra una vista completa de todas las tareas almacenadas en la base de datos en una tabla.

### Límites del sistema:
* No incluye manejo multiusuario ni funcionalidades de autenticación/autorización.
* No implementa sincronización en red o capacidades de trabajo colaborativo.
* No envía notificaciones o recordatorios automáticos para fechas límite.
* No incluye una funcionalidad de "Buscar tareas por ID" explícita en la GUI, aunque la estructura de datos lo permitiría fácilmente.

## Tecnologías usadas

* **Lenguaje:** Java
* **IDE:** Apache NetBeans
* **Base de datos:** MySQL / MariaDB (el proyecto está configurado para MySQL)
* **Conexión a Base de Datos:** JDBC (Java Database Connectivity)
* **Arquitectura:** Aunque simplificada para el alcance del proyecto, sigue principios del patrón Modelo-Vista-DAO (Data Access Object), donde `Tarea` es el Modelo, `ConexionDB` y `TareaDAO` forman la capa de acceso a datos, y `TareaGUI` es la Vista y Controlador de la UI.
* **Librerías GUI:** Java Swing

## Requisitos del sistema

* **Java Development Kit (JDK):** Versión 8 o superior instalado.
* **Servidor de Base de Datos:** MySQL o MariaDB funcionando y accesible.
* **NetBeans IDE:** (Opcional, para desarrollo y ejecución fácil).
* **Sistema Operativo:** Compatible con Java (Windows, Linux, macOS).

## Requisitos funcionales

* **CRUD completo para tareas:** La aplicación debe soportar la creación (Create), lectura (Read), actualización (Update) y eliminación (Delete) de tareas.
* **Visualización en tablas:** Las tareas deben mostrarse en una tabla interactiva en la interfaz gráfica.

## Requisitos no funcionales

* **Interfaz amigable y sencilla:** La GUI debe ser intuitiva y fácil de usar para el usuario final.
* **Respuesta rápida a operaciones:** Las operaciones de base de datos deben ejecutarse de manera eficiente.
* **Código modular y mantenible:** La estructura del proyecto debe permitir una fácil comprensión y futuras modificaciones.

### Casos de Uso:

* **Gestión de Tareas:**
    * **Crear Tarea:** El usuario selecciona la opción para añadir una nueva tarea. Se añade una fila vacía a la tabla y, al "Guardar", se inserta en la DB.
    * **Modificar Tarea:** El usuario edita los campos de una tarea existente directamente en la tabla y, al "Guardar", los cambios se persisten en la DB.
    * **Eliminar Tarea:** El usuario selecciona una o varias tareas de la tabla y, al "Eliminar", estas se borran de la DB.
    * **Listar Tareas:** Al iniciar la aplicación y después de cada operación de modificación de datos, todas las tareas se cargan y se muestran en la tabla.

## Diseño

La aplicación se diseñó con una única ventana principal (`TareaGUI`) que presenta una tabla para visualizar las tareas. Los botones en la parte inferior de la tabla permiten realizar las operaciones CRUD. La interfaz es intuitiva y orientada a la interacción directa con la tabla.

* **Wireframes/Mockups (Conceptual):**
    * Ventana principal con un título en la parte superior.
    * Una tabla (`JTable`) grande que ocupa la mayor parte de la ventana, mostrando las columnas: ID, Título, Descripción, Estado, Fecha Límite.
    * Tres botones principales debajo de la tabla: "Agregar nueva tarea", "Eliminar Tarea", "Guardar tarea".

## Conclusiones

### Aprendizajes clave:
* Manejo de bases de datos relacionales (MySQL) utilizando JDBC para operaciones CRUD.
* Diseño y desarrollo de interfaces gráficas de usuario con Java Swing y `JTable` para la visualización y edición de datos.
* Implementación del patrón Data Access Object (DAO) para desacoplar la lógica de acceso a datos de la lógica de negocio y la interfaz de usuario.
* Manejo de objetos `Date` y su conversión a `java.sql.Timestamp` para la persistencia en la base de datos.
* Control de excepciones `SQLException` para un manejo robusto de errores de base de datos.

### Problemas encontrados y soluciones:
* **Formato de fechas:** La conversión entre `java.util.Date` (para el modelo) y `java.sql.Timestamp` (para la base de datos) requirió el uso de `SimpleDateFormat` para la entrada del usuario en la GUI y la conversión de `Timestamp` a `Date` al leer de la DB. Se implementó validación de formato de fecha al intentar guardar.
* **Manejo de datos nulos en SQL (fechas):** Se aseguró que los campos de fecha puedan ser `null` tanto en el modelo (`Tarea.fechaLimite`) como al setear y obtener `Timestamp` del `PreparedStatement` y `ResultSet` para evitar `NullPointerException`.
* **Actualización de ID autoincremental:** Al insertar una nueva tarea, se utilizó `Statement.RETURN_GENERATED_KEYS` para recuperar el ID asignado por la base de datos y actualizar el objeto `Tarea` en memoria, reflejándolo inmediatamente en la GUI.

## Instrucciones para ejecutar

Para ejecutar este proyecto, sigue los siguientes pasos:

1.  **Configuración de la Base de Datos:**
    * Asegúrate de tener un servidor MySQL o MariaDB en ejecución.
    * Crea una base de datos llamada `tarea_dao`.
    * Ejecuta el siguiente script SQL para crear la tabla `tareas`:
        ```sql
        CREATE TABLE tareas (
            id INT AUTO_INCREMENT PRIMARY KEY,
            titulo VARCHAR(255) NOT NULL,
            descripcion TEXT,
            estado VARCHAR(50) NOT NULL,
            fecha_limite DATETIME
        );
        ```
    * **Nota:** Se recomienda `DATETIME` en lugar de `DATE` para `fecha_limite` en la base de datos para una mejor compatibilidad con `java.sql.Timestamp`, aunque `DATE` también funcionaría si solo necesitas la fecha sin la hora.

2.  **Ajuste de Credenciales de Conexión:**
    * Abre la clase `ConexionDB.java`.
    * Verifica y ajusta si es necesario los valores de `DB_URL`, `USER`, y `PASS` para que coincidan con tu configuración de base de datos:
        ```java
        private static final String DB_URL = "jdbc:mysql://localhost:3306/tarea_dao";
        private static final String USER = "tu_usuario_db"; // Por ejemplo: "root" o "admin"
        private static final String PASS = "tu_contrasena_db"; // Por ejemplo: "password" o "admin"
        ```

3.  **Configuración del MySQL Connector/J:**
    * Si usas Maven (que es lo que hace NetBeans por defecto para proyectos generados así), asegúrate de que la dependencia `mysql-connector-j` esté en tu `pom.xml`. Si no lo está, agrégala (la versión puede variar):
        ```xml
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.0.33</version> </dependency>
        ```
    * Si no usas Maven, descarga el JAR de MySQL Connector/J y añádelo manualmente a las librerías de tu proyecto en NetBeans (clic derecho en "Libraries" > "Add JAR/Folder...").

4.  **Colocación de Recursos (Imágenes):**
    * Asegúrate de que el archivo `mainIcon.png` esté ubicado correctamente en la carpeta de recursos de tu proyecto. La ruta esperada es:
        `src/main/resources/com/mycompany/todo/imagenes/mainIcon.png`
    * Si no lo tienes, la aplicación se ejecutará pero no mostrará el icono. Puedes descargar uno simple o comentar la línea en `TareaGUI.java` que carga el icono.

5.  **Compilar y Ejecutar:**
    * **Desde NetBeans:**
        * Haz clic derecho en tu proyecto (`ToDo`).
        * Selecciona "Clean and Build" para asegurarte de que todos los cambios (incluyendo los recursos) se compilen.
        * Haz clic derecho en tu proyecto (`ToDo`) nuevamente y selecciona "Run" o haz clic en el botón de "Run Project" (triángulo verde).
    * **Desde la línea de comandos (Maven):**
        * Navega hasta la raíz de tu proyecto (`miniCRUDToDo/ToDo`).
        * Ejecuta: `mvn clean install`
        * Luego: `mvn exec:java -Dexec.mainClass="com.mycompany.todo.ToDo"`

La ventana de la aplicación `TareaGUI` debería aparecer, mostrando la tabla de tareas.

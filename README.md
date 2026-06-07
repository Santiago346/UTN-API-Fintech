# UTN-API-Fintech


API REST desarrollada con Spring Boot para la gestión de usuarios, cuentas bancarias y consulta de cotizaciones del dólar.

## Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Docker
- Swagger/OpenAPI
- Maven
- JUnit 5
- Mockito

## Funcionalidades

### Usuarios
- Crear usuario

### Cuentas
- Crear cuenta bancaria
- Obtener cuenta por ID
- Listar cuentas
- Actualizar cuenta
- Eliminar cuenta
- Consultar saldo en pesos utilizando la cotización del dólar MEP

### Cotización del dólar
- Consulta de cotización mediante DolarAPI
- Conversión de saldos en USD a ARS

## Configuración

Ejecutar MySQL con Docker:

```bash
docker run --name mysql-fintech \
-e MYSQL_ROOT_PASSWORD=root \
-e MYSQL_DATABASE=utn \
-p 3311:3306 \
-d mysql:8
```

## Variables de entorno

El proyecto utiliza un archivo `.env` en la raíz para la configuración externa.

Spring Boot carga estas variables automáticamente mediante:

```yaml
spring.config.import=optional:file:.env[.properties]
```

## Ejecución

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## Tests

```powershell
.\mvnw.cmd test
```

## Documentación API

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

## Autor
Santiago Elian Kantor

Trabajo práctico desarrollado para la UTN.
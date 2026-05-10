# NutriWeb (TFG) - App de seguimiento nutricional

Aplicacion full-stack para evaluacion nutricional y generacion de un plan diario de macros.

- Frontend: Astro (paginas + JS en cliente) en `frontend/`
- Backend: Spring Boot (4.1.0-SNAPSHOT) + Spring MVC + Spring Data JPA en `backend/`
- Base de datos: PostgreSQL (el proyecto usa Supabase como proveedor en desarrollo)

## Requisitos

- Node.js (recomendado: 18+)
- Java 21
- (Opcional) Maven si no usas el wrapper (`mvnw`)
- PostgreSQL (local o Supabase)

## Puertos por defecto

- Frontend (Astro dev): `http://localhost:4321`
- Backend (Spring Boot): `http://localhost:8080`

## Arranque rapido (desarrollo)

### 1) Backend

1. Configura la conexion a PostgreSQL.

   El backend lee `spring.datasource.*` desde `backend/src/main/resources/application.properties`.

   Recomendado para desarrollo:
   - Crea variables de entorno (`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`) o
   - Usa un fichero no versionado tipo `application-local.properties` y ejecuta con perfil/override.



2. Arranca el backend:

   Windows:
   ```powershell
   cd backend
   .\mvnw.cmd spring-boot:run
   ```

   Linux/macOS:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

### 2) Frontend

1. Instala dependencias y arranca Astro:

```bash
cd frontend
npm install
npm run dev
```


## Endpoints (Backend)

Base URL: `http://localhost:8080`

- `GET /api/alimentos`
  - Devuelve la lista completa de alimentos.

- `POST /api/auth/login`
  - Body JSON:
    ```json
    {"email":"...","password":"..."}
    ```
  - Respuesta 200: `LoginResponse` (id, nombre, email, rol, mensaje)

- `POST /api/calculo`
  - Body JSON (campos requeridos):
    ```json
    {"peso":70,"altura":175,"edad":30,"sexo":"masc"}
    ```
  - Campo opcional: `email` (si existe, guarda el registro asociado al usuario)
  - Respuesta 200: `{ "imc": ..., "calorias": ..., "masaMuscular": ... }`

- `GET /api/userdata/{email}`
  - Devuelve perfil + calculos (imc, calorias, masaMuscular) si hay datos suficientes.

- `POST /api/userdata`
- `PUT /api/userdata`
  - Body JSON (UserDataRequest):
    ```json
    {
      "email":"...",
      "edad":30,
      "sexo":"masc",
      "peso":70,
      "altura":175,
      "nivelActividad":"moderado",
      "objetivo":"mantenimiento"
    }
    ```

- `GET /api/macros?sexo=...&peso=...&altura=...&edad=...&actividad=...&objetivo=...`
  - Devuelve un mapa con la distribucion diaria por comida (`desayuno`, `almuerzo`, `preentreno`, `cena`).

## Modelo de datos (JPA)

Entidades principales en `backend/src/main/java/com/nutriweb100/model`:

- `Usuario` (`usuarios`)
- `Alimento` (`alimentos`) con `categoria` como `text[]`
- `RegistroNutricional` (`registros_nutricionales`)
- `RegistroAlimento` (`registro_alimentos`)

En desarrollo, Hibernate esta configurado con `spring.jpa.hibernate.ddl-auto=update`, asi que crea/actualiza tablas automaticamente en la BD configurada.

## Estructura del repo

```text
TFG_NutriWeb_MMT/
  backend/   # Spring Boot API
  frontend/  # Astro web
```



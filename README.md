# NutriWeb (TFG) - App de seguimiento nutricional

Aplicación full-stack para evaluación nutricional, generación de un plan diario de macros y seguimiento personalizado.
Con una interfaz moderna, minimalista (estilo Notion) y completamente adaptable a los objetivos del usuario.

## Características Principales

- **Evaluación Nutricional:** Captura de datos biométricos y cálculo automático de IMC, calorías recomendadas y masa muscular estimada.
- **Plan Nutricional Personalizado:** Generación de un plan de macronutrientes distribuidos en comidas diarias (Desayuno, Almuerzo, Pre-entreno, Cena) según sexo, peso, altura, edad, actividad y objetivo.
- **Gestión de Objetivos:** Modificación dinámica de objetivos (Pérdida de grasa, Mantenimiento, Ganancia muscular) y datos físicos, con actualización en tiempo real del plan.
- **Consejos Personalizados:** Página de recomendaciones dinámicas basadas en el objetivo nutricional actual del usuario.
- **Diseño Minimalista:** Interfaz limpia, clara y profesional inspirada en Notion. Botones redondeados, temas claros con fondos sutiles y modales personalizados para una experiencia premium.
- **Seguridad y Sincronización:** Integración de API con cabeceras `X-API-KEY`, bypass de caché del navegador para datos frescos, y sincronización fluida entre base de datos y DOM.

## Tecnologías

- **Frontend:** Astro, HTML, Vanilla CSS, JS Vanilla. (Directorio `frontend/`)
- **Backend:** Spring Boot (4.1.0-SNAPSHOT), Spring MVC, Spring Data JPA. (Directorio `backend/`)
- **Base de Datos:** PostgreSQL (usando Supabase en desarrollo/producción).
- **Despliegue:** Preparado para despliegue en plataformas como Railway (con configuración de variables de entorno).

## Requisitos

- Node.js (recomendado: 18+)
- Java 21
- PostgreSQL (local o Supabase)
- (Opcional) Maven si no usas el wrapper (`mvnw`)

## Puertos por defecto

- Frontend (Astro dev): `http://localhost:4321`
- Backend (Spring Boot): `http://localhost:8080`

## Arranque rápido (desarrollo)

### 1) Backend

1. Configura la conexión a PostgreSQL.

   El backend lee las propiedades desde `backend/src/main/resources/application.properties`.
   Además, usa una API Key para asegurar los endpoints: `X-API-KEY`.

   Recomendado para desarrollo:
   - Crea variables de entorno (`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `API_KEY`) o
   - Usa un fichero no versionado tipo `application-local.properties` y ejecuta con perfil.

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

1. Configura el archivo `.env` en la raíz de `frontend/` con las variables necesarias:
   ```env
   PUBLIC_API_URL=http://localhost:8080
   PUBLIC_API_KEY=tu_api_key_aqui
   ```

2. Instala dependencias y arranca Astro:

   ```bash
   cd frontend
   npm install
   npm run dev
   ```

## Endpoints Principales (Backend)

Base URL: `http://localhost:8080`
*(Requieren cabecera `X-API-KEY`)*

- `POST /api/auth/login`: Autenticación de usuarios.
- `POST /api/calculo`: Cálculo inicial de métricas biométricas.
- `GET /api/userdata/{email}`: Obtiene perfil y cálculos del usuario.
- `POST /api/userdata` y `PUT /api/userdata`: Creación y actualización de datos físicos y objetivos.
- `GET /api/macros`: Obtiene distribución de macronutrientes diaria según los datos del usuario.
- `GET /api/alimentos`: Lista de alimentos disponibles en base de datos.

## Modelo de datos (JPA)

Entidades principales en `backend/src/main/java/com/nutriweb100/model`:
- `Usuario`
- `Alimento`
- `RegistroNutricional`
- `RegistroAlimento`

## Estructura del repositorio

```text
TFG_NutriWeb_MMT/
  backend/   # Spring Boot API
  frontend/  # Astro Web App
```

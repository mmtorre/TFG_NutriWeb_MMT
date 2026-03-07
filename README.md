# 🥗 NutriTrack - Sistema de Control Nutricional

![Estado](https://img.shields.io/badge/Estado-En_Desarrollo-green?style=flat-square)
![Licencia](https://img.shields.io/badge/Licencia-MIT-blue?style=flat-square)
![Versión](https://img.shields.io/badge/Versión-1.0.0-orange?style=flat-square)

> **NutriTrack** es una aplicación full-stack diseñada para monitorizar tu salud. Ingresa tus datos diarios (peso, ingesta calórica, macros) y nuestro motor de análisis te devolverá resultados personalizados y proyecciones de salud.

---

## 🛠️ Tecnologías (Tech Stack)

El proyecto utiliza una arquitectura moderna y escalable:

### Frontend
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)

### Backend
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)

### Base de Datos & Nube
![Supabase](https://img.shields.io/badge/Supabase-3ECF8E?style=for-the-badge&logo=supabase&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

---

## ✨ Funcionalidades

* 🔐 **Autenticación Segura:** Registro e inicio de sesión gestionado vía Supabase Auth.
* 📊 **Dashboard Interactivo:** Visualización gráfica de la evolución del peso y calorías.
* 🍎 **Calculadora Nutricional (Backend Java):** Algoritmos en Java que procesan la Tasa Metabólica Basal (TMB) y el Déficit/Superávit calórico.
* 📅 **Histórico:** Base de datos persistente de todas las comidas y pesajes del usuario.

---

## 🚀 Instalación y Configuración

Sigue estos pasos para levantar el entorno de desarrollo local.

### 📋 Prerrequisitos

* [Node.js](https://nodejs.org/) (v16+)
* [Java JDK](https://www.oracle.com/java/technologies/downloads/) (v17+)
* [Maven](https://maven.apache.org/) (o usar el wrapper incluido)
* Cuenta en [Supabase](https://supabase.com/)

### 1. Configuración de Base de Datos (Supabase)

1.  Crea un proyecto en Supabase.
2.  Ve al **SQL Editor** y ejecuta el script de creación de tablas (ubicado en `/database/schema.sql`).
3.  Copia las credenciales de tu proyecto: `Project URL` y `API Key` (service_role o anon).

### 2. Configuración del Backend (Java)

1.  Entra en la carpeta del servidor:
    ```bash
    cd backend
    ```
2.  Configura las variables de entorno en `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://<TU_HOST_SUPABASE>:5432/postgres
    spring.datasource.username=postgres
    spring.datasource.password=<TU_PASSWORD_DB>
    
    # Supabase API config (si es necesario para el cliente Java)
    supabase.url=<TU_PROYECTO_URL>
    supabase.key=<TU_API_KEY>
    ```
3.  Ejecuta la aplicación:
    ```bash
    ./mvnw spring-boot:run
    ```
prueba
### 3. Configuración del Frontend (React)

1.  Entra en la carpeta del cliente:
    ```bash
    cd frontend
    ```
2.  Instala las dependencias:
    ```bash
    npm install
    ```
3.  Crea un archivo `.env` en la raíz y añade tus claves:
    ```env
    VITE_SUPABASE_URL=[https://tu-proyecto.supabase.co](https://tu-proyecto.supabase.co)
    VITE_SUPABASE_ANON_KEY=tu-clave-anonima
    VITE_API_URL=http://localhost:8080/api
    ```
4.  Inicia el servidor de desarrollo:
    ```bash
    npm run dev
    ```

---

## 📂 Estructura del Proyecto

```text
root/
├── 📂 backend/         # API REST en Java Spring Boot
│   ├── 📂 src/main/java
│   │   ├── 📂 controller   # Endpoints (Recibe datos de React)
│   │   ├── 📂 service      # Lógica de nutrición
│   │   └── 📂 model        # Entidades JPA
│   └── 📄 pom.xml
│
├── 📂 frontend/        # SPA en React
│   ├── 📂 src/
│   │   ├── 📂 components   # Gráficos, Formularios
│   │   ├── 📂 hooks        # Lógica de Supabase
│   │   └── 📂 pages        # Vistas (Home, Login, Dashboard)
│   └── 📄 package.json
│
└── 📂 database/        # Scripts SQL
    └── 📄 schema.sql

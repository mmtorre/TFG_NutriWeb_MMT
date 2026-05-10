# NutriWeb - Frontend (Astro)

Frontend en Astro. Renderiza paginas en `src/pages/` y ejecuta logica en cliente para llamar al backend Spring Boot.

## Requisitos

- Node.js (recomendado: 18+)

## Scripts

```bash
npm install
npm run dev      # http://localhost:4321
npm run build
npm run preview
```

## Configuracion de API

Actualmente las llamadas al backend estan hardcodeadas a `http://localhost:8080` en varios ficheros dentro de `src/pages/*.astro`.

Para despliegue o para cambiar el host, se recomienda:

1. Introducir una variable de entorno de Astro (por ejemplo `PUBLIC_API_BASE_URL`).
2. Sustituir las URLs hardcodeadas por `import.meta.env.PUBLIC_API_BASE_URL`.

## Paginas relevantes

- `/login`
- `/evaluacion`
- `/plan`
- `/alimentos`
- `/consejos`
- `/modificar-objetivo`


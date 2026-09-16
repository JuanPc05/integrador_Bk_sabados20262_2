# Integrador Backend — CESDE

Backend en Java + Spring Boot del Proyecto Integrador.
Parte de la arquitectura multi-repo (un repositorio por tecnología).

## 🛠️ Stack

| Componente   | Versión  |
|--------------|----------|
| Java (JDK)   | 21 (LTS) |
| Spring Boot  | 4.1.1    |
| Maven        | vía wrapper (no requiere instalación) |

## ✅ Requisitos previos

Solo necesitas **Java 21** y **Git**. Maven lo maneja el wrapper del proyecto.

### Windows
```powershell
winget install EclipseAdoptium.Temurin.21.JDK
winget install Git.Git
```
### Ubuntu / Linux
```bash
sudo apt update
sudo apt install openjdk-21-jdk git
```
Verifica en ambos casos:
```bash
java -version   # debe mostrar "21"
```

## 🚀 Cómo ejecutar

Clona el repositorio:
```bash
git clone <URL-DEL-REPO>
cd integrador-backend
```

Levanta la aplicación (usa el wrapper según tu SO):

**Windows (CMD / PowerShell):**
```powershell
mvnw.cmd spring-boot:run
```
**Ubuntu / Linux / Mac:**
```bash
./mvnw spring-boot:run
```
La app queda en http://localhost:8080

## 🔨 Comandos útiles

| Acción            | Windows                 | Linux/Mac              |
|-------------------|-------------------------|------------------------|
| Compilar y empaquetar | `mvnw.cmd clean package` | `./mvnw clean package` |
| Ejecutar tests    | `mvnw.cmd test`         | `./mvnw test`          |

## 📁 Estructura

src/main/java/com/cesde/integrador # código de la aplicación
src/main/resources # application.properties, etc.
src/test/java # pruebas


## 🌿 Convenciones de Git (obligatorias)

**Ramas:**
- `main` — estable, siempre desplegable. Nadie commitea directo.
- `feature/<historia>` — nueva funcionalidad. Ej: `feature/login-usuario`
- `fix/<descripcion>` — corrección de bug.

**Commits (Conventional Commits):**

## 🌿 Convenciones de Git (obligatorias)

**Ramas:**
- `main` — estable, siempre desplegable. Nadie commitea directo.
- `feature/<historia>` — nueva funcionalidad. Ej: `feature/login-usuario`
- `fix/<descripcion>` — corrección de bug.

**Commits (Conventional Commits):**
feat: agrega endpoint de registro de usuario
fix: corrige validación de correo
docs: actualiza README con pasos de instalación
refactor: extrae lógica de precios a un servicio
test: agrega pruebas del repositorio de usuarios


**Flujo:** ramificas desde `main` → trabajas → abres Pull Request → se revisa → merge.

## 👥 Equipo
- Juan — Scrum Master
- (compañero 2)
- (compañero 3)
- (compañero 4)
- (compañero 5)

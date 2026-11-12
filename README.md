# ☕ CESDE Conecta — Backend Core (`integrador_Bk_sabados20262_2`)

Backend transaccional desarrollado en **Java 21 + Spring Boot 3.5 + Spring Security 6 + MySQL 8** para la plataforma de innovación abierta **CESDE Conecta**. Forma parte del ecosistema integrado junto con el **Frontend en React (`:5173`)** y el **Motor de Analítica & ETL en Python/FastAPI (`:8000`)**.

---

## 🏗️ Arquitectura del Sistema Integrado

| Microservicio | Directorio | Tecnologías Clave | Puerto | Función Principal |
| :--- | :--- | :--- | :---: | :--- |
| **1. Backend Core (Este repo)** | `integrador_Bk_sabados20262_2` | Java 21, Spring Boot 3.5, Spring Security, JWT, JPA/Hibernate, MySQL | `8080` | Autenticación JWT Stateless, Control de Acceso Basado en Roles (RBAC), Máquina de Estados Finita (FSM) de Retos y persistencia relacional en `proyecta_db`. |
| **2. Motor Analítica & ETL** | `integrador_API` | Python 3.10+, FastAPI, Uvicorn, Pandas, Faker (`es_CO`) | `8000` | Poblado masivo de datos sintéticos vía REST/JWT (`etl/seeder.py`) y cálculo vectorizado de KPIs ejecutivos (`/api/v1/metrics/dashboard`). |
| **3. Frontend Web** | `integrador_Ft_sabado20262_2` | React 19, Vite, Tailwind CSS, Axios, Recharts | `5173` | Vitrina pública de retos (`/retos`), Landing Page con analítica en vivo y paneles administrativos/empresariales. |

---

## 🛠️ Prerrequisitos Exactos

| Herramienta | Versión Recomendada | Verificación |
| :--- | :--- | :--- |
| **Java JDK** | **Java 21 LTS** | `java -version` |
| **Maven** | **3.9+** (o usar el wrapper `./mvnw` incluido) | `mvn -version` |
| **MySQL / MariaDB** | **MySQL 8.0+** (o MariaDB 10.6+) | `mysql --version` |
| **Node.js** *(para Frontend)* | **v18.18+ / v20 LTS** | `node -v` |
| **Python** *(para Analítica)* | **Python 3.10+** | `python --version` |

---

## 🗄️ Paso 1: Base de Datos (`proyecta_db`)

### 1.1. Crear la base de datos e importar `usuarios.sql`
Abre tu cliente MySQL o terminal y ejecuta:

```sql
CREATE DATABASE IF NOT EXISTS proyecta_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

Para importar el archivo `usuarios.sql` manualmente:

```bash
mysql -u root -p proyecta_db < usuarios.sql
```

> 💡 **Sembrado Automático (`DataSeedConfig.java`):** Al iniciar Spring Boot con `spring.jpa.hibernate.ddl-auto=update`, las tablas se crean automáticamente y `DataSeedConfig` inserta de forma idempotente las **Categorías Académicas**, **Prioridades SLA**, la empresa **Tech Antioquia SAS** y los usuarios iniciales con contraseña encriptada en BCrypt.

---

## ⚙️ Paso 2: Configuración y Ejecución del Backend (`Puerto 8080`)

### 2.1. Configurar `src/main/resources/application.properties`

```properties
spring.application.name=proyecta-backend

# Conexión JDBC a MySQL / MariaDB (crea proyecta_db si no existe)
spring.datasource.url=jdbc:mysql://localhost:3306/proyecta_db?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC

# Credenciales de MySQL (por variable de entorno o valor por defecto tras ':')
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASSWORD:}

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 2.2. Instalar dependencias y ejecutar el servidor

```bash
cd integrador_Bk_sabados20262_2

# 1. Instalar dependencias y empaquetar (Windows PowerShell / CMD)
.\mvnw.cmd clean install -DskipTests
# En Linux / macOS / Git Bash:
./mvnw clean install -DskipTests

# 2. Levantar el servidor en http://localhost:8080
.\mvnw.cmd spring-boot:run
# En Linux / macOS / Git Bash:
./mvnw spring-boot:run
```

* **API Base:** `http://localhost:8080`
* **Swagger UI:** `http://localhost:8080/swagger-ui.html`

---

## 🐍 Paso 3: Motor Python / FastAPI (`Puerto 8000`)

```bash
cd ../integrador_API

# 1. Crear y activar el entorno virtual
python -m venv venv
# Windows PowerShell:
.\venv\Scripts\Activate.ps1
# Linux / macOS / Git Bash:
source venv/bin/activate

# 2. Instalar librerías requeridas
pip install fastapi "uvicorn[standard]" pandas faker requests pydantic mysql-connector-python

# 3. (Opcional) Poblar base de datos con 10 empresas, 15 usuarios y 20 retos
python -m etl.seeder

# 4. Arrancar el servidor de analítica en el puerto 8000
uvicorn api.main:app --reload --port 8000
```

---

## ⚛️ Paso 4: Frontend React / Vite (`Puerto 5173`)

```bash
cd ../integrador_Ft_sabado20262_2

# 1. Instalar dependencias
npm install

# 2. Iniciar servidor de desarrollo Vite en http://localhost:5173
npm run dev
```

---

## 🚦 Orden Estricto de Ejecución

1. **MySQL (`:3306`)** → Asegurar que el motor de base de datos esté encendido.
2. **Backend Spring Boot (`:8080`)** → Levantar primero para exponer los endpoints REST, JWT y crear las tablas/semillas.
3. **Motor Python FastAPI (`:8000`)** → Ejecutar `python -m etl.seeder` (si se requiere poblar datos) y levantar `uvicorn api.main:app --reload --port 8000`.
4. **Frontend React (`:5173`)** → Levantar `npm run dev` al final para consumir sin errores tanto Spring Boot (`:8080`) como FastAPI (`:8000`).

---

## 🔐 Credenciales de Prueba (RBAC & Máquina de Estados)

| Rol en Plataforma | Correo Electrónico (`email`) | Contraseña (`password`) | Permisos y Reglas de Negocio |
| :--- | :--- | :--- | :--- |
| **`ADMIN_UNIVERSIDAD`** | `admin.conecta@cesde.edu.co` | `Cesde2026*` | Ve todos los retos (`findAll`), gestiona catálogos, elige cualquier empresa al crear retos y ejecuta cualquier transición de estado (`EN_DESARROLLO`, `FINALIZADO`, `CANCELADO`, `APLAZADO`). |
| **`REPRESENTANTE_EMPRESA`** | `representante@techantioquia.com` | `Cesde2026*` | Vinculado a **Tech Antioquia SAS**. Solo ve retos de su `idEmpresa`, tiene el selector de empresa bloqueado a su organización y transiciona de `BORRADOR -> EN_CONVOCATORIA` o `CANCELADO`. |
| **`ESTUDIANTE`** | `estudiante.conecta@cesde.edu.co` | `Cesde2026*` | Ve únicamente retos públicos en estado `EN_CONVOCATORIA` para postular soluciones. |
| **Público (Sin Token)** | *Vitrina Pública (`/retos`)* | *No requiere* | `GET /api/retos` y `GET /api/empresas` están abiertos (`permitAll`) devolviendo solo retos `EN_CONVOCATORIA`. |

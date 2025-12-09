# Software Logging & Observability (TP3)

This project demonstrates the implementation of a full observability pipeline using **Structured Logging** for user profiling and **Distributed Tracing** for end-to-end transaction monitoring.

## 🏗️ Architecture
The system consists of three distinct components:
1.  **Backend (Java/Spring Boot):** Instrumented with the OpenTelemetry Java Agent (Auto-Instrumentation).
2.  **Frontend (React):** Instrumented with Manual W3C Trace Context propagation to simulate client-side tracing.
3.  **Infrastructure (Jaeger):** A Dockerized backend for collecting and visualizing traces.

---

## ✅ Prerequisites
Before running the project, ensure you have the following installed:
* **Java JDK 17+** (Required for Spring Boot 3)
* **Node.js** (v18 or higher recommended)
* **Docker Desktop** (Must be installed and **RUNNING**)
* **Eclipse IDE** (or IntelliJ)

---

## 🛠️ Installation & Setup

### 1. Infrastructure Setup (Jaeger)
We use Jaeger to visualize the distributed traces.
1.  Open a terminal in the project root (where `docker-compose.yaml` is located).
2.  Start the container:
    ```bash
    docker compose up -d
    ```
    *(Note: If `docker compose` fails, try `docker-compose up -d`)*
3.  **Verify:** Open [http://localhost:16686](http://localhost:16686). You should see the Jaeger UI.

### 2. Backend Setup (Spring Boot)
The backend requires the **OpenTelemetry Java Agent** to inject tracers at runtime.

1.  **Download the Agent:**
    Ensure `opentelemetry-javaagent.jar` is present in the project root.
    *(Download from: https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar)*

2.  **Configure Eclipse:**
    * Right-click `com.logging.tp.ObservabilityApp` -> **Run As** -> **Run Configurations...**
    * Select the **Arguments** tab.
    * In the **VM Arguments** box, paste the following configuration (Update `/PATH/TO/` to your actual file path):

    ```text
    -javaagent:/ABSOLUTE/PATH/TO/opentelemetry-javaagent.jar
    -Dotel.service.name=backend-store
    -Dotel.traces.exporter=otlp
    -Dotel.exporter.otlp.protocol=grpc
    -Dotel.exporter.otlp.endpoint=http://localhost:4317
    -Dotel.metrics.exporter=none
    -Dotel.logs.exporter=none
    ```

3.  **Run:** Click "Run". The console should show `[otel.javaagent]` logs at startup.

### 3. Frontend Setup (React)
1.  Open a terminal in the `frontend-observability` folder.
2.  Install dependencies:
    ```bash
    npm install
    ```
3.  Start the development server:
    ```bash
    npm start
    ```
4.  **Verify:** The browser should open [http://localhost:3000](http://localhost:3000).

---

## 🧪 How to Test

### Exercise 1: User Profiling (Structured Logging)
* **Goal:** Detect if a user is a "Reader" or "Writer".
* **Action:** Run the `MainApp.java` class (Java Application).
* **Result:** Check the console output or `application.log`.
    ```text
    User U1: [READER PROFILE] (Reads > Writes)
    User U2: [WRITER PROFILE] (Writes > Reads)
    ```

### Exercise 2: Distributed Tracing
* **Goal:** Trace a request from React to Spring Boot.
* **Step 1:** Ensure Backend, Frontend, and Jaeger are running.
* **Step 2:** On the Frontend ([localhost:3000](http://localhost:3000)):
    * Click **"Load Products"** (Generates GET requests).
    * Click **"Add Random Product"** (Generates POST requests).
* **Step 3:** On Jaeger ([localhost:16686](http://localhost:16686)):
    * Refresh the page.
    * Select Service: **`backend-store`**.
    * Click **Find Traces**.
    * Click on a trace to see the waterfall view connecting the simulated client to the backend.

---

## 🐞 Troubleshooting

* **"Safari Can't Connect" (Jaeger):** Ensure Docker Desktop is running and you executed `docker compose up`.
* **"Blank Page" on Frontend:** Ensure `App.js` does not contain conflicting OpenTelemetry imports (use the manual propagation version provided).
* **No Traces in Jaeger:**
    * Check Eclipse console for `[otel.javaagent]` errors.
    * Ensure the VM Argument `endpoint` is set to `http://localhost:4317` (gRPC), not 4318.
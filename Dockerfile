FROM openjdk:17

WORKDIR /app

# Instalar librerías necesarias para JavaFX GUI
RUN apt-get update && apt-get install -y \
    libx11-6 libxext6 libxrender1 libxtst6 libxi6 libgtk-3-0 mesa-utils wget unzip \
    && rm -rf /var/lib/apt/lists/*

# Descargar y preparar JavaFX SDK para Linux
RUN mkdir -p /javafx-sdk \
    && wget -O javafx.zip https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_linux-x64_bin-sdk.zip \
    && unzip javafx.zip -d /javafx-sdk \
    && mv /javafx-sdk/javafx-sdk-21.0.2/lib /javafx-sdk/lib \
    && rm -rf /javafx-sdk/javafx-sdk-21.0.2 javafx.zip

# Copiar tu JAR generado por Maven
COPY target/studyplanner.jar app.jar
COPY init.sql /docker-entrypoint-initdb.d/init.sql

# Configurar DISPLAY para mostrar GUI en Windows (usando Xming o VcXsrv)
ENV DISPLAY=host.docker.internal:0.0

# Ejecutar la aplicación JavaFX
CMD ["java", "--module-path", "/javafx-sdk/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "app.jar"]

FROM openjdk:17-jdk-slim



RUN apt-get update && apt-get install -y \
    libgtk-3-0 \
    libxtst6 \
    xvfb \
    x11vnc


WORKDIR /app


COPY target/studyplanner.jar .


COPY run.sh .


RUN chmod +x run.sh


EXPOSE 5900


CMD ["./run.sh"]

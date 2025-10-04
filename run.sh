#!/bin/bash
# Start Xvfb, a virtual screen that lets GUI apps run in a headless environment
Xvfb :99 -screen 0 1280x1024x24 &

# Set the DISPLAY environment variable to point to the virtual screen
export DISPLAY=:99

# Start the VNC server, allowing connections from anywhere without a password
x11vnc -display :99 -nopw -forever &

# Run the Java application
# The -jar argument points to the JAR file created by Maven
java -jar notebook-1.0-SNAPSHOT.jar

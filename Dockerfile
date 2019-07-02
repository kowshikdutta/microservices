FROM tomcat:8.5-jdk11-adoptopenjdk-openj9
RUN mkdir -p /var/turing/log
RUN touch /var/turing/log/Application.log
COPY turing.war /usr/local/tomcat/webapps/turing.war
EXPOSE 8080


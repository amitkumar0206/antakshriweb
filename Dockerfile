FROM tomcat:8.5
COPY target/*.war /usr/local/tomcat/webapps/app.war
RUN sh -c 'touch /usr/local/tomcat/webapps/app.war'
ENTRYPOINT [ "sh", "-c", "java -jar /usr/local/tomcat/webapps/app.war"]
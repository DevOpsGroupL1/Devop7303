FROM openjdk:17
EXPOSE 2025
# Set OS time zone
#ENV TZ Africa/Lagos
ENV VAR_SERVICE_NAME=devop7303
ENV VAR_DEPLOYMENT_ARTEFACT_PATH=Docker
# Setup
COPY ./$VAR_DEPLOYMENT_ARTEFACT_PATH/startup.sh /root/$VAR_SERVICE_NAME/startup.sh
COPY ./$VAR_DEPLOYMENT_ARTEFACT_PATH/env.sh /root/$VAR_SERVICE_NAME/env.sh
RUN chmod +x /root/$VAR_SERVICE_NAME/env.sh
RUN /root/$VAR_SERVICE_NAME/env.sh
ADD target/devop7303-*.jar /opt/$VAR_SERVICE_NAME/devop7303.jar
WORKDIR /opt/$VAR_SERVICE_NAME
#COPY ./$VAR_DEPLOYMENT_ARTEFACT_PATH/application.properties /opt/$VAR_SERVICE_NAME/config/
#COPY ./$VAR_DEPLOYMENT_ARTEFACT_PATH/application-prod.properties /opt/$VAR_SERVICE_NAME/config/
#COPY ./config/* /opt/$VAR_SERVICE_NAME/config/
RUN chmod +x /root/$VAR_SERVICE_NAME/startup.sh
CMD  /root/$VAR_SERVICE_NAME/startup.sh
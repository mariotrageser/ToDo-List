FROM maven as build
WORKDIR /usr/src/todo-ist-service
COPY . .
RUN mvn package

FROM jboss/wildfly
COPY --from=build /usr/src/todo-ist-service/target/todo-list-service.war /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
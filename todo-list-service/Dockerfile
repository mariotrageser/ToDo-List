FROM maven as build
WORKDIR /usr/src/todo-ist-service
COPY . .
RUN mvn package

FROM jboss/wildfly:23.0.2.Final
ARG MYSQL_VERSION
ARG TODO_LIST_DB_USER
ARG TODO_LIST_DB_PASSWORD
ARG TODO_LIST_DB_NAME
ARG KEYCLOAK_VERSION
ENV WILDFLY_HOME /opt/jboss/wildfly
ENV STANDALONE $WILDFLY_HOME/standalone
ENV BIN $WILDFLY_HOME/bin
ENV STANDALONE_SH $BIN/standalone.sh
ENV JBOSS_CLI $BIN/jboss-cli.sh
ENV MYSQL_CONNECTOR mysql-connector-java-$MYSQL_VERSION.jar
ENV MYSQL_CONNECTOR_PATH /tmp/$MYSQL_CONNECTOR
ENV KEYCLOAK_ADAPTER keycloak-oidc-wildfly-adapter-$KEYCLOAK_VERSION.zip
ENV KEYCLOAK_ADAPTER_PATH /tmp/$KEYCLOAK_ADAPTER

RUN curl --location --output $MYSQL_CONNECTOR_PATH --url \
    http://search.maven.org/remotecontent?filepath=mysql/mysql-connector-java/$MYSQL_VERSION/$MYSQL_CONNECTOR
RUN curl --location --output $KEYCLOAK_ADAPTER_PATH --url \
    https://github.com/keycloak/keycloak/releases/download/$KEYCLOAK_VERSION/$KEYCLOAK_ADAPTER
RUN $BIN/add-user.sh admin admin --silent
RUN unzip $KEYCLOAK_ADAPTER_PATH -d $WILDFLY_HOME
RUN $JBOSS_CLI --file=$WILDFLY_HOME/bin/adapter-elytron-install-offline.cli
RUN bash -c '$STANDALONE_SH &' && \
    bash -c \
        'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; \
            do echo `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; \
            sleep 1; \
        done' && \
    $JBOSS_CLI --connect --command="module add --name=com.mysql --resources=$MYSQL_CONNECTOR_PATH --dependencies=javax.api,javax.transaction.api" && \
    $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,\
        driver-xa-datasource-class-name=com.mysql.cj.jdbc.MysqlXADataSource)" && \
    $JBOSS_CLI --connect --command="data-source add --name=todo-list-ds --driver-name=mysql --connection-url=jdbc:mysql://todo-list-db:3306/$TODO_LIST_DB_NAME \
        --jndi-name=java:/jdbc/datasources/todo-list-ds --user-name=$TODO_LIST_DB_USER --password=$TODO_LIST_DB_PASSWORD --use-ccm=false --max-pool-size=25 \
        --blocking-timeout-wait-millis=5000 --enabled=true" && \
    $JBOSS_CLI --connect --command=":shutdown"
RUN rm -rf $STANDALONE/configuration/standalone_xml_history/ $STANDALONE/standalone/log/*
RUN rm -f /tmp/*.jar
COPY --from=build /usr/src/todo-ist-service/target/todo-list-service.war $STANDALONE/deployments

CMD $STANDALONE_SH -b 0.0.0.0 -bmanagement 0.0.0.0
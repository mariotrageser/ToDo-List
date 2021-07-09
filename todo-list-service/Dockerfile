FROM maven as build
WORKDIR /usr/src/todo-ist-service
COPY . .
RUN mvn package

FROM jboss/wildfly
ENV MYSQL_VERSION 8.0.25
ENV WILDFLY_HOME /opt/jboss/wildfly
ENV STANDALONE $WILDFLY_HOME/standalone
ENV BIN $WILDFLY_HOME/bin
ENV STANDALONE_SH $BIN/standalone.sh
ENV JBOSS_CLI $BIN/jboss-cli.sh
ENV MYSQL_CONNECTOR /tmp/mysql-connector-java-$MYSQL_VERSION.jar
ENV TODO_LIST_DB_USER todo-list-db-user
ENV TODO_LIST_DB_PASSWORD todo-list-db-password
ENV TODO_LIST_DB todo-list-db
RUN curl --location --output $MYSQL_CONNECTOR --url \
    http://search.maven.org/remotecontent?filepath=mysql/mysql-connector-java/$MYSQL_VERSION/mysql-connector-java-$MYSQL_VERSION.jar
RUN $BIN/add-user.sh admin admin --silent
RUN bash -c '$STANDALONE_SH &' && \
    bash -c \
        'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; \
            do echo `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; \
            sleep 1; \
        done' && \
    $JBOSS_CLI --connect --command="module add --name=com.mysql --resources=$MYSQL_CONNECTOR --dependencies=javax.api,javax.transaction.api" && \
    $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,\
        driver-xa-datasource-class-name=com.mysql.cj.jdbc.MysqlXADataSource)" && \
    $JBOSS_CLI --connect --command="data-source add --name=todo-list-ds --driver-name=mysql --connection-url=jdbc:mysql://todo-list-db:3306/$TODO_LIST_DB \
        --jndi-name=java:/jdbc/datasources/todo-list-ds --user-name=$TODO_LIST_DB_USER --password=$TODO_LIST_DB_PASSWORD --use-ccm=false --max-pool-size=25 \
        --blocking-timeout-wait-millis=5000 --enabled=true" && \
    $JBOSS_CLI --connect --command=":shutdown" && \
    rm -rf $STANDALONE/configuration/standalone_xml_history/ $STANDALONE/standalone/log/* && \
    rm -f /tmp/*.jar
COPY --from=build /usr/src/todo-ist-service/target/todo-list-service.war $STANDALONE/deployments

CMD $STANDALONE_SH -b 0.0.0.0 -bmanagement 0.0.0.0
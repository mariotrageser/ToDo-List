package de.mt.todolist.liquibase;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
import java.sql.SQLException;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class LiquibaseInitializationBean {

    private static final String CHANGE_LOG_FILE = "de/mt/todolist/liquibase/change-log.xml";

    @Resource(lookup = "java:/jdbc/datasources/todo-list-ds")
    private DataSource todoListDataSource;

    @PostConstruct
    protected void initializeLiquibase() {
        final var resourceAccessor = new ClassLoaderResourceAccessor((getClass().getClassLoader()));
        try(final var connection = todoListDataSource.getConnection()) {
            final var jdbcConnection = new JdbcConnection(connection);
            final var database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            try(final var liquibase = new Liquibase(CHANGE_LOG_FILE, resourceAccessor, database)) {
                liquibase.update(new Contexts());
            }
        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }
}

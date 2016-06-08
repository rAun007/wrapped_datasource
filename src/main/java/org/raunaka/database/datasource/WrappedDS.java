package org.raunaka.database.datasource;

import com.codahale.metrics.MetricRegistry;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author rAun007
 */

public class WrappedDS implements DataSource {

    private final DataSource dataSource;
    private final DBMetrics  dbMetrics;

    public WrappedDS(DataSource dataSource, MetricRegistry metricRegistry, String metricPrefix, String apiNameKey) {

        this.dataSource = dataSource;

        dbMetrics = new DBMetrics(metricRegistry, metricPrefix, apiNameKey);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new WrappedConnection(dataSource.getConnection(), dbMetrics);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return new WrappedConnection(dataSource.getConnection(username, password), dbMetrics);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return dataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        dataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        dataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return dataSource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return dataSource.getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return dataSource.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return dataSource.isWrapperFor(iface);
    }
}

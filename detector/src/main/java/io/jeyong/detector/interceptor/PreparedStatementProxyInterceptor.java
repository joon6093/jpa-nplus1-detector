package io.jeyong.detector.interceptor;

import io.jeyong.detector.dto.RequestLogDto;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public final class PreparedStatementProxyInterceptor implements MethodInterceptor {

    private static final String JDBC_QUERY_METHOD = "executeQuery";

    private final RequestLogDto requestLogDto;

    public PreparedStatementProxyInterceptor(final RequestLogDto requestLogDto) {
        this.requestLogDto = requestLogDto;
    }

    @Nullable
    @Override
    public Object invoke(@Nonnull final MethodInvocation invocation) throws Throwable {
        final Method method = invocation.getMethod();

        if (JDBC_QUERY_METHOD.equals(method.getName())) {
            final Object result = invocation.proceed();

            if (invocation.getThis() instanceof PreparedStatement preparedStatement) {
                final String sql = preparedStatement.toString().toUpperCase();

                if (sql.startsWith("SELECT")) {
                    requestLogDto.addQueryOccurrence(sql);
                }
            }

            return result;
        }

        return invocation.proceed();
    }
}

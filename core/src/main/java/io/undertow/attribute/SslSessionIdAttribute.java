package io.undertow.attribute;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.SSLSessionInfo;

/**
 * @author Stuart Douglas
 */
public class SslSessionIdAttribute implements ExchangeAttribute {

    public static final SslSessionIdAttribute INSTANCE = new SslSessionIdAttribute();

    @Override
    public String readAttribute(HttpServerExchange exchange) {
        SSLSessionInfo ssl = exchange.getConnection().getSslSessionInfo();
        if(ssl == null) {
            return null;
        }
        return new String(ssl.getId());
    }

    @Override
    public void writeAttribute(HttpServerExchange exchange, String newValue) throws ReadOnlyAttributeException {
        throw new ReadOnlyAttributeException("SSL Session ID", newValue);
    }

    public static final class Builder implements ExchangeAttributeBuilder {

        @Override
        public String name() {
            return "SSL Session ID";
        }

        @Override
        public ExchangeAttribute build(final String token) {
            if (token.equals("%{SSL_SESSION_ID}")) {
                return INSTANCE;
            }
            return null;
        }
    }
}

package com.ezhuspace.configuration;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

/**
 *         Date: 15-12-18
 */
public class EzhuEmbeddedServletContainerCustomizer implements EmbeddedServletContainerCustomizer {

    private static final Logger LOG = LoggerFactory.getLogger(EzhuEmbeddedServletContainerCustomizer.class);

    @Value("${servlet.container.maxThreads}")
    private int MAX_THREADS;

    @Override
    public void customize(ConfigurableEmbeddedServletContainer factory) {
        if(factory instanceof TomcatEmbeddedServletContainerFactory) {
            customizeTomcat((TomcatEmbeddedServletContainerFactory) factory);
        }
    }

    public void customizeTomcat(TomcatEmbeddedServletContainerFactory factory) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Object defaultMaxThreads = connector.getAttribute("maxThreads");
                connector.setAttribute("maxThreads", MAX_THREADS);
                LOG.info("Changed Tomcat connector maxThreads from " + defaultMaxThreads + " to " + MAX_THREADS);
            }
        });
    }
}

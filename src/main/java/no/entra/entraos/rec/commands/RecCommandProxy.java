package no.entra.entraos.rec.commands;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import no.cantara.base.command.CommandProxy;

public class RecCommandProxy extends CommandProxy {

    @Override
    protected CircuitBreakerConfig getConfig() {
        //See examples at https://resilience4j.readme.io/docs/examples#override-the-registrystore
        return super.getConfig();
    }
}

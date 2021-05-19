package no.entra.entraos.rec.commands;

import no.cantara.base.command.BaseResilience4jCommand;
import no.cantara.base.command.UnsuccesfulStatusCodeException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.model.Header;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.slf4j.LoggerFactory.getLogger;

@ExtendWith(MockServerExtension.class)
class RecCommandProxyTest {
    private static final Logger log = getLogger(RecCommandProxyTest.class);
    public static final int PORT = 1083;
    public static final String GROUP_KEY = "test";

    private RecCommandProxy commandProxy;
    private static ClientAndServer server;
    private String dynamicId;
    private String path;

   @BeforeAll
    public static void startServer() {
        server = startClientAndServer(PORT);
    }
    @BeforeEach
    public void setUp() {
        commandProxy = new RecCommandProxy();
        dynamicId = "12345";
    }

    @AfterAll
    public static void stopServer() {
        server.stop();
    }

    @Test
    public void queryRealEstateCore() {
        path = format("/demo/%s", dynamicId);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("floor", "u1");
        String realEstate = "testEstate";
        path = "/tfm2rec/realestate/" + realEstate; // + queryParamsString();

        //Expect
        createExpectationForGet(path, 200, "Any response");

        //Execute
        URI uri = URI.create(baseUrl());
        String bearerToken = "bearer not%20authenticated";



        BaseResilience4jCommand getCommand = new QueryRecCommand(uri, bearerToken, realEstate, queryParams);
        try {
            Object response = commandProxy.run(getCommand);
            log.info("Response: {}", response);
        } catch (UnsuccesfulStatusCodeException e) {
            e.printStackTrace();
            fail();
        } catch (IOException | InterruptedException e) {
            fail();
        }

    }

    private void createExpectationForGet(String path, int status, String returnBody) {
        new MockServerClient("127.0.0.1", PORT)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath(path),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(status)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(returnBody)
//                                .withDelay(TimeUnit.SECONDS,1)
                );
    }

    private String baseUrl() {
        return format("http://localhost:%d", server.getPort());
    }

}
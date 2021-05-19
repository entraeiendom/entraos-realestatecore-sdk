package no.entra.entraos.rec.commands;


import no.cantara.base.command.BaseHttpPostResilience4jCommand;

import java.net.URI;

public class UpdateRecCommand extends BaseHttpPostResilience4jCommand {

    private final static String GROUP_KEY = "AddTfm2Rec";
    private String realEstate;
    private String body;
    private String bearerToken;

    protected UpdateRecCommand(URI baseUri) {
        super(baseUri, GROUP_KEY);
    }

    /**
     * Add to Tfm2Rec
     * @param uri
     * @param bearerToken
     * @param realEstate
     * @param tfm2recJson
     */
    public UpdateRecCommand(URI uri, String bearerToken, String realEstate, String tfm2recJson) {
        this(uri);
        this.realEstate = realEstate;
        this.body = tfm2recJson;
        this.bearerToken = bearerToken;
    }

    @Override
    protected URI buildUri() {
        String baseUrl = getBaseUri().toString();
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        String fullUrl = baseUrl + "tfm2rec/tfm/" + realEstate;
        URI uri = URI.create(fullUrl);
        return uri;
    }

    @Override
    protected String buildAuthorization() {
        return "Bearer " + this.bearerToken;
    }

    @Override
    protected String getBody() {
        return this.body;
    }
}

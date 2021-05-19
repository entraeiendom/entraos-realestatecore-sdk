package no.entra.entraos.rec.commands;

import no.cantara.base.command.BaseHttpDeleteResilience4jCommand;

import java.net.URI;

public class DeleteRecCommand extends BaseHttpDeleteResilience4jCommand {

    private final static String GROUP_KEY = "DeleteTfm2Rec";
    private String tfm2recId = null;
    private String bearerToken;

    protected DeleteRecCommand(URI baseUri) {
        super(baseUri, GROUP_KEY);
    }

    /**
     * Add to Tfm2Rec
     * @param uri
     * @param bearerToken
     * @param tfm2recId
     */
    public DeleteRecCommand(URI uri, String bearerToken, String tfm2recId) {
        this(uri);
        this.tfm2recId = tfm2recId;
        this.bearerToken = bearerToken;
    }

    @Override
    protected URI buildUri() {
        String baseUrl = getBaseUri().toString();
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        String fullUrl = baseUrl;
        if (tfm2recId == null) {
            fullUrl = baseUrl;
        } else {
            fullUrl = baseUrl + "tfm2rec/" + tfm2recId;
        }
        URI uri = URI.create(fullUrl);
        return uri;
    }


    @Override
    protected String buildAuthorization() {
        return "Bearer " + this.bearerToken;
    }

}

package no.entra.entraos.rec.commands;

import no.cantara.base.command.BaseHttpGetResilience4jCommand;

import java.net.URI;
import java.util.Map;

public class QueryRecCommand extends BaseHttpGetResilience4jCommand {

    private final static String GROUP_KEY = "QueryTfm2Rec";
    private String realEstate;
    private Map<String, String> queryParams;
    private String bearerToken;

    protected QueryRecCommand(URI baseUri) {
        super(baseUri, GROUP_KEY);
    }

    /**
     * Add to Tfm2Rec
     * @param uri
     * @param bearerToken
     * @param realEstate
     * @param queryParams
     */
    public QueryRecCommand(URI uri, String bearerToken, String realEstate, Map<String,String> queryParams) {
        this(uri);
        this.realEstate = realEstate;
        this.queryParams = queryParams;
        this.bearerToken = bearerToken;
    }

    @Override
    protected URI buildUri() {
        String baseUrl = getBaseUri().toString();
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        String fullUrl = baseUrl + "tfm2rec/realestate/" + realEstate + queryParamsString();
        URI uri = URI.create(fullUrl);
        return uri;
    }

    String queryParamsString() {
        String queryParamString = null;
        if (queryParams != null && queryParams.size() > 0) {
            queryParamString = "?";
            for (String key : queryParams.keySet()) {
                queryParamString += key + "=" + queryParams.get(key) + "&";
            }
            if (queryParamString.endsWith("&")) {
                queryParamString = queryParamString.substring(0, queryParamString.length() -1);
            }
        }
        return queryParamString;
    }

    @Override
    protected String buildAuthorization() {
        return "Bearer " + this.bearerToken;
    }

}

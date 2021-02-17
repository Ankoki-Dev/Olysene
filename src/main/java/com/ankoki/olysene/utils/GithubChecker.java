package com.ankoki.olysene.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;

public class GithubChecker implements UpdateChecker {

    private final URL repoUrl;

    public GithubChecker(String user, String repo) throws MalformedURLException {
        repoUrl = new URL("https://www.github.com/" + user + "/" + repo + "/releases/latest");
    }

    @Override
    public CompletableFuture<String> getLatestTag() {
        return CompletableFuture.supplyAsync(() ->
        {
            String version = null;
            try {
                URLConnection connection = repoUrl.openConnection();
                connection.connect();
                connection.getInputStream().close();
                System.out.println(connection.getURL().toString());
                version = connection.getURL().toString().split("/releases/tag/")[1];
            } catch (IOException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            } return version;
        });
    }
}

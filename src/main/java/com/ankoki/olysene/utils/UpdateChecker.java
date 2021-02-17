package com.ankoki.olysene.utils;

import java.util.concurrent.CompletableFuture;

public interface UpdateChecker {
    CompletableFuture<String> getLatestTag();
}

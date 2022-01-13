package com.phil.queue;

public class ConnectionProperties {

    private final String url;

    private final String user;

    private final String pass;

    private final String queue;

    private final int readTimeout;

    private final int processingTimeout;

    private final int maxAttempts;

    public ConnectionProperties(String url, String user, String pass, String queue, int readTimeout, int processingTimeout, int maxAttempts) {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.queue = queue;
        this.readTimeout = readTimeout;
        this.processingTimeout = processingTimeout;
        this.maxAttempts = maxAttempts;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getQueue() {
        return queue;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getProcessingTimeout() {
        return processingTimeout;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}

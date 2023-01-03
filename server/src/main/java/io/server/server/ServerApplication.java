package io.server.server;

public class ServerApplication {

    public static void main(String[] args) throws InterruptedException {

        EchoServer echoServer = new EchoServer();
        echoServer.start(8080);
    }
}

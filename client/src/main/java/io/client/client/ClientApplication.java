package io.client.client;

public class ClientApplication {

    public static void main(String[] args) throws InterruptedException {
        EchoClient echoClient = new EchoClient();
        echoClient.connect("localhost",8080);
    }
}

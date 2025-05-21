package app;

import di.Injector;

public class Main {
    public static void main(String[] args) {
        Injector injector = new Injector("config.properties");
        Client client = injector.inject(new Client());
        client.doWork();
    }
}
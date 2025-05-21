package app;

import annotations.AutoInjectable;
import com.example.Service;

public class Client {
    @AutoInjectable
    private Service service;

    public void doWork() {
        service.serve();
    }
}
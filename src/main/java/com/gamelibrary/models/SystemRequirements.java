package com.gamelibrary.models;

public class SystemRequirements {
    private final String os;
    private final String processor;
    private final String ram;
    private final String gpu;
    private final String directX;
    private final String network;
    private final String storage;
    private final String soundCard;
    private final String vrSupport;

    public SystemRequirements(String os, String processor, String ram, String gpu, String directX, String network, String storage, String soundCard, String vrSupport) {
        this.os = os;
        this.processor = processor;
        this.ram = ram;
        this.gpu = gpu;
        this.directX = directX;
        this.network = network;
        this.storage = storage;
        this.soundCard = soundCard;
        this.vrSupport = vrSupport;
    }

    public String getFormatted() {
        return String.format("""
                ОС: %s
                Процессор: %s
                Оперативная память: %s
                Видеокарта: %s
                DirectX: %s
                Сеть: %s
                Место на диске: %s
                Звуковая карта: %s
                Поддержка VR: %s
                """, os, processor, ram, gpu, directX, network, storage, soundCard, vrSupport);
    }
}
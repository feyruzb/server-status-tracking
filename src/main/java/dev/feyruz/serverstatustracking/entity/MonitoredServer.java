package dev.feyruz.serverstatustracking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MonitoredServer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;
    private String name;
    private String description;

    protected MonitoredServer(){
    }
    public MonitoredServer(String ip, String name, String description) {
        this.ip = ip;
        this.name = name;
        this.description = description;
    }

    // ======  GETTERS ========
    public Long getId(){
        return id;
    }

    public String getIp(){
        return ip;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    // ======  SETTERS ========

    public void setIp(String ip){
        this.ip = ip;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return "MonitoredServer{id=" + id + ", ip='" + ip + "', name='" + name + "'}";
    }

}

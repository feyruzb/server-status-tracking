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
    private Integer port;
    private String name;
    private String description;
    private boolean enabled = true;

    protected MonitoredServer(){
    }
    public MonitoredServer(String ip, Integer port, String name, String description, boolean enabled) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        this.description = description;
        this.enabled = enabled;
    }

    // ======  GETTERS ========
    public Long getId(){
        return id;
    }

    public String getIp(){
        return ip;
    }

    public Integer getPort() { return port; }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public boolean isEnabled(){ return enabled; }

    // ======  SETTERS ========

    public void setIp(String ip){
        this.ip = ip;
    }

    public void setPort(Integer port) { this.port = port; }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setEnabled( boolean enabled) { this.enabled = enabled; }

    @Override
    public String toString() {
        return "MonitoredServer{id=" + id + ", ip='" + ip + "'"+ ", port= '" + port + "', name=<" + name + ">}";
    }

}

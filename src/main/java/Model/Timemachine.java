package Model;

public class Timemachine {
    private int id;
    private String name;
    private int capacity;
    private String status;

    public Timemachine(String name, int capacity, String status) {
        this.name = name;
        this.capacity = capacity;
        this.status = status;
    }
    public Timemachine(String name, int capacity, String status, int id) {
        this.name = name;
        this.capacity = capacity;
        this.status = status;
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getStatus(){
        return status;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return name + " - " + capacity + " - " + status;
    }
}

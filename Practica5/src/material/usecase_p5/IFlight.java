package material.usecase_p5;



public interface IFlight extends Comparable<Flight>{

    void setTime(int hours, int minutes);

    int getHours();
    int getMinutes();

    String getCompany();

    void setCompany(String company);

    int getFlightCode();

    void setFlightCode(int flightCode);

    void setDate(int year, int month, int day);

    int getYear();
    int getMonth();
    int getDay();


    int getCapacity();

    void setCapacity(int capacity);

    String getOrigin();

    void setOrigin(String origin);

    String getDestination();

    void setDestination(String destination);

    int getDelay();

    void setDelay(int delay);

    void setProperty(String attribute, String value);

    String getProperty(String attribute);

    Iterable<String> getAllAttributes();

}

package material.usecase_p5;

import material.ordereddictionary.AVLOrderedDict;

public class Flight implements IFlight, Comparable<Flight>{
	private String Company;
	private int FlightCode;
	private int Year, Month, Day;
	private int Hours, Minutes;
	private int Capacity;
	private String Origin;
	private String Destination;
	private int Delay;
	private AVLOrderedDict<String, String> Property = new AVLOrderedDict();
	
	public Flight() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Company == null) ? 0 : Company.hashCode());
		result = prime * result + Day;
		result = prime * result + FlightCode;
		result = prime * result + Month;
		result = prime * result + Year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		if (Company == null) {
			if (other.Company != null)
				return false;
		} else if (!Company.equals(other.Company))
			return false;
		if (Day != other.Day)
			return false;
		
		if (FlightCode != other.FlightCode)
			return false;
		
		if (Month != other.Month)
			return false;
		if (Year != other.Year)
			return false;
		return true;
	}


	@Override
	public String toString() {
		
		if(!(this.Origin == null) && !(this.Destination == null) && (this.Delay>0)) {
			return this.Day+"-"+this.Month+"-"+this.Year
				+"\t"+this.Company+this.FlightCode
				+"\t"+this.Hours+":"+this.Minutes
				+"\t"+this.Origin
				+"\t"+this.Destination
				+"\tDELAYED ("+this.Delay+"min)";
		}else if(!(this.Origin == null) && !(this.Destination == null) && (this.Delay<=0)&& (this.Hours != 0) && (this.Minutes != 0)){
			return this.Day+"-"+this.Month+"-"+this.Year
					+"\t"+this.Company+this.FlightCode
					+"\t"+this.Hours+":"+this.Minutes
					+"\t"+this.Origin
					+"\t"+this.Destination;
		}else if(!(this.Origin == null) && !(this.Destination == null) && (this.Hours == 0) && (this.Minutes == 0)){
			return this.Day+"-"+this.Month+"-"+this.Year
					+"\t"+this.Company+this.FlightCode
					+"\t"+this.Origin
					+"\t"+this.Destination;
		}else {
			return this.Day+"-"+this.Month+"-"+this.Year
					+"\t"+this.Company+this.FlightCode;
		}
	}

	@Override
	public void setTime(int hours, int minutes) {
		Hours = hours;
		Minutes = minutes;
	}
	@Override
	public int getHours() {
		return Hours;
	}
	@Override
	public int getMinutes() {
		return Minutes;
	}
	@Override
	public String getCompany() {
		return Company;
	}
	@Override
	public void setCompany(String company) {
		Company = company;
	}
	@Override
	public int getFlightCode() {
		return FlightCode;
	}
	@Override
	public void setFlightCode(int flightCode) {
		FlightCode = flightCode;
	}
	@Override
	public void setDate(int year, int month, int day) {
		Year = year;
		Month = month;
		Day = day;
	}
	@Override
	public int getYear() {
		return Year;
	}
	@Override
	public int getMonth() {
		return Month;
	}
	@Override
	public int getDay() {
		return Day;
	}
	@Override
	public int getCapacity() {
		return Capacity;
	}
	@Override
	public void setCapacity(int capacity) {
		Capacity = capacity;
	}
	@Override
	public String getOrigin() {
		return Origin;
	}
	@Override
	public void setOrigin(String origin) {
		Origin = origin;
	}
	@Override
	public String getDestination() {
		return Destination;
	}
	@Override
	public void setDestination(String destination) {
		Destination = destination;
	}
	@Override
	public int getDelay() {
		return Delay;
	}
	@Override
	public void setDelay(int delay) {
		Delay = delay;
	}
	@Override
	public void setProperty(String attribute, String value) {
		Property.insert(attribute, value);
	}
	@Override
	public String getProperty(String attribute) {
		return Property.find(attribute).getValue();
	}
	@Override
	public Iterable<String> getAllAttributes() {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public int compareTo(Flight arg0) {
		if(this.Year-arg0.Year!=0) {
			return this.Year-arg0.Year;
		}else if(this.Year-arg0.Year==0 && this.Month-arg0.Month!=0){
			return this.Month-arg0.Month;
		}else if(this.Year-arg0.Year==0 && this.Month-arg0.Month==0 && this.Day-arg0.Month!=0){
			return this.Day-arg0.Month;
		}else
			return 0;
	}
	
	
	
}

package material.usecase_p4;

import java.util.ArrayList;
import java.util.Iterator;

import material.maps.HashTableMapSC;

public class FlightManager {
    //Erratas corregidas:
    // - flightCode es un int (no un String)
    // - getFlightsByPassenger devuelve vuelos: Iterable<IFlight>
    // - nombre del parametro "fullname" -> surname

	
	private HashTableMapSC<IFlight, ArrayList<IPassenger>> system;
	private ArrayList<IFlight> fls;
	
	public FlightManager() {
		system = new HashTableMapSC<>();
		fls = new ArrayList<>();
	}
		
    public ArrayList<IFlight> getFls() {
		return fls;
	}

	public void setFls(ArrayList<IFlight> fls) {
		this.fls = fls;
	}

	public IFlight addFlight(String company, int flightCode, int year, int month, int day){
    	IFlight fl = new Flight();
    	fl.setCompany(company);
        fl.setFlightCode(flightCode);
        fl.setDate(year,month,day); 
        if(!fls.contains(fl) ) {
        	fls.add(fl);
        	ArrayList<IPassenger> pss = new ArrayList<>();
        	system.put(fl, pss);
        }
        else {
        	throw new RuntimeException("The flight already exists.");
        }
        return fl;
    }

    public IFlight getFlight(String company, int flightCode, int year, int month, int day){
    	IFlight fl = new Flight();
		fl.setCompany(company);
		fl.setFlightCode(flightCode);
		fl.setDate(year, month, day);
		IFlight aux = new Flight();
		Iterator<IFlight> it = system.keys().iterator();
		while(it.hasNext()) {
			aux = it.next();
			if(fl.equals(aux)) {
				break;
			}
			aux = null;
		}
		if(aux == null) {
			throw new RuntimeException("Flight not found.");
		}else {
	    	IFlight flaux = new Flight();
	    	flaux.setCapacity(aux.getCapacity());
	    	flaux.setCompany(aux.getCompany());
	    	flaux.setDate(aux.getYear(), aux.getMonth(), aux.getDay());
	    	flaux.setDelay(aux.getDelay());
	    	flaux.setDestination(aux.getDestination());
	    	flaux.setFlightCode(aux.getFlightCode());
	    	flaux.setOrigin(aux.getOrigin());
	    	flaux.setTime(aux.getHours(), aux.getMinutes());
	    	Iterable<String> flup = aux.getAllAttributes();
        	Iterator<String> flupit = flup.iterator();
        	String atr;
        	String value;
        	while(flupit.hasNext()) {
        		atr = (String) flupit.next();
        		value = aux.getProperty(atr);
        		flaux.setProperty(atr, value);
        	}
			return flaux;
		}
    }

    public void updateFlight(String company, int flightCode, int year, int month, int day, IFlight updatedFlightInfo){
    	IFlight f = new Flight();			
		try {
			f = this.getFlight(company, flightCode, year, month, day);
		} catch (RuntimeException e) {
			throw new RuntimeException("The flight doesn't exists and can't be updated.");
		}
    	fls.remove(f);
    	if(updatedFlightInfo != null) {
			Iterator<IFlight> it = fls.iterator(); 
			while(it.hasNext()) {
				if(it.next().equals(updatedFlightInfo))
					throw new RuntimeException("The new flight identifiers are already in use.");
			}
			IFlight f1 = new Flight();
        	ArrayList<IPassenger> ps = system.remove(f);
            f1.setFlightCode(updatedFlightInfo.getFlightCode());
            f1.setCompany(updatedFlightInfo.getCompany());
            f1.setDate(updatedFlightInfo.getYear(), updatedFlightInfo.getMonth(), updatedFlightInfo.getDay());
        	f1.setDelay(updatedFlightInfo.getDelay());
        	f1.setOrigin(updatedFlightInfo.getOrigin());
        	f1.setDestination(updatedFlightInfo.getDestination());
        	f1.setTime(updatedFlightInfo.getHours(), updatedFlightInfo.getMinutes());
        	f1.setCapacity(updatedFlightInfo.getCapacity());
        	Iterable<String> flup = updatedFlightInfo.getAllAttributes();
        	Iterator<String> flupit = flup.iterator();
        	String atr;
        	String value;
        	while(flupit.hasNext()) {
        		atr = (String) flupit.next();
        		value = updatedFlightInfo.getProperty(atr);
        		f1.setProperty(atr, value);
        	}
        	fls.add(f1);
        	system.put(f1, ps);
    	}
    }

    public void addPassenger(String dni, String name, String surname, IFlight vuelo){
    	IFlight fl = new Flight();
    	try {
			fl = this.getFlight(vuelo.getCompany(), vuelo.getFlightCode(), vuelo.getYear(), vuelo.getMonth(), vuelo.getDay());
		} catch (RuntimeException e) {
			throw new RuntimeException("The flight doesn't exits.");
		}
    	ArrayList<IPassenger> passList = system.get(fl);
    	int numPass = passList.size();
    	if(fl.getCapacity() > numPass) {
        	Passenger newPass = new Passenger();
        	newPass.setDNI(dni);
        	newPass.setName(name);
        	newPass.setSurname(surname);
        	if(!passList.contains(newPass)) {
        		passList.add(newPass);
        	}
    	}else {
    		throw new RuntimeException("This flight doesn't have capacity for more passengers.");
    	}
    }

    public Iterable<IPassenger> getPassengers(String company, int flightCode, int year, int month, int day){
    	IFlight fl = new Flight();
    	try {
			fl = this.getFlight(company, flightCode, year, month, day);
		} catch (RuntimeException e) {
			throw new RuntimeException("The flight doesn't exists.");
		}
    	return system.get(fl);
    }

    public Iterable<IFlight> flightsByDate(int year, int month, int day){
    	Iterator<IFlight> fit = system.keys().iterator();
    	ArrayList<IFlight> retList = new ArrayList<>();
    	IFlight fl = new Flight();
    	while(fit.hasNext()) {
    		fl = fit.next();
    		if(fl.getYear() == year && fl.getMonth() == month && fl.getDay() == day) {
    			retList.add(fl);
    		}
    	}
    	return retList;
    }

    public Iterable<IFlight> getFlightsByPassenger(IPassenger passenger){
    	Iterator<IFlight> fit = system.keys().iterator();
    	ArrayList<IFlight> retList = new ArrayList<>();
    	ArrayList<IPassenger> psList = new ArrayList<>();
    	IFlight fl = new Flight();
    	while(fit.hasNext()) {
    		fl = fit.next();
    		psList = system.get(fl);
    		if(psList.contains(passenger)){
    			retList.add(fl);
    		}
    	}
    	return retList;
    }

    public Iterable<IFlight> getFlightsByDestination(String destination, int year, int month, int day){
    	Iterator<IFlight> fit = system.keys().iterator();
    	boolean found = false;
    	for(IFlight f: system.keys()) {
    		found = f.getDestination() == destination;
    		if(found)
    			break;
    	}
    	//if(!found)
    		//throw new RuntimeException("The destination doesn't exists.");
    	
    	ArrayList<IFlight> retList = new ArrayList<>();
    	IFlight fl = new Flight();
    	while(fit.hasNext()) {
    		fl = fit.next();
    		if(fl.getDestination().equals(destination) && fl.getYear() == year && fl.getMonth() == month && fl.getDay() == day) {
    			retList.add(fl);
    		}
    	}
    	return retList;
    }
    
    
    
}
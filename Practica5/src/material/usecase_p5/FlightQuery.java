package material.usecase_p5;

import material.Position;
import material.tree.binarysearchtree.*;
import java.util.*;
import java.util.function.*;

public class FlightQuery {
    //Se utilizara la clase Flight que hayais desarrollado en la practica de mapas
    //se recomienda que se llame Flight y tenga al menos el constructor vacio.

    private BinarySearchTree<IFlight> system;
    private Comparator<String> comparator = new DefaultComparator<>();
    
    public FlightQuery() {
    	system = new LinkedBinarySearchTree<>();
    }
	
	public void addFlight(IFlight flight){
		if(flight != null) {
			system.insert(flight);
		}
	}


    public Iterable<IFlight> searchByDates(int start_year, int start_month, int start_day, int end_year, int end_month, int end_day) throws RuntimeException {
    	if(start_year>end_year) {
    		throw new RuntimeException("Invalid range. (min>max)");
    	}else if(start_month>end_month) {
    		throw new RuntimeException("Invalid range. (min>max)");
    	}else if(start_day>end_day) {
    		throw new RuntimeException("Invalid range. (min>max)");
    	}
    	ArrayList<IFlight> list = new ArrayList<>();
    	Iterator<Position<IFlight>> it = system.iterator();
    	Position<IFlight> aux;
    	IFlight startAux = new Flight();
    	startAux.setDate(start_year, start_month, start_day);
    	IFlight endAux = new Flight();
    	endAux.setDate(end_year, end_month, end_day);
    	int iS, iE;
    	while(it.hasNext()) {
    		aux = it.next();
    		iS = aux.getElement().compareTo((Flight) startAux);
    		iE = aux.getElement().compareTo((Flight) endAux);
    		if(iS >= 0 && iE <= 0) {
    			list.add(aux.getElement());
    		}
    	}
    	return list;
    }

    public Iterable<IFlight> searchByDestinations(String start_destination, String end_destination) throws RuntimeException {
    	if(comparator.compare(start_destination, end_destination)>0) {
    		throw new RuntimeException("Invalid range. (min>max)");
    	}
    	ArrayList<IFlight> list = new ArrayList<>();
    	Iterator<Position<IFlight>> it = system.iterator();
    	Position<IFlight> aux;
    	while(it.hasNext()) {
    		aux = it.next();
    		if(comparator.compare(aux.getElement().getDestination(), start_destination)>=0 && comparator.compare(aux.getElement().getDestination(), end_destination)<=0) {
    			list.add(aux.getElement());
    		}
    	}
    	return list;
    }


    public Iterable<IFlight> searchByCompanyAndFLightCode(String start_company, int start_flightCode, String end_company, int end_flightCode){
    	if(comparator.compare(start_company, end_company)>0) {
    		throw new RuntimeException("Invalid range. (min>max)");
    	}
    	
    	if(start_flightCode>end_flightCode) {
    		throw new RuntimeException("Invalid range. (min>max)");
    	}
    	
    	ArrayList<IFlight> list = new ArrayList<>();
    	Iterator<Position<IFlight>> it = system.iterator();
    	Position<IFlight> aux;
    	while(it.hasNext()) {
    		aux = it.next();
    		if(comparator.compare(aux.getElement().getCompany(), start_company)>=0 && comparator.compare(aux.getElement().getCompany(), end_company)<=0 && aux.getElement().getFlightCode()>=start_flightCode && aux.getElement().getFlightCode()<=end_flightCode) {
    			list.add(aux.getElement());
    		}
    	}
    	return list;
    }
}

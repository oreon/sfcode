package ${package}.biz;

import java.util.List;

import ${package}.domain.geog.Country;
import ${package}.domain.geog.State;

/**
 * @author Kamalpreet Singh
 *
 */
public interface IGeogManager {

	Country createCountry(Country country);
	
	Country updateCountry(Country country);
	
	void flagDeleteCountry(Country country);

	Country findCountryById(Long countryId);
			
	List<Country> findAllCountries();
	
	List<Country> findAllActiveCountries();
	
	State createState(State state);
	
	State updateState(State state);
	
	void flagDeleteState(State state);
	
	State findStateById(Long stateId);
	
	List<State> findAllStates();
	
	List<State> findAllActiveStates();
}
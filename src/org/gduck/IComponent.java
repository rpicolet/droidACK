package rpicolet.mvc;

public interface IComponent {
	/**
	 * Get the fully-qualified component Class name, 
	 * 
	 * @return - Component Class name
	 */
	public String getMvcClass();
	/**
	 * Get the unique (MVC runtime) instanceID, suitable for functional 
	 * mappings within a process; not useful _across_ processes/app versions!  
	 * 
	 * @return - Component ID
	 */
	public int getMvcId();
	/**
	 * Get the logging tag for the Component
	 * 
	 * @return - Component tag 
	 */
	public String getMvcTag();	
}

package simulation_objects;


public class Ant implements Cloneable {
	
	
	private Orientation currentOrientation;
	private boolean hasFood;
	

	public Ant() {
		currentOrientation = Orientation.randomOrientation();
	}
	
	public void setOrientation(Orientation newOrientation){
		currentOrientation = newOrientation;		
	}
	
	public Orientation getOrientation() {
		return currentOrientation;
	}
	
	public boolean hasFood() {
		return hasFood;
	}
	
	public void toggleHasFood() {
		hasFood = !hasFood;
	}
	
	public Ant clone() {
		try {
			return (Ant) super.clone();
		} 
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}

package application;

public class AirDistance {
	private City target;
	private double airDistance;
	public AirDistance( City target, double airDistance) {
	
		this.target = target;
		this.airDistance = airDistance;
	}



	public City gettarget() {
		return target;
	}

	public void settarget(City destination) {
		this.target = destination;
	}

	public double getAirDistance() {
		return airDistance;
	}

	public void setAirDistance(double airDistance) {
		this.airDistance = airDistance;
	}

}

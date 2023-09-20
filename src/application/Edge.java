package application;

public class Edge {

	private City target;
	private double distance;

	public Edge(City target, double distance) {
		this.target = target;
		this.distance = distance;
	}

	public City getTarget() {
		return target;
	}

	public void setTarget(City target) {
		this.target = target;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}

package application;

import java.util.LinkedList;

import javafx.scene.control.Button;

public class City implements Comparable{
	private String name;
	private double X;
	private double Y;
	private LinkedList<Edge> edges = new LinkedList<>();//target & distance
	private LinkedList<AirDistance> airDistancesList = new LinkedList<>();
	double cost;
	Button button = new Button();
	City prev;
	public City(String name,  double x, double y) {
		this.name = name;
		X = x;
		Y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public double getX() {
		return X;
	}

	public void setX(double X) {
		X = X;
	}

	public double getY() {
		return Y;
	}

	public void setY(double Y) {
		Y = Y;
	}

	public LinkedList<Edge> getEdgesList() {
		return edges;
	}

	public void setEdgesList(LinkedList<Edge> edges) {
		this.edges = edges;
	}

	public LinkedList<AirDistance> getAirDistancesList() {
		return airDistancesList;
	}

	public void setAirDistancesList(LinkedList<AirDistance> airDistancesList) {
		this.airDistancesList = airDistancesList;
	}
	public void addEdge (City city ,double distance) {
		edges.add(new Edge(city,distance));
	}
	public void addAirDistance(City city ,double distance) {
		airDistancesList.add(new AirDistance(city,distance));
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return (int)(this.cost-((City)o).cost);
	}

	@Override
	public String toString() {
		return   name +" -> ";
	}

	public City getPreviousCity() {
		// TODO Auto-generated method stub
		return prev;
	}

	public void setPreviousCity(City currentCity) {
		prev=currentCity;
		
	}
	

}

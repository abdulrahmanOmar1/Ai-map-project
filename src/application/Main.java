package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	static ArrayList<City> cities = new ArrayList<City>();
	static HashMap<String, City> citiesMap = new HashMap<>();
	Pane root = new Pane();
	City source;
	City target;
	ChoiceBox<String> sourceBox = new ChoiceBox<String>();
	ChoiceBox<String> targetBox = new ChoiceBox<String>();
	Group train = new Group();

	@Override
	public void start(Stage arg0) throws Exception {
		Stage primaryStage = new Stage();

		Scene scene = new Scene(root, 1080, 720);
		primaryStage.setTitle("MAP");
		root.setStyle("-fx-background-color:#1412A4	;\r\n");
		startMap();

		sourceBox.setTranslateX(400);
		sourceBox.setTranslateY(90);
		sourceBox.setPrefSize(150, 50);
		sourceBox.getItems().add("source");
		sourceBox.setValue("source");
		sourceBox.setStyle("-fx-background-color:#87CEFA;\r\n");

		for (int i = 0; i < cities.size(); i++) { // put city  sourceBox
			sourceBox.getItems().add(cities.get(i).getName());
		}
		sourceBox.setOnAction(e -> {
			if (sourceBox.getValue().compareTo("source") != 0) {
				source = citiesMap.get(sourceBox.getValue());
				source.getButton().setStyle("-fx-background-color: #0044FF;\r\n" + "-fx-background-radius:100;\r\n"); //color select city
			}
		});
		

		targetBox.setTranslateX(400);
		targetBox.setTranslateY(150);
		targetBox.setPrefSize(150, 50);
		targetBox.getItems().add("Target");
		targetBox.setStyle("-fx-background-color: #87CEFA;\r\n");

		targetBox.setValue("Target");
		for (int i = 0; i < cities.size(); i++) {
			targetBox.getItems().add(cities.get(i).getName());
		}
		targetBox.setOnAction(e -> {
			if (targetBox.getValue().compareTo("Target") != 0) {
				target = citiesMap.get(targetBox.getValue());
				target.getButton().setStyle("-fx-background-color: #0044FF;\r\n" + "-fx-background-radius:100;\r\n");
			}
		});
		
		
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioButton dfs = new RadioButton("DFS");
		dfs.setMinWidth(40);
		dfs.setMinHeight(40);
		dfs.setTextFill(Color.LIGHTSKYBLUE);
		dfs.setFont(new Font("Times New Roman", 18));
		dfs.setTranslateX(530);
		dfs.setTranslateY(30);
		dfs.setToggleGroup(toggleGroup);

		RadioButton As = new RadioButton("A-STAR");
		As.setMinWidth(40);
		As.setMinHeight(40);
		As.setTextFill(Color.LIGHTSKYBLUE);
		As.setFont(new Font("Times New Roman", 18));
		As.setTranslateX(405);
		As.setTranslateY(30);
		As.setToggleGroup(toggleGroup);

		Label Labelpath = new Label("Path in KM:");
		Labelpath.setFont(new Font("Times New Roman", 25));
		Labelpath.setTextFill(Color.WHITE);//Path in KM:
		Labelpath.setTranslateX(800);
		Labelpath.setTranslateY(60);
		root.getChildren().add(Labelpath);

		TextArea pathArea = new TextArea();
		pathArea.setTranslateX(700);
		pathArea.setTranslateY(100);
		pathArea.setMinSize(350, 270);
		pathArea.setMaxSize(350, 270);
		pathArea.setEditable(false);

		Label LabelCost = new Label("total cost:");
		LabelCost.setFont(new Font("Times New Roman", 25));
		LabelCost.setTextFill(Color.WHITE);
		LabelCost.setTranslateX(800);
		LabelCost.setTranslateY(400);
		root.getChildren().add(LabelCost);

		TextField TextField = new TextField();
		TextField.setTranslateX(690);
		TextField.setTranslateY(430);
		TextField.setPrefSize(360, 50);
		TextField.setFont(new Font("Arial", 25));
		TextField.setStyle("-fx-background-color: #eeffff;\r\n" + "-fx-background-radius:100;\r\n");
		root.getChildren().add(TextField);

		Button runButton = new Button("Run");
		runButton.setMinWidth(130);
		runButton.setMinHeight(60);
		runButton.setTextFill(Color.DARKBLUE);
		runButton.setFont(new Font("Arial", 30));
		runButton.setTranslateX(400);
		runButton.setTranslateY(290);
		runButton.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(25), Insets.EMPTY)));

		runButton.setOnAction(e -> {
			if (source != target && source != null && target != null) {
				if (dfs.isSelected() == true || As.isSelected() == true) {
					if (root.getChildren().contains(train) == false) {
						if (dfs.isSelected()) {
							LinkedList<City> path = new LinkedList<>();
							path = DFS(source, target);
							if (path != null) {
								double totalDis = 0;

								int ii = 0;
								while (ii < path.size() - 1) {
									Line line = new Line();
									if (ii != 0) {
										if (path.get(ii).getName().compareTo(source.getName()) != 0) {
											path.get(ii).getButton().setStyle("-fx-background-color: #0000FF;\r\n"
													+ "-fx-background-radius:100;\r\n");
										}
									}
									line.setStartX(path.get(ii).getX() + 55);
									line.setStartY(path.get(ii).getY() + 30);
									line.setEndX(path.get(ii + 1).getX() + 55);
									line.setEndY(path.get(ii + 1).getY() + 30);
									line.setFill(Color.BLACK);
									line.setStroke(Color.GREEN);
									line.setStrokeWidth(2);
									train.getChildren().add(line);

									ii++;
								}
								train.setVisible(true);
								root.getChildren().add(train);
								String x = "";
								x = path.get(0).getName();
								for (int i = 1; i < path.size(); i++) {
									double distance = findCost(path.get(i - 1), path.get(i));
									totalDis = totalDis + distance;
									if (i != path.size() - 1) {
										x = x + "-to-->" + path.get(i).getName() + " " + distance + "KM \n"
												+ path.get(i).getName();
									} else {
										x = x + "-to-->" + path.get(i).getName() + " " + distance + "KM ";
									}
								}
								pathArea.setText(x);
								TextField.setText(totalDis + "KM");

							} else {
								Alert error = new Alert(AlertType.INFORMATION);
								error.setHeaderText("No Path !!");
								error.setContentText("No path between " + source.getName() + " & " + target.getName());
								error.showAndWait();
							}
						} else if (As.isSelected()) {
							List<City> path = new LinkedList<>();
							path = A_Star(source, target);
							if (path != null) {
								double totalCost = 0;
								System.out.println(path.toString());
								for (int i = 0; i < path.size() - 1; i++) {

									Line line = new Line();
									if (i != 0)
										System.out.println("sadsa");
									if (path.get(i).getName().compareTo(source.getName()) != 0) {
										path.get(i).getButton().setStyle("-fx-background-color: #0000FF;\r\n"
												+ "        -fx-background-radius:100;\r\n");
									}
									line.setStartX(path.get(i).getX() + 55);
									line.setStartY(path.get(i).getY() + 30);
									line.setEndX(path.get(i + 1).getX() + 55);
									line.setEndY(path.get(i + 1).getY() + 30);
									line.setFill(Color.BLACK);
									line.setStroke(Color.GREEN);
									line.setStrokeWidth(2);
									train.getChildren().add(line);

								}
								train.setVisible(true);
								root.getChildren().add(train);
								String x = "";
								x = path.get(0).getName();
								for (int i = 1; i < path.size(); i++) {
									double distance = findCost(path.get(i - 1), path.get(i));
									totalCost = totalCost + distance;
									if (i != path.size() - 1) {
										x = x + "-to-->" + path.get(i).getName() + " " + distance + "KM \n"
												+ path.get(i).getName();
									} else {
										x = x + "-to-->" + path.get(i).getName() + " " + distance + "KM ";
									}
								}
								pathArea.setText(x);
								TextField.setText(totalCost + "KM");
							} else {
								Alert error = new Alert(AlertType.INFORMATION);
								error.setHeaderText("No Path !!");
								error.setContentText("No path between " + source.getName() + " & " + target.getName());
								error.showAndWait();
							}
						}
					} else {
						Alert error = new Alert(AlertType.ERROR);
						error.setHeaderText("Clear please");
						error.setContentText("please Clear before new run");
						error.showAndWait();
					}
				} else {
					Alert error = new Alert(AlertType.WARNING);
					error.setHeaderText("please choose DFS or A-STAR before run");
					error.setContentText("please choose DFS or A-STAR algorithm");
					error.showAndWait();
				}
			} else if (source == null || target == null) {
				Alert error = new Alert(AlertType.ERROR);
				error.setHeaderText("please choose source and destination!");
				error.setContentText("the source city or destination city or both is null");
				error.showAndWait();
				source = null;
				target = null;
				sourceBox.setValue("source");
				targetBox.setValue("target");
				for (int i = 0; i < cities.size(); i++) {
					cities.get(i).button
							.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
				}
			} else {
				Alert error = new Alert(AlertType.ERROR);
				error.setHeaderText("Same city !");
				error.setContentText("the source city and destination city are the same!");
				error.showAndWait();
				source = null;
				target = null;
				sourceBox.setValue("source");
				targetBox.setValue("taget");
				for (int i = 0; i < cities.size(); i++) {
					cities.get(i).button
							.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
				}
			}

		});
		Button clear = new Button("clear");
		clear.setTranslateX(550);
		clear.setTranslateY(290);
		clear.setMinWidth(130);
		clear.setMinHeight(60);
		clear.setMaxWidth(150);
		clear.setMaxHeight(60);
		clear.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(25), Insets.EMPTY)));
		clear.setTextFill(Color.DARKBLUE);
		clear.setFont(new Font("Arial", 20));
		clear.setOnAction(e -> {
			source = null;
			target = null;
			train.setVisible(false);
			train.getChildren().clear();
			root.getChildren().remove(train);
			sourceBox.setValue("source");
			dfs.setSelected(false);
			As.setSelected(false);
			targetBox.setValue("Target");
			pathArea.setText("");
			TextField.setText("");
			for (int i = 0; i < cities.size(); i++) {
				cities.get(i).button.setStyle("-fx-background-color: #FF0000;\r\n" + "-fx-background-radius:100;\r\n");
			}
		});

		
		root.getChildren().addAll(sourceBox, targetBox, runButton, clear, dfs, As, pathArea);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) throws IOException {

		readFileCity();
		readFileRoad();
		readFlyDisFile();
		launch(args);

	}

	public static void readFileCity() throws NumberFormatException, IOException {
		String line = null;

		BufferedReader bufferedReader = new BufferedReader(new FileReader("cities.csv"));
		while ((line = bufferedReader.readLine()) != null) {

			String[] array = line.split(",");

			City newCity = new City(array[0], Double.parseDouble(array[1]), Double.parseDouble(array[2]));
			cities.add(newCity);
			citiesMap.putIfAbsent(array[0], newCity);

		}

	}

	public static void readFileRoad() throws NumberFormatException, IOException {
		String string = null;
		BufferedReader bufferedReader = new BufferedReader(new FileReader("roads.csv"));

		while ((string = bufferedReader.readLine()) != null) {
			String[] array = string.split(",");
			City source = citiesMap.get(array[0]);
			City target = citiesMap.get(array[1]);
			source.addEdge(target, Double.parseDouble(array[2]));
			target.addEdge(source, Double.parseDouble(array[2]));
		}

	}

	public static void readFlyDisFile() throws NumberFormatException, IOException {
		String string = null;
		BufferedReader bufferedReader = new BufferedReader(new FileReader("AirDistance.csv"));

		while ((string = bufferedReader.readLine()) != null) {
			String[] array = string.split(",");
			City source = citiesMap.get(array[0]);
			City target = citiesMap.get(array[1]);
			source.addAirDistance(target, Double.parseDouble(array[2]));

		}

	}

	public double findCost(City source, City target) {
		for (int i = 0; i < source.getEdgesList().size(); i++) {
			if (target.getName().compareTo(source.getEdgesList().get(i).getTarget().getName()) == 0) {
				return source.getEdgesList().get(i).getDistance();
			}
		}
		return 0;
	}

	public static List<City> A_Star(City initialState, City goalState) {
		PriorityQueue<City> heap = new PriorityQueue<>();
		HashMap<City, Double> gMap = new HashMap<>();
		HashMap<City, Double> fMap = new HashMap<>();
		HashMap<City, City> ArriveFrom = new HashMap<>();  
		
		for (int i = 0; i < cities.size(); i++) {
			City city = cities.get(i);
			gMap.put(city, Double.POSITIVE_INFINITY);
			fMap.put(city, Double.POSITIVE_INFINITY);
		}
		
		gMap.put(initialState, 0.0);
		fMap.put(initialState, function(initialState, goalState)); 
		heap.add(initialState);
		while (!heap.isEmpty()) {
			City c = heap.poll();
			
			if (c.equals(goalState)) {
				return reBuildPath(ArriveFrom, c);
				}
			
			for (Edge e : c.getEdgesList()) { 
				City neighbor = e.getTarget();
				double tentativeGScore = gMap.get(c) + e.getDistance();
				if (tentativeGScore < gMap.get(neighbor)) {
					ArriveFrom.put(neighbor, c);
					gMap.put(neighbor, tentativeGScore);
					fMap.put(neighbor, gMap.get(neighbor) + function(neighbor, goalState));
					if (!heap.contains(neighbor)) {
						heap.add(neighbor);
					   }	
					}
			    }	
			}

		return null;
	}

	public void startMap() {
		Image palestineImage = new Image("palestine_map.png");
		ImageView palestineImageView = new ImageView(palestineImage);
		palestineImageView.setTranslateX(5);
		palestineImageView.setTranslateY(30);
		palestineImageView.setFitHeight(658);
		palestineImageView.setFitWidth(374);
		palestineImageView.setVisible(true);
		root.getChildren().add(palestineImageView);
		for (int i = 0; i < cities.size(); i++) {
			Button button = new Button();

			button.setTranslateX(cities.get(i).getX() + 50);
			button.setTranslateY(cities.get(i).getY() + 25);
			button.setMinWidth(10);
			button.setMinHeight(10);
			button.setMaxWidth(10);
			button.setMaxHeight(10);
			button.setStyle("-fx-background-color: #FF0000;\r\n" + "-fx-background-radius:100;\r\n");
			cities.get(i).setButton(button);
			button.setUserData(cities.get(i));
			button.setOnAction(event -> {

				if (source == null) {
					button.setStyle("-fx-background-color: #000000;\r\n" + "-fx-background-radius:100;\r\n");
					source = (City) button.getUserData();
					sourceBox.setValue(source.getName());
				} else if (source != null && target == null) {
					button.setStyle("-fx-background-color: #000000;\r\n" + "-fx-background-radius:100;\r\n");
					target = (City) button.getUserData();
					targetBox.setValue(target.getName());
				}
			});

			Label Label = new Label(cities.get(i).getName());
			Label.setFont(new Font("Times New Roman", 10));
			Label.setTextFill(Color.BLACK);// color name city in map
			Label.setTranslateX(cities.get(i).getX() + 50);
			Label.setTranslateY(cities.get(i).getY() + 15);
			root.getChildren().add(button);
			root.getChildren().add(Label);
		}

	}

	public static LinkedList<City> DFS(City start, City goalCheck) {
		LinkedList<City> pathOfState = new LinkedList<>();
		pathOfState.add(start);
		Stack<City> frontier = new Stack<City>();
		frontier.push(start);
		LinkedList<City> exploredState = new LinkedList<>();

		int ic = 0;
		while (ic < cities.size()) {
			cities.get(ic).setPreviousCity(null);
			ic++;
		}

		while (!frontier.isEmpty()) {
			City state = frontier.pop();
			exploredState.add(state);

			if (goalCheck.getName().compareTo(state.getName()) == 0) {
				LinkedList<City> finalPath = new LinkedList<>();
				City currentCity = state;
				while (currentCity != null) {
					finalPath.addFirst(currentCity);
					currentCity = currentCity.getPreviousCity();
				}

				return finalPath;
			}

			for (int i = 0; i < state.getEdgesList().size(); i++) {
				City neighbor = state.getEdgesList().get(i).getTarget();
				if (!frontier.contains(neighbor) && !exploredState.contains(neighbor)) {
					frontier.push(neighbor);
					neighbor.setPreviousCity(state);
				}
			}
		}
		return null;
	}

	public static double function(City source, City target) {
		for (int i = 0; i < source.getAirDistancesList().size(); i++) {
			if (target.getName().compareTo(source.getAirDistancesList().get(i).gettarget().getName()) == 0) {
				return source.getAirDistancesList().get(i).getAirDistance();
			}
		}
		return 0;
	}

	public static List<City> reBuildPath(HashMap<City, City> ArriveFrom, City c) {
		LinkedList<City> path = new LinkedList<>();
		path.addFirst(c);
		while (ArriveFrom.containsKey(c)) {
			c = ArriveFrom.get(c);
			path.addFirst(c);
		}
		return path;
	}

}
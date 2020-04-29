package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 Assignment by Max T. Nielsen
 **/

public class View {

    private GridPane startView;
    private final AStarGraph model;
    Button exitBtn = new Button("Exit");
    Label Vstart = new Label("select start vertex");
    ComboBox<Vertex> startVertexComb = new ComboBox<>();
    Button AStarBtn = new Button("Run AStar from start vertex");
    Label Vend = new Label("select end vertex");
    ComboBox<Vertex> endVertexComb = new ComboBox<>();
    Button printBtn = new Button("AStar solution");
    TextArea solution = new TextArea();


    public View(AStarGraph model) {
        this.model = model;
        startView = new GridPane();
        startView.setMinSize(300, 200);
        startView.setPadding(new Insets(10, 10, 10, 10));
        startView.setVgap(5);
        startView.setHgap(1);

        ObservableList<Vertex> VertexList = FXCollections.observableArrayList(model.getVertices());

        Callback<ListView<Vertex>, ListCell<Vertex>> VertexcellFactory = new Callback<ListView<Vertex>, ListCell<Vertex>>() {
            @Override
            public ListCell<Vertex> call(ListView<Vertex> vertexListView) {
                return new ListCell<Vertex>() {
                    @Override
                    protected void updateItem(Vertex vertex, boolean empty) {
                        super.updateItem(vertex, empty);
                        if (vertex == null || empty) {
                            setText(null);
                        } else {
                            setText(vertex.getid());
                        }
                    }
                };
            }
        };

        startVertexComb.setItems(VertexList);
        startVertexComb.setButtonCell(VertexcellFactory.call(null));
        startVertexComb.setCellFactory(VertexcellFactory);
        startVertexComb.setValue(model.getVertices().get(0));
        endVertexComb.setItems(VertexList);
        endVertexComb.setButtonCell(VertexcellFactory.call(null));
        endVertexComb.setCellFactory(VertexcellFactory);
        endVertexComb.setValue(model.getVertices().get(model.getVertices().size()-1));

        solution.setPrefColumnCount(1);
        //Add content to pane
        startView.add(Vstart, 1, 1);
        startView.add(startVertexComb, 15,1);
        startView.add(Vend, 1, 3);
        startView.add(endVertexComb, 15, 3);
        startView.add(AStarBtn, 15, 2);
        startView.add(printBtn,15,4);
        startView.add(solution, 15,5);
        startView.add(exitBtn, 20, 6);
    }

    public Parent asParent() {
        return startView;
    }
}
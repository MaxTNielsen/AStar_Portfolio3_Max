package sample;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import java.util.Stack;

/**
 Assignment by Max T. Nielsen
 **/

public class Controller {

    private final AStarGraph model;
    private final View view;

    public Controller(AStarGraph graph, View view) {
        this.model = graph;
        this.view = view;
        view.exitBtn.setOnAction(e -> Platform.exit());
        view.AStarBtn.setOnAction(e -> model.A_Star(view.startVertexComb.getValue(), view.endVertexComb.getValue()));
        view.printBtn.setOnAction(e ->
                printSolution(view.endVertexComb.getValue(), view.solution));
    }

    public void printSolution(Vertex goal, TextArea textArea) {
            Vertex pvertex = goal;
            Stack<Vertex> Path = new Stack<>();
            int limit = 0;
            while (pvertex != null) { //Check if its the origin
                Path.push(pvertex);
                pvertex = pvertex.getPrev();
            }
            if (!Path.isEmpty()) limit = Path.size();
            for (int i = 0; i < limit - 1; i++)
                textArea.appendText(Path.pop().getid() + " - > ");
            if (limit > 0) textArea.appendText(Path.pop().getid() + "\n");
    }
}
package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Batch;
import service.BatchService;
import service.RackService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private ScrollPane dashboardPane;
    @FXML private AnchorPane contentPane;
    @FXML private FlowPane batchCardPane;

    @FXML private Label totalRackLabel, totalBatchLabel;
    @FXML private Label temperatureLabel, phLabel;

    @FXML private LineChart<String, Number> temperatureChart, phChart;

    private XYChart.Series<String, Number> tempSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> phSeries = new XYChart.Series<>();

    private final RackService rackService = new RackService();
    private final BatchService batchService = new BatchService();

    private final Random random = new Random();
    private final DateTimeFormatter timeFormat =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDashboard();
        loadSummary();
        initCharts();
        startRealtime();
    }

    private void showOnly(Parent view) {
        contentPane.getChildren().setAll(view);
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
    }

    @FXML private void showDashboard() { showOnly(dashboardPane); }
    @FXML private void showTanaman() { loadExternal("/views/tanaman.fxml"); }
    @FXML private void showRack() { loadExternal("/views/rack.fxml"); }
    @FXML private void showBatch() { loadExternal("/views/batch.fxml"); }
    @FXML private void showSimulation() { loadExternal("/views/simulation.fxml"); }

    private void loadExternal(String fxml) {
        try {
            showOnly(FXMLLoader.load(getClass().getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSummary() {
        totalRackLabel.setText(String.valueOf(rackService.getAllRacks().size()));
        totalBatchLabel.setText(String.valueOf(batchService.getAllBatch().size()));
    }

    private void initCharts() {
        temperatureChart.getData().add(tempSeries);
        phChart.getData().add(phSeries);
        tempSeries.setName("Suhu Umum");
        phSeries.setName("pH Umum");
    }

    private void startRealtime() {

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(2), e -> {

                String time = LocalTime.now().format(timeFormat);
                double temp = 23 + random.nextDouble() * 3;
                double ph = 5.8 + random.nextDouble() * 0.5;

                tempSeries.getData().add(new XYChart.Data<>(time, temp));
                phSeries.getData().add(new XYChart.Data<>(time, ph));

                if (tempSeries.getData().size() > 12) tempSeries.getData().remove(0);
                if (phSeries.getData().size() > 12) phSeries.getData().remove(0);

                temperatureLabel.setText(String.format("%.1f °C", temp));
                phLabel.setText(String.format("%.2f", ph));

                loadBatchCards();
            })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void loadBatchCards() {

        batchCardPane.getChildren().clear();
        for (Batch batch : batchService.getAllBatch()) {

            double temp = 23 + random.nextDouble() * 3;
            double ph = 5.8 + random.nextDouble() * 0.5;

            String status;
            String style;

            if (temp > 26 || ph < 5.5 || ph > 6.5) {
                status = "KRITIS";
                style = "batch-danger";
            } else if (temp > 25) {
                status = "WARNING";
                style = "batch-warn";
            } else {
                status = "AMAN";
                style = "batch-ok";
            }

            VBox card = new VBox(6);
            card.getStyleClass().add("batch-card");

            Label title = new Label("Batch #" + batch.getBatchId());
            title.getStyleClass().add("batch-title");

            Label t = new Label(String.format("Suhu: %.1f °C", temp));
            Label p = new Label(String.format("pH: %.2f", ph));

            Label s = new Label("Status: " + status);
            s.getStyleClass().add(style);

            card.getChildren().addAll(title, t, p, s);
            batchCardPane.getChildren().add(card);
        }
    }
}

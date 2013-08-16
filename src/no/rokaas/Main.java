package no.rokaas;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import no.rokaas.streamredirect.LoggingOutputStream;
import no.rokaas.streamredirect.StdOutErrLevel;

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.*;

public class Main extends Application {
    private Scene scene;
    private final static String homePage = "http://www.google.com";

    @Override
    public void start(Stage stage) {
        GridPane grid = getGridPane();
        stage.setTitle("JfxBrowser");
        scene = new Scene(grid, Color.GHOSTWHITE);
        stage.setScene(scene);
        scene.getStylesheets().add("/no/rokaas/Browser.css");
        stage.show();
    }

    private GridPane getGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label addressLabel = new Label("Address:");
        grid.add(addressLabel, 0, 0, 1, 1);

        final TextField address = new TextField();
        address.setMinHeight(22);
        grid.add(address, 1, 0, 1, 1);

        final Browser browser = new Browser(homePage);
        address.setText(browser.getLocation());
        address.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fixAddress(address);
                browser.load(address.getText());
                address.setText(browser.getLocation());
            }
        });
        grid.add(browser, 0, 1, 2, 1);
        grid.getColumnConstraints().addAll(
                ColumnConstraintsBuilder.create().percentWidth(5).build(),
                ColumnConstraintsBuilder.create().percentWidth(95).build()
        );
        return grid;
    }

    private void fixAddress(TextField address) {
        String content = address.getText();
        int numberOfDots = content.length() - content.replace(".", "").length();
        if (numberOfDots == 0) {
            // do a google search
            address.setText("http://www.google.com/search?q=" + content.replace(' ', '+'));
            return;
        } else if (numberOfDots == 1) {
            // only one . found - prepend www.
            content = "www." + content;
        }
        if (!content.startsWith("http://")) {
            address.setText("http://" + content);
        }
    }

    public static void main(String[] args) throws IOException {
        initLogging();
        redirectStreams();
        launch(args);
    }

    private static void initLogging() throws IOException {
        // initialize logging to go to rolling log file
        LogManager logManager = LogManager.getLogManager();
        logManager.reset();

        // log file max size 10K, 3 rolling files, append-on-open
        Handler fileHandler = new FileHandler("log", 10000, 3, true);
        fileHandler.setFormatter(new SimpleFormatter());
        Logger.getLogger("").addHandler(fileHandler);
    }

    private static void redirectStreams() {
        // rebind stdout/stderr to logger
        Logger logger;
        LoggingOutputStream los;

        logger = Logger.getLogger("stdout");
        los = new LoggingOutputStream(logger, StdOutErrLevel.STDOUT);
        System.setOut(new PrintStream(los, true));

        logger = Logger.getLogger("stderr");
        los = new LoggingOutputStream(logger, StdOutErrLevel.STDERR);
        System.setErr(new PrintStream(los, true));
    }

}

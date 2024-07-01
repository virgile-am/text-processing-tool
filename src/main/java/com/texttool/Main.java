package src.main.java.com.texttool;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    private TextField regexPatternField;
    private TextField replaceWithField;
    private TextArea textArea;
    private TextArea resultArea;
    private ComboBox<String> collectionTypeComboBox;
    private ComboBox<String> currentCollectionComboBox;
    private ListView<String> collectionListView;
    private ObservableList<String> listData;
    private Set<String> setData;
    private Map<String, String> mapData;
    private TextField itemInputField;
    private TextField mapKeyInputField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text Processing and Data Management Tool");

        // Initialize Data Collections
        listData = FXCollections.observableArrayList();
        setData = new HashSet<>();
        mapData = new HashMap<>();

        // Create UI components
        regexPatternField = new TextField();
        regexPatternField.setPromptText("Enter Regex Pattern");

        replaceWithField = new TextField();
        replaceWithField.setPromptText("Replace With");

        textArea = new TextArea();
        textArea.setPromptText("Enter text to process");

        Button findMatchesButton = new Button("Find Matches");
        findMatchesButton.setOnAction(e -> findMatches());

        Button replaceButton = new Button("Replace");
        replaceButton.setOnAction(e -> replaceText());

        Button highlightMatchesButton = new Button("Highlight Matches");
        highlightMatchesButton.setOnAction(e -> highlightMatches());

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPromptText("Results");

        collectionTypeComboBox = new ComboBox<>(FXCollections.observableArrayList("List", "Set", "Map"));
        collectionTypeComboBox.setValue("List");

        currentCollectionComboBox = new ComboBox<>(FXCollections.observableArrayList("Main Collection"));
        currentCollectionComboBox.setValue("Main Collection");

        collectionListView = new ListView<>();
        updateCollectionDisplay();

        itemInputField = new TextField();
        itemInputField.setPromptText("Item");

        mapKeyInputField = new TextField();
        mapKeyInputField.setPromptText("Map Key (for Map only)");

        Button addItemButton = new Button("Add Item");
        addItemButton.setOnAction(e -> addItemToCollection());

        Button editItemButton = new Button("Edit Item");
        editItemButton.setOnAction(e -> editSelectedItem());

        Button deleteItemButton = new Button("Delete Item");
        deleteItemButton.setOnAction(e -> deleteSelectedItem());

        Button clearCollectionButton = new Button("Clear Collection");
        clearCollectionButton.setOnAction(e -> clearCollection());

        Button updateSelectedButton = new Button("Update Selected");
        updateSelectedButton.setOnAction(e -> updateSelectedItem());

        Button findInCollectionButton = new Button("Find in Collection");
        findInCollectionButton.setOnAction(e -> findInCollection());

        VBox root = new VBox(10);
        root.getChildren().addAll(
                new Label("Text Input"),
                textArea,
                new Label("Regex Operations"),
                new HBox(10, regexPatternField, replaceWithField, findMatchesButton, replaceButton, highlightMatchesButton),
                new Label("Results"),
                resultArea,
                new Label("Data Collections"),
                new HBox(10, new Label("Collection Type:"), collectionTypeComboBox, new Label("Current Collection:"), currentCollectionComboBox),
                collectionListView,
                new HBox(10, addItemButton, editItemButton, deleteItemButton, clearCollectionButton),
                new Label("Item Operations"),
                new HBox(10, itemInputField, mapKeyInputField),
                new HBox(10, updateSelectedButton, findInCollectionButton)
        );

        Scene scene = new Scene(root, 900, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void findMatches() {
        try {
            String pattern = regexPatternField.getText();
            String text = textArea.getText();
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(text);
            StringBuilder results = new StringBuilder();
            while (matcher.find()) {
                results.append("Match found: ").append(matcher.group()).append("\n");
            }
            resultArea.setText(results.toString());
        } catch (Exception e) {
            showError("Error finding matches: " + e.getMessage());
        }
    }

    private void replaceText() {
        try {
            String pattern = regexPatternField.getText();
            String replaceWith = replaceWithField.getText();
            String text = textArea.getText();
            Pattern compiledPattern = Pattern.compile("\\b" + pattern + "\\b");
            Matcher matcher = compiledPattern.matcher(text);
            String result = matcher.replaceAll(replaceWith);
            resultArea.setText(result);
        } catch (Exception e) {
            showError("Error replacing text: " + e.getMessage());
        }
    }

    private void highlightMatches() {
        try {
            String pattern = regexPatternField.getText();
            String text = textArea.getText();
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(text);
            String highlightedText = matcher.replaceAll(match -> "[" + match.group() + "]");
            resultArea.setText(highlightedText);
        } catch (Exception e) {
            showError("Error highlighting matches: " + e.getMessage());
        }
    }

    private void addItemToCollection() {
        try {
            String item = itemInputField.getText();
            String mapKey = mapKeyInputField.getText();
            switch (collectionTypeComboBox.getValue()) {
                case "List":
                    if (!listData.contains(item)) {
                        listData.add(item);
                    }
                    break;
                case "Set":
                    setData.add(item);
                    break;
                case "Map":
                    if (!mapKey.isEmpty()) {
                        mapData.put(mapKey, item);
                    } else {
                        showError("Map Key cannot be empty for Map collection.");
                    }
                    break;
            }
            updateCollectionDisplay();
            itemInputField.clear();
            mapKeyInputField.clear();
        } catch (Exception e) {
            showError("Error adding item to collection: " + e.getMessage());
        }
    }

    private void editSelectedItem() {
        String selectedItem = collectionListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemInputField.setText(selectedItem);
        } else {
            showError("No item selected to edit.");
        }
    }

    private void deleteSelectedItem() {
        try {
            String selectedItem = collectionListView.getSelectionModel().getSelectedItem();
            switch (collectionTypeComboBox.getValue()) {
                case "List":
                    listData.remove(selectedItem);
                    break;
                case "Set":
                    setData.remove(selectedItem);
                    break;
                case "Map":
                    mapData.values().remove(selectedItem);
                    break;
            }
            updateCollectionDisplay();
        } catch (Exception e) {
            showError("Error deleting item: " + e.getMessage());
        }
    }

    private void clearCollection() {
        try {
            switch (collectionTypeComboBox.getValue()) {
                case "List":
                    listData.clear();
                    break;
                case "Set":
                    setData.clear();
                    break;
                case "Map":
                    mapData.clear();
                    break;
            }
            updateCollectionDisplay();
        } catch (Exception e) {
            showError("Error clearing collection: " + e.getMessage());
        }
    }

    private void updateSelectedItem() {
        try {
            String selectedItem = collectionListView.getSelectionModel().getSelectedItem();
            String newItem = itemInputField.getText();
            if (selectedItem != null && !newItem.isEmpty()) {
                switch (collectionTypeComboBox.getValue()) {
                    case "List":
                        listData.set(listData.indexOf(selectedItem), newItem);
                        break;
                    case "Set":
                        setData.remove(selectedItem);
                        setData.add(newItem);
                        break;
                    case "Map":
                        mapData.values().remove(selectedItem);
                        mapData.put(mapKeyInputField.getText(), newItem);
                        break;
                }
                updateCollectionDisplay();
            } else {
                showError("Select an item and enter a new value.");
            }
        } catch (Exception e) {
            showError("Error updating selected item: " + e.getMessage());
        }
    }

    private void findInCollection() {
        try {
            String item = itemInputField.getText();
            boolean found = false;
            switch (collectionTypeComboBox.getValue()) {
                case "List":
                    found = listData.contains(item);
                    break;
                case "Set":
                    found = setData.contains(item);
                    break;
                case "Map":
                    found = mapData.containsValue(item);
                    break;
            }
            resultArea.setText(found ? "Item found in collection." : "Item not found in collection.");
        } catch (Exception e) {
            showError("Error finding item in collection: " + e.getMessage());
        }
    }

    private void updateCollectionDisplay() {
        try {
            switch (collectionTypeComboBox.getValue()) {
                case "List":
                    collectionListView.setItems(listData);
                    break;
                case "Set":
                    collectionListView.setItems(FXCollections.observableArrayList(setData));
                    break;
                case "Map":
                    ObservableList<String> mapList = FXCollections.observableArrayList();
                    mapData.forEach((key, value) -> mapList.add(key + ": " + value));
                    collectionListView.setItems(mapList);
                    break;
            }
        } catch (Exception e) {
            showError("Error updating collection display: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

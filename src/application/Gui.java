package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jdom2.input.SAXBuilder;

import com.classdiagram.model.Attribute;
import com.classdiagram.model.Class;
import com.classdiagram.model.ClassDiagram;
import com.classdiagram.model.Interface;
import com.classdiagram.model.Methode;
import com.classdiagram.model.Relation;
import com.classdiagram.model.Relation.FirstToSecond;
import com.classdiagram.model.ClassDiagram.Type;
import com.classdiagram.model.ClassDiagram.TypeRelation;
import com.classdiagram.model.Structure.Visibility;
import com.classdiagram.model.Argument;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui extends Application {
	
	private volatile boolean isGenerating = false;

    private Stage primaryStage;
    private Scene scene1;
    private Scene scene2;
    //private String projectName;
    
    private List<String>  interfaces;
    private String classP;
    private String classQ;
    private String classR;
    private String classX;
    
    private volatile ClassDiagram diagram = new ClassDiagram();
    
    @Override
    public void start(Stage primaryStage) {
    	
    	
    	
    	
    	
        TreeItem<String> rootItem = new TreeItem<>("Class Diagram");
        
        TreeItem<String> classesItem = new TreeItem<>("Classes");

        TreeItem<String> interfacesItem = new TreeItem<>("Interfaces");

        TreeItem<String> relationsItem = new TreeItem<>("Relations");

        
        rootItem.getChildren().addAll(classesItem, interfacesItem, relationsItem);
        
        
        
        TreeView<String> classDiagramTree = new TreeView<>(rootItem);
        classDiagramTree.setShowRoot(false);
        
        HBox treeContainer = new HBox(classDiagramTree);
        treeContainer.setPadding(new Insets(10));
        treeContainer.setAlignment(Pos.CENTER);
    	
    	
    	
    	
    	
    	
    	
    	
    	

    	
        this.primaryStage = primaryStage;

        // Scene 1: project name input and next button
        VBox vbox1 = new VBox();
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setSpacing(20);

        Label label1 = new Label("Enter project name:");
        TextField textField1 = new TextField();
        Button nextButton = new Button("Next");

        vbox1.getChildren().addAll(label1, textField1, nextButton);

        scene1 = new Scene(vbox1, 400, 300);

        // Scene 2: buttons to add class/interface/relation/attribute/method
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(800, 600);

        VBox vbox2 = new VBox();
        //vbox2.setAlignment(Pos.CENTER);
        vbox2.setSpacing(20);

        Label label2 = new Label("Project: " + diagram.getName());
        
        
        
        
        
        Button addClassBtn = new Button("Add Class");
        addClassBtn.setOnAction(e -> {
        	
        	// Attributes list
        	List<Attribute> attributesList = new ArrayList<Attribute>();
        	List<Methode> methodesList = new ArrayList<Methode>();
        	
            
        	// Create a new dialog
        	Dialog<Class> dialog = new Dialog<>();
            dialog.setTitle("Add Class");
            dialog.setHeaderText("Enter the name and details of the class");
            dialog.setResizable(false);

            // Set the button types
            ButtonType addBtn = new ButtonType("Add", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);

            //Create the add Attribute button and action
            Button addAttrBtn = new Button("Add Attribute");
            addAttrBtn.setOnAction(ev -> {
            	
            	Dialog<Attribute> dialog1 = new Dialog<>();
                dialog1.setTitle("Add Attribute");
                dialog1.setHeaderText("Enter the name and details of the attribute");
                dialog1.setResizable(false);
                
                ButtonType addAttBtn = new ButtonType("Add", ButtonData.OK_DONE);
                dialog1.getDialogPane().getButtonTypes().addAll(addAttBtn, ButtonType.CANCEL);
                
                Label nameLabel = new Label("Attribute Name:");
                TextField nameField = new TextField();
                
                Label accessLabel = new Label("Access Modifier:");
                RadioButton privateRB = new RadioButton("Private");
                RadioButton publicRB = new RadioButton("Public");
                RadioButton protectedRB = new RadioButton("Protected");
                ToggleGroup accessGroup = new ToggleGroup();
                privateRB.setToggleGroup(accessGroup);
                publicRB.setToggleGroup(accessGroup);
                protectedRB.setToggleGroup(accessGroup);
                HBox accessBox = new HBox(10, privateRB, publicRB, protectedRB);
                
                Label typeLabel = new Label("Type:");
                ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("BOOLEAN", "STRING", "CHAR","FLOAT","INT"));
                
                Label staticLabel = new Label("Is Static:");
                RadioButton yesRB = new RadioButton("yes");
                RadioButton noRB = new RadioButton("no");
                ToggleGroup staticGroup = new ToggleGroup();
                yesRB.setToggleGroup(staticGroup);
                noRB.setToggleGroup(staticGroup);
                HBox staticBox = new HBox(10, yesRB, noRB);
                
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.addRow(0, nameLabel, nameField);
                grid.addRow(1, accessLabel, accessBox);
                grid.addRow(2, typeLabel, cb);
                grid.addRow(3, staticLabel,staticBox);
                dialog1.getDialogPane().setContent(grid);
                
                dialog1.setResultConverter(button -> {
                	if(button == addAttBtn) {
                		
                		String name = nameField.getText();
                		boolean st = yesRB.isSelected();
                		


                		
                		Attribute at = new Attribute();
                		at.setName(name);
                		if(st) at.setStatic();
                		if(privateRB.isSelected()) {
                			at.setV(Visibility.PRIVATE);
                		}else if(publicRB.isSelected()) {
                			at.setV(Visibility.PUBLIC);
                		}else if(protectedRB.isSelected()) {
                			at.setV(Visibility.PROTECTED);
                		}
                		if(cb.getSelectionModel().getSelectedItem().equals("BOOLEAN")) {
                			at.setType(Type.BOOLEAN);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("STRING")) {
                			at.setType(Type.STRING);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("CHAR")) {
                			at.setType(Type.CHAR);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("FLOAT")) {
                			at.setType(Type.FLOAT);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("INT")) {
                			at.setType(Type.INT);
                		}
                		
                		return at;
                	}
                	
                	
                	return null;
                });
                
                Optional<Attribute> result = dialog1.showAndWait();
                result.ifPresent(attribute -> {
                    // Add the class to the project
                    // diagram.addClass(classData);
                	attributesList.add(attribute);
                	System.out.println(attribute.getName());
                });
            	
            });
            
            //Create add method button and action
            Button addMethBtn = new Button("Add Method");
            addMethBtn.setOnAction(ev -> {
            	
            	List<Argument> argumentsList = new ArrayList<Argument>();
            	
            	Dialog<Methode> dialog2 = new Dialog<>();
                dialog2.setTitle("Add Method");
                dialog2.setHeaderText("Enter the name and details of the Method");
                dialog2.setResizable(false);
                
                ButtonType addMethoBtn = new ButtonType("Add", ButtonData.OK_DONE);
                dialog2.getDialogPane().getButtonTypes().addAll(addMethoBtn, ButtonType.CANCEL);
                
                Button addArgBtn = new Button("Add Argument");
                addArgBtn.setOnAction(evv -> {
                	
                	Dialog<Argument> dialog3 = new Dialog<>();
                    dialog3.setTitle("Add Argument");
                    dialog3.setHeaderText("Enter the name and details of the Argument");
                    dialog3.setResizable(false);
                    
                    ButtonType addArgsBtn = new ButtonType("Add", ButtonData.OK_DONE);
                    dialog3.getDialogPane().getButtonTypes().addAll(addArgsBtn, ButtonType.CANCEL);
                	
                    Label nameLabel = new Label("Argument Name:");
                    TextField nameField = new TextField();
                    
                    Label typeLabel = new Label("Type:");
                    ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("BOOLEAN", "STRING", "CHAR","FLOAT","INT"));
                    
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.addRow(0, nameLabel, nameField);
                    grid.addRow(1, typeLabel, cb);
                    dialog3.getDialogPane().setContent(grid);
                    
                    dialog3.setResultConverter(button -> {
                    	if(button == addArgsBtn) {
                    		String name = nameField.getText();
                    		Argument arg = new Argument();
                    		arg.setName(name);
                    		if(cb.getSelectionModel().getSelectedItem().equals("BOOLEAN")) {
                    			arg.setType(Type.BOOLEAN);
                    		}else if(cb.getSelectionModel().getSelectedItem().equals("STRING")) {
                    			arg.setType(Type.STRING);
                    		}else if(cb.getSelectionModel().getSelectedItem().equals("CHAR")) {
                    			arg.setType(Type.CHAR);
                    		}else if(cb.getSelectionModel().getSelectedItem().equals("FLOAT")) {
                    			arg.setType(Type.FLOAT);
                    		}else if(cb.getSelectionModel().getSelectedItem().equals("INT")) {
                    			arg.setType(Type.INT);
                    		}
                    		return arg;
                    	}
                    	return null;
                    });
                    
                    Optional<Argument> result = dialog3.showAndWait();
                    result.ifPresent(argument -> {
                        // Add the class to the project
                        // diagram.addClass(classData);
                    	argumentsList.add(argument);
                    	System.out.println(argument.getName());
                    });
                    
                });
                
                Label nameLabel = new Label("Methode Name:");
                TextField nameField = new TextField();
                
                Label accessLabel = new Label("Access Modifier:");
                RadioButton privateRB = new RadioButton("Private");
                RadioButton publicRB = new RadioButton("Public");
                RadioButton protectedRB = new RadioButton("Protected");
                ToggleGroup accessGroup = new ToggleGroup();
                privateRB.setToggleGroup(accessGroup);
                publicRB.setToggleGroup(accessGroup);
                protectedRB.setToggleGroup(accessGroup);
                HBox accessBox = new HBox(10, privateRB, publicRB, protectedRB);
                
                Label typeLabel = new Label("Type:");
                ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("BOOLEAN", "STRING", "CHAR","FLOAT","INT"));
                
                Label staticLabel = new Label("Is Static:");
                RadioButton yesRB = new RadioButton("yes");
                RadioButton noRB = new RadioButton("no");
                ToggleGroup staticGroup = new ToggleGroup();
                yesRB.setToggleGroup(staticGroup);
                noRB.setToggleGroup(staticGroup);
                HBox staticBox = new HBox(10, yesRB, noRB);
                
                Label abstractLabel = new Label("Is Abstract:");
                RadioButton yessRB = new RadioButton("yes");
                RadioButton nooRB = new RadioButton("no");
                ToggleGroup abstractGroup = new ToggleGroup();
                yessRB.setToggleGroup(abstractGroup);
                nooRB.setToggleGroup(abstractGroup);
                HBox abstractBox = new HBox(10, yessRB, nooRB);
                
                Label voidLabel = new Label("Is Void:");
                RadioButton yyesRB = new RadioButton("yes");
                RadioButton nnoRB = new RadioButton("no");
                ToggleGroup voidGroup = new ToggleGroup();
                yyesRB.setToggleGroup(voidGroup);
                nnoRB.setToggleGroup(voidGroup);
                HBox voidBox = new HBox(10, yyesRB, nnoRB);
                
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.addRow(0, nameLabel, nameField);
                grid.addRow(1, accessLabel, accessBox);
                grid.addRow(2, typeLabel, cb);
                grid.addRow(2, voidLabel, voidBox);
                grid.addRow(3, staticLabel,staticBox);
                grid.addRow(4, abstractLabel,abstractBox);
                grid.addRow(5, addArgBtn);
                
                dialog2.getDialogPane().setContent(grid);
                
                dialog2.setResultConverter(button -> {
                	if(button == addMethoBtn) {
                		
                		String name = nameField.getText();
                		boolean st = yesRB.isSelected();
                		


                		
                		Methode meth = new Methode();
                		meth.setName(name);
                		if(st) meth.setStatic();
                		if(privateRB.isSelected()) {
                			meth.setV(Visibility.PRIVATE);
                		}else if(publicRB.isSelected()) {
                			meth.setV(Visibility.PUBLIC);
                		}else if(protectedRB.isSelected()) {
                			meth.setV(Visibility.PROTECTED);
                		}
                		if(cb.getSelectionModel().getSelectedItem().equals("BOOLEAN")) {
                			meth.setReturnType(Type.BOOLEAN);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("STRING")) {
                			meth.setReturnType(Type.STRING);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("CHAR")) {
                			meth.setReturnType(Type.CHAR);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("FLOAT")) {
                			meth.setReturnType(Type.FLOAT);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("INT")) {
                			meth.setReturnType(Type.INT);
                		}
                		if(yyesRB.isSelected()) meth.setVoid();
                		if(yessRB.isSelected()) meth.setAbstract();
                		
                		
                		meth.setArguments(argumentsList);
                		
                		
                		return meth;
                	}
                	
                	
                	return null;
                });
                
                Optional<Methode> result = dialog2.showAndWait();
                result.ifPresent(method -> {
                    // Add the class to the project
                    // diagram.addClass(classData);
                	methodesList.add(method);
                	System.out.println(method.getName()+""+method.getReturnType());
                });
            	
            });
            
            
            
            
            // Create the name field and label
            Label nameLabel = new Label("Class Name:");
            TextField nameField = new TextField();

            // Create the parent class field and label
            Label parentLabel = new Label("Parent Class:");
            //TextField parentField = new TextField();
            ListView<String> listClasses = new ListView<>();
            
        	List<String> classesnames = new ArrayList<String>();
            for (Class c : diagram.getClasses()) {
            	classesnames.add(c.getName());
            }
            
            listClasses.getItems().addAll(classesnames);
            
            
            listClasses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                	classP = newValue;
                    System.out.println("Selected item: " + newValue);
                }
            });
            
            listClasses.setPrefWidth(100);
            listClasses.setPrefHeight(50);
            

            // Create the access modifier field and label
            Label accessLabel = new Label("Access Modifier:");
            RadioButton privateRB = new RadioButton("Private");
            RadioButton publicRB = new RadioButton("Public");
            ToggleGroup accessGroup = new ToggleGroup();
            privateRB.setToggleGroup(accessGroup);
            publicRB.setToggleGroup(accessGroup);
            HBox accessBox = new HBox(10, privateRB, publicRB);

            
            Label abstractLabel = new Label("Is Abstract:");
            RadioButton yesRB = new RadioButton("yes");
            RadioButton noRB = new RadioButton("no");
            ToggleGroup abstractGroup = new ToggleGroup();
            yesRB.setToggleGroup(abstractGroup);
            noRB.setToggleGroup(abstractGroup);
            HBox abstractBox = new HBox(10, yesRB, noRB);
            
            
            // Create the list for interfaces
            Label interfaceLabel = new Label("Implements Interfaces:");
            ListView<String> listInterface = new ListView<>();
            
            List<String> interfacesnames = new ArrayList<String>();
            for (Interface i : diagram.getInterfaces()) {
            	interfacesnames.add(i.getName());
            }
            
            listInterface.getItems().addAll(interfacesnames);
            
            listInterface.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            
            listInterface.setPrefWidth(100);
            listInterface.setPrefHeight(50);
            	
            interfaces = new ArrayList<String>();
            listInterface.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) c -> {
                	
            		List<String> selectedItems = listInterface.getSelectionModel().getSelectedItems();
                	
                	interfaces.addAll(selectedItems);
                	
                    System.out.println("Selected items: " + selectedItems);
              
            });
            /*
            listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                	 
                    System.out.println("Selected item: " + newValue);
                    this.interfaces = newValue;
                }
            });*/
            
            //ScrollPane scrollPane = new ScrollPane();
            //scrollPane.setContent(listView);
            
            //scrollPane.setPrefWidth(200);
            //scrollPane.setPrefHeight(50);
            
            
            
            
            /*
            CheckBox[] interfaceCheckboxes = new CheckBox[] {
                    new CheckBox("Serializable"),
                    new CheckBox("Comparable"),
                    new CheckBox("Iterable")
            }; */
            VBox interfaceBox = new VBox(listInterface);

            // Add the fields to the dialog
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.addRow(0, nameLabel, nameField);
            grid.addRow(1, parentLabel, listClasses);
            grid.addRow(2, accessLabel, accessBox);
            grid.addRow(3, abstractLabel, abstractBox);
            grid.addRow(4, interfaceLabel,interfaceBox);
            grid.addRow(5, addAttrBtn);
            grid.addRow(6, addMethBtn);
            dialog.getDialogPane().setContent(grid);

            // Convert the result to a ClassData object when the add button is clicked
            dialog.setResultConverter(button -> {
                if (button == addBtn) {
                    String name = nameField.getText();
                    //String parent = parentField.getText();
                    boolean isPrivate = privateRB.isSelected();
                    
                    
                    
                    /*
                    List<String> interfaces = Arrays.stream(interfaceCheckboxes)
                        .filter(CheckBox::isSelected)
                        .map(CheckBox::getText)
                        .collect(Collectors.toList());
                    */
                    ArrayList<Interface> l = new ArrayList<Interface>();
                    for(String s : interfaces) {
                    	l.add(diagram.getInterfaceByName(s));
                    }
                    
                    Class a = new Class(name, diagram.getClassByName(classP), isPrivate, l);		
                    
                    if(yesRB.isSelected())  a.turnAbstract(); 	
                    
                    for(Attribute att : attributesList) {
                    	a.addAttribute(att);
                    }
                    
                    for(Methode m : methodesList) {
                    	a.addMethode(m);
                    }
                    
                    return a;
                }
                return null;
            });

            // Show the dialog and add the class if the result is not null
            Optional<Class> result = dialog.showAndWait();
            result.ifPresent(classe -> {
                // Add the class to the project
                // diagram.addClass(classData);
            	diagram.addClass(classe);
            	System.out.println(classe.getName()+ " " +classe.getSuperClass()+ " " +classe.getV()+ " " +classe.getInterfaces());
            	
            	TreeItem<String> nameClassItem1 = new TreeItem<>(classe.getName());
            	classesItem.getChildren().add(nameClassItem1);
            	
            	List<TreeItem<String>> l = new ArrayList<TreeItem<String>>();
            	
            	TreeItem<String> attributesItem1 = new TreeItem<>("Attributes");
                for(Attribute a : classe.getAttributes()) {
                	TreeItem<String> attributeItem1 = new TreeItem<>(a.getName()+":"+a.getType());
                	l.add(attributeItem1);
                }
            	
                List<TreeItem<String>> ll = new ArrayList<TreeItem<String>>();
                
            	TreeItem<String> methodesItem1 = new TreeItem<>("Methodes");
            	for(Methode m : classe.getMethodes()) {
            		TreeItem<String> methodeItem1 = new TreeItem<>(m.getName()+":"+ m.getReturnType());
                	ll.add(methodeItem1);
            	}
            	
                nameClassItem1.getChildren().addAll(attributesItem1, methodesItem1);
                attributesItem1.getChildren().addAll(l);
                methodesItem1.getChildren().addAll(ll);
                
            	
            });
        });
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        Button addInterfaceButton = new Button("Add Interface");
        addInterfaceButton.setOnAction(e -> {
        	
        	List<Attribute> attributesList = new ArrayList<Attribute>();
        	List<Methode> methodesList = new ArrayList<Methode>();
        	
        	// create new dialogue
        	Dialog<Interface> dialog = new Dialog<>();
            dialog.setTitle("Add Interface");
            dialog.setHeaderText("Enter the name and details of the Interface");
            dialog.setResizable(false);
            
            // Set the button types
            ButtonType addBtn = new ButtonType("Add", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);
            
            
            
            
            //Create the add Attribute button and action
            Button addAttrBtn = new Button("Add Attribute");
            addAttrBtn.setOnAction(ev -> {
            	
            	Dialog<Attribute> dialog1 = new Dialog<>();
                dialog1.setTitle("Add Attribute");
                dialog1.setHeaderText("Enter the name and details of the attribute");
                dialog1.setResizable(false);
                
                ButtonType addAttBtn = new ButtonType("Add", ButtonData.OK_DONE);
                dialog1.getDialogPane().getButtonTypes().addAll(addAttBtn, ButtonType.CANCEL);
                
                Label nameLabel = new Label("Attribute Name:");
                TextField nameField = new TextField();
                
                Label accessLabel = new Label("Access Modifier:");
                RadioButton privateRB = new RadioButton("Private");
                RadioButton publicRB = new RadioButton("Public");
                RadioButton protectedRB = new RadioButton("Protected");
                ToggleGroup accessGroup = new ToggleGroup();
                privateRB.setToggleGroup(accessGroup);
                publicRB.setToggleGroup(accessGroup);
                protectedRB.setToggleGroup(accessGroup);
                HBox accessBox = new HBox(10, privateRB, publicRB, protectedRB);
                
                Label typeLabel = new Label("Type:");
                ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("BOOLEAN", "STRING", "CHAR","FLOAT","INT"));
                

                
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.addRow(0, nameLabel, nameField);
                grid.addRow(1, accessLabel, accessBox);
                grid.addRow(2, typeLabel, cb);
              
                dialog1.getDialogPane().setContent(grid);
                
                dialog1.setResultConverter(button -> {
                	if(button == addAttBtn) {
                		
                		String name = nameField.getText();
                		


                		
                		Attribute at = new Attribute();
                		at.setName(name);
                		at.setStatic();
                		if(privateRB.isSelected()) {
                			at.setV(Visibility.PRIVATE);
                		}else if(publicRB.isSelected()) {
                			at.setV(Visibility.PUBLIC);
                		}else if(protectedRB.isSelected()) {
                			at.setV(Visibility.PROTECTED);
                		}
                		if(cb.getSelectionModel().getSelectedItem().equals("BOOLEAN")) {
                			at.setType(Type.BOOLEAN);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("STRING")) {
                			at.setType(Type.STRING);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("CHAR")) {
                			at.setType(Type.CHAR);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("FLOAT")) {
                			at.setType(Type.FLOAT);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("INT")) {
                			at.setType(Type.INT);
                		}
                		
                		return at;
                	}
                	
                	
                	return null;
                });
                
                Optional<Attribute> result = dialog1.showAndWait();
                result.ifPresent(attribute -> {
                    // Add the class to the project
                    // diagram.addClass(classData);
                	attributesList.add(attribute);
                	System.out.println(attribute.getName());
                });
            	
            });
            
            //Create add method button and action
            Button addMethBtn = new Button("Add Method");
            addMethBtn.setOnAction(ev -> {
            	
            	List<Argument> argumentsList = new ArrayList<Argument>();
            	
            	Dialog<Methode> dialog2 = new Dialog<>();
                dialog2.setTitle("Add Method");
                dialog2.setHeaderText("Enter the name and details of the Method");
                dialog2.setResizable(false);
                
                ButtonType addMethoBtn = new ButtonType("Add", ButtonData.OK_DONE);
                dialog2.getDialogPane().getButtonTypes().addAll(addMethoBtn, ButtonType.CANCEL);
                
                Button addArgBtn = new Button("Add Argument");
                addArgBtn.setOnAction(evv -> {
                	
                	Dialog<Argument> dialog3 = new Dialog<>();
                    dialog3.setTitle("Add Argument");
                    dialog3.setHeaderText("Enter the name and details of the Argument");
                    dialog3.setResizable(false);
                    
                    ButtonType addArgsBtn = new ButtonType("Add", ButtonData.OK_DONE);
                    dialog3.getDialogPane().getButtonTypes().addAll(addArgsBtn, ButtonType.CANCEL);
                	
                    Label nameLabel = new Label("Argument Name:");
                    TextField nameField = new TextField();
                    
                    Label typeLabel = new Label("Type:");
                    ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("BOOLEAN", "STRING", "CHAR","FLOAT","INT"));
                    
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.addRow(0, nameLabel, nameField);
                    grid.addRow(1, typeLabel, cb);
                    dialog3.getDialogPane().setContent(grid);
                    
                    dialog3.setResultConverter(button -> {
                    	if(button == addArgsBtn) {
                    		String name = nameField.getText();
                    		Argument arg = new Argument();
                    		arg.setName(name);
                    		if(cb.getSelectionModel().getSelectedItem().equals("BOOLEAN")) {
                    			arg.setType(Type.BOOLEAN);
                    		}else if(cb.getSelectionModel().getSelectedItem().equals("STRING")) {
                    			arg.setType(Type.STRING);
                    		}else if(cb.getSelectionModel().getSelectedItem().equals("CHAR")) {
                    			arg.setType(Type.CHAR);
                    		}else if(cb.getSelectionModel().getSelectedItem().equals("FLOAT")) {
                    			arg.setType(Type.FLOAT);
                    		}else if(cb.getSelectionModel().getSelectedItem().equals("INT")) {
                    			arg.setType(Type.INT);
                    		}
                    		return arg;
                    	}
                    	return null;
                    });
                    
                    Optional<Argument> result = dialog3.showAndWait();
                    result.ifPresent(argument -> {
                        // Add the class to the project
                        // diagram.addClass(classData);
                    	argumentsList.add(argument);
                    	System.out.println(argument.getName());
                    });
                    
                });
                
                Label nameLabel = new Label("Methode Name:");
                TextField nameField = new TextField();
                
                Label accessLabel = new Label("Access Modifier:");
                RadioButton privateRB = new RadioButton("Private");
                RadioButton publicRB = new RadioButton("Public");
                RadioButton protectedRB = new RadioButton("Protected");
                ToggleGroup accessGroup = new ToggleGroup();
                privateRB.setToggleGroup(accessGroup);
                publicRB.setToggleGroup(accessGroup);
                protectedRB.setToggleGroup(accessGroup);
                HBox accessBox = new HBox(10, privateRB, publicRB, protectedRB);
                
                Label typeLabel = new Label("Type:");
                ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("BOOLEAN", "STRING", "CHAR","FLOAT","INT"));
                
                Label staticLabel = new Label("Is Static:");
                RadioButton yesRB = new RadioButton("yes");
                RadioButton noRB = new RadioButton("no");
                ToggleGroup staticGroup = new ToggleGroup();
                yesRB.setToggleGroup(staticGroup);
                noRB.setToggleGroup(staticGroup);
                HBox staticBox = new HBox(10, yesRB, noRB);
                
                
                Label voidLabel = new Label("Is Void:");
                RadioButton yyesRB = new RadioButton("yes");
                RadioButton nnoRB = new RadioButton("no");
                ToggleGroup voidGroup = new ToggleGroup();
                yyesRB.setToggleGroup(voidGroup);
                nnoRB.setToggleGroup(voidGroup);
                HBox voidBox = new HBox(10, yyesRB, nnoRB);
                
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.addRow(0, nameLabel, nameField);
                grid.addRow(1, accessLabel, accessBox);
                grid.addRow(2, typeLabel, cb);
                grid.addRow(2, voidLabel, voidBox);
                grid.addRow(3, staticLabel,staticBox);
                grid.addRow(4, addArgBtn);
                
                
                dialog2.getDialogPane().setContent(grid);
                
                dialog2.setResultConverter(button -> {
                	if(button == addMethoBtn) {
                		
                		String name = nameField.getText();
                		boolean st = yesRB.isSelected();
                		


                		
                		Methode meth = new Methode();
                		meth.setName(name);
                		if(st) meth.setStatic();
                		if(privateRB.isSelected()) {
                			meth.setV(Visibility.PRIVATE);
                		}else if(publicRB.isSelected()) {
                			meth.setV(Visibility.PUBLIC);
                		}else if(protectedRB.isSelected()) {
                			meth.setV(Visibility.PROTECTED);
                		}
                		if(cb.getSelectionModel().getSelectedItem().equals("BOOLEAN")) {
                			meth.setReturnType(Type.BOOLEAN);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("STRING")) {
                			meth.setReturnType(Type.STRING);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("CHAR")) {
                			meth.setReturnType(Type.CHAR);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("FLOAT")) {
                			meth.setReturnType(Type.FLOAT);
                		}else if(cb.getSelectionModel().getSelectedItem().equals("INT")) {
                			meth.setReturnType(Type.INT);
                		}
                		if(yyesRB.isSelected()) meth.setVoid();
                		
                		
                		
                		meth.setArguments(argumentsList);
                		
                		
                		return meth;
                	}
                	
                	
                	return null;
                });
                
                Optional<Methode> result = dialog2.showAndWait();
                result.ifPresent(method -> {
                    // Add the class to the project
                    // diagram.addClass(classData);
                	methodesList.add(method);
                	System.out.println(method.getName()+""+method.getReturnType());
                });
            	
            });
            
            
            
            
            // Create the name field and label
            Label nameLabel = new Label("Interface Name:");
            TextField nameField = new TextField();
            
            
            Label interfaceLabel = new Label("extends Interfaces:");
            ListView<String> listInterface = new ListView<>();
            
            List<String> interfacesnames = new ArrayList<String>();
            for (Interface i : diagram.getInterfaces()) {
            	interfacesnames.add(i.getName());
            }
            
            listInterface.getItems().addAll(interfacesnames);
            listInterface.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            
            listInterface.setPrefWidth(100);
            listInterface.setPrefHeight(50);
            	
            interfaces = new ArrayList<String>();
            listInterface.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) c -> {
                	
            		List<String> selectedItems = listInterface.getSelectionModel().getSelectedItems();
                	
                	interfaces.addAll(selectedItems);
                	
                    System.out.println("Selected items: " + selectedItems);
              
            });
            
            VBox interfaceBox = new VBox(listInterface);

            // Add the fields to the dialog
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.addRow(0, nameLabel, nameField);
            grid.addRow(3, interfaceLabel,interfaceBox);
            grid.addRow(4, addMethBtn);
            grid.addRow(5, addAttrBtn);
            dialog.getDialogPane().setContent(grid);
            
            dialog.setResultConverter(button -> {
                if (button == addBtn) {
                    String name = nameField.getText();
                    //String parent = parentField.getText();
                    //boolean isPrivate = privateRB.isSelected();
                    
                    /*
                    List<String> interfaces = Arrays.stream(interfaceCheckboxes)
                        .filter(CheckBox::isSelected)
                        .map(CheckBox::getText)
                        .collect(Collectors.toList());
                    */
                    
                    ArrayList<Interface> l = new ArrayList<Interface>();
                    for(String s : interfaces) {
                    	l.add(diagram.getInterfaceByName(s));
                    }
                    Interface in = new Interface(name, l);
                    for(Attribute att : attributesList) {
                    	in.addAttribute(att);
                    }
                    for(Methode m : methodesList) {
                    	in.addMethode(m);
                    }
                    return in;
                }
                return null;
            });
            
            Optional<Interface> result = dialog.showAndWait();
            result.ifPresent(interfacee -> {
                // Add the interface to the project
                // diagram.addClass(classData);
            	diagram.addInterface(interfacee);
            	System.out.println(interfacee.getName()+ " " +interfacee.getInterfaces());
            	
            	
            	
            	TreeItem<String> nameInItem1 = new TreeItem<>(interfacee.getName());
            	interfacesItem.getChildren().add(nameInItem1);
            	
            	List<TreeItem<String>> l = new ArrayList<TreeItem<String>>();
            	
            	TreeItem<String> attributesItem1 = new TreeItem<>("Attributes");
                for(Attribute a : interfacee.getAttributes()) {
                	TreeItem<String> attributeItem1 = new TreeItem<>(a.getName()+":"+a.getType());
                	l.add(attributeItem1);
                }
            	
                List<TreeItem<String>> ll = new ArrayList<TreeItem<String>>();
                
            	TreeItem<String> methodesItem1 = new TreeItem<>("Methodes");
            	for(Methode m : interfacee.getMethodes()) {
            		TreeItem<String> methodeItem1 = new TreeItem<>(m.getName()+":"+ m.getReturnType());
                	ll.add(methodeItem1);
            	}
            	
                nameInItem1.getChildren().addAll(attributesItem1, methodesItem1);
                attributesItem1.getChildren().addAll(l);
                methodesItem1.getChildren().addAll(ll);
            	
            	
            	
            	
            });

        });
        
        
        
        
        
        
        
        
        
        
        
        
        Button addRelationButton = new Button("Add Relation");
        addRelationButton.setOnAction(e -> {
        	
        	Dialog<Relation> dialog = new Dialog<>();
        	
            dialog.setTitle("Add Relation");
            dialog.setHeaderText("Enter the name and details of the Relation");
            dialog.setResizable(false);
            
            
            ButtonType addBtn = new ButtonType("Add", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);
            
            Label nameLabel = new Label("Relation Name:");
            TextField nameField = new TextField();
            
            Label typeLabel = new Label("Type Relation:");
            ListView<String> typeRelations = new ListView<>();
            
            typeRelations.getItems().addAll("Aggregation", "Composition");
            
            
            typeRelations.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                	classX = newValue;
                    System.out.println("Selected item: " + newValue);
                }
            });
            
            typeRelations.setPrefWidth(100);
            typeRelations.setPrefHeight(50);
            
            Label mLabel = new Label("Multiplicity: ");
            RadioButton oneRB = new RadioButton("one");
            RadioButton nRB = new RadioButton("n");
            ToggleGroup mGroup = new ToggleGroup();
            oneRB.setToggleGroup(mGroup);
            nRB.setToggleGroup(mGroup);
            HBox mBox = new HBox(10, oneRB, nRB);
        	
            
            Label fromLabel = new Label("From: ");
            ListView<String> fromRelations = new ListView<>();
            List<String> classesnames1 = new ArrayList<String>();
            for (Class i : diagram.getClasses()) {
            	classesnames1.add(i.getName());
            }
            fromRelations.getItems().addAll(classesnames1);
            fromRelations.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                	classQ = newValue;
                    System.out.println("Selected item: " + newValue);
                }
            });
            fromRelations.setPrefWidth(100);
            fromRelations.setPrefHeight(50);
            
            
            
            
            Label toLabel = new Label("To: ");
            ListView<String> toRelations = new ListView<>();
            List<String> classesnames2 = new ArrayList<String>();
            for (Class i : diagram.getClasses()) {
            	classesnames2.add(i.getName());
            }
            toRelations.getItems().addAll(classesnames2);
            toRelations.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                	classR = newValue;
                    System.out.println("Selected item: " + newValue);
                }
            });
            toRelations.setPrefWidth(100);
            toRelations.setPrefHeight(50);
            
            VBox typeBox = new VBox(typeRelations);
            VBox fromBox = new VBox(fromRelations);
            VBox toBox = new VBox(toRelations);

            // Add the fields to the dialog
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.addRow(0, nameLabel, nameField);
            grid.addRow(1, typeLabel, typeBox);
            grid.addRow(2, fromLabel,fromBox);
            grid.addRow(3, toLabel,toBox);
            grid.addRow(4, mLabel, mBox);
            
            dialog.getDialogPane().setContent(grid);
            
            dialog.setResultConverter(button -> {
            	
            	if (button == addBtn) {
            		String name = nameField.getText();
            		
            		
            		Relation r = new Relation();
            		r.setName(name);
            		if(classX != null) {
                		if(classX.equals("Aggregation")) {
                			r.setType(TypeRelation.Agregation);
                		}else if(classX.equals("Composition")) {
                			r.setType(TypeRelation.Composition);
                		}
            		}

            		if(oneRB.isSelected()) {
            			r.setMultiplicity(FirstToSecond.one);
            		}else if(nRB.isSelected()) {
            			r.setMultiplicity(FirstToSecond.n);
            		}
            		r.setFirstClass(diagram.getClassByName(classQ));
            		r.setSecondClass(diagram.getClassByName(classR));
            		
            		return r;
            	}
           
            	return null;
            });
            
            Optional<Relation> result = dialog.showAndWait();
            result.ifPresent(relation -> {
                // Add the interface to the project
                // diagram.addClass(classData);
            	diagram.addRelation(relation);
            	System.out.println(relation.getName()+ " ");
            	
            	TreeItem<String> nameRItem1 = new TreeItem<>(relation.getName());
            	relationsItem.getChildren().add(nameRItem1);
            	
            	TreeItem<String> fromRItem1 = new TreeItem<>("From: "+relation.getFirstClass().getName());
            	TreeItem<String> toRItem1 = new TreeItem<>("To: "+relation.getSecondClass().getName());
            	
            	TreeItem<String> mRItem1;
            	TreeItem<String> multItem1 = new TreeItem<>("multiplicity");
            	
            	if(relation.getMultiplicity() == FirstToSecond.one) {
            		mRItem1 = new TreeItem<>("mul: one");
            		multItem1.getChildren().add(mRItem1);
            	}else if(relation.getMultiplicity() == FirstToSecond.n) {
            		mRItem1 = new TreeItem<>("mul: many");
            		multItem1.getChildren().add(mRItem1);
            	}
            	
            	
            	nameRItem1.getChildren().addAll(fromRItem1, toRItem1, multItem1);
            	
            	
            });
            
            
        });
        
        
        
        
        
        
        
        
        Button generateBtn = new Button("Generate");
        generateBtn.setOnAction(e -> {
        	
        	if(!isGenerating) {
                isGenerating = true;
                generateBtn.setText("Generating...");
                
                // Create a new thread to generate the XML file
                new Thread(() -> {
                    try {
                        // Generate the XML file with DOM4J

                	    Document document = DocumentHelper.createDocument();
                	    Element root = document.addElement( "ClassDiagram" ).addAttribute("id", diagram.getName());
                	    
                	    Element classes = null;
                	    
                	    Element relations = null;
                	    Element relation = null;
                	    Element FirstClasse = null;
                	    Element SecondClasse = null;
                	    
                	    Element class_ = null;
    	    	    
                	    Element implement = null;   
                	    Element superInterface = null;
                	    
                	    Element extend = null;
                	    Element superClass = null;
                	    
                	    Element attributes = null;
                	    Element attribute = null;
                	    
                	    Element methodes = null;
                	    Element methode = null;
                	    Element typeRetour = null;
                	    
                	    Element arguments = null;
                	    Element argument = null;
                	     
                	   
                	    
                	    
                	   // try {
                	    	//root.addAttribute("id", diagram.getName());
                	    	classes = root.addElement("Classes");
                	    	
                		    for(Class cc : diagram.getClasses()) {
                		    	class_ = classes.addElement("Classe");
                		    	class_.addAttribute("id", cc.getName());
                		    	
                		    	if(cc.isAbstract()) {
                		    		class_.addAttribute("isAbstract", "true");
                		    	}else { class_.addAttribute("isAbstract", "false"); }

                		    	if(cc.getV() == Visibility.PUBLIC) {
                		    		class_.addAttribute("visibility", "public");
                		    	}else if(cc.getV() == Visibility.PRIVATE) { class_.addAttribute("visibility", "private"); }
                		    	
                		    	implement = class_.addElement("Implements");
                		     if(cc.getInterfaces()!=null) {
                			    	for(Interface i : cc.getInterfaces()) {
                				    	superInterface = implement.addElement("SuperInterface");
                				    	superInterface.addAttribute("refid", i.getName());		    		
                			    	}		    	 
                		     }

                		    	
                		    	extend = class_.addElement("Extends");
                		    	superClass = extend.addElement("SuperClasse");
                		    	if(cc.getSuperClass() != null ) {
                		    		superClass.addAttribute("refid", cc.getSuperClass().getName());
                		    	}
                		    	
                		    	
                		    	attributes = class_.addElement("Attributs");
                		    	for (Attribute a : cc.getAttributes()) {
                		    		attribute = attributes.addElement("Attribut");
                		    		attribute.addAttribute("id", a.getName());
                		    		
                		    		if(a.isStatic()) {
                		    			attribute.addAttribute("isStatic", "true");
                		    		}else { attribute.addAttribute("isStatic", "false"); }
                		    		
                		    		if(a.getV() == Visibility.PRIVATE) {
                		    			attribute.addAttribute("visibility", "private");
                		    		}else if(a.getV() == Visibility.PROTECTED) {
                		    			attribute.addAttribute("visibility", "protected");
                		    		}else if(a.getV() == Visibility.PUBLIC) {
                		    			attribute.addAttribute("visibility", "public");
                		    		}else attribute.addAttribute("visibility", "public");
                		    	
                		    		if(a.getType() == Type.BOOLEAN) {
                		    			attribute.addAttribute("type", "boolean");
                		    		}else if(a.getType() == Type.STRING) {
                		    			attribute.addAttribute("type", "String");
                		    		}else if(a.getType() == Type.INT) {
                		    			attribute.addAttribute("type", "int");
                		    		}else if(a.getType() == Type.FLOAT) {
                		    			attribute.addAttribute("type", "float");
                		    		}else if(a.getType() == Type.CHAR) {
                		    			attribute.addAttribute("type", "char");
                		    		}
                		    		
                		    	}
                		    	

                		    	
                		    	methodes = class_.addElement("Methodes");
                		    	for(Methode m : cc.getMethodes()) {
                		    		
                		    		methode = methodes.addElement("Methode");
                		    		methode.addAttribute("id", m.getName());
                		    		
                		    		if(m.isAbstract()) {
                    		    		methode.addAttribute("isAbstract", "true");
                		    		}else {
                    		    		methode.addAttribute("isAbstract", "false");
                		    		}
                		    		if(m.isStatic()) {
                		    			methode.addAttribute("isStatic", "true");
                		    		}else {
                		    			methode.addAttribute("isStatic", "false");
                		    		}

                		    		typeRetour = methode.addElement("TypeRetour");
                		    		if(m.isVoid()) {
                		    			typeRetour.addAttribute("isVoid", "true");
                		    		}else {
                		    			typeRetour.addAttribute("isVoid", "false");
                		    		}
                		    		
                		    		
                		    		
                		    		if(m.getReturnType() == Type.BOOLEAN) {
                		    			typeRetour.addAttribute("type", "boolean");
                		    		}else if(m.getReturnType() == Type.STRING) {
                		    			typeRetour.addAttribute("type", "String");
                		    		}else if(m.getReturnType() == Type.INT) {
                		    			typeRetour.addAttribute("type", "int");
                		    		}else if(m.getReturnType() == Type.FLOAT) {
                		    			typeRetour.addAttribute("type", "float");
                		    		}else if(m.getReturnType() == Type.CHAR) {
                		    			typeRetour.addAttribute("type", "char");
                		    		}
                		    		
                		    		arguments = methode.addElement("Arguments");
                		    		for(Argument a : m.getArguments()) {
                		    			argument = arguments.addElement("Argument");
                		    			argument.addAttribute("id", a.getName());
                		    			
                		    			if(a.getType() == Type.BOOLEAN) {
                		    				argument.addAttribute("type", "boolean");
                    		    		}else if(a.getType() == Type.STRING) {
                    		    			argument.addAttribute("type", "String");
                    		    		}else if(a.getType() == Type.INT) {
                    		    			argument.addAttribute("type", "int");
                    		    		}else if(a.getType() == Type.FLOAT) {
                    		    			argument.addAttribute("type", "float");
                    		    		}else if(a.getType() == Type.CHAR) {
                    		    			argument.addAttribute("type", "char");
                    		    		}
                		    			
                		    		}
                		    		
                		    		
                		    	}
                		    	 
                		    	
                		    	
                		    	
                		    	}
                		    	
                		    	relations = classes.addElement("Relations");
                		    	for(Relation r : diagram.getRelations()) {
                		    		relation = relations.addElement("Relation");
                		    		
                		    		if(r.getType() == TypeRelation.Association_simple) {
                		    			relation.addAttribute("type", "association simple");
                		    		}else if(r.getType() == TypeRelation.Agregation) {
                		    			relation.addAttribute("type", "aggregation");
                		    		}else if(r.getType() == TypeRelation.Composition) {
                		    			relation.addAttribute("type", "composition");
                		    		}
                		    		
                		    		if(r.getMultiplicity() == FirstToSecond.one) {
                		    			relation.addAttribute("multiplicity_sf", "1");
                		    		}else if(r.getMultiplicity() == FirstToSecond.n) {
                		    			relation.addAttribute("multiplicity_sf", "n");
                		    		}
                		    		
                		    		FirstClasse = relation.addElement("FirstClasse");
                		    		FirstClasse.addAttribute("refid", r.getFirstClass().getName());
                		    		SecondClasse = relation.addElement("SecondClasse");
                		    		SecondClasse.addAttribute("refid", r.getSecondClass().getName());
                		    		
                		    		
                		    	}
                		    	
                		    Element interfaces = null;
                		    Element interface_ = null;
                		    
                		    Element extend_ = null;
                		    Element superinterface = null;
                		    Element attributes_ = null;
                		    Element attribute_ = null;
                		    Element methodes_ = null;
                		    Element methode_ = null;
                		    Element TypeRetour = null;
                		    Element Arguments = null;
                		    Element Argument = null;
                		    
                		    interfaces = root.addElement("Classes");
                		    
                		    for(Interface in : diagram.getInterfaces()) {
                		    	interface_ = interfaces.addElement("Interface");
                		    	interface_.addAttribute("id", in.getName());

                		    	if(in.getV() == Visibility.PUBLIC) {
                		    		interface_.addAttribute("visibility", "public");
                		    	}else if(in.getV() == Visibility.PRIVATE) { interface_.addAttribute("visibility", "private"); }
                		    	
                		    	extend_ = interface_.addElement("Extends");
                		     if(in.getInterfaces()!=null) {
                			    	for(Interface i : in.getInterfaces()) {
                			    		superinterface = extend_.addElement("SuperInterface");
                			    		superinterface.addAttribute("refid", i.getName());		    		
                			    	}		    	 
                		     }
                		    	
                		    	
                		     	attributes_ = interface_.addElement("Attributs");
                		    	for (Attribute a : in.getAttributes()) {
                		    		attribute_ = attributes.addElement("Attribut");
                		    		attribute_.addAttribute("id", a.getName());
                		    		
                		    		
                		    		attribute_.addAttribute("isStatic", "true");
                		    		
                		    		
                		    		if(a.getV() == Visibility.PRIVATE) {
                		    			attribute_.addAttribute("visibility", "private");
                		    		}else if(a.getV() == Visibility.PROTECTED) {
                		    			attribute_.addAttribute("visibility", "protected");
                		    		}else if(a.getV() == Visibility.PUBLIC) {
                		    			attribute_.addAttribute("visibility", "public");
                		    		}else attribute_.addAttribute("visibility", "public");
                		    	
                		    		if(a.getType() == Type.BOOLEAN) {
                		    			attribute.addAttribute("type", "boolean");
                		    		}else if(a.getType() == Type.STRING) {
                		    			attribute.addAttribute("type", "String");
                		    		}else if(a.getType() == Type.INT) {
                		    			attribute.addAttribute("type", "int");
                		    		}else if(a.getType() == Type.FLOAT) {
                		    			attribute.addAttribute("type", "float");
                		    		}else if(a.getType() == Type.CHAR) {
                		    			attribute.addAttribute("type", "char");
                		    		}
                		    		
                		    	}
                		    	

                		    	
                		    	methodes_ = interfaces.addElement("Methodes");
                		    	for(Methode m : in.getMethodes()) {
                		    		
                		    		methode_ = methodes_.addElement("Methode");
                		    		methode_.addAttribute("id", m.getName());
                		    		
                		    		if(m.isStatic()) {
                		    			methode_.addAttribute("isStatic", "true");
                		    		}else {
                		    			methode_.addAttribute("isStatic", "false");
                		    		}

                		    		TypeRetour = methode_.addElement("TypeRetour");
                		    		if(m.isVoid()) {
                		    			TypeRetour.addAttribute("isVoid", "true");
                		    		}else {
                		    			TypeRetour.addAttribute("isVoid", "false");
                		    		}
                		    		
                		    		
                		    		
                		    		if(m.getReturnType() == Type.BOOLEAN) {
                		    			TypeRetour.addAttribute("type", "boolean");
                		    		}else if(m.getReturnType() == Type.STRING) {
                		    			TypeRetour.addAttribute("type", "String");
                		    		}else if(m.getReturnType() == Type.INT) {
                		    			TypeRetour.addAttribute("type", "int");
                		    		}else if(m.getReturnType() == Type.FLOAT) {
                		    			TypeRetour.addAttribute("type", "float");
                		    		}else if(m.getReturnType() == Type.CHAR) {
                		    			TypeRetour.addAttribute("type", "char");
                		    		}
                		    		
                		    		Arguments = methode_.addElement("Arguments");
                		    		for(Argument a : m.getArguments()) {
                		    			Argument = Arguments.addElement("Argument");
                		    			Argument.addAttribute("id", a.getName());
                		    			
                		    			if(a.getType() == Type.BOOLEAN) {
                		    				Argument.addAttribute("type", "boolean");
                    		    		}else if(a.getType() == Type.STRING) {
                    		    			Argument.addAttribute("type", "String");
                    		    		}else if(a.getType() == Type.INT) {
                    		    			Argument.addAttribute("type", "int");
                    		    		}else if(a.getType() == Type.FLOAT) {
                    		    			Argument.addAttribute("type", "float");
                    		    		}else if(a.getType() == Type.CHAR) {
                    		    			Argument.addAttribute("type", "char");
                    		    		}
                		    			
                		    		}
                		    		
                		    		
                		    	}
                		    	 
                		    	
                		    	
                		    	
                		    	}
                		    
                		    
                		    //livre.addElement("auteur").addText("auteur 1");
                		    //livre.addElement("editeur").addText("editeur 1");
                		    
                	        // create a pretty print format for the output
                	        OutputFormat format = OutputFormat.createPrettyPrint();
                	        // create an XML writer and write the document to a file
                	        XMLWriter writer;
            				try {
            					// Write the XML file to a specific folder
            					writer = new XMLWriter(new FileWriter("test.xml"), format);
            					writer.write(document);
            					writer.close();
            				} catch (IOException e1) {
            					// TODO Auto-generated catch block
            					e1.printStackTrace();
            				}
            				
            				
                    	
            				
            				
            				
            				
            				
            				
            				
                    	
                    	

                        Platform.runLater(() -> {
                        	
                        	
                        	
                        	
                        	SAXBuilder builder = new SAXBuilder();
            				try {
            		            org.jdom2.Document document1 = builder.build("C:\\Users\\user\\OneDrive\\Bureau\\Java_workspace\\userInterface\\test.xml");
            		            org.jdom2.Element rootElement = document1.getRootElement();
            		            //String packageName = rootElement.getAttributeValue("id");
            		            String packageName = "test_jdomm";
            		            File dir = new File(packageName);
            		    		if (!dir.exists()) {
            		    		    dir.mkdirs();
            		    		}
            		            org.jdom2.Element classesElement = rootElement.getChild("Classes");
            		            Map<String, org.jdom2.Element> classesMap = new HashMap<>();
            		            List<org.jdom2.Element> classes1 = classesElement.getChildren("Classe");
            		            for (org.jdom2.Element classe : classes1) {
            		                String className = classe.getAttributeValue("id");
            		                classesMap.put(className, classe);
            		            }
            		            org.jdom2.Element interfacesElement = rootElement.getChild("Interfaces");
            		            Map<String, org.jdom2.Element> interfacesMap = new HashMap<>();
            		            List<org.jdom2.Element> interfaces2=null;
            		            if(interfacesElement!=null) {
            		            	interfaces2 = interfacesElement.getChildren("Interface");
            		            }
            		            if(interfaces2!=null) {
            		            	for (org.jdom2.Element interfaceElement : interfaces2) {
                		                String interfaceName = interfaceElement.getAttributeValue("id");
                		                interfacesMap.put(interfaceName, interfaceElement);
                		            }
            		            }
            		            
            		            org.jdom2.Element relationsElement = classesElement.getChild("Relations");
            		            List<org.jdom2.Element> relations1 = relationsElement.getChildren("Relation");
            		            for (org.jdom2.Element relation1 : relations1) {
            		                String type = relation1.getAttributeValue("type");
            		                String multiplicity = relation1.getAttributeValue("multiplicity_sf");
            		                org.jdom2.Element firstClasseElement = relation1.getChild("FirstClasse");
            		                String firstClasseRefId = firstClasseElement.getAttributeValue("refid");
            		                org.jdom2.Element secondClasseElement = relation1.getChild("SecondClasse");
            		                String secondClasseRefId = secondClasseElement.getAttributeValue("refid");
            		                if (classesMap.containsKey(firstClasseRefId) && classesMap.containsKey(secondClasseRefId)) {
            		                	org.jdom2.Element firstClasse = classesMap.get(firstClasseRefId);
            		                	org.jdom2.Element secondClasse = classesMap.get(secondClasseRefId);
            		                    if ("composition".equals(type)) {
            		                        if ("1".equals(multiplicity)) {
            		                            // add attribute to firstClasse
            		                        	org.jdom2.Element attributElement = new org.jdom2.Element("Attribut");
            		                            attributElement.setAttribute("id", "_composition_attribut");
            		                            attributElement.setAttribute("isStatic", "false");
            		                            attributElement.setAttribute("visibility", "private");
            		                            attributElement.setAttribute("type", secondClasseRefId);
            		                            //firstClasse.getChild("Attributs").addContent(attributElement);
            		                            org.jdom2.Element classe = classesMap.get(firstClasseRefId);
            		                            classe.getChild("Attributs").addContent(attributElement);
            		                        } else if ("n".equals(multiplicity)) {
            		                            // add attribute to firstClasse
            		                        	org.jdom2.Element attributElement = new org.jdom2.Element("Attribut");
            		                            attributElement.setAttribute("id", "_composition_attribut");
            		                            attributElement.setAttribute("isStatic", "false");
            		                            attributElement.setAttribute("visibility", "private");
            		                            attributElement.setAttribute("type", "List<" + secondClasseRefId + ">");
            		                            //firstClasse.getChild("Attributs").addContent(attributElement);
            		                            org.jdom2.Element classe = classesMap.get(firstClasseRefId);
            		                            classe.getChild("Attributs").addContent(attributElement);
            		                        }
            		                    } else if ("aggregation".equals(type)) {
            		                        if ("1".equals(multiplicity)) {
            		                            // add attribute to firstClasse
            		                        	org.jdom2.Element attributElement = new org.jdom2.Element("Attribut");
            		                            attributElement.setAttribute("id", "_aggregation_attribut");
            		                            attributElement.setAttribute("isStatic", "false");
            		                            attributElement.setAttribute("visibility", "private");
            		                            attributElement.setAttribute("type", secondClasseRefId);
            		                            //firstClasse.getChild("Attributs").addContent(attributElement);
            		                            org.jdom2.Element classe = classesMap.get(firstClasseRefId);
            		                            classe.getChild("Attributs").addContent(attributElement);
            		                        } else if ("n".equals(multiplicity)) {
            		                            // add attribute to firstClasse
            		                        	org.jdom2.Element attributElement = new org.jdom2.Element("Attribut");
            		                            attributElement.setAttribute("id", "_aggregation_attribut");
            		                            attributElement.setAttribute("isStatic", "false");
            		                            attributElement.setAttribute("visibility", "private");
            		                            attributElement.setAttribute("type", "List<" + secondClasseRefId + ">");
            		                            //firstClasse.getChild("Attributs").addContent(attributElement);
            		                            org.jdom2.Element classe = classesMap.get(firstClasseRefId);
            		                            classe.getChild("Attributs").addContent(attributElement);
            		                        }
            		                    } else if ("association simple".equals(type)) {
            		                        // add attribute to firstClasse
            		                    	org.jdom2.Element attributElement = new org.jdom2.Element("Attribut");
            		                        attributElement.setAttribute("id", "_association_simple_attribut");
            		                        attributElement.setAttribute("isStatic", "false");
            		                        attributElement.setAttribute("visibility", "private");
            		                        attributElement.setAttribute("type", secondClasseRefId);
            		                        //firstClasse.getChild("Attributs").addContent(attributElement);
            		                        org.jdom2.Element classe = classesMap.get(firstClasseRefId);
            		                        classe.getChild("Attributs").addContent(attributElement);
            		                    }
            		                }
            		            }

            		            StringBuilder sqlBuilder = new StringBuilder();
            		            
            		            for (Map.Entry<String, org.jdom2.Element> entry : classesMap.entrySet()) {
            		                String className = entry.getKey();
            		                org.jdom2.Element classe = entry.getValue();
            		                String isAbstract = classe.getAttributeValue("isAbstract");
            		                String visibility = classe.getAttributeValue("visibility");
            		                if (visibility == null) {
            		                    visibility = "public";
            		                }
            		                StringBuilder classContentBuilder = new StringBuilder();
            		                classContentBuilder.append("package ").append(packageName).append(";\n\n");
            		                classContentBuilder.append(visibility).append(" ");
            		                if ("true".equals(isAbstract)) {
            		                    classContentBuilder.append("abstract ");
            		                }
            		                classContentBuilder.append("class ").append(className);
            		                org.jdom2.Element extendsElement = classe.getChild("Extends");
            		                if (extendsElement != null) {
            		                	org.jdom2.Element superClasseElement = extendsElement.getChild("SuperClasse");
            		                    if (superClasseElement != null) {
            		                        String superClasseRefId = superClasseElement.getAttributeValue("refid");
            		                        if (classesMap.containsKey(superClasseRefId)) {
            		                            classContentBuilder.append(" extends ").append(superClasseRefId);
            		                        }
            		                    }
            		                }
            		                org.jdom2.Element implementesElement = classe.getChild("Implementes");
            		                if (implementesElement != null) {
            		                    List<org.jdom2.Element> superInterfaces = implementesElement.getChildren("SuperInterface");
            		                    if (!superInterfaces.isEmpty()) {
            		                        classContentBuilder.append(" implements ");
            		                        for (int i = 0; i < superInterfaces.size(); i++) {
            		                        	org.jdom2.Element superInterface1 = superInterfaces.get(i);
            		                            String superInterfaceRefId = superInterface1.getAttributeValue("refid");
            		                            if (interfacesMap.containsKey(superInterfaceRefId)) {
            		                                classContentBuilder.append(superInterfaceRefId);
            		                                if (i < superInterfaces.size() - 1) {
            		                                    classContentBuilder.append(", ");
            		                                }
            		                            }
            		                        }
            		                    }
            		                }
            		                classContentBuilder.append(" {\n\n");
            		                
            		                // add attributes
            		                org.jdom2.Element attributsElement = classe.getChild("Attributs");
            		                if (attributsElement != null) {
            		                    List<org.jdom2.Element> attributs = attributsElement.getChildren("Attribut");
            		                    for (org.jdom2.Element attribut : attributs) {
            		                        String attributId = attribut.getAttributeValue("id");
            		                        String attributIsStatic = attribut.getAttributeValue("isStatic");
            		                        String attributVisibility = attribut.getAttributeValue("visibility");
            		                        if (attributVisibility == null) {
            		                        	attributVisibility = "private";
            		                        }
            		                        String attributType = attribut.getAttributeValue("type");
            		                        classContentBuilder.append("\t").append(attributVisibility).append(" ");
            		                        if ("true".equals(attributIsStatic)) {
            		                            classContentBuilder.append("static ");
            		                        }
            		                        classContentBuilder.append(attributType).append(" ").append(attributId).append(";\n\n");
            		                        //
            		                        if ("private".equals(attributVisibility)) {
            		                            // generate getter
            		                            classContentBuilder.append("\tpublic ").append(attributType).append(" get")
            		                                    .append(attributId.substring(0, 1).toUpperCase()).append(attributId.substring(1))
            		                                    .append("() {\n\t\treturn this.").append(attributId).append(";\n\t}\n\n");
            		                            
            		                            // generate setter
            		                            classContentBuilder.append("\tpublic void set")
            		                                    .append(attributId.substring(0, 1).toUpperCase()).append(attributId.substring(1))
            		                                    .append("(").append(attributType).append(" ").append(attributId)
            		                                    .append(") {\n\t\tthis.").append(attributId).append(" = ").append(attributId).append(";\n\t}\n\n");
            		                            
            		                        }
            		                        
            		                    }
            		                    classContentBuilder.append("\n");
            		                }
            		                
            		              //add constructor(ghy default HHHH)
            		                classContentBuilder.append("\n\tpublic ").append(className).append("() {\n\t\t// default constructor body here\n\t}\n\n");
            		                
            		             // add methods
            		                org.jdom2.Element methodesElement = classe.getChild("Methodes");
            		                if (methodesElement != null) {
            		                    List<org.jdom2.Element> methodes1 = methodesElement.getChildren("Methode");
            		                    for (org.jdom2.Element methode1 : methodes1) {
            		                        String methodeId = methode1.getAttributeValue("id");
            		                        String methodeIsAbstract = methode1.getAttributeValue("isAbstract");
            		                        String methodeIsStatic = methode1.getAttributeValue("isStatic");
            		                        org.jdom2.Element typeRetourElement = methode1.getChild("TypeRetour");
            		                        String typeRetourIsVoid = typeRetourElement.getAttributeValue("isVoid");
            		                        String typeRetourType = typeRetourElement.getAttributeValue("type");
            		                        classContentBuilder.append("\t").append(visibility).append(" ");
            		                        if ("true".equals(methodeIsAbstract)) {
            		                            classContentBuilder.append("abstract ");
            		                        }
            		                        if ("true".equals(methodeIsStatic)) {
            		                            classContentBuilder.append("static ");
            		                        }
            		                        if ("true".equals(typeRetourIsVoid)) {
            		                            classContentBuilder.append("void ");
            		                        } else {
            		                            classContentBuilder.append(typeRetourType).append(" ");
            		                        }
            		                        classContentBuilder.append(methodeId).append("(");
            		                        org.jdom2.Element argumentsElement = methode1.getChild("Arguments");
            		                        if (argumentsElement != null) {
            		                            List<org.jdom2.Element> arguments1 = argumentsElement.getChildren("Argument");
            		                            for (int i = 0; i < arguments1.size(); i++) {
            		                            	org.jdom2.Element argument1 = arguments1.get(i);
            		                                String argumentId = argument1.getAttributeValue("id");
            		                                String argumentType = argument1.getAttributeValue("type");
            		                                classContentBuilder.append(argumentType).append(" ").append(argumentId);
            		                                if (i < arguments1.size() - 1) {
            		                                    classContentBuilder.append(", ");
            		                                }
            		                            }
            		                        }
            		                        classContentBuilder.append(") {\n\t\t// method body here\n\t}\n\n");
            		                    }
            		                }

            		                classContentBuilder.append("\n}");
            		                //FileWriter fileWriter = new FileWriter(packageName + "/" + className + ".java");
            		                FileWriter fileWriter = new FileWriter(packageName + File.separator + className + ".java");
            		                fileWriter.write(classContentBuilder.toString());
            		                fileWriter.close();
            		                
            		             // create a new StringBuilder object to hold the C++ code
            		                StringBuilder cppClassContentBuilder = new StringBuilder();

            		                
            		               
            		                cppClassContentBuilder.append("#include <iostream>\n");
            		                cppClassContentBuilder.append("#include <string>\n");
            		                cppClassContentBuilder.append("#include <vector>\n\n");
            		                cppClassContentBuilder.append("struct ").append(className);
            		                
            		                if (extendsElement != null) {
            		                	org.jdom2.Element superClasseElement = extendsElement.getChild("SuperClasse");
            		                    if (superClasseElement != null) {
            		                        String superClasseRefId = superClasseElement.getAttributeValue("refid");
            		                        if (classesMap.containsKey(superClasseRefId)) {
            		                            cppClassContentBuilder.append(" : public ").append(superClasseRefId);
            		                        }
            		                    }
            		                }
            		                
            		                if (implementesElement != null) {
            		                    List<org.jdom2.Element> superInterfaces = implementesElement.getChildren("SuperInterface");
            		                    if (!superInterfaces.isEmpty()) {
            		                        for (int i = 0; i < superInterfaces.size(); i++) {
            		                        	org.jdom2.Element superInterface1 = superInterfaces.get(i);
            		                            String superInterfaceRefId = superInterface1.getAttributeValue("refid");
            		                            if (interfacesMap.containsKey(superInterfaceRefId)) {
            		                                cppClassContentBuilder.append(" : public ").append(superInterfaceRefId);
            		                            }
            		                        }
            		                    }
            		                }
            		                cppClassContentBuilder.append(" {\nprivate:\n");
            		                // add attributes
            		                
            		                if (attributsElement != null) {
            		                    List<org.jdom2.Element> attributs = attributsElement.getChildren("Attribut");
            		                    for (org.jdom2.Element attribut : attributs) {
            		                        String attributId = attribut.getAttributeValue("id");
            		                        String attributIsStatic = attribut.getAttributeValue("isStatic");
            		                        String attributVisibility = attribut.getAttributeValue("visibility");
            		                        if (attributVisibility == null) {
            		                            attributVisibility = "private";
            		                        }
            		                        String attributType = attribut.getAttributeValue("type");
            		                        if ("true".equals(attributIsStatic)) {
            		                            cppClassContentBuilder.append("\tstatic ");
            		                        } else {
            		                            cppClassContentBuilder.append("\t");
            		                        }
            		                        if ("String".equals(attributType)) {
            		                            cppClassContentBuilder.append("std::string ");
            		                        } else if ("List".equals(attributType)) {
            		                            cppClassContentBuilder.append("std::vector<").append(attributType).append("> ");
            		                        } else {
            		                            cppClassContentBuilder.append(attributType).append(" ");
            		                        }
            		                        cppClassContentBuilder.append(attributId).append(";\n\n");
            		                        
            		                        //
            		                        if ("private".equals(attributVisibility)) {
            		                            // generate getter
            		                            if ("String".equals(attributType)) {
            		                                cppClassContentBuilder.append("\tstd::string get")
            		                                        .append(attributId.substring(0, 1).toUpperCase()).append(attributId.substring(1))
            		                                        .append("() {\n\t\treturn this->").append(attributId).append(";\n\t}\n\n");
            		                            } else if ("List".equals(attributType)) {
            		                                cppClassContentBuilder.append("\tstd::vector<").append(attributType).append("> get")
            		                                        .append(attributId.substring(0, 1).toUpperCase()).append(attributId.substring(1))
            		                                        .append("() {\n\t\treturn this->").append(attributId).append(";\n\t}\n\n");
            		                            } else {
            		                                cppClassContentBuilder.append("\t").append(attributType).append(" get")
            		                                        .append(attributId.substring(0, 1).toUpperCase()).append(attributId.substring(1))
            		                                        .append("() {\n\t\treturn this->").append(attributId).append(";\n\t}\n\n");
            		                            }
            		                            // generate setter
            		                            if ("String".equals(attributType)) {
            		                                cppClassContentBuilder.append("\tvoid set")
            		                                        .append(attributId.substring(0, 1).toUpperCase()).append(attributId.substring(1))
            		                                        .append("(std::string ").append(attributId)
            		                                        .append(") {\n\t\tthis->").append(attributId).append(" = ").append(attributId).append(";\n\t}\n\n");
            		                            } else if ("List".equals(attributType)) {
            		                                cppClassContentBuilder.append("\tvoid set")
            		                                        .append(attributId.substring(0, 1).toUpperCase()).append(attributId.substring(1))
            		                                        .append("(std::vector<").append(attributType).append("> ").append(attributId)
            		                                        .append(") {\n\t\tthis->").append(attributId).append(" = ").append(attributId).append(";\n\t}\n\n");
            		                            } else {
            		                                cppClassContentBuilder.append("\tvoid set")
            		                                        .append(attributId.substring(0, 1).toUpperCase()).append(attributId.substring(1))
            		                                        .append("(").append(attributType).append(" ").append(attributId)
            		                                        .append(") {\n\t\tthis->").append(attributId).append(" = ").append(attributId).append(";\n\t}\n\n");
            		                            }
            		                        }
            		                    }
            		                    cppClassContentBuilder.append("\n");
            		                }
            		             // add constructor
            		                cppClassContentBuilder.append("\n\t").append(className).append("() {\n\t\t// default constructor body here\n\t}\n\n");
            		                
            		             // add methods
            		                if (methodesElement != null) {
            		                    List<org.jdom2.Element> methodes1 = methodesElement.getChildren("Methode");
            		                    for (org.jdom2.Element methode1 : methodes1) {
            		                        String methodeId = methode1.getAttributeValue("id");
            		                        String methodeIsStatic = methode1.getAttributeValue("isStatic");
            		                        org.jdom2.Element typeRetourElement = methode1.getChild("TypeRetour");
            		                        String typeRetourIsVoid = typeRetourElement.getAttributeValue("isVoid");
            		                        String typeRetourType = typeRetourElement.getAttributeValue("type");
            		                        if ("true".equals(methodeIsStatic)) {
            		                            cppClassContentBuilder.append("\tstatic ");
            		                        } else {
            		                            cppClassContentBuilder.append("\t");
            		                        }
            		                        if ("true".equals(typeRetourIsVoid)) {
            		                            cppClassContentBuilder.append("void ");
            		                        } else if ("String".equals(typeRetourType)) {
            		                            cppClassContentBuilder.append("std::string ");
            		                        } else if ("List".equals(typeRetourType)) {
            		                            cppClassContentBuilder.append("std::vector<").append(typeRetourType).append("> ");
            		                        } else {
            		                            cppClassContentBuilder.append(typeRetourType).append(" ");
            		                        }
            		                        cppClassContentBuilder.append(methodeId).append("(");
            		                        org.jdom2.Element argumentsElement = methode1.getChild("Arguments");
            		                        if (argumentsElement != null) {
            		                            List<org.jdom2.Element> arguments1 = argumentsElement.getChildren("Argument");
            		                            for (int i = 0; i < arguments1.size(); i++) {
            		                            	org.jdom2.Element argument1 = arguments1.get(i);
            		                                String argumentId = argument1.getAttributeValue("id");
            		                                String argumentType = argument1.getAttributeValue("type");
            		                                if ("String".equals(argumentType)) {
            		                                    cppClassContentBuilder.append("std::string ").append(argumentId);
            		                                } else if ("List".equals(argumentType)) {
            		                                    cppClassContentBuilder.append("std::vector<").append(argumentType).append("> ").append(argumentId);
            		                                } else {
            		                                    cppClassContentBuilder.append(argumentType).append(" ").append(argumentId);
            		                                }
            		                                if (i < arguments1.size() - 1) {
            		                                    cppClassContentBuilder.append(", ");
            		                                }
            		                            }
            		                        }
            		                        cppClassContentBuilder.append(") {\n\t\t// method body here\n\t}\n\n");
            		                    }
            		                }

            		                
            		                // maintenant write the generated C++ code to a .cpp file
            		                FileWriter cppFileWriter = new FileWriter(packageName + "/" + className + ".cpp");
            		                cppFileWriter.write(cppClassContentBuilder.toString());
            		                cppFileWriter.close();
            		                System.out.println("Generating C++ code ");
            		                
            		                //for sql now
            		                sqlBuilder.append("CREATE TABLE ").append(className).append(" (\n");
            		                sqlBuilder.append("\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n");
            		                // add columns for attributes
            		                
            		                if (attributsElement != null) {
            		                    List<org.jdom2.Element> attributs = attributsElement.getChildren("Attribut");
            		                    for (org.jdom2.Element attribut : attributs) {
            		                        String attributId = attribut.getAttributeValue("id");
            		                        String attributType = attribut.getAttributeValue("type");
            		                        sqlBuilder.append("\t").append(attributId).append(" ");
            		                        if ("int".equals(attributType)) {
            		                            sqlBuilder.append("INTEGER");
            		                        } else if ("float".equals(attributType)) {
            		                            sqlBuilder.append("REAL");
            		                        } else if ("String".equals(attributType)) {
            		                            sqlBuilder.append("TEXT");
            		                        } else if ("boolean".equals(attributType)) {
            		                            sqlBuilder.append("INTEGER"); // use 0 and 1 to represent false and true
            		                        } else {
            		                            sqlBuilder.append("TEXT");
            		                        }
            		                        sqlBuilder.append(",\n");
            		                    }
            		                }
            		                // add foreign key columns for relationships
            		                for (org.jdom2.Element relation1 : relations1) {
            		                    String type = relation1.getAttributeValue("type");
            		                    String multiplicity = relation1.getAttributeValue("multiplicity_sf");
            		                    org.jdom2.Element firstClasseElement = relation1.getChild("FirstClasse");
            		                    String firstClasseRefId = firstClasseElement.getAttributeValue("refid");
            		                    org.jdom2.Element secondClasseElement = relation1.getChild("SecondClasse");
            		                    String secondClasseRefId = secondClasseElement.getAttributeValue("refid");
            		                    if (firstClasseRefId.equals(className)) {
            		                        if ("composition".equals(type) || "aggregation".equals(type) || "association simple".equals(type)) {
            		                            if ("1".equals(multiplicity)) {
            		                                // add foreign key column
            		                                sqlBuilder.append("\t").append(secondClasseRefId).append("_id INTEGER REFERENCES ")
            		                                        .append(secondClasseRefId).append("(id),\n");
            		                            }
            		                        }
            		                    }
            		                }
            		                // enlever la dernière ","
            		                sqlBuilder.setLength(sqlBuilder.length() - 2);
            		                sqlBuilder.append("\n);\n\n");
            		            }
            		            
            		            System.out.println("Generating classes for " + classes1.size() + " class elements.");
            		            
            		           //Add Interface: même chose que classe
            		            for (Map.Entry<String, org.jdom2.Element> entry : interfacesMap.entrySet()) {
            		                String interfaceName = entry.getKey();
            		                org.jdom2.Element interfaceElement = entry.getValue();
            		                String visibility = interfaceElement.getAttributeValue("visibility");
            		                if (visibility == null) {
            		                    visibility = "public";
            		                }
            		                StringBuilder interfaceContentBuilder = new StringBuilder();
            		                interfaceContentBuilder.append("package ").append(packageName).append(";\n\n");
            		                interfaceContentBuilder.append(visibility).append(" ");
            		                interfaceContentBuilder.append("interface ").append(interfaceName);
            		                org.jdom2.Element extendsElement = interfaceElement.getChild("Extends");
            		                if (extendsElement != null) {
            		                    List<org.jdom2.Element> superInterfaces = extendsElement.getChildren("SuperInterface");
            		                    if (!superInterfaces.isEmpty()) {
            		                        interfaceContentBuilder.append(" extends ");
            		                        for (int i = 0; i < superInterfaces.size(); i++) {
            		                        	org.jdom2.Element superInterface1 = superInterfaces.get(i);
            		                            String superInterfaceRefId = superInterface1.getAttributeValue("refid");
            		                            if (interfacesMap.containsKey(superInterfaceRefId)) {
            		                                interfaceContentBuilder.append(superInterfaceRefId);
            		                                if (i < superInterfaces.size() - 1) {
            		                                    interfaceContentBuilder.append(", ");
            		                                }
            		                            }
            		                        }
            		                    }
            		                }
            		                interfaceContentBuilder.append(" {\n\n");
            		                // add attributes
            		                org.jdom2.Element attributsElement = interfaceElement.getChild("Attributs");
            		                if (attributsElement != null) {
            		                    List<org.jdom2.Element> attributs = attributsElement.getChildren("Attribut");
            		                    for (org.jdom2.Element attribut : attributs) {
            		                        String attributId = attribut.getAttributeValue("id");
            		                        String attributIsStatic = attribut.getAttributeValue("isStatic");
            		                        String attributVisibility = attribut.getAttributeValue("visibility");
            		                        if (attributVisibility == null) {
            		                        	attributVisibility = "public";
            		                        }
            		                        String attributType = attribut.getAttributeValue("type");
            		                        interfaceContentBuilder.append("\t").append(attributVisibility).append(" ");
            		                        if ("true".equals(attributIsStatic)) {
            		                            interfaceContentBuilder.append("static ");
            		                        }
            		                        interfaceContentBuilder.append(attributType).append(" ").append(attributId).append(";\n");
            		                    }
            		                    interfaceContentBuilder.append("\n");
            		                }
            		                // add methods
            		                org.jdom2.Element methodesElement = interfaceElement.getChild("Methodes");
            		                if (methodesElement != null) {
            		                    List<org.jdom2.Element> methodes1 = methodesElement.getChildren("Methode");
            		                    for (org.jdom2.Element methode1 : methodes1) {
            		                        String methodeId = methode1.getAttributeValue("id");
            		                        org.jdom2.Element typeRetourElement = methode1.getChild("TypeRetour");
            		                        String typeRetourIsVoid = typeRetourElement.getAttributeValue("isVoid");
            		                        String typeRetourType = typeRetourElement.getAttributeValue("type");
            		                        if ("true".equals(typeRetourIsVoid)) {
            		                            interfaceContentBuilder.append("\tvoid ");
            		                        } else {
            		                            interfaceContentBuilder.append("\t").append(typeRetourType).append(" ");
            		                        }
            		                        interfaceContentBuilder.append(methodeId).append("(");
            		                        org.jdom2.Element argumentsElement = methode1.getChild("Arguments");
            		                        if (argumentsElement != null) {
            		                            List<org.jdom2.Element> arguments1 = argumentsElement.getChildren("Argument");
            		                            for (int i = 0; i < arguments1.size(); i++) {
            		                            	org.jdom2.Element argument1 = arguments1.get(i);
            		                                String argumentId = argument1.getAttributeValue("id");
            		                                String argumentType = argument1.getAttributeValue("type");
            		                                interfaceContentBuilder.append(argumentType).append(" ").append(argumentId);
            		                                if (i < arguments1.size() - 1) {
            		                                    interfaceContentBuilder.append(", ");
            		                                }
            		                            }
            		                        }
            		                        interfaceContentBuilder.append(");\n\n");
            		                    }
            		                }
            		                interfaceContentBuilder.append("\n}");
            		                //FileWriter fileWriter = new FileWriter(packageName + "/" + interfaceName + ".java");
            		                FileWriter fileWriter = new FileWriter(packageName + File.separator + interfaceName + ".java");
            		                fileWriter.write(interfaceContentBuilder.toString());
            		                fileWriter.close();
            		                
            		                StringBuilder interfaceContentBuilder1 = new StringBuilder();
            		                interfaceContentBuilder1.append("#include <iostream>\n");
            		                interfaceContentBuilder1.append("#include <string>\n");
            		                interfaceContentBuilder1.append("#include <vector>\n\n");
            		                interfaceContentBuilder1.append("struct ").append(interfaceName);
            		                
            		                if (extendsElement != null) {
            		                    List<org.jdom2.Element> superInterfaces = extendsElement.getChildren("SuperInterface");
            		                    if (!superInterfaces.isEmpty()) {
            		                        for (int i = 0; i < superInterfaces.size(); i++) {
            		                        	org.jdom2.Element superInterface1 = superInterfaces.get(i);
            		                            String superInterfaceRefId = superInterface1.getAttributeValue("refid");
            		                            if (interfacesMap.containsKey(superInterfaceRefId)) {
            		                                interfaceContentBuilder1.append(" : public ").append(superInterfaceRefId);
            		                            }
            		                        }
            		                    }
            		                }
            		                interfaceContentBuilder1.append(" {\nprivate:\n\tvirtual ~").append(interfaceName).append("() {}\n");
            		                // add attributes
            		                
            		                if (attributsElement != null) {
            		                    List<org.jdom2.Element> attributs = attributsElement.getChildren("Attribut");
            		                    for (org.jdom2.Element attribut : attributs) {
            		                        String attributId = attribut.getAttributeValue("id");
            		                        String attributIsStatic = attribut.getAttributeValue("isStatic");
            		                        String attributVisibility = attribut.getAttributeValue("visibility");
            		                        if (attributVisibility == null) {
            		                            attributVisibility = "private";
            		                        }
            		                        String attributType = attribut.getAttributeValue("type");
            		                        if ("true".equals(attributIsStatic)) {
            		                            interfaceContentBuilder1.append("\tstatic ");
            		                        } else {
            		                            interfaceContentBuilder1.append("\t");
            		                        }
            		                        if ("String".equals(attributType)) {
            		                            interfaceContentBuilder1.append("std::string ");
            		                        } else if ("List".equals(attributType)) {
            		                            interfaceContentBuilder1.append("std::vector<").append(attributType).append("> ");
            		                        } else {
            		                            interfaceContentBuilder1.append(attributType).append(" ");
            		                        }
            		                        interfaceContentBuilder1.append(attributId).append(";\n");
            		                    }
            		                    interfaceContentBuilder1.append("\n");
            		                }
            		                // add methods
            		                
            		                if (methodesElement != null) {
            		                    List<org.jdom2.Element> methodes1 = methodesElement.getChildren("Methode");
            		                    for (org.jdom2.Element methode1 : methodes1) {
            		                        String methodeId = methode1.getAttributeValue("id");
            		                        org.jdom2.Element typeRetourElement = methode1.getChild("TypeRetour");
            		                        String typeRetourIsVoid = typeRetourElement.getAttributeValue("isVoid");
            		                        String typeRetourType = typeRetourElement.getAttributeValue("type");
            		                        if ("true".equals(typeRetourIsVoid)) {
            		                            interfaceContentBuilder1.append("\tvirtual void ");
            		                        } else if ("String".equals(typeRetourType)) {
            		                            interfaceContentBuilder1.append("\tvirtual std::string ");
            		                        } else if ("List".equals(typeRetourType)) {
            		                            interfaceContentBuilder1.append("\tvirtual std::vector<").append(typeRetourType).append("> ");
            		                        } else {
            		                            interfaceContentBuilder1.append("\tvirtual ").append(typeRetourType).append(" ");
            		                        }
            		                        interfaceContentBuilder1.append(methodeId).append("(");
            		                        org.jdom2.Element argumentsElement = methode1.getChild("Arguments");
            		                        if (argumentsElement != null) {
            		                            List<org.jdom2.Element> arguments1 = argumentsElement.getChildren("Argument");
            		                            for (int i = 0; i < arguments1.size(); i++) {
            		                            	org.jdom2.Element argument1 = arguments1.get(i);
            		                                String argumentId = argument1.getAttributeValue("id");
            		                                String argumentType = argument1.getAttributeValue("type");
            		                                if ("String".equals(argumentType)) {
            		                                    interfaceContentBuilder1.append("std::string ").append(argumentId);
            		                                } else if ("List".equals(argumentType)) {
            		                                    interfaceContentBuilder1.append("std::vector<").append(argumentType).append("> ").append(argumentId);
            		                                } else {
            		                                    interfaceContentBuilder1.append(argumentType).append(" ").append(argumentId);
            		                                }
            		                                if (i < arguments1.size() - 1) {
            		                                    interfaceContentBuilder1.append(", ");
            		                                }
            		                            }
            		                        }
            		                        interfaceContentBuilder1.append(") = 0;\n\n");
            		                    }
            		                }
            		                interfaceContentBuilder1.append("\n};\n");
            		                // write the generated C++ code to a .cpp file
            		                FileWriter cppFileWriter = new FileWriter(packageName + "/" + interfaceName + ".cpp");
            		                cppFileWriter.write(interfaceContentBuilder1.toString());
            		                cppFileWriter.close();
            		                //for sql
            		                sqlBuilder.append("CREATE TABLE ").append(interfaceName).append(" (\n");
            		                sqlBuilder.append("\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n");
            		                // add columns for attributes
            		                if (attributsElement != null) {
            		                    List<org.jdom2.Element> attributs = attributsElement.getChildren("Attribut");
            		                    for (org.jdom2.Element attribut : attributs) {
            		                        String attributId = attribut.getAttributeValue("id");
            		                        String attributType = attribut.getAttributeValue("type");
            		                        sqlBuilder.append("\t").append(attributId).append(" ");
            		                        if ("int".equals(attributType)) {
            		                            sqlBuilder.append("INTEGER");
            		                        } else if ("float".equals(attributType)) {
            		                            sqlBuilder.append("REAL");
            		                        } else if ("String".equals(attributType)) {
            		                            sqlBuilder.append("TEXT");
            		                        } else if ("boolean".equals(attributType)) {
            		                            sqlBuilder.append("INTEGER"); // use 0 and 1 to represent false and true
            		                        } else if ("List".equals(attributType)) {
            		                            // ignore List attributes as they are handled by relationships
            		                            continue;
            		                        } else {
            		                            // use TEXT as the default column type
            		                            sqlBuilder.append("TEXT");
            		                        }
            		                        sqlBuilder.append(",\n");
            		                    }
            		                }
            		                // remove the last comma and newline
            		                sqlBuilder.setLength(sqlBuilder.length() - 2);
            		                sqlBuilder.append("\n);\n\n"); 
            		            }
            		            
            		            String sqlCode = sqlBuilder.toString();
            		            try (FileWriter fileWriter = new FileWriter(packageName + "/output.sql")) {
            		                fileWriter.write(sqlCode);
            		            } catch (IOException e1) {
            		                e1.printStackTrace();
            		            }
            		            System.out.println("Generating sql tables");
            		            
            		            if(interfaces2!=null) {
            		            	System.out.println("Generating interfaces for " + interfaces2.size() + " interface elements.");
            		            }
            		            
            		        } catch (Exception e2) {
            		            e2.printStackTrace();
            		        }
                        	
                        	
                        	
                        	
                        	
                        	
                        	
                            // Update the UI on the JavaFX application thread
                            classDiagramTree.getRoot().getChildren().get(0).getChildren().clear();
                            classDiagramTree.getRoot().getChildren().get(1).getChildren().clear();
                            classDiagramTree.getRoot().getChildren().get(2).getChildren().clear();
                            // ...
                            // 
                            // ...
                            
                            diagram = new ClassDiagram();
                            
                            generateBtn.setText("Generate");
                            isGenerating = false;
                        });
                        
                        
                        
                        
                    } catch (Exception ex) {
                        // Handle any exceptions that occur during XML generation
                        ex.printStackTrace();
                    }
                }).start();
                
                //second thread
                
               // new Thread(() -> {
                	
                	
                //}).start();
                
        	}
    	        
        });
        


        

        vbox2.getChildren().addAll(label2, addClassBtn, addInterfaceButton, addRelationButton);
        //vbox2.setPadding(new Insets(10));
        
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(vbox2);
        
        
        borderPane.setCenter(treeContainer);
        borderPane.setLeft(hbox);
        borderPane.setBottom(generateBtn);

        scene2 = new Scene(borderPane);

        // Set event handler for next button
        nextButton.setOnAction(event -> {
            diagram.setName(textField1.getText());
            primaryStage.setScene(scene2);
            label2.setText("Project: " + diagram.getName());
        });

        scene1.getStylesheets().add("style1.css");
        scene2.getStylesheets().add("style1.css");
        
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import sun.awt.image.ImageWatched;

import java.io.*;


/**
 *
 * @author JC
 */
public class Main extends Application {
    private TextField name, idNumber, grade;
    private ObservableList<Student> studentItems;

    ListView<Student> students;
    private LinkedList<Student> studentLinkedList;

    public void start(Stage stage) throws Exception{

        BorderPane mainPane = new BorderPane();

        FlowPane contentPane = new FlowPane();
        FlowPane controlsPane = new FlowPane();

        GridPane labelsPane = new GridPane();

        mainPane.setCenter(contentPane);
        mainPane.setBottom(controlsPane);


        name = new TextField();
        idNumber = new TextField();
        grade = new TextField();
        studentLinkedList = new LinkedList<>();

        studentItems =FXCollections.observableArrayList(studentLinkedList.toArrayList());
        students = new ListView<>(studentItems);

        displayStudents();



        Label nameLabel = new Label("Name:");
        Label idNumLabel = new Label("ID Number:");
        Label gradeLabel = new Label("Grade:");


        Button btnAdd = new Button("Add");
        Button btnDelete = new Button("Delete");
        Button btnSave = new Button("Save");
        Button btnSort = new Button("Sort students");

        contentPane.getChildren().add(labelsPane);
        contentPane.getChildren().add(students);

        controlsPane.getChildren().add(btnAdd);
        controlsPane.getChildren().add(btnDelete);
        controlsPane.getChildren().add(btnSave);
        controlsPane.getChildren().add(btnSort);

        controlsPane.setAlignment(Pos.CENTER);
        controlsPane.setPadding(new Insets(20));

        contentPane.setAlignment(Pos.CENTER);
        contentPane.setPadding(new Insets(20));

        labelsPane.add(nameLabel, 0, 0);
        labelsPane.add(idNumLabel, 0, 1);
        labelsPane.add(gradeLabel, 0, 2);


        labelsPane.add(name, 1, 0);
        labelsPane.add(idNumber, 1, 1);
        labelsPane.add(grade, 1, 2);


        labelsPane.setHgap(20);
        labelsPane.setVgap(10);
        contentPane.setVgap(20);
        contentPane.setHgap(20);

        controlsPane.setHgap(20);

        EventHandler<MouseEvent> handlerSelect = new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event){
                resetText();
                displayInfo();
            }
        };

        EventHandler<MouseEvent> handlerAdd = new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event){
                addStudent();
                resetText();
                storeStudents(studentItems);


            }
        };

        EventHandler<MouseEvent> handlerDelete = new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event){

                deleteStudent();
                resetText();
                storeStudents(studentItems);

            }
        };

        EventHandler<MouseEvent> handlerSave = (MouseEvent event) -> {

            updateStudent();
            resetText();
            storeStudents(studentItems);

        };

        EventHandler<MouseEvent> handlerSort = new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event){
                sortStudents();
            }
        };


        btnAdd.setOnMouseClicked(handlerAdd);
        btnDelete.setOnMouseClicked(handlerDelete);
        btnSave.setOnMouseClicked(handlerSave);
        btnSort.setOnMouseClicked(handlerSort);
        students.setOnMouseClicked(handlerSelect);


        Scene scene = new Scene(mainPane);
        stage.setScene(scene);

        stage.show();
    }

    private  void addStudent(){
        Student s = new Student();

        try{
            s.setName(name.getText());
            s.setIdNumber(idNumber.getText());
            s.setGrade(grade.getText());
            studentItems.add(s);
            studentLinkedList.insertAtStart(s);
            studentLinkedList.print();

        }catch(EmptyFieldException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add a student");
            alert.setHeaderText("Error While adding a new student");
            alert.setContentText(e.getMessage());

            alert.showAndWait();

        }

    }

    private void sortStudents(){
        studentLinkedList.quickSort();
        int i=0;
        studentItems.clear();
        while (i<studentLinkedList.size()){
            studentItems.add(studentLinkedList.getElementAt(i));
            i++;
        }
        storeStudents(studentItems);
    }

    private void storeStudents(ObservableList ol){
        Student[] students = new Student[ol.size()];
        for(int i=0; i<students.length;i++){
            students[i]=(Student) ol.get(i);
        }

        try{
            FileOutputStream fos = new FileOutputStream("students.list");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(students);
            oos.close();
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }

    }
    private void displayStudents(){

        try{
            FileInputStream fis = new FileInputStream("students.list");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Student[] students = (Student[]) ois.readObject();
            ois.close();
            for(int i=0; i<students.length; i++){
                studentLinkedList.insertAtStart(students[i]);
                studentItems.add(students[i]);
            }

        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }catch(ClassNotFoundException ce){
            System.out.println(ce.getMessage());
        }



    }
    private void deleteStudent(){
        Student s = students.getSelectionModel().getSelectedItem();

        studentItems.remove(s);
    }
    private void resetText(){
        name.setText("");
        idNumber.setText("");
        grade.setText("");
    }
    private void displayInfo(){
        name.setText(students.getSelectionModel().getSelectedItem().getName());
        idNumber.setText(students.getSelectionModel().getSelectedItem().getIdNumber());
        grade.setText(String.valueOf(students.getSelectionModel().getSelectedItem().getGrade()));

    }
    private void updateStudent(){
        Student s = new Student();
        Student s1 = students.getSelectionModel().getSelectedItem();
        try{

            s.setName(name.getText());
            s.setIdNumber(idNumber.getText());
            s.setGrade(grade.getText());

        }catch(EmptyFieldException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update student");
            alert.setHeaderText("Error While updating a student");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }

        studentItems.set(studentItems.indexOf(s1), s);
    }

    public static void main(String[] args){
        LinkedList<Student> list = new LinkedList<>();
        Student s = new Student();
        try{
            s.setGrade("100");
            s.setName("JC");
            s.setIdNumber("1");
        }catch (EmptyFieldException efe){
            System.out.println(efe.getMessage());
        }

        list.insertAtStart(s);
        list.print();
        launch();

    }
}

module com.example.typo {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.typo to javafx.fxml;
    exports com.example.typo;
}
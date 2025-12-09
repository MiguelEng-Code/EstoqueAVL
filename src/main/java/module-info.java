module br.com.seuNome.meuprimeiroapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires static lombok;

    opens br.com.miguel.estoqueavl to javafx.fxml;

    opens br.com.miguel.estoqueavl.controller to javafx.fxml;

    opens br.com.miguel.estoqueavl.model to com.google.gson, javafx.base;

    exports br.com.miguel.estoqueavl;
}
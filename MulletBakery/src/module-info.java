module MulletBakery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // diperlukan agar FXMLLoader bisa mengakses controller & model
    opens application to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;
    opens model to javafx.base;
    opens util to javafx.fxml;
    opens views to javafx.fxml;

    exports application;
    exports controller;
    exports model;
    exports dao;
    exports dao.impl;
    exports service;
    exports util;
}
module com.guitool.tpbomb {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires http.request;
    requires org.apache.commons.lang3;

    opens com.guitool.tpbomb to javafx.fxml;
    exports com.guitool.tpbomb;
    exports com.guitool.tpbomb.view;
    exports com.guitool.tpbomb.controller;
}
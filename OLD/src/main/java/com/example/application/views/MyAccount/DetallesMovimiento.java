package com.example.application.views.MyAccount;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route("basic-layouts/DetallesMovimiento")
public class DetallesMovimiento extends Div {
    public DetallesMovimiento() {
        // tag::snippet[]
        VerticalLayout layout = new VerticalLayout();
        layout.add(new H2("Detalles Movimiento"));
        layout.add(new Span("Cuenta origen:"));
        layout.add(new Span("Cuenta destino:"));
        layout.add(new Span("Valor:"));
        layout.add(new Span("Fecha:"));
        // end::snippet[]

        this.setClassName("basic-layouts-example");
        this.add(layout);
    }
    
    

}

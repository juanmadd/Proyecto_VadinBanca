package com.example.application.views.MyAccount;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route("basic-layouts/DetallesDomiciliacion")
public class DetallesDomiciliacion extends Div {
    public DetallesDomiciliacion() {
        // tag::snippet[]
    	VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.add(new H2("Detalles Domiciliacion"));
        layout.add(new Span("Concepto"));
        layout.add(new Span("Valor"));
        layout.add(new Span("Fecha"));
        // end::snippet[]

        this.setClassName("basic-layouts-example");
        this.add(layout);   
    }
}

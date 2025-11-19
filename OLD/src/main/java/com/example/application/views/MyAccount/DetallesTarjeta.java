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
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;

@SuppressWarnings("serial")
@Route("basic-layouts/DetallesTarjeta")
public class DetallesTarjeta extends Div {
    public DetallesTarjeta() {
        // tag::snippet[]
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.add(new H2("Detalles Tarjeta"));
        layout.add(new Span("xxxx xxxx xxxx xxxx"));
        layout.add(new Span("Valido hasta:"));
        layout.add(new Span("CVV:"));
        layout.add(new H4("Limite actual:"));
        LimiteDinero();
        Pin();
        
        // end::snippet[]

        this.setClassName("basic-layouts-example");
        this.add(layout);   
    }
   
    public void LimiteDinero() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setPadding(true);
        
        NumberField euroField = new NumberField();
        euroField.setLabel("Valor minimo");
        euroField.setValue(0.0);
        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        euroField.setSuffixComponent(euroSuffix);
        
        Button secondaryButton = new Button("Guardar");
        secondaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        
        layout.add(euroField, secondaryButton);
        this.setClassName("basic-layouts-example");
        this.add(layout);
    }
    
    public void Pin() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setPadding(true);
        
        NumberField euroField = new NumberField();
        euroField.setLabel("Nuevo pin");
        euroField.setValue(0.0);
        Div euroSuffix = new Div();
        euroField.setSuffixComponent(euroSuffix);
        
        Button secondaryButton = new Button("Guardar");
        secondaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        
        layout.add(euroField, secondaryButton);
        this.setClassName("basic-layouts-example");
        this.add(layout);
    }
    
    

}

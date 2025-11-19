package com.example.application.views.Transfer;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;

import javax.annotation.security.PermitAll;

@PageTitle("Transferecia")
@Route(value = "transfer", layout = MainLayout.class)
@PermitAll
public class TransferView extends VerticalLayout {


    private TextField cuentaOrigen = new TextField("Cuenta de origen");
    private TextField cuentaDestino = new TextField("Cuenta de destino");
    private Month month;
    private int year;
    private DateTransfer expiration = new DateTransfer("Fecha de transferencia", month, year);
    private BigDecimalField csc = new BigDecimalField("Cantidad");

    private Button cancel = new Button("Cancel");
    private Button submit = new Button("Submit");

    public TransferView() {
        addClassName("credit-card-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        cancel.addClickListener(e -> {
            Notification.show("No ha sido implementado");
        });
        submit.addClickListener(e -> {
            Notification.show("No ha sido implementado");
        });
    }

    private Component createTitle() {
        return new H3("Transferencia entre cuentas");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(cuentaOrigen, cuentaDestino, expiration, csc);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        Calendar c1 = Calendar.getInstance();
        cuentaOrigen.setPattern("[\\d ]*");
        cuentaOrigen.setPreventInvalidInput(true);
        cuentaOrigen.setRequired(true);
        cuentaOrigen.setErrorMessage("Please enter a valid account number");
        month = LocalDate.now().getMonth();
        year = LocalDate.now().getYear();
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(submit);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private class DateTransfer extends CustomField<String> {
        public DateTransfer(String label, Month month, int year) {
            setLabel(label);
            /*HorizontalLayout layout = new HorizontalLayout(month, year);
            layout.setFlexGrow(1.0, month, year);
            month.setWidth("100px");
            year.setWidth("100px");
            add(layout);*/
        }

        @Override
        protected String generateModelValue() {
            // Unused as month and year fields part are of the outer class
            return "";
        }

        @Override
        protected void setPresentationValue(String newPresentationValue) {
            // Unused as month and year fields part are of the outer class
        }

    }

}

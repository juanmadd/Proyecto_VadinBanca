package com.example.application.views.transferencias;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Movimiento;
import com.example.application.data.entity.User;
import com.example.application.data.service.CuentaService;
import com.example.application.data.service.MovimientoService;
import com.example.application.data.service.UserService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.security.PermitAll;
@PageTitle("Transferencias")
@Route(value = "Transferencias", layout = MainLayout.class)
@PermitAll
public class TransferenciasView extends Div {
	
	private AuthenticatedUser authenticatedUser;
	private UserService userservice;
	private CuentaService cuentaservice;
	private MovimientoService movimientoservice;
    private Button cancel;
    private Button submit;
  

    public TransferenciasView(AuthenticatedUser authenticatedUser, UserService userservice, CuentaService cuentaservice, MovimientoService movimientoservice) {
        addClassName("transferencias-view");
        
        this.authenticatedUser = authenticatedUser;
        this.userservice = userservice;
        this.cuentaservice = cuentaservice;
        this.movimientoservice = movimientoservice;
        Optional<User> maybeUser = authenticatedUser.get();
        User userActual = maybeUser.get();
        add(createTitle());
        add(createFormLayout(authenticatedUser,userservice,cuentaservice,movimientoservice));
        
    }
    
    private Component createTitle() {
        return new H3("Realizar Transferencia");
    }

    private Component createFormLayout(AuthenticatedUser authenticatedUser, UserService userservice, CuentaService cuentaservice, MovimientoService movimientoservice) {
    	
    	this.authenticatedUser = authenticatedUser;
        this.userservice = userservice;
        this.cuentaservice = cuentaservice;
        this.movimientoservice = movimientoservice;
        Optional<User> maybeUser = authenticatedUser.get();
        User userActual = maybeUser.get();
    	
        Binder<Movimiento> binder = new Binder<>(Movimiento.class);
        Binder<Cuenta> binderC = new Binder<>(Cuenta.class);
             
        List<String> items = cuentaservice.getByUser(userActual);

        FormLayout form = new FormLayout();
        TextField concepto = new TextField("Concepto");
        DatePicker dFecha = new DatePicker("dFecha");
        dFecha.setValue(LocalDate.now());
        dFecha.setReadOnly(true);
        NumberField fValor = new NumberField("fValor");
        Select<String> cuentaOrigen = new Select<>();
        cuentaOrigen.setLabel("Cuenta origen");
        cuentaOrigen.setItems(items);
        TextField cuentaDestino = new TextField("Cuenta destino");
        form.add(concepto,dFecha,fValor,cuentaOrigen,cuentaDestino);
        binder.bind(concepto, "concepto");
        binder.bind(dFecha, "dFecha");
        binder.bind(fValor, "fValor");
        binder.bind(cuentaOrigen, "cuentaOrigen");
        binder.bind(cuentaDestino, "cuentaDestino");
        
        binder.forField(concepto)
        .withValidator(value -> value != "", "Indique el concepto")
        .bind(Movimiento::getConcepto, Movimiento::setConcepto);
        
        binderC.forField(cuentaOrigen)
        .withValidator(value -> value != null, "Seleccione una cuenta")
        .bind(Cuenta::getIban, Cuenta::setIban);
                
		binder.forField(fValor)
		 .withValidator(value -> value != null , "Indique un valor para la transferencia")
	     .bind(Movimiento::getfValor, Movimiento::setfValor);
							
		/*binder.forField(cuentaDestino)
		.withValidator(value -> value == cuentaOrigen.getValue(), "No se puede pasar dinero a la misma cuenta")
		.bind(Movimiento::getCuentaOrigen, Movimiento::setCuentaOrigen);*/
		
		binder.forField(cuentaDestino)
		.withValidator(value -> value != "", "Indique un valor de destino para la transferencia")
		.bind(Movimiento::getCuentaOrigen, Movimiento::setCuentaOrigen);
        
        //Logica
               
        Button submit = new Button("Crear movimiento");
        submit.addClickListener(e -> {
            Movimiento movimiento = new Movimiento();
            try {
            	if (binder.validate().isOk() && binderC.validate().isOk() && cuentaOrigen.getValue() != cuentaDestino.getValue() && cuentaDestino.getValue() != null) {
            		String ibanCuenta = cuentaOrigen.getValue();
            		Cuenta cu = new Cuenta();
            		cu = cuentaservice.getByIban(ibanCuenta);
            		
            		if(fValor.getValue() < 0) {
        				Notification notification = Notification.show("Error, el valor de la transferencia debe ser mayor a 0");
		                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		                fValor.clear();
		                concepto.clear();
        			}
        			if(fValor.getValue() > cu.getSaldo()) {
            			Notification notification = Notification.show("Error, saldo insuficiente");
		                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		                fValor.clear();
		                concepto.clear();
        			}
        			if(cuentaOrigen.getValue() == cuentaDestino.getValue()) {
            			Notification notification = Notification.show("Error, no se puede transferir dinero a la misma cuenta");
		                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		                fValor.clear();
		                concepto.clear();
        			}
        			if(cuentaDestino.getValue() == null) {
            			Notification notification = Notification.show("Error, Indique un valor para la cuenta destino");
		                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		                fValor.clear();
		                concepto.clear();
        			}
            		
            		if(fValor.getValue() < cu.getSaldo() && fValor.getValue() > 0) {
	            		Float nuevoSaldo = (float) ((float) cu.getSaldo()-fValor.getValue());
	            		
	            		//Agregar un nuevo movimiento y modificar el estado de la cuenta Origen
	            		cu.setSaldo(nuevoSaldo);
		                binder.writeBean(movimiento);
		                movimiento.setCuentaOrigen(cuentaOrigen.getValue());
		                movimiento.setCuentaDestino(cuentaDestino.getValue());
		                movimientoservice.update(movimiento);
		                binderC.writeBean(cu);
		                cuentaservice.update(cu);
		               
		                //Modificar el estado de la cuenta Destino (si existe en nuestra BD)
		                Cuenta cuDestino = new Cuenta();
		                
		                cuDestino = cuentaservice.getByIban(cuentaDestino.getValue());
		                if(cuDestino != null) {
			                Float nuevoSaldoDestino = (float) ((float) cuDestino.getSaldo()+fValor.getValue());
			                cuDestino.setSaldo(nuevoSaldoDestino);
			                //binderC.writeBean(cuDestino);
			                cuentaservice.update(cuDestino);
		                }

		                Notification notification = Notification.show("Transferencia realizada!");
		                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		                //fValor.clear();
		                //concepto.clear();
		                UI.getCurrent().getPage().reload();
            		}
            	}
                //guardar movimiento en tu base de datos o en una lista
            } catch (ValidationException ex) {
            	 Notification notification = Notification.show("Error al realizar la transferencia, consulte este caso a su asesor.");
                 notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                // manejar excepción de validación
            }
        });

        Button cancel = new Button("Cancelar");
    	cancel.addClickListener(e -> {
    		concepto.clear();
         	fValor.clear();
         	cuentaOrigen.clear();
         	cuentaDestino.clear();
         });
    	form.add(submit,cancel);
        return form;
    }

    
}
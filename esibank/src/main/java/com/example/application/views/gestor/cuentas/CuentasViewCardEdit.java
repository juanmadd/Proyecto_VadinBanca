package com.example.application.views.gestor.cuentas;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Noticia;
import com.example.application.data.entity.Tarjeta;
import com.example.application.data.entity.User;
import com.example.application.data.service.CuentaService;
import com.example.application.data.service.NoticiaService;
import com.example.application.data.service.TarjetaService;
import com.example.application.data.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

@SuppressWarnings("serial")
@RolesAllowed("ADMIN")
public class CuentasViewCardEdit extends ListItem {
	private NoticiaService noticiaService;
	private CuentaService cuentaservice;
	private UserService userservice;
	private TarjetaService tarjetaservice;
	List<Noticia> noticias;
	List<Cuenta> cuentas;
	private Button deleteButton = new Button("Eliminar");
	private Button editButton = new Button("Editar");

    public CuentasViewCardEdit(String iban, String nombre, Long cuentaId,CuentaService cuentaservice, UserService userservice, TarjetaService tarjetaservice) {
    	this.cuentaservice = cuentaservice;
    	this.userservice = userservice;
    	this.tarjetaservice = tarjetaservice;
    	Cuenta cuentaActual = cuentaservice.getById(cuentaId);
        User userActual = cuentaActual.getUser();
        
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");

        
        
        //Delete dialog
        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(iban);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        
        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Borrar cuenta: " + iban);
        dialog.setText(
                "Estas seguro de que quieres borrar esta cuenta?");

        dialog.setCancelable(true);
        dialog.addCancelListener(event -> dialog.close());

        dialog.setConfirmText("Delete");
        dialog.addConfirmListener(event -> {
        	cuentaservice.delete(cuentaId);
        	Notification notification = Notification.show("Noticia borrada!");
        	UI.getCurrent().getPage().reload();
        	notification.addThemeVariants(NotificationVariant.LUMO_ERROR);	
        } );

        deleteButton.addClickListener(event -> {
        	dialog.open();
		});
        
        //EDIT dialog
        Dialog dialogEdit = new Dialog();

        dialogEdit.setHeaderTitle("Estas editando la cuenta: " + iban);
        DatePicker dFecha = new DatePicker("dFecha");
        dFecha.setValue(LocalDate.now());
        dFecha.setReadOnly(true);
        TextField ibanf = new TextField("Titulo");
        ibanf.setPlaceholder(iban);
        Float saldof = cuentaActual.getSaldo();
 
        VerticalLayout dialogEditLayout =  new VerticalLayout(ibanf);
        dialogEdit.add(dialogEditLayout);
        dialogEditLayout.setPadding(false);
        dialogEditLayout.setSpacing(false);
        dialogEditLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogEditLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        
        
        Button saveButton = new Button("Guardar", e ->{ 
        	cuentaservice.delete(cuentaId);
        	if(ibanf.getValue() == "") {
        		ibanf.setValue(iban);
        	}
        	        	
        	Cuenta cuentaEditable = new Cuenta (ibanf.getValue(),saldof,dFecha.getValue(),userActual);
        	cuentaEditable.setId(cuentaId);
    		cuentaservice.update(cuentaEditable);
    		Notification notification = Notification.show("Cuenta modificada correctamente.");
    		dialogEdit.close();
    		UI.getCurrent().getPage().reload();
    	} );
        
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancelar", e -> dialogEdit.close());
        dialogEdit.getFooter().add(cancelButton);
        dialogEdit.getFooter().add(saveButton);

        Button button = new Button("Editar", e -> dialogEdit.open());
        editButton.addClickListener(event -> {
        	dialogEdit.open();
		});
        
        //Dialog tarjetas
        Dialog dialogTarjetas = new Dialog();
        
        FormLayout form = new FormLayout();
        
        List<Tarjeta> listaTarjeta = tarjetaservice.getTarjetasByCuenta(cuentaActual);

        dialogEdit.setHeaderTitle("Estas editando la Tarjeta: " + iban);
        Select<Tarjeta> numTarjeta = new Select<>();
        numTarjeta.setLabel("Elija una tarjeta");
        numTarjeta.setItemLabelGenerator(Tarjeta::getNumero);
        numTarjeta.setItems(listaTarjeta);
        
           
        NumberField pin = new NumberField("PIN");
        
        NumberField limiteMax = new NumberField("Limite Maximo");

        NumberField limiteMin = new NumberField("Limite Minimo");
        
        Checkbox checkbox = new Checkbox();
        checkbox.setLabel("Activar Tarjeta");

                      
        form.add(pin, limiteMax, limiteMin, checkbox);
        Button guardarButton = new Button("Guardar Cambios");
        guardarButton.addClickListener(event -> {
        	Tarjeta tarjetaActual = new Tarjeta();
        	tarjetaActual = tarjetaservice.getByNumber(numTarjeta.getValue().getNumero());
        	tarjetaActual.setActiva(checkbox.getValue());
        	tarjetaActual.setLimite_maximo(limiteMax.getValue() != null ? limiteMax.getValue().floatValue() : 0.0f);
        	tarjetaActual.setLimite_minimo(limiteMin.getValue() != null ? limiteMin.getValue().floatValue() : 0.0f);
        	tarjetaActual.setPin(pin.getValue() != null ? pin.getValue().intValue() : 0);
        	tarjetaservice.update(tarjetaActual);
        	Notification notification = Notification.show("Tarjeta modificada correctamente");
    		dialogEdit.close();
    		UI.getCurrent().getPage().reload();
        	
        }		
        );
        form.add(guardarButton);
                
        VerticalLayout dialogTarjetasLayout =  new VerticalLayout();
        dialogTarjetasLayout.add(numTarjeta, form);
        dialogTarjetas.add(dialogTarjetasLayout);
        dialogTarjetasLayout.setPadding(false);
        dialogTarjetasLayout.setSpacing(false);
        dialogTarjetasLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogTarjetasLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        
        Button tarjetas = new Button("Gestionar Tarjetas");
        tarjetas.addClickListener(event -> {
        	dialogTarjetas.open();
		});
                
        add(div, header, deleteButton, editButton, tarjetas);
    }
}

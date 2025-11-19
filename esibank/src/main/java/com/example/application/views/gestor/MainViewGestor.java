package com.example.application.views.gestor;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.prepost.PreAuthorize;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@RolesAllowed("ADMIN")
@Route(value = "Gestor", layout = MainLayout.class)
public class MainViewGestor extends VerticalLayout {

    public MainViewGestor() {
        // Crea 4 botones con textos
        Button view1Button = new Button("Modificar Noticias");
        Button view2Button = new Button("Modificar Usuarios");
        Button view3Button = new Button("Modificar Cuentas");

        
        view1Button.addClickListener(event ->
            UI.getCurrent().navigate("crud-noticias")
        );
        view3Button.addClickListener(event ->
            UI.getCurrent().navigate("crud-cuentas")
        );
        view2Button.addClickListener(event ->{
        	Notification notification = Notification.show("Menú en desarrollo...");
        	notification.addThemeVariants(NotificationVariant.LUMO_ERROR);}
	        
        );

        
        add(view1Button, view3Button, view2Button);
    }
}

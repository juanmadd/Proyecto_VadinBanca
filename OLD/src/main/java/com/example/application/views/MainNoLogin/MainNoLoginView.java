package com.example.application.views.MainNoLogin;

import com.example.application.components.appnav.AppNav;


import com.example.application.components.appnav.AppNavItem;
import com.example.application.data.Role;
import com.example.application.data.entity.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.example.application.views.MainNoLoginLayout;
import com.example.application.views.Moves.MovesView;
import com.example.application.views.MyAccount.MyAccountView;
import com.example.application.views.MyProducts.MyProductsView;
import com.example.application.views.Resume.ResumeView;
import com.example.application.views.Transfer.TransferView;
import com.nimbusds.jose.util.Container;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@AnonymousAllowed
@PageTitle("Home")
@Route(value = "", layout = MainNoLoginLayout.class)
public class MainNoLoginView extends VerticalLayout {

    private H2 viewTitle;
    
    @PersistenceContext(name = "UnidadUser")
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadUser");
	@PersistenceContext(name = "UnidadUser")
	EntityManager em = emf.createEntityManager();

    public MainNoLoginView() {
    	setSpacing(false);
    	    	
    	Image img = new Image("images/sacar_dinero.png", "sacar dinero");
        img.setWidth("400px");
        img.setHeight("300px");
        add(img);

        add(new H2("Cómo sacar dinero sin tarjeta"));
        
        Image img2 = new Image("images/banca_digital.png", "banca digital");
        img2.setWidth("400px");
        img2.setHeight("300px");
        add(img2);

        add(new H2("Banca digital"));

        Image img3 = new Image("images/nuestras_oficinas.png", "nuestras oficinas");
        img3.setWidth("400px");
        img3.setHeight("300px");
        add(img3);

        add(new H2("Nuestras oficinas"));
        
        Image img4 = new Image("images/tipos_cuentas.png", "tipos cuentas");
        img4.setWidth("400px");
        img4.setHeight("300px");
        add(img4);
        add(new H2("¿Qué tipos de cuentras existen?"));
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.BETWEEN);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "");
        
    }

}
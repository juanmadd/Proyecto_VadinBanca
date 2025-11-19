package com.example.application.views.MyAccount;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.List;

import javax.annotation.security.PermitAll;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.example.application.data.entity.User;
import com.example.application.data.entity.Movimiento;

@PageTitle("Mi cuenta")
@Route(value = "myaccount", layout = MainLayout.class)
@PermitAll
public class MyAccountView extends VerticalLayout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyAccountView() {
        setSpacing(false);
        
        Select<String> select = new Select<>();
        select.setLabel("Cuentas bancarias"); 
        select.setItems("Most recent first", 
        		"Rating: high to low",
                "Rating: low to high", 
                "Price: high to low",
                "Price: low to high"); //Cuentas bancarias de la BD

        add(select);
        Button button = new Button("Mostrar informacion de la cuenta seleccionada");
        button.addClickListener(clickEvent -> {
            //Consulta con la cuenta seleccionada
        	//mostrar info de esa cuenta
        });
        
        //TENEMOS YA LA CUENTA SELEECIONADA
        //Buscamos en movimientos del sistema, solo los de esa cuenta. (consulta SQL) - rs
        //
        
        Grid<Movimiento> grid = new Grid<>(Movimiento.class, false);
        grid.addColumn(Movimiento::getdFecha).setHeader("Fecha realizacion");
        grid.addColumn(Movimiento::getCuentaOrigen).setHeader("Cuenta origen");
        grid.addColumn(Movimiento::getCuentaDestino).setHeader("Cuenta destino");
        grid.addColumn(Movimiento::getfValor).setHeader("Cantidad");
        
        /*DataProvider<Movimiento, String> dataProvider = new DatabaseDataProvider();
        DataService<Movimiento> dataService = new DataService();

        List<Movimiento> mov = dataService.getMovimiento();
        grid.setItems(mov);*/
       
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");   
    }

}

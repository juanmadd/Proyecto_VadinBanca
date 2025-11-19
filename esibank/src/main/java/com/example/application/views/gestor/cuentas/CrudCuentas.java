package com.example.application.views.gestor.cuentas;
import javax.annotation.security.RolesAllowed;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.User;
import com.example.application.data.service.CuentaService;
import com.example.application.data.service.TarjetaService;
import com.example.application.data.service.UserService;
import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("serial")
@RolesAllowed("ADMIN")
@PageTitle("CrudCuentas")
@Route(value = "crud-cuentas", layout = MainLayout.class)
public class CrudCuentas extends Div {
	
	private AuthenticatedUser authenticatedUser;
	private CuentaService cuentaservice;
	private UserService userservice;
	private TarjetaService tarjetaservice;
	private FormLayout form = new FormLayout();
	private TextField ibanf = new TextField("IBAN");
	private TextField userIdf = new TextField("Texto");
	private DatePicker dFecha = new DatePicker("Fecha Actual");
	private Button saveButton = new Button("Guardar");
	private OrderedList imageContainer = new OrderedList();
	private static Float saldof = (float) 0.0;
	List<User> usersList;
	List<Cuenta> cuentas;
	Cuenta cuenta;
	User user;
	
	public CrudCuentas(CuentaService cuentaservice, UserService userservice,  AuthenticatedUser authenticatedUser,
			List<User> usersList, List<Cuenta> cuentas, User user, TarjetaService tarjetaservice) {
		this.cuentaservice = cuentaservice;
		this.userservice = userservice;
		this.authenticatedUser = authenticatedUser;
		this.tarjetaservice = tarjetaservice;
		usersList = userservice.getAllUsers();
		cuentas = cuentaservice.getAllCuentas();
		dFecha.setValue(LocalDate.now());
	    dFecha.setReadOnly(true);
		
		//Formulario para añadir cuentas
		Div div = new Div();
		VerticalLayout vl = new VerticalLayout();
		form.add(ibanf, dFecha, saveButton);
		saveButton.addClickListener(event -> {
			saveCuenta(userservice);
			Notification notification = Notification.show("Cuenta creada con exito!");
			notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			ibanf.clear();
			UI.getCurrent().getPage().reload();
		});
		vl.add(form);
		vl.setPadding(true);
		div.add(vl);
		add(div);
		
		//Editar o eliminar cuentas.
		constructUI();
		for (Cuenta cuenta : cuentas) {
			imageContainer.add(new CuentasViewCardEdit
					(
							cuenta.getIban(),
							cuenta.getUser().getName(),
							cuenta.getId(),
							cuentaservice,
							userservice,
							tarjetaservice
							)); 
		}
		
	}
	
	private void saveCuenta(UserService userservice) {
		User userNuevo = userservice.getById((long) 1);
		/*
		if(userIdf.getValue() == null) {
			//1. Se añade cuenta al user con id(1), que existe. -->
			//2. Se crea la cuenta para un user que no existe.
		}
		else
		*/
		Cuenta cuenta = new Cuenta(ibanf.getValue(),saldof, dFecha.getValue(),userNuevo);
		cuentaservice.update(cuenta);
		form.setVisible(true);
	}
	
	 private void constructUI() {
	        addClassNames("Cuentas");
	        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

	        HorizontalLayout container = new HorizontalLayout();
	        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

	        VerticalLayout headerContainer = new VerticalLayout();
	        H2 header = new H2("Cuentas");
	        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
	        headerContainer.add(header);

	        imageContainer = new OrderedList();
	        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

	        container.add(headerContainer);
	        add(container, imageContainer);
	    }
}

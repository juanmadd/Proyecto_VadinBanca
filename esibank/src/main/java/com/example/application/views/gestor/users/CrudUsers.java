package com.example.application.views.gestor.users;
import com.example.application.views.MainLayout;

import com.example.application.data.entity.Noticia;
import com.example.application.data.entity.User;
import com.example.application.data.service.NoticiaService;
import com.example.application.data.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import com.example.application.views.noticias.NoticiasViewCard;
import com.example.application.views.gestor.noticias.NoticiasViewCardEdit;
import java.util.Arrays;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("serial")
@RolesAllowed("ADMIN")
@PageTitle("Modificar Usuarios")
@Route(value = "crud-users", layout = MainLayout.class)
public class CrudUsers extends Div{

	private UserService userService;
	private PasswordEncoder passwordEncoder ;
	private FormLayout form = new FormLayout();
	private final TextField username = new TextField("Username");
	private final TextField name = new TextField("Name");
	private final PasswordField password = new PasswordField("Password");
	private final PasswordField confirmPassword = new PasswordField("Confirm Password");
	private User user;

	public CrudUsers(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		Button submit = new Button("Registrar nuevo usuario");
		Div div = new Div();
		VerticalLayout vl = new VerticalLayout();
		form.add(username, name, password, confirmPassword, submit);
		submit.addClickListener(e -> {
			if (!password.getValue().equals(confirmPassword.getValue())) {
				Notification notification = Notification.show("Las contraseñas no coinciden!");
				notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
				return;
			}
			User user = new User();
			user.setUsername(username.getValue());
			user.setName(name.getValue());
			user.setHashedPassword(passwordEncoder.encode(password.getValue()));
			userService.update(user);
			Notification notification2 = Notification.show("Usuario registrado con exito!");
			notification2.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		});
		
		vl.setPadding(true);
		div.add(form);
		add(div);
		constructUI();
	}
	
	 private void constructUI() {
	        addClassNames("Lista de usuarios");
	        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

	        HorizontalLayout container = new HorizontalLayout();
	        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

	        VerticalLayout headerContainer = new VerticalLayout();
	        H2 header = new H2("Lista de usuarios");
	        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
	        headerContainer.add(header);
	        container.add(headerContainer);
	        add(container);

	    }
}

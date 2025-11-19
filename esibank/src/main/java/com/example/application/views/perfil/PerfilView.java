package com.example.application.views.perfil;
import com.example.application.data.entity.User;
import com.example.application.data.service.UserService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.crud.Crud.SaveEvent;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.shared.Registration;
import net.bytebuddy.asm.Advice.This;
import java.util.Optional;
import javax.annotation.security.PermitAll;
@PageTitle("Perfil")
@Route(value = "Perfil", layout = MainLayout.class)
@PermitAll
public class PerfilView extends VerticalLayout {
	
	private static final long serialVersionUID = 1L;
	private AuthenticatedUser authenticatedUser;
	private AccessAnnotationChecker accessChecker;
	private UserService userservice;
	
	TextField username = new TextField("Nombre de usuario");
    //PasswordField hashedPassword = new PasswordField("Password");
    //PasswordField confirmPassword = new PasswordField("Confirm password");
    Binder<User> binder = new Binder<>(User.class);
    
    private void validateAndSave() {
    	if ( binder.isValid()) {
    		//fireEvent(new SaveEvent(this, binder.getBean()));
    	}
    }
	
    public PerfilView(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, UserService userservice) {
    	this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        this.userservice = userservice;
    	Optional<User> maybeUser = authenticatedUser.get();
        User userActual = maybeUser.get();
        setSpacing(false);
        
        binder.forField(username)
        // Validator defined based on a lambda
        // and an error message
        .withValidator(
        		username -> username.length() >= 3,
            "El nombre debe tener al menos 3 caracteres de longitud")
        .bind(User::getName, User::setName);
        
        binder.setBean(userActual);
        Button saveButton = new Button("Guardar", event -> {
            if (binder.validate().isOk()) {
                // person is always up-to-date as long as
                // there are no validation errors
            	userservice.update(userActual);
            	Notification notification = Notification.show("Username modificado con éxito");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                UI.getCurrent().getPage().reload();
            }
            else {
            	Notification notification = Notification.show("Error, no se ha podido modificar el username");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        

        FormLayout formLayout = new FormLayout();
        formLayout.add(username);
        formLayout.setResponsiveSteps(
                // Use one column by default
                new ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new ResponsiveStep("500px", 2));
        // Stretch the username field over 2 columns
        formLayout.setColspan(username, 2);
        add(formLayout);
 
        Avatar user = new Avatar();
        user.setImage(userActual.getProfilePicture().toString()); 
        user.setWidth("200px");
        user.setHeight("200px");
        add(user);
        
        Span name = new Span(userActual.getName());
        Span email = new Span(userActual.getUsername() + "@gmail.com");
        VerticalLayout content = new VerticalLayout(name, email);
        content.setSpacing(false);
        content.setPadding(false);
        
        VerticalLayout formulario = new VerticalLayout( username, saveButton);
        content.setSpacing(false);
        content.setPadding(false);
        Details details = new Details("Información personal", content);
        details.setOpened(true);
        add(details);
        
        Details modificar_datos = new Details("Modificar datos", formulario);
        modificar_datos.setOpened(false);
        add(modificar_datos);
        
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
	private void notifyValidationException(ValidationException e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
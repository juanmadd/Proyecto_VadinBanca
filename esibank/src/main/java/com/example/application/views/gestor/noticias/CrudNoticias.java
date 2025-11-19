
package com.example.application.views.gestor.noticias;
import com.example.application.views.MainLayout;

import com.example.application.data.entity.Noticia;
import com.example.application.data.service.NoticiaService;
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

@RolesAllowed("ADMIN")
@PageTitle("CrudNoticias")
@Route(value = "crud-noticias", layout = MainLayout.class)

public class CrudNoticias extends Div {

	private Noticia noticia = new Noticia();
	private FormLayout form = new FormLayout();
	private TextField tittleField = new TextField("Título");
	private TextField paragraphField = new TextField("Párrafo");
	private TextField textField = new TextField("Texto");
	private TextField urlField = new TextField("Url");
	private Button saveButton = new Button("Añadir nueva noticia");
	
	private NoticiaService noticiaService;
	private OrderedList imageContainer = new OrderedList();
	List<Noticia> noticias ;

	public CrudNoticias(NoticiaService noticiaService) {
		
		this.noticiaService = noticiaService;
		Div div = new Div();
		VerticalLayout vl = new VerticalLayout();
		form.add(tittleField, paragraphField, textField, urlField, saveButton);
		saveButton.addClickListener(event -> {
			saveNoticia();
			Notification notification = Notification.show("Noticia creada con exito!");
			notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			tittleField.clear();
			paragraphField.clear();
			textField.clear();
			urlField.clear();
			UI.getCurrent().getPage().reload();
		});
		vl.add(form);
		vl.setPadding(true);
		div.add(vl);
		add(div);
		constructUI();
		noticias = noticiaService.getAllNoticias();
		for (Noticia noticia : noticias) {
			imageContainer.add(new NoticiasViewCardEdit
					(
							noticia.getTittle(),
							noticia.getParagraph(),
							noticia.getText(),
							noticia.getUrl(),
							noticia.getId(),
							noticiaService
							)); 
		}
		
	}

	private void saveNoticia() {
		noticia = new Noticia(tittleField.getValue(),paragraphField.getValue(),textField.getValue(),urlField.getValue());
		noticiaService.update(noticia);
		form.setVisible(true);
	}
	
	 private void constructUI() {
	        addClassNames("noticias");
	        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

	        HorizontalLayout container = new HorizontalLayout();
	        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

	        VerticalLayout headerContainer = new VerticalLayout();
	        H2 header = new H2("Noticias");
	        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
	        headerContainer.add(header);

	        imageContainer = new OrderedList();
	        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

	        container.add(headerContainer);
	        add(container, imageContainer);

	    }
}

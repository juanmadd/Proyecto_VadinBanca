package com.example.application.views.gestor.noticias;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.example.application.data.entity.Noticia;
import com.example.application.data.service.NoticiaService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
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
import com.vaadin.flow.component.textfield.TextField;
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

@RolesAllowed("ADMIN")
@SuppressWarnings("serial")
public class NoticiasViewCardEdit extends ListItem {
	private NoticiaService noticiaService;
	List<Noticia> noticias ;
	private Button deleteButton = new Button("Eliminar");
	private Button editButton = new Button("Editar");

    public NoticiasViewCardEdit(String title, String paragraph, String text, String url, Long noticiaId,NoticiaService noticiaService) {
    	this.noticiaService = noticiaService;
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(url);
        image.setAlt(text);

        div.add(image);

        
        //Delete dialog
        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(title);
        Paragraph description = new Paragraph(paragraph);
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        
        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Borrar noticia:" + title);
        dialog.setText(
                "Estas seguro de que quieres borrar esta noticia?");

        dialog.setCancelable(true);
        dialog.addCancelListener(event -> dialog.close());

        dialog.setConfirmText("Delete");
        dialog.addConfirmListener(event -> {
        	noticiaService.delete(noticiaId);
        	Notification notification = Notification.show("Noticia borrada!");
        	UI.getCurrent().getPage().reload();
        	notification.addThemeVariants(NotificationVariant.LUMO_ERROR);	
        } );

        deleteButton.addClickListener(event -> {
        	dialog.open();
		});
        
        //EDIT dialog
        Dialog dialogEdit = new Dialog();

        dialogEdit.setHeaderTitle("Estas editando la noticia: " + title);
        
        TextField tittlef = new TextField("Titulo");
        tittlef.setPlaceholder(title);
        TextField paragraphf = new TextField("Parrafo");
        paragraphf.setPlaceholder(paragraph);
        TextField textf = new TextField("Texto");
        textf.setPlaceholder(text);
        TextField urlf = new TextField("url");
        urlf.setPlaceholder(url);
        
        
        VerticalLayout dialogEditLayout =  new VerticalLayout(tittlef,paragraphf,textf,urlf);
        dialogEdit.add(dialogEditLayout);
        dialogEditLayout.setPadding(false);
        dialogEditLayout.setSpacing(false);
        dialogEditLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogEditLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        
        
        Button saveButton = new Button("Guardar", e ->{ 
        	noticiaService.delete(noticiaId);
        	if(tittlef.getValue() == "") {
        		tittlef.setValue(title);
        	}
        	if(paragraphf.getValue() == "") {
        		paragraphf.setValue(paragraph);
        	}
        	if(textf.getValue() == "") {
        		textf.setValue(text);
        	}
        	if(urlf.getValue() == "") {
        		urlf.setValue(url);
        	}
        	Noticia noticiaEditable = new Noticia(tittlef.getValue(),paragraphf.getValue(),textf.getValue(),urlf.getValue());
        	noticiaEditable.setId(noticiaId);
    		noticiaService.update(noticiaEditable);
    		Notification notification = Notification.show("Noticia modificada correctamente.");
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

        add(div, header, description, deleteButton, editButton);
    }
}

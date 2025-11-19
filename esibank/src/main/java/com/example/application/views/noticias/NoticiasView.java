package com.example.application.views.noticias;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.application.data.entity.Noticia;
import com.example.application.data.service.NoticiaRepository;
import com.example.application.data.service.NoticiaService;

@PageTitle("Noticias")
@Route(value = "Noticias", layout = MainLayout.class)
@AnonymousAllowed
public class NoticiasView extends Main implements HasComponents, HasStyle {
	
	private OrderedList imageContainer = new OrderedList();
	private NoticiaService noticiaService; 
	List<Noticia> noticias ;
	public List<Noticia> getAllNoticias() {
	      return noticiaService.getAllNoticias();
	 }

    public NoticiasView(NoticiaService noticiaService, List<Noticia> noticias ) {
        constructUI();
        this.noticiaService = noticiaService;
        noticias = noticiaService.getAllNoticias();
        for (Noticia noticia : noticias) {
        	 imageContainer.add(new NoticiasViewCard
        			 (
        					 noticia.getTittle(),
        					 noticia.getParagraph(),
        					 noticia.getText(),
        					 noticia.getUrl())
        			 ); 
        }
    }
      
    private void constructUI() {
        addClassNames("noticias-view");
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

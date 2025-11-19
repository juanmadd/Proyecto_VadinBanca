package com.example.application.views.cuenta;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Tarjeta;
import com.example.application.data.service.TarjetaService;
import com.example.application.views.cuenta.CuentaView.Filters;
import com.example.application.views.cuenta.CuentaView.Filters2;
import com.example.application.views.cuenta.CuentaView.TarjetasView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

public class TarjetasViewCard extends ListItem {
	private TarjetaService tarjetaservice;

    public TarjetasViewCard(String emisor, int cvv, Date fecha_caducidad, String numero, boolean activa, Float limite_maximo, Float limite_minimo, String imagen, TarjetaService tarjetaservice) {
        this.tarjetaservice = tarjetaservice;
        		
    	addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");
        
        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(imagen);

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(emisor);
        
        Span num_tarjeta = new Span();
        num_tarjeta.addClassNames(FontSize.LARGE, FontWeight.BLACK);
        num_tarjeta.setText("Nº: " + numero);
        
        Span cvv_tarjeta = new Span();
        cvv_tarjeta.addClassNames(FontSize.LARGE, FontWeight.BLACK);
        cvv_tarjeta.setText("cvv: " + String.valueOf(cvv));
        
        //Convertir fecha a texto en formato M/A
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        String fechaCadena = sdf.format(fecha_caducidad);
        
        Span fecha_tarjeta = new Span();
        fecha_tarjeta.addClassNames(FontSize.LARGE, FontWeight.BLACK);
        fecha_tarjeta.setText("Fecha Caducidad: " + fechaCadena);
        
        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        
        add(div, header, num_tarjeta, cvv_tarjeta, fecha_tarjeta);
        
        if(activa == true) {
        	Span estado = new Span("Activa");
        	estado.getElement().getThemeList().add("badge success primary");
        	add(estado);
        }
        else {
        	Span estado = new Span("Desactivada");
        	estado.getElement().getThemeList().add("badge error primary");
        	add(estado);
        }
                
        if(activa == true) {
        	Button desactivacion = new Button("Desactivar tarjeta");
        	add(desactivacion);
        	
        	desactivacion.addClickListener(e -> {
        		Tarjeta t = new Tarjeta();
        		t = tarjetaservice.getByNumber(numero);
        		t.setActiva(false);
        		tarjetaservice.update(t);
                UI.getCurrent().getPage().reload();
            });
        }
        else {
        	Button activacion = new Button("Activar tarjeta");
        	add(activacion);
        	activacion.addClickListener(e -> {
        		Tarjeta t = new Tarjeta();
        		t = tarjetaservice.getByNumber(numero);
        		t.setActiva(true);
        		tarjetaservice.update(t);
                UI.getCurrent().getPage().reload();
            });
        }
        
          
    }
}

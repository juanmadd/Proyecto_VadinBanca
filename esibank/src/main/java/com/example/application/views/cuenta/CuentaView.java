package com.example.application.views.cuenta;

import com.example.application.data.entity.SamplePerson;
import com.example.application.data.entity.Tarjeta;
import com.example.application.data.entity.User;
import com.example.application.data.service.SamplePersonService;
import com.example.application.data.service.TarjetaService;
import com.example.application.data.service.UserService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Movimiento;
import com.example.application.data.entity.Noticia;
import com.example.application.data.entity.RecibosDomiciliados;
import com.example.application.data.service.CuentaService;
import com.example.application.data.service.MovimientoService;
import com.example.application.data.service.NoticiaService;
import com.example.application.data.service.ReciboDomiciliadoService;
import com.example.application.views.MainLayout;
import com.example.application.views.cuenta.CuentaView.Filters2;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.example.application.views.noticias.*;

@SuppressWarnings("serial")
@PageTitle("Cuenta")
@Route(value = "Cuenta", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class CuentaView extends Div {
    private Grid<Movimiento> grid;
    private Grid<RecibosDomiciliados> grid2;
    private Filters filters;
    private Filters2 filters2;
    private final MovimientoService movimientoService;
    private OrderedList imageContainer;
    private AuthenticatedUser authenticatedUser;
	private AccessAnnotationChecker accessChecker;
	private UserService userservice;
	private static CuentaService cuentaservice;
	private ReciboDomiciliadoService reciboservice;
	private TarjetaService tarjetaservice;

    public CuentaView(MovimientoService movimientoService, AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, UserService userservice, CuentaService cuentaservice, ReciboDomiciliadoService reciboservice, TarjetaService tarjetaservice) {
    	this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        this.userservice = userservice;
        this.movimientoService = movimientoService;
        this.cuentaservice = cuentaservice;
        this.reciboservice = reciboservice;
        this.tarjetaservice = tarjetaservice;
        setSizeFull();
        addClassNames("cuenta-view");
                                            
        H2 header = new H2("Movimientos");
        H2 header2 = new H2("Recibos");
        
        header.setClassName(Padding.Horizontal.LARGE);
        header2.setClassName(Padding.Horizontal.LARGE);
         
        //Usuario autenticado
    	Optional<User> maybeUser = authenticatedUser.get();
        User userActual = maybeUser.get();
                        
        List<String> items = cuentaservice.getByUser(userActual);   //Almacenamos en una lista de String los iban de las cuentas que están relacionados
        															//Con el usuario autenticado
        //Formulario para seleccionar cuenta: Select para seleccionar una cuenta del usuario que se ha autenticado y un botón
        
        Binder<Cuenta> binder = new Binder<>(Cuenta.class);

        FormLayout form = new FormLayout();
        
        Select<String> iban = new Select<>();
        iban.setLabel("Elija una cuenta");
        iban.setItems(items);
        
        form.add(iban);
        binder.bind(iban, "iban");
        Button submitForm = new Button("Seleccionar cuenta");
        form.add(submitForm);
        
        binder.forField(iban)
        .withValidator(value -> value != null, "Seleccione una cuenta")
        .bind(Cuenta::getIban, Cuenta::setIban);
        
        VerticalLayout layoutForm = new VerticalLayout(form);
        add(layoutForm);
        
            
        //Mostramos solo cuando se seleccione una cuenta
        
        submitForm.addClickListener(e -> {
        	if (binder.validate().isOk()) {
	            String ibanCuenta = iban.getValue();
	            Cuenta cu = new Cuenta();
	            cu = cuentaservice.getByIban(ibanCuenta);
	            Span saldo = new Span("Saldo de cuenta: " + cu.getSaldo() + " €");
	            
	            saldo.setClassName(Padding.Horizontal.LARGE);
	        
	            filters = new Filters(() -> refreshGrid(), ibanCuenta);
	            filters2 = new Filters2(() -> refreshGrid2(), ibanCuenta);
	            
	            TarjetasView tv = new TarjetasView(tarjetaservice,cu);
	            
	            HorizontalLayout container = new HorizontalLayout();
	            container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
	            container.add(header);
	            
				VerticalLayout layout = new VerticalLayout(saldo, header, createMobileFilters(ibanCuenta), filters, createGrid(), header2, createMobileFilters2(), filters2, createGrid2(), tv);
				//layout.setSizeFull();
				layout.setPadding(false);
				layout.setSpacing(false);
				add(layout);
        	}
        });  
    }
    
    //Tarjetas
    /*private HorizontalLayout Algo(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, UserService userservice) {
    	
    	Optional<User> maybeUser = authenticatedUser.get();
        User userActual = maybeUser.get();
        
    	HorizontalLayout algo = new HorizontalLayout();
    	 Accordion accordion = new Accordion();
         Span name = new Span(userActual.getName());
         Span email = new Span(userActual.getName()+"@company.com");
         VerticalLayout personalInformationLayout = new VerticalLayout(name,
                 email);
         personalInformationLayout.setSpacing(false);
         personalInformationLayout.setPadding(false);
         /*accordion.add("Informacion Personal", personalInformationLayout);
         Span street = new Span("4027 Amber Lake Canyon");
         Span zipCode = new Span("72333-5884 Cozy Nook");
         Span city = new Span("Arkansas");
         VerticalLayout billingAddressLayout = new VerticalLayout();
         billingAddressLayout.setSpacing(false);
         billingAddressLayout.setPadding(false);
         billingAddressLayout.add(street, zipCode, city);
         accordion.add("Direccion", billingAddressLayout);*/
        /* Span cardBrand = new Span("Mastercard");
         Span cardNumber = new Span("1234 5678 9012 3456");
         Span expiryDate = new Span("Expires 06/21");
         VerticalLayout paymentLayout = new VerticalLayout();
         paymentLayout.setSpacing(true);
         paymentLayout.setPadding(true);
         paymentLayout.add(cardBrand, cardNumber, expiryDate);
         accordion.add("Tarjeta", paymentLayout);
         algo.add(accordion);
         algo.setSpacing(true);
         algo.setPadding(true);
    	return algo;
    }*/
    
    
    public class TarjetasView extends Main implements HasComponents, HasStyle {
    	
    	private OrderedList imageContainer = new OrderedList();
    	private TarjetaService tarjetaservice; 
    	List<Tarjeta> tarjetas;
    	/*public List<Tarjeta> getAllNoticias() {
    	      return noticiaService.getAllNoticias();
    	 }*/

        public TarjetasView(TarjetaService tarjetaservice, Cuenta cuenta) {
            constructUI();
            this.tarjetaservice = tarjetaservice;
            tarjetas = tarjetaservice.getTarjetasByCuenta(cuenta);
            for (Tarjeta t : tarjetas) {
            	 imageContainer.add(new TarjetasViewCard
            			 (
            					 t.getEmisor(),
            					(int) t.getCvv(),
            					 t.getFecha_caducidad(),
            					 t.getNumero(),
            					 t.getActiva(),
            					 t.getLimite_maximo(),
            					 t.getLimite_minimo(),
            					 t.getImagenURL(),
            			 		 tarjetaservice)
            			 ); 
            }
        }
          
        private void constructUI() {
            addClassNames("tarjetas-view");
            
            //Para cambiar disposición de título e imagen, hay que poner Margin.Horizontal.AUTO
            addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.NONE, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

            HorizontalLayout container = new HorizontalLayout();
            container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
                                                	
            VerticalLayout headerContainer = new VerticalLayout();
            H2 header = new H2("Tarjetas");
            header.addClassNames(Margin.Bottom.NONE, Margin.Top.NONE, FontSize.XXLARGE);
            headerContainer.add(header);

            imageContainer = new OrderedList();
            imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

            container.add(headerContainer);
            add(container, imageContainer);

        }
    }
    
    //Horizont Layout de Movimientos

    private HorizontalLayout createMobileFilters(String iban) {
        // Mobile version
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
                LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");
        Icon mobileIcon = new Icon("lumo", "plus");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        mobileFilters.addClickListener(e -> {
            if (filters.getClassNames().contains("visible")) {
                filters.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }
    
  //Horizont Layout de Recibos
    
    private HorizontalLayout createMobileFilters2() {
        // Mobile version
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
                LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");
        Icon mobileIcon = new Icon("lumo", "plus");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        mobileFilters.addClickListener(e -> {
            if (filters2.getClassNames().contains("visible")) {
                filters2.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters2.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }
    
    //Filtros para Movimientos
    public static class Filters extends Div implements Specification<Movimiento> {

        //private final TextField concepto = new TextField("concepto");
        //private final DatePicker startDate = new DatePicker("Fecha de realizacion");
        private final String cuentaOrigen;
        private final String cuentaDestino;
        //private final DatePicker endDate = new DatePicker();

        public Filters(Runnable onSearch, String ibanCuenta) {
        	this.cuentaOrigen = ibanCuenta;
        	this.cuentaDestino = ibanCuenta;
        	
            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
           // name.setPlaceholder("First or last name");

            //occupations.setItems("Insurance Clerk", "Mortarman", "Beer Coil Cleaner", "Scale Attendant");

           // roles.setItems("Worker", "Supervisor", "Manager", "External");
           // roles.addClassName("double-width");

            // Action buttons
            Button resetBtn = new Button("Reset");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
            	//concepto.clear();
            	//fValor.clear();
                //startDate.clear();
                //endDate.clear();
                onSearch.run();
            });
            //Button searchBtn = new Button("Search");
            //searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
           // searchBtn.addClickListener(e -> onSearch.run());
            //Div actions = new Div(resetBtn, searchBtn);
            //actions.addClassName(LumoUtility.Gap.SMALL);
           // actions.addClassName("actions");

            //add(concepto, createDateRangeFilter(), actions);
        }

        /*private Component createDateRangeFilter() {
            startDate.setPlaceholder("Desde");
            endDate.setPlaceholder("Hasta");

            // For screen readers
            setAriaLabel(startDate, "Desde");
            setAriaLabel(endDate, "Hasta");

            FlexLayout dateRangeComponent = new FlexLayout(startDate, new Text(" – "), endDate);
            dateRangeComponent.setAlignItems(FlexComponent.Alignment.BASELINE);
            dateRangeComponent.addClassName(LumoUtility.Gap.XSMALL);
            return dateRangeComponent;
        }*/

        private void setAriaLabel(DatePicker datePicker, String label) {
            datePicker.getElement().executeJs("const input = this.inputElement;" //
                    + "input.setAttribute('aria-label', $0);" //
                    + "input.removeAttribute('aria-labelledby');", label);
        }

        @Override
        public Predicate toPredicate(Root<Movimiento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();
            List<Predicate> cuentaUser = new ArrayList<>();
            Predicate cuentaUserPred = null;
            Predicate cuentaUserPred2 = null;
            
            String lowerCaseFilter = cuentaOrigen.toLowerCase();
            Predicate cuentaOrigenrigenMATCH = criteriaBuilder.like(criteriaBuilder.lower(root.get("cuentaOrigen")),lowerCaseFilter + "%");
            cuentaUser.add(criteriaBuilder.or(cuentaOrigenrigenMATCH));
            cuentaUserPred = criteriaBuilder.equal(root.get("cuentaOrigen"),lowerCaseFilter);
            
            String lowerCaseFilter2 = cuentaDestino.toLowerCase();
            Predicate cuentaDestinoestinoMATCH = criteriaBuilder.like(criteriaBuilder.lower(root.get("cuentaDestino")), lowerCaseFilter2 + "%");
            cuentaUser.add(criteriaBuilder.or(cuentaDestinoestinoMATCH));
            List<Predicate> cuentaUser3 = new ArrayList<>();
            cuentaUserPred2 = criteriaBuilder.equal(root.get("cuentaDestino"),lowerCaseFilter2);
            
            Predicate cuentaUserPred3 = criteriaBuilder.or(cuentaUserPred,cuentaUserPred2);
            
            /*if (!concepto.isEmpty()) {
                String lowerCaseFilter3 = concepto.getValue().toLowerCase();
                Predicate conceptoMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("concepto")),
                        lowerCaseFilter3 + "%");

                predicates.add(criteriaBuilder.or(conceptoMatch));
            }

            if (startDate.getValue() != null) {
                String databaseColumn = "dFecha";
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(databaseColumn),
                        criteriaBuilder.literal(startDate.getValue())));
            }
            if (endDate.getValue() != null) {
                String databaseColumn = "dFecha";
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.literal(endDate.getValue()),
                        root.get(databaseColumn)));
            }*/
            
            cuentaUser3.add(cuentaUserPred3);
            List<Predicate> combinedPredicates = new ArrayList<>(predicates);
            combinedPredicates.addAll(cuentaUser);
            
            
            return criteriaBuilder.and(cuentaUser3.toArray(Predicate[]::new));
        }

        private String ignoreCharacters(String characters, String in) {
            String result = in;
            for (int i = 0; i < characters.length(); i++) {
                result = result.replace("" + characters.charAt(i), "");
            }
            return result;
        }
        private Expression<String> ignoreCharacters(String characters, CriteriaBuilder criteriaBuilder,
                Expression<String> inExpression) {
            Expression<String> expression = inExpression;
            for (int i = 0; i < characters.length(); i++) {
                expression = criteriaBuilder.function("replace", String.class, expression,
                        criteriaBuilder.literal(characters.charAt(i)), criteriaBuilder.literal(""));
            }
            return expression;
        }
    }
    
    //Filtros para Recibos
    
    public static class Filters2 extends Div implements Specification<RecibosDomiciliados> {

    	private final String cuenta;
        
        public Filters2(Runnable onSearch, String ibanCuenta) {
        	this.cuenta = ibanCuenta;
        	
            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
           // name.setPlaceholder("First or last name");

            //occupations.setItems("Insurance Clerk", "Mortarman", "Beer Coil Cleaner", "Scale Attendant");

           // roles.setItems("Worker", "Supervisor", "Manager", "External");
           // roles.addClassName("double-width");

            // Action buttons
            Button resetBtn = new Button("Reset");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
            	//cuenta.clear();
                onSearch.run();
            });
            Button searchBtn = new Button("Search");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());
            Div actions = new Div(resetBtn, searchBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions"); //no se añade los botones

        }

		@Override
		public Predicate toPredicate(Root<RecibosDomiciliados> root, CriteriaQuery<?> query,
				CriteriaBuilder criteriaBuilder) {
			            
            Cuenta cu = cuentaservice.getByIban(cuenta);
            Long cuenta_id = cu.getId();
                        
            List<Predicate> predicates = new ArrayList<>();
            String databaseColumn = "cuenta_id";
            predicates.add(criteriaBuilder.equal(root.get(databaseColumn), criteriaBuilder.literal(cuenta_id)));
            
			return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
		}
     }
        
    //Grid de movimientos

    private Component createGrid() {
        grid = new Grid<>(Movimiento.class, false);
        grid.addColumn("concepto").setAutoWidth(true);
        grid.addColumn("fValor").setAutoWidth(true);
        grid.addColumn("dFecha").setAutoWidth(true);
        grid.addColumn("cuentaOrigen").setAutoWidth(true);
        grid.addColumn("cuentaDestino").setAutoWidth(true);

        grid.setItems(query -> movimientoService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);
        
       /*grid.setItems(query -> movimientoService.listMovCuent(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters, iban).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);*/
        return grid;
    }
    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }
    
    //Grid de recibos domiciliados
    private Component createGrid2() {
        grid2 = new Grid<>(RecibosDomiciliados.class, false);
        grid2.addColumn("concepto").setAutoWidth(true);
        grid2.addColumn("valor").setAutoWidth(true);
        grid2.addColumn("fecha").setAutoWidth(true);
       // grid2.addColumn("nombreEmisor").setAutoWidth(true);
        grid2.addColumn("ibanEmisor").setAutoWidth(true);
        

        grid2.setItems(query -> reciboservice.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters2).stream());
        grid2.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid2.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);
        
        return grid2;
    }
    private void refreshGrid2() {
        grid2.getDataProvider().refreshAll();
    }
}
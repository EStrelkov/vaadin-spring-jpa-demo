package com.gp.vaadin.vaadin_spring_jpa_demo.ui;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
@Theme("mytheme")
public class MainUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5797851854545091816L;
	
	@WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }
    
    @Autowired
    private SpringViewProvider viewProvider;
    
	@Override
    protected void init(VaadinRequest request) {
		
		
		VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);
        
        Panel viewDisplay = new Panel();
        viewDisplay.setSizeFull();
		
		Navigator navigator = new Navigator(this,viewDisplay);
        navigator.addProvider(viewProvider);
        this.setNavigator(navigator);

        NavigableMenuBar menu = new NavigableMenuBar(navigator);
        menu.addStyleName("mybarmenu");
        layout.addComponent(menu);
        
        viewDisplay.setSizeFull();
        layout.addComponent(viewDisplay);
        layout.setExpandRatio(viewDisplay, 1.0f);

        navigator.addViewChangeListener(menu);
        
        menu.addView(HotelView.VIEW_NAME, "Hotel", VaadinIcons.BUILDING);
        menu.addView(HotelCategoryView.VIEW_NAME, "Category", VaadinIcons.ACADEMY_CAP);
        menu.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
        
        navigator.navigateTo(HotelView.VIEW_NAME);
        
    }
	
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    public static class MyUIServlet extends SpringVaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8532242783577934767L;
    }
	
}

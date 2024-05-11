package com.example.demo.view;

import com.example.demo.entity.Land;
import com.example.demo.prices.MediaPrice;
import com.example.demo.prices.SizePrice;
import com.example.demo.prices.TypePrice;
import com.example.demo.service.LandService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Flex;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import java.util.ArrayList;
import java.util.Arrays;

@PageTitle("Checkout Form")
@Route("")
public class CheckoutFormView extends Div {

    public CheckoutFormView() {
        addClassNames("checkout-form-view");
        addClassNames(Display.FLEX, FlexDirection.COLUMN, Height.FULL);

        Main content = new Main();
        content.addClassNames(Display.GRID, Gap.XLARGE, AlignItems.START, JustifyContent.CENTER, MaxWidth.SCREEN_MEDIUM,
                Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        content.add(createCheckoutForm());
        add(content);
    }

    private Component createCheckoutForm() {
        Section checkoutForm = new Section();
        checkoutForm.addClassNames(Display.FLEX, FlexDirection.COLUMN, Flex.GROW);

        H2 header = new H2("Wycena działki");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph note = new Paragraph("podaj szczegóły i wyceń swoją działke!");
        note.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        checkoutForm.add(header, note);

        checkoutForm.add(createPersonalDetailsSection());

        return checkoutForm;
    }

    private Section createPersonalDetailsSection() {
        Section personalDetails = new Section();
        personalDetails.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);


        H3 header = new H3("Sczegóły działki");
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);


        ComboBox<String> comboBox = new ComboBox<>("Miasto");
        comboBox.setItems(Arrays.asList("Warszawa", "Białystok", "Poznań", "Wrocław"));

        Land land = new Land();
        Paragraph paragraph = new Paragraph();

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Media");
        checkboxGroup.setItems("Prąd", "Gaz", "Woda", "Kanalizacja");
        checkboxGroup.addValueChangeListener(e -> {
            LandService landService = new LandService();
            land.setMedia(new ArrayList<>(e.getValue()));
            MediaPrice mediaPrice = new MediaPrice();
            landService.suggestMediaPrice(land, mediaPrice);
        });



        H2 price = new H2("Cena:");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(price);
        horizontalLayout.add(paragraph);
        horizontalLayout.getStyle().set("padding-top", "30px");

        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Rodzaj");
        radioGroup.setItems("Rolna", "Budowlana", "Lesna", "Rekreacyjna", "Inwestycyjna", "Siedliskowa");
        radioGroup.setValue("Budowlana");
        radioGroup.addValueChangeListener(e -> {
            LandService landService = new LandService();
            land.setType(e.getValue());
            TypePrice typePrice = new TypePrice();
            paragraph.setText(landService.suggestTypePrice(land, typePrice).getPrice().toString());
        });

        RadioButtonGroup<String> shapeRadioGroup = new RadioButtonGroup<>();
        shapeRadioGroup.setLabel("Kształt działki");
        shapeRadioGroup.setItems("Regularny", "Nieregularny");
        shapeRadioGroup.setValue("Budowlana");
        shapeRadioGroup.addValueChangeListener(e -> {
//            LandService landService = new LandService();
//            land.setType(e.getValue());
//            TypePrice typePrice = new TypePrice();
//            paragraph.setText(landService.suggestTypePrice(land, typePrice).getPrice().toString());
        });

        IntegerField integerField = new IntegerField();
        integerField.setValue(1);
        integerField.setStepButtonsVisible(true);
        integerField.setMin(1);
        integerField.setLabel("Wielkość w m2");
        integerField.addValueChangeListener(e -> {
            LandService landService = new LandService();
            land.setSize(e.getValue().longValue());
            SizePrice sizePrice = new SizePrice();
            paragraph.setText(landService.suggestSizePrice(land, sizePrice).getPrice().toString());
        });

        CheckboxGroup<String> surroundingsCheckboxGroup = new CheckboxGroup<>();
        surroundingsCheckboxGroup.setLabel("Infrastruktura drogowa");
        surroundingsCheckboxGroup.setItems("Droga ekspresowa w okolicy", "Autostrada w okolicy", "Dojazd drogą asfaltowa");
        surroundingsCheckboxGroup.addValueChangeListener(e ->{
//            LandService landService = new LandService();
//            land.setMedia(new ArrayList<>(e.getValue()));
//            MediaPrice mediaPrice = new MediaPrice();
//            landService.suggestMediaPrice(land, mediaPrice);
        });

        personalDetails.add(header, comboBox, checkboxGroup, radioGroup, integerField, surroundingsCheckboxGroup, shapeRadioGroup, horizontalLayout);
        return personalDetails;
    }

}
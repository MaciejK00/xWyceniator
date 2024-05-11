package com.example.demo.view;

import com.example.demo.entity.Land;
import com.example.demo.prices.*;
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

import static com.example.demo.common.Dictionary.*;
import static com.example.demo.common.MediaEnum.*;
import static com.example.demo.common.CityEnum.*;
import static com.example.demo.common.SurroundingsEnum.*;
import static com.example.demo.common.ShapeEnum.*;
import static com.example.demo.common.TypeEnum.*;

@PageTitle("Wyceniator")
@Route("")
public class CheckoutFormView extends Div {
    private Land land;
    private Paragraph paragraph;

    public CheckoutFormView() {
        this.addClassNames(Display.FLEX, FlexDirection.COLUMN, Height.FULL);

        final Main content = new Main();
        content.addClassNames(Display.GRID, Gap.XLARGE, AlignItems.START, JustifyContent.CENTER, MaxWidth.SCREEN_MEDIUM,
                Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        content.add(createMainForm());
        this.add(content);
    }

    private Component createMainForm() {
        final Section checkoutForm = new Section();
        checkoutForm.addClassNames(Display.FLEX, FlexDirection.COLUMN, Flex.GROW);

        final H2 header = new H2(LAND_PRICE);
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);

        final Paragraph note = new Paragraph(GIVE_DETAILS);
        note.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);

        checkoutForm.add(header, note);
        checkoutForm.add(createFormDetails());

        return checkoutForm;
    }

    private Section createFormDetails() {
        final Section formDetails = this.prepareFormDetails();
        final H3 header = this.prepareSiteHeader();

        this.land = this.prepareLand();
        this.paragraph = new Paragraph();

        final IntegerField sizeField = prepareSizeField();
        final ComboBox<String> cityBox = this.prepareCityBox();
        final CheckboxGroup<String> mediaCheckboxGroup = this.prepareMediaCheckboxGroup();
        final RadioButtonGroup<String> typeRadioGroup = this.prepareTypeRadioGroup();
        final CheckboxGroup<String> surroundingsCheckboxGroup = this.prepareShapeCheckboxGroup();
        final RadioButtonGroup<String> shapeRadioGroup = this.prepareShapeRadioGroup();

        final HorizontalLayout horizontalLayout = preparePriceHeader(paragraph);

        formDetails.add(header, sizeField, cityBox, mediaCheckboxGroup, typeRadioGroup, surroundingsCheckboxGroup, shapeRadioGroup, horizontalLayout);
        return formDetails;
    }

    private CheckboxGroup<String> prepareShapeCheckboxGroup() {
        final CheckboxGroup<String> surroundingsCheckboxGroup = new CheckboxGroup<>();
        surroundingsCheckboxGroup.setLabel(INFRASTRUCTURE);
        surroundingsCheckboxGroup.setItems(EXPRESS.getName(), HIGHWAY.getName(), TARMAC.getName());

        surroundingsCheckboxGroup.addValueChangeListener(e -> {
            final LandService landService = new LandService();
            land.setSurroundings(new ArrayList<>(e.getValue()));
            SurroundingsPrice surroundingsPrice = new SurroundingsPrice();
            land.setSurroundingsPrice(landService.suggestSurroundingsPrice(land, surroundingsPrice).getPrice());
            paragraph.setText(this.calculatePrice(land).toString());
        });

        return surroundingsCheckboxGroup;
    }

    private RadioButtonGroup<String> prepareShapeRadioGroup() {
        final RadioButtonGroup<String> shapeRadioGroup = new RadioButtonGroup<>();
        shapeRadioGroup.setLabel(LAND_SHAPE);
        shapeRadioGroup.setItems(REGULAR.getName(), IRREGULAR.getName());

        shapeRadioGroup.addValueChangeListener(e -> {
            final LandService landService = new LandService();
            land.setRegular(e.getValue().equals(REGULAR.getName()));
            ShapePrice shapePrice = new ShapePrice();
            land.setShapeMultiplier(landService.suggestShapePrice(land, shapePrice).getShapeMultiplier());
            paragraph.setText(this.calculatePrice(land).toString());
        });

        return shapeRadioGroup;
    }

    private RadioButtonGroup<String> prepareTypeRadioGroup() {
        final RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel(TYPE);
        radioGroup.setItems(AGRICULTURAL.getName(), BUILDING.getName(), FOREST.getName(), LEISURE.getName(),
                INVESTMENT.getName(), HABITAT.getName());

        radioGroup.addValueChangeListener(e -> {
            final LandService landService = new LandService();
            land.setType(e.getValue());
            TypePrice typePrice = new TypePrice();
            land.setTypePrice(landService.suggestTypePrice(land, typePrice).getPrice());
            paragraph.setText(this.calculatePrice(land).toString());
        });

        return radioGroup;
    }

    private CheckboxGroup<String> prepareMediaCheckboxGroup() {
        final CheckboxGroup<String> mediaCheckBox = new CheckboxGroup<>();
        mediaCheckBox.setLabel(MEDIA);
        mediaCheckBox.setItems(POWER.getName(), GAS.getName(), WATER.getName(), SEWER.getName());

        mediaCheckBox.addValueChangeListener(e -> {
            final LandService landService = new LandService();
            land.setMedia(new ArrayList<>(e.getValue()));
            MediaPrice mediaPrice = new MediaPrice();
            land.setMediaPrice(landService.suggestMediaPrice(land, mediaPrice).getPrice());
            paragraph.setText(this.calculatePrice(land).toString());
        });

        return mediaCheckBox;
    }

    private ComboBox<String> prepareCityBox() {
        final ComboBox<String> comboBox = new ComboBox<>(CITY);
        comboBox.setItems(Arrays.asList(WARSAW.getName(), BIALYSTOK.getName(), POZNAN.getName(), WROCLAW.getName()));

        comboBox.addValueChangeListener(e -> {
            final LandService landService = new LandService();
            land.setCity(e.getValue());
            CityPrice cityPrice = new CityPrice();
            land.setCityMultiplier(landService.suggestCityPrice(land, cityPrice).getMultiplier());
            paragraph.setText(this.calculatePrice(land).toString());
        });

        return comboBox;
    }

    private IntegerField prepareSizeField() {
        final IntegerField sizeField = new IntegerField();
        sizeField.setStepButtonsVisible(true);
        sizeField.setMin(1);
        sizeField.setLabel(SIZE_IN_M2);

        sizeField.addValueChangeListener(e -> {
            final LandService landService = new LandService();
            land.setSize(e.getValue().longValue());
            SizePrice sizePrice = new SizePrice();
            land.setSizePrice(landService.suggestSizePrice(land, sizePrice).getPrice());
            paragraph.setText(this.calculatePrice(land).toString());
        });

        return sizeField;
    }

    private Land prepareLand() {
        final Land land = new Land();
        land.setSizePrice(0.0);
        land.setCityMultiplier(1);
        land.setMediaPrice(0.0);
        land.setShapeMultiplier(1.0);
        land.setSurroundingsPrice(0.0);
        land.setTypePrice(0.0);
        return land;
    }

    private Section prepareFormDetails() {
        final Section formDetails = new Section();
        formDetails.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);
        return formDetails;
    }

    private H3 prepareSiteHeader() {
        final H3 header = new H3(LAND_DETAILS);
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);
        return header;
    }

    private static HorizontalLayout preparePriceHeader(Paragraph paragraph) {
        final H2 price = new H2(PRICE);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(price);
        horizontalLayout.add(paragraph);
        horizontalLayout.addClassName(PRICE_HEADER);
        return horizontalLayout;
    }

    private Double calculatePrice(Land land){
        final double sizePrice = land.getSizePrice() != null ? land.getSizePrice():0.0;
        final int cityMultiplier = land.getCityMultiplier() != null ? land.getCityMultiplier():1;
        final double mediaPrice = land.getMediaPrice() != null ? land.getMediaPrice():0.0;
        final double typePrice = land.getTypePrice() != null ? land.getTypePrice():0.0;
        final double surroundingsPrice = land.getSurroundingsPrice() != null ? land.getSurroundingsPrice():0.0;
        final double shapeMultiplier = land.getShapeMultiplier() != null ? land.getShapeMultiplier():1.0;

        return (sizePrice * cityMultiplier + mediaPrice + typePrice + surroundingsPrice) * shapeMultiplier ;
    }
}
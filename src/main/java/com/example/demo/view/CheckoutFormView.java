package com.example.demo.view;

import com.example.demo.common.CityMultiplier;
import com.example.demo.common.MediaEnum;
import com.example.demo.common.SurroundingsEnum;
import com.example.demo.entity.Land;
import com.example.demo.prices.*;
import com.example.demo.service.LandService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.common.CityEnum.*;
import static com.example.demo.common.Dictionary.*;
import static com.example.demo.common.MediaEnum.*;
import static com.example.demo.common.ShapeEnum.IRREGULAR;
import static com.example.demo.common.ShapeEnum.REGULAR;
import static com.example.demo.common.SurroundingsEnum.*;
import static com.example.demo.common.TypeEnum.*;

@PageTitle("Wyceniator")
@Route("")
public class CheckoutFormView extends Div {
    private Land land;
    private Paragraph paragraph;
    private Button summaryButton;

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

        final HorizontalLayout horizontalLayout = this.preparePriceHeader(paragraph);

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
            this.validateSummaryButton();
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
            this.validateSummaryButton();
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
            this.validateSummaryButton();
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
            this.validateSummaryButton();
        });

        return mediaCheckBox;
    }

    private ComboBox<String> prepareCityBox() {
        final ComboBox<String> comboBox = new ComboBox<>(CITY);
        LandService landService2 = new LandService();
        comboBox.setItems(landService2.getCities(land).stream().map(CityMultiplier::getCity).toList());

        comboBox.addValueChangeListener(e -> {
            final LandService landService = new LandService();
            land.setCity(e.getValue());
            CityPrice cityPrice = new CityPrice();
            land.setCityMultiplier(landService.suggestCityPrice(land, cityPrice).getMultiplier());
            paragraph.setText(this.calculatePrice(land).toString());
            this.validateSummaryButton();
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
            this.validateSummaryButton();
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
        land.setGasPrice(3000.0);
        land.setPowerPrice(1000.0);
        land.setSewerPrice(4000.0);
        land.setWaterPrice(2000.0);
        land.setExpressPrice(10000.0);
        land.setHighwayPrice(20000.0);
        land.setTarmacPrice(5000.0);
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

    private HorizontalLayout preparePriceHeader(Paragraph paragraph) {
        final H2 price = new H2(PRICE);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(price);
        horizontalLayout.add(paragraph);
        horizontalLayout.addClassName(PRICE_HEADER);
        final Button summaryButton = this.prepareSummaryButton();
        horizontalLayout.add(summaryButton);

        return horizontalLayout;
    }

    private Button prepareSummaryButton() {
        this.summaryButton = new Button(SUMMARY);
        this.summaryButton.setEnabled(false);
        this.summaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.summaryButton.addClickListener(e -> {
            final Dialog summaryPopup = this.prepareSummaryPopup();
            summaryPopup.open();
        });
        return this.summaryButton;
    }

    private Dialog prepareSummaryPopup() {
        final Dialog summaryPopup = new Dialog();
        summaryPopup.setHeaderTitle(PRICE_SUMMARY);

        final VerticalLayout dialogLayout = createDialogLayout();
        summaryPopup.add(dialogLayout);

        final Button cancelButton = this.prepareCloseButton(summaryPopup);
        summaryPopup.getFooter().add(cancelButton);
        return summaryPopup;
    }

    private Button prepareCloseButton(Dialog summaryPopup) {
        Button cancelButton = new Button(EXIT);
        cancelButton.addClickListener(e -> summaryPopup.close());
        return cancelButton;
    }

    private VerticalLayout createDialogLayout() {
        final HorizontalLayout pricePerMeterHorizontalLayout = new HorizontalLayout();
        final H3 pricePerMeterHeader = new H3(PRICE_PER_METER);
        final double pricePerMeter = (this.land.getSizePrice() / this.land.getSize()) * this.land.getCityMultiplier();
        final Paragraph price = new Paragraph(Double.toString(pricePerMeter));
        pricePerMeterHorizontalLayout.add(pricePerMeterHeader, price);

        final H3 mediaHeader = new H3(MEDIA);
        final H3 typeHeader = new H3(TYPE);
        final H3 infrastructureHeader = new H3(INFRASTRUCTURE);
        final H3 shapeHeader = new H3(LAND_SHAPE);

        final VerticalLayout mediaVerticalLayout = new VerticalLayout();
        this.prepareMediaSummary(mediaVerticalLayout);

        final VerticalLayout typeVerticalLayout = this.prepareTypeLayout();
        final VerticalLayout shapeVerticalLayout = this.prepareShapeLayout();

        final VerticalLayout infrastructureVerticalLayout = new VerticalLayout();
        this.prepareInfrastructureSummary(infrastructureVerticalLayout);

        final VerticalLayout dialogLayout = new VerticalLayout(pricePerMeterHorizontalLayout, mediaHeader, mediaVerticalLayout,
                typeHeader, typeVerticalLayout, infrastructureHeader, infrastructureVerticalLayout, shapeHeader, shapeVerticalLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.addClassName(DIALOG_LAYOUT);

        return dialogLayout;
    }

    private void prepareInfrastructureSummary(VerticalLayout infrastructureVerticalLayout) {
        final SurroundingsEnum[] surroundingsList = SurroundingsEnum.values();
        final Map<SurroundingsEnum, Icon> infrastructureMap = new HashMap<>();
        for (SurroundingsEnum surroundingsEnum : surroundingsList) {
            final Icon check = VaadinIcon.CHECK.create();
            check.addClassName(CHECK);
            check.setColor(GREEN);

            final Icon close = VaadinIcon.CLOSE.create();
            close.addClassName(CLOSE);
            close.setColor(RED);

            infrastructureMap.put(surroundingsEnum, land.getSurroundings().contains(surroundingsEnum.getName()) ? check : close);
        }

        infrastructureMap.forEach((k, v) -> {
            final HorizontalLayout horizontalLayout = new HorizontalLayout();
            final Text infrastrucutreText = new Text(k.getName());
            Double infrastructurePrice = 0.0;
            if (v.getClassNames().contains(CHECK)) {
                switch (k) {
                    case EXPRESS -> infrastructurePrice = land.getExpressPrice();
                    case HIGHWAY -> infrastructurePrice = land.getHighwayPrice();
                    case TARMAC -> infrastructurePrice = land.getTarmacPrice();
                }

            }
            final Text priceText = new Text(" " + infrastructurePrice.toString());
            horizontalLayout.add(v, infrastrucutreText, priceText);
            infrastructureVerticalLayout.add(horizontalLayout);

        });
    }

    private VerticalLayout prepareTypeLayout() {
        final VerticalLayout typeVerticalLayout = new VerticalLayout();
        final Icon check = VaadinIcon.CHECK.create();
        check.setColor(GREEN);
        final HorizontalLayout typeHorizontalLayout = new HorizontalLayout(check, new Text(land.getType() + " " + land.getTypePrice()));
        typeVerticalLayout.add(typeHorizontalLayout);
        return typeVerticalLayout;
    }

    private VerticalLayout prepareShapeLayout() {
        final VerticalLayout shapeVerticalLayout = new VerticalLayout();
        final Icon check = land.isRegular() ? VaadinIcon.CHECK.create() : VaadinIcon.CLOSE.create();
        check.setColor(land.isRegular() ? GREEN : RED);

        String text = land.isRegular() ? REGULAR.getName() : IRREGULAR.getName();

        final HorizontalLayout typeHorizontalLayout = new HorizontalLayout(check, new Text(text + " x" + land.getShapeMultiplier()));
        shapeVerticalLayout.add(typeHorizontalLayout);
        return shapeVerticalLayout;
    }

    private void prepareMediaSummary(VerticalLayout mediaHorizontalLayout) {
        final MediaEnum[] mediaList = MediaEnum.values();
        final Map<MediaEnum, Icon> mediaMap = new HashMap<>();
        for (MediaEnum mediaEnum : mediaList) {
            final Icon check = VaadinIcon.CHECK.create();
            check.addClassName(CHECK);
            check.setColor(GREEN);

            final Icon close = VaadinIcon.CLOSE.create();
            close.addClassName(CLOSE);
            close.setColor(RED);

            mediaMap.put(mediaEnum, land.getMedia().contains(mediaEnum.getName()) ?
                    check : close);
        }

        mediaMap.forEach((k, v) -> {
            final HorizontalLayout horizontalLayout = new HorizontalLayout();
            final Text mediaText = new Text(k.getName());
            Double mediaPrice = 0.0;
            if (v.getClassNames().contains(CHECK)) {
                switch (k) {
                    case POWER -> mediaPrice = land.getPowerPrice();
                    case WATER -> mediaPrice = land.getWaterPrice();
                    case GAS -> mediaPrice = land.getGasPrice();
                    case SEWER -> mediaPrice = land.getSewerPrice();
                }
            }

            final Text priceText = new Text(" " + mediaPrice.toString());
            horizontalLayout.add(v, mediaText, priceText);
            mediaHorizontalLayout.add(horizontalLayout);
        });
    }

    private Double calculatePrice(Land land) {
        final double sizePrice = land.getSizePrice() != null ? land.getSizePrice() : 0.0;
        final int cityMultiplier = land.getCityMultiplier() != null ? land.getCityMultiplier() : 1;
        final double mediaPrice = land.getMediaPrice() != null ? land.getMediaPrice() : 0.0;
        final double typePrice = land.getTypePrice() != null ? land.getTypePrice() : 0.0;
        final double surroundingsPrice = land.getSurroundingsPrice() != null ? land.getSurroundingsPrice() : 0.0;
        final double shapeMultiplier = land.getShapeMultiplier() != null ? land.getShapeMultiplier() : 1.0;

        return (sizePrice * cityMultiplier + mediaPrice + typePrice + surroundingsPrice) * shapeMultiplier;
    }

    private void validateSummaryButton(){
        this.summaryButton.setEnabled(this.land.getSize() != null && this.land.getType() != null
                && this.land.getShapeMultiplier() != 1.0 && this.land.getCity() != null);
    }
}
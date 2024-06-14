package com.example.demo.view;

import com.example.demo.common.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.common.Dictionary.*;
import static com.example.demo.common.ShapeEnum.IRREGULAR;
import static com.example.demo.common.ShapeEnum.REGULAR;

@PageTitle("Wyceniator")
@Route("")
public class CheckoutFormView extends Div {
    final private LandService mapGetterService = new LandService();
    private Land land;
    private Paragraph paragraph;
    private Button summaryButton;
    private TotalPriceFact totalPriceFact = new TotalPriceFact();

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
        LandService landService2 = new LandService();
        surroundingsCheckboxGroup.setItems(landService2.getSurroundings(land, totalPriceFact).stream().map(obj -> obj.getSurroundingsList().get(0)).toArray(String[]::new));

        surroundingsCheckboxGroup.addValueChangeListener(e -> {
            land.setSurroundings(new ArrayList<>(e.getValue()));
            LandService landService = new LandService();
            landService.getSurroundings(land, totalPriceFact);
            paragraph.setText(this.calculatePrice().toString());
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

            land.setShapeMultiplier(landService.suggestShapePrice(land, totalPriceFact));
            paragraph.setText(this.calculatePrice().toString());
            this.validateSummaryButton();
        });

        return shapeRadioGroup;
    }

    private RadioButtonGroup<String> prepareTypeRadioGroup() {
        final RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel(TYPE);
        LandService landService2 = new LandService();

        radioGroup.setItems(landService2.getTypes(land, totalPriceFact).stream().map(LandTypePrice::getType).toArray(String[]::new));

        radioGroup.addValueChangeListener(e -> {
            land.setType(e.getValue());
            LandService landService = new LandService();
            landService.getTypes(land, totalPriceFact);
            paragraph.setText(this.calculatePrice().toString());
            this.validateSummaryButton();
        });

        return radioGroup;
    }

    private CheckboxGroup<String> prepareMediaCheckboxGroup() {
        final CheckboxGroup<String> mediaCheckBox = new CheckboxGroup<>();
        mediaCheckBox.setLabel(MEDIA);
        LandService landService2 = new LandService();

        mediaCheckBox.setItems(landService2.getMedia(land, totalPriceFact).stream().map(obj -> obj.getMediaList().get(0)).toArray(String[]::new));

        mediaCheckBox.addValueChangeListener(e -> {
            land.setMedia(new ArrayList<>(e.getValue()));
            LandService service = new LandService();
            service.getMedia(land, totalPriceFact);
            paragraph.setText(this.calculatePrice().toString());
            this.validateSummaryButton();
        });

        return mediaCheckBox;
    }

    private ComboBox<String> prepareCityBox() {
        final ComboBox<String> comboBox = new ComboBox<>(CITY);
        LandService landService2 = new LandService();
        comboBox.setItems(landService2.getCities(land, totalPriceFact).stream().map(CityMultiplier::getCity).toList());

        comboBox.addValueChangeListener(e -> {
            land.setCity(e.getValue());
            LandService service = new LandService();
            service.getCities(land, totalPriceFact);
            paragraph.setText(this.calculatePrice().toString());
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
            land.setSizePrice(landService.suggestSizePrice(land, totalPriceFact));
            paragraph.setText(this.calculatePrice().toString());
            this.validateSummaryButton();
        });

        return sizeField;
    }

    private Land prepareLand() {
        final Land land = new Land();


        land.setCityMap(this.mapGetterService.getCityMap(land, totalPriceFact));
        land.setMediaMap(this.mapGetterService.getMediaMap(land, totalPriceFact));
        land.setTypeMap(this.mapGetterService.getTypeMap(land, totalPriceFact));
        land.setSurroundingsMap(this.mapGetterService.getSurroundingsMap(land, totalPriceFact));
        land.setShapeMultiplier(1.0);

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
        final double pricePerMeter = (this.land.getSizePrice() / this.land.getSize()) * Double.parseDouble(this.land.getCityMap().get(this.land.getCity()));
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
        final List<SurroundingsPriceFact> surroundingsList = this.mapGetterService.getSurroundings(this.land, totalPriceFact).stream().toList();
        final Map<SurroundingsPriceFact, Icon> infrastructureMap = new HashMap<>();
        for (SurroundingsPriceFact surroundingsEnum : surroundingsList) {
            final Icon check = VaadinIcon.CHECK.create();
            check.addClassName(CHECK);
            check.setColor(GREEN);

            final Icon close = VaadinIcon.CLOSE.create();
            close.addClassName(CLOSE);
            close.setColor(RED);

            infrastructureMap.put(surroundingsEnum, land.getSurroundings().contains(surroundingsEnum.getSurroundingsList().get(0)) ? check : close);
        }
        infrastructureMap.forEach((k, v) -> {

            final HorizontalLayout horizontalLayout = new HorizontalLayout();
            final Text infrastrucutreText = new Text(k.getSurroundingsList().get(0));
            Double infrastructurePrice = 0.0;
            final Text priceText = new Text(" " + (this.land.getSurroundings().size() != 0 ? this.land.getSurroundingsMap().get(k.getSurroundingsList().get(0)) : infrastructurePrice));
            horizontalLayout.add(v, infrastrucutreText, priceText);
            infrastructureVerticalLayout.add(horizontalLayout);

        });
    }

    private VerticalLayout prepareTypeLayout() {
        final VerticalLayout typeVerticalLayout = new VerticalLayout();
        final Icon check = VaadinIcon.CHECK.create();
        check.setColor(GREEN);
        final HorizontalLayout typeHorizontalLayout = new HorizontalLayout(check, new Text(land.getType() + " " + land.getTypeMap().get(land.getType())));
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
        final List<MediaPriceFact> mediaList = this.mapGetterService.getMedia(this.land, totalPriceFact);
        final Map<MediaPriceFact, Icon> mediaMap = new HashMap<>();
        for (MediaPriceFact mediaEnum : mediaList) {
            final Icon check = VaadinIcon.CHECK.create();
            check.addClassName(CHECK);
            check.setColor(GREEN);

            final Icon close = VaadinIcon.CLOSE.create();
            close.addClassName(CLOSE);
            close.setColor(RED);

            mediaMap.put(mediaEnum, land.getMedia().contains(mediaEnum.getMediaList().get(0)) ?
                    check : close);
        }

        mediaMap.forEach((k, v) -> {
            final HorizontalLayout horizontalLayout = new HorizontalLayout();
            final Text mediaText = new Text(k.getMediaList().get(0));
            Double mediaPrice = 0.0;

            final Text priceText = new Text(" " + (this.land.getMedia().size() != 0 ? this.land.getMediaMap().get(k.getMediaList().get(0)) : mediaPrice));

            horizontalLayout.add(v, mediaText, priceText);
            mediaHorizontalLayout.add(horizontalLayout);
        });
    }

    private Double calculatePrice() {
        LandService landService = new LandService();
        return landService.recalculate(totalPriceFact);
    }

    private void validateSummaryButton() {
        this.summaryButton.setEnabled(this.land.getSize() != null && this.land.getType() != null
                && this.land.getShapeMultiplier() != 1.0 && this.land.getCity() != null);
    }
}
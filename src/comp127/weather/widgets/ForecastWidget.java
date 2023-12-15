package comp127.weather.widgets;

import comp127.weather.api.CurrentConditions;
import comp127.weather.api.ForecastConditions;
import comp127.weather.api.WeatherData;
import edu.macalester.graphics.*;

import java.util.ArrayList;
import java.util.List;

public class ForecastWidget implements WeatherWidget {

    private final double size;
    private GraphicsGroup group;



    // TODO: Add instance variables for any UI elements that you create in the constructor but
    //       will need to update later with new data

    private GraphicsGroup boxGroup;  // Holds all the ForecastBox objects
    public GraphicsText dateBox;
    public GraphicsText timeBox;
    public Image icon;
    public GraphicsText tempLabel;
    public GraphicsText highLowLabel;
    public GraphicsText description;

    private List<ForecastBox> boxes = new ArrayList<>();

    public ForecastWidget(double size) {
        this.size = size;

        group = new GraphicsGroup();

        dateBox= new GraphicsText();
        dateBox.setFont(FontStyle.BOLD, size * 0.05);
        group.add(dateBox);

        timeBox= new GraphicsText();
        timeBox.setFont(FontStyle.BOLD, size * 0.05);
        group.add(timeBox);

        icon = new Image(0, 0);
        icon.setMaxWidth(size);
        icon.setMaxHeight(size * 0.5);
        group.add(icon);

        tempLabel= new GraphicsText();
        tempLabel.setFont(FontStyle.BOLD, size * 0.05);
        group.add(tempLabel);

        highLowLabel= new GraphicsText();
        highLowLabel.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(highLowLabel);

        description= new GraphicsText();
        description.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(description);



        // TODO: Create the various text and image elements you will need (but not the row of boxes
        //      at the bottom; those you will create in update())

        boxGroup = new GraphicsGroup();
        group.add(boxGroup);

        updateLayout();
    }

    @Override
    public GraphicsObject getGraphics() {
        return group;
    }

    public void update(WeatherData data) {
        boxGroup.removeAll();

        // TODO: Remove all the existing elements from boxGroup.
        //       HINT: check the javadoc for GraphicsGroup to keep this simple!

        boxes.clear();  // Remove all the old ForecastBoxes from our list


        List<ForecastConditions> forecastData = data.getForecasts();
        
        double x = 10;
        double  y =size *.95;
       
        for (ForecastConditions condition : forecastData){
            ForecastBox newCondition = new ForecastBox(condition, x, y, size*.05, size*.05);
            x= x+newCondition.getWidth();
            if(x>group.getWidth()){
                x = 10;
                y= y+ newCondition.getHeight();
            }

            boxGroup.add(newCondition);
            boxes.add(newCondition);
            newCondition.setCenter(x, y);
        }
        // TODO: Loop through all the ForecastConditions objects from data, and for each one:
        //       - Wrap it in a new ForecastBox
        //       - Position it to the right of the previous box, wrapping to a new line if you are
        //         past the end of the current one.
        //       - Add the new box to the graphics group
        //       - Add the new box to the `boxes` list

        selectForecast(boxes.get(0));
        // TODO: Call selectForecast() with the first ForecastBox, which will update the various
        //       text and icon elements.
    }

    private void selectForecast(ForecastBox box) {
       
       for (ForecastBox currentBox: boxes){
        if (currentBox.equals(box)){
            currentBox.setActive(true);
        }
        else{
            currentBox.setActive(false);
        }
       }
        // TODO: Call setActive() for all the forecast boxes, with true for the selected box and
        //       false for all the others (so that the previously active one becomes inactive).

        // TODO: Get the forecast data from the box, and use it to update the text and icon.
        ForecastConditions currentConditions = box.getForecast();
        icon.setImagePath(currentConditions.getWeatherIcon());

        tempLabel.setText(
            FormattingHelpers.checkNullToDecimal(currentConditions.getTemperature())
        
             + "\u2109");  

        highLowLabel.setText(
            FormattingHelpers.checkNullToDecimal(currentConditions.getMaxTemperature())+
             "\u2109" + "|"+
            FormattingHelpers.checkNullToDecimal(currentConditions.getMinTemperature())+
              "\u2109"); 

        description.setText(currentConditions.getWeatherDescription());
        dateBox.setText(FormattingHelpers.WEEKDAY_AND_NAME.format(currentConditions.getPredictionTime()));
       timeBox.setText(FormattingHelpers.TIME_OF_DAY.format(currentConditions.getPredictionTime()));
        
        updateLayout();
    }

    private void updateLayout() {

    dateBox.setCenter(dateBox.getWidth() * .5 + 5,size * .05);
    timeBox.setCenter(size- (timeBox.getWidth()/2) -5, size * .05);
    icon.setCenter(size * .5, size*.3);
    tempLabel.setCenter(size * .5, size * .3 + icon.getHeight() );
    highLowLabel.setCenter(size * .5, size * .3 + icon.getHeight() + (size *.07));
    description.setCenter(size *.5, size * .3 + icon.getHeight() + tempLabel.getHeight() +(size *.1));
    

        
        // TODO: Place all the elements on the canvas in the correct position
        //       HINT: Use multiples of size instead of absolute pixel measurements to adjust to
        //             different widget sizes.
        //       HINT: Study the methods of GraphicsObject to find different ways of positioning and
        //             measuring graphics objects.
    }

    /**
     * Given a position in the widget, this returns the ForecastBox at that position if one exists
     *
     * @param location pos to check
     * @return null if not over a forecast box
     */
    private ForecastBox getBoxAt(Point location) {
        GraphicsObject obj = group.getElementAt(location);
        if (obj instanceof ForecastBox) {
            return (ForecastBox) obj;
        }
        return null;
    }

    /**
     * Updates the currently displayed forecast information as the mouse moves over the widget.
     * If there is not a ForecastBox at that position, the display does not change.
     */
    @Override
    public void onHover(Point position) {
       if (getBoxAt(position)!= null){
        selectForecast(getBoxAt(position));
       }
       
       
        // TODO: Check if there is a box at the current mouse position.
        //       If there is, make it the selected forecast.
        //       HINT: Study the methods above! They will help you immensely.
        //       HINT: This should be a small method. If it gets complicated,
        //             you‘ve gone off the rails.
    }
}

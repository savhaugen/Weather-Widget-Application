package comp127.weather.widgets;

import comp127.weather.api.CurrentConditions;
import comp127.weather.api.WeatherData;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

public class SunriseSunsetWidget implements WeatherWidget{
    
    private final double size;
    private GraphicsGroup group;

    private GraphicsText sunriseLabel;
    private GraphicsText sunsetLabel;
    private GraphicsText sunriseBox;
    private GraphicsText sunsetBox;
    private Image sunriseIcon;
    private Image sunsetIcon;


    public SunriseSunsetWidget(double size){
        this.size = size;

        group = new GraphicsGroup();

        sunriseIcon = new Image(0, 0);
        sunriseIcon.setMaxWidth(size*.5);
        sunriseIcon.setMaxHeight(size * 0.4);
        group.add(sunriseIcon);

        sunsetIcon = new Image(0, 0);
        sunsetIcon.setMaxWidth(size*.5);
        sunsetIcon.setMaxHeight(size * 0.4);
        group.add(sunsetIcon);

        sunriseLabel = new GraphicsText();
        sunriseLabel.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(sunriseLabel);

        sunsetLabel = new GraphicsText();
        sunsetLabel.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(sunsetLabel);

        sunriseBox = new GraphicsText();
        sunriseBox.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(sunriseBox);

        sunsetBox = new GraphicsText();
        sunsetBox.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(sunsetBox);

        updateLayout();

    }

      @Override
    public GraphicsObject getGraphics() {
        return group;
    }

    public void update(WeatherData data) {
        CurrentConditions currentConditions = data.getCurrentConditions();

        sunriseIcon.setImagePath("condition-icons/01d.png");
        sunsetIcon.setImagePath("condition-icons/01n.png");

        sunriseLabel.setText(FormattingHelpers.TIME_OF_DAY.format(currentConditions.getSunriseTime()));
        sunsetLabel.setText(FormattingHelpers.TIME_OF_DAY.format(currentConditions.getSunsetTime()));

        sunriseBox.setText("Sunrise Time");
        sunsetBox.setText("Sunset Time");

        updateLayout();
    }

      private void updateLayout() {
        sunriseIcon.setCenter(size * 0.25, size * 0.4);
        sunsetIcon.setCenter(size * 0.7, size * 0.4);

        sunriseLabel.setCenter(size * .25, size * .55 +15);
        sunsetLabel.setCenter(size * .7, size * .55+15);

        sunriseBox.setCenter(size *.25, size*.65+10);
        sunsetBox.setCenter(size*.7, size*.65+10);
        
    }

    @Override
    public void onHover(Point position) {
    }
}




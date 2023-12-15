package comp127.weather.widgets;

import comp127.weather.api.CurrentConditions;
import comp127.weather.api.WeatherData;
import edu.macalester.graphics.*;

public class HumidityWidget implements WeatherWidget{
    
    private final double size;
    private GraphicsGroup group;

    private GraphicsText label;
    private GraphicsText description;
    

    public HumidityWidget(double size){
        this.size = size;
        group = new GraphicsGroup();


        label = new GraphicsText();
        label.setFont(FontStyle.BOLD, size * 0.1);
        group.add(label);

        description = new GraphicsText();
        description.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(description);

        updateLayout();
    }

    public void update(WeatherData data) {
        CurrentConditions c = data.getCurrentConditions();
        // double currentHumidity=c.getHumidity();

        label.setText(FormattingHelpers.checkNullToDecimal(c.getHumidity())+"%");

        description.setText("Humidity");
        updateLayout();

    }

    private void updateLayout() {

        label.setCenter(size * 0.5, size * 0.8);
        description.setCenter(size * 0.5, size * 0.8 + description.getHeight()+(size*.05));
  
    }
        


    @Override
    public void onHover(Point position) {
        // This widget is not interactive, so this method does nothing.
    }

    @Override
    public GraphicsObject getGraphics() {
        return group;
    }

}



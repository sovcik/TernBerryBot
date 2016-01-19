package tern.hwbutton;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import tern.Logger;

/**
 * Created by Jozef on 18.01.2016.
 */
public class RaspiGPIOButton extends HWButton {

    // gpio controller
    protected GpioController controller;

    protected GpioPinDigitalInput myButton;

    protected Logger log;


    public RaspiGPIOButton(){

        if (this.log == null)
            log = new Logger();

        init();
    }

    public RaspiGPIOButton(Logger log){
        this.log = log;
        init();
    }

    protected void init(){

        log.log("Connecting to Raspberry Pi GPIO.");

        // create gpio controller
        controller = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        myButton = controller.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                log.log(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
            }

        });

    }

    public void disconnect(){
        controller.shutdown();
    }
}

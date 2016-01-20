package tern.hwbutton;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import tern.Logger;
import tern.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Jozef on 18.01.2016.
 */
public class RaspiGPIOButton extends HWButton implements GpioPinListenerDigital{

    // gpio controller
    protected GpioController controller;

    protected GpioPinDigitalInput myButton;

    // class which will handle changes in GPIO pin statuses
    // this class is going to emulate key-press on GPIO change
    protected KeyListener listener;

    // AWT component which key-press is going to be sent to
    protected Component component;

    protected Logger log;

    public RaspiGPIOButton(){

        if (this.log == null)
            log = new Logger();

        init();
    }

    public RaspiGPIOButton(Logger log, KeyListener l, Component c){
        this.log = log;
        this.listener = l;
        this.component = c;

        init();
    }

    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        // display pin state on console
        log.log(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
        System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
        if (listener != null)
            // send key press in specified component to specified key event listener
            listener.keyPressed(new KeyEvent(
                    component,
                    KeyEvent.KEY_PRESSED,
                    10, 0,
                    KeyEvent.VK_F5,
                    KeyEvent.CHAR_UNDEFINED));
    }


    protected void init(){

        log.log("Connecting to Raspberry Pi GPIO.");
        System.out.println("Connecting to Raspberry Pi GPIO.");

        // create gpio controller
        controller = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        myButton = controller.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

        // register this class as gpio pin listener
        myButton.addListener(this);

    }


    public void disconnect(){
        controller.shutdown();
    }
}

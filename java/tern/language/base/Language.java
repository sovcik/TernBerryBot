package tern.language.base;

/**
 * Created by Jozef on 12.01.2016.
 */
abstract public class Language {

    // helper method to register all statements for specific language
    public static void registerStatements(){
        Backward.register();
        Beep.register();
        End.register();
        Forward.register();
        Go.register();
        Growl.register();
        Left.register();
        Num.register();
        Repeat.register();
        Right.register();
        Sensor.register();
        Shake.register();
        Shuffle.register();
        Song.register();
        Spin.register();
        Start.register();
        Stop.register();
        Wait.register();
        Whistle.register();
        Wiggle.register();

    }

}

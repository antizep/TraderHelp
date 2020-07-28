package traderHelp.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class IndicateController {
	
	private GpioController controller = GpioFactory.getInstance();
	private GpioPinDigitalOutput out1;
	private GpioPinDigitalOutput out2;
	
	public void indikate() {
		if (out1 == null) {
			out1 = controller.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.HIGH);
		}else {
			out1.high();
		}
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		out1.low();
		if (out2 == null) {
			out2 = controller.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.HIGH);
		}else {
			out2.high();
		}

		try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		out2.low();

	}
	
}

package javax.flat.bind.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Gloaguen Joel 
 *
 * class pour effectuer des test de perf
 *
 */
public class TimerPerf {
	private long t;
	DateFormat df = new SimpleDateFormat(" mm 'mins,' ss.S 'seconds' ");

	public TimerPerf() {
		reset();
	}

	public long elapsed() {
		return System.currentTimeMillis() - t;
	}

	public void print(String s) {

		System.out.println(s + " => " + df.format(new Date(this.elapsed())));
	}

	public String print() {

		return df.format(new Date(this.elapsed()));
	}

	public void reset() {
		t = System.currentTimeMillis();
	}
}
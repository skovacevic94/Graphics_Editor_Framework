/**
 * @author Stanko 14.06.2014.
 * ExitLineListener.java
 */
package gdiagram.controller;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * @author Stanko
 *
 */
public class ExitLineListener implements LineListener{

	/* (non-Javadoc)
	 * @see javax.sound.sampled.LineListener#update(javax.sound.sampled.LineEvent)
	 */
	@Override
	public void update(LineEvent event) {
		// TODO Auto-generated method stub
		if(event.getType() == LineEvent.Type.STOP)
			System.exit(0);
	}

}

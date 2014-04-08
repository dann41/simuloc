package com.dglapps.simuloc.desktop;

import com.dglapps.simuloc.Position;
import com.dglapps.simuloc.PositionReceiver;

public class ConsolePositionReceiver implements PositionReceiver {

	@Override
	public void onPositionReceived(Position p) {
		System.out.println(p.getLatitude() + "," + p.getLongitude());
	}

}

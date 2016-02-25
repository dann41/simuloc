package com.dglapps.simuloc.desktop;

import com.dglapps.simuloc.PositionReceiver;
import com.dglapps.simuloc.entities.Position;

public class ConsolePositionReceiver implements PositionReceiver {

	@Override
	public void onPositionReceived(Position p) {
		System.out.println(p.getLatitude() + "," + p.getLongitude());
	}

}
